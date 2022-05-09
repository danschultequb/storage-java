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
                    final Blob blob = blobStorage.getBlob("a", BitArray.createFromBitString("1"));

                    final BlobNotFoundException exception = new BlobNotFoundException(null, blob);
                    test.assertNull(exception.getMessage());
                    test.assertSame(blob, exception.getNotFoundBlob());
                });

                runner.test("with empty message and non-null Blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("a", BitArray.createFromBitString("1"));

                    final BlobNotFoundException exception = new BlobNotFoundException("", blob);
                    test.assertEqual("", exception.getMessage());
                    test.assertSame(blob, exception.getNotFoundBlob());
                });

                runner.test("with non-empty message and null blob", (Test test) ->
                {
                    final BlobNotFoundException exception = new BlobNotFoundException("hello", null);
                    test.assertEqual("hello", exception.getMessage());
                    test.assertNull(exception.getNotFoundBlob());
                });

                runner.test("with non-empty message and non-null blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("a", BitArray.createFromBitString("1"));

                    final BlobNotFoundException exception = new BlobNotFoundException("abc", blob);
                    test.assertEqual("abc", exception.getMessage());
                    test.assertSame(blob, exception.getNotFoundBlob());
                });
            });

            runner.testGroup("constructor(String)", () ->
            {
                runner.test("with null message", (Test test) ->
                {
                    final BlobNotFoundException exception = new BlobNotFoundException((String)null);
                    test.assertNull(exception.getMessage());
                    test.assertNull(exception.getNotFoundBlob());
                });

                runner.test("with empty message", (Test test) ->
                {
                    final BlobNotFoundException exception = new BlobNotFoundException("");
                    test.assertEqual("", exception.getMessage());
                    test.assertNull(exception.getNotFoundBlob());
                });

                runner.test("with non-empty message", (Test test) ->
                {
                    final BlobNotFoundException exception = new BlobNotFoundException("hello there");
                    test.assertEqual("hello there", exception.getMessage());
                    test.assertNull(exception.getNotFoundBlob());
                });
            });

            runner.testGroup("constructor(Blob)", () ->
            {
                runner.test("with null blob", (Test test) ->
                {
                    test.assertThrows(() -> new BlobNotFoundException((Blob)null),
                        new PreConditionFailure("notFoundBlob cannot be null."));
                });

                runner.test("with non-null blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("a", BitArray.createFromBitString("1"));

                    final BlobNotFoundException exception = new BlobNotFoundException(blob);
                    test.assertEqual("Could not find a blob with checksum a:8.", exception.getMessage());
                    test.assertSame(blob, exception.getNotFoundBlob());
                });
            });
        });
    }
}
