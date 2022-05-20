package qub;

public interface BlobStorageTests
{
    public static void test(TestRunner runner, Function0<? extends BlobStorage> creator)
    {
        PreCondition.assertNotNull(runner, "runner");
        PreCondition.assertNotNull(creator, "creator");

        runner.testGroup(BlobStorage.class, () ->
        {
            runner.testGroup("getBlob(BlobChecksum)", () ->
            {
                final Action2<BlobChecksum,Throwable> getBlobErrorTest = (BlobChecksum checksum, Throwable expected) ->
                {
                    runner.test("with " + checksum, (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlob(checksum),
                            expected);
                    });
                };

                getBlobErrorTest.run(null, new PreConditionFailure("checksum cannot be null."));

                final Action1<BlobChecksum> getBlobTest = (BlobChecksum checksum) ->
                {
                    runner.test("with " + checksum, (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        final Blob blob = blobStorage.getBlob(checksum);
                        test.assertNotNull(blob);
                        test.assertSame(blobStorage, blob.getBlobStorage());
                        test.assertEqual(checksum, blob.getChecksum());
                    });
                };

                getBlobTest.run(BlobChecksum.create("md5", BitArray.create(5)));
                getBlobTest.run(BlobChecksum.create("MD5", BitArray.createFromBitString("01100")));
            });

            runner.testGroup("iterateBlobs()", () ->
            {
                runner.test("with no blobs", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Iterator<Blob> blobs = blobStorage.iterateBlobs();
                    test.assertNotNull(blobs);
                    test.assertEqual(Iterable.create(), blobs.toList());
                });
            });

            runner.testGroup("blobExists(BlobChecksum)", () ->
            {
                final Action2<BlobChecksum,Throwable> blobExistsErrorTest = (BlobChecksum checksum, Throwable expected) ->
                {
                    runner.test("with " + checksum, (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.blobExists(checksum).await(),
                            expected);
                    });
                };

                blobExistsErrorTest.run(null, new PreConditionFailure("checksum cannot be null."));

                final Action1<BlobChecksum> blobExistsTest = (BlobChecksum checksum) ->
                {
                    runner.test("with " + checksum, (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertFalse(blobStorage.blobExists(checksum).await());
                    });
                };

                blobExistsTest.run(BlobChecksum.create(BlobChecksumType.MD5, BitArray.createFromBitString("01010101")));
                blobExistsTest.run(BlobChecksum.create(BlobChecksumType.MD5, BitArray.createFromBitString("111")));

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3 }).await();

                    test.assertEqual(BlobChecksum.create(BlobChecksumType.MD5, BitArray.createFromHexString("5289DF737DF57326FCDD22597AFB1FAC")), blob.getChecksum());
                    test.assertTrue(blobStorage.blobExists(blob.getChecksum()).await());
                });
            });

            runner.testGroup("getBlobByteCount(BlobChecksum)", () ->
            {
                final Action2<BlobChecksum,Throwable> getBlobByteCountErrorTest = (BlobChecksum checksum, Throwable expected) ->
                {
                    runner.test("with " + checksum, (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlobByteCount(checksum).await(),
                            expected);
                    });
                };

                getBlobByteCountErrorTest.run(null, new PreConditionFailure("checksum cannot be null."));
                getBlobByteCountErrorTest.run(BlobChecksum.create(BlobChecksumType.MD5, BitArray.createFromBitString("01010101")), new BlobNotFoundException("Could not find a blob with checksum MD5:55."));
                getBlobByteCountErrorTest.run(BlobChecksum.create(BlobChecksumType.MD5, BitArray.createFromBitString("111")), new BlobNotFoundException("Could not find a blob with checksum MD5:E."));
            });

            runner.testGroup("getBlobContents(BlobChecksum)", () ->
            {
                final Action2<BlobChecksum,Throwable> getBlobByteCountErrorTest = (BlobChecksum checksum, Throwable expected) ->
                {
                    runner.test("with " + checksum, (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlobContents(checksum).await(),
                            expected);
                    });
                };

                getBlobByteCountErrorTest.run(null, new PreConditionFailure("checksum cannot be null."));
                getBlobByteCountErrorTest.run(BlobChecksum.create(BlobChecksumType.MD5, BitArray.createFromBitString("01010101")), new BlobNotFoundException("Could not find a blob with checksum MD5:55."));
                getBlobByteCountErrorTest.run(BlobChecksum.create(BlobChecksumType.MD5, BitArray.createFromBitString("111")), new BlobNotFoundException("Could not find a blob with checksum MD5:E."));
            });

            runner.testGroup("createBlob(byte[])", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertThrows(() -> blobStorage.createBlob((byte[])null),
                        new PreConditionFailure("blobContents cannot be null."));
                    test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
                });

                runner.test("with empty", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(new byte[0]).await();
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual(BlobChecksum.create("MD5", BitArray.createFromHexString("D41D8CD98F00B204E9800998ECF8427E")), blob.getChecksum());
                    test.assertEqual(0, blob.getByteCount().await());
                    test.assertEqual(new byte[0], blob.getContents().await().readAllBytes().await());

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });

                runner.test("with non-empty", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4, 5 }).await();
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual(BlobChecksum.create("MD5", BitArray.createFromHexString("7CFDD07889B3295D6A550914AB35E068")), blob.getChecksum());
                    test.assertEqual(5, blob.getByteCount().await());
                    test.assertEqual(new byte[] { 1, 2, 3, 4, 5 }, blob.getContents().await().readAllBytes().await());

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4, 5 }).await();

                    test.assertThrows(() -> blobStorage.createBlob(new byte[] { 1, 2, 3, 4, 5 }).await(),
                        new BlobAlreadyExistsException(blob));

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });
            });

            runner.testGroup("createBlob(ByteReadStream)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertThrows(() -> blobStorage.createBlob((ByteReadStream)null),
                        new PreConditionFailure("blobContents cannot be null."));
                    test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
                });

                runner.test("with empty", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(InMemoryByteStream.create().endOfStream()).await();
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual(BlobChecksum.create("MD5", BitArray.createFromHexString("D41D8CD98F00B204E9800998ECF8427E")), blob.getChecksum());
                    test.assertEqual(0, blob.getByteCount().await());
                    test.assertEqual(new byte[0], blob.getContents().await().readAllBytes().await());

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });

                runner.test("with non-empty", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(InMemoryByteStream.create(new byte[] { 1, 2, 3, 4, 5 }).endOfStream()).await();
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual(BlobChecksum.create("MD5", BitArray.createFromHexString("7CFDD07889B3295D6A550914AB35E068")), blob.getChecksum());
                    test.assertEqual(5, blob.getByteCount().await());
                    test.assertEqual(new byte[] { 1, 2, 3, 4, 5 }, blob.getContents().await().readAllBytes().await());

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4, 5 }).await();

                    test.assertThrows(() -> blobStorage.createBlob(InMemoryByteStream.create(new byte[] { 1, 2, 3, 4, 5 }).endOfStream()).await(),
                        new BlobAlreadyExistsException(blob));

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });
            });

            runner.testGroup("getOrCreateBlob(byte[])", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertThrows(() -> blobStorage.getOrCreateBlob((byte[])null),
                        new PreConditionFailure("blobContents cannot be null."));
                    test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
                });

                runner.test("with empty", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.getOrCreateBlob(new byte[0]).await();
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual(BlobChecksum.create("MD5", BitArray.createFromHexString("D41D8CD98F00B204E9800998ECF8427E")), blob.getChecksum());
                    test.assertEqual(0, blob.getByteCount().await());
                    test.assertEqual(new byte[0], blob.getContents().await().readAllBytes().await());

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });

                runner.test("with non-empty", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.getOrCreateBlob(new byte[] { 1, 2, 3, 4, 5 }).await();
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual(BlobChecksum.create("MD5", BitArray.createFromHexString("7CFDD07889B3295D6A550914AB35E068")), blob.getChecksum());
                    test.assertEqual(5, blob.getByteCount().await());
                    test.assertEqual(new byte[] { 1, 2, 3, 4, 5 }, blob.getContents().await().readAllBytes().await());

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4, 5 }).await();

                    final Blob blob2 = blobStorage.getOrCreateBlob(new byte[] { 1, 2, 3, 4, 5 }).await();
                    test.assertEqual(blob, blob2);

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });
            });

            runner.testGroup("getOrCreateBlob(ByteReadStream)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();
                    test.assertThrows(() -> blobStorage.getOrCreateBlob((ByteReadStream)null),
                        new PreConditionFailure("blobContents cannot be null."));
                    test.assertEqual(Iterable.create(), blobStorage.iterateBlobs().toList());
                });

                runner.test("with empty", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.getOrCreateBlob(InMemoryByteStream.create().endOfStream()).await();
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual(BlobChecksum.create("MD5", BitArray.createFromHexString("D41D8CD98F00B204E9800998ECF8427E")), blob.getChecksum());
                    test.assertEqual(0, blob.getByteCount().await());
                    test.assertEqual(new byte[0], blob.getContents().await().readAllBytes().await());

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });

                runner.test("with non-empty", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.getOrCreateBlob(InMemoryByteStream.create(new byte[] { 1, 2, 3, 4, 5 }).endOfStream()).await();
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual(BlobChecksum.create("MD5", BitArray.createFromHexString("7CFDD07889B3295D6A550914AB35E068")), blob.getChecksum());
                    test.assertEqual(5, blob.getByteCount().await());
                    test.assertEqual(new byte[] { 1, 2, 3, 4, 5 }, blob.getContents().await().readAllBytes().await());

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4, 5 }).await();

                    final Blob blob2 = blobStorage.getOrCreateBlob(InMemoryByteStream.create(new byte[] { 1, 2, 3, 4, 5 }).endOfStream()).await();
                    test.assertEqual(blob, blob2);

                    test.assertEqual(Iterable.create(blob), blobStorage.iterateBlobs().toList());
                });
            });
        });
    }
}
