package qub;

public interface InMemoryItemTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(InMemoryItem.class, () ->
        {
            runner.test("create()", (Test test) ->
            {
                final InMemoryItem item = InMemoryItem.create();
                test.assertNotNull(item);
                test.assertNull(item.getName());
                test.assertNull(item.getContents());
            });

            runner.testGroup("setName(String)", () ->
            {
                final Action2<String,Throwable> setNameErrorTest = (String name, Throwable expected) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(name), (Test test) ->
                    {
                        final InMemoryItem item = InMemoryItem.create();
                        test.assertThrows(() -> item.setName(name), expected);
                        test.assertNull(item.getName());
                    });
                };

                setNameErrorTest.run(null, new PreConditionFailure("name cannot be null."));
                setNameErrorTest.run("", new PreConditionFailure("name cannot be empty."));

                final Action1<String> setNameTest = (String name) ->
                {
                    runner.test("with " + Strings.escapeAndQuote(name), (Test test) ->
                    {
                        final InMemoryItem item = InMemoryItem.create();
                        final InMemoryItem setNameResult = item.setName(name);
                        test.assertSame(item, setNameResult);
                        test.assertEqual(name, item.getName());
                    });
                };

                setNameTest.run("hello");
                setNameTest.run("configuration.json");
                setNameTest.run("13512839048");
            });

            runner.testGroup("setContents(byte[])", () ->
            {
                final Action1<byte[]> setContentsTest = (byte[] contents) ->
                {
                    runner.test("with " + Objects.toString(contents), (Test test) ->
                    {
                        final InMemoryItem item = InMemoryItem.create();
                        final InMemoryItem setContentsResult = item.setContents(contents);
                        test.assertSame(item, setContentsResult);
                        test.assertEqual(contents, item.getContents());
                    });
                };

                setContentsTest.run(null);
                setContentsTest.run(new byte[] {});
                setContentsTest.run(new byte[] { 1 });
                setContentsTest.run(new byte[] { 1, 2, 3, 4, 5 });
            });
        });
    }
}
