package com.blendycat.landclaim.listeners;

import com.blendycat.landclaim.LandClaim;
import com.blendycat.landclaim.land.Plot;
import com.blendycat.landclaim.land.WorldManager;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by EvanMerz on 1/6/17.
 */
public class PlotListener implements Listener {

    private WorldManager wm;

    public PlotListener(LandClaim lc){
        wm = lc.getWorldManager();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(e.getBlock() == null) return;
        Chunk chunk = e.getBlock().getChunk();
        Player player = e.getPlayer();
        Plot plot;
        if((plot = wm.loadPlot(chunk)) == null){
            e.setCancelled(true);
            player.sendMessage(ChatColor.DARK_RED +
                    "You are not allowed to break blocks on unclaimed plots!" +
                    " Type /claim if you desire to claim this plot");
            return;
        }
        OfflinePlayer owner = plot.getOwner();
        if(!owner.getUniqueId().equals(player.getUniqueId())){
            e.setCancelled(true);
            player.sendMessage(ChatColor.DARK_RED + owner.getName() +
                    " does not allow you to break blocks on their plot");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getClickedBlock() == null) return;
        Chunk chunk = e.getClickedBlock().getChunk();
        Player player = e.getPlayer();
        Plot plot;
        if((plot = wm.loadPlot(chunk)) == null){
            e.setCancelled(true);
            player.sendMessage(ChatColor.DARK_RED +
                    "You are not allowed to interact on unclaimed plots!" +
                    " Type /claim if you desire to claim this plot");
            return;
        }
        OfflinePlayer owner = plot.getOwner();
        if(!owner.getUniqueId().equals(player.getUniqueId())){
            e.setCancelled(true);
            player.sendMessage(ChatColor.DARK_RED + owner.getName() +
                    " does not allow you to interact on their plot");
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e){

        Chunk chunk = e.getBlock().getChunk();
        Player player = e.getPlayer();
        Plot plot;
        if((plot = wm.loadPlot(chunk)) == null){
            e.setCancelled(true);
            player.sendMessage(ChatColor.DARK_RED +
                    "You are not allowed to place blocks on unclaimed plots!" +
                    " Type /claim if you desire to claim this plot");
            return;
        }
        OfflinePlayer owner = plot.getOwner();
        if(!owner.getUniqueId().equals(player.getUniqueId())){
            e.setCancelled(true);
            player.sendMessage(ChatColor.DARK_RED + owner.getName() +
                    " does not allow you to place blocks on their plot");
        }
    }
}
