//package qub;
//
//public interface InMemoryItemStorageTests
//{
//    public static void test(TestRunner runner)
//    {
//        runner.testGroup(InMemoryItemStorage.class, () ->
//        {
//            runner.test("create()", (Test test) ->
//            {
//                final InMemoryItemStorage itemStorage = InMemoryItemStorage.create();
//                test.assertNotNull(itemStorage, "itemStorage");
//                test.assertEqual(Iterable.create(), itemStorage.iterateItems().toList());
//            });
//
//            ItemStorageTests.test(runner, InMemoryItemStorage::create);
//        });
//    }
//}
