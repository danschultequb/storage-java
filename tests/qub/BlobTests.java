package qub;

public interface BlobTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(Blob.class, () ->
        {
            runner.testGroup("create(BlobStorage,BlobId)", () ->
            {
                runner.test("with null blobStorage", (Test test) ->
                {
                    final BlobStorage blobStorage = null;
                    final BlobId blobId = BlobId.create().addElement("a", "b");

                    test.assertThrows(() -> Blob.create(blobStorage, blobId),
                        new PreConditionFailure("blobStorage cannot be null."));
                });

                runner.test("with null blobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = null;

                    test.assertThrows(() -> Blob.create(blobStorage, blobId),
                        new PreConditionFailure("blobId cannot be null."));
                });

                runner.test("with empty blobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create();

                    test.assertThrows(() -> Blob.create(blobStorage, blobId),
                        new PreConditionFailure("blobId.getElementCount() (0) must be greater than or equal to 1."));
                });

                runner.test("with non-existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");

                    final Blob blob = Blob.create(blobStorage, blobId);
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertSame(blobId, blob.getId());
                });
            });

            runner.testGroup("exists()", () ->
            {
                runner.test("with non-existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob = Blob.create(blobStorage, blobId);

                    test.assertFalse(blob.exists().await());
                });

                runner.test("with existing blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();
                    final Blob blob = blobStorage.createBlob(new byte[0]).await();

                    test.assertTrue(blob.exists().await());
                });
            });

            runner.testGroup("getByteCount()", () ->
            {
                runner.test("with non-existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob = Blob.create(blobStorage, blobId);

                    test.assertThrows(() -> blob.getByteCount().await(),
                        new BlobNotFoundException(blob));
                    test.assertFalse(blob.exists().await());
                });

                runner.test("with existing empty blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();
                    final Blob blob = blobStorage.createBlob(new byte[0]).await();

                    test.assertEqual(0, blob.getByteCount().await());
                });

                runner.test("with existing non-empty blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();
                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2 }).await();

                    test.assertEqual(2, blob.getByteCount().await());
                });
            });

            runner.testGroup("getContents()", () ->
            {
                runner.test("with non-existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob = Blob.create(blobStorage, blobId);

                    test.assertThrows(() -> blob.getContents().await(),
                        new BlobNotFoundException(blob));
                    test.assertFalse(blob.exists().await());
                });

                runner.test("with existing empty blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();
                    final Blob blob = blobStorage.createBlob(new byte[0]).await();

                    try (final ByteReadStream blobContents = blob.getContents().await())
                    {
                        test.assertEqual(new byte[0], blobContents.readAllBytes().await());
                    }
                });

                runner.test("with existing non-empty blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    blobStorage.addBlobIdElementCreatorFunction(ContentLengthBlobIdElementCreator::create).await();
                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2 }).await();

                    try (final ByteReadStream blobContents = blob.getContents().await())
                    {
                        test.assertEqual(new byte[] { 1, 2 }, blobContents.readAllBytes().await());
                    }
                });
            });

            runner.testGroup("equals(Object)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob = blobStorage.getBlob(blobId);

                    test.assertFalse(blob.equals((Object)null));
                });

                runner.test("with non-Blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob = blobStorage.getBlob(blobId);

                    test.assertFalse(blob.equals((Object)"hello"));
                });

                runner.test("with same Blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob = blobStorage.getBlob(blobId);

                    test.assertTrue(blob.equals((Object)blob));
                });

                runner.test("with equal BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob1 = blobStorage.getBlob(blobId);
                    final Blob blob2 = blobStorage.getBlob(blobId);

                    test.assertTrue(blob1.equals((Object)blob2));
                });

                runner.test("with no overlap BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId1 = BlobId.create().addElement("a", "b");
                    final Blob blob1 = blobStorage.getBlob(blobId1);
                    final BlobId blobId2 = BlobId.create().addElement("c", "d");
                    final Blob blob2 = blobStorage.getBlob(blobId2);

                    test.assertFalse(blob1.equals((Object)blob2));
                });

                runner.test("with subset BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId1 = BlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d");
                    final Blob blob1 = blobStorage.getBlob(blobId1);
                    final BlobId blobId2 = BlobId.create().addElement("c", "d");
                    final Blob blob2 = blobStorage.getBlob(blobId2);

                    test.assertFalse(blob1.equals((Object)blob2));
                });

                runner.test("with superset BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId1 = BlobId.create().addElement("a", "b");
                    final Blob blob1 = blobStorage.getBlob(blobId1);
                    final BlobId blobId2 = BlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d");
                    final Blob blob2 = blobStorage.getBlob(blobId2);

                    test.assertFalse(blob1.equals((Object)blob2));
                });
            });

            runner.testGroup("equals(Blob)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob = blobStorage.getBlob(blobId);

                    test.assertFalse(blob.equals((Blob)null));
                });

                runner.test("with same Blob", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob = blobStorage.getBlob(blobId);

                    test.assertTrue(blob.equals(blob));
                });

                runner.test("with different BlobStorage", (Test test) ->
                {
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final BlobStorage blobStorage1 = InMemoryBlobStorage.create();
                    final Blob blob1 = blobStorage1.getBlob(blobId);
                    final BlobStorage blobStorage2 = InMemoryBlobStorage.create();
                    final Blob blob2 = blobStorage2.getBlob(blobId);

                    test.assertFalse(blob1.equals(blob2));
                });

                runner.test("with equal BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob1 = blobStorage.getBlob(blobId);
                    final Blob blob2 = blobStorage.getBlob(blobId);

                    test.assertTrue(blob1.equals(blob2));
                });

                runner.test("with no overlap BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId1 = BlobId.create().addElement("a", "b");
                    final Blob blob1 = blobStorage.getBlob(blobId1);
                    final BlobId blobId2 = BlobId.create().addElement("c", "d");
                    final Blob blob2 = blobStorage.getBlob(blobId2);

                    test.assertFalse(blob1.equals(blob2));
                });

                runner.test("with subset BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId1 = BlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d");
                    final Blob blob1 = blobStorage.getBlob(blobId1);
                    final BlobId blobId2 = BlobId.create().addElement("c", "d");
                    final Blob blob2 = blobStorage.getBlob(blobId2);

                    test.assertFalse(blob1.equals(blob2));
                });

                runner.test("with superset BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = InMemoryBlobStorage.create();
                    final BlobId blobId1 = BlobId.create().addElement("a", "b");
                    final Blob blob1 = blobStorage.getBlob(blobId1);
                    final BlobId blobId2 = BlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d");
                    final Blob blob2 = blobStorage.getBlob(blobId2);

                    test.assertFalse(blob1.equals(blob2));
                });
            });
        });
    }
}
