package qub;

public interface InMemoryStorageAttributeValueLongTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageAttributeValueLong.class, () ->
        {
            runner.testGroup("create(long)", () ->
            {
                final Action1<Long> createTest = (Long longValue) ->
                {
                    runner.test("with " + longValue, (Test test) ->
                    {
                        final InMemoryStorageAttributeValueLong value = InMemoryStorageAttributeValueLong.create(longValue.longValue());
                        test.assertNotNull(value);
                        test.assertEqual(longValue, value.getValue());
                    });
                };

                createTest.run(-1L);
                createTest.run(0L);
                createTest.run(1L);
            });

            runner.test("getBooleanValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueLong.create(3).getBooleanValue().await(),
                    new WrongTypeException("Expected Boolean, but found Long instead."));
            });

            runner.test("getIntegerValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueLong.create(3).getIntegerValue().await(),
                    new WrongTypeException("Expected Integer, but found Long instead."));
            });

            runner.test("getLongValue()", (Test test) ->
            {
                test.assertEqual(3, InMemoryStorageAttributeValueLong.create(3).getLongValue().await());
            });

            runner.test("getDoubleValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueLong.create(3).getDoubleValue().await(),
                    new WrongTypeException("Expected Double, but found Long instead."));
            });

            runner.test("getStringValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueLong.create(3).getStringValue().await(),
                    new WrongTypeException("Expected String, but found Long instead."));
            });

            runner.test("getArrayValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueLong.create(3).getArrayValue().await(),
                    new WrongTypeException("Expected StorageAttributeArray, but found Long instead."));
            });

            runner.test("getMapValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueLong.create(3).getMapValue().await(),
                    new WrongTypeException("Expected StorageAttributeMap, but found Long instead."));
            });

            runner.testGroup("toString()", () ->
            {
                final Action2<InMemoryStorageAttributeValueLong,String> toStringTest = (InMemoryStorageAttributeValueLong value, String expected) ->
                {
                    runner.test("with " + value.toString(), (Test test) ->
                    {
                        test.assertEqual(expected, value.toString());
                    });
                };

                toStringTest.run(InMemoryStorageAttributeValueLong.create(2), "2");
                toStringTest.run(InMemoryStorageAttributeValueLong.create(3), "3");
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<InMemoryStorageAttributeValueLong,Object,Boolean> equalsTest = (InMemoryStorageAttributeValueLong lhs, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(lhs, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, lhs.equals(rhs));
                    });
                };

                equalsTest.run(InMemoryStorageAttributeValueLong.create(2), null, false);
                equalsTest.run(InMemoryStorageAttributeValueLong.create(2), "2", false);
                equalsTest.run(InMemoryStorageAttributeValueLong.create(2), 2L, false);
                equalsTest.run(InMemoryStorageAttributeValueLong.create(2), InMemoryStorageAttributeValueLong.create(2), true);
                equalsTest.run(InMemoryStorageAttributeValueLong.create(2), InMemoryStorageAttributeValueLong.create(3), false);
                equalsTest.run(InMemoryStorageAttributeValueLong.create(3), InMemoryStorageAttributeValueLong.create(2), false);
                equalsTest.run(InMemoryStorageAttributeValueLong.create(3), InMemoryStorageAttributeValueLong.create(3), true);
            });
        });
    }
}
