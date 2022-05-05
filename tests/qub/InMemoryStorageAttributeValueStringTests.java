package qub;

public interface InMemoryStorageAttributeValueStringTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageAttributeValueString.class, () ->
        {
            runner.testGroup("create(String)", () ->
            {
                final Action1<String> createTest = (String stringValue) ->
                {
                    runner.test("with " + stringValue, (Test test) ->
                    {
                        final InMemoryStorageAttributeValueString value = InMemoryStorageAttributeValueString.create(stringValue);
                        test.assertNotNull(value);
                        test.assertEqual(stringValue, value.getValue());
                    });
                };

                createTest.run("");
                createTest.run("a");
                createTest.run("hello there!");
            });

            runner.test("getBooleanValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueString.create("abc").getBooleanValue().await(),
                    new WrongTypeException("Expected Boolean, but found String instead."));
            });

            runner.test("getIntegerValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueString.create("abc").getIntegerValue().await(),
                    new WrongTypeException("Expected Integer, but found String instead."));
            });

            runner.test("getLongValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueString.create("abc").getLongValue().await(),
                    new WrongTypeException("Expected Long, but found String instead."));
            });

            runner.test("getDoubleValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueString.create("abc").getDoubleValue().await(),
                    new WrongTypeException("Expected Double, but found String instead."));
            });

            runner.test("getStringValue()", (Test test) ->
            {
                test.assertEqual("abc", InMemoryStorageAttributeValueString.create("abc").getStringValue().await());
            });

            runner.test("getArrayValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueString.create("abc").getArrayValue().await(),
                    new WrongTypeException("Expected StorageAttributeArray, but found String instead."));
            });

            runner.test("getMapValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueString.create("abc").getMapValue().await(),
                    new WrongTypeException("Expected StorageAttributeMap, but found String instead."));
            });

            runner.testGroup("toString()", () ->
            {
                final Action2<InMemoryStorageAttributeValueString,String> toStringTest = (InMemoryStorageAttributeValueString value, String expected) ->
                {
                    runner.test("with " + value.toString(), (Test test) ->
                    {
                        test.assertEqual(expected, value.toString());
                    });
                };

                toStringTest.run(InMemoryStorageAttributeValueString.create("d"), "d");
                toStringTest.run(InMemoryStorageAttributeValueString.create("abc"), "abc");
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<InMemoryStorageAttributeValueString,Object,Boolean> equalsTest = (InMemoryStorageAttributeValueString lhs, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(lhs, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, lhs.equals(rhs));
                    });
                };

                equalsTest.run(InMemoryStorageAttributeValueString.create("d"), null, false);
                equalsTest.run(InMemoryStorageAttributeValueString.create("d"), "d", false);
                equalsTest.run(InMemoryStorageAttributeValueString.create("d"), InMemoryStorageAttributeValueString.create("d"), true);
                equalsTest.run(InMemoryStorageAttributeValueString.create("d"), InMemoryStorageAttributeValueString.create("abc"), false);
                equalsTest.run(InMemoryStorageAttributeValueString.create("abc"), InMemoryStorageAttributeValueString.create("d"), false);
                equalsTest.run(InMemoryStorageAttributeValueString.create("abc"), InMemoryStorageAttributeValueString.create("abc"), true);
            });
        });
    }
}
