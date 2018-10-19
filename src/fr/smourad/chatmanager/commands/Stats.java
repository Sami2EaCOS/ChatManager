package fr.smourad.chatmanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class Stats implements CommandExecutor {

	ChatManager main;
	
	public Stats(ChatManager plugin) {
		this.main = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if (!(player.hasPermission("ChatManager.admin") || player.isOp())) {
			player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de faire cette commande.");
			return true;
		}
		
		if (label.equalsIgnoreCase("stats")) {
			
			if (args.length != 3) {
				sender.sendMessage(ChatColor.RED + "/stats [username] [type] [number]");
				return true;
			}
			
			if (args.length == 3) {
				
				Player p = main.getServer().getPlayer(args[0]);
				
				if (p != null) {
					main.playerDataLoad(p.getName(), p);
					PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
					
					if (args[1].equalsIgnoreCase("dexterity")) {
						playerfile.setFloat("Stats.dexterity", Float.parseFloat(args[2]));
						player.sendMessage(ChatColor.AQUA + "La dexterite de " + ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " est maintenant de " + args[2]);
						if (Float.parseFloat(args[2]) >= 10) {
							p.setWalkSpeed((float) (0.2f + (Float.parseFloat(args[2]))*0.002));
						} else {
							p.setWalkSpeed(0.2f);
						}
						return true;
					
					} else if (args[1].equalsIgnoreCase("constitution")) {
						playerfile.setDouble("Stats.constitution", Double.parseDouble(args[2]));
						player.sendMessage(ChatColor.AQUA + "La constitution de " + ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " est maintenant de " + args[2]);

						if (Double.parseDouble(args[2]) >= 10) {
							if (Double.parseDouble(args[2])%2 == 0) {
								p.setMaxHealth(20 + Double.parseDouble(args[2])*0.5);
								p.setHealth(20 + Double.parseDouble(args[2])*0.5);
							} else {
								p.setMaxHealth(20 + (Double.parseDouble(args[2])-1)*0.5);
								p.setHealth(20 + (Double.parseDouble(args[2])-1)*0.5);
							}
						} else {
							p.setMaxHealth(20.0);
							p.setHealth(20.0);
						}
						return true;
						
					} else if (args[1].equalsIgnoreCase("strength")) {
						playerfile.setDouble("Stats.strength", Double.parseDouble(args[2]));
						player.sendMessage(ChatColor.AQUA + "La force de " + ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " est maintenant de " + args[2]);
						return true;
						
					} else if (args[1].equalsIgnoreCase("sensibility")) {
						playerfile.setDouble("Stats.sensibility", Double.parseDouble(args[2]));
						player.sendMessage(ChatColor.AQUA + "La sensibilite de " + ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " est maintenant de " + args[2]);
						return true;
						
					} else if (args[1].equalsIgnoreCase("cleverness")) {
						playerfile.setDouble("Stats.cleverness", Double.parseDouble(args[2]));
						player.sendMessage(ChatColor.AQUA + "L'intelligence de " + ChatColor.DARK_AQUA + p.getName() + ChatColor.AQUA + " est maintenant de " + args[2]);
						return true;
						
					} else {
						player.sendMessage(ChatColor.RED + "Ce type de stats n'existe pas");
						return true;
					}
					
				} else {
					player.sendMessage(ChatColor.RED + "Le joueur n'est pas en ligne");
					return true;
				}
				
			}
			
		}
		return false;
	}

}
