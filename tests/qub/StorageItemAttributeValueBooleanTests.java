package qub;

public interface StorageItemAttributeValueBooleanTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageItemAttributeValueBoolean.class, () ->
        {
            runner.test("getTrue()", (Test test) ->
            {
                final InMemoryStorageItemAttributeValueBoolean value = InMemoryStorageItemAttributeValueBoolean.getTrue();
                test.assertNotNull(value);
                test.assertTrue(value.getValue());
                test.assertSame(value, InMemoryStorageItemAttributeValueBoolean.getTrue());
            });

            runner.test("getFalse()", (Test test) ->
            {
                final InMemoryStorageItemAttributeValueBoolean value = InMemoryStorageItemAttributeValueBoolean.getFalse();
                test.assertNotNull(value);
                test.assertFalse(value.getValue());
                test.assertSame(value, InMemoryStorageItemAttributeValueBoolean.getFalse());
            });

            runner.testGroup("get(boolean)", () ->
            {
                final Action1<Boolean> getTest = (Boolean booleanValue) ->
                {
                    runner.test("with " + booleanValue, (Test test) ->
                    {
                        final InMemoryStorageItemAttributeValueBoolean value = InMemoryStorageItemAttributeValueBoolean.get(booleanValue.booleanValue());
                        test.assertNotNull(value);
                        test.assertEqual(booleanValue, value.getValue());
                        test.assertSame(value, InMemoryStorageItemAttributeValueBoolean.get(booleanValue.booleanValue()));
                    });
                };

                getTest.run(false);
                getTest.run(true);
            });
        });
    }
}
