package qub;

public interface MutableBlobIdTests
{
    public static void test(TestRunner runner)
    {
        runner.testGroup(MutableBlobId.class, () ->
        {
            BlobIdTests.test(runner, MutableBlobId::create);

            runner.test("create()", (Test test) ->
            {
                final MutableBlobId blobId = MutableBlobId.create();
                test.assertNotNull(blobId);
                test.assertEqual(0, blobId.getElementCount());
            });

            runner.testGroup("containsElementType(String)", () ->
            {
                final Action3<BlobId,String,Boolean> containsElementTypeTest = (BlobId blobId, String blobIdElementType, Boolean expected) ->
                {
                    runner.test("with " + English.andList(blobId, Strings.escapeAndQuote(blobIdElementType)), (Test test) ->
                    {
                        test.assertEqual(expected, blobId.containsElementType(blobIdElementType));
                    });
                };

                containsElementTypeTest.run(
                    MutableBlobId.create(),
                    "a",
                    false);
                containsElementTypeTest.run(
                    MutableBlobId.create(),
                    "SHA1",
                    false);
                containsElementTypeTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    "a",
                    true);
                containsElementTypeTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    "A",
                    false);
            });

            runner.testGroup("iterateElementTypes()", () ->
            {
                final Action2<MutableBlobId,Iterable<String>> iterateElementTypesTest = (MutableBlobId blobId, Iterable<String> expected) ->
                {
                    runner.test("with " + blobId.toString(), (Test test) ->
                    {
                        test.assertEqual(expected, blobId.iterateElementTypes().toList());
                    });
                };

                iterateElementTypesTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    Iterable.create("a"));
                iterateElementTypesTest.run(
                    MutableBlobId.create()
                        .addElement("MD5", "b"),
                    Iterable.create("MD5"));
                iterateElementTypesTest.run(
                    MutableBlobId.create()
                        .addElement("MD5", "b")
                        .addElement("SHA1", "c"),
                    Iterable.create("MD5", "SHA1"));
                iterateElementTypesTest.run(
                    MutableBlobId.create()
                        .addElement("SHA256", "a")
                        .addElement("MD5", "b")
                        .addElement("SHA1", "c"),
                    Iterable.create("SHA256", "MD5", "SHA1"));
            });

            runner.testGroup("containsElement(BlobIdElement)", () ->
            {
                final Action3<MutableBlobId,BlobIdElement,Boolean> containsElementTest = (MutableBlobId blobId, BlobIdElement blobIdElement, Boolean expected) ->
                {
                    runner.test("with " + English.andList(blobId, blobIdElement), (Test test) ->
                    {
                        test.assertEqual(expected, blobId.containsElement(blobIdElement));
                    });
                };

                containsElementTest.run(
                    MutableBlobId.create(),
                    BlobIdElement.create("a", "b"),
                    false);
                containsElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "1"),
                    BlobIdElement.create("a", "b"),
                    false);
                containsElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    BlobIdElement.create("A", "b"),
                    false);
                containsElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    BlobIdElement.create("a", "B"),
                    false);
                containsElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    BlobIdElement.create("A", "B"),
                    false);
                containsElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    BlobIdElement.create("a", "b"),
                    true);
            });

            runner.testGroup("iterateElements()", () ->
            {
                final Action2<MutableBlobId,Iterable<BlobIdElement>> iterateElementsTest = (MutableBlobId blobId, Iterable<BlobIdElement> expected) ->
                {
                    runner.test("with " + blobId.toString(), (Test test) ->
                    {
                        test.assertEqual(expected, blobId.iterateElements().toList());
                    });
                };

                iterateElementsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    Iterable.create(BlobIdElement.create("a", "b")));
                iterateElementsTest.run(
                    MutableBlobId.create()
                        .addElement("MD5", "b"),
                    Iterable.create(BlobIdElement.create("MD5", "b")));
                iterateElementsTest.run(
                    MutableBlobId.create()
                        .addElement("MD5", "b")
                        .addElement("SHA1", "c"),
                    Iterable.create(
                        BlobIdElement.create("MD5", "b"),
                        BlobIdElement.create("SHA1", "c")));
                iterateElementsTest.run(
                    MutableBlobId.create()
                        .addElement("SHA256", "a")
                        .addElement("MD5", "b")
                        .addElement("SHA1", "c"),
                    Iterable.create(
                        BlobIdElement.create("SHA256", "a"),
                        BlobIdElement.create("MD5", "b"),
                        BlobIdElement.create("SHA1", "c")));
            });

            runner.testGroup("getElement(String)", () ->
            {
                final Action3<MutableBlobId,String,BlobIdElement> getElementTest = (MutableBlobId blobId, String blobIdElementType, BlobIdElement expected) ->
                {
                    runner.test("with " + English.andList(blobId, Strings.escapeAndQuote(blobIdElementType)), (Test test) ->
                    {
                        test.assertEqual(expected, blobId.getElement(blobIdElementType).await());
                    });
                };

                getElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    "a",
                    BlobIdElement.create("a", "b"));
                getElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("A", "B"),
                    "a",
                    BlobIdElement.create("a", "b"));
                getElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("A", "B"),
                    "A",
                    BlobIdElement.create("A", "B"));
            });

            runner.testGroup("getElementValue(String)", () ->
            {
                final Action3<MutableBlobId,String,String> getElementTest = (MutableBlobId blobId, String blobIdElementType, String expected) ->
                {
                    runner.test("with " + English.andList(blobId, Strings.escapeAndQuote(blobIdElementType)), (Test test) ->
                    {
                        test.assertEqual(expected, blobId.getElementValue(blobIdElementType).await());
                    });
                };

                getElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    "a",
                    "b");
                getElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("A", "B"),
                    "a",
                    "b");
                getElementTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("A", "B"),
                    "A",
                    "B");
            });

            runner.testGroup("addElement(String,String)", () ->
            {
                final Action4<MutableBlobId,String,String,Throwable> addElementErrorTest = (MutableBlobId blobId, String blobIdElementType, String blobIdElementValue, Throwable expected) ->
                {
                    runner.test("with " + English.andList(blobId, Strings.escapeAndQuote(blobIdElementType), Strings.escapeAndQuote(blobIdElementValue)), (Test test) ->
                    {
                        test.assertThrows(() -> blobId.addElement(blobIdElementType, blobIdElementValue),
                            expected);
                    });
                };

                addElementErrorTest.run(
                    MutableBlobId.create(),
                    null,
                    "b",
                    new PreConditionFailure("blobIdElementType cannot be null."));
                addElementErrorTest.run(
                    MutableBlobId.create(),
                    "",
                    "b",
                    new PreConditionFailure("blobIdElementType cannot be empty."));
                addElementErrorTest.run(
                    MutableBlobId.create(),
                    "a",
                    null,
                    new PreConditionFailure("blobIdElementValue cannot be null."));
                addElementErrorTest.run(
                    MutableBlobId.create(),
                    "a",
                    "",
                    new PreConditionFailure("blobIdElementValue cannot be empty."));
                addElementErrorTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    "a",
                    "c",
                    new AlreadyExistsException("A BlobIdElement already exists in this MutableBlobId with the element type \"a\"."));

                runner.test("with non-existing blobIdElementType", (Test test) ->
                {
                    final MutableBlobId blobId = MutableBlobId.create();

                    final MutableBlobId addElementResult = blobId.addElement("a", "b");
                    test.assertSame(blobId, addElementResult);
                    test.assertEqual(
                        Iterable.create(
                            BlobIdElement.create("a", "b")),
                        blobId.iterateElements().toList());

                    blobId.addElement("A", "B");
                    test.assertEqual(
                        Iterable.create(
                            BlobIdElement.create("a", "b"),
                            BlobIdElement.create("A", "B")),
                        blobId.iterateElements().toList());
                });
            });

            runner.testGroup("addElement(BlobIdElement)", () ->
            {
                final Action3<MutableBlobId,BlobIdElement,Throwable> addElementErrorTest = (MutableBlobId blobId, BlobIdElement blobIdElement, Throwable expected) ->
                {
                    runner.test("with " + English.andList(blobId, blobIdElement), (Test test) ->
                    {
                        test.assertThrows(() -> blobId.addElement(blobIdElement),
                            expected);
                    });
                };

                addElementErrorTest.run(
                    MutableBlobId.create(),
                    null,
                    new PreConditionFailure("blobIdElement cannot be null."));
                addElementErrorTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    BlobIdElement.create("a", "c"),
                    new AlreadyExistsException("A BlobIdElement already exists in this MutableBlobId with the element type \"a\"."));

                runner.test("with non-existing blobIdElementType", (Test test) ->
                {
                    final MutableBlobId blobId = MutableBlobId.create();

                    final MutableBlobId addElementResult = blobId.addElement(BlobIdElement.create("a", "b"));
                    test.assertSame(blobId, addElementResult);
                    test.assertEqual(
                        Iterable.create(
                            BlobIdElement.create("a", "b")),
                        blobId.iterateElements().toList());

                    blobId.addElement(BlobIdElement.create("A", "B"));
                    test.assertEqual(
                        Iterable.create(
                            BlobIdElement.create("a", "b"),
                            BlobIdElement.create("A", "B")),
                        blobId.iterateElements().toList());
                });
            });

            runner.testGroup("equals(Object)", () ->
            {
                final Action3<MutableBlobId,Object,Boolean> equalsTest = (MutableBlobId blobId, Object rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(blobId, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, blobId.equals(rhs));
                    });
                };

                equalsTest.run(
                    MutableBlobId.create(),
                    null,
                    false);
                equalsTest.run(
                    MutableBlobId.create(),
                    "hello",
                    false);
                equalsTest.run(
                    MutableBlobId.create(),
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create(),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("A", "B"),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("a", "c"),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d"),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    true);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d"),
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d"),
                    true);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("c", "d")
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d"),
                    true);
            });

            runner.testGroup("equals(BlobId)", () ->
            {
                final Action3<MutableBlobId,BlobId,Boolean> equalsTest = (MutableBlobId blobId, BlobId rhs, Boolean expected) ->
                {
                    runner.test("with " + English.andList(blobId, rhs), (Test test) ->
                    {
                        test.assertEqual(expected, blobId.equals(rhs));
                    });
                };

                equalsTest.run(
                    MutableBlobId.create(),
                    null,
                    false);
                equalsTest.run(
                    MutableBlobId.create(),
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create(),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("A", "B"),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("a", "c"),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d"),
                    false);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("a", "b"),
                    true);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d"),
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d"),
                    true);
                equalsTest.run(
                    MutableBlobId.create()
                        .addElement("c", "d")
                        .addElement("a", "b"),
                    MutableBlobId.create()
                        .addElement("a", "b")
                        .addElement("c", "d"),
                    true);
            });

            runner.test("hashCode()", (Test test) ->
            {
                final MutableBlobId blobId1 = MutableBlobId.create();
                final int blobId1HashCode = blobId1.hashCode();
                test.assertEqual(blobId1HashCode, blobId1.hashCode());
                test.assertEqual(blobId1HashCode, MutableBlobId.create().hashCode());

                final MutableBlobId blobId2 = MutableBlobId.create()
                    .addElement("a", "b");
                final int blobId2HashCode = blobId2.hashCode();
                test.assertEqual(blobId2HashCode, blobId2.hashCode());
                test.assertNotEqual(blobId1HashCode, blobId2HashCode);

                final MutableBlobId blobId3 = MutableBlobId.create()
                    .addElement("a", "b")
                    .addElement("c", "d");
                final int blobId3HashCode = blobId3.hashCode();
                test.assertEqual(blobId3HashCode, blobId3.hashCode());
                test.assertNotEqual(blobId1HashCode, blobId3HashCode);
                test.assertNotEqual(blobId2HashCode, blobId3HashCode);

                final MutableBlobId blobId4 = MutableBlobId.create()
                    .addElement("c", "d")
                    .addElement("a", "b");
                final int blobId4HashCode = blobId4.hashCode();
                test.assertEqual(blobId3HashCode, blobId4HashCode);
            });
        });
    }
}
