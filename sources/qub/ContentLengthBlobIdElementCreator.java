package qub;

public class ContentLengthBlobIdElementCreator implements BlobIdElementCreator
{
    private long contentLength;
    private boolean disposed;

    private ContentLengthBlobIdElementCreator()
    {
    }

    public static ContentLengthBlobIdElementCreator create()
    {
        return new ContentLengthBlobIdElementCreator();
    }

    @Override
    public String getBlobIdElementType()
    {
        return "ContentLength";
    }

    @Override
    public void addByte(byte value)
    {
        PreCondition.assertNotDisposed(this, "this");

        this.contentLength += 1;
    }

    @Override
    public void addBytes(byte[] values, int startIndex, int length)
    {
        PreCondition.assertNotNull(values, "values");
        PreCondition.assertStartIndex(startIndex, values.length);
        PreCondition.assertLength(length, startIndex, values.length);
        PreCondition.assertNotDisposed(this, "this");

        this.contentLength += length;
    }

    @Override
    public BlobIdElement takeBlobIdElement()
    {
        PreCondition.assertNotDisposed(this, "this");

        final String blobIdElementType = this.getBlobIdElementType();

        final String blobIdElementValue = Longs.toString(this.contentLength);
        this.contentLength = 0;

        final BlobIdElement result = BlobIdElement.create(blobIdElementType, blobIdElementValue);

        PostCondition.assertNotNull(result, "result");

        return result;
    }

    @Override
    public boolean isDisposed()
    {
        return this.disposed;
    }

    @Override
    public Result<Boolean> dispose()
    {
        return Result.create(() ->
        {
            final boolean result = !this.disposed;
            if (result)
            {
                this.disposed = true;
                this.contentLength = 0;
            }
            return result;
        });
    }
}
