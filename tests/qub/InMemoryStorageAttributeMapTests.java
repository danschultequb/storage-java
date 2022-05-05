package qub;

public interface InMemoryStorageAttributeMapTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageAttributeMap.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                final InMemoryStorageAttributeMap map = InMemoryStorageAttributeMap.create();
                test.assertNotNull(map);
                test.assertEqual(Iterable.create(), map);
            });

            MutableStorageAttributeMapTests.test(runner, InMemoryStorageAttributeMap::create);
        });
    }
}
