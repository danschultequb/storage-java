package qub;

public interface BlobStorageTests
{
    public static void test(TestRunner runner, Function0<? extends BlobStorage> creator)
    {
        PreCondition.assertNotNull(runner, "runner");
        PreCondition.assertNotNull(creator, "creator");

        runner.testGroup(BlobStorage.class, () ->
        {
            runner.testGroup("getBlob(BlobChecksumType,BitArray)", () ->
            {
                final Action3<BlobChecksumType,BitArray,Throwable> getBlobErrorTest = (BlobChecksumType checksumType, BitArray checksumValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlob(checksumType, checksumValue),
                            expected);
                    });
                };

                getBlobErrorTest.run(null, BitArray.create(5), new PreConditionFailure("checksumType cannot be null."));
                getBlobErrorTest.run(BlobChecksumType.MD5, null, new PreConditionFailure("checksumValue cannot be null."));
                getBlobErrorTest.run(BlobChecksumType.MD5, BitArray.create(0), new PreConditionFailure("checksumValue cannot be empty."));

                final Action2<BlobChecksumType,BitArray> getBlobTest = (BlobChecksumType checksumType, BitArray checksumValue) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        final Blob blob = blobStorage.getBlob(checksumType, checksumValue);
                        test.assertNotNull(blob);
                        test.assertSame(blobStorage, blob.getBlobStorage());
                        test.assertEqual(BlobChecksum.create(checksumType, checksumValue), blob.getChecksum());
                    });
                };

                getBlobTest.run(BlobChecksumType.MD5, BitArray.create(5));
            });

            runner.testGroup("getBlob(String,BitArray)", () ->
            {
                final Action3<String,BitArray,Throwable> getBlobErrorTest = (String checksumType, BitArray checksumValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlob(checksumType, checksumValue),
                            expected);
                    });
                };

                getBlobErrorTest.run(null, BitArray.create(3), new PreConditionFailure("checksumType cannot be null."));
                getBlobErrorTest.run("", BitArray.create(4), new PreConditionFailure("checksumType cannot be empty."));
                getBlobErrorTest.run("md5", null, new PreConditionFailure("checksumValue cannot be null."));
                getBlobErrorTest.run("MD5", BitArray.create(0), new PreConditionFailure("checksumValue cannot be empty."));

                final Action2<String,BitArray> getBlobTest = (String checksumType, BitArray checksumValue) ->
                {
                    runner.test("with " + English.andList(Iterable.create(checksumType, checksumValue).map(Strings::escapeAndQuote)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        final Blob blob = blobStorage.getBlob(checksumType, checksumValue);
                        test.assertNotNull(blob);
                        test.assertSame(blobStorage, blob.getBlobStorage());
                        test.assertEqual(BlobChecksum.create(checksumType, checksumValue), blob.getChecksum());
                    });
                };

                getBlobTest.run("md5", BitArray.create(5));
                getBlobTest.run("MD5", BitArray.createFromBitString("01100"));
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

            runner.testGroup("blobExists(BlobChecksumType,BitArray)", () ->
            {
                final Action3<BlobChecksumType,BitArray,Throwable> blobExistsErrorTest = (BlobChecksumType checksumType, BitArray checksumValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.blobExists(checksumType, checksumValue).await(),
                            expected);
                    });
                };

                blobExistsErrorTest.run(null, BitArray.create(3), new PreConditionFailure("checksumType cannot be null."));
                blobExistsErrorTest.run(BlobChecksumType.MD5, null, new PreConditionFailure("checksumValue cannot be null."));
                blobExistsErrorTest.run(BlobChecksumType.MD5, BitArray.create(0), new PreConditionFailure("checksumValue cannot be empty."));

                final Action2<BlobChecksumType,BitArray> blobExistsTest = (BlobChecksumType checksumType, BitArray checksumValue) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertFalse(blobStorage.blobExists(checksumType, checksumValue).await());
                    });
                };

                blobExistsTest.run(BlobChecksumType.MD5, BitArray.createFromBitString("01010101"));
                blobExistsTest.run(BlobChecksumType.MD5, BitArray.createFromBitString("111"));

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3 }).await();

                    test.assertEqual(BlobChecksum.create(BlobChecksumType.MD5, BitArray.createFromHexString("5289DF737DF57326FCDD22597AFB1FAC")), blob.getChecksum());
                    test.assertTrue(blobStorage.blobExists(blob.getChecksum()).await());
                });
            });

            runner.testGroup("blobExists(String,BitArray)", () ->
            {
                final Action3<String,BitArray,Throwable> blobExistsErrorTest = (String checksumType, BitArray checksumValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.blobExists(checksumType, checksumValue).await(),
                            expected);
                    });
                };

                blobExistsErrorTest.run(null, BitArray.create(3), new PreConditionFailure("checksumType cannot be null."));
                blobExistsErrorTest.run("", BitArray.create(4), new PreConditionFailure("checksumType cannot be empty."));
                blobExistsErrorTest.run("md5", null, new PreConditionFailure("checksumValue cannot be null."));
                blobExistsErrorTest.run("MD5", BitArray.create(0), new PreConditionFailure("checksumValue cannot be empty."));

                final Action2<String,BitArray> blobExistsTest = (String checksumType, BitArray checksumValue) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertFalse(blobStorage.blobExists(checksumType, checksumValue).await());
                    });
                };

                blobExistsTest.run("md5", BitArray.createFromBitString("01010101"));
                blobExistsTest.run("MD5", BitArray.createFromBitString("111"));

                runner.test("with existing blob", (Test test) ->
                {
                    final BlobStorage blobStorage = creator.run();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3 }).await();

                    test.assertTrue(blobStorage.blobExists(blob.getChecksum()).await());
                });
            });

            runner.testGroup("getBlobByteCount(BlobChecksumType,BitArray)", () ->
            {
                final Action3<BlobChecksumType,BitArray,Throwable> getBlobByteCountErrorTest = (BlobChecksumType checksumType, BitArray checksumValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlobByteCount(checksumType, checksumValue).await(),
                            expected);
                    });
                };

                getBlobByteCountErrorTest.run(null, BitArray.create(3), new PreConditionFailure("checksumType cannot be null."));
                getBlobByteCountErrorTest.run(BlobChecksumType.MD5, null, new PreConditionFailure("checksumValue cannot be null."));
                getBlobByteCountErrorTest.run(BlobChecksumType.MD5, BitArray.create(0), new PreConditionFailure("checksumValue cannot be empty."));
                getBlobByteCountErrorTest.run(BlobChecksumType.MD5, BitArray.createFromBitString("01010101"), new BlobNotFoundException("Could not find a blob with checksum MD5:55."));
                getBlobByteCountErrorTest.run(BlobChecksumType.MD5, BitArray.createFromBitString("111"), new BlobNotFoundException("Could not find a blob with checksum MD5:E."));
            });

            runner.testGroup("getBlobByteCount(String,BitArray)", () ->
            {
                final Action3<String,BitArray,Throwable> getBlobByteCountErrorTest = (String checksumType, BitArray checksumValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlobByteCount(checksumType, checksumValue).await(),
                            expected);
                    });
                };

                getBlobByteCountErrorTest.run(null, BitArray.create(3), new PreConditionFailure("checksumType cannot be null."));
                getBlobByteCountErrorTest.run("", BitArray.create(4), new PreConditionFailure("checksumType cannot be empty."));
                getBlobByteCountErrorTest.run("md5", null, new PreConditionFailure("checksumValue cannot be null."));
                getBlobByteCountErrorTest.run("MD5", BitArray.create(0), new PreConditionFailure("checksumValue cannot be empty."));
                getBlobByteCountErrorTest.run("md5", BitArray.createFromBitString("01010101"), new BlobNotFoundException("Could not find a blob with checksum md5:55."));
                getBlobByteCountErrorTest.run("MD5", BitArray.createFromBitString("111"), new BlobNotFoundException("Could not find a blob with checksum MD5:E."));
            });

            runner.testGroup("getBlobContents(BlobChecksumType,BitArray)", () ->
            {
                final Action3<BlobChecksumType,BitArray,Throwable> getBlobByteCountErrorTest = (BlobChecksumType checksumType, BitArray checksumValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlobContents(checksumType, checksumValue).await(),
                            expected);
                    });
                };

                getBlobByteCountErrorTest.run(null, BitArray.create(3), new PreConditionFailure("checksumType cannot be null."));
                getBlobByteCountErrorTest.run(BlobChecksumType.MD5, null, new PreConditionFailure("checksumValue cannot be null."));
                getBlobByteCountErrorTest.run(BlobChecksumType.MD5, BitArray.create(0), new PreConditionFailure("checksumValue cannot be empty."));
                getBlobByteCountErrorTest.run(BlobChecksumType.MD5, BitArray.createFromBitString("01010101"), new BlobNotFoundException("Could not find a blob with checksum MD5:55."));
                getBlobByteCountErrorTest.run(BlobChecksumType.MD5, BitArray.createFromBitString("111"), new BlobNotFoundException("Could not find a blob with checksum MD5:E."));
            });

            runner.testGroup("getBlobContents(String,BitArray)", () ->
            {
                final Action3<String,BitArray,Throwable> getBlobByteCountErrorTest = (String checksumType, BitArray checksumValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(checksumType, Strings.escapeAndQuote(checksumValue)), (Test test) ->
                    {
                        final BlobStorage blobStorage = creator.run();
                        test.assertThrows(() -> blobStorage.getBlobContents(checksumType, checksumValue).await(),
                            expected);
                    });
                };

                getBlobByteCountErrorTest.run(null, BitArray.create(3), new PreConditionFailure("checksumType cannot be null."));
                getBlobByteCountErrorTest.run("", BitArray.create(4), new PreConditionFailure("checksumType cannot be empty."));
                getBlobByteCountErrorTest.run("md5", null, new PreConditionFailure("checksumValue cannot be null."));
                getBlobByteCountErrorTest.run("MD5", BitArray.create(0), new PreConditionFailure("checksumValue cannot be empty."));
                getBlobByteCountErrorTest.run("md5", BitArray.createFromBitString("01010101"), new BlobNotFoundException("Could not find a blob with checksum md5:55."));
                getBlobByteCountErrorTest.run("MD5", BitArray.createFromBitString("111"), new BlobNotFoundException("Could not find a blob with checksum MD5:E."));
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
