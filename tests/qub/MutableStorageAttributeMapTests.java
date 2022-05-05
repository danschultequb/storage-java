package qub;

public interface MutableStorageAttributeMapTests
{
    public static void test(TestRunner runner, Function0<? extends MutableStorageAttributeMap> creator)
    {
        runner.testGroup(MutableStorageAttributeMap.class, () ->
        {
            StorageAttributeMapTests.test(runner, creator);

            runner.testGroup("clear()", () ->
            {
                runner.test("with empty map", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();
                    test.assertNotNull(map);
                    test.assertEqual(Iterable.create(), map);

                    map.clear();

                    test.assertEqual(Iterable.create(), map);
                });

                runner.test("with non-empty map", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();
                    test.assertNotNull(map);
                    test.assertEqual(Iterable.create(), map);

                    map.setBoolean("hello", true);
                    test.assertEqual(1, map.getCount());

                    map.clear();

                    test.assertEqual(Iterable.create(), map);
                });
            });

            runner.testGroup("setValue(String,StorageAttributeValue)", () ->
            {
                final Action3<String,StorageAttributeValue,Throwable> setValueErrorTest = (String attributeName, StorageAttributeValue attributeValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(attributeName), Objects.toString(attributeValue)), (Test test) ->
                    {
                        final MutableStorageAttributeMap map = creator.run();

                        test.assertThrows(() -> map.setValue(attributeName, attributeValue),
                            expected);

                        test.assertEqual(Iterable.create(), map);
                    });
                };

                setValueErrorTest.run(null, InMemoryStorageAttributeValueBoolean.getFalse(), new PreConditionFailure("attributeName cannot be null."));
                setValueErrorTest.run("", null, new PreConditionFailure("attributeValue cannot be null."));

                runner.test("with valid arguments", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();
                    final MutableStorageAttributeMap setValueResult = map.setValue("hello", InMemoryStorageAttributeValueDouble.create(5.1));
                    test.assertSame(map, setValueResult);
                    test.assertEqual(5.1, map.getDouble("hello").await());
                });

                runner.test("with existing attribute with same name", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run()
                        .setString("hello", "there");
                    final MutableStorageAttributeMap setValueResult = map.setValue("hello", InMemoryStorageAttributeValueDouble.create(5.1));
                    test.assertSame(map, setValueResult);
                    test.assertEqual(5.1, map.getDouble("hello").await());
                });
            });

            runner.testGroup("setBoolean(String,boolean)", () ->
            {
                runner.test("with null attributeName", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setBoolean(null, false),
                        new PreConditionFailure("attributeName cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                final Action2<String,Boolean> setBooleanTest = (String attributeName, Boolean attributeValue) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(attributeName), attributeValue.toString()), (Test test) ->
                    {
                        final MutableStorageAttributeMap map = creator.run();
                        final MutableStorageAttributeMap setBooleanResult = map.setBoolean(attributeName, attributeValue.booleanValue());
                        test.assertSame(map, setBooleanResult);
                        test.assertEqual(attributeValue, map.getBoolean(attributeName).await());
                    });
                };

                setBooleanTest.run("", false);
                setBooleanTest.run("hello", true);
            });

            runner.testGroup("setInteger(String,int)", () ->
            {
                runner.test("with null attributeName", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setInteger(null, 3),
                        new PreConditionFailure("attributeName cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                final Action2<String,Integer> setIntegerTest = (String attributeName, Integer attributeValue) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(attributeName), Integers.toString(attributeValue)), (Test test) ->
                    {
                        final MutableStorageAttributeMap map = creator.run();
                        final MutableStorageAttributeMap setIntegerResult = map.setInteger(attributeName, attributeValue.intValue());
                        test.assertSame(map, setIntegerResult);
                        test.assertEqual(attributeValue, map.getInteger(attributeName).await());
                    });
                };

                setIntegerTest.run("", -1);
                setIntegerTest.run("hello", 0);
                setIntegerTest.run("hello there", 1);
            });

            runner.testGroup("setLong(String,long)", () ->
            {
                runner.test("with null attributeName", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setLong(null, 3),
                        new PreConditionFailure("attributeName cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                final Action2<String,Long> setLongTest = (String attributeName, Long attributeValue) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(attributeName), Longs.toString(attributeValue)), (Test test) ->
                    {
                        final MutableStorageAttributeMap map = creator.run();
                        final MutableStorageAttributeMap setLongResult = map.setLong(attributeName, attributeValue.intValue());
                        test.assertSame(map, setLongResult);
                        test.assertEqual(attributeValue, map.getLong(attributeName).await());
                    });
                };

                setLongTest.run("", -1L);
                setLongTest.run("hello", 0L);
                setLongTest.run("hello there", 1L);
            });

            runner.testGroup("setDouble(String,double)", () ->
            {
                runner.test("with null attributeName", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setDouble(null, 3),
                        new PreConditionFailure("attributeName cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                final Action2<String,Double> setDoubleTest = (String attributeName, Double attributeValue) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(attributeName), Doubles.toString(attributeValue)), (Test test) ->
                    {
                        final MutableStorageAttributeMap map = creator.run();
                        final MutableStorageAttributeMap setDoubleResult = map.setDouble(attributeName, attributeValue.intValue());
                        test.assertSame(map, setDoubleResult);
                        test.assertEqual(attributeValue, map.getDouble(attributeName).await());
                    });
                };

                setDoubleTest.run("", -1.0);
                setDoubleTest.run("hello", 0.0);
                setDoubleTest.run("hello there", 1.0);
            });

            runner.testGroup("setString(String,string)", () ->
            {
                runner.test("with null attributeName", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setString(null, "there"),
                        new PreConditionFailure("attributeName cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                runner.test("with null attributeValue", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setString("hello", null),
                        new PreConditionFailure("attributeValue cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                final Action2<String,String> setStringTest = (String attributeName, String attributeValue) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(attributeName), attributeValue.toString()), (Test test) ->
                    {
                        final MutableStorageAttributeMap map = creator.run();
                        final MutableStorageAttributeMap setStringResult = map.setString(attributeName, attributeValue);
                        test.assertSame(map, setStringResult);
                        test.assertEqual(attributeValue, map.getString(attributeName).await());
                    });
                };

                setStringTest.run("", "there");
                setStringTest.run("hello", "there");
                setStringTest.run("hello there", "");
            });

            runner.testGroup("setMap(String,StorageAttributeMap)", () ->
            {
                runner.test("with null attributeName", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setMap(null, InMemoryStorageAttributeMap.create()),
                        new PreConditionFailure("attributeName cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                runner.test("with null attributeValue", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setMap("hello", null),
                        new PreConditionFailure("attributeValue cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                final Action2<String,StorageAttributeMap> setMapTest = (String attributeName, StorageAttributeMap attributeValue) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(attributeName), attributeValue.toString()), (Test test) ->
                    {
                        final MutableStorageAttributeMap map = creator.run();
                        final MutableStorageAttributeMap setMapResult = map.setMap(attributeName, attributeValue);
                        test.assertSame(map, setMapResult);
                        test.assertEqual(attributeValue, map.getMap(attributeName).await());
                    });
                };

                setMapTest.run("", InMemoryStorageAttributeMap.create());
                setMapTest.run("hello", InMemoryStorageAttributeMap.create());
            });

            runner.testGroup("setArray(String,StorageAttributeArray)", () ->
            {
                runner.test("with null attributeName", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setArray(null, InMemoryStorageAttributeArray.create()),
                        new PreConditionFailure("attributeName cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                runner.test("with null attributeValue", (Test test) ->
                {
                    final MutableStorageAttributeMap map = creator.run();

                    test.assertThrows(() -> map.setArray("hello", null),
                        new PreConditionFailure("attributeValue cannot be null."));

                    test.assertEqual(Iterable.create(), map);
                });

                final Action2<String, StorageAttributeArray> setArrayTest = (String attributeName, StorageAttributeArray attributeValue) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(attributeName), attributeValue.toString()), (Test test) ->
                    {
                        final MutableStorageAttributeMap map = creator.run();
                        final MutableStorageAttributeMap setArrayResult = map.setArray(attributeName, attributeValue);
                        test.assertSame(map, setArrayResult);
                        test.assertEqual(attributeValue, map.getArray(attributeName).await());
                    });
                };

                setArrayTest.run("", InMemoryStorageAttributeArray.create());
                setArrayTest.run("hello", InMemoryStorageAttributeArray.create());
            });

            runner.testGroup("toString()", () ->
            {
                final Action2<MutableStorageAttributeMap,String> toStringTest = (MutableStorageAttributeMap map, String expected) ->
                {
                    runner.test("with " + map.toString(), (Test test) ->
                    {
                        test.assertEqual(expected, map.toString());
                    });
                };

                toStringTest.run(creator.run(), "[]");
                toStringTest.run(creator.run().setBoolean("hello", false).setInteger("age", 5), "[hello:false,age:5]");
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<MutableStorageAttributeMap,Object,Boolean> equalsTest = (MutableStorageAttributeMap lhs, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(lhs, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, lhs.equals(rhs));
                    });
                };

                equalsTest.run(creator.run(), null, false);
                equalsTest.run(creator.run(), "hello", false);
                equalsTest.run(creator.run(), creator.run(), true);
                equalsTest.run(creator.run().setBoolean("hello", true), creator.run(), false);
                equalsTest.run(creator.run().setBoolean("hello", true), creator.run().setBoolean("hello", true), true);
                equalsTest.run(creator.run().setBoolean("hello", true), creator.run().setBoolean("hello", false), false);
                equalsTest.run(creator.run().setBoolean("help", true), creator.run().setBoolean("hello", true), false);
            });
        });
    }
}
