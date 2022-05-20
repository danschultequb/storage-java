package qub;

public class InMemoryItem
{
    private String name;
    private byte[] contents;

    private InMemoryItem()
    {
    }

    public static InMemoryItem create()
    {
        return new InMemoryItem();
    }

    public String getName()
    {
        return this.name;
    }

    public InMemoryItem setName(String name)
    {
        PreCondition.assertNotNullAndNotEmpty(name, "name");

        this.name = name;

        return this;
    }

    public byte[] getContents()
    {
        return this.contents;
    }

    public InMemoryItem setContents(byte[] contents)
    {
        this.contents = contents;

        return this;
    }
}
