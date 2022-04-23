package qub;

public interface StorageContainerTests
{
    public static void test(TestRunner runner, Function1<Clock,? extends StorageContainer> creator)
    {
        PreCondition.assertNotNull(runner, "runner");
        PreCondition.assertNotNull(creator, "creator");

        runner.testGroup(StorageContainer.class, () ->
        {
            runner.testGroup("iterateItems()", () ->
            {
                runner.test("with no items", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();

                    final StorageContainer container = creator.run(clock);
                    
                    final Iterator<? extends StorageItem> items = container.iterateItems();
                    IteratorTests.assertIterator(test, items, false, null);

                    test.assertFalse(items.next());
                    IteratorTests.assertIterator(test, items, true, null);
                });
            });

            runner.testGroup("getItem(String)", () ->
            {
                final Action2<String,Throwable> getItemErrorTest = (String itemId, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(itemId), (Test test) ->
                    {
                        final ManualClock clock = ManualClock.create();

                        final StorageContainer container = creator.run(clock);

                        test.assertThrows(() -> container.getItem(itemId).await(),
                            expected);

                        test.assertEqual(Iterable.create(), container.iterateItems().toList());
                    });
                };

                getItemErrorTest.run(null, new PreConditionFailure("id cannot be null."));
                getItemErrorTest.run("", new NotFoundException("No item found with the id: \"\""));
                getItemErrorTest.run("idontexist", new NotFoundException("No item found with the id: \"idontexist\""));
            });

            runner.test("createItem()", (Test test) ->
            {
                final ManualClock clock = ManualClock.create();

                final StorageContainer container = creator.run(clock);

                final StorageItem item1 = container.createItem().await();
                test.assertNotNull(item1);
                test.assertNotNullAndNotEmpty(item1.getId());
                test.assertEqual(clock.getCurrentDateTime(), item1.getCreatedAt());
                test.assertEqual(item1.getCreatedAt(), item1.getLastModified());
                test.assertNull(item1.getAttributes().await());
                test.assertEqual(item1, container.getItem(item1.getId()).await());
                test.assertEqual(Iterable.create(item1), container.iterateItems().toList());

                final StorageItem item2 = container.createItem().await();
                test.assertNotNull(item2);
                test.assertNotNullAndNotEmpty(item2.getId());
                test.assertEqual(clock.getCurrentDateTime(), item2.getCreatedAt());
                test.assertEqual(item2.getCreatedAt(), item2.getLastModified());
                test.assertNull(item2.getAttributes().await());
                test.assertEqual(item2, container.getItem(item2.getId()).await());
                test.assertEqual(Set.create(item1, item2), container.iterateItems().toSet());

                test.assertNotEqual(item1.getId(), item2.getId());
            });

            runner.testGroup("createItem(StorageItemCreateParameters)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();

                    final StorageContainer container = creator.run(clock);

                    test.assertThrows(() -> container.createItem(null),
                        new PreConditionFailure("parameters cannot be null."));

                    test.assertEqual(Iterable.create(), container.iterateItems().toList());
                });

                runner.test("with empty parameters", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();

                    final StorageContainer container = creator.run(clock);

                    final StorageItem item1 = container.createItem(StorageItemCreateParameters.create()).await();
                    test.assertNotNull(item1);
                    test.assertNotNullAndNotEmpty(item1.getId());
                    test.assertEqual(clock.getCurrentDateTime(), item1.getCreatedAt());
                    test.assertEqual(item1.getCreatedAt(), item1.getLastModified());
                    test.assertNull(item1.getAttributes().await());
                    test.assertEqual(item1, container.getItem(item1.getId()).await());
                    test.assertEqual(Iterable.create(item1), container.iterateItems().toList());

                    final StorageItem item2 = container.createItem(StorageItemCreateParameters.create()).await();
                    test.assertNotNull(item2);
                    test.assertNotNullAndNotEmpty(item2.getId());
                    test.assertEqual(clock.getCurrentDateTime(), item2.getCreatedAt());
                    test.assertEqual(item2.getCreatedAt(), item2.getLastModified());
                    test.assertNull(item2.getAttributes().await());
                    test.assertEqual(item2, container.getItem(item2.getId()).await());
                    test.assertEqual(Set.create(item1, item2), container.iterateItems().toSet());

                    test.assertNotEqual(item1.getId(), item2.getId());
                });

                runner.test("with empty id", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();

                    final StorageContainer container = creator.run(clock);

                    final StorageItem item1 = container.createItem(
                        StorageItemCreateParameters.create()
                            .setId(""))
                        .await();
                    test.assertNotNull(item1);
                    test.assertEqual("", item1.getId());
                    test.assertEqual(clock.getCurrentDateTime(), item1.getCreatedAt());
                    test.assertEqual(item1.getCreatedAt(), item1.getLastModified());
                    test.assertNull(item1.getAttributes().await());
                    test.assertEqual(item1, container.getItem(item1.getId()).await());
                    test.assertEqual(Iterable.create(item1), container.iterateItems().toList());

                    test.assertThrows(() -> container.createItem(
                        StorageItemCreateParameters.create()
                            .setId(""))
                        .await(),
                        new AlreadyExistsException("An item with the id \"\" already exists."));
                    test.assertEqual(Iterable.create(item1), container.iterateItems().toList());
                });

                runner.test("with non-existing id", (Test test) ->
                {
                    final ManualClock clock = ManualClock.create();

                    final StorageContainer container = creator.run(clock);
                    
                    final StorageItem item1 = container.createItem(
                        StorageItemCreateParameters.create()
                            .setId("hello"))
                        .await();
                    test.assertNotNull(item1);
                    test.assertEqual("hello", item1.getId());
                    test.assertEqual(clock.getCurrentDateTime(), item1.getCreatedAt());
                    test.assertEqual(item1.getCreatedAt(), item1.getLastModified());
                    test.assertNull(item1.getAttributes().await());
                    test.assertEqual(item1, container.getItem(item1.getId()).await());
                    test.assertEqual(Iterable.create(item1), container.iterateItems().toList());

                    test.assertThrows(() -> container.createItem(
                        StorageItemCreateParameters.create()
                            .setId("hello"))
                        .await(),
                        new AlreadyExistsException("An item with the id \"hello\" already exists."));
                    test.assertEqual(Iterable.create(item1), container.iterateItems().toList());
                });

                runner.testGroup("with createdAt", () ->
                {
                    runner.test("less than current time", (Test test) ->
                    {
                        final ManualClock clock = ManualClock.create();

                        final StorageContainer container = creator.run(clock);
                        
                        final DateTime createdAt = clock.getCurrentDateTime().minus(Duration.minutes(5));
                        final StorageItem item = container.createItem(
                            StorageItemCreateParameters.create()
                                .setCreatedAt(createdAt))
                            .await();
                        
                        test.assertNotNull(item);
                        test.assertNotNullAndNotEmpty(item.getId());
                        test.assertEqual(createdAt, item.getCreatedAt());
                        test.assertEqual(clock.getCurrentDateTime(), item.getLastModified());
                        test.assertNull(item.getAttributes().await());
                        test.assertEqual(item, container.getItem(item.getId()).await());
                        test.assertEqual(Iterable.create(item), container.iterateItems().toList());
                    });

                    runner.test("equal to current time", (Test test) ->
                    {
                        final ManualClock clock = ManualClock.create();

                        final StorageContainer container = creator.run(clock);
                        
                        final DateTime createdAt = clock.getCurrentDateTime();
                        final StorageItem item = container.createItem(
                            StorageItemCreateParameters.create()
                                .setCreatedAt(createdAt))
                            .await();
                        
                        test.assertNotNull(item);
                        test.assertNotNullAndNotEmpty(item.getId());
                        test.assertEqual(createdAt, item.getCreatedAt());
                        test.assertEqual(createdAt, item.getLastModified());
                        test.assertNull(item.getAttributes().await());
                        test.assertEqual(item, container.getItem(item.getId()).await());
                        test.assertEqual(Iterable.create(item), container.iterateItems().toList());
                    });

                    runner.test("greater than current time", (Test test) ->
                    {
                        final ManualClock clock = ManualClock.create();

                        final StorageContainer container = creator.run(clock);
                        
                        final DateTime createdAt = clock.getCurrentDateTime().plus(Duration.minutes(5));
                        final StorageItem item = container.createItem(
                            StorageItemCreateParameters.create()
                                .setCreatedAt(createdAt))
                            .await();
                        
                        test.assertNotNull(item);
                        test.assertNotNullAndNotEmpty(item.getId());
                        test.assertEqual(createdAt, item.getCreatedAt());
                        test.assertEqual(clock.getCurrentDateTime(), item.getLastModified());
                        test.assertNull(item.getAttributes().await());
                        test.assertEqual(item, container.getItem(item.getId()).await());
                        test.assertEqual(Iterable.create(item), container.iterateItems().toList());
                    });
                });

                runner.testGroup("with lastModified", () ->
                {
                    runner.test("less than current time", (Test test) ->
                    {
                        final ManualClock clock = ManualClock.create();

                        final StorageContainer container = creator.run(clock);
                        
                        final DateTime lastModified = clock.getCurrentDateTime().minus(Duration.minutes(5));
                        final StorageItem item = container.createItem(
                            StorageItemCreateParameters.create()
                                .setLastModified(lastModified))
                            .await();
                        
                        test.assertNotNull(item);
                        test.assertNotNullAndNotEmpty(item.getId());
                        test.assertEqual(clock.getCurrentDateTime(), item.getCreatedAt());
                        test.assertEqual(lastModified, item.getLastModified());
                        test.assertNull(item.getAttributes().await());
                        test.assertEqual(item, container.getItem(item.getId()).await());
                        test.assertEqual(Iterable.create(item), container.iterateItems().toList());
                    });

                    runner.test("equal to current time", (Test test) ->
                    {
                        final ManualClock clock = ManualClock.create();

                        final StorageContainer container = creator.run(clock);
                        
                        final DateTime lastModified = clock.getCurrentDateTime();
                        final StorageItem item = container.createItem(
                            StorageItemCreateParameters.create()
                                .setLastModified(lastModified))
                            .await();
                        
                        test.assertNotNull(item);
                        test.assertNotNullAndNotEmpty(item.getId());
                        test.assertEqual(clock.getCurrentDateTime(), item.getCreatedAt());
                        test.assertEqual(lastModified, item.getLastModified());
                        test.assertNull(item.getAttributes().await());
                        test.assertEqual(item, container.getItem(item.getId()).await());
                        test.assertEqual(Iterable.create(item), container.iterateItems().toList());
                    });

                    runner.test("greater than current time", (Test test) ->
                    {
                        final ManualClock clock = ManualClock.create();

                        final StorageContainer container = creator.run(clock);
                        
                        final DateTime lastModified = clock.getCurrentDateTime().plus(Duration.minutes(5));
                        final StorageItem item = container.createItem(
                            StorageItemCreateParameters.create()
                                .setLastModified(lastModified))
                            .await();
                        
                        test.assertNotNull(item);
                        test.assertNotNullAndNotEmpty(item.getId());
                        test.assertEqual(clock.getCurrentDateTime(), item.getCreatedAt());
                        test.assertEqual(lastModified, item.getLastModified());
                        test.assertNull(item.getAttributes().await());
                        test.assertEqual(item, container.getItem(item.getId()).await());
                        test.assertEqual(Iterable.create(item), container.iterateItems().toList());
                    });
                });
            });
        });
    }
}
