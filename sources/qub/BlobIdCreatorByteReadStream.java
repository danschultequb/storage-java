package qub;

public class BlobIdCreatorByteReadStream implements ByteReadStream
{
    private final ByteReadStream innerStream;
    private final Iterable<? extends BlobIdElementCreator> blobIdElementCreators;

    private BlobIdCreatorByteReadStream(ByteReadStream innerStream, Iterable<? extends BlobIdElementCreator> blobIdElementCreators)
    {
        PreCondition.assertNotNull(innerStream, "innerStream");
        PreCondition.assertNotNullAndNotEmpty(blobIdElementCreators, "blobIdElementCreators");

        this.innerStream = innerStream;
        this.blobIdElementCreators = blobIdElementCreators;
    }

    public static BlobIdCreatorByteReadStream create(ByteReadStream innerStream, Iterable<? extends BlobIdElementCreator> blobIdElementCreators)
    {
        return new BlobIdCreatorByteReadStream(innerStream, blobIdElementCreators);
    }

    @Override
    public Result<Byte> readByte()
    {
        return Result.create(() ->
        {
            final byte result = this.innerStream.readByte().await();
            for (final BlobIdElementCreator blobIdElementCreator : this.blobIdElementCreators)
            {
                blobIdElementCreator.addByte(result);
            }
            return result;
        });
    }

    @Override
    public Result<Integer> readBytes(byte[] outputBytes, int startIndex, int length)
    {
        return Result.create(() ->
        {
            final int result = this.innerStream.readBytes(outputBytes, startIndex, length).await();
            for (final BlobIdElementCreator blobIdElementCreator : this.blobIdElementCreators)
            {
                blobIdElementCreator.addBytes(outputBytes, startIndex, result);
            }
            return result;
        });
    }

    @Override
    public boolean isDisposed()
    {
        return this.innerStream.isDisposed();
    }

    @Override
    public Result<Boolean> dispose()
    {
        return Result.create(() ->
        {
            for (final BlobIdElementCreator blobIdElementCreator : this.blobIdElementCreators)
            {
                blobIdElementCreator.dispose().await();
            }
            return this.innerStream.dispose().await();
        });
    }

    /**
     * Take the {@link BlobId} that has been created/generated. This will reset this
     * {@link BlobIdCreatorByteReadStream}'s {@link BlobIdElementCreator}s back to their initial
     * states.
     */
    public BlobId takeBlobId()
    {
        final MutableBlobId result = MutableBlobId.create();
        for (final BlobIdElementCreator blobIdElementCreator : this.blobIdElementCreators)
        {
            result.addElement(blobIdElementCreator.takeBlobIdElement());
        }

        PostCondition.assertNotNull(result, "result");
        PostCondition.assertGreaterThanOrEqualTo(result.getElementCount(), 1, "result.getElementCount()");

        return result;
    }
}
