package qub;

public interface BlobIdTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(MutableBlobId.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                final MutableBlobId blobId = BlobId.create();
                test.assertNotNull(blobId);
                test.assertEqual(0, blobId.getElementCount());
            });

            runner.testGroup("toString(BlobId)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    test.assertThrows(() -> BlobId.toString(null),
                        new PreConditionFailure("blobId cannot be null."));
                });
            });

            runner.testGroup("equals(BlobId,Object)", () ->
            {
                runner.test("with null BlobId", (Test test) ->
                {
                    test.assertThrows(() -> BlobId.equals(null, "hello"),
                        new PreConditionFailure("blobId cannot be null."));
                });
            });

            runner.testGroup("hashCode(BlobId)", () ->
            {
                runner.test("with null BlobId", (Test test) ->
                {
                    test.assertThrows(() -> BlobId.hashCode(null),
                        new PreConditionFailure("blobId cannot be null."));
                });
            });
        });
    }

    public static void test(TestRunner runner, Function0<? extends BlobId> creator)
    {
        runner.testGroup(BlobId.class, () ->
        {
            runner.testGroup("containsElementType(String)", () ->
            {
                final Action2<String,Throwable> containsElementTypeErrorTest = (String blobIdElementType, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(blobIdElementType), (Test test) ->
                    {
                        final BlobId blobId = creator.run();
                        test.assertThrows(() -> blobId.containsElementType(blobIdElementType),
                            expected);
                        test.assertEqual(0, blobId.getElementCount());
                    });
                };

                containsElementTypeErrorTest.run(null, new PreConditionFailure("blobIdElementType cannot be null."));
                containsElementTypeErrorTest.run("", new PreConditionFailure("blobIdElementType cannot be empty."));
            });

            runner.testGroup("iterateElementTypes()", () ->
            {
                runner.test("with empty", (Test test) ->
                {
                    final BlobId blobId = creator.run();
                    test.assertEqual(Iterable.create(), blobId.iterateElementTypes().toList());
                });
            });

            runner.testGroup("containsElement(BlobIdElement)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobId blobId = creator.run();
                    test.assertThrows(() -> blobId.containsElement(null),
                        new PreConditionFailure("blobIdElement cannot be null."));
                });
            });

            runner.testGroup("iterateElements()", () ->
            {
                runner.test("with empty", (Test test) ->
                {
                    final BlobId blobId = creator.run();
                    test.assertEqual(Iterable.create(), blobId.iterateElements().toList());
                });
            });

            runner.testGroup("getElement(String)", () ->
            {
                final Action2<String,Throwable> getElementErrorTest = (String blobIdElementType, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(blobIdElementType), (Test test) ->
                    {
                        final BlobId blobId = creator.run();
                        test.assertThrows(() -> blobId.getElement(blobIdElementType).await(),
                            expected);
                        test.assertEqual(0, blobId.getElementCount());
                    });
                };

                getElementErrorTest.run(null, new PreConditionFailure("blobIdElementType cannot be null."));
                getElementErrorTest.run("", new PreConditionFailure("blobIdElementType cannot be empty."));
                getElementErrorTest.run("notFoundBlobIdElementType", new NotFoundException("Could not find the blob id element type \"notFoundBlobIdElementType\" in this BlobId."));
            });
        });
    }
}
