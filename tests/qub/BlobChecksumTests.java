package qub;

public interface BlobChecksumTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(BlobChecksum.class, () ->
        {
            runner.testGroup("create(BlobChecksumType,BitArray)", () ->
            {
                runner.test("with null checksumType", (Test test) ->
                {
                    test.assertThrows(() -> BlobChecksum.create((BlobChecksumType)null, BitArray.create(4)),
                        new PreConditionFailure("checksumType cannot be null."));
                });

                runner.test("with null checksumValue", (Test test) ->
                {
                    test.assertThrows(() -> BlobChecksum.create(BlobChecksumType.MD5, null),
                        new PreConditionFailure("checksumValue cannot be null."));
                });

                runner.test("with empty checksumValue", (Test test) ->
                {
                    test.assertThrows(() -> BlobChecksum.create(BlobChecksumType.MD5, BitArray.create(0)),
                        new PreConditionFailure("checksumValue cannot be empty."));
                });

                runner.test("with non-null checksumType and non-empty checksumValue", (Test test) ->
                {
                    final BlobChecksum checksum = BlobChecksum.create(BlobChecksumType.MD5, BitArray.create(8));
                    test.assertNotNull(checksum);
                    test.assertEqual("MD5", checksum.getChecksumType());
                    test.assertEqual(BitArray.create(8), checksum.getChecksumValue());
                });
            });

            runner.testGroup("create(String,BitArray)", () ->
            {
                runner.test("with null checksumType", (Test test) ->
                {
                    test.assertThrows(() -> BlobChecksum.create((String)null, BitArray.create(4)),
                        new PreConditionFailure("checksumType cannot be null."));
                });

                runner.test("with empty checksumType", (Test test) ->
                {
                    test.assertThrows(() -> BlobChecksum.create("", BitArray.create(4)),
                        new PreConditionFailure("checksumType cannot be empty."));
                });

                runner.test("with null checksumValue", (Test test) ->
                {
                    test.assertThrows(() -> BlobChecksum.create("md5", null),
                        new PreConditionFailure("checksumValue cannot be null."));
                });

                runner.test("with empty checksumValue", (Test test) ->
                {
                    test.assertThrows(() -> BlobChecksum.create("Md5", BitArray.create(0)),
                        new PreConditionFailure("checksumValue cannot be empty."));
                });

                runner.test("with non-null checksumType and non-empty checksumValue", (Test test) ->
                {
                    final BlobChecksum checksum = BlobChecksum.create("MD5", BitArray.create(8));
                    test.assertNotNull(checksum);
                    test.assertEqual("MD5", checksum.getChecksumType());
                    test.assertEqual(BitArray.create(8), checksum.getChecksumValue());
                });
            });

            runner.testGroup("toString()", () ->
            {
                final Action2<BlobChecksum,String> toStringTest = (BlobChecksum checksum, String expected) ->
                {
                    runner.test("with " + checksum, (Test test) ->
                    {
                        test.assertEqual(expected, checksum.toString());
                    });
                };

                toStringTest.run(BlobChecksum.create("a", BitArray.createFromHexString("b")), "a:B");
                toStringTest.run(BlobChecksum.create("MD5", BitArray.createFromHexString("12351f43512345")), "MD5:12351F43512345");
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<BlobChecksum,Object,Boolean> equalsTest = (BlobChecksum checksum, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(checksum, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, checksum.equals(rhs));
                    });
                };

                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    null,
                    false);
                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    "hello",
                    false);
                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md", BitArray.createFromHexString("abc")),
                    false);
                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md5", BitArray.createFromHexString("ab")),
                    false);
                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    true);
                equalsTest.run(
                    BlobChecksum.create("MD5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    true);
                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md5", BitArray.createFromHexString("ABC")),
                    true);
            });

            runner.testGroup("equals(BlobChecksum)", () ->
            {
                final Action3<BlobChecksum,BlobChecksum,Boolean> equalsTest = (BlobChecksum checksum, BlobChecksum rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(checksum, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, checksum.equals(rhs));
                    });
                };

                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    null,
                    false);
                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md", BitArray.createFromHexString("abc")),
                    false);
                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md5", BitArray.createFromHexString("ab")),
                    false);
                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    true);
                equalsTest.run(
                    BlobChecksum.create("MD5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    true);
                equalsTest.run(
                    BlobChecksum.create("md5", BitArray.createFromHexString("abc")),
                    BlobChecksum.create("md5", BitArray.createFromHexString("ABC")),
                    true);
            });
        });
    }
}
