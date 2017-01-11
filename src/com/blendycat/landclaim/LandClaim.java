package com.blendycat.landclaim;

import com.blendycat.landclaim.land.Plot;
import com.blendycat.landclaim.land.WorldManager;
import com.blendycat.landclaim.listeners.PlotListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by EvanMerz on 12/28/16.
 */
public class LandClaim extends JavaPlugin {

    private WorldManager wm;

    @Override
    public void onEnable(){
        wm = new WorldManager(this);
        Bukkit.getPluginManager().registerEvents(new PlotListener(this), this);
    }

    @Override
    public void onDisable(){
        saveConfig();
    }

    public WorldManager getWorldManager(){
        return wm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(command.getName().equalsIgnoreCase("claim")){
            Chunk chunk = player.getLocation().getChunk();
            if(wm.loadPlot(chunk) != null  && wm.loadPlot(chunk).getOwner() != null) {
                player.sendMessage(ChatColor.DARK_RED+"This plot has already been claimed!");
                return true;
            }
            Plot plot = new Plot(chunk, player);
            wm.savePlot(plot);
            player.sendMessage(ChatColor.GREEN + "You have claimed this plot!");
            return true;
        }
        else if(command.getName().equalsIgnoreCase("unclaim")){
            Chunk chunk = player.getLocation().getChunk();
            Plot plot = wm.loadPlot(chunk);
            if(plot== null  || plot.getOwner() == null || !plot.getOwner().equals(player)){
                player.sendMessage(ChatColor.DARK_RED+ "You cannot unclaim plots you do not own!");
                return true;
            }
            wm.savePlot(new Plot(chunk, null));
            player.sendMessage(ChatColor.GREEN + "You have unclaimed this plot!");
            return true;
        }
        return false;
    }


}
