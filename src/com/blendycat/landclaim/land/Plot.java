package com.blendycat.landclaim.land;

import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;

/**
 * Created by EvanMerz on 12/28/16.
 */
public class Plot {
    private Chunk chunk;
    private OfflinePlayer owner;

    public Plot(Chunk chunk, OfflinePlayer player){
        this.chunk = chunk;
        owner = player;
    }

    public Chunk getChunk(){
        return chunk;
    }

    public OfflinePlayer getOwner(){
        return owner;
    }
}
