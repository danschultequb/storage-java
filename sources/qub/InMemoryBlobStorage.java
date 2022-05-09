package qub;

public class InMemoryBlobStorage implements BlobStorage
{
    private final MutableMap<BlobChecksum,byte[]> blobContents;

    private InMemoryBlobStorage()
    {
        this.blobContents = Map.create();
    }

    public static InMemoryBlobStorage create()
    {
        return new InMemoryBlobStorage();
    }

    private Result<byte[]> getBlobBytes(BlobChecksum checksum)
    {
        PreCondition.assertNotNull(checksum, "checksum");

        return this.blobContents.get(checksum)
            .convertError(NotFoundException.class, () ->
            {
                return new BlobNotFoundException(this.getBlob(checksum));
            });
    }

    @Override
    public Iterator<Blob> iterateBlobs()
    {
        return this.blobContents.getKeys().iterate()
            .map((BlobChecksum checksum) ->
            {
                return Blob.create(this, checksum);
            });
    }

    @Override
    public Result<Boolean> blobExists(BlobChecksum checksum)
    {
        PreCondition.assertNotNull(checksum, "checksum");

        return Result.create(() ->
        {
            final byte[] blobBytes = this.getBlobBytes(checksum).catchError().await();
            return blobBytes != null;
        });
    }

    @Override
    public Result<Long> getBlobByteCount(BlobChecksum checksum)
    {
        PreCondition.assertNotNull(checksum, "checksum");

        return Result.create(() ->
        {
            final byte[] blobBytes = this.getBlobBytes(checksum).await();
            return (long)blobBytes.length;
        });
    }

    @Override
    public Result<ByteReadStream> getBlobContents(BlobChecksum checksum)
    {
        PreCondition.assertNotNull(checksum, "checksum");

        return Result.create(() ->
        {
            final byte[] blobBytes = this.getBlobBytes(checksum).await();
            return InMemoryByteStream.create(blobBytes).endOfStream();
        });
    }

    @Override
    public Result<Blob> createBlob(ByteReadStream blobContents)
    {
        PreCondition.assertNotNull(blobContents, "blobContents");

        return Result.create(() ->
        {
            final byte[] blobBytes = blobContents.readAllBytes().await();

            final BlobChecksumType checksumType = BlobChecksumType.MD5;
            final BitArray checksumValue = MD5.hash(blobBytes).await();
            final BlobChecksum checksum = BlobChecksum.create(checksumType, checksumValue);

            if (this.blobContents.containsKey(checksum))
            {
                throw new BlobAlreadyExistsException(this.getBlob(checksumType, checksumValue));
            }
            this.blobContents.set(checksum, blobBytes);

            return this.getBlob(checksumType, checksumValue);
        });
    }
}
