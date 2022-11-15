package qub;

public interface BlobAlreadyExistsExceptionTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(BlobAlreadyExistsException.class, () ->
        {
            runner.test("constructor()", (Test test) ->
            {
                final BlobAlreadyExistsException exception = new BlobAlreadyExistsException();
                test.assertEqual("A blob already exists with the provided contents.", exception.getMessage());
                test.assertNull(exception.getAlreadyExistingBlob());
            });

            runner.testGroup("constructor(Blob)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobAlreadyExistsException exception = new BlobAlreadyExistsException(null);
                    test.assertEqual("A blob already exists with the provided contents.", exception.getMessage());
                    test.assertNull(exception.getAlreadyExistingBlob());
                });

                runner.test("with non-null", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobId.create().addElement("a", "b"));

                    final BlobAlreadyExistsException exception = new BlobAlreadyExistsException(blob);
                    test.assertEqual("A blob already exists with the provided contents.", exception.getMessage());
                    test.assertSame(blob, exception.getAlreadyExistingBlob());
                });
            });
        });
    }
}
