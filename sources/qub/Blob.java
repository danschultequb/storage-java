package qub;

/**
 * An object that provides access to a Binary Large OBject (BLOB).
 */
public class Blob
{
    private final BlobStorage blobStorage;
    private final String checksumType;
    private final BitArray checksumValue;

    private Blob(BlobStorage blobStorage, String checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNull(blobStorage, "blobStorage");
        PreCondition.assertNotNullAndNotEmpty(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        this.blobStorage = blobStorage;
        this.checksumType = checksumType;
        this.checksumValue = checksumValue;
    }

    public static Blob create(BlobStorage blobStorage, String checksumType, BitArray checksumValue)
    {
        return new Blob(blobStorage, checksumType, checksumValue);
    }

    /**
     * Get the {@link BlobStorage} that this {@link Blob} came from.
     */
    public BlobStorage getBlobStorage()
    {
        return this.blobStorage;
    }

    public String getChecksumType()
    {
        return this.checksumType;
    }

    public BitArray getChecksumValue()
    {
        return this.checksumValue;
    }

    /**
     * Get whether this {@link Blob} exists.
     */
    public Result<Boolean> exists()
    {
        return this.blobStorage.blobExists(this.checksumType, this.checksumValue);
    }

    /**
     * Get the number of bytes that are in this {@link Blob}.
     */
    public Result<Long> getByteCount()
    {
        return this.blobStorage.getBlobByteCount(this.checksumType, this.checksumValue);
    }

    /**
     * Get the contents of this {@link Blob}.
     */
    public Result<ByteReadStream> getContents()
    {
        return this.blobStorage.getBlobContents(this.checksumType, this.checksumValue);
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof Blob &&
            this.equals((Blob)rhs);
    }

    public boolean equals(Blob rhs)
    {
        return rhs != null &&
            this.blobStorage == rhs.blobStorage &&
            this.checksumType.equalsIgnoreCase(rhs.checksumType) &&
            this.checksumValue.equals(rhs.checksumValue);
    }
}
