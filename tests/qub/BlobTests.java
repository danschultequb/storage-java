package qub;

public interface BlobTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(Blob.class, () ->
        {
            runner.testGroup("create(BlobStorage,BlobChecksum)", () ->
            {
                runner.test("with null BlobStorage", (Test test) ->
                {
                    test.assertThrows(() -> Blob.create(null, BlobChecksum.create("md5", BitArray.createFromBitString("1"))),
                        new PreConditionFailure("blobStorage cannot be null."));
                });

                runner.test("with null checksum", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    test.assertThrows(() -> Blob.create(blobStorage, null),
                        new PreConditionFailure("checksum cannot be null."));
                });

                runner.test("with valid arguments", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = Blob.create(blobStorage, BlobChecksum.create("spam", BitArray.createFromBitString("101010")));
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual(BlobChecksum.create("spam", BitArray.createFromBitString("101010")), blob.getChecksum());
                });
            });

            runner.testGroup("exists()", () ->
            {
                runner.test("when blob doesn't exist", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("101010")));
                    test.assertFalse(blob.exists().await());
                });

                runner.test("when blob exists", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    final Blob blob = blobStorage.createBlob(new byte[0]).await();
                    test.assertTrue(blob.exists().await());
                });
            });

            runner.testGroup("getByteCount()", () ->
            {
                runner.test("when blob doesn't exist", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("101010")));
                    test.assertThrows(() -> blob.getByteCount().await(),
                        new BlobNotFoundException(blob));
                });

                runner.test("when blob exists and is empty", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    final Blob blob = blobStorage.createBlob(new byte[0]).await();
                    test.assertEqual(0L, blob.getByteCount().await());
                });

                runner.test("when blob exists and is non-empty", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4 }).await();
                    test.assertEqual(4L, blob.getByteCount().await());
                });
            });

            runner.testGroup("getContents()", () ->
            {
                runner.test("when blob doesn't exist", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("101010")));
                    test.assertThrows(() -> blob.getContents().await(),
                        new BlobNotFoundException(blob));
                });

                runner.test("when blob exists and is empty", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    final Blob blob = blobStorage.createBlob(new byte[0]).await();
                    try (final ByteReadStream contents = blob.getContents().await())
                    {
                        test.assertNotNull(contents);
                        test.assertEqual(new byte[0], contents.readAllBytes().await());
                    }
                });

                runner.test("when blob exists and is non-empty", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    final Blob blob = blobStorage.createBlob(new byte[] { 1, 2, 3, 4 }).await();
                    try (final ByteReadStream contents = blob.getContents().await())
                    {
                        test.assertNotNull(contents);
                        test.assertEqual(new byte[] { 1, 2, 3, 4 }, contents.readAllBytes().await());
                    }
                });
            });

            runner.testGroup("equals(Object)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    test.assertFalse(blob.equals((Object)null));
                });

                runner.test("with non-Blob type", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    test.assertFalse(blob.equals((Object)"hello"));
                });

                runner.test("with different blobStorage", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));

                    final InMemoryBlobStorage blobStorage2 = InMemoryBlobStorage.create();
                    final Blob blob2 = blobStorage2.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    test.assertFalse(blob.equals((Object)blob2));
                });

                runner.test("with different checksumType", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    final Blob blob2 = blobStorage.getBlob(BlobChecksum.create("spam2", BitArray.createFromBitString("1010")));
                    test.assertFalse(blob.equals((Object)blob2));
                });

                runner.test("with different checksumType case", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    final Blob blob2 = blobStorage.getBlob(BlobChecksum.create("SPAM", BitArray.createFromBitString("1010")));
                    test.assertTrue(blob.equals((Object)blob2));
                });

                runner.test("with different checksumValue", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    final Blob blob2 = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("10101")));
                    test.assertFalse(blob.equals((Object)blob2));
                });

                runner.test("with equal Blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    final Blob blob2 = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    test.assertTrue(blob.equals((Object)blob2));
                });

                runner.test("with same Blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    test.assertTrue(blob.equals((Object)blob));
                });
            });

            runner.testGroup("equals(Blob)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    test.assertFalse(blob.equals((Blob)null));
                });

                runner.test("with different blobStorage", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));

                    final InMemoryBlobStorage blobStorage2 = InMemoryBlobStorage.create();
                    final Blob blob2 = blobStorage2.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    test.assertFalse(blob.equals(blob2));
                });

                runner.test("with different checksumType", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    final Blob blob2 = blobStorage.getBlob(BlobChecksum.create("spam2", BitArray.createFromBitString("1010")));
                    test.assertFalse(blob.equals(blob2));
                });

                runner.test("with different checksumType case", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    final Blob blob2 = blobStorage.getBlob(BlobChecksum.create("SPAM", BitArray.createFromBitString("1010")));
                    test.assertTrue(blob.equals(blob2));
                });

                runner.test("with different checksumValue", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    final Blob blob2 = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("10101")));
                    test.assertFalse(blob.equals(blob2));
                });

                runner.test("with equal Blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    final Blob blob2 = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    test.assertTrue(blob.equals(blob2));
                });

                runner.test("with same Blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob(BlobChecksum.create("spam", BitArray.createFromBitString("1010")));
                    test.assertTrue(blob.equals(blob));
                });
            });
        });
    }
}
