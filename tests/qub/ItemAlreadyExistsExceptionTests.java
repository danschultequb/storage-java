//package qub;
//
//public interface ItemAlreadyExistsExceptionTests
//{
//    public static void test(TestRunner runner)
//    {
//        runner.testGroup(ItemAlreadyExistsException.class, () ->
//        {
//            runner.testGroup("constructor(Item)", () ->
//            {
//                runner.test("with null", (Test test) ->
//                {
//                    test.assertThrows(() -> new ItemAlreadyExistsException(null),
//                        new PreConditionFailure("alreadyExistingItem cannot be null."));
//                });
//
//                runner.test("with non-null", (Test test) ->
//                {
//                    final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
//                    final Item item = itemStorage.getItem("hello").await();
//
//                    final ItemAlreadyExistsException exception = new ItemAlreadyExistsException(item);
//                    test.assertNotNull(exception);
//                    test.assertSame(item, exception.getAlreadyExistingItem());
//                    test.assertEqual("An Item already exists with the name \"hello\" in this ItemStorage.", exception.getMessage());
//                });
//            });
//        });
//    }
//}
