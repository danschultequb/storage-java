package qub;

/**
 * Parameters that can be passed to an {@link ItemStorage} object to create a new {@link Item}.
 */
public class CreateItemParameters
{
    private String name;
    private ByteReadStream contents;

    private CreateItemParameters()
    {
    }

    /**
     * Create a new {@link CreateItemParameters} object.
     */
    public static CreateItemParameters create()
    {
        return new CreateItemParameters();
    }

    /**
     * Get the name of the {@link Item} to create. If no name is provided, then this will return
     * null and the newly created {@link Item} will have a random name assigned that is unique to
     * the {@link ItemStorage} object.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Set the name of the {@link Item} to create.
     * @param name The name of the {@link Item} to create.
     * @return This object for method chaining.
     */
    public CreateItemParameters setName(String name)
    {
        PreCondition.assertNotNullAndNotEmpty(name, "name");

        this.name = name;

        return this;
    }

    /**
     * Get the contents of the {@link Item} to create.
     */
    public ByteReadStream getContents()
    {
        return this.contents;
    }

    /**
     * Set the contents of the {@link Item} to create.
     * @param contents The contents of the {@link Item} to create.
     * @return This object for method chaining.
     */
    public CreateItemParameters setContents(byte[] contents)
    {
        PreCondition.assertNotNull(contents, "contents");

        return this.setContents(InMemoryByteStream.create(contents).endOfStream());
    }

    /**
     * Set the contents of the {@link Item} to create.
     * @param contents The contents of the {@link Item} to create.
     * @return This object for method chaining.
     */
    public CreateItemParameters setContents(ByteReadStream contents)
    {
        PreCondition.assertNotNull(contents, "contents");

        this.contents = contents;

        return this;
    }
}
