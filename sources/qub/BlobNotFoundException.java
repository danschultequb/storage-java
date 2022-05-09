package qub;

public class BlobNotFoundException extends NotFoundException
{
    private final Blob notFoundBlob;

    public BlobNotFoundException(String message, Blob notFoundBlob)
    {
        super(message);

        this.notFoundBlob = notFoundBlob;
    }

    public BlobNotFoundException(String message)
    {
        this(message, null);
    }

    public BlobNotFoundException(Blob notFoundBlob)
    {
        this(BlobNotFoundException.getMessage(notFoundBlob), notFoundBlob);
    }

    private static String getMessage(Blob notFoundBlob)
    {
        PreCondition.assertNotNull(notFoundBlob, "notFoundBlob");

        return "Could not find a blob with checksum " + notFoundBlob.getChecksum().toString() + ".";
    }

    /**
     * Get the {@link Blob} that doesn't exist.
     */
    public Blob getNotFoundBlob()
    {
        return this.notFoundBlob;
    }
}
