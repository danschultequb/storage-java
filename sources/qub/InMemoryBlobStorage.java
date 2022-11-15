package qub;

/**
 * A {@link BlobStorage} implementation that is completely in-memory.
 */
public class InMemoryBlobStorage implements BlobStorage
{
    private MutableMap<BlobId,byte[]> blobIdToContentsMap;
    private final List<Function0<? extends BlobIdElementCreator>> blobIdElementCreatorFunctions;

    private InMemoryBlobStorage()
    {
        this.blobIdToContentsMap = MutableMap.create();
        this.blobIdElementCreatorFunctions = List.create();
    }

    /**
     * Create a new {@link InMemoryBlobStorage} object.
     */
    public static InMemoryBlobStorage create()
    {
        return new InMemoryBlobStorage();
    }

    @Override
    public Result<Integer> getBlobCount()
    {
        return Result.create(() ->
        {
            return this.blobIdToContentsMap.getCount();
        });
    }

    /**
     * Get the number of {@link BlobIdElementCreator}s that have been registered with this
     * {@link InMemoryBlobStorage}.
     */
    public int getBlobIdElementCreatorCount()
    {
        return this.blobIdElementCreatorFunctions.getCount();
    }

    /**
     * Add a new {@link BlobIdElement} type to this {@link InMemoryBlobStorage}. This function will
     * update all the existing {@link Blob}s in this {@link InMemoryBlobStorage} to include the
     * latest {@link BlobIdElement} that this new {@link Function0} adds.
     * @param blobIdElementCreatorFunction The new {@link BlobIdElementCreator} {@link Function0} to
     *                                     add.
     */
    public Result<Void> addBlobIdElementCreatorFunction(Function0<? extends BlobIdElementCreator> blobIdElementCreatorFunction)
    {
        PreCondition.assertNotNull(blobIdElementCreatorFunction, "blobIdElementCreatorFunction");

        return Result.create(() ->
        {
            this.blobIdElementCreatorFunctions.add(blobIdElementCreatorFunction);

            final MutableMap<BlobId,byte[]> newBlobIdToContentsMap = Map.create();
            for (final byte[] contents : this.blobIdToContentsMap.iterateValues())
            {
                final BlobId newBlobId = this.generateBlobId(contents).await();
                newBlobIdToContentsMap.set(newBlobId, contents);
            }

            this.blobIdToContentsMap = newBlobIdToContentsMap;
        });
    }

    @Override
    public Result<Boolean> blobExists(BlobId blobId)
    {
        BlobStorage.assertNotNullAndNotEmpty(blobId, "blobId");

        return Result.create(() ->
        {
            return this.blobIdToContentsMap.containsKey(blobId);
        });
    }

    @Override
    public Iterator<Blob> iterateBlobs()
    {
        return this.blobIdToContentsMap.iterateKeys()
            .map(this::getBlob);
    }

    private Result<byte[]> getBlobContentsArray(BlobId blobId)
    {
        BlobStorage.assertNotNullAndNotEmpty(blobId, "blobId");

        return this.blobIdToContentsMap.get(blobId)
            .convertError(NotFoundException.class, () -> new BlobNotFoundException(this.getBlob(blobId)));
    }

    @Override
    public Result<Long> getBlobByteCount(BlobId blobId)
    {
        BlobStorage.assertNotNullAndNotEmpty(blobId, "blobId");

        return Result.create(() ->
        {
            final byte[] blobContents = this.getBlobContentsArray(blobId).await();
            final long result = blobContents.length;

            PostCondition.assertGreaterThanOrEqualTo(result, 0, "result");

            return result;
        });
    }

    @Override
    public Result<ByteReadStream> getBlobContents(BlobId blobId)
    {
        BlobStorage.assertNotNullAndNotEmpty(blobId, "blobId");

        return Result.create(() ->
        {
            final byte[] blobContents = this.getBlobContentsArray(blobId).await();
            return InMemoryByteStream.create(blobContents).endOfStream();
        });
    }

    private Result<BlobId> generateBlobId(byte[] blobContents)
    {
        return Result.create(() ->
        {
            BlobId result;
            try (final InMemoryByteStream blobContentsStream = InMemoryByteStream.create(blobContents).endOfStream())
            {
                final Tuple2<BlobId,byte[]> blobIdAndContents = this.generateBlobId(blobContentsStream).await();
                result = blobIdAndContents.getValue1();
            }

            PostCondition.assertNotNull(result, "result");

            return result;
        });
    }

    private Result<Tuple2<BlobId,byte[]>> generateBlobId(ByteReadStream blobContents)
    {
        return Result.create(() ->
        {
            Tuple2<BlobId,byte[]> result;

            final Iterable<? extends BlobIdElementCreator> blobIdElementCreators = this.blobIdElementCreatorFunctions.map(Function0::run).toList();
            try (final BlobIdCreatorByteReadStream blobIdReadStream = BlobIdCreatorByteReadStream.create(blobContents, blobIdElementCreators))
            {
                final byte[] blobContentsArray = blobIdReadStream.readAllBytes().await();
                final BlobId blobId = blobIdReadStream.takeBlobId();
                result = Tuple.create(blobId, blobContentsArray);
            }

            PostCondition.assertNotNull(result, "result");

            return result;
        });
    }

    @Override
    public Result<Blob> createBlob(ByteReadStream blobContents)
    {
        PreCondition.assertNotNull(blobContents, "blobContents");

        return Result.create(() ->
        {
            final Tuple2<BlobId,byte[]> blobIdAndContents = this.generateBlobId(blobContents).await();
            final byte[] blobContentsArray = blobIdAndContents.getValue2();
            final BlobId blobId = blobIdAndContents.getValue1();

            if (this.blobIdToContentsMap.containsKey(blobId))
            {
                throw new BlobAlreadyExistsException(this.getBlob(blobId));
            }
            this.blobIdToContentsMap.set(blobId, blobContentsArray);

            final Blob result = this.getBlob(blobId);

            PostCondition.assertNotNull(result, "result");

            return result;
        });
    }
}
