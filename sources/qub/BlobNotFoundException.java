package qub;

/**
 * An {@link NotFoundException} that is thrown when a {@link Blob} isn't found.
 */
public class BlobNotFoundException extends NotFoundException
{
    private final Blob blob;

    public BlobNotFoundException(String message, Blob notFoundBlob)
    {
        super(message);

        this.blob = notFoundBlob;
    }

    public BlobNotFoundException(String message)
    {
        this(message, null);
    }

    public BlobNotFoundException(Blob blob)
    {
        this(BlobNotFoundException.getMessage(blob), blob);
    }

    private static String getMessage(Blob blob)
    {
        PreCondition.assertNotNull(blob, "blob");

        return "Could not find a blob with the id " + blob.getId().toString() + ".";
    }

    /**
     * Get the {@link Blob} that doesn't exist.
     */
    public Blob getBlob()
    {
        return this.blob;
    }
}
