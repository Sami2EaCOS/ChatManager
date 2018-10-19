package fr.smourad.chatmanager.listener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;
import fr.smourad.chatmanager.helping.Xp;

public class BlockBreak implements Listener {
	
	ChatManager plugin;
	
	public BlockBreak(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockXp(BlockBreakEvent e) {
		Player p = e.getPlayer();
		String playerName = p.getName();
		
		plugin.playerDataLoad(playerName, p);
		PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
		
		if (p.getGameMode() == GameMode.CREATIVE) {
			return;
		}
		
		if (e.getBlock().getType() == Material.LOG || e.getBlock().getType() == Material.LOG_2) {
			int random_bucheron = 1 + (int) (Math.random() * ((10 - 1)+1));
			e.getBlock().setType(Material.AIR);
			if (random_bucheron <= playerfile.getInt("Metier.Bucheron.level")) {
				if (e.getBlock().getType() == Material.LOG) {
					e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.LOG, 1, e.getBlock().getData()));
				} else {
					e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.LOG_2, 1, e.getBlock().getData()));
				}
				if (playerfile.getInt("Metier.Bucheron.level") != 10) {
					playerfile.setInt("Metier.Bucheron.xp", playerfile.getInt("Metier.Bucheron.xp") + 2);
					p.sendMessage(ChatColor.AQUA + "Bucheron + 2 XP");
					if (playerfile.getInt("Metier.Bucheron.xp") >= Xp.getXp(playerfile.getInt("Metier.Bucheron.level"))) {
						playerfile.setInt("Metier.Bucheron.xp", 0);
						playerfile.setInt("Metier.Bucheron.level", playerfile.getInt("Metier.Bucheron.level")+1);
						p.sendMessage(ChatColor.AQUA + "Vous êtes maintenant un bucheron de niveau " + playerfile.getInt("Metier.Bucheron.level") + ".");
					}
				}
			} else {
				e.getBlock().setType(Material.AIR);
				e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.STICK, 3));
			}
		}
	}
}
