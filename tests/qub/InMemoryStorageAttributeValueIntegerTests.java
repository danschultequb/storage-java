package qub;

public interface InMemoryStorageAttributeValueIntegerTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageAttributeValueInteger.class, () ->
        {
            runner.testGroup("create(int)", () ->
            {
                final Action1<Integer> createTest = (Integer intValue) ->
                {
                    runner.test("with " + intValue, (Test test) ->
                    {
                        final InMemoryStorageAttributeValueInteger value = InMemoryStorageAttributeValueInteger.create(intValue.intValue());
                        test.assertNotNull(value);
                        test.assertEqual(intValue, value.getValue());
                    });
                };

                createTest.run(-1);
                createTest.run(0);
                createTest.run(1);
            });

            runner.test("getBooleanValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueInteger.create(3).getBooleanValue().await(),
                    new WrongTypeException("Expected Boolean, but found Integer instead."));
            });

            runner.test("getIntegerValue()", (Test test) ->
            {
                test.assertEqual(3, InMemoryStorageAttributeValueInteger.create(3).getIntegerValue().await());
            });

            runner.test("getLongValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueInteger.create(3).getLongValue().await(),
                    new WrongTypeException("Expected Long, but found Integer instead."));
            });

            runner.test("getDoubleValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueInteger.create(3).getDoubleValue().await(),
                    new WrongTypeException("Expected Double, but found Integer instead."));
            });

            runner.test("getStringValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueInteger.create(3).getStringValue().await(),
                    new WrongTypeException("Expected String, but found Integer instead."));
            });

            runner.test("getArrayValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueInteger.create(3).getArrayValue().await(),
                    new WrongTypeException("Expected StorageAttributeArray, but found Integer instead."));
            });

            runner.test("getMapValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueInteger.create(3).getMapValue().await(),
                    new WrongTypeException("Expected StorageAttributeMap, but found Integer instead."));
            });

            runner.testGroup("toString()", () ->
            {
                final Action2<InMemoryStorageAttributeValueInteger,String> toStringTest = (InMemoryStorageAttributeValueInteger value, String expected) ->
                {
                    runner.test("with " + value.toString(), (Test test) ->
                    {
                        test.assertEqual(expected, value.toString());
                    });
                };

                toStringTest.run(InMemoryStorageAttributeValueInteger.create(2), "2");
                toStringTest.run(InMemoryStorageAttributeValueInteger.create(3), "3");
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<InMemoryStorageAttributeValueInteger,Object,Boolean> equalsTest = (InMemoryStorageAttributeValueInteger lhs, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(lhs, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, lhs.equals(rhs));
                    });
                };

                equalsTest.run(InMemoryStorageAttributeValueInteger.create(2), null, false);
                equalsTest.run(InMemoryStorageAttributeValueInteger.create(2), "2", false);
                equalsTest.run(InMemoryStorageAttributeValueInteger.create(2), 2, false);
                equalsTest.run(InMemoryStorageAttributeValueInteger.create(2), InMemoryStorageAttributeValueInteger.create(2), true);
                equalsTest.run(InMemoryStorageAttributeValueInteger.create(2), InMemoryStorageAttributeValueInteger.create(3), false);
                equalsTest.run(InMemoryStorageAttributeValueInteger.create(3), InMemoryStorageAttributeValueInteger.create(2), false);
                equalsTest.run(InMemoryStorageAttributeValueInteger.create(3), InMemoryStorageAttributeValueInteger.create(3), true);
            });
        });
    }
}
