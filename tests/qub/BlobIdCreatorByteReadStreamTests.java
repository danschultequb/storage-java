package qub;

public interface BlobIdCreatorByteReadStreamTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(BlobIdCreatorByteReadStream.class, () ->
        {
            runner.testGroup("create(ByteReadStream,Iterable<BlobIdElementCreator>)", () ->
            {
                runner.test("with null innerStream", (Test test) ->
                {
                    test.assertThrows(() -> BlobIdCreatorByteReadStream.create(null, Iterable.create()),
                        new PreConditionFailure("innerStream cannot be null."));
                });

                runner.test("with null blobIdElementCreators", (Test test) ->
                {
                    try (final InMemoryByteStream innerStream = InMemoryByteStream.create().endOfStream())
                    {
                        test.assertThrows(() -> BlobIdCreatorByteReadStream.create(innerStream, null),
                            new PreConditionFailure("blobIdElementCreators cannot be null."));
                    }
                });

                runner.test("with empty blobIdElementCreators", (Test test) ->
                {
                    try (final InMemoryByteStream innerStream = InMemoryByteStream.create().endOfStream())
                    {
                        test.assertThrows(() -> BlobIdCreatorByteReadStream.create(innerStream, Iterable.create()),
                            new PreConditionFailure("blobIdElementCreators cannot be empty."));
                    }
                });

                runner.test("with empty innerStream and one element creator", (Test test) ->
                {
                    try (final InMemoryByteStream innerStream = InMemoryByteStream.create().endOfStream())
                    {
                        try (final BlobIdCreatorByteReadStream stream =
                                 BlobIdCreatorByteReadStream.create(
                                     innerStream,
                                     Iterable.create(
                                        HashFunctionBlobIdElementCreator.createSHA1())))
                        {
                            test.assertNotNull(stream);
                            test.assertFalse(stream.isDisposed());
                            test.assertEqual(new byte[0], stream.readAllBytes().await());
                            test.assertEqual(
                                BlobId.create()
                                    .addElement(BlobIdElement.create("SHA1", "DA39A3EE5E6B4B0D3255BFEF95601890AFD80709")),
                                stream.takeBlobId());
                        }
                    }
                });

                runner.test("with empty innerStream and two element creators", (Test test) ->
                {
                    try (final InMemoryByteStream innerStream = InMemoryByteStream.create().endOfStream())
                    {
                        try (final BlobIdCreatorByteReadStream stream =
                                 BlobIdCreatorByteReadStream.create(
                                     innerStream,
                                     Iterable.create(
                                         ContentLengthBlobIdElementCreator.create(),
                                         HashFunctionBlobIdElementCreator.createSHA1())))
                        {
                            test.assertNotNull(stream);
                            test.assertFalse(stream.isDisposed());
                            test.assertEqual(new byte[0], stream.readAllBytes().await());
                            test.assertEqual(
                                BlobId.create()
                                    .addElement(BlobIdElement.create("ContentLength", "0"))
                                    .addElement(BlobIdElement.create("SHA1", "DA39A3EE5E6B4B0D3255BFEF95601890AFD80709")),
                                stream.takeBlobId());
                        }
                    }
                });

                runner.test("with non-empty innerStream and one element creator", (Test test) ->
                {
                    try (final InMemoryByteStream innerStream = InMemoryByteStream.create(new byte[] { 1, 2, 3, 4, 5 }).endOfStream())
                    {
                        try (final BlobIdCreatorByteReadStream stream =
                                 BlobIdCreatorByteReadStream.create(
                                     innerStream,
                                     Iterable.create(
                                        HashFunctionBlobIdElementCreator.createSHA1())))
                        {
                            test.assertNotNull(stream);
                            test.assertFalse(stream.isDisposed());
                            test.assertEqual(new byte[] { 1, 2, 3, 4, 5 }, stream.readAllBytes().await());
                            test.assertEqual(
                                BlobId.create()
                                    .addElement(BlobIdElement.create("SHA1", "11966AB9C099F8FABEFAC54C08D5BE2BD8C903AF")),
                                stream.takeBlobId());
                        }
                    }
                });

                runner.test("with non-empty innerStream and two element creators", (Test test) ->
                {
                    try (final InMemoryByteStream innerStream = InMemoryByteStream.create(new byte[] { 1, 2, 3, 4, 5 }).endOfStream())
                    {
                        try (final BlobIdCreatorByteReadStream stream =
                                 BlobIdCreatorByteReadStream.create(
                                     innerStream,
                                     Iterable.create(
                                         ContentLengthBlobIdElementCreator.create(),
                                         HashFunctionBlobIdElementCreator.createSHA1())))
                        {
                            test.assertNotNull(stream);
                            test.assertFalse(stream.isDisposed());
                            test.assertEqual(new byte[] { 1, 2, 3, 4, 5 }, stream.readAllBytes().await());
                            test.assertEqual(
                                BlobId.create()
                                    .addElement(BlobIdElement.create("ContentLength", "5"))
                                    .addElement(BlobIdElement.create("SHA1", "11966AB9C099F8FABEFAC54C08D5BE2BD8C903AF")),
                                stream.takeBlobId());
                        }
                    }
                });
            });

            runner.testGroup("readByte()", () ->
            {
                final Action2<byte[],String> readByteTest = (byte[] bytes, String expectedSHA1Hash) ->
                {
                    runner.test("with " + Array.toString(bytes), (Test test) ->
                    {
                        try (final InMemoryByteStream innerStream = InMemoryByteStream.create(bytes).endOfStream())
                        {
                            try (final BlobIdCreatorByteReadStream stream =
                                     BlobIdCreatorByteReadStream.create(
                                         innerStream,
                                         Iterable.create(
                                             ContentLengthBlobIdElementCreator.create(),
                                             HashFunctionBlobIdElementCreator.createSHA1())))
                            {
                                for (final byte b : bytes)
                                {
                                    test.assertEqual(b, stream.readByte().await());
                                }

                                for (int i = 0; i < 2; i++)
                                {
                                    test.assertThrows(() -> stream.readByte().await(),
                                        new EmptyException());
                                }

                                test.assertEqual(
                                    BlobId.create()
                                        .addElement(BlobIdElement.create("ContentLength", Integers.toString(bytes.length)))
                                        .addElement(BlobIdElement.create("SHA1", expectedSHA1Hash)),
                                    stream.takeBlobId());
                            }
                        }
                    });
                };

                readByteTest.run(
                    new byte[0],
                    "DA39A3EE5E6B4B0D3255BFEF95601890AFD80709");
                readByteTest.run(
                    new byte[] { 1 },
                    "BF8B4530D8D246DD74AC53A13471BBA17941DFF7");
                readByteTest.run(
                    new byte[] { 1, 2, 3, 4, 5 },
                    "11966AB9C099F8FABEFAC54C08D5BE2BD8C903AF");
            });
        });
    }
}
