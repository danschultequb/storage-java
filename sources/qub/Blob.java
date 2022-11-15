package qub;

/**
 * An object that references a Binary Large OBject (BLOB) in a {@link BlobStorage}.
 */
public class Blob
{
    private final BlobStorage blobStorage;
    private final BlobId blobId;

    private Blob(BlobStorage blobStorage, BlobId blobId)
    {
        PreCondition.assertNotNull(blobStorage, "blobStorage");
        BlobStorage.assertNotNullAndNotEmpty(blobId, "blobId");

        this.blobStorage = blobStorage;
        this.blobId = blobId;
    }

    public static Blob create(BlobStorage blobStorage, BlobId blobId)
    {
        return new Blob(blobStorage, blobId);
    }

    /**
     * Get the {@link BlobStorage} that this {@link Blob} came from.
     */
    public BlobStorage getBlobStorage()
    {
        return this.blobStorage;
    }

    /**
     * Get the {@link BlobId} that identifies this {@link Blob}.
     */
    public BlobId getId()
    {
        return this.blobId;
    }

    /**
     * Get whether this {@link Blob} exists.
     */
    public Result<Boolean> exists()
    {
        return this.blobStorage.blobExists(this.blobId);
    }

    /**
     * Get the number of bytes that are in this {@link Blob}.
     */
    public Result<Long> getByteCount()
    {
        return this.blobStorage.getBlobByteCount(this.blobId);
    }

    /**
     * Get the contents of this {@link Blob}.
     */
    public Result<ByteReadStream> getContents()
    {
        return this.blobStorage.getBlobContents(this.blobId);
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof Blob &&
            this.equals((Blob)rhs);
    }

    /**
     * Get whether this {@link Blob} equals the provided {@link Blob}. This means that both
     * {@link Blob}s come from the same {@link BlobStorage} and have the same {@link BlobIdElement}.
     * @param rhs The {@link Blob} to compare to this {@link Blob}.
     */
    public boolean equals(Blob rhs)
    {
        return rhs != null &&
            this.blobStorage == rhs.blobStorage &&
            this.blobId.equals(rhs.blobId);
    }
}
