package qub;

public interface BlobIdElementCreatorTests
{
    public static void test(TestRunner runner, Function0<? extends BlobIdElementCreator> creatorFunction)
    {
        runner.testGroup(BlobIdElementCreator.class, () ->
        {
            runner.testGroup("addByte(byte)", () ->
            {
                runner.test("when disposed", (Test test) ->
                {
                    try (final BlobIdElementCreator creator = creatorFunction.run())
                    {
                        test.assertTrue(creator.dispose().await());

                        test.assertThrows(() -> creator.addByte((byte)15),
                            new PreConditionFailure("this.isDisposed() cannot be true."));
                    }
                });
            });

            runner.testGroup("addBytes(byte[])", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    try (final BlobIdElementCreator creator = creatorFunction.run())
                    {
                        test.assertThrows(() -> creator.addBytes(null),
                            new PreConditionFailure("values cannot be null."));
                    }
                });

                runner.test("when disposed", (Test test) ->
                {
                    try (final BlobIdElementCreator creator = creatorFunction.run())
                    {
                        test.assertTrue(creator.dispose().await());
                        test.assertTrue(creator.isDisposed());

                        test.assertThrows(() -> creator.addBytes(new byte[] { 1, 2, 3 }),
                            new PreConditionFailure("this.isDisposed() cannot be true."));
                    }
                });
            });

            runner.testGroup("addBytes(byte[],int,int)", () ->
            {
                final Action4<byte[],Integer,Integer,Throwable> addBytesErrorTest = (byte[] values, Integer startIndex, Integer length, Throwable expected) ->
                {
                    runner.test("with " + English.andList(Array.toString(values), startIndex, length), (Test test) ->
                    {
                        try (final BlobIdElementCreator creator = creatorFunction.run())
                        {
                            test.assertThrows(() -> creator.addBytes(values, startIndex, length),
                                expected);
                        }
                    });
                };

                addBytesErrorTest.run(null, 0, 0, new PreConditionFailure("values cannot be null."));
                addBytesErrorTest.run(new byte[0], -1, 0, new PreConditionFailure("startIndex (-1) must be equal to 0."));
                addBytesErrorTest.run(new byte[0], 1, 0, new PreConditionFailure("startIndex (1) must be equal to 0."));
                addBytesErrorTest.run(new byte[0], 0, -1, new PreConditionFailure("length (-1) must be equal to 0."));
                addBytesErrorTest.run(new byte[0], 0, 1, new PreConditionFailure("length (1) must be equal to 0."));

                runner.test("when disposed", (Test test) ->
                {
                    try (final BlobIdElementCreator creator = creatorFunction.run())
                    {
                        test.assertTrue(creator.dispose().await());

                        test.assertThrows(() -> creator.addBytes(new byte[5], 3, 2),
                            new PreConditionFailure("this.isDisposed() cannot be true."));
                    }
                });
            });

            runner.testGroup("takeBlobIdElement()", () ->
            {
                runner.test("when disposed", (Test test) ->
                {
                    try (final BlobIdElementCreator creator = creatorFunction.run())
                    {
                        test.assertTrue(creator.dispose().await());

                        for (int i = 0; i < 2; i++)
                        {
                            test.assertThrows(() -> creator.takeBlobIdElement(),
                                new PreConditionFailure("this.isDisposed() cannot be true."));
                        }
                    }
                });
            });

            runner.test("dispose()", (Test test) ->
            {
                try (final BlobIdElementCreator creator = creatorFunction.run())
                {
                    test.assertFalse(creator.isDisposed());

                    test.assertTrue(creator.dispose().await());
                    test.assertTrue(creator.isDisposed());

                    test.assertFalse(creator.dispose().await());
                    test.assertTrue(creator.isDisposed());
                }
            });
        });
    }
}
