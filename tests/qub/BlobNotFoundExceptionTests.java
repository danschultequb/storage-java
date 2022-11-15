package qub;

public interface BlobNotFoundExceptionTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(BlobNotFoundException.class, () ->
        {
            runner.testGroup("constructor(String,Blob)", () ->
            {
                runner.test("with null message and non-null Blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobId.create().addElement("a", "b"));

                    final BlobNotFoundException exception = new BlobNotFoundException(null, blob);
                    test.assertNull(exception.getMessage());
                    test.assertSame(blob, exception.getBlob());
                });

                runner.test("with empty message and non-null Blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobId.create().addElement("a", "b"));

                    final BlobNotFoundException exception = new BlobNotFoundException("", blob);
                    test.assertEqual("", exception.getMessage());
                    test.assertSame(blob, exception.getBlob());
                });

                runner.test("with non-empty message and null blob", (Test test) ->
                {
                    final BlobNotFoundException exception = new BlobNotFoundException("hello", null);
                    test.assertEqual("hello", exception.getMessage());
                    test.assertNull(exception.getBlob());
                });

                runner.test("with non-empty message and non-null blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobId.create().addElement("a", "b"));

                    final BlobNotFoundException exception = new BlobNotFoundException("abc", blob);
                    test.assertEqual("abc", exception.getMessage());
                    test.assertSame(blob, exception.getBlob());
                });
            });

            runner.testGroup("constructor(String)", () ->
            {
                runner.test("with null message", (Test test) ->
                {
                    final BlobNotFoundException exception = new BlobNotFoundException((String)null);
                    test.assertNull(exception.getMessage());
                    test.assertNull(exception.getBlob());
                });

                runner.test("with empty message", (Test test) ->
                {
                    final BlobNotFoundException exception = new BlobNotFoundException("");
                    test.assertEqual("", exception.getMessage());
                    test.assertNull(exception.getBlob());
                });

                runner.test("with non-empty message", (Test test) ->
                {
                    final BlobNotFoundException exception = new BlobNotFoundException("hello there");
                    test.assertEqual("hello there", exception.getMessage());
                    test.assertNull(exception.getBlob());
                });
            });

            runner.testGroup("constructor(Blob)", () ->
            {
                runner.test("with null blob", (Test test) ->
                {
                    test.assertThrows(() -> new BlobNotFoundException((Blob)null),
                        new PreConditionFailure("blob cannot be null."));
                });

                runner.test("with non-null blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobId.create().addElement("a", "b"));

                    final BlobNotFoundException exception = new BlobNotFoundException(blob);
                    test.assertEqual("Could not find a blob with the id {\"a\":\"b\"}.", exception.getMessage());
                    test.assertSame(blob, exception.getBlob());
                });
            });
        });
    }
}
