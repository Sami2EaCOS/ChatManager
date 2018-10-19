package fr.smourad.chatmanager.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class EntityDamageByEntity implements Listener {
	
	ChatManager main;
	
	public EntityDamageByEntity(ChatManager plugin) {
		this.main = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamaging(EntityDamageByEntityEvent e) {		
		Entity damager = e.getDamager();
		Entity damaged = e.getEntity();
		
		double damageGet = e.getFinalDamage();
		e.isCancelled();
		
		if (damager instanceof Player) {
			Player p = (Player) damager;
			String pName = p.getName();
			
			main.playerDataLoad(pName, p);
			PlayerData pfile = ChatManager.PlayerDataMap.get(p);
			
			if (!(pfile.getDouble("Stats.strength") < 10)) {
				damageGet += pfile.getDouble("Stats.strength")*0.2;
			}
		}
		
		if (damager instanceof Arrow) {
			Arrow a = (Arrow) damager;
			
			if (a.getShooter() instanceof Player) {
				Player dam = (Player) a.getShooter();
				String damName = dam.getName();
				
				main.playerDataLoad(damName, dam);
				PlayerData damfile = ChatManager.PlayerDataMap.get(dam);
				
				if (damfile.getString("Player.race").equalsIgnoreCase("elfe")) {
					damageGet += 1;
				}
				
			}
			
			if (damaged instanceof Player) {
				Player dama = (Player) damaged;
				
				main.playerDataLoad(dama.getName(), dama);
				PlayerData damafile = ChatManager.PlayerDataMap.get(dama);
				
				if (damafile.getString("Player.race").equalsIgnoreCase("gobelin")) {
					int random = 1 + (int) (Math.random() * ((10 - 1)+1));
						
					if (random <= 1) {
						dama.sendMessage(ChatColor.ITALIC + "" + ChatColor.LIGHT_PURPLE + "Vous avez esquivé ce tir");
						damageGet = 0;
						e.setCancelled(true);
					}
				}
			}
		}
		
		// TRUC
		
		if (damaged instanceof Player) {
			Player player = (Player) damaged;
			String playerName = player.getName();
			
			main.playerDataLoad(playerName, player);
			PlayerData playerfile = ChatManager.PlayerDataMap.get(player);
			
			if (playerfile.getString("Player.race").equalsIgnoreCase("transcende")) {
				damageGet -= 2;
			}
			if (playerfile.getString("Player.race").equalsIgnoreCase("nain")) {
				damageGet -= 1;
			}
		}
		
		e.setDamage(damageGet);
	}
}
