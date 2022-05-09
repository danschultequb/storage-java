package qub;

/**
 * An object that provides access to a Binary Large OBject (BLOB).
 */
public class Blob
{
    private final BlobStorage blobStorage;
    private final BlobChecksum checksum;

    private Blob(BlobStorage blobStorage, BlobChecksum checksum)
    {
        PreCondition.assertNotNull(blobStorage, "blobStorage");
        PreCondition.assertNotNull(checksum, "checksum");

        this.blobStorage = blobStorage;
        this.checksum = checksum;
    }

    public static Blob create(BlobStorage blobStorage, BlobChecksum checksum)
    {
        return new Blob(blobStorage, checksum);
    }

    /**
     * Get the {@link BlobStorage} that this {@link Blob} came from.
     */
    public BlobStorage getBlobStorage()
    {
        return this.blobStorage;
    }

    public BlobChecksum getChecksum()
    {
        return this.checksum;
    }

    /**
     * Get whether this {@link Blob} exists.
     */
    public Result<Boolean> exists()
    {
        return this.blobStorage.blobExists(this.checksum);
    }

    /**
     * Get the number of bytes that are in this {@link Blob}.
     */
    public Result<Long> getByteCount()
    {
        return this.blobStorage.getBlobByteCount(this.checksum);
    }

    /**
     * Get the contents of this {@link Blob}.
     */
    public Result<ByteReadStream> getContents()
    {
        return this.blobStorage.getBlobContents(this.checksum);
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
            this.checksum.equals(rhs.checksum);
    }
}
