package qub;

public interface StorageItemCreateParametersTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(StorageItemCreateParameters.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                final StorageItemCreateParameters parameters = StorageItemCreateParameters.create();
                test.assertNotNull(parameters);
                test.assertNull(parameters.getId());
                test.assertNull(parameters.getCreatedAt());
                test.assertNull(parameters.getLastModified());
                test.assertNull(parameters.getAttributes());
            });

            runner.testGroup("setId(String)", () ->
            {
                final Action2<String,Throwable> setIdErrorTest = (String id, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(id), (Test test) ->
                    {
                        final StorageItemCreateParameters parameters = StorageItemCreateParameters.create();

                        test.assertThrows(() -> parameters.setId(id),
                            expected);
                        
                        test.assertNull(parameters.getId());
                    });
                };

                setIdErrorTest.run(null, new PreConditionFailure("id cannot be null."));
                
                final Action1<String> setIdTest = (String id) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(id), (Test test) ->
                    {
                        final StorageItemCreateParameters parameters = StorageItemCreateParameters.create();

                        final StorageItemCreateParameters setIdResult = parameters.setId(id);
                        test.assertSame(parameters, setIdResult);
                        test.assertEqual(id, parameters.getId());
                    });
                };

                setIdTest.run("");
                setIdTest.run("1251325");
                setIdTest.run("hello there");
                setIdTest.run("configuration.json");
            });

            runner.testGroup("setCreatedAt(DateTime)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final StorageItemCreateParameters parameters = StorageItemCreateParameters.create();
                    
                    test.assertThrows(() -> parameters.setCreatedAt(null),
                        new PreConditionFailure("createdAt cannot be null."));

                    test.assertNull(parameters.getCreatedAt());
                });

                runner.test("with non-null", (Test test) ->
                {
                    final StorageItemCreateParameters parameters = StorageItemCreateParameters.create();

                    final StorageItemCreateParameters setCreatedAtResult = parameters.setCreatedAt(DateTime.create(1, 2, 3));
                    test.assertSame(parameters, setCreatedAtResult);

                    test.assertEqual(DateTime.create(1, 2, 3), parameters.getCreatedAt());
                });
            });

            runner.testGroup("setLastModified(DateTime)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final StorageItemCreateParameters parameters = StorageItemCreateParameters.create();
                    
                    test.assertThrows(() -> parameters.setLastModified(null),
                        new PreConditionFailure("lastModified cannot be null."));

                    test.assertNull(parameters.getLastModified());
                });

                runner.test("with non-null", (Test test) ->
                {
                    final StorageItemCreateParameters parameters = StorageItemCreateParameters.create();

                    final StorageItemCreateParameters setLastModifiedResult = parameters.setLastModified(DateTime.create(1, 2, 3));
                    test.assertSame(parameters, setLastModifiedResult);

                    test.assertEqual(DateTime.create(1, 2, 3), parameters.getLastModified());
                });
            });

            runner.testGroup("setAttributes(StorageItemAttributes)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final StorageItemCreateParameters parameters = StorageItemCreateParameters.create();

                    test.assertThrows(() -> parameters.setAttributes(null),
                        new PreConditionFailure("attributes cannot be null."));

                    test.assertNull(parameters.getAttributes());
                });

                runner.test("with non-null", (Test test) ->
                {
                    final StorageItemCreateParameters parameters = StorageItemCreateParameters.create();

                    final MutableStorageItemAttributes attributes = StorageItemAttributes.create()
                        .setString("hello", "there");
                    final StorageItemCreateParameters setAttributesResult = parameters.setAttributes(attributes);
                    test.assertSame(parameters, setAttributesResult);

                    test.assertEqual(attributes, parameters.getAttributes());
                });
            });
        });
    }
}
