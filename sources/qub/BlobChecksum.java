package qub;

public class BlobChecksum
{
    private final String checksumType;
    private final BitArray checksumValue;

    private BlobChecksum(String checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNullAndNotEmpty(checksumType, "checksumType");
        PreCondition.assertNotNullAndNotEmpty(checksumValue, "checksumValue");

        this.checksumType = checksumType;
        this.checksumValue = checksumValue;
    }

    public static BlobChecksum create(BlobChecksumType checksumType, BitArray checksumValue)
    {
        PreCondition.assertNotNull(checksumType, "checksumType");

        return BlobChecksum.create(checksumType.toString(), checksumValue);
    }

    public static BlobChecksum create(String checksumType, BitArray checksumValue)
    {
        return new BlobChecksum(checksumType, checksumValue);
    }

    public String getChecksumType()
    {
        return this.checksumType;
    }

    public BitArray getChecksumValue()
    {
        return this.checksumValue;
    }

    @Override
    public String toString()
    {
        return this.checksumType + ":" + this.checksumValue.toHexString();
    }

    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof BlobChecksum && this.equals((BlobChecksum)rhs);
    }

    public boolean equals(BlobChecksum rhs)
    {
        return rhs != null &&
            this.checksumType.equalsIgnoreCase(rhs.checksumType) &&
            this.checksumValue.equals(rhs.checksumValue);
    }
}
