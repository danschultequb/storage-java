package qub;

/**
 * An object that stores Binary Large OBjects (BLOBs).
 */
public interface BlobStorage
{
    /**
     * Get an {@link Iterator} that will return the blobs in this {@link BlobStorage}.
     */
    public Iterator<Blob> iterateBlobs();

    /**
     * Get the number of {@link Blob}s that are in this {@link BlobStorage}.
     */
    public Result<Integer> getBlobCount();

    /**
     * Get a {@link Blob} reference that can be used to access a {@link Blob} in this
     * {@link BlobStorage}. This method only creates the {@link Blob} reference and doesn't
     * validate that the {@link Blob} actually exists.
     * @param blobId The {@link BlobId} of the {@link Blob}.
     */
    public default Blob getBlob(BlobId blobId)
    {
        PreCondition.assertNotNull(blobId, "blobId");

        return Blob.create(this, blobId);
    }

    /**
     * Get whether a {@link Blob} exists for the provided {@link BlobId}.
     * @param blobId The {@link BlobId} of the {@link Blob} to look for.
     */
    public Result<Boolean> blobExists(BlobId blobId);

    /**
     * Get the byte count of the {@link Blob} with the provided {@link BlobId}.
     * @param blobId The {@link BlobId} of the {@link Blob}.
     * @return The byte count of the {@link Blob} with the provided {@link BlobId}.
     */
    public Result<Long> getBlobByteCount(BlobId blobId);

    /**
     * Get the byte contents of the {@link Blob} with the provided {@link BlobId}.
     * @param blobId The {@link BlobId} of the {@link Blob}.
     */
    public Result<ByteReadStream> getBlobContents(BlobId blobId);

    /**
     * Create a new {@link Blob} from the provided contents.
     * @param blobContents The contents of the new {@link Blob}.
     * @return The new {@link Blob}.
     */
    public default Result<Blob> createBlob(byte[] blobContents)
    {
        PreCondition.assertNotNull(blobContents, "blobContents");

        return this.createBlob(InMemoryByteStream.create(blobContents).endOfStream());
    }

    /**
     * Create a new {@link Blob} from the provided contents.
     * @param blobContents The contents of the new {@link Blob}.
     * @return The new {@link Blob}.
     */
    public Result<Blob> createBlob(ByteReadStream blobContents);

    /**
     * Get a {@link Blob} with the provided contents, or if no {@link Blob} already exists, then
     * create and return a new {@link Blob} with the provided contents.
     * @param blobContents The {@link Blob} contents.
     */
    public default Result<Blob> getOrCreateBlob(byte[] blobContents)
    {
        PreCondition.assertNotNull(blobContents, "blobContents");

        return this.getOrCreateBlob(InMemoryByteStream.create(blobContents).endOfStream());
    }

    /**
     * Get a {@link Blob} with the provided contents, or if no {@link Blob} already exists, then
     * create and return a new {@link Blob} with the provided contents.
     * @param blobContents The {@link Blob} contents.
     */
    public default Result<Blob> getOrCreateBlob(ByteReadStream blobContents)
    {
        PreCondition.assertNotNull(blobContents, "blobContents");

        return this.createBlob(blobContents)
            .catchError(BlobAlreadyExistsException.class, BlobAlreadyExistsException::getAlreadyExistingBlob);
    }

    /**
     * Assert that the provided {@link BlobId} is not null and not empty.
     * @param blobId The {@link BlobId} to check.
     * @param expressionName The "name" of the expression that created the {@link BlobId} to check.
     */
    public static void assertNotNullAndNotEmpty(BlobId blobId, String expressionName)
    {
        PreCondition.assertNotNull(blobId, expressionName);
        PreCondition.assertGreaterThanOrEqualTo(blobId.getElementCount(), 1, expressionName + ".getElementCount()");
    }
}
