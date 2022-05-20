package qub;

/**
 * An object that stores Binary Large OBjects (BLOBs).
 */
public interface BlobStorage
{
    /**
     * Get the {@link Blob} that has the provided checksum.
     * @param checksum The checksum of the {@link Blob}.
     * @return The matching {@link Blob}.
     */
    public default Blob getBlob(BlobChecksum checksum)
    {
        PreCondition.assertNotNull(checksum, "checksum");

        return Blob.create(this, checksum);
    }

    /**
     * Get an {@link Iterator} that will return all of the blobs in this {@link BlobStorage}.
     */
    public Iterator<Blob> iterateBlobs();

    /**
     * Get whether a {@link Blob} exists for the provided checksum.
     * @param checksum The checksum of the {@link Blob}.
     */
    public Result<Boolean> blobExists(BlobChecksum checksum);

    /**
     * Get the byte count of the {@link Blob} with the provided {@link BlobChecksum}.
     * @param checksum The {@link BlobChecksum} of the {@link Blob}.
     * @return The byte count of the blob with the provided checksum.
     */
    public Result<Long> getBlobByteCount(BlobChecksum checksum);

    /**
     * Get the byte contents of the {@link Blob} with the provided {@link BlobChecksum}.
     * @param checksum The {@link BlobChecksum} of the {@link Blob}.
     * @return The byte contents of the {@link Blob} with the provided {@link BlobChecksum}.
     */
    public Result<ByteReadStream> getBlobContents(BlobChecksum checksum);

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
}
