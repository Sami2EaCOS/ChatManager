package fr.smourad.chatmanager.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class Race implements CommandExecutor {

	ChatManager main;
	
	public Race(ChatManager plugin) {
		this.main = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		Damageable dam = (Damageable) player;
		
		if (label.equalsIgnoreCase("race")) {
			if (args.length != 2) {
				player.sendMessage(ChatColor.RED + "/race [username] [race]");
				return true;
			}
			
			if (args.length == 2) {
				Player p = main.getServer().getPlayer(args[0]);
				
				if (p != null) {
					main.playerDataLoad(p.getName(), p);
					PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
					
					double life = dam.getMaxHealth();
					
					// Choix des races
					
					if (args[1].equalsIgnoreCase("humain")) {
						playerfile.setString("Player.race", args[1]);
						player.sendMessage(ChatColor.DARK_GRAY + "La race de " + ChatColor.BLACK+ p.getName() + ChatColor.DARK_GRAY + " est maintenant la race " + args[1]);
						
						p.setMaxHealth(life + 2);
						p.setHealth(life + 2);
						
					} else if (args[1].equalsIgnoreCase("nain")) {
						playerfile.setString("Player.race", args[1]);
						player.sendMessage(ChatColor.DARK_GRAY + "La race de " + ChatColor.BLACK+ p.getName() + ChatColor.DARK_GRAY + " est maintenant la race " + args[1]);
						
						p.setMaxHealth(life + 6);
						p.setHealth(life + 6);
						p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0, true));
						
					} else if (args[1].equalsIgnoreCase("elfe")) {
						playerfile.setString("Player.race", args[1]);
						player.sendMessage(ChatColor.DARK_GRAY + "La race de " + ChatColor.BLACK+ p.getName() + ChatColor.DARK_GRAY + " est maintenant la race " + args[1]);
						
						p.setMaxHealth(life + 4);
						p.setHealth(life + 4);
						
					} else if (args[1].equalsIgnoreCase("gobelin")) {
						playerfile.setString("Player.race", args[1]);
						player.sendMessage(ChatColor.DARK_GRAY + "La race de " + ChatColor.BLACK+ p.getName() + ChatColor.DARK_GRAY + " est maintenant la race " + args[1]);
						
						p.setMaxHealth(life - 2);
						p.setHealth(life - 2);
						double speed = p.getWalkSpeed();
						p.setWalkSpeed((float) (speed + 0.05));
						
					} else if (args[1].equalsIgnoreCase("naga")) {
						playerfile.setString("Player.race", args[1]);
						player.sendMessage(ChatColor.DARK_GRAY + "La race de " + ChatColor.BLACK+ p.getName() + ChatColor.DARK_GRAY + " est maintenant la race " + args[1]);
						
						p.setMaxHealth(life + 4);
						p.setHealth(life + 4);
						p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, true));
						
					} else if (args[1].equalsIgnoreCase("transcende")) {
						playerfile.setString("Player.race", args[1]);
						player.sendMessage(ChatColor.DARK_GRAY + "La race de " + ChatColor.BLACK+ p.getName() + ChatColor.DARK_GRAY + " est maintenant la race " + args[1]);
						
						p.setMaxHealth(life - 2);
						p.setHealth(life - 2);
						
					} else if (args[1].equalsIgnoreCase("arachnide")) {
						playerfile.setString("Player.race", args[1]);
						player.sendMessage(ChatColor.DARK_GRAY + "La race de " + ChatColor.BLACK+ p.getName() + ChatColor.DARK_GRAY + " est maintenant la race " + args[1]);
						
						p.setMaxHealth(life + 2);
						p.setHealth(life + 2);
						double speed = p.getWalkSpeed();
						p.setWalkSpeed((float) (speed + 0.05));
						
					} else if (args[1].equalsIgnoreCase("crapahuteur")) {
						playerfile.setString("Player.race", args[1]);
						player.sendMessage(ChatColor.DARK_GRAY + "La race de " + ChatColor.BLACK+ p.getName() + ChatColor.DARK_GRAY + " est maintenant la race " + args[1]);
						
						p.setMaxHealth(life + 6);
						p.setHealth(life + 6);
						p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1, true));
						
					} else {
						player.sendMessage(ChatColor.RED + "Cette race n'existe pas");
					}
					return true;
				
				} else {
					player.sendMessage(ChatColor.RED + "Le joueur n'est pas en ligne");
					return true;
				}
				
			}
			
		}
		
		return false;
	}

}
