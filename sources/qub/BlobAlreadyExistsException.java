package qub;

public class BlobAlreadyExistsException extends AlreadyExistsException
{
    private final Blob alreadyExistingBlob;

    public BlobAlreadyExistsException(Blob alreadyExistingBlob)
    {
        super("A blob already exists for the provided contents.");

        PreCondition.assertNotNull(alreadyExistingBlob, "alreadyExistingBlob");

        this.alreadyExistingBlob = alreadyExistingBlob;
    }

    /**
     * Get the {@link Blob} that already exists.
     */
    public Blob getAlreadyExistingBlob()
    {
        return this.alreadyExistingBlob;
    }
}
