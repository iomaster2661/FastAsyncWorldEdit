package com.boydti.fawe.object.regions;

import com.boydti.fawe.object.HasFaweQueue;
import com.boydti.fawe.object.collection.BlockVectorSet;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.function.RegionFunction;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.visitor.RecursiveVisitor;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.AbstractRegion;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldedit.world.World;
import java.util.Iterator;

public class FuzzyRegion extends AbstractRegion {

    private final Mask mask;
    private BlockVectorSet set = new BlockVectorSet();
    private boolean populated;
    private int minX, minY, minZ, maxX, maxY, maxZ;
    private Extent extent;
    private int count = 0;

    {
        minX = minY = minZ = Integer.MAX_VALUE;
        maxX = maxY = maxZ = Integer.MIN_VALUE;
    }

    public FuzzyRegion(World world, Extent editSession, Mask mask) {
        super(world);
        this.extent = editSession;
        this.mask = mask;
    }

    public Mask getMask() {
        return mask;
    }

    @Override
    public int getArea() {
        return set.size();
    }

    public void select(int x, int y, int z) {
        RecursiveVisitor search = new RecursiveVisitor(mask, new RegionFunction() {
            @Override
            public boolean apply(BlockVector3 p) throws WorldEditException {
                setMinMax(p.getBlockX(), p.getBlockY(), p.getBlockZ());
                return true;
            }
        }, 256, extent instanceof HasFaweQueue ? (HasFaweQueue) extent : null);
        search.setVisited(set);
        search.visit(new BlockVector3(x, y, z));
        Operations.completeBlindly(search);
    }

    @Override
    public Iterator<BlockVector3> iterator() {
        return (Iterator) set.iterator();
    }

    private final void setMinMax(int x, int y, int z) {
        if (x > maxX) {
            maxX = x;
        }
        if (x < minX) {
            minX = x;
        }
        if (z > maxZ) {
            maxZ = z;
        }
        if (z < minZ) {
            minZ = z;
        }
        if (y > maxY) {
            maxY = y;
        }
        if (y < minY) {
            minY = y;
        }
    }

    public final void set(int x, int y, int z) throws RegionOperationException {
        set.add(x, y, z);
        setMinMax(x, y, z);
    }

    public boolean contains(int x, int y, int z) {
        return set.contains(x, y, z);
    }

    @Override
    public BlockVector3 getMinimumPoint() {
        return new BlockVector3(minX, minY, minZ);
    }

    @Override
    public BlockVector3 getMaximumPoint() {
        return new BlockVector3(maxX, maxY, maxZ);
    }

    @Override
    public void expand(BlockVector3... changes) throws RegionOperationException {
        throw new RegionOperationException("Selection cannot expand");
    }

    @Override
    public void contract(BlockVector3... changes) throws RegionOperationException {
        throw new RegionOperationException("Selection cannot contract");
    }

    @Override
    public boolean contains(BlockVector3 position) {
        return contains(position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }

    @Override
    public void shift(BlockVector3 change) throws RegionOperationException {
        throw new RegionOperationException("Selection cannot be shifted");
    }

    public void setExtent(EditSession extent) {
        this.extent = extent;
    }
}
