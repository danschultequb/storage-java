package qub;

/**
 * A {@link StorageContainer} implementation that keeps all of its data in-memory.
 */
public class InMemoryStorageContainer implements StorageContainer
{
    private final Clock clock;
    private final MutableMap<String,InMemoryStorageItem> items;
    
    private InMemoryStorageContainer(Clock clock)
    {
        PreCondition.assertNotNull(clock, "clock");

        this.clock = clock;
        this.items = Map.create();
    }

    /**
     * Create a new {@link InMemoryStorageContainer}.
     */
    public static InMemoryStorageContainer create(Clock clock)
    {
        return new InMemoryStorageContainer(clock);
    }

    @Override
    public Iterator<? extends StorageItem> iterateItems()
    {
        return this.items.getValues().iterate();
    }

    public DateTime getCurrentDateTime()
    {
        return this.clock.getCurrentDateTime();
    }

    @Override
    public Result<? extends StorageItem> getItem(String id)
    {
        PreCondition.assertNotNull(id, "id");

        return this.items.get(id)
            .convertError(NotFoundException.class, () -> new NotFoundException("No item found with the id: " + Strings.escapeAndQuote(id)));
    }

    @Override
    public Result<InMemoryStorageItem> createItem()
    {
        return this.createItem(StorageItemCreateParameters.create());
    }

    @Override
    public Result<InMemoryStorageItem> createItem(StorageItemCreateParameters parameters)
    {
        PreCondition.assertNotNull(parameters, "parameters");

        return Result.create(() ->
        {
            InMemoryStorageItem result = null;

            String resultId = parameters.getId();
            if (resultId != null)
            {
                if (this.items.containsKey(resultId))
                {
                    throw new AlreadyExistsException("An item with the id " + Strings.escapeAndQuote(resultId) + " already exists.");
                }

                result = InMemoryStorageItem.create(this, resultId);
            }
            else
            {
                int id = 1;
                do
                {
                    resultId = Integers.toString(id);
                    if (this.items.containsKey(resultId))
                    {
                        id++;
                    }
                    else
                    {
                        result = InMemoryStorageItem.create(this, resultId);
                    }
                }
                while (result == null);
            }

            DateTime createdAt = parameters.getCreatedAt();
            if (createdAt == null)
            {
                createdAt = this.clock.getCurrentDateTime();
            }
            result.setCreatedAt(createdAt);

            DateTime lastModified = parameters.getLastModified();
            if (lastModified == null)
            {
                lastModified = this.clock.getCurrentDateTime();
            }
            result.setLastModified(lastModified);

            this.items.set(resultId, result);
            
            PostCondition.assertNotNull(result, "result");

            return result;
        });
    }
}
