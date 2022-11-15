package qub;

/**
 * A collection of {@link BlobIdElement}s that identify a {@link Blob}.
 */

public interface BlobId
{
    /**
     * Create a new and empty {@link MutableBlobId}.
     */
    public static MutableBlobId create()
    {
        return MutableBlobId.create();
    }

    /**
     * Get the number of {@link BlobIdElement}s in this {@link BlobId}.
     */
    public int getElementCount();

    /**
     * Get whether this {@link BlobId} contains a {@link BlobIdElement} with the provided element
     * type.
     * @param blobIdElementType The element type to look for.
     */
    public boolean containsElementType(String blobIdElementType);

    /**
     * Iterate over the element types in this {@link BlobId}.
     */
    public Iterator<String> iterateElementTypes();

    /**
     * Get whether this {@link BlobId} contains the provided {@link BlobIdElement}.
     * @param blobIdElement The {@link BlobIdElement} to look for.
     */
    public boolean containsElement(BlobIdElement blobIdElement);

    /**
     * Iterate over the {@link BlobIdElement}s in this {@link BlobId}.
     */
    public Iterator<BlobIdElement> iterateElements();

    /**
     * Get the {@link BlobIdElement} in this {@link BlobId} that has the provided element type.
     * @param blobIdElementType The element type to look for.
     */
    public Result<BlobIdElement> getElement(String blobIdElementType);

    /**
     * Get the {@link BlobIdElement} value in this {@link BlobId} that is associated with the
     * provided element type.
     * @param blobIdElementType The element type to look for.
     */
    public Result<String> getElementValue(String blobIdElementType);

    /**
     * Get the {@link String} representation of this {@link BlobId}.
     * @param blobId The {@link BlobId} to get the {@link String} representation of.
     */
    public static String toString(BlobId blobId)
    {
        PreCondition.assertNotNull(blobId, "blobId");

        final CharacterList list = CharacterList.create();
        list.add('{');

        boolean firstChecksum = true;
        for (final BlobIdElement blobIdElement : blobId.iterateElements())
        {
            if (!firstChecksum)
            {
                list.add(',');
            }
            else
            {
                firstChecksum = false;
            }

            list.addAll(blobIdElement.toString());
        }

        list.add('}');
        final String result = list.toString();

        PostCondition.assertNotNullAndNotEmpty(result, "result");

        return result;
    }

    /**
     * Get whether the provided {@link BlobId} is equal to the provided {@link Object}.
     * @param blobId The {@link BlobId} to compare against the provided {@link Object}.
     * @param rhs The {@link Object} to compare against the provided {@link BlobId}.
     */
    public static boolean equals(BlobId blobId, Object rhs)
    {
        PreCondition.assertNotNull(blobId, "blobId");

        return rhs instanceof BlobId && blobId.equals((BlobId)rhs);
    }

    /**
     * Get whether this {@link BlobId} is equal to the provided {@link BlobId}.
     * @param rhs The {@link BlobId} to compare against this {@link BlobId}.
     */
    public default boolean equals(BlobId rhs)
    {
        boolean result = (rhs != null && this.getElementCount() == rhs.getElementCount());

        if (result && this != rhs)
        {
            for (final String blobChecksumType : this.iterateElementTypes())
            {
                final String lhsBlobElementValue = this.getElementValue(blobChecksumType).await();
                final String rhsBlobElementValue = rhs.getElementValue(blobChecksumType)
                    .catchError(NotFoundException.class)
                    .await();
                if (!lhsBlobElementValue.equals(rhsBlobElementValue))
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Get the hash code for the provided {@link BlobId}.
     * @param blobId The {@link BlobId} to get the hash code of.
     */
    public static int hashCode(BlobId blobId)
    {
        PreCondition.assertNotNull(blobId, "blobId");

        int result = Hash.getHashCode(blobId.getClass());
        for (final BlobIdElement blobIdElement : blobId.iterateElements())
        {
            result ^= blobIdElement.hashCode();
        }
        return result;
    }
}
