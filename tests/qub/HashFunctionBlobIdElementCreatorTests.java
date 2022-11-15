package qub;

public interface HashFunctionBlobIdElementCreatorTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(HashFunctionBlobIdElementCreator.class, () ->
        {
            BlobIdElementCreatorTests.test(runner, HashFunctionBlobIdElementCreator::createSHA1);

            runner.testGroup("create(String,HashFunction)", () ->
            {
                final Action3<String,HashFunction,Throwable> createErrorTest = (String blobIdElementType, HashFunction hashFunction, Throwable expected) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(blobIdElementType), hashFunction == null ? "null" : "not null"), (Test test) ->
                    {
                        test.assertThrows(() -> HashFunctionBlobIdElementCreator.create(blobIdElementType, hashFunction),
                            expected);
                    });
                };

                createErrorTest.run(
                    null,
                    HashFunction.createMD5().await(),
                    new PreConditionFailure("blobIdElementType cannot be null."));
                createErrorTest.run(
                    "",
                    HashFunction.createMD5().await(),
                    new PreConditionFailure("blobIdElementType cannot be empty."));
                createErrorTest.run(
                    "hello",
                    null,
                    new PreConditionFailure("hashFunction cannot be null."));

                final Action2<String,HashFunction> createTest = (String blobIdElementType, HashFunction hashFunction) ->
                {
                    runner.test("with " + English.andList(Strings.escapeAndQuote(blobIdElementType), hashFunction == null ? "null" : "not null"), (Test test) ->
                    {
                        try (final HashFunctionBlobIdElementCreator creator = HashFunctionBlobIdElementCreator.create(blobIdElementType, hashFunction))
                        {
                            test.assertNotNull(creator);
                            test.assertEqual(blobIdElementType, creator.getBlobIdElementType());
                            test.assertFalse(creator.isDisposed());
                        }
                    });
                };

                createTest.run("MD5", HashFunction.createMD5().await());
                createTest.run("SHA1", HashFunction.createSHA1().await());
            });

            runner.test("createMD5()", (Test test) ->
            {
                try (final HashFunctionBlobIdElementCreator creator = HashFunctionBlobIdElementCreator.createMD5())
                {
                    test.assertNotNull(creator);
                    test.assertEqual("MD5", creator.getBlobIdElementType());
                    test.assertEqual(
                        BlobIdElement.create("MD5", "D41D8CD98F00B204E9800998ECF8427E"),
                        creator.takeBlobIdElement());
                }
            });

            runner.test("createSHA1()", (Test test) ->
            {
                try (final HashFunctionBlobIdElementCreator creator = HashFunctionBlobIdElementCreator.createSHA1())
                {
                    test.assertNotNull(creator);
                    test.assertEqual("SHA1", creator.getBlobIdElementType());
                    test.assertEqual(
                        BlobIdElement.create("SHA1", "DA39A3EE5E6B4B0D3255BFEF95601890AFD80709"),
                        creator.takeBlobIdElement());
                }
            });

            runner.test("createSHA256()", (Test test) ->
            {
                try (final HashFunctionBlobIdElementCreator creator = HashFunctionBlobIdElementCreator.createSHA256())
                {
                    test.assertNotNull(creator);
                    test.assertEqual("SHA256", creator.getBlobIdElementType());
                    test.assertEqual(
                        BlobIdElement.create("SHA256", "E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855"),
                        creator.takeBlobIdElement());
                }
            });

            runner.testGroup("addByte(byte)", () ->
            {
                final Action3<HashFunctionBlobIdElementCreator,String,BlobIdElement> addByteTest = (HashFunctionBlobIdElementCreator creator, String bytesHexString, BlobIdElement expected) ->
                {
                    runner.test("with " + English.andList(creator.getBlobIdElementType() + " creator", Strings.escapeAndQuote(bytesHexString)), (Test test) ->
                    {
                        try
                        {
                            for (final char c : Strings.iterate(bytesHexString))
                            {
                                creator.addByte(Bytes.fromHexChar(c));
                            }
                            test.assertEqual(
                                expected,
                                creator.takeBlobIdElement());
                        }
                        finally
                        {
                            creator.dispose().await();
                        }
                    });
                };

                addByteTest.run(
                    HashFunctionBlobIdElementCreator.createMD5(),
                    "1",
                    BlobIdElement.create("MD5", "55A54008AD1BA589AA210D2629C1DF41"));
                addByteTest.run(
                    HashFunctionBlobIdElementCreator.createMD5(),
                    "2",
                    BlobIdElement.create("MD5", "9E688C58A5487B8EAF69C9E1005AD0BF"));
                addByteTest.run(
                    HashFunctionBlobIdElementCreator.createMD5(),
                    "123",
                    BlobIdElement.create("MD5", "5289DF737DF57326FCDD22597AFB1FAC"));
            });

            runner.testGroup("addBytes(byte[])", () ->
            {
                final Action3<HashFunctionBlobIdElementCreator,byte[],BlobIdElement> addBytesTest = (HashFunctionBlobIdElementCreator creator, byte[] values, BlobIdElement expected) ->
                {
                    runner.test("with " + English.andList(creator.getBlobIdElementType(), Array.toString(values)), (Test test) ->
                    {
                        try
                        {
                            creator.addBytes(values);

                            test.assertEqual(expected, creator.takeBlobIdElement());
                        }
                        finally
                        {
                            creator.dispose().await();
                        }
                    });
                };

                addBytesTest.run(
                    HashFunctionBlobIdElementCreator.createMD5(),
                    new byte[0],
                    BlobIdElement.create("MD5", "D41D8CD98F00B204E9800998ECF8427E"));
                addBytesTest.run(
                    HashFunctionBlobIdElementCreator.createMD5(),
                    new byte[] { 1, 2, 3 },
                    BlobIdElement.create("MD5", "5289DF737DF57326FCDD22597AFB1FAC"));
            });

            runner.testGroup("addBytes(byte[],int,int)", () ->
            {
                final Action5<HashFunctionBlobIdElementCreator,byte[],Integer,Integer,BlobIdElement> addBytesTest = (HashFunctionBlobIdElementCreator creator, byte[] values, Integer startIndex, Integer length, BlobIdElement expected) ->
                {
                    runner.test("with " + English.andList(Array.toString(values), startIndex, length), (Test test) ->
                    {
                        try
                        {
                            creator.addBytes(values, startIndex, length);

                            test.assertEqual(expected, creator.takeBlobIdElement());
                        }
                        finally
                        {
                            creator.dispose().await();
                        }
                    });
                };

                addBytesTest.run(
                    HashFunctionBlobIdElementCreator.createMD5(),
                    new byte[0], 0, 0,
                    BlobIdElement.create("MD5", "D41D8CD98F00B204E9800998ECF8427E"));
            });

            runner.testGroup("takeBlobIdElement()", () ->
            {
                runner.test("when not disposed", (Test test) ->
                {
                    try (final HashFunctionBlobIdElementCreator creator = HashFunctionBlobIdElementCreator.createSHA1())
                    {
                        creator.addBytes(new byte[34]);

                        test.assertEqual(
                            BlobIdElement.create("SHA1", "3173532552077D0D796C3628AC35C76343DC3A04"),
                            creator.takeBlobIdElement());

                        for (int i = 0; i < 2; i++)
                        {
                            test.assertEqual(
                                BlobIdElement.create("SHA1", "DA39A3EE5E6B4B0D3255BFEF95601890AFD80709"),
                                creator.takeBlobIdElement());
                        }
                    }
                });
            });
        });
    }
}
