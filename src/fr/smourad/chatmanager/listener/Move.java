package fr.smourad.chatmanager.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class Move implements Listener {
	
	public List<Block> blocked = new ArrayList<Block>();
	
	private ChatManager main;
	
	public Move(ChatManager plugin) {
		this.main = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		String playerName = player.getName();
		
		main.playerDataLoad(playerName, player);
		PlayerData playerfile = ChatManager.PlayerDataMap.get(player);
		
		// A tester
		
		for (Block b : blocked) {
			if (player.getLocation() != b.getLocation()) {
				player.sendBlockChange(b.getLocation(), Material.AIR, (byte) 1);
			}
			blocked.remove(b);
		}
		
		if (playerfile.getString("Player.race").equalsIgnoreCase("arachnide")) {					
			
			// Sinon remettre ici 
			
			Block b1 = player.getLocation().getBlock();
			if (!(b1.getType() != Material.AIR)) {
				Block b2 = b1.getRelative(BlockFace.UP);
				Location l = player.getLocation();
				if (b1.getRelative(BlockFace.NORTH).getType().isSolid() || b1.getRelative(BlockFace.EAST).getType().isSolid() || b1.getRelative(BlockFace.SOUTH).getType().isSolid() || b1.getRelative(BlockFace.WEST).getType().isSolid()) {
					
					double y = l.getY();
					player.sendBlockChange(b1.getLocation(), Material.VINE, (byte) 0);
					blocked.add(b1);
					
					if (y % 1 > .40 && b2.getType() == Material.AIR) {
						player.sendBlockChange(b2.getLocation(), Material.VINE, (byte) 0);
						blocked.add(b2);
						
					}
				}
			}
		}
				
		Material m = e.getPlayer().getLocation().getBlock().getType();
				
	    if (m == Material.STATIONARY_WATER || m == Material.WATER) {
	    	if (playerfile.getString("Player.race").equalsIgnoreCase("naga")) {
			   player.setAllowFlight(true);
			   player.setFlying(true);
			   player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, true));
			   player.setFlySpeed(0.07f);
	    	}
			        
	    	if (playerfile.getString("Player.race").equalsIgnoreCase("crapahuteur")) {
	    		player.setAllowFlight(true);
				player.setFlying(true);
				player.setFlySpeed(0.04f);
	    	}
	    
	    }
	    
	    if (m == Material.AIR) {
	    	
	    	if (playerfile.getString("Player.race").equalsIgnoreCase("naga") || playerfile.getString("Player.race").equalsIgnoreCase("crapahuteur")) {
	    		if (!(player.getGameMode() == GameMode.CREATIVE)) {
	    			if (!player.isOp()) {
	    				player.setAllowFlight(false);
	    			}
					player.setFlying(false);
	    		} else {
	    			player.setFlySpeed(0.1f);
	    		}
	    		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
	    			
	    	}
	    }

	}

}


