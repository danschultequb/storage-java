package qub;

public interface ItemNotFoundExceptionTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(ItemNotFoundException.class, () ->
        {
            runner.testGroup("constructor(String,Item)", () ->
            {
                runner.test("with null item", (Test test) ->
                {
                    final String message = "hello there";
                    final Item item = null;
                    final ItemNotFoundException exception = new ItemNotFoundException(message, item);
                    test.assertEqual(message, exception.getMessage());
                    test.assertNull(exception.getNotFoundItem());
                });

                runner.test("with null message", (Test test) ->
                {
                    final String message = null;
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.getItem("Non-existing Item").await();
                    final ItemNotFoundException exception = new ItemNotFoundException(message, item);
                    test.assertEqual(message, exception.getMessage());
                    test.assertSame(item, exception.getNotFoundItem());
                });

                runner.test("with empty message", (Test test) ->
                {
                    final String message = "";
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.getItem("Non-existing Item").await();
                    final ItemNotFoundException exception = new ItemNotFoundException(message, item);
                    test.assertEqual(message, exception.getMessage());
                    test.assertSame(item, exception.getNotFoundItem());
                });

                runner.test("with non-empty message", (Test test) ->
                {
                    final String message = "hello there";
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.getItem("Non-existing Item").await();
                    final ItemNotFoundException exception = new ItemNotFoundException(message, item);
                    test.assertEqual(message, exception.getMessage());
                    test.assertSame(item, exception.getNotFoundItem());
                });
            });

            runner.testGroup("constructor(String)", () ->
            {
                runner.test("with null message", (Test test) ->
                {
                    final String message = null;
                    final ItemNotFoundException exception = new ItemNotFoundException(message);
                    test.assertEqual(message, exception.getMessage());
                    test.assertNull(exception.getNotFoundItem());
                });

                runner.test("with empty message", (Test test) ->
                {
                    final String message = "";
                    final ItemNotFoundException exception = new ItemNotFoundException(message);
                    test.assertEqual(message, exception.getMessage());
                    test.assertNull(exception.getNotFoundItem());
                });

                runner.test("with non-empty message", (Test test) ->
                {
                    final String message = "hello there";
                    final ItemNotFoundException exception = new ItemNotFoundException(message);
                    test.assertEqual(message, exception.getMessage());
                    test.assertNull(exception.getNotFoundItem());
                });
            });

            runner.testGroup("constructor(Item)", () ->
            {
                runner.test("with null item", (Test test) ->
                {
                    final Item item = null;
                    test.assertThrows(() -> new ItemNotFoundException(item),
                        new PreConditionFailure("notFoundItem cannot be null."));
                });

                runner.test("with non-null item", (Test test) ->
                {
                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
                    final Item item = itemStorage.getItem("Non-existing Item").await();
                    final ItemNotFoundException exception = new ItemNotFoundException(item);
                    test.assertEqual("No Item named \"Non-existing Item\" found in this ItemStorage.", exception.getMessage());
                    test.assertSame(item, exception.getNotFoundItem());
                });
            });
        });
    }
}
