package fr.smourad.chatmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.smourad.chatmanager.ChatManager;

public class Roll implements CommandExecutor {

	private ChatManager main;
	
	public Roll(ChatManager plugin) {
		this.main = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if (label.equalsIgnoreCase("roll")) {
			if (args.length != 1) {
				player.sendMessage(ChatColor.RED + "/roll [number]");
				return true;
			}
			
			if (args.length == 1) {
				int roll = 1 + (int) (Math.random() * ((Integer.parseInt(args[0]) - 1) + 1));
				
				int blockDistance = main.getConfig().getInt("Type.roll.distance");
				
				Location playerLocation = player.getLocation();
				
				for (Player pl : Bukkit.getOnlinePlayers())
				{
					if (pl.getLocation().getWorld() == playerLocation.getWorld()) {
						
						if (pl.getLocation().distance(playerLocation) <= blockDistance)
						{
							pl.sendMessage(ChatColor.LIGHT_PURPLE + "Le resultat est: " + ChatColor.RED + roll + ChatColor.LIGHT_PURPLE + "/" + args[0]);
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
}
