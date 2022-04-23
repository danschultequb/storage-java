package qub;

public interface InMemoryStorageContainerTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageContainer.class, () ->
        {
            StorageContainerTests.test(runner, InMemoryStorageContainerTests::createContainer);
            
            runner.testGroup("create(Clock)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    test.assertThrows(() -> InMemoryStorageContainer.create(null),
                        new PreConditionFailure("clock cannot be null."));
                });

                runner.test("with non-null", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    test.assertNotNull(container);
                    test.assertEqual(Iterable.create(), container.iterateItems().toList());
                });
            });
        });
    }

    public static InMemoryStorageContainer createContainer()
    {
        return InMemoryStorageContainerTests.createContainer(ManualClock.create());
    }

    public static InMemoryStorageContainer createContainer(Clock clock)
    {
        PreCondition.assertNotNull(clock, "clock");

        return InMemoryStorageContainer.create(clock);
    }
}
