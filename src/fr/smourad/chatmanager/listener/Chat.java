package fr.smourad.chatmanager.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.smourad.chatmanager.ChatManager;

public class Chat implements Listener {

	private ChatManager main;
	
	public Chat(ChatManager plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.main = plugin;
	}
	
	@EventHandler
	public boolean onPlayerChat(AsyncPlayerChatEvent e) {
		
		Player player = e.getPlayer();
		String rawMessage = e.getMessage();
		
		if (rawMessage.substring(0, 1).equals(main.getConfig().getString("Type.crier.signe")))
		{
			String message = "§c" + player.getDisplayName() + " crie: " + rawMessage.substring(1, rawMessage.length());
			 
			int blockDistance = main.getConfig().getInt("Type.crier.distance");
			
			Location playerLocation = player.getLocation();
			
			for (Player pl : e.getRecipients())
			{
				if (pl.getLocation().getWorld() == playerLocation.getWorld()) {
					
					if (pl.getLocation().distance(playerLocation) <= blockDistance)
					{
						pl.sendMessage(message);
					}
				}
				
			}
			
			e.getRecipients().clear();
			return true;
			
		}
		
		
		if (rawMessage.substring(0, 1).equals(main.getConfig().getString("Type.daction.signe")))
		{			
			String message = "§e" + "§o" + player.getDisplayName() + " " + rawMessage.substring(1, rawMessage.length());
			
			int blockDistance = main.getConfig().getInt("Type.daction.distance");
			
			Location playerLocation = player.getLocation();
			
			for (Player pl : e.getRecipients())
			{
				if (pl.getLocation().getWorld() == playerLocation.getWorld()) {
					
					if (pl.getLocation().distance(playerLocation) <= blockDistance)
					{
						pl.sendMessage(message);
					}
				}
			}
			
			e.getRecipients().clear();
			return true;
		}
		
	
		if (rawMessage.substring(0, 1).equals(main.getConfig().getString("Type.chuchoter.signe")))
		{	
			
			String message = "§b" + "§o" + player.getDisplayName() + " dit: " + rawMessage.substring(1, rawMessage.length());
			
			int blockDistance = main.getConfig().getInt("Type.chuchoter.distance");
			
			Location playerLocation = player.getLocation();
			
			for (Player pl : e.getRecipients())
			{
				if (pl.getLocation().getWorld() == playerLocation.getWorld()) {
					
					if (pl.getLocation().distance(playerLocation) <= blockDistance)
					{
						pl.sendMessage(message);
					}
				}
			}
			
			e.getRecipients().clear();
			return true;
			
		}
		
		if (rawMessage.substring(0, 1).equals(main.getConfig().getString("Type.hrp.signe")))
		{	
			
			String message = "§7" + player.getDisplayName() + ": (" + rawMessage.substring(1, rawMessage.length()) + ")";
				
			int blockDistance = main.getConfig().getInt("Type.hrp.distance");
			
			Location playerLocation = player.getLocation();
			
			for (Player pl : e.getRecipients())
			{
				if (pl.getLocation().getWorld() == playerLocation.getWorld()) {
					
					if (pl.getLocation().distance(playerLocation) <= blockDistance)
					{
						pl.sendMessage(message);
					}
				}
			}
			
			e.getRecipients().clear();
			return true;
		}
		
		if (rawMessage.substring(0, 1).equals(main.getConfig().getString("Type.lore.signe")))
		{
			if (player.hasPermission("chatManager.lore") || player.isOp())
			{
				String message = "§d" + rawMessage.substring(1, rawMessage.length());
				
				int blockDistance = main.getConfig().getInt("Type.lore.distance");
				
				Location playerLocation = player.getLocation();
				
				for (Player pl : e.getRecipients())
				{
					if (pl.getLocation().getWorld() == playerLocation.getWorld()) {
						
						if (pl.getLocation().distance(playerLocation) <= blockDistance)
						{
							pl.sendMessage(message);
						}
					}
				}
				
				e.getRecipients().clear();
				return true;
			}
			else {
				player.sendMessage("§cTu ne peux pas envoyer de message de lore!");
				return false;
			}
			
		}
		
		if (rawMessage.substring(0, 1).equals(main.getConfig().getString("Type.absolu.signe")))
		{
			if (player.hasPermission("chatManager.absolu") || player.isOp())
			{
				String message = "§5" + rawMessage.substring(1, rawMessage.length());
				
				for (Player pl : e.getRecipients())
				{
					pl.sendMessage(message);
				}
				
				e.getRecipients().clear();
				return true;
			}
			else {
				player.sendMessage("§cTu ne peux pas envoyer de message d'absolu!");
				return false;
			}
			
		}
		
		if (rawMessage.substring(0, 1).equals(main.getConfig().getString("Type.action.signe")))
		{
			
			String message = "§6" + player.getDisplayName() + " " + rawMessage.substring(1, rawMessage.length());
			 
			int blockDistance = main.getConfig().getInt("Type.action.distance");
			
			Location playerLocation = player.getLocation();
			
			for (Player pl : e.getRecipients())
			{
				if (pl.getLocation().getWorld() == playerLocation.getWorld()) {
					
					if (pl.getLocation().distance(playerLocation) <= blockDistance)
					{
						pl.sendMessage(message);
					}
				}
			}
			
			e.getRecipients().clear();
			return true;
		}
		
		String message = "§3" + player.getDisplayName() + " dit: " + rawMessage;
			
		int blockDistance = main.getConfig().getInt("Type.parler.distance");
			
		Location playerLocation = player.getLocation();
			
		for (Player pl : e.getRecipients())
		{
			if (pl.getLocation().getWorld() == playerLocation.getWorld()) {
				
				if (pl.getLocation().distance(playerLocation) <= blockDistance)
				{
					pl.sendMessage(message);
				}
			}
		}
			
		e.getRecipients().clear();
		return false;
	}
}
