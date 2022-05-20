package qub;

public interface CreateItemParametersTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(CreateItemParameters.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                final CreateItemParameters parameters = CreateItemParameters.create();
                test.assertNotNull(parameters);
                test.assertNull(parameters.getName());
                test.assertNull(parameters.getContents());
            });

            runner.testGroup("setName(String)", () ->
            {
                final Action2<String,Throwable> setNameErrorTest = (String name, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(name), (Test test) ->
                    {
                        final CreateItemParameters parameters = CreateItemParameters.create();
                        test.assertThrows(() -> parameters.setName(name),
                            expected);
                        test.assertNull(parameters.getName());
                    });
                };

                setNameErrorTest.run(null, new PreConditionFailure("name cannot be null."));
                setNameErrorTest.run("", new PreConditionFailure("name cannot be empty."));

                final Action1<String> setNameTest = (String name) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(name), (Test test) ->
                    {
                        final CreateItemParameters parameters = CreateItemParameters.create();
                        final CreateItemParameters setNameResult = parameters.setName(name);
                        test.assertSame(parameters, setNameResult);
                        test.assertEqual(name, parameters.getName());
                        test.assertNull(parameters.getContents());
                    });
                };

                setNameTest.run("hello");
            });

            runner.testGroup("setContents(byte[])", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final CreateItemParameters parameters = CreateItemParameters.create();
                    test.assertThrows(() -> parameters.setContents((byte[])null),
                        new PreConditionFailure("contents cannot be null."));
                    test.assertNull(parameters.getName());
                    test.assertNull(parameters.getContents());
                });

                final Action1<byte[]> setContentsTest = (byte[] contents) ->
                {
                    runner.test("with " + ByteArray.create(contents).toString(), (Test test) ->
                    {
                        final CreateItemParameters parameters = CreateItemParameters.create();
                        final CreateItemParameters setContentsResult = parameters.setContents(contents);
                        test.assertSame(parameters, setContentsResult);
                        test.assertNull(parameters.getName());
                        test.assertEqual(contents, parameters.getContents().readAllBytes().await());
                    });
                };

                setContentsTest.run(new byte[0]);
                setContentsTest.run(new byte[] { 1 });
                setContentsTest.run(new byte[] { 1, 2, 3, 4, 5 });
            });

            runner.testGroup("setContents(ByteReadStream)", () ->
            {
                runner.test("with null", (Test test) ->
                {
                    final CreateItemParameters parameters = CreateItemParameters.create();
                    test.assertThrows(() -> parameters.setContents((ByteReadStream)null),
                        new PreConditionFailure("contents cannot be null."));
                    test.assertNull(parameters.getName());
                    test.assertNull(parameters.getContents());
                });

                final Action1<byte[]> setContentsTest = (byte[] contents) ->
                {
                    runner.test("with " + ByteArray.create(contents).toString(), (Test test) ->
                    {
                        final CreateItemParameters parameters = CreateItemParameters.create();
                        final CreateItemParameters setContentsResult = parameters.setContents((ByteReadStream)InMemoryByteStream.create(contents).endOfStream());
                        test.assertSame(parameters, setContentsResult);
                        test.assertNull(parameters.getName());
                        test.assertEqual(contents, parameters.getContents().readAllBytes().await());
                    });
                };

                setContentsTest.run(new byte[0]);
                setContentsTest.run(new byte[] { 1 });
                setContentsTest.run(new byte[] { 1, 2, 3, 4, 5 });
            });
        });
    }
}
