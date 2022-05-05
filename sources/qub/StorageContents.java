package qub;

/**
 * Contents that have been assigned to a {@link StorageItem}.
 */
public interface StorageContents
{
    /**
     * Get the {@link DateTime} when these attributes were set.
     */
    public DateTime getTimestamp();

    /**
     * Get the {@link CharacterEncoding} that was used when writing these contents. Null will be
     * returned if no {@link CharacterEncoding} was used when writing these contents (such as for
     * raw binary content).
     */
    public CharacterEncoding getEncoding();

    /**
     * Get the size in bytes of these contents.
     */
    public long getSize();

    /**
     * Get the {@link CharacterToByteReadStream} to these contents.
     */
    public Result<CharacterToByteReadStream> getReadStream();
}
