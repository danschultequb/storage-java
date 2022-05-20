package qub;

public interface BlobAlreadyExistsExceptionTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(BlobAlreadyExistsException.class, () ->
        {
            runner.testGroup("constructor(Blob)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    test.assertThrows(() -> new BlobAlreadyExistsException(null),
                        new PreConditionFailure("alreadyExistingBlob cannot be null."));
                });

                runner.test("with non-null", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("a", BitArray.createFromBitString("1")));

                    final BlobAlreadyExistsException exception = new BlobAlreadyExistsException(blob);
                    test.assertEqual("A blob already exists for the provided contents.", exception.getMessage());
                    test.assertSame(blob, exception.getAlreadyExistingBlob());
                });
            });
        });
    }
}
