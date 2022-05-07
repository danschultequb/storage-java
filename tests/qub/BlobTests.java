package qub;

public interface BlobTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(Blob.class, () ->
        {
            runner.testGroup("create(BlobStorage,String,BitArray)", () ->
            {
                runner.test("with null BlobStorage", (Test test) ->
                {
                    test.assertThrows(() -> Blob.create(null, "md5", BitArray.createFromBitString("1")),
                        new PreConditionFailure("blobStorage cannot be null."));
                });

                runner.test("with null checksum type", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    test.assertThrows(() -> Blob.create(blobStorage, null, BitArray.createFromBitString("1")),
                        new PreConditionFailure("checksumType cannot be null."));
                });

                runner.test("with empty checksum type", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    test.assertThrows(() -> Blob.create(blobStorage, "", BitArray.createFromBitString("1")),
                        new PreConditionFailure("checksumType cannot be empty."));
                });

                runner.test("with null checksum value", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    test.assertThrows(() -> Blob.create(blobStorage, "spam", null),
                        new PreConditionFailure("checksumValue cannot be null."));
                });

                runner.test("with empty checksum value", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    test.assertThrows(() -> Blob.create(blobStorage, "spam", BitArray.createFromBitString("")),
                        new PreConditionFailure("checksumValue cannot be empty."));
                });

                runner.test("with valid arguments", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = Blob.create(blobStorage, "spam", BitArray.createFromBitString("101010"));
                    test.assertNotNull(blob);
                    test.assertSame(blobStorage, blob.getBlobStorage());
                    test.assertEqual("spam", blob.getChecksumType());
                    test.assertEqual(BitArray.createFromBitString("101010"), blob.getChecksumValue());
                });
            });

            runner.testGroup("exists()", () ->
            {
                runner.test("when blob doesn't exist", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();

                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("101010"));
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

                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("101010"));
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

                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("101010"));
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
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    test.assertFalse(blob.equals((Object)null));
                });

                runner.test("with non-Blob type", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    test.assertFalse(blob.equals((Object)"hello"));
                });

                runner.test("with different blobStorage", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));

                    final InMemoryBlobStorage blobStorage2 = InMemoryBlobStorage.create();
                    final Blob blob2 = blobStorage2.getBlob("spam", BitArray.createFromBitString("1010"));
                    test.assertFalse(blob.equals((Object)blob2));
                });

                runner.test("with different checksumType", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    final Blob blob2 = blobStorage.getBlob("spam2", BitArray.createFromBitString("1010"));
                    test.assertFalse(blob.equals((Object)blob2));
                });

                runner.test("with different checksumType case", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    final Blob blob2 = blobStorage.getBlob("SPAM", BitArray.createFromBitString("1010"));
                    test.assertTrue(blob.equals((Object)blob2));
                });

                runner.test("with different checksumValue", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    final Blob blob2 = blobStorage.getBlob("spam", BitArray.createFromBitString("10101"));
                    test.assertFalse(blob.equals((Object)blob2));
                });

                runner.test("with equal Blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    final Blob blob2 = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    test.assertTrue(blob.equals((Object)blob2));
                });

                runner.test("with same Blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    test.assertTrue(blob.equals((Object)blob));
                });
            });

            runner.testGroup("equals(Blob)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    test.assertFalse(blob.equals((Blob)null));
                });

                runner.test("with different blobStorage", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));

                    final InMemoryBlobStorage blobStorage2 = InMemoryBlobStorage.create();
                    final Blob blob2 = blobStorage2.getBlob("spam", BitArray.createFromBitString("1010"));
                    test.assertFalse(blob.equals(blob2));
                });

                runner.test("with different checksumType", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    final Blob blob2 = blobStorage.getBlob("spam2", BitArray.createFromBitString("1010"));
                    test.assertFalse(blob.equals(blob2));
                });

                runner.test("with different checksumType case", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    final Blob blob2 = blobStorage.getBlob("SPAM", BitArray.createFromBitString("1010"));
                    test.assertTrue(blob.equals(blob2));
                });

                runner.test("with different checksumValue", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    final Blob blob2 = blobStorage.getBlob("spam", BitArray.createFromBitString("10101"));
                    test.assertFalse(blob.equals(blob2));
                });

                runner.test("with equal Blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    final Blob blob2 = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    test.assertTrue(blob.equals(blob2));
                });

                runner.test("with same Blob", (Test test) ->
                {
                    final InMemoryBlobStorage blobStorage = InMemoryBlobStorage.create();
                    final Blob blob = blobStorage.getBlob("spam", BitArray.createFromBitString("1010"));
                    test.assertTrue(blob.equals(blob));
                });
            });
        });
    }
}
