package com.boydti.fawe.object.extent;

import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.MutableBlockVector;
import com.sk89q.worldedit.math.MutableVector;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.math.transform.Transform;
import com.sk89q.worldedit.world.biome.BaseBiome;
import com.sk89q.worldedit.world.block.BlockStateHolder;

public class PositionTransformExtent extends ResettableExtent {

    private transient MutableBlockVector mutable = new MutableBlockVector();
    private transient BlockVector3 min;
    private Transform transform;

    public PositionTransformExtent(Extent parent, Transform transform) {
        super(parent);
        this.transform = transform;
    }

    @Override
    public ResettableExtent setExtent(Extent extent) {
        mutable = new MutableBlockVector();
        min = null;
        return super.setExtent(extent);
    }

    public void setOrigin(BlockVector3 pos) {
        this.min = pos;
    }

    private BlockVector3 getPos(BlockVector3 pos) {
        if (min == null) {
            min = pos;
        }
        mutable.mutX(((pos.getX() - min.getX())));
        mutable.mutY(((pos.getY() - min.getY())));
        mutable.mutZ(((pos.getZ() - min.getZ())));
        MutableVector tmp = new MutableVector(transform.apply(mutable.toVector3()));
        tmp.mutX((tmp.getX() + min.getX()));
        tmp.mutY((tmp.getY() + min.getY()));
        tmp.mutZ((tmp.getZ() + min.getZ()));
        return tmp.toBlockPoint();
    }

    private BlockVector3 getPos(int x, int y, int z) {
        if (min == null) {
            min = new BlockVector3(x, y, z);
        }
        mutable.mutX(((x - min.getX())));
        mutable.mutY(((y - min.getY())));
        mutable.mutZ(((z - min.getZ())));
        MutableVector tmp = new MutableVector(transform.apply(mutable.toVector3()));
        tmp.mutX((tmp.getX() + min.getX()));
        tmp.mutY((tmp.getY() + min.getY()));
        tmp.mutZ((tmp.getZ() + min.getZ()));
        return tmp.toBlockPoint();
    }

    @Override
    public BlockState getLazyBlock(int x, int y, int z) {
        return super.getLazyBlock(getPos(x, y, z));
    }

    @Override
    public BlockState getLazyBlock(BlockVector3 position) {
        return super.getLazyBlock(getPos(position));
    }

    @Override
    public BlockState getBlock(BlockVector3 position) {
        return super.getBlock(getPos(position));
    }

    @Override
    public BaseBiome getBiome(BlockVector2 position) {
        mutable.mutX(position.getBlockX());
        mutable.mutZ(position.getBlockZ());
        mutable.mutY(0);
        return super.getBiome(getPos(mutable).toBlockVector2());
    }

    @Override
    public boolean setBlock(int x, int y, int z, BlockStateHolder block) throws WorldEditException {
        return super.setBlock(getPos(x, y, z), block);
    }


    @Override
    public boolean setBlock(BlockVector3 location, BlockStateHolder block) throws WorldEditException {
        return super.setBlock(getPos(location), block);
    }

    @Override
    public boolean setBiome(BlockVector2 position, BaseBiome biome) {
        mutable.mutX(position.getBlockX());
        mutable.mutZ(position.getBlockZ());
        mutable.mutY(0);
        return super.setBiome(getPos(mutable).toBlockVector2(), biome);
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
