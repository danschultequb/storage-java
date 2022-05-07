package qub;

public interface InMemoryBlobStorageTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryBlobStorage.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                test.assertNotNull(blobStorage);
                test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
            });

            BlobStorageTests.test(runner, InMemoryBlobStorage::create);
        });
    }
}
