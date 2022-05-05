package qub;

public interface StorageAttributesTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(StorageAttributes.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                final MutableStorageAttributes attributes = StorageAttributes.create();
                test.assertNotNull(attributes);
                test.assertInstanceOf(attributes, InMemoryStorageAttributes.class);
                test.assertEqual(Iterable.create(), attributes);
                test.assertNull(attributes.getTimestamp());
            });
        });
    }

    public static void test(TestRunner runner, Function0<? extends StorageAttributes> creator)
    {
        PreCondition.assertNotNull(runner, "runner");
        PreCondition.assertNotNull(creator, "creator");

        runner.testGroup(StorageAttributes.class, () ->
        {
            StorageAttributeMapTests.test(runner, creator);
        });
    }
}
