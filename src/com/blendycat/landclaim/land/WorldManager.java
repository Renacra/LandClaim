package com.blendycat.landclaim.land;

import com.blendycat.landclaim.LandClaim;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

/**
 * Created by EvanMerz on 1/4/17.
 */
public class WorldManager {
    private LandClaim claim;

    public WorldManager(LandClaim claim){
        this.claim = claim;
    }

    /**
     * Config Diagram:
     * WORLDNAME-X-Z: UUID of Player
     *
     * @param plot plot that you want to save
     *
     */
    public void savePlot(Plot plot){
        FileConfiguration config = claim.getConfig();

        Chunk chunk = plot.getChunk();
        OfflinePlayer player = plot.getOwner();

        int x = chunk.getX();
        int z = chunk.getZ();
        String world = chunk.getWorld().getName();
        if(player == null){
            config.set(String.format("%s-%d-%d", world, x, z), null);
            return;
        }
        UUID id = player.getUniqueId();

        config.set(String.format("%s-%d-%d", world, x, z), id.toString());
    }

    public Plot loadPlot(Chunk chunk){
        FileConfiguration config = claim.getConfig();
        String key =  String.format("%s-%d-%d",
                chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
        if (!config.contains(key)) return null;
        UUID id = UUID.fromString(config.getString(key));

        OfflinePlayer player = Bukkit.getPlayer(id);
        return new Plot(chunk, player);
    }

}
