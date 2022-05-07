package qub;

public class InMemoryBlobStorage implements BlobStorage
{
    private final MutableMap<Tuple2<String,BitArray>,byte[]> blobContents;

    private InMemoryBlobStorage()
    {
        this.blobContents = Map.create();
    }

    public static InMemoryBlobStorage create()
    {
        return new InMemoryBlobStorage();
    }

    private static Tuple2<String,BitArray> getBlobKey(BlobChecksumType checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNull(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return InMemoryBlobStorage.getBlobKey(checksumType.toString(), checksumValue);
    }

    private static Tuple2<String,BitArray> getBlobKey(String checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNullAndNotEmpty(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return Tuple.create(checksumType.toLowerCase(), checksumValue);
    }

    private Result<byte[]> getBlobBytes(String checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNullAndNotEmpty(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return this.blobContents.get(InMemoryBlobStorage.getBlobKey(checksumType, checksumValue))
            .convertError(NotFoundException.class, () ->
            {
                return new BlobNotFoundException(this.getBlob(checksumType, checksumValue));
            });
    }

    @Override
    public Iterator<Blob> iterateBlobs()
    {
        return this.blobContents.getKeys().iterate()
            .map((Tuple2<String,BitArray> blobKey) ->
            {
                return Blob.create(this, blobKey.getValue1(), blobKey.getValue2());
            });
    }

    @Override
    public Result<Boolean> blobExists(String checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNullAndNotEmpty(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return Result.create(() ->
        {
            final byte[] blobBytes = this.getBlobBytes(checksumType, checksumValue).catchError().await();
            return blobBytes != null;
        });
    }

    @Override
    public Result<Long> getBlobByteCount(String checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNullAndNotEmpty(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return Result.create(() ->
        {
            final byte[] blobBytes = this.getBlobBytes(checksumType, checksumValue).await();
            return (long)blobBytes.length;
        });
    }

    @Override
    public Result<ByteReadStream> getBlobContents(String checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNullAndNotEmpty(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return Result.create(() ->
        {
            final byte[] blobBytes = this.getBlobBytes(checksumType, checksumValue).await();
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
            final Tuple2<String,BitArray> blobKey = InMemoryBlobStorage.getBlobKey(checksumType, checksumValue);

            if (this.blobContents.containsKey(blobKey))
            {
                throw new BlobAlreadyExistsException(this.getBlob(checksumType, checksumValue));
            }
            this.blobContents.set(blobKey, blobBytes);

            return this.getBlob(checksumType, checksumValue);
        });
    }
}
