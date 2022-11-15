package qub;

public interface ItemStorageTests
{
    public static void test(TestRunner runner, Function0<? extends ItemStorage> creator)
    {
        runner.testGroup(ItemStorage.class, () ->
        {
            runner.testGroup("getItem(String)", () ->
            {
                final Action2<String,Throwable> getItemErrorTest = (String itemName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(itemName), (Test test) ->
                    {
                        final ItemStorage itemStorage = creator.run();
                        test.assertThrows(() -> itemStorage.getItem(itemName),
                            expected);
                        test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                    });
                };

                getItemErrorTest.run(null, new PreConditionFailure("itemName cannot be null."));
                getItemErrorTest.run("", new PreConditionFailure("itemName cannot be empty."));

                runner.test("with item that doesn't exist", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    final Item item = itemStorage.getItem("doesn't exist").await();
                    test.assertNotNull(item);
                    test.assertSame(itemStorage, item.getItemStorage());
                    test.assertEqual("doesn't exist", item.getName());
                    test.assertFalse(item.exists().await());
                });
            });

            runner.testGroup("iterateItems()", () ->
            {
                runner.test("with no items", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    final Iterator<Item> items = itemStorage.iterateItems();
                    IteratorTests.assertIterator(test, items, false, null);
                    test.assertEqual(Iterable.create(), items.toList());
                });
            });

            runner.testGroup("itemExists(String)", () ->
            {
                final Action2<String,Throwable> itemExistsErrorTest = (String itemName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(itemName), (Test test) ->
                    {
                        final ItemStorage itemStorage = creator.run();
                        test.assertThrows(() -> itemStorage.itemExists(itemName).await(),
                            expected);
                        test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                    });
                };

                itemExistsErrorTest.run(null, new PreConditionFailure("itemName cannot be null."));
                itemExistsErrorTest.run("", new PreConditionFailure("itemName cannot be empty."));

                runner.test("with item that doesn't exist", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    test.assertFalse(itemStorage.itemExists("doesn't exist").await());
                });
            });

            runner.testGroup("getItemContents(String)", () ->
            {
                final Action2<String,Throwable> getItemContentsErrorTest = (String itemName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(itemName), (Test test) ->
                    {
                        final ItemStorage itemStorage = creator.run();
                        test.assertThrows(() -> itemStorage.getItemContents(itemName).await(),
                            expected);
                        test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                    });
                };

                getItemContentsErrorTest.run(null, new PreConditionFailure("itemName cannot be null."));
                getItemContentsErrorTest.run("", new PreConditionFailure("itemName cannot be empty."));
                getItemContentsErrorTest.run("item that doesn't exist", new ItemNotFoundException("No Item named \"item that doesn't exist\" found in this ItemStorage."));
            });

            runner.testGroup("setItemContents(String,byte[])", () ->
            {
                final Action3<String,byte[],Throwable> setItemContentsErrorTest = (String itemName, byte[] itemContents, Throwable expected) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(itemName), Objects.toString(itemContents)), (Test test) ->
                    {
                        final ItemStorage itemStorage = creator.run();
                        test.assertThrows(() -> itemStorage.setItemContents(itemName, itemContents).await(),
                            expected);
                        test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                    });
                };

                setItemContentsErrorTest.run(null, new byte[0], new PreConditionFailure("itemName cannot be null."));
                setItemContentsErrorTest.run("", new byte[0], new PreConditionFailure("itemName cannot be empty."));
                setItemContentsErrorTest.run("item that doesn't exist", null, new PreConditionFailure("itemContents cannot be null."));
                setItemContentsErrorTest.run("item that doesn't exist", new byte[0], new ItemNotFoundException("No Item named \"item that doesn't exist\" found in this ItemStorage."));

                runner.test("with item that exists", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    final Item item = itemStorage.createItem().await();

                    final Void setContentsResult = item.setContents(new byte[] { 1, 2, 3 }).await();
                    test.assertNull(setContentsResult);

                    try (final ByteReadStream itemContents = itemStorage.getItemContents(item.getName()).await())
                    {
                        test.assertEqual(new byte[] { 1, 2, 3 }, itemContents.readAllBytes().await());
                    }

                    try (final ByteReadStream itemContents = item.getContents().await())
                    {
                        test.assertEqual(new byte[] { 1, 2, 3 }, itemContents.readAllBytes().await());
                    }
                });
            });

            runner.testGroup("setItemContents(String,ByteReadStream)", () ->
            {
                final Action3<String,byte[],Throwable> setItemContentsErrorTest = (String itemName, byte[] itemContents, Throwable expected) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(itemName), Objects.toString(itemContents)), (Test test) ->
                    {
                        final ItemStorage itemStorage = creator.run();
                        test.assertThrows(() -> itemStorage.setItemContents(itemName, itemContents == null ? null : InMemoryByteStream.create(itemContents).endOfStream()).await(),
                            expected);
                        test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                    });
                };

                setItemContentsErrorTest.run(null, new byte[0], new PreConditionFailure("itemName cannot be null."));
                setItemContentsErrorTest.run("", new byte[0], new PreConditionFailure("itemName cannot be empty."));
                setItemContentsErrorTest.run("item that doesn't exist", null, new PreConditionFailure("itemContents cannot be null."));
                setItemContentsErrorTest.run("item that doesn't exist", new byte[0], new ItemNotFoundException("No Item named \"item that doesn't exist\" found in this ItemStorage."));
            });

            runner.test("createItem()", (Test test) ->
            {
                final ItemStorage itemStorage = creator.run();

                final Item item1 = itemStorage.createItem().await();
                test.assertNotNull(item1);
                test.assertNotNullAndNotEmpty(item1.getName());
                test.assertTrue(item1.exists().await());
                test.assertNull(item1.getContents().await());
                test.assertEqual(Iterable.create(item1), itemStorage.iterateItems().toList());

                final Item item2 = itemStorage.createItem().await();
                test.assertNotNull(item2);
                test.assertNotNullAndNotEmpty(item2.getName());
                test.assertNotEqual(item1.getName(), item2.getName());
                test.assertTrue(item2.exists().await());
                test.assertNull(item2.getContents().await());
                test.assertEqual(Iterable.create(item1, item2), itemStorage.iterateItems().toList());
            });

            runner.testGroup("createItem(CreateItemParameters)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();

                    test.assertThrows(() -> itemStorage.createItem(null),
                        new PreConditionFailure("parameters cannot be null."));

                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with empty parameters", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();

                    final Item item = itemStorage.createItem(CreateItemParameters.create()).await();
                    test.assertNotNull(item);
                    test.assertNotNullAndNotEmpty(item.getName());
                    test.assertTrue(item.exists().await());
                    test.assertNull(item.getContents().await());
                    test.assertEqual(Iterable.create(item), itemStorage.iterateItems().toList());
                });

                runner.test("with non-existing item name", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();

                    final Item item = itemStorage.createItem(CreateItemParameters.create()
                        .setName("configuration"))
                        .await();
                    test.assertNotNull(item);
                    test.assertEqual("configuration", item.getName());
                    test.assertTrue(item.exists().await());
                    test.assertNull(item.getContents().await());
                    test.assertEqual(Iterable.create(item), itemStorage.iterateItems().toList());
                });

                runner.test("with existing item name", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    final Item item = itemStorage.createItem().await();

                    test.assertThrows(() -> itemStorage.createItem(CreateItemParameters.create().setName(item.getName())).await(),
                        new ItemAlreadyExistsException(item));

                    test.assertEqual(Iterable.create(item), itemStorage.iterateItems().toList());
                });

                runner.test("with empty content", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();

                    final Item item = itemStorage.createItem(CreateItemParameters.create()
                        .setContents(new byte[0]))
                        .await();
                    test.assertNotNull(item);
                    test.assertNotNullAndNotEmpty(item.getName());
                    test.assertTrue(item.exists().await());
                    test.assertEqual(new byte[0], item.getContents().await().readAllBytes().await());
                    test.assertEqual(Iterable.create(item), itemStorage.iterateItems().toList());
                });

                runner.test("with non-empty content", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();

                    final Item item = itemStorage.createItem(CreateItemParameters.create()
                        .setContents(new byte[] { 1, 2, 3, 4, 5 }))
                        .await();
                    test.assertNotNull(item);
                    test.assertNotNullAndNotEmpty(item.getName());
                    test.assertTrue(item.exists().await());
                    test.assertEqual(new byte[] { 1, 2, 3, 4, 5 }, item.getContents().await().readAllBytes().await());
                    test.assertEqual(Iterable.create(item), itemStorage.iterateItems().toList());
                });

                runner.test("with contents stream that throws", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();

                    final CreateItemParameters parameters = CreateItemParameters.create()
                        .setContents(FakeByteReadStream.create(() -> { throw new NotFoundException("oops!"); }));
                    test.assertThrows(() -> itemStorage.createItem(parameters).await(),
                        new NotFoundException("oops!"));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with contents stream that throws", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();

                    final CreateItemParameters parameters = CreateItemParameters.create()
                        .setContents(FakeByteReadStream.create(() -> { throw new NotFoundException("oops!"); }));
                    test.assertThrows(() -> itemStorage.createItem(parameters).await(),
                        new NotFoundException("oops!"));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with contents stream that returns a null byte[]", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();

                    final CreateItemParameters parameters = CreateItemParameters.create()
                        .setContents(FakeByteReadStream.create(() -> { throw new NotFoundException("oops!"); }));
                    test.assertThrows(() -> itemStorage.createItem(parameters).await(),
                        new NotFoundException("oops!"));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });
            });

            runner.testGroup("deleteItem(String)", () ->
            {
                runner.test("with null itemName", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    test.assertThrows(() -> itemStorage.deleteItem(null).await(),
                        new PreConditionFailure("itemName cannot be null."));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with empty itemName", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    test.assertThrows(() -> itemStorage.deleteItem("").await(),
                        new PreConditionFailure("itemName cannot be empty."));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with non-existing itemName", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    final Item item = itemStorage.getItem("non-existing item").await();
                    test.assertThrows(() -> itemStorage.deleteItem(item.getName()).await(),
                        new ItemNotFoundException(item));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with existing itemName", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    final Item item = itemStorage.createItem().await();
                    final Void deleteItemResult = itemStorage.deleteItem(item.getName()).await();
                    test.assertNull(deleteItemResult);
                    test.assertFalse(item.exists().await());
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });
            });

            runner.testGroup("getOrCreateItem(String)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    test.assertThrows(() -> itemStorage.getOrCreateItem((String)null),
                        new PreConditionFailure("itemName cannot be null."));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with empty", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    test.assertThrows(() -> itemStorage.getOrCreateItem(""),
                        new PreConditionFailure("itemName cannot be empty."));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with non-existing item", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    final Item item = itemStorage.getOrCreateItem("hello").await();
                    test.assertNotNull(item);
                    test.assertEqual("hello", item.getName());
                    test.assertNull(item.getContents().await());
                    test.assertEqual(Iterable.create(item), itemStorage.iterateItems().toList());
                });

                runner.test("with existing item", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    itemStorage.createItem(CreateItemParameters.create()
                        .setName("hello")
                        .setContents(new byte[] { 1, 2, 3 }))
                        .await();
                    final Item item = itemStorage.getOrCreateItem("hello").await();
                    test.assertNotNull(item);
                    test.assertEqual("hello", item.getName());
                    try (final ByteReadStream contents = item.getContents().await())
                    {
                        test.assertEqual(new byte[] { 1, 2, 3 }, contents.readAllBytes().await());
                    }
                    test.assertEqual(Iterable.create(item), itemStorage.iterateItems().toList());
                });
            });

            runner.testGroup("getOrCreateItem(CreateItemParameters)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    test.assertThrows(() -> itemStorage.getOrCreateItem((CreateItemParameters)null),
                        new PreConditionFailure("parameters cannot be null."));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with no name in parameters", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    test.assertThrows(() -> itemStorage.getOrCreateItem(CreateItemParameters.create()),
                        new PreConditionFailure("parameters.getName() cannot be null."));
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with non-existing item", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    final Item item = itemStorage.getOrCreateItem(CreateItemParameters.create().setName("hello")).await();
                    test.assertNotNull(item);
                    test.assertEqual("hello", item.getName());
                    test.assertNull(item.getContents().await());
                    test.assertEqual(Iterable.create(item), itemStorage.iterateItems().toList());
                });

                runner.test("with existing item", (Test test) ->
                {
                    final ItemStorage itemStorage = creator.run();
                    itemStorage.createItem(CreateItemParameters.create()
                        .setName("hello")
                        .setContents(new byte[] { 1, 2, 3 }))
                        .await();
                    final Item item = itemStorage.getOrCreateItem(CreateItemParameters.create()
                        .setName("hello")
                        .setContents(new byte[] { 4, 5 }))
                        .await();
                    test.assertNotNull(item);
                    test.assertEqual("hello", item.getName());
                    try (final ByteReadStream contents = item.getContents().await())
                    {
                        test.assertEqual(new byte[] { 1, 2, 3 }, contents.readAllBytes().await());
                    }
                    test.assertEqual(Iterable.create(item), itemStorage.iterateItems().toList());
                });
            });
        });
    }
}
