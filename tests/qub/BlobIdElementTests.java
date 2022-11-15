package qub;

public interface BlobIdElementTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(BlobIdElement.class, () ->
        {
            runner.testGroup("create(String,String)", () ->
            {
                final Action3<String,String,Throwable> createErrorTest = (String blobIdElementType, String blobIdElementValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(Iterable.create(blobIdElementType, blobIdElementValue).map(Strings::escapeAndQuote)), (Test test) ->
                    {
                        test.assertThrows(() -> BlobIdElement.create(blobIdElementType, blobIdElementValue),
                            expected);
                    });
                };

                createErrorTest.run(null, "4", new PreConditionFailure("blobIdElementType cannot be null."));
                createErrorTest.run("", "4", new PreConditionFailure("blobIdElementType cannot be empty."));
                createErrorTest.run("md5", null, new PreConditionFailure("blobIdElementValue cannot be null."));
                createErrorTest.run("md5", "", new PreConditionFailure("blobIdElementValue cannot be empty."));

                final Action2<String,String> createTest = (String blobIdElementType, String blobIdElementValue) ->
                {
                    runner.test("with " + English.andList(Iterable.create(blobIdElementType, blobIdElementValue).map(Strings::escapeAndQuote)), (Test test) ->
                    {
                        final BlobIdElement blobIdElement = BlobIdElement.create(blobIdElementType, blobIdElementValue);
                        test.assertNotNull(blobIdElement);
                        test.assertEqual(blobIdElementType, blobIdElement.getElementType());
                        test.assertEqual(blobIdElementValue, blobIdElement.getElementValue());
                    });
                };

                createTest.run("a", "b");
            });

            runner.testGroup("toString()", () ->
            {
                final Action2<BlobIdElement,String> toStringTest = (BlobIdElement checksum, String expected) ->
                {
                    runner.test("with " + checksum, (Test test) ->
                    {
                        test.assertEqual(expected, checksum.toString());
                    });
                };

                toStringTest.run(BlobIdElement.create("a", "b"), "\"a\":\"b\"");
                toStringTest.run(BlobIdElement.create("SHA1", "12351f43512345"), "\"SHA1\":\"12351f43512345\"");
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<BlobIdElement,Object,Boolean> equalsTest = (BlobIdElement checksum, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(checksum, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, checksum.equals(rhs));
                    });
                };

                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    null,
                    false);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    "hello",
                    false);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("SHA1", "abc"),
                    false);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("MD5", "ab"),
                    false);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("MD5", "abc"),
                    true);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("MD5", "ABC"),
                    false);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("md5", "abc"),
                    false);
            });

            runner.testGroup("equals(BlobIdElement)", () ->
            {
                final Action3<BlobIdElement, BlobIdElement,Boolean> equalsTest = (BlobIdElement checksum, BlobIdElement rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(checksum, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, checksum.equals(rhs));
                    });
                };

                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    null,
                    false);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("SHA1", "abc"),
                    false);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("MD5", "ab"),
                    false);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("MD5", "abc"),
                    true);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("MD5", "ABC"),
                    false);
                equalsTest.run(
                    BlobIdElement.create("MD5", "abc"),
                    BlobIdElement.create("md5", "abc"),
                    false);
            });

            runner.test("hashCode()", (Test test) ->
            {
                final BlobIdElement element1 = BlobIdElement.create("a", "b");
                final int element1HashCode = element1.hashCode();
                test.assertEqual(element1HashCode, element1.hashCode());
                test.assertEqual(element1HashCode, BlobIdElement.create("a", "b").hashCode());

                final BlobIdElement element2 = BlobIdElement.create("c", "d");
                final int element2HashCode = element2.hashCode();
                test.assertEqual(element2HashCode, element2.hashCode());
                test.assertNotEqual(element1HashCode, element2HashCode);

                final BlobIdElement element3 = BlobIdElement.create("d", "c");
                final int element3HashCode = element3.hashCode();
                test.assertNotEqual(element2HashCode, element3HashCode);
            });
        });
    }
}
