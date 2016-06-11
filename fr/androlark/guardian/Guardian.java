package fr.androlark.guardian;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class Guardian
  extends JavaPlugin
{
  public File avert = new File(getDataFolder(), "avert.yml");
  
  public YamlConfiguration loadavert = YamlConfiguration.loadConfiguration(this.avert);
  
  String prefix = "§7[§e§lGUARDIAN§7] " + ChatColor.RESET;
  
  public HashMap<Player, Integer> warning = new HashMap<Player, Integer>();
  
  public HashMap<Player, Integer> npca = new HashMap();
  
  private ProtocolManager protocolManager;
  
  public HackChecker hackChecker = new HackChecker(this);
  
  
  public void removeNPC(final NPC npc, final Player p)
  {
    BukkitTask task2 = new BukkitRunnable()
    {
      public void run()
      {
        Guardian.this.npca.remove(p);
        npc.remove();
      }
    }.runTaskLater(this, 6L);
  }
  
  public void onLoad()
  {
    this.protocolManager = ProtocolLibrary.getProtocolManager();
  }
  
  public void onEnable()
  {
    if (!this.avert.exists()) {
      try
      {
        this.avert.createNewFile();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    final Player p = (Player)sender;
    if (command.getName().equalsIgnoreCase("guardian")) {
      if ((args.length == 0) || (args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?")))
      {
        Messages.sendMessage(p, "help");
      }
      else
      {
        if ((args[0].equalsIgnoreCase("ff")) && (p.hasPermission("guardian.check.forcefield")))
        {
          hackChecker.checkForForcefield(p, args);
        }
        
        if ((args[0].equalsIgnoreCase("speedhack")) && (p.hasPermission("guardian.check.speedhack")))
        {
           hackChecker.checkForSpeedhack(p, args);
        }
        
        if ((args[0].equalsIgnoreCase("autoclick")) && (p.hasPermission("guardian.check.autoclick")))
        {
           hackChecker.checkForAutoclick(p, args);
        }
              
        if ((args[0].equalsIgnoreCase("reset")) && (p.hasPermission("guardian.reset")))
        {
        	hackChecker.resetStats(p, args, loadavert, avert);
        }
        
      }
    }
    return false;
  }
  
  /**
   *     BukkitTask task1 = new BukkitRunnable()
    {
      public void run()
      {
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
        for (int i = 0; i < j; i++)
        {
          Player online = arrayOfPlayer[i];
          
          World world = online.getWorld();
          Location loc = online.getLocation();
          
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
          String name = "";
          int id = new Random().nextInt(4000) + 1000;
          
          int itemInHandId = online.getItemInHand().getTypeId();
          NPC npc = new NPC(world, name, id, loc, itemInHandId);
          Guardian.this.npca.put(online, Integer.valueOf(npc.id));
          Guardian.this.removeNPC(npc, online);
        }
      }
    }.runTaskTimer(this, 0L, 12000L);
    
    Bukkit.getPluginManager().registerEvents(new EventsClass(this), this);
    ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Client.USE_ENTITY })
    {
      public void onPacketReceiving(PacketEvent event)
      {
        if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
          try
          {
            int entityID = ((Integer)event.getPacket().getIntegers().read(0)).intValue();
            Player p = event.getPlayer();
            if (!Guardian.this.warning.containsKey(event.getPlayer()))
            {
              PacketContainer packet = event.getPacket();
              if (entityID == ((Integer)Guardian.this.npca.get(p)).intValue())
              {
                World world = p.getWorld();
                String name = "";
                int tmp = (int)(Math.random() * 2.0D + 1.0D);
                int id = new Random().nextInt(4000) + 1000;
                Location location = new Location(p.getWorld(), p.getLocation().getX() + tmp, p.getLocation().getY() + tmp, p.getLocation().getZ() + tmp);
                int itemInHandId = p.getItemInHand().getTypeId();
                NPC npc = new NPC(world, name, id, location, itemInHandId);
                
                Guardian.this.removeNPC2(npc, p);
                Guardian.this.warning.put(p, Integer.valueOf(1));
              }
            }
            else if (((Integer)Guardian.this.warning.get(p)).intValue() < 5)
            {
              Guardian.this.warning.put(p, Integer.valueOf(((Integer)Guardian.this.warning.get(p)).intValue() + 1));
              
              World world = p.getWorld();
              String name = "";
              int tmp = (int)(Math.random() * 2.0D + 1.0D);
              int id = new Random().nextInt(4000) + 1000;
              Location location = new Location(p.getWorld(), p.getLocation().getX() + tmp, p.getLocation().getY() + tmp, p.getLocation().getZ() + tmp);
              int itemInHandId = p.getItemInHand().getTypeId();
              NPC npc = new NPC(world, name, id, location, itemInHandId);
              Guardian.this.removeNPC2(npc, p);
            }
            else if (((Integer)Guardian.this.warning.get(p)).intValue() == 5)
            {
              Guardian.this.warning.remove(p);
              
              Guardian.this.kickplayerff(p);
            }
          }
          catch (Exception localException) {}
        }
      }
    });
    **/
}
