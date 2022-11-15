package qub;

/**
 * An object that can be used to create/generate a {@link BlobIdElement} based on the {@link byte}s
 * that are added to it.
 */
public interface BlobIdElementCreator extends Disposable
{
    /**
     * Get the type of the {@link BlobIdElement}s that will be created by this
     * {@link BlobIdElementCreator}.
     */
    public String getBlobIdElementType();

    /**
     * Add the provided {@link byte} to this {@link BlobIdElementCreator}.
     * @param value The {@link byte} to add.
     */
    public void addByte(byte value);

    /**
     * Add the provided values to this {@link BlobIdElementCreator}.
     * @param values The values to add to this {@link BlobIdElementCreator}.
     */
    public default void addBytes(byte[] values)
    {
        PreCondition.assertNotNull(values, "values");
        PreCondition.assertNotDisposed(this, "this");

        this.addBytes(values, 0, values.length);
    }

    /**
     * Add the provided values to this {@link BlobIdElementCreator}.
     * @param values The values to add to this {@link BlobIdElementCreator}.
     * @param startIndex The index to start adding values from.
     * @param length The number of values to add.
     */
    public void addBytes(byte[] values, int startIndex, int length);

    /**
     * Take the {@link BlobIdElement} that has been created/generated. This will reset this
     * {@link BlobIdElementCreator} back to its initial state.
     */
    public BlobIdElement takeBlobIdElement();
}
