package qub;

public interface BlobStorageTests
{
    public static void test(TestRunner runner, Function0<? extends BlobStorage> creator)
    {
        PreCondition.assertNotNull(runner, "runner");
        PreCondition.assertNotNull(creator, "creator");

        runner.testGroup(BlobStorage.class, () ->
        {
            runner.testGroup("iterateBlobs()", () ->
            {
                runner.test("with no blobs", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Iterator<Blob> blobs = blobStorage.iterateBlobs();
                    IteratorTests.assertIterator(test, blobs, false, null);
                    test.assertEqual(Iterable.create(), blobs.toList());
                });

                runner.test("with one blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    final Blob blob = blobStorage.createBlob(new byte[] { 10, 20 }).await();

                    final Iterator<Blob> blobs = blobStorage.iterateBlobs();
                    IteratorTests.assertIterator(test, blobs, false, null);
                    test.assertEqual(Iterable.create(blob), blobs.toList());
                });
            });

            runner.testGroup("getBlob(BlobId)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertThrows(() -> blobStorage.getBlob(null),
                        new PreConditionFailure("blobId cannot be null."));
                    test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
                });

                runner.test("with non-existing BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    final BlobId blobId = BlobId.create().addElement("a", "b");
                    final Blob blob = blobStorage.getBlob(blobId);
                    test.assertNotNull(blob);
                    test.assertEqual(blobId, blob.getId());
                    test.assertFalse(blob.exists().await());
                    test.assertEqual(0, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
                });

                runner.test("with existing BlobId", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob1 = blobStorage.createBlob(new byte[] { 1, 2, 3 }).await();
                    final BlobId blobId = blob1.getId();

                    final Blob blob2 = blobStorage.getBlob(blobId);
                    test.assertNotNull(blob2);
                    test.assertEqual(blobId, blob2.getId());
                    test.assertTrue(blob2.exists().await());
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob2), blobStorage.iterateBlobs().toList());
                });
            });

            runner.testGroup("blobExists(BlobId)", () ->
            {
                final Action2<BlobId,Throwable> blobExistsErrorTest = (BlobId blobId, Throwable expected) ->
                {
                    runner.test("with " + blobId, (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.blobExists(blobId).await(),
                            expected);
                        test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
                    });
                };

                blobExistsErrorTest.run(null, new PreConditionFailure("blobId cannot be null."));
                blobExistsErrorTest.run(BlobId.create(), new PreConditionFailure("blobId.getElementCount() (0) must be greater than or equal to 1."));

                runner.test("with non-existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertFalse(blobStorage.blobExists(BlobId.create().addElement("a", "b")).await());
                });

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4 }).await();
                    test.assertTrue(blobStorage.blobExists(blob.getId()).await());
                });
            });

            runner.testGroup("getBlobByteCount(BlobId)", () ->
            {
                final Action2<BlobId,Throwable> getBlobByteCountErrorTest = (BlobId blobId, Throwable expected) ->
                {
                    runner.test("with " + blobId, (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlobByteCount(blobId).await(),
                            expected);
                        test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
                    });
                };

                getBlobByteCountErrorTest.run(null, new PreConditionFailure("blobId cannot be null."));
                getBlobByteCountErrorTest.run(BlobId.create(), new PreConditionFailure("blobId.getElementCount() (0) must be greater than or equal to 1."));
                getBlobByteCountErrorTest.run(BlobId.create().addElement("a", "b"), new BlobNotFoundException("Could not find a blob with the id {\"a\":\"b\"}."));

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4 }).await();
                    test.assertEqual(4, blobStorage.getBlobByteCount(blob.getId()).await());
                });
            });

            runner.testGroup("getBlobContents(BlobId)", () ->
            {
                final Action2<BlobId,Throwable> getBlobContentsErrorTest = (BlobId blobId, Throwable expected) ->
                {
                    runner.test("with " + blobId, (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlobContents(blobId).await(),
                            expected);
                        test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
                    });
                };

                getBlobContentsErrorTest.run(null, new PreConditionFailure("blobId cannot be null."));
                getBlobContentsErrorTest.run(BlobId.create(), new PreConditionFailure("blobId.getElementCount() (0) must be greater than or equal to 1."));
                getBlobContentsErrorTest.run(BlobId.create().addElement("a", "b"), new BlobNotFoundException("Could not find a blob with the id {\"a\":\"b\"}."));

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4 }).await();
                    try (final ByteReadStream blobContents = blobStorage.getBlobContents(blob.getId()).await())
                    {
                        test.assertEqual(new byte[] { 1, 2, 3, 4 }, blobContents.readAllBytes().await());
                    }
                });
            });

            runner.testGroup("createBlob(byte[])", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    test.assertThrows(() -> blobStorage.createBlob((byte[])null),
                        new PreConditionFailure("blobContents cannot be null."));

                    test.assertEqual(0, blobStorage.getBlobCount().await());
                });

                runner.test("with empty contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob = blobStorage.createBlob(new byte[0]).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[0],
                        blob.getContents().await()
                            .readAllBytes().await());
                });

                runner.test("with non-empty contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4 }).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[] { 1, 2, 3, 4 },
                        blob.getContents().await()
                            .readAllBytes().await());
                });

                runner.test("with already existing contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob = blobStorage.createBlob(new byte[0]).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());

                    test.assertThrows(() -> blobStorage.createBlob(new byte[0]).await(),
                        new BlobAlreadyExistsException(blob));
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[0],
                        blob.getContents().await()
                            .readAllBytes().await());
                });
            });

            runner.testGroup("createBlob(ByteReadStream)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    test.assertThrows(() -> blobStorage.createBlob((ByteReadStream)null),
                        new PreConditionFailure("blobContents cannot be null."));

                    test.assertEqual(0, blobStorage.getBlobCount().await());
                });

                runner.test("with empty contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob;
                    try (final InMemoryByteStream blobContents = InMemoryByteStream.create().endOfStream())
                    {
                        blob = blobStorage.createBlob(blobContents).await();
                    }
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[0],
                        blob.getContents().await()
                            .readAllBytes().await());
                });

                runner.test("with non-empty contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob;
                    try (final InMemoryByteStream blobContents = InMemoryByteStream.create(new byte[] { 1, 2, 3, 4 }).endOfStream())
                    {
                        blob = blobStorage.createBlob(blobContents).await();
                    }
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[] { 1, 2, 3, 4 },
                        blob.getContents().await()
                            .readAllBytes().await());
                });

                runner.test("with already existing contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob = blobStorage.createBlob(new byte[0]).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());

                    try (final InMemoryByteStream blobContents = InMemoryByteStream.create().endOfStream())
                    {
                        test.assertThrows(() -> blobStorage.createBlob(blobContents).await(),
                            new BlobAlreadyExistsException(blob));
                    }
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[0],
                        blob.getContents().await()
                            .readAllBytes().await());
                });
            });

            runner.testGroup("getOrCreateBlob(byte[])", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    test.assertThrows(() -> blobStorage.getOrCreateBlob((byte[])null),
                        new PreConditionFailure("blobContents cannot be null."));

                    test.assertEqual(0, blobStorage.getBlobCount().await());
                });

                runner.test("with empty contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob = blobStorage.getOrCreateBlob(new byte[0]).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[0],
                        blob.getContents().await()
                            .readAllBytes().await());
                });

                runner.test("with non-empty contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob = blobStorage.getOrCreateBlob(new byte[] { 1, 2, 3, 4 }).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[] { 1, 2, 3, 4 },
                        blob.getContents().await()
                            .readAllBytes().await());
                });

                runner.test("with already existing contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob1 = blobStorage.getOrCreateBlob(new byte[0]).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());

                    final Blob blob2 = blobStorage.getOrCreateBlob(new byte[0]).await();
                    test.assertNotNull(blob2);
                    test.assertEqual(blob1, blob2);
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob1), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[0],
                        blob2.getContents().await()
                            .readAllBytes().await());
                });
            });

            runner.testGroup("getOrCreateBlob(ByteReadStream)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    test.assertThrows(() -> blobStorage.getOrCreateBlob((ByteReadStream)null),
                        new PreConditionFailure("blobContents cannot be null."));

                    test.assertEqual(0, blobStorage.getBlobCount().await());
                });

                runner.test("with empty contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob;
                    try (final InMemoryByteStream blobContents = InMemoryByteStream.create().endOfStream())
                    {
                        blob = blobStorage.getOrCreateBlob(blobContents).await();
                    }
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[0],
                        blob.getContents().await()
                            .readAllBytes().await());
                });

                runner.test("with non-empty contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob;
                    try (final InMemoryByteStream blobContents = InMemoryByteStream.create(new byte[] { 1, 2, 3, 4 }).endOfStream())
                    {
                        blob = blobStorage.getOrCreateBlob(blobContents).await();
                    }
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[] { 1, 2, 3, 4 },
                        blob.getContents().await()
                            .readAllBytes().await());
                });

                runner.test("with already existing contents", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertEqual(0, blobStorage.getBlobCount().await());

                    final Blob blob1 = blobStorage.getOrCreateBlob(new byte[0]).await();
                    test.assertEqual(1, blobStorage.getBlobCount().await());

                    final Blob blob2;
                    try (final InMemoryByteStream blobContents = InMemoryByteStream.create().endOfStream())
                    {
                        blob2 = blobStorage.getOrCreateBlob(blobContents).await();
                    }
                    test.assertEqual(blob1, blob2);
                    test.assertEqual(1, blobStorage.getBlobCount().await());
                    test.assertEqual(Iterable.create(blob1), blobStorage.iterateBlobs().toList());

                    test.assertEqual(
                        new byte[0],
                        blob1.getContents().await()
                            .readAllBytes().await());
                });
            });
        });
    }
}
