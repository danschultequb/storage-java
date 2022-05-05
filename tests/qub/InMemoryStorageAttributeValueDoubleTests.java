package qub;

public interface InMemoryStorageAttributeValueDoubleTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageAttributeValueDouble.class, () ->
        {
            runner.testGroup("create(double)", () ->
            {
                final Action1<Double> createTest = (Double doubleValue) ->
                {
                    runner.test("with " + doubleValue, (Test test) ->
                    {
                        final InMemoryStorageAttributeValueDouble value = InMemoryStorageAttributeValueDouble.create(doubleValue.doubleValue());
                        test.assertNotNull(value);
                        test.assertEqual(doubleValue, value.getValue());
                    });
                };

                createTest.run(-1.0);
                createTest.run(0.0);
                createTest.run(1.0);
            });

            runner.test("getBooleanValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueDouble.create(3).getBooleanValue().await(),
                    new WrongTypeException("Expected Boolean, but found Double instead."));
            });

            runner.test("getIntegerValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueDouble.create(3).getIntegerValue().await(),
                    new WrongTypeException("Expected Integer, but found Double instead."));
            });

            runner.test("getLongValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueDouble.create(3).getLongValue().await(),
                    new WrongTypeException("Expected Long, but found Double instead."));
            });

            runner.test("getDoubleValue()", (Test test) ->
            {
                test.assertEqual(3.0, InMemoryStorageAttributeValueDouble.create(3).getDoubleValue().await());
            });

            runner.test("getStringValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueDouble.create(3).getStringValue().await(),
                    new WrongTypeException("Expected String, but found Double instead."));
            });

            runner.test("getArrayValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueDouble.create(3).getArrayValue().await(),
                    new WrongTypeException("Expected StorageAttributeArray, but found Double instead."));
            });

            runner.test("getMapValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueDouble.create(3).getMapValue().await(),
                    new WrongTypeException("Expected StorageAttributeMap, but found Double instead."));
            });

            runner.testGroup("toString()", () ->
            {
                final Action2<InMemoryStorageAttributeValueDouble,String> toStringTest = (InMemoryStorageAttributeValueDouble value, String expected) ->
                {
                    runner.test("with " + value.toString(), (Test test) ->
                    {
                        test.assertEqual(expected, value.toString());
                    });
                };

                toStringTest.run(InMemoryStorageAttributeValueDouble.create(2), "2.0");
                toStringTest.run(InMemoryStorageAttributeValueDouble.create(3), "3.0");
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<InMemoryStorageAttributeValueDouble,Object,Boolean> equalsTest = (InMemoryStorageAttributeValueDouble lhs, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(lhs, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, lhs.equals(rhs));
                    });
                };

                equalsTest.run(InMemoryStorageAttributeValueDouble.create(2), null, false);
                equalsTest.run(InMemoryStorageAttributeValueDouble.create(2), "2.0", false);
                equalsTest.run(InMemoryStorageAttributeValueDouble.create(2), 2.0, false);
                equalsTest.run(InMemoryStorageAttributeValueDouble.create(2), InMemoryStorageAttributeValueDouble.create(2), true);
                equalsTest.run(InMemoryStorageAttributeValueDouble.create(2), InMemoryStorageAttributeValueDouble.create(3), false);
                equalsTest.run(InMemoryStorageAttributeValueDouble.create(3), InMemoryStorageAttributeValueDouble.create(2), false);
                equalsTest.run(InMemoryStorageAttributeValueDouble.create(3), InMemoryStorageAttributeValueDouble.create(3), true);
            });
        });
    }
}
