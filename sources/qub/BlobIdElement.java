package qub;

/**
 * A part of a {@link BlobId} that uniquely identifies a given {@link Blob}.
 */
public class BlobIdElement
{
    private final String elementType;
    private final String elementValue;

    private BlobIdElement(String blobIdElementType, String blobIdElementValue)
    {
        PreCondition.assertNotNullAndNotEmpty(blobIdElementType, "blobIdElementType");
        PreCondition.assertNotNullAndNotEmpty(blobIdElementValue, "blobIdElementValue");

        this.elementType = blobIdElementType;
        this.elementValue = blobIdElementValue;
    }

    /**
     * Create a new {@link BlobIdElement} with the provided element type and value.
     * @param blobIdElementType The type of the new {@link BlobIdElement}.
     * @param blobIdElementValue The value of the new {@link BlobIdElement}.
     */
    public static BlobIdElement create(String blobIdElementType, String blobIdElementValue)
    {
        return new BlobIdElement(blobIdElementType, blobIdElementValue);
    }

    /**
     * Get the type of this {@link BlobIdElement}.
     */
    public String getElementType()
    {
        return this.elementType;
    }

    /**
     * Get the value of this {@link BlobIdElement}.
     */
    public String getElementValue()
    {
        return this.elementValue;
    }

    @Override
    public String toString()
    {
        return Strings.escapeAndQuote(this.elementType) + ":" + Strings.escapeAndQuote(this.elementValue);
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof BlobIdElement && this.equals((BlobIdElement)rhs);
    }

    /**
     * Get whether this {@link BlobIdElement} is equal to the provided {@link BlobIdElement}.
     * @param rhs The {@link BlobIdElement} to compare against this {@link BlobIdElement}.
     */
    public boolean equals(BlobIdElement rhs)
    {
        return rhs != null &&
            this.elementType.equals(rhs.elementType) &&
            this.elementValue.equals(rhs.elementValue);
    }

    @Override
    public int hashCode()
    {
        return Hash.getHashCode(this.getClass(), this.elementType, this.elementValue);
    }
}
