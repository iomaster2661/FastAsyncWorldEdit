/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.history.change;

<<<<<<< HEAD
import com.sk89q.worldedit.BlockVector;
=======
import static com.google.common.base.Preconditions.checkNotNull;

>>>>>>> 399e0ad5... Refactor vector system to be cleaner
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.history.UndoContext;
<<<<<<< HEAD

import static com.google.common.base.Preconditions.checkNotNull;
=======
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockStateHolder;
>>>>>>> 399e0ad5... Refactor vector system to be cleaner

/**
 * Represents a block change that may be undone or replayed.
 *
 * <p>This block change does not have an {@link Extent} assigned to it because
 * one will be taken from the passed {@link UndoContext}. If the context
 * does not have an extent (it is null), cryptic errors may occur.</p>
 */
public class BlockChange implements Change {

    private final BlockVector3 position;
    private final BlockStateHolder previous;
    private final BlockStateHolder current;

    /**
     * Create a new block change.
     *
     * @param position the position
     * @param previous the previous block
     * @param current the current block
     */
    public BlockChange(BlockVector3 position, BlockStateHolder previous, BlockStateHolder current) {
        checkNotNull(position);
        checkNotNull(previous);
        checkNotNull(current);
        this.position = position;
        this.previous = previous;
        this.current = current;
    }

    /**
     * Get the position.
     *
     * @return the position
     */
    public BlockVector3 getPosition() {
        return position;
    }

    /**
     * Get the previous block.
     *
     * @return the previous block
     */
    public BlockStateHolder getPrevious() {
        return previous;
    }

    /**
     * Get the current block.
     *
     * @return the current block
     */
    public BlockStateHolder getCurrent() {
        return current;
    }

    @Override
    public void undo(UndoContext context) throws WorldEditException {
        checkNotNull(context.getExtent()).setBlock(position, previous);
    }

    @Override
    public void redo(UndoContext context) throws WorldEditException {
        checkNotNull(context.getExtent()).setBlock(position, current);
    }

}
