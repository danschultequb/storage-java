//package qub;
//
//public class InMemoryItemStorage implements ItemStorage
//{
//    private final List<InMemoryItem> items;
//
//    private InMemoryItemStorage()
//    {
//        this.items = List.create();
//    }
//
//    public static InMemoryItemStorage create()
//    {
//        return new InMemoryItemStorage();
//    }
//
//    private Result<InMemoryItem> getInMemoryItem(String itemName)
//    {
//        PreCondition.assertNotNullAndNotEmpty(itemName, "itemName");
//
//        return Result.create(() ->
//        {
//            final InMemoryItem result = this.items.first((InMemoryItem item) -> Strings.equal(itemName, item.getName()));
//            if (result == null)
//            {
//                throw new ItemNotFoundException(this.getItem(itemName).await());
//            }
//            return result;
//        });
//    }
//
//    @Override
//    public Iterator<Item> iterateItems()
//    {
//        return this.items.iterate()
//            .map((InMemoryItem item) ->
//            {
//                return this.getItem(item.getName()).await();
//            });
//    }
//
//    @Override
//    public Result<Boolean> itemExists(String itemName)
//    {
//        PreCondition.assertNotNullAndNotEmpty(itemName, "itemName");
//
//        return this.getInMemoryItem(itemName)
//            .then(() -> { return true; })
//            .catchError(() -> { return false; });
//    }
//
//    @Override
//    public Result<ByteReadStream> getItemContents(String itemName)
//    {
//        PreCondition.assertNotNullAndNotEmpty(itemName, "itemName");
//
//        return this.getInMemoryItem(itemName)
//            .then((InMemoryItem item) ->
//            {
//                final byte[] contents = item.getContents();
//                return contents == null ? null : InMemoryByteStream.create(contents).endOfStream();
//            });
//    }
//
//    @Override
//    public Result<Void> setItemContents(String itemName, ByteReadStream itemContents)
//    {
//        PreCondition.assertNotNullAndNotEmpty(itemName, "itemName");
//        PreCondition.assertNotNull(itemContents, "itemContents");
//
//        return this.getInMemoryItem(itemName)
//            .then((InMemoryItem item) ->
//            {
//                item.setContents(itemContents.readAllBytes().await());
//            });
//    }
//
//    @Override
//    public Result<Item> createItem(CreateItemParameters parameters)
//    {
//        PreCondition.assertNotNull(parameters, "parameters");
//
//        return Result.create(() ->
//        {
//            final InMemoryItem newInMemoryItem = InMemoryItem.create();
//
//            final String providedName = parameters.getName();
//            if (providedName != null)
//            {
//                final InMemoryItem existingInMemoryItem = this.getInMemoryItem(providedName).catchError(NotFoundException.class).await();
//                if (existingInMemoryItem != null)
//                {
//                    final Item alreadyExistingItem = this.getItem(providedName).await();
//                    throw new ItemAlreadyExistsException(alreadyExistingItem);
//                }
//                newInMemoryItem.setName(providedName);
//            }
//            else
//            {
//                int itemNumber = 1;
//                while (newInMemoryItem.getName() == null)
//                {
//                    final String possibleItemName = Integers.toString(itemNumber);
//                    final InMemoryItem existingInMemoryItem = this.getInMemoryItem(possibleItemName).catchError(NotFoundException.class).await();
//                    if (existingInMemoryItem != null)
//                    {
//                        itemNumber++;
//                    }
//                    else
//                    {
//                        newInMemoryItem.setName(possibleItemName);
//                    }
//                }
//            }
//
//            final ByteReadStream providedContents = parameters.getContents();
//            if (providedContents != null)
//            {
//                newInMemoryItem.setContents(providedContents.readAllBytes().await());
//            }
//
//            this.items.add(newInMemoryItem);
//
//            return this.getItem(newInMemoryItem.getName()).await();
//        });
//    }
//
//    @Override
//    public Result<Void> deleteItem(String itemName)
//    {
//        PreCondition.assertNotNullAndNotEmpty(itemName, "itemName");
//
//        return Result.create(() ->
//        {
//            final InMemoryItem item = this.items.removeFirst((InMemoryItem existingItem) ->
//            {
//                return existingItem.getName().equals(itemName);
//            });
//            if (item == null)
//            {
//                throw new ItemNotFoundException(this.getItem(itemName).await());
//            }
//        });
//    }
//
//    @Override
//    public Result<Item> getOrCreateItem(CreateItemParameters parameters)
//    {
//        PreCondition.assertNotNull(parameters, "parameters");
//        PreCondition.assertNotNullAndNotEmpty(parameters.getName(), "parameters.getName()");
//
//        return this.createItem(parameters)
//            .catchError(ItemAlreadyExistsException.class, ItemAlreadyExistsException::getAlreadyExistingItem);
//    }
//}
