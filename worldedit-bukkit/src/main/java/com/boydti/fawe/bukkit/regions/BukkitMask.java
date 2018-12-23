package com.boydti.fawe.bukkit.regions;

import com.boydti.fawe.regions.FaweMask;
import com.sk89q.worldedit.math.BlockVector3;

import org.bukkit.Location;

public class BukkitMask extends FaweMask {

    public BukkitMask(Location pos1, Location pos2) {
        this(pos1, pos2, null);
    }

    public BukkitMask(Location pos1, Location pos2, String name) {
        super(new BlockVector3(pos1.getBlockX(), pos1.getBlockY(), pos1.getBlockZ()), new BlockVector3(pos2.getBlockX(), pos2.getBlockY(), pos2.getBlockZ()), name);
    }
}