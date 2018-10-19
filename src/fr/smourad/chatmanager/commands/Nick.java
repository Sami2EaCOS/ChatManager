package fr.smourad.chatmanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class Nick implements CommandExecutor {

	private ChatManager plugin;
	
	public Nick(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("nick")) {
			
			Player player = (Player) sender;
			
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "Tu ne peux pas te renommer avec rien!");
				return true;
			}
			
			if (args.length == 2) {
				Player p = plugin.getServer().getPlayer(args[1]);
				String nick = args[0];
				
				if (p != null) {

					plugin.playerDataLoad(p.getName(), p);
					PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
					
					p.setDisplayName(nick);
					p.setPlayerListName(nick);
					
					if (args[0].equalsIgnoreCase("reset")) {
						p.setDisplayName(p.getName());
						p.setPlayerListName(p.getName());
						player.sendMessage(ChatColor.BLUE + "Le pseudo de " + p.getName() + " est revenu à la normal");
						playerfile.setString("Player.nick", "reset");
						return true;
					}
					
					player.sendMessage(ChatColor.BLUE + "Le pseudo de " + p.getName() + " est devenu <" + nick + ">");
					playerfile.setString("Player.nick", nick);
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "Le joueur n'est pas en ligne");
					return true;
				}
			}
			
			if (args.length == 1)  {
				String nick = args[0];
				
				plugin.playerDataLoad(player.getName(), player);
				PlayerData playerfile = ChatManager.PlayerDataMap.get(player);
				
				player.setDisplayName(nick);
				player.setPlayerListName(nick);
				
				if (args[0].equalsIgnoreCase("reset")) {
					player.setDisplayName(player.getName());
					player.setPlayerListName(player.getName());
					player.sendMessage(ChatColor.BLUE + "Votre pseudo est revenu à la normal");
					playerfile.setString("Player.nick", "reset");
					return true;
				}
				player.sendMessage(ChatColor.BLUE + "Votre pseudo est devenu <" + nick + ">");
				playerfile.setString("Player.nick", nick);
				return true;
			}
			
			if (args.length > 2)
			{
				player.sendMessage(ChatColor.RED + "Il y a trop d'arguments!");
				return true;
			}
			
		}
		return false;
	}
	
}


