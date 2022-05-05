package qub;

public interface InMemoryStorageAttributeArrayTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageAttributeArray.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                final InMemoryStorageAttributeArray map = InMemoryStorageAttributeArray.create();
                test.assertNotNull(map);
                test.assertEqual(Iterable.create(), map);
            });

            MutableStorageAttributeArrayTests.test(runner, InMemoryStorageAttributeArray::create);
        });
    }
}
