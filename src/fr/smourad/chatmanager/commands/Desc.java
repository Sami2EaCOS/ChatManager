package fr.smourad.chatmanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class Desc implements CommandExecutor {

	ChatManager main;
	
	public Desc(ChatManager plugin) {
		this.main = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("desc")) {
			
			if (!(sender instanceof Player)) {
				return true;
			}
			
			Player p = (Player) sender;
			
			main.playerDataLoad(p.getName(), p);
			PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
			
			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "Vous devez ecrire quelquechose");
				return true;
			}
			
			String desc = "";
			
			for (int i=0; i<args.length; i++) {
				desc += (args[i] + " ");
			}
			
			playerfile.setString("Player.desc", desc);
			p.sendMessage(ChatColor.DARK_AQUA + "Votre nouvelle description est maintenant: " + ChatColor.BLUE + desc);
			return true;
		}
		
		return false;
	}

}
