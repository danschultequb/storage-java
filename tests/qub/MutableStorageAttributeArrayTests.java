package qub;

public interface MutableStorageAttributeArrayTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(MutableStorageAttributeArray.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                final InMemoryStorageAttributeArray array = MutableStorageAttributeArray.create();
                test.assertNotNull(array);
                test.assertEqual(Iterable.create(), array);
            });
        });
    }

    public static void test(TestRunner runner, Function0<? extends MutableStorageAttributeArray> creator)
    {
        PreCondition.assertNotNull(runner, "runner");
        PreCondition.assertNotNull(creator, "creator");

        runner.testGroup(MutableStorageAttributeArray.class, () ->
        {
            runner.testGroup("set(int,StorageAttributeValue)", () ->
            {
                runner.testGroup("when array is empty", () ->
                {
                    final Action3<Integer,StorageAttributeValue,Throwable> setErrorTest = (Integer index, StorageAttributeValue value, Throwable expected) ->
                    {
                        runner.test("with " + English.andList(index, value), (Test test) ->
                        {
                            final MutableStorageAttributeArray array = creator.run();
                            test.assertThrows(() -> array.set(index.intValue(), value),
                                expected);
                            test.assertEqual(Iterable.create(), array);
                        });
                    };

                    setErrorTest.run(-1, InMemoryStorageAttributeValueBoolean.getFalse(), new PreConditionFailure("Indexable length (0) must be greater than or equal to 1."));
                    setErrorTest.run(0, InMemoryStorageAttributeValueBoolean.getFalse(), new PreConditionFailure("Indexable length (0) must be greater than or equal to 1."));
                    setErrorTest.run(1, InMemoryStorageAttributeValueBoolean.getFalse(), new PreConditionFailure("Indexable length (0) must be greater than or equal to 1."));
                    setErrorTest.run(0, null, new PreConditionFailure("Indexable length (0) must be greater than or equal to 1."));
                });

                runner.testGroup("when array has one value", () ->
                {
                    final Action3<Integer,StorageAttributeValue,Throwable> setErrorTest = (Integer index, StorageAttributeValue value, Throwable expected) ->
                    {
                        runner.test("with " + English.andList(index, value), (Test test) ->
                        {
                            final MutableStorageAttributeArray array = creator.run().addBoolean(false);
                            test.assertThrows(() -> array.set(index.intValue(), value),
                                expected);
                            test.assertEqual(Iterable.create(InMemoryStorageAttributeValueBoolean.getFalse()), array);
                        });
                    };

                    setErrorTest.run(-1, InMemoryStorageAttributeValueBoolean.getFalse(), new PreConditionFailure("index (-1) must be equal to 0."));
                    setErrorTest.run(1, InMemoryStorageAttributeValueBoolean.getFalse(), new PreConditionFailure("index (1) must be equal to 0."));
                    setErrorTest.run(0, null, new PreConditionFailure("value cannot be null."));

                    final Action2<Integer,StorageAttributeValue> setTest = (Integer index, StorageAttributeValue value) ->
                    {
                        runner.test("with " + English.andList(index, value), (Test test) ->
                        {
                            final MutableStorageAttributeArray array = creator.run().addBoolean(false);
                            final MutableStorageAttributeArray setResult = array.set(index.intValue(), value);
                            test.assertSame(array, setResult);
                            test.assertEqual(value, array.get(index.intValue()));
                            test.assertEqual(Iterable.create(value), array);
                        });
                    };

                    setTest.run(0, InMemoryStorageAttributeValueBoolean.getTrue());
                    setTest.run(0, InMemoryStorageAttributeValueInteger.create(5));
                    setTest.run(0, InMemoryStorageAttributeValueString.create("hello"));
                });

                runner.testGroup("when array has two values", () ->
                {
                    final Action3<Integer,StorageAttributeValue,Throwable> setErrorTest = (Integer index, StorageAttributeValue value, Throwable expected) ->
                    {
                        runner.test("with " + English.andList(index, value), (Test test) ->
                        {
                            final MutableStorageAttributeArray array = creator.run().addBoolean(false).addBoolean(true);
                            test.assertThrows(() -> array.set(index.intValue(), value),
                                expected);
                            test.assertEqual(
                                Iterable.create(
                                    InMemoryStorageAttributeValueBoolean.getFalse(),
                                    InMemoryStorageAttributeValueBoolean.getTrue()),
                                array);
                        });
                    };

                    setErrorTest.run(-1, InMemoryStorageAttributeValueBoolean.getFalse(), new PreConditionFailure("index (-1) must be between 0 and 1."));
                    setErrorTest.run(2, InMemoryStorageAttributeValueBoolean.getFalse(), new PreConditionFailure("index (2) must be between 0 and 1."));
                    setErrorTest.run(0, null, new PreConditionFailure("value cannot be null."));

                    final Action2<Integer,StorageAttributeValue> setTest = (Integer index, StorageAttributeValue value) ->
                    {
                        runner.test("with " + English.andList(index, value), (Test test) ->
                        {
                            final MutableStorageAttributeArray array = creator.run()
                                .addBoolean(false)
                                .addBoolean(true);
                            final MutableStorageAttributeArray setResult = array.set(index.intValue(), value);
                            test.assertSame(array, setResult);
                            test.assertEqual(value, array.get(index.intValue()));
                            test.assertEqual(
                                Iterable.create(
                                    index == 0 ? value : InMemoryStorageAttributeValueBoolean.getFalse(),
                                    index == 1 ? value : InMemoryStorageAttributeValueBoolean.getTrue()),
                                array);
                        });
                    };

                    setTest.run(0, InMemoryStorageAttributeValueBoolean.getTrue());
                    setTest.run(0, InMemoryStorageAttributeValueInteger.create(5));
                    setTest.run(0, InMemoryStorageAttributeValueString.create("hello"));
                    setTest.run(1, InMemoryStorageAttributeValueBoolean.getTrue());
                    setTest.run(1, InMemoryStorageAttributeValueInteger.create(5));
                    setTest.run(1, InMemoryStorageAttributeValueString.create("hello"));
                });
            });
        });
    }
}
