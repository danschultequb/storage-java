package qub;

public interface ContentLengthBlobIdElementCreatorTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(ContentLengthBlobIdElementCreator.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                try (final ContentLengthBlobIdElementCreator creator = ContentLengthBlobIdElementCreator.create())
                {
                    test.assertNotNull(creator);
                    test.assertFalse(creator.isDisposed());
                    test.assertEqual("ContentLength", creator.getBlobIdElementType());
                }
            });

            runner.testGroup("addByte(byte)", () ->
            {
                final Action1<Integer> addByteTest = (Integer calls) ->
                {
                    runner.test("with " + calls + " call(s)", (Test test) ->
                    {
                        try (final ContentLengthBlobIdElementCreator creator = ContentLengthBlobIdElementCreator.create())
                        {
                            for (int i = 0; i < calls; i++)
                            {
                                creator.addByte((byte)20);
                            }

                            test.assertEqual(
                                BlobIdElement.create("ContentLength", Integers.toString(calls)),
                                creator.takeBlobIdElement());
                        }
                    });
                };

                addByteTest.run(1);
                addByteTest.run(2);
                addByteTest.run(3);
            });

            runner.testGroup("addBytes(byte[])", () ->
            {
                final Action1<byte[]> addBytesTest = (byte[] values) ->
                {
                    runner.test("with " + Array.toString(values), (Test test) ->
                    {
                        try (final ContentLengthBlobIdElementCreator creator = ContentLengthBlobIdElementCreator.create())
                        {
                            creator.addBytes(values);

                            test.assertEqual(
                                BlobIdElement.create("ContentLength", Integers.toString(values.length)),
                                creator.takeBlobIdElement());
                        }
                    });
                };

                addBytesTest.run(new byte[0]);
                addBytesTest.run(new byte[5]);
            });

            runner.testGroup("addBytes(byte[],int,int)", () ->
            {
                final Action3<byte[],Integer,Integer> addBytesTest = (byte[] values, Integer startIndex, Integer length) ->
                {
                    runner.test("with " + English.andList(Array.toString(values), startIndex, length), (Test test) ->
                    {
                        try (final ContentLengthBlobIdElementCreator creator = ContentLengthBlobIdElementCreator.create())
                        {
                            creator.addBytes(values, startIndex, length);

                            test.assertEqual(
                                BlobIdElement.create("ContentLength", Integers.toString(length)),
                                creator.takeBlobIdElement());
                        }
                    });
                };

                addBytesTest.run(new byte[0], 0, 0);
                addBytesTest.run(new byte[5], 0, 0);
                addBytesTest.run(new byte[5], 1, 2);
                addBytesTest.run(new byte[5], 2, 3);
            });

            runner.testGroup("takeBlobIdElement()", () ->
            {
                runner.test("when not disposed", (Test test) ->
                {
                    try (final ContentLengthBlobIdElementCreator creator = ContentLengthBlobIdElementCreator.create())
                    {
                        creator.addBytes(new byte[34]);

                        test.assertEqual(
                            BlobIdElement.create("ContentLength", "34"),
                            creator.takeBlobIdElement());

                        for (int i = 0; i < 2; i++)
                        {
                            test.assertEqual(
                                BlobIdElement.create("ContentLength", "0"),
                                creator.takeBlobIdElement());
                        }
                    }
                });
            });
        });
    }
}
