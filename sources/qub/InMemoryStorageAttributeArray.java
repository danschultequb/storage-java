package qub;

public class InMemoryStorageAttributeArray implements MutableStorageAttributeArray
{
    private final List<StorageAttributeValue> values;

    private InMemoryStorageAttributeArray(List<StorageAttributeValue> values)
    {
        PreCondition.assertNotNull(values, "values");

        this.values = values;
    }

    public static InMemoryStorageAttributeArray create()
    {
        return InMemoryStorageAttributeArray.create(List.create());
    }

    public static InMemoryStorageAttributeArray create(Iterable<StorageAttributeValue> values)
    {
        return new InMemoryStorageAttributeArray(values.toList());
    }

    @Override
    public int getCount()
    {
        return this.values.getCount();
    }

    @Override
    public StorageAttributeValue get(int index)
    {
        PreCondition.assertIndexAccess(index, this.values.getCount());

        return this.values.get(index);
    }

    @Override
    public InMemoryStorageAttributeArray set(int index, StorageAttributeValue value)
    {
        PreCondition.assertIndexAccess(index, this.getCount());
        PreCondition.assertNotNull(value, "value");

        this.values.set(index, value);

        return this;
    }

    @Override
    public MutableStorageAttributeArray insert(int index, StorageAttributeValue value)
    {
        PreCondition.assertBetween(0, index, this.getCount(), "index");
        PreCondition.assertNotNull(value, "value");

        this.values.insert(index, value);

        return this;
    }

    @Override
    public Iterator<StorageAttributeValue> iterate()
    {
        return this.values.iterate().map(x -> x);
    }

    @Override
    public String toString()
    {
        return this.values.toString();
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof InMemoryStorageAttributeArray &&
            Iterable.equals(this, rhs);
    }
}