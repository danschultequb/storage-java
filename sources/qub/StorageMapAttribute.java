package qub;

/**
 * A {@link StorageAttribute} that came from a {@link StorageAttributeMap}.
 */
public interface StorageMapAttribute extends StorageAttribute
{
    public static InMemoryStorageMapAttribute create(String name, StorageAttributeValue value)
    {
        return InMemoryStorageMapAttribute.create(name, value);
    }

    /**
     * Get the name of this {@link StorageMapAttribute}.
     */
    public String getName();

    /**
     * Get whether this {@link StorageMapAttribute} is equal to the provided
     * {@link StorageMapAttribute}.
     * @param rhs The {@link StorageMapAttribute} to compare against this
     * {@link StorageMapAttribute}.
     */
    public default boolean equals(StorageMapAttribute rhs)
    {
        return rhs != null &&
            this.getName().equals(rhs.getName()) &&
            this.getValue().equals(rhs.getValue());
    }

    public static boolean equals(StorageMapAttribute lhs, Object rhs)
    {
        PreCondition.assertNotNull(lhs, "lhs");

        return rhs instanceof StorageMapAttribute &&
            lhs.equals((StorageMapAttribute)rhs);
    }

    public static String toString(StorageMapAttribute attribute)
    {
        PreCondition.assertNotNull(attribute, "attribute");

        return attribute.getName() + ":" + attribute.getValue();
    }
}
