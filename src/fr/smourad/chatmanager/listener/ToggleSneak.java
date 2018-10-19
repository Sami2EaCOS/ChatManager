package fr.smourad.chatmanager.listener;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;
import fr.smourad.chatmanager.helping.HashMapBooster;

public class ToggleSneak implements Listener {

	public HashMap<String, HashMapBooster> antiSpam = new HashMap<String, HashMapBooster>();
	
	ChatManager plugin;
	
	public ToggleSneak(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		plugin.playerDataLoad(p.getName(), p);
		
		PlayerData playerFile = ChatManager.PlayerDataMap.get(p);
		
		if (p.getGameMode() == GameMode.CREATIVE) {
			return;
		}
		
		if (playerFile.getString("Player.race").equalsIgnoreCase("arachnide")) {
			if (e.isSneaking() == true) {
				if (p.getLocation().getBlock().getType() == Material.AIR) {
					if (antiSpam.containsKey(p.getName())) {
						long secondsLeft = ((antiSpam.get(p.getName()).getLong()/1000)+3) - (System.currentTimeMillis()/1000);
						if (secondsLeft > 0) {
							antiSpam.put(p.getName(), new HashMapBooster(System.currentTimeMillis(), (antiSpam.get(p.getName()).getNumber())+1));
							if (p.getFoodLevel() >= 1*Math.pow(2, antiSpam.get(p.getName()).getNumber())) {
								p.getLocation().getBlock().setType(Material.WEB);
								p.setFoodLevel((int) (p.getFoodLevel()-1*Math.pow(2, antiSpam.get(p.getName()).getNumber())));
							}
						} else {
							antiSpam.put(p.getName(), new HashMapBooster(System.currentTimeMillis(), 0));
							if (p.getFoodLevel() >= 1) {
								p.getLocation().getBlock().setType(Material.WEB);
								p.setFoodLevel(p.getFoodLevel()-1);
							}
						}
						
					} else {
						antiSpam.put(p.getName(), new HashMapBooster(System.currentTimeMillis(), 0));
						if (p.getFoodLevel() >= 1) {
							p.getLocation().getBlock().setType(Material.WEB);
							p.setFoodLevel(p.getFoodLevel()-1);
						}
					}
				}
			}
		}
	}
}
