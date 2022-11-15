package qub;

/**
 * The {@link BlobIdElement}s that identify a {@link Blob}.
 */
public class MutableBlobId implements BlobId
{
    private final MutableMap<String, BlobIdElement> elements;

    private MutableBlobId()
    {
        this.elements = MutableMap.create();
    }

    /**
     * Create a new and empty {@link MutableBlobId}.
     */
    public static MutableBlobId create()
    {
        return new MutableBlobId();
    }

    @Override
    public int getElementCount()
    {
        return this.elements.getCount();
    }

    @Override
    public boolean containsElementType(String blobIdElementType)
    {
        PreCondition.assertNotNullAndNotEmpty(blobIdElementType, "blobIdElementType");

        return this.elements.containsKey(blobIdElementType);
    }

    @Override
    public Iterator<String> iterateElementTypes()
    {
        return this.elements.iterateKeys();
    }

    @Override
    public boolean containsElement(BlobIdElement blobIdElement)
    {
        PreCondition.assertNotNull(blobIdElement, "blobIdElement");

        return this.getElementValue(blobIdElement.getElementType())
            .then((String existingBlobIdElementValue) ->
            {
                return blobIdElement.getElementValue().equals(existingBlobIdElementValue);
            })
            .catchError(NotFoundException.class, () -> false)
            .await();
    }

    @Override
    public Iterator<BlobIdElement> iterateElements()
    {
        return this.elements.iterateValues();
    }

    @Override
    public Result<BlobIdElement> getElement(String blobIdElementType)
    {
        PreCondition.assertNotNullAndNotEmpty(blobIdElementType, "blobIdElementType");

        return this.elements.get(blobIdElementType)
            .convertError(NotFoundException.class, () -> new NotFoundException("Could not find the blob id element type " + Strings.escapeAndQuote(blobIdElementType) + " in this " + Types.getTypeName(BlobId.class) + "."));
    }

    @Override
    public Result<String> getElementValue(String blobIdElementType)
    {
        PreCondition.assertNotNullAndNotEmpty(blobIdElementType, "blobChecksumType");

        return Result.create(() ->
        {
            final BlobIdElement blobChecksum = this.getElement(blobIdElementType).await();
            return blobChecksum.getElementValue();
        });
    }

    /**
     * Add the provided blob ID element type and value to this {@link MutableBlobId}.
     * @param blobIdElementType The type of the {@link BlobIdElement} to add.
     * @param blobIdElementValue The value of the {@link BlobIdElement} to add.
     * @return This object for method chaining.
     */
    public MutableBlobId addElement(String blobIdElementType, String blobIdElementValue)
    {
        return this.addElement(BlobIdElement.create(blobIdElementType, blobIdElementValue));
    }

    /**
     * Add the provided {@link BlobIdElement} to this {@link MutableBlobId}.
     * @param blobIdElement The {@link BlobIdElement} to add to this {@link MutableBlobId}.
     * @return This object for method chaining.
     */
    public MutableBlobId addElement(BlobIdElement blobIdElement)
    {
        PreCondition.assertNotNull(blobIdElement, "blobIdElement");

        final String elementType = blobIdElement.getElementType();
        if (this.containsElementType(elementType))
        {
            throw new AlreadyExistsException("A " + Types.getTypeName(BlobIdElement.class) + " already exists in this " + Types.getTypeName(MutableBlobId.class) + " with the element type " + Strings.escapeAndQuote(elementType) + ".");
        }
        this.elements.set(blobIdElement.getElementType(), blobIdElement);

        return this;
    }

    @Override
    public boolean equals(Object rhs)
    {
        return BlobId.equals(this, rhs);
    }

    @Override
    public String toString()
    {
        return BlobId.toString(this);
    }

    @Override
    public int hashCode()
    {
        return BlobId.hashCode(this);
    }
}
