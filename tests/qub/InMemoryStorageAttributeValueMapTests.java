package qub;

public interface InMemoryStorageAttributeValueMapTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageAttributeValueMap.class, () ->
        {
            runner.testGroup("create(StorageAttributeMap)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    test.assertThrows(() -> InMemoryStorageAttributeValueMap.create(null),
                        new PreConditionFailure("value cannot be null."));
                });

                final Action1<StorageAttributeMap> createTest = (StorageAttributeMap mapValue) ->
                {
                    runner.test("with " + mapValue, (Test test) ->
                    {
                        final InMemoryStorageAttributeValueMap value = InMemoryStorageAttributeValueMap.create(mapValue);
                        test.assertNotNull(value);
                        test.assertEqual(mapValue, value.getValue());
                    });
                };

                createTest.run(StorageAttributeMap.create());
                createTest.run(StorageAttributeMap.create().setString("hello", "there"));
            });

            runner.test("getBooleanValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()).getBooleanValue().await(),
                    new WrongTypeException("Expected Boolean, but found StorageAttributeMap instead."));
            });

            runner.test("getIntegerValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()).getIntegerValue().await(),
                    new WrongTypeException("Expected Integer, but found StorageAttributeMap instead."));
            });

            runner.test("getLongValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()).getLongValue().await(),
                    new WrongTypeException("Expected Long, but found StorageAttributeMap instead."));
            });

            runner.test("getDoubleValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()).getDoubleValue().await(),
                    new WrongTypeException("Expected Double, but found StorageAttributeMap instead."));
            });

            runner.test("getStringValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()).getStringValue().await(),
                    new WrongTypeException("Expected String, but found StorageAttributeMap instead."));
            });

            runner.test("getArrayValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()).getArrayValue().await(),
                    new WrongTypeException("Expected StorageAttributeArray, but found StorageAttributeMap instead."));
            });

            runner.test("getMapValue()", (Test test) ->
            {
                test.assertEqual(StorageAttributeMap.create(), InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()).getMapValue().await());
            });

            runner.testGroup("toString()", () ->
            {
                final Action2<InMemoryStorageAttributeValueMap,String> toStringTest = (InMemoryStorageAttributeValueMap value, String expected) ->
                {
                    runner.test("with " + value.toString(), (Test test) ->
                    {
                        test.assertEqual(expected, value.toString());
                    });
                };

                toStringTest.run(InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()), "[]");
                toStringTest.run(InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create().setString("hello", "there")), "[hello:there]");
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<InMemoryStorageAttributeValueMap,Object,Boolean> equalsTest = (InMemoryStorageAttributeValueMap lhs, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(lhs, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, lhs.equals(rhs));
                    });
                };

                equalsTest.run(InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create().setString("hello", "there")), null, false);
                equalsTest.run(InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create().setString("hello", "there")), "2.0", false);
                equalsTest.run(InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create().setString("hello", "there")), 2.0, false);
                equalsTest.run(InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create().setString("hello", "there")), InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create().setString("hello", "there")), true);
                equalsTest.run(InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create().setString("hello", "there")), InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()), false);
                equalsTest.run(InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()), InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create().setString("hello", "there")), false);
                equalsTest.run(InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()), InMemoryStorageAttributeValueMap.create(StorageAttributeMap.create()), true);
            });
        });
    }
}
