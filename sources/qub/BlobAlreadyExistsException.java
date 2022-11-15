package qub;

public class BlobAlreadyExistsException extends AlreadyExistsException
{
    private final Blob alreadyExistingBlob;

    public BlobAlreadyExistsException(Blob alreadyExistingBlob)
    {
        super("A blob already exists with the provided contents.");

        this.alreadyExistingBlob = alreadyExistingBlob;
    }

    public BlobAlreadyExistsException()
    {
        this(null);
    }

    /**
     * Get the {@link Blob} that already exists.
     */
    public Blob getAlreadyExistingBlob()
    {
        return this.alreadyExistingBlob;
    }
}
