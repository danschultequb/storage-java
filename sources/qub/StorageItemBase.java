package qub;

public abstract class StorageItemBase implements StorageItem
{
    @Override
    public boolean equals(Object rhs)
    {
        return rhs instanceof StorageItem && this.equals((StorageItem)rhs);
    }
}
