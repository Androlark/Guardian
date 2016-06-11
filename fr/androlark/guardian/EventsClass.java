package fr.androlark.guardian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class EventsClass
  implements Listener
{
  public static Guardian plugin;
  ArrayList<Player> immunite = new ArrayList();
  
  public EventsClass(Guardian guardian)
  {
    plugin = guardian;
  }
  
  public static void kickPlayerForSpeed(final Player p){
	  p.getPlayer().kickPlayer("&7Kické du serveur: &eSpeedhack");
	  Bukkit.broadcastMessage(EventsClass.plugin.prefix + ChatColor.YELLOW + p.getName() +  ChatColor.GRAY + " a été éjecté pour : " + ChatColor.YELLOW + "Speedhack");
  }
  
  public static void kickPlayerForFly(final Player p)
  {
	  p.kickPlayer("&7Kické du serveur: &eFly");
	  Bukkit.broadcastMessage(EventsClass.plugin.prefix + ChatColor.YELLOW + p.getName() +  ChatColor.GRAY + " a été éjecté pour : " + ChatColor.YELLOW + "Fly");
  }
  
  @EventHandler
  public void attackFakeplayer(PlayerMoveEvent e)
  {
    final Player p = e.getPlayer();
    double lvl = e.getTo().distance(p.getLocation());
    if ((!plugin.getConfig().getStringList("disabledworlds").contains(p.getWorld().getName())) && 
      (!p.hasPermission("guardian.immune")))
    {
      Location to = e.getTo();
      Location from = e.getFrom();
      
      Vector vec = new Vector(to.getX(), to.getY(), to.getZ());
      
      double i = vec.distance(new Vector(from.getX(), from.getY(), 
        from.getZ()));
      if (p.getGameMode().equals(GameMode.SURVIVAL)) {
        if (p.getLocation().getBlock().getRelative(BlockFace.DOWN)
          .getType() == Material.SPONGE)
        {
          this.immunite.add(p);
          BukkitTask localBukkitTask = new BukkitRunnable()
          {
            public void run()
            {
              EventsClass.this.immunite.remove(p);
            }
          }.runTaskLater(plugin, 200L);
        }
        else if (((e.getTo().getY() - e.getFrom().getY() == 1.0D) || ((e.getTo().getY() - e.getFrom().getY() >= 0.7499D) && (e.getTo().getY() - e.getFrom().getY() <= 0.75D))) && 
          (!this.immunite.contains(p)) && 
          (p.getFallDistance() == 0.0F))
        {
          Location loc = e.getPlayer().getLocation();
          if (loc.getBlock().getRelative(BlockFace.DOWN)
            .getType() == Material.AIR)
          {
            e.setCancelled(true);
            
            kickPlayerForFly(p);
          }
        }
      }
    }
  }
}
