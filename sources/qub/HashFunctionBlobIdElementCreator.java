package qub;

/**
 * A {@link BlobIdElementCreator} implementation that uses a {@link HashFunction} to compute the
 * {@link BlobIdElement}'s value.
 */
public class HashFunctionBlobIdElementCreator implements BlobIdElementCreator
{
    private final String blobIdElementType;
    private final HashFunction hashFunction;

    private HashFunctionBlobIdElementCreator(String blobIdElementType, HashFunction hashFunction)
    {
        PreCondition.assertNotNullAndNotEmpty(blobIdElementType, "blobIdElementType");
        PreCondition.assertNotNull(hashFunction, "hashFunction");

        this.blobIdElementType = blobIdElementType;
        this.hashFunction = hashFunction;
    }

    public static HashFunctionBlobIdElementCreator create(String blobIdElementType, HashFunction hashFunction)
    {
        return new HashFunctionBlobIdElementCreator(blobIdElementType, hashFunction);
    }

    public static HashFunctionBlobIdElementCreator createMD5()
    {
        return HashFunctionBlobIdElementCreator.create("MD5", HashFunction.createMD5().await());
    }

    public static HashFunctionBlobIdElementCreator createSHA1()
    {
        return HashFunctionBlobIdElementCreator.create("SHA1", HashFunction.createSHA1().await());
    }

    public static HashFunctionBlobIdElementCreator createSHA256()
    {
        return HashFunctionBlobIdElementCreator.create("SHA256", HashFunction.createSHA256().await());
    }

    @Override
    public String getBlobIdElementType()
    {
        return this.blobIdElementType;
    }

    @Override
    public void addByte(byte value)
    {
        this.hashFunction.addByte(value);
    }

    @Override
    public void addBytes(byte[] values, int startIndex, int length)
    {
        this.hashFunction.addBytes(values, startIndex, length);
    }

    @Override
    public BlobIdElement takeBlobIdElement()
    {
        final String blobIdElementType = this.getBlobIdElementType();

        final BitArray hashDigest = this.hashFunction.takeDigest();
        final String blobIdElementValue = hashDigest.toHexString();

        final BlobIdElement result = BlobIdElement.create(blobIdElementType, blobIdElementValue);

        PostCondition.assertNotNull(result, "result");

        return result;
    }

    @Override
    public boolean isDisposed()
    {
        return this.hashFunction.isDisposed();
    }

    @Override
    public Result<Boolean> dispose()
    {
        return this.hashFunction.dispose();
    }
}
