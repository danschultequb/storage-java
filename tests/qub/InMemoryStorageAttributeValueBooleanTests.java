package qub;

public interface InMemoryStorageAttributeValueBooleanTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageAttributeValueBoolean.class, () ->
        {
            runner.test("getTrue()", (Test test) ->
            {
                final InMemoryStorageAttributeValueBoolean value = InMemoryStorageAttributeValueBoolean.getTrue();
                test.assertNotNull(value);
                test.assertTrue(value.getValue());
                test.assertSame(value, InMemoryStorageAttributeValueBoolean.getTrue());
            });

            runner.test("getFalse()", (Test test) ->
            {
                final InMemoryStorageAttributeValueBoolean value = InMemoryStorageAttributeValueBoolean.getFalse();
                test.assertNotNull(value);
                test.assertFalse(value.getValue());
                test.assertSame(value, InMemoryStorageAttributeValueBoolean.getFalse());
            });

            runner.testGroup("get(boolean)", () ->
            {
                final Action1<Boolean> getTest = (Boolean booleanValue) ->
                {
                    runner.test("with " + booleanValue, (Test test) ->
                    {
                        final InMemoryStorageAttributeValueBoolean value = InMemoryStorageAttributeValueBoolean.get(booleanValue.booleanValue());
                        test.assertNotNull(value);
                        test.assertEqual(booleanValue, value.getValue());
                        test.assertSame(value, InMemoryStorageAttributeValueBoolean.get(booleanValue.booleanValue()));
                    });
                };

                getTest.run(false);
                getTest.run(true);
            });

            runner.test("getBooleanValue()", (Test test) ->
            {
                test.assertFalse(InMemoryStorageAttributeValueBoolean.getFalse().getBooleanValue().await());
                test.assertTrue(InMemoryStorageAttributeValueBoolean.getTrue().getBooleanValue().await());
            });

            runner.test("getIntegerValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueBoolean.get(true).getIntegerValue().await(),
                    new WrongTypeException("Expected Integer, but found Boolean instead."));
            });

            runner.test("getLongValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueBoolean.get(true).getLongValue().await(),
                    new WrongTypeException("Expected Long, but found Boolean instead."));
            });

            runner.test("getDoubleValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueBoolean.get(true).getDoubleValue().await(),
                    new WrongTypeException("Expected Double, but found Boolean instead."));
            });

            runner.test("getStringValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueBoolean.get(true).getStringValue().await(),
                    new WrongTypeException("Expected String, but found Boolean instead."));
            });

            runner.test("getArrayValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueBoolean.get(true).getArrayValue().await(),
                    new WrongTypeException("Expected StorageAttributeArray, but found Boolean instead."));
            });

            runner.test("getMapValue()", (Test test) ->
            {
                test.assertThrows(() -> InMemoryStorageAttributeValueBoolean.get(true).getMapValue().await(),
                    new WrongTypeException("Expected StorageAttributeMap, but found Boolean instead."));
            });

            runner.testGroup("toString()", () ->
            {
                final Action2<InMemoryStorageAttributeValueBoolean,String> toStringTest = (InMemoryStorageAttributeValueBoolean value, String expected) ->
                {
                    runner.test("with " + value.toString(), (Test test) ->
                    {
                        test.assertEqual(expected, value.toString());
                    });
                };

                toStringTest.run(InMemoryStorageAttributeValueBoolean.getFalse(), "false");
                toStringTest.run(InMemoryStorageAttributeValueBoolean.getTrue(), "true");
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<InMemoryStorageAttributeValueBoolean,Object,Boolean> equalsTest = (InMemoryStorageAttributeValueBoolean lhs, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(lhs, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, lhs.equals(rhs));
                    });
                };

                equalsTest.run(InMemoryStorageAttributeValueBoolean.getFalse(), null, false);
                equalsTest.run(InMemoryStorageAttributeValueBoolean.getFalse(), "false", false);
                equalsTest.run(InMemoryStorageAttributeValueBoolean.getFalse(), false, false);
                equalsTest.run(InMemoryStorageAttributeValueBoolean.getFalse(), InMemoryStorageAttributeValueBoolean.getFalse(), true);
                equalsTest.run(InMemoryStorageAttributeValueBoolean.getFalse(), InMemoryStorageAttributeValueBoolean.getTrue(), false);
                equalsTest.run(InMemoryStorageAttributeValueBoolean.getTrue(), InMemoryStorageAttributeValueBoolean.getFalse(), false);
                equalsTest.run(InMemoryStorageAttributeValueBoolean.getTrue(), InMemoryStorageAttributeValueBoolean.getTrue(), true);
            });
        });
    }
}
