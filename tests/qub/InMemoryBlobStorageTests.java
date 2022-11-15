package qub;

public interface InMemoryBlobStorageTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryBlobStorage.class, () ->
        {
            BlobStorageTests.test(runner, () ->
            {
                final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();
                return blobStorage;
            });

            runner.test("create()", (Test test) ->
            {
                final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                test.assertNotNull(blobStorage);
                test.assertEqual(0, blobStorage.getBlobCount().await());
                test.assertEqual(0, blobStorage.getBlobIdElementCreatorCount());
                test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
            });

            runner.testGroup("addBlobIdElementCreatorFunction(Function0<? extends BlobIdElementCreator>)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    test.assertThrows(() -> blobStorage.addBlobIdElementCreatorFunction(null),
                        new PreConditionFailure("blobIdElementCreatorFunction cannot be null."));
                });

                runner.test("with non-existing creator function and no existing blobs", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();
                    test.assertEqual(0, blobStorage.getBlobCount().await());
                    test.assertEqual(1, blobStorage.getBlobIdElementCreatorCount());
                });

                runner.test("with existing creator function and no existing blobs", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();

                    blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();
                    test.assertEqual(0, blobStorage.getBlobCount().await());
                    test.assertEqual(2, blobStorage.getBlobIdElementCreatorCount());
                });

                runner.test("with non-existing creator function and an existing blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();
                    test.assertEqual(0, blobStorage.getBlobCount().await());
                    test.assertEqual(1, blobStorage.getBlobIdElementCreatorCount());

                    final Blob blob1 = blobStorage.getOrCreateBlob(new byte[0]).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(1, blobStorage.getBlobIdElementCreatorCount());

                    blobStorage.addBlobIdElementCreatorFunction(HashFunctionBlobIdElementCreator::createMD5).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(2, blobStorage.getBlobIdElementCreatorCount());

                    final Blob blob2 = blobStorage.getOrCreateBlob(new byte[0]).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(2, blobStorage.getBlobIdElementCreatorCount());

                    test.assertNotEqual(blob1.getId(), blob2.getId());
                });
            });
        });
    }
}
