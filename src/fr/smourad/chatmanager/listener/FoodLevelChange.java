package fr.smourad.chatmanager.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class FoodLevelChange implements Listener {
	
	ChatManager plugin;
	
	public FoodLevelChange(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		Player p = (Player) e.getEntity();
		
		plugin.playerDataLoad(p.getName(), p);
		PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
		
		if (playerfile.getString("Player.race").equalsIgnoreCase("transcende")) {
			if (!(e.getFoodLevel() > p.getFoodLevel())) {
				e.setCancelled(true);
			}
		}
	}
}
