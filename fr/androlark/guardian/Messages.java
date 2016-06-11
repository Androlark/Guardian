package fr.androlark.guardian;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages
{
  public static void sendMessage(Player p, String type)
  {
    String str;
    switch ((str = type).hashCode())
    {
    case 3198785: 
      if (str.equals("help"))
      {
        p.sendMessage(ChatColor.GRAY + "---------------" + ChatColor.YELLOW + " Guardian " + ChatColor.GRAY + "---------------");
        p.sendMessage(ChatColor.YELLOW + "/guardian - " + ChatColor.GRAY + " Commande pricipale");
        p.sendMessage(ChatColor.YELLOW + "/guardian ff <joueur> - " + ChatColor.GRAY + " Force la v�rification du forcefield sur un joueur.");
        p.sendMessage(ChatColor.YELLOW + "/guardian speedhack <joueur> - " + ChatColor.GRAY + " Force la v�rification du speedhack sur un joueur.");
        p.sendMessage(ChatColor.YELLOW + "/guardian autoclick <joueur> - " + ChatColor.GRAY + " Force la v�rification de l'autoclick sur un joueur.");
        p.sendMessage(ChatColor.YELLOW + "/guardian reset <joueur> - " + ChatColor.GRAY + " Supprime les avertissements pr�sent sur un joueur.");
        p.sendMessage(ChatColor.GRAY + "------------------------------------------");
      }
      break;
    }
  }
}
