package qub;

/**
 * An object that stores Binary Large OBjects (BLOBs).
 */
public interface BlobStorage
{
    /**
     * Get the {@link Blob} that has the provided checksum.
     * @param checksumType The type of checksum algorithm to use.
     * @param checksumValue The value of the blob's checksum.
     * @return The matching {@link Blob}.
     */
    public default Blob getBlob(BlobChecksumType checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNull(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return this.getBlob(checksumType.toString(), checksumValue);
    }

    /**
     * Get the {@link Blob} that has the provided checksum.
     * @param checksumType The type of checksum algorithm to use.
     * @param checksumValue The value of the blob's checksum.
     * @return The matching {@link Blob}.
     */
    public default Blob getBlob(String checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNullAndNotEmpty(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return Blob.create(this, checksumType, checksumValue);
    }

    /**
     * Get an {@link Iterator} that will return all of the blobs in this {@link BlobStorage}.
     */
    public Iterator<Blob> iterateBlobs();

    /**
     * Get whether a {@link Blob} exists for the provided checksum.
     * @param checksumType The type of checksum algorithm to use.
     * @param checksumValue The value of the blob's checksum.
     */
    public default Result<Boolean> blobExists(BlobChecksumType checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNull(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return this.blobExists(checksumType.toString(), checksumValue);
    }

    /**
     * Get whether a {@link Blob} exists for the provided checksum.
     * @param checksumType The type of checksum algorithm to use.
     * @param checksumValue The value of the blob's checksum.
     */
    public Result<Boolean> blobExists(String checksumType, BitArray checksumValue);

    /**
     * Get the byte count of the blob with the provided checksum.
     * @param checksumType The type of checksum algorithm to use.
     * @param checksumValue The value of the blob's checksum.
     * @return The byte count of the blob with the provided checksum.
     */
    public default Result<Long> getBlobByteCount(BlobChecksumType checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNull(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return this.getBlobByteCount(checksumType.toString(), checksumValue);
    }

    /**
     * Get the byte count of the blob with the provided checksum.
     * @param checksumType The type of checksum algorithm to use.
     * @param checksumValue The value of the blob's checksum.
     * @return The byte count of the blob with the provided checksum.
     */
    public Result<Long> getBlobByteCount(String checksumType, BitArray checksumValue);

    /**
     * Get the byte contents of the blob with the provided checksum.
     * @param checksumType The type of checksum algorithm to use.
     * @param checksumValue The value of the blob's checksum.
     * @return The byte contents of the blob with the provided checksum.
     */
    public default Result<ByteReadStream> getBlobContents(BlobChecksumType checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNull(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        return this.getBlobContents(checksumType.toString(), checksumValue);
    }

    /**
     * Get the byte contents of the blob with the provided checksum.
     * @param checksumType The type of checksum algorithm to use.
     * @param checksumValue The value of the blob's checksum.
     * @return The byte contents of the blob with the provided checksum.
     */
    public Result<ByteReadStream> getBlobContents(String checksumType, BitArray checksumValue);

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
