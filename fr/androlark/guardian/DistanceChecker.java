package fr.androlark.guardian;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DistanceChecker implements Runnable{
	
	public Guardian guardian;
	public Location botLoc;
	public NPC npc;
	public Player p;
	
	public DistanceChecker(Guardian g, Location bLoc, NPC bot, Player sender){
		guardian = g;
		botLoc = bLoc;
		npc = bot;
		p = sender;
	}
	
	@Override
	public void run() {
		try
		{
			Thread.sleep(3000L);
			
			System.out.println(p.getLocation().distance(botLoc));
			if(p.getLocation().distance(botLoc) >= 25)
			{
				p.sendMessage("§7[§e§lGUARDIAN§7] Résultat du test: §epositif§7.");
				if(guardian.warning.containsKey(p)){
					int warnings = guardian.warning.get(p);
					guardian.warning.put(p, warnings + 1);
					p.sendMessage("§7[§e§lGUARDIAN§7] Avertissements sur le joueur: §e" + warnings + "§7.");
				}
				else{
					guardian.warning.put(p, 1);
					p.sendMessage("§7[§e§lGUARDIAN§7] §eAucun §7antécédent de triche, ajout d'un §eavertissement§7.");
				}
				if(guardian.warning.get(p).intValue() >= 3){
					System.out.println("Ban.");
				}
			}
			else{
				p.sendMessage("§7[§e§lGUARDIAN§7] Résultat du test: §enégatif§7.");
			}
            guardian.removeNPC(npc, p);
			new Thread(this).stop();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
