package fr.androlark.guardian;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class HackChecker {
	
	public Guardian guardian;
	  
	public HackChecker(Guardian g)
	{
		guardian = g;
	}
	
	
	public void checkForForcefield(Player p, String[] args)
	{
        if (args.length == 2)
        {
          try
          {
            final Player pl = Bukkit.getPlayerExact(args[1]);
            final int id = new Random().nextInt(4000) + 1000;
            
            final int itemInHandId = pl.getItemInHand().getTypeId();
            BukkitTask localBukkitTask1 = new BukkitRunnable()
            {
              public void run()
              {
                World world = pl.getWorld();
                Location loc = pl.getLocation();
                
                int direction = (int)loc.getYaw();
                if (direction < 0)
                {
                  direction += 360;
                  direction = (direction + 45) / 90;
                }
                else
                {
                  direction = (direction + 45) / 90;
                }
                switch (direction)
                {
                case 1: 
                  loc = new Location(world, loc.getX() - 1.0D, loc.getY() - 0.5D, loc.getZ());
                  break;
                case 2: 
                  loc = new Location(world, loc.getX(), loc.getY() - 0.5D, loc.getZ() - 1.0D);
                  break;
                case 3: 
                  loc = new Location(world, loc.getX() - 1.0D, loc.getY() - 0.5D, loc.getZ());
                  break;
                case 4: 
                  loc = new Location(world, loc.getX(), loc.getY() - 0.5D, loc.getZ() - 1.0D);
                  break;
                case 0: 
                  loc = new Location(world, loc.getX(), loc.getY() - 0.5D, loc.getZ() - 1.0D);
                  break;
                }
                try
                {
                  NPC npc = new NPC(world, "", id, loc, itemInHandId);
                  p.sendMessage(ChatColor.GREEN + "Le bot pour la vérification est apparu.");
                  guardian.npca.put(p, Integer.valueOf(npc.id));
                  guardian.removeNPC(npc, pl);
                }
                catch (Exception e)
                {
                  p.sendMessage(ChatColor.RED + "Une erreur s'est produite ! : " + e.getStackTrace());
                }
              }
            }.runTaskLater(guardian, 100L);
          }
          catch (Exception localException) {}
        }
        else if (args.length == 1)
        {
          String name = "";
          final int id = new Random().nextInt(4000) + 1000;
          Location location = p.getLocation();
          final int itemInHandId = p.getItemInHand().getTypeId();
          
          BukkitTask localBukkitTask2 = new BukkitRunnable()
          {
            public void run()
            {
              Player[] arrayOfPlayer;
              int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
              for (int i = 0; i < j; i++)
              {
                Player pl = arrayOfPlayer[i];
                World world = pl.getWorld();
                Location loc = p.getLocation();
                
                int direction = (int)loc.getYaw();
                if (direction < 0)
                {
                  direction += 360;
                  direction = (direction + 45) / 90;
                }
                else
                {
                  direction = (direction + 45) / 90;
                }
                switch (direction)
                {
                case 1: 
                  loc = new Location(world, loc.getX() - 1.0D, loc.getY() - 0.75D, loc.getZ());
                  break;
                case 2: 
                  loc = new Location(world, loc.getX(), loc.getY() - 0.75D, loc.getZ() - 1.0D);
                  break;
                case 3: 
                  loc = new Location(world, loc.getX() - 1.0D, loc.getY() - 0.75D, loc.getZ());
                  break;
                case 4: 
                  loc = new Location(world, loc.getX(), loc.getY() - 0.75D, loc.getZ() - 1.0D);
                  break;
                case 0: 
                  loc = new Location(world, loc.getX(), loc.getY() - 0.75D, loc.getZ() - 1.0D);
                  break;
                }
                try
                {
                  NPC npc = new NPC(world, "", id, loc, itemInHandId);
                  p.sendMessage(ChatColor.GREEN + "Le bot pour la vérification est apparu.");
                  guardian.npca.put(p, Integer.valueOf(npc.id));
                  guardian.removeNPC(npc, pl);
                }
                catch (Exception e)
                {
                  p.sendMessage(ChatColor.RED + "Une erreur s'est produite ! : " + e.getStackTrace());
                }
              }
            }
          }.runTaskLater(guardian, 100L);
        }
        else
        {
          p.sendMessage(ChatColor.RED + "Il vous manque des arguments !");
        }
	}
	
	
	public void resetStats(Player p, String[] args, YamlConfiguration config, File configFile){
        if(args.length >= 2)
        {
          Player pl = Bukkit.getPlayerExact(args[1]);
          OfflinePlayer disconnected = Bukkit.getOfflinePlayer(args[1]);
          if (pl != null)
          {
            if (config.get(pl.getUniqueId().toString()) != null)
            {
              config.set(pl.getUniqueId() + ".avertff", Integer.valueOf(0));
              config.set(pl.getUniqueId() + ".avertfly", Integer.valueOf(0));
              try
              {
                config.save(configFile);
                p.sendMessage(ChatColor.GREEN + "Les avertissements de " + ChatColor.GOLD + pl.getName() + ChatColor.GREEN + " ont été remis é zéro !");
              }
              catch (IOException e)
              {
                p.sendRawMessage("Error :" + e.getStackTrace());
              }
            }
          }
          else if (config.get(disconnected.getUniqueId().toString()) != null)
          {
            config.set(disconnected.getUniqueId() + ".avertff", Integer.valueOf(0));
            config.set(disconnected.getUniqueId() + ".avertfly", Integer.valueOf(0));
            try
            {
            	config.save(configFile);
              p.sendMessage(ChatColor.GREEN + "Les avertissements de " + ChatColor.GOLD + disconnected.getName() + ChatColor.GREEN + " ont été remis à zéro !");
            }
            catch (IOException e)
            {
              p.sendRawMessage("Error :" + e.getStackTrace());
            }
          }
        }
	}
	
	
	public void checkForSpeedhack(Player p, String[] args)

	{
        if (args.length == 2)
        {
          try
          {
            final Player pl = Bukkit.getPlayerExact(args[1]);
            final int id = new Random().nextInt(4000) + 1000;
            
            final int itemInHandId = pl.getItemInHand().getTypeId();

            World playerWorld = pl.getWorld();
            Location playerLoc = pl.getLocation();
                
                try
                {
                  NPC npc = new NPC(playerWorld, "", id, playerLoc, itemInHandId);
                  npc.hideForPlayer(pl);
                  p.sendMessage("§7[§e§lGUARDIAN§7] Vérification en cours..");
                  
                  new Thread(new DistanceChecker(guardian, npc.getLocation(), npc, pl)).start();
                  
                  guardian.npca.put(p, Integer.valueOf(npc.id));
                }
                catch (Exception e)
                {
                  p.sendMessage(ChatColor.RED + "Une erreur s'est produite ! : " + e.getStackTrace());
                }
          }
          catch (Exception localException) {}
        }
        else
        {
          p.sendMessage(ChatColor.RED + "Vous devez entrer le pseudo du joueur !");
        }
      }
	
	public void checkForAutoclick(Player p, String[] args)
	{	
		Bukkit.getServer().dispatchCommand(p, "gverif " + args[1]);
	  }
}
