package qub;

public interface ItemTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(Item.class, () ->
        {
            runner.testGroup("create(ItemStorage,String)", () ->
            {
                runner.test("with null itemStorage", (Test test) ->
                {
                    test.assertThrows(() -> Item.create(null, "hello"),
                        new PreConditionFailure("itemStorage cannot be null."));
                });

                runner.test("with null name", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    test.assertThrows(() -> Item.create(itemStorage, null),
                        new PreConditionFailure("name cannot be null."));
                });

                runner.test("with empty name", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    test.assertThrows(() -> Item.create(itemStorage, ""),
                        new PreConditionFailure("name cannot be empty."));
                });

                runner.test("with valid arguments", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = Item.create(itemStorage, "hello");
                    test.assertNotNull(item);
                    test.assertSame(itemStorage, item.getItemStorage());
                    test.assertEqual("hello", item.getName());
                    test.assertEqual("hello", item.toString());
                });
            });

            runner.testGroup("exists()", () ->
            {
                runner.test("with non-existing item", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.getItem("hello").await();
                    test.assertFalse(item.exists().await());
                });

                runner.test("with existing item", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.createItem().await();
                    test.assertTrue(item.exists().await());
                });
            });

            runner.testGroup("getContents()", () ->
            {
                runner.test("with non-existing item", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.getItem("hello").await();
                    test.assertThrows(() -> item.getContents().await(),
                        new ItemNotFoundException(item));
                });

                runner.test("with existing item with no contents", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.createItem().await();
                    test.assertNull(item.getContents().await());
                });

                runner.test("with existing item with empty contents", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.createItem(CreateItemParameters.create()
                        .setContents(new byte[0]))
                        .await();
                    try (final ByteReadStream itemContents = item.getContents().await())
                    {
                        test.assertEqual(new byte[0], itemContents.readAllBytes().await());
                    }
                });

                runner.test("with existing item with non-empty contents", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.createItem(CreateItemParameters.create()
                        .setContents(new byte[] { 1, 2, 3, 4 }))
                        .await();
                    try (final ByteReadStream itemContents = item.getContents().await())
                    {
                        test.assertEqual(new byte[] { 1, 2, 3, 4 }, itemContents.readAllBytes().await());
                    }
                });
            });

            runner.testGroup("setContents(byte[])", () ->
            {
                runner.test("with non-existing item", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.getItem("nonexistingitem").await();
                    test.assertThrows(() -> item.setContents(new byte[0]).await(),
                        new ItemNotFoundException(item));
                    test.assertFalse(item.exists().await());
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with existing item with no contents", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.createItem().await();

                    final Void setContentsResult = item.setContents(new byte[] { 1 }).await();
                    test.assertNull(setContentsResult);

                    try (final ByteReadStream itemContents = item.getContents().await())
                    {
                        test.assertEqual(new byte[] { 1 }, itemContents.readAllBytes().await());
                    }
                });
            });

            runner.testGroup("setContents(ByteReadStream)", () ->
            {
                runner.test("with non-existing item", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.getItem("nonexistingitem").await();
                    test.assertThrows(() -> item.setContents(InMemoryByteStream.create(new byte[0]).endOfStream()).await(),
                        new ItemNotFoundException(item));
                    test.assertFalse(item.exists().await());
                    test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
                });

                runner.test("with existing item with no contents", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.createItem().await();

                    final Void setContentsResult = item.setContents(InMemoryByteStream.create(new byte[] { 1 }).endOfStream()).await();
                    test.assertNull(setContentsResult);

                    try (final ByteReadStream itemContents = item.getContents().await())
                    {
                        test.assertEqual(new byte[] { 1 }, itemContents.readAllBytes().await());
                    }
                });
            });

            runner.testGroup("equals(Object)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item lhs = itemStorage.getItem("lhs").await();
                    final Object rhs = null;
                    test.assertFalse(lhs.equals(rhs));
                });

                runner.test("with empty String", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item lhs = itemStorage.getItem("lhs").await();
                    final Object rhs = "";
                    test.assertFalse(lhs.equals(rhs));
                });

                runner.test("with Item with different name from same ItemStorage", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item lhs = itemStorage.getItem("lhs").await();
                    final Object rhs = itemStorage.getItem("rhs").await();
                    test.assertFalse(lhs.equals(rhs));
                });

                runner.test("with Item with same name from same ItemStorage", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item lhs = itemStorage.getItem("lhs").await();
                    final Object rhs = itemStorage.getItem("lhs").await();
                    test.assertTrue(lhs.equals(rhs));
                });

                runner.test("with Item with different name from different ItemStorage", (Test test) ->
                {
                    final InMemoryItemStorage lhsItemStorage = InMemoryItemStorage.create();
                    final InMemoryItemStorage rhsItemStorage = InMemoryItemStorage.create();
                    final Item lhs = lhsItemStorage.getItem("lhs").await();
                    final Object rhs = rhsItemStorage.getItem("rhs").await();
                    test.assertFalse(lhs.equals(rhs));
                });

                runner.test("with Item with same name from different ItemStorage", (Test test) ->
                {
                    final InMemoryItemStorage lhsItemStorage = InMemoryItemStorage.create();
                    final InMemoryItemStorage rhsItemStorage = InMemoryItemStorage.create();
                    final Item lhs = lhsItemStorage.getItem("lhs").await();
                    final Object rhs = rhsItemStorage.getItem("lhs").await();
                    test.assertFalse(lhs.equals(rhs));
                });
            });

            runner.testGroup("equals(Item)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item lhs = itemStorage.getItem("lhs").await();
                    final Item rhs = null;
                    test.assertFalse(lhs.equals(rhs));
                });

                runner.test("with Item with different name from same ItemStorage", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item lhs = itemStorage.getItem("lhs").await();
                    final Item rhs = itemStorage.getItem("rhs").await();
                    test.assertFalse(lhs.equals(rhs));
                });

                runner.test("with Item with same name from same ItemStorage", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item lhs = itemStorage.getItem("lhs").await();
                    final Item rhs = itemStorage.getItem("lhs").await();
                    test.assertTrue(lhs.equals(rhs));
                });

                runner.test("with Item with different name from different ItemStorage", (Test test) ->
                {
                    final InMemoryItemStorage lhsItemStorage = InMemoryItemStorage.create();
                    final InMemoryItemStorage rhsItemStorage = InMemoryItemStorage.create();
                    final Item lhs = lhsItemStorage.getItem("lhs").await();
                    final Item rhs = rhsItemStorage.getItem("rhs").await();
                    test.assertFalse(lhs.equals(rhs));
                });

                runner.test("with Item with same name from different ItemStorage", (Test test) ->
                {
                    final InMemoryItemStorage lhsItemStorage = InMemoryItemStorage.create();
                    final InMemoryItemStorage rhsItemStorage = InMemoryItemStorage.create();
                    final Item lhs = lhsItemStorage.getItem("lhs").await();
                    final Item rhs = rhsItemStorage.getItem("lhs").await();
                    test.assertFalse(lhs.equals(rhs));
                });
            });
        });
    }
}
