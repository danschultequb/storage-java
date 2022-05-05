package qub;

public interface StorageAttributeMapTests
{
    public static void test(TestRunner runner, Function0<? extends StorageAttributeMap> creator)
    {
        PreCondition.assertNotNull(runner, "runner");
        PreCondition.assertNotNull(creator, "creator");

        runner.testGroup(StorageAttributeMap.class, () ->
        {
            runner.testGroup("iterateAttributeNames()", () ->
            {
                runner.test("with no attributes", (Test test) ->
                {
                    final StorageAttributeMap map = creator.run();
                    test.assertEqual(Iterable.create(), map.iterateAttributeNames().toList());
                });
            });

            runner.testGroup("getValue(String)", () ->
            {
                final Action2<String,Throwable> getValueErrorTest = (String attributeName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(attributeName), (Test test) ->
                    {
                        final StorageAttributeMap map = creator.run();
                        test.assertThrows(() -> map.getValue(attributeName).await(),
                            expected);
                    });
                };

                getValueErrorTest.run(null, new PreConditionFailure("attributeName cannot be null."));
                getValueErrorTest.run("", new NotFoundException("No attribute found with the name: \"\""));
                getValueErrorTest.run("hello", new NotFoundException("No attribute found with the name: \"hello\""));
            });

            runner.testGroup("getBoolean(String)", () ->
            {
                final Action2<String,Throwable> getBooleanErrorTest = (String attributeName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(attributeName), (Test test) ->
                    {
                        final StorageAttributeMap map = creator.run();
                        test.assertThrows(() -> map.getBoolean(attributeName).await(),
                            expected);
                    });
                };

                getBooleanErrorTest.run(null, new PreConditionFailure("attributeName cannot be null."));
                getBooleanErrorTest.run("", new NotFoundException("No attribute found with the name: \"\""));
                getBooleanErrorTest.run("hello", new NotFoundException("No attribute found with the name: \"hello\""));
            });

            runner.testGroup("getInteger(String)", () ->
            {
                final Action2<String,Throwable> getIntegerErrorTest = (String attributeName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(attributeName), (Test test) ->
                    {
                        final StorageAttributeMap map = creator.run();
                        test.assertThrows(() -> map.getInteger(attributeName).await(),
                            expected);
                    });
                };

                getIntegerErrorTest.run(null, new PreConditionFailure("attributeName cannot be null."));
                getIntegerErrorTest.run("", new NotFoundException("No attribute found with the name: \"\""));
                getIntegerErrorTest.run("hello", new NotFoundException("No attribute found with the name: \"hello\""));
            });

            runner.testGroup("getLong(String)", () ->
            {
                final Action2<String,Throwable> getLongErrorTest = (String attributeName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(attributeName), (Test test) ->
                    {
                        final StorageAttributeMap map = creator.run();
                        test.assertThrows(() -> map.getLong(attributeName).await(),
                            expected);
                    });
                };

                getLongErrorTest.run(null, new PreConditionFailure("attributeName cannot be null."));
                getLongErrorTest.run("", new NotFoundException("No attribute found with the name: \"\""));
                getLongErrorTest.run("hello", new NotFoundException("No attribute found with the name: \"hello\""));
            });

            runner.testGroup("getDouble(String)", () ->
            {
                final Action2<String,Throwable> getDoubleErrorTest = (String attributeName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(attributeName), (Test test) ->
                    {
                        final StorageAttributeMap map = creator.run();
                        test.assertThrows(() -> map.getDouble(attributeName).await(),
                            expected);
                    });
                };

                getDoubleErrorTest.run(null, new PreConditionFailure("attributeName cannot be null."));
                getDoubleErrorTest.run("", new NotFoundException("No attribute found with the name: \"\""));
                getDoubleErrorTest.run("hello", new NotFoundException("No attribute found with the name: \"hello\""));
            });

            runner.testGroup("getString(String)", () ->
            {
                final Action2<String,Throwable> getStringErrorTest = (String attributeName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(attributeName), (Test test) ->
                    {
                        final StorageAttributeMap map = creator.run();
                        test.assertThrows(() -> map.getString(attributeName).await(),
                            expected);
                    });
                };

                getStringErrorTest.run(null, new PreConditionFailure("attributeName cannot be null."));
                getStringErrorTest.run("", new NotFoundException("No attribute found with the name: \"\""));
                getStringErrorTest.run("hello", new NotFoundException("No attribute found with the name: \"hello\""));
            });

            runner.testGroup("getMap(String)", () ->
            {
                final Action2<String,Throwable> getMapErrorTest = (String attributeName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(attributeName), (Test test) ->
                    {
                        final StorageAttributeMap map = creator.run();
                        test.assertThrows(() -> map.getMap(attributeName).await(),
                            expected);
                    });
                };

                getMapErrorTest.run(null, new PreConditionFailure("attributeName cannot be null."));
                getMapErrorTest.run("", new NotFoundException("No attribute found with the name: \"\""));
                getMapErrorTest.run("hello", new NotFoundException("No attribute found with the name: \"hello\""));
            });

            runner.testGroup("getArray(String)", () ->
            {
                final Action2<String,Throwable> getArrayErrorTest = (String attributeName, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(attributeName), (Test test) ->
                    {
                        final StorageAttributeMap map = creator.run();
                        test.assertThrows(() -> map.getArray(attributeName).await(),
                            expected);
                    });
                };

                getArrayErrorTest.run(null, new PreConditionFailure("attributeName cannot be null."));
                getArrayErrorTest.run("", new NotFoundException("No attribute found with the name: \"\""));
                getArrayErrorTest.run("hello", new NotFoundException("No attribute found with the name: \"hello\""));
            });
        });
    }
}
