package qub;

public interface InMemoryStorageItemTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryStorageItem.class, () ->
        {
            runner.testGroup("create(InMemoryStorageContainer,String)", () ->
            {
                runner.test("with null container", (Test test) ->
                {
                    final InMemoryStorageContainer container = null;
                    final String id = "fake-id";
                    test.assertThrows(() -> InMemoryStorageItem.create(container, id),
                        new PreConditionFailure("container cannot be null."));
                });

                runner.test("with null id", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    final String id = null;
                    test.assertThrows(() -> InMemoryStorageItem.create(container, id),
                        new PreConditionFailure("id cannot be null."));
                });

                runner.test("with empty id", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    final String id = "";
                    final InMemoryStorageItem item = InMemoryStorageItem.create(container, id);
                    test.assertNotNull(item);
                    test.assertSame(container, item.getContainer());
                    test.assertEqual(id, item.getId());
                    test.assertNull(item.getAttributes().await());
                    test.assertEqual(clock.getCurrentDateTime(), item.getCreatedAt());
                    test.assertEqual(clock.getCurrentDateTime(), item.getLastModified());
                });

                runner.test("with non-empty id", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    final String id = "12345";
                    final InMemoryStorageItem item = InMemoryStorageItem.create(container, id);
                    test.assertNotNull(item);
                    test.assertSame(container, item.getContainer());
                    test.assertEqual(id, item.getId());
                    test.assertNull(item.getAttributes().await());
                    test.assertEqual(clock.getCurrentDateTime(), item.getCreatedAt());
                    test.assertEqual(clock.getCurrentDateTime(), item.getLastModified());
                });
            });

            runner.testGroup("setCreatedAt(DateTime)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    final String id = "abc";
                    final InMemoryStorageItem item = InMemoryStorageItem.create(container, id);

                    test.assertThrows(() -> item.setCreatedAt(null),
                        new PreConditionFailure("createdAt cannot be null."));

                    test.assertEqual(clock.getCurrentDateTime(), item.getCreatedAt());
                    test.assertEqual(clock.getCurrentDateTime(), item.getLastModified());
                });

                runner.test("with non-null", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    final String id = "abc";
                    final InMemoryStorageItem item = InMemoryStorageItem.create(container, id);

                    final InMemoryStorageItem setCreatedAtResult = item.setCreatedAt(DateTime.create(1, 2, 3));
                    test.assertSame(item, setCreatedAtResult);
                    test.assertEqual(DateTime.create(1, 2, 3), item.getCreatedAt());
                    test.assertEqual(clock.getCurrentDateTime(), item.getLastModified());
                });
            });

            runner.testGroup("setLastModified(DateTime)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    final String id = "abc";
                    final InMemoryStorageItem item = InMemoryStorageItem.create(container, id);

                    test.assertThrows(() -> item.setLastModified(null),
                        new PreConditionFailure("lastModified cannot be null."));

                    test.assertEqual(clock.getCurrentDateTime(), item.getCreatedAt());
                    test.assertEqual(clock.getCurrentDateTime(), item.getLastModified());
                });

                runner.test("with non-null", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    final String id = "abc";
                    final InMemoryStorageItem item = InMemoryStorageItem.create(container, id);

                    final InMemoryStorageItem setLastModifiedResult = item.setLastModified(DateTime.create(1, 2, 3));
                    test.assertSame(item, setLastModifiedResult);
                    test.assertEqual(DateTime.create(1, 2, 3), item.getLastModified());
                    test.assertEqual(clock.getCurrentDateTime(), item.getCreatedAt());
                });
            });

            runner.testGroup("setAttributes(StorageAttributes)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    final String id = "abc";
                    final InMemoryStorageItem item = InMemoryStorageItem.create(container, id);

                    test.assertThrows(() -> item.setAttributes(null),
                        new PreConditionFailure("attributes cannot be null."));

                    test.assertNull(item.getAttributes().await());
                });

                runner.test("with non-null", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();
                    final DateTime startTime = clock.getCurrentDateTime();
                    final InMemoryStorageContainer container = InMemoryStorageContainer.create(clock);
                    final String id = "abc";
                    final InMemoryStorageItem item = InMemoryStorageItem.create(container, id);

                    clock.advance(Duration.minutes(1)).await();

                    final InMemoryStorageAttributes attributes = InMemoryStorageAttributes.create();
                    test.assertNull(item.setAttributes(attributes).await());

//                    test.assertEqual(attributes, item.getAttributes().await());
//                    test.assertNotSame(attributes, item.getAttributes().await());
                    test.assertEqual(startTime, item.getCreatedAt());
//                    test.assertEqual(clock.getCurrentDateTime(), item.getLastModified());
//                    test.assertEqual(clock.getCurrentDateTime(), item.getAttributes().await().getTimestamp());
                });
            });
        });
    }
}
