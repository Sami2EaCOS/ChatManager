package fr.smourad.chatmanager.listener;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class RightClick implements Listener {

	public HashMap<String, Long> cooldown = new HashMap<String, Long>();
	
	ChatManager main;
	
	public RightClick(ChatManager plugin) {
		this.main = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		String playerName = p.getName();
		
		main.playerDataLoad(playerName, p);
		PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
		
		if ((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			if (e.getPlayer().getItemInHand().getType() == Material.AIR) {
				if (playerfile.getString("Player.race").equalsIgnoreCase("arachnide")) {
					p.setItemInHand(new ItemStack(Material.STRING,5));
				}
				
			}
			
			if (e.getPlayer().getItemInHand().getType() == Material.DIAMOND) {
				if (p.getFoodLevel() >= 4) {
				
					if (playerfile.getString("Player.race").equalsIgnoreCase("transcende")) {
						
						int cooldownTime = 5;
						
						if (cooldown.containsKey(playerName)) {
							long secondsLeft = ((cooldown.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
							if (secondsLeft > 0) {
								p.sendMessage(ChatColor.DARK_PURPLE + "il reste " + secondsLeft + " secondes avant de pouvoir refaire ce sort");
							
							} else {
								cooldown.put(p.getName(), System.currentTimeMillis());
								
								p.chat("/cast magic_missile");
								p.setFoodLevel(p.getFoodLevel()-4);
							}			
						} else {
							cooldown.put(p.getName(), System.currentTimeMillis());
							
							p.chat("/cast magic_missile");
							p.setFoodLevel(p.getFoodLevel()-4);
						}
					}
				} else {
					p.sendMessage(ChatColor.DARK_GRAY + "Vous n'avez pas assez d'énergie pour lancer le sortilège.");
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.PLAYER) {
			Player p = (Player) e.getRightClicked();
			main.playerDataLoad(p.getName(), p);
			PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
			
			Player player = e.getPlayer();
			
			player.sendMessage(ChatColor.DARK_AQUA + p.getName() + ": " + ChatColor.BLUE + playerfile.getString("Player.desc"));
			
		}
	}
}
