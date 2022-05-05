package qub;

/**
 * A {@link StorageAttribute} that came from a {@link StorageAttributeArray}.
 */
public interface StorageArrayAttribute extends StorageAttribute
{
    /**
     * Get the index in the {@link StorageAttributeArray} that this {@link StorageArrayAttribute}
     * came from.
     */
    public int getIndex();
}
