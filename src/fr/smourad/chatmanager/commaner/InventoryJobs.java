package fr.smourad.chatmanager.commaner;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;
import fr.smourad.chatmanager.helping.Xp;

public class InventoryJobs implements Listener, CommandExecutor {
	
	public String inv_name = "";
	
	ChatManager plugin;
	
	public InventoryJobs(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		// ItemStack clicked = e.getCurrentItem();
		Inventory inv = (Inventory) e.getInventory();
		
		if (!(inv.getName().equals(inv_name))) {
			return;
		}
		
		/*
		if (clicked.getType().equals(Material.DIAMOND_AXE)) {
			
		}
		*/
		
		e.setCancelled(true);
	}
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("metier")) {
			if (!(sender instanceof Player)) {
				return true;
			}
			
			Player psender = (Player) sender;
			
			if (args.length == 0) {	
				String playerName = psender.getName();
				
				plugin.playerDataLoad(playerName, psender);
				PlayerData playerfile = ChatManager.PlayerDataMap.get(psender);
				
				Inventory jobs = Bukkit.getServer().createInventory(null, 9*1, "Métier");		
				inv_name = "Métier";
				
				inventory(playerfile, jobs);
				
				psender.openInventory(jobs);
				return true;
			}
			
			if (args.length == 1) {
				if (Bukkit.getPlayer(args[0]) == null) {
					sender.sendMessage(ChatColor.RED + "Le joueur n'est pas en ligne.");
					return true;
				}
				
				Player p = Bukkit.getPlayer(args[0]);
				
				plugin.playerDataLoad(args[0], p);
				PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
				
				Inventory jobs = Bukkit.getServer().createInventory(null, 9*1, "Métier de " + args[0]);
				inv_name = "Métier de " + args[0];
				
				inventory(playerfile, jobs);
				
				psender.openInventory(jobs);
				return true;
			}
			
			if (psender.hasPermission("chatManager.admin") || psender.isOp()) {
				if (args.length == 3) {
					if (Bukkit.getPlayer(args[0]) == null) {
						sender.sendMessage(ChatColor.RED + "Le joueur n'est pas en ligne.");
						return true;
					}
					
					Player p = Bukkit.getPlayer(args[0]);
					
					plugin.playerDataLoad(args[0], p);
					PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
					
					if (args[1].equalsIgnoreCase("bucheron")) {
						if (Integer.parseInt(args[2]) <= 10 && Integer.parseInt(args[2]) >= 1) {
							playerfile.setInt("Metier.Bucheron.level", Integer.parseInt(args[2]));
							playerfile.setInt("Metier.Bucheron.xp", 0);
							if (p != psender) {
								p.sendMessage(ChatColor.AQUA + "Votre niveau de bucheron est maintenant de " + args[2]);
								psender.sendMessage(ChatColor.AQUA + "Le niveau de bucheron de " + args[0] + " est maintenant de " + args[2]);
							} else {
								p.sendMessage(ChatColor.AQUA + "Votre niveau de bucheron est maintenant de " + args[2]);
							}
						} else {
							psender.sendMessage(ChatColor.RED + "Le niveau doit être entre 1 et 10.");
						}
					}
					
					if (args[1].equalsIgnoreCase("mineur")) {
						if (Integer.parseInt(args[2]) <= 10 && Integer.parseInt(args[2]) >= 1) {
							playerfile.setInt("Metier.Mineur.level", Integer.parseInt(args[2]));
							playerfile.setInt("Metier.Mineur.xp", 0);
							if (p != psender) {
								p.sendMessage(ChatColor.AQUA + "Votre niveau de mineur est maintenant de " + args[2]);
								psender.sendMessage(ChatColor.AQUA + "Le niveau de mineur de " + args[0] + " est maintenant de " + args[2]);
							} else {
								p.sendMessage(ChatColor.AQUA + "Votre niveau de mineur est maintenant de " + args[2]);
							}
						} else {
							psender.sendMessage(ChatColor.RED + "Le niveau doit être entre 1 et 10.");
						}
					}
					
					if (args[1].equalsIgnoreCase("boucherie")) {
						if (Integer.parseInt(args[2]) <= 10 && Integer.parseInt(args[2]) >= 1) {
							playerfile.setInt("Metier.Boucherie.level", Integer.parseInt(args[2]));
							playerfile.setInt("Metier.Boucherie.xp", 0);
							if (p != psender) {
								p.sendMessage(ChatColor.AQUA + "Votre niveau de boucherie est maintenant de " + args[2]);
								psender.sendMessage(ChatColor.AQUA + "Le niveau de boucherie de " + args[0] + " est maintenant de " + args[2]);
							} else {
								p.sendMessage(ChatColor.AQUA + "Votre niveau de boucherie est maintenant de " + args[2]);
							}
						} else {
							psender.sendMessage(ChatColor.RED + "Le niveau doit être entre 1 et 10.");
						}
					}
					return true;
					
				}
			}
		}	
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void inventory(PlayerData playerfile, Inventory jobs) {
		
		ArrayList<String> bucheron_lore = new ArrayList<String>();
		bucheron_lore.add("Niveau: " + playerfile.getString("Metier.Bucheron.level"));
		if (playerfile.getInt("Metier.Bucheron.level") != 10) {
			bucheron_lore.add("XP: " + playerfile.getString("Metier.Bucheron.xp") + "/" + Xp.getXp(playerfile.getInt("Metier.Bucheron.level")));
		} else {
			bucheron_lore.add("XP: MAX");
		}
		bucheron_lore.add("");
		bucheron_lore.add(playerfile.getString("Metier.Bucheron.level") + "/10 de couper une buche avec succès.");
		
		ItemStack bucheron = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta bucheron_m = bucheron.getItemMeta();
		bucheron_m.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "Bucheron");
		bucheron_m.setLore(bucheron_lore);
		bucheron.setItemMeta(bucheron_m);
		
		jobs.setItem(0, bucheron);
		
		ArrayList<String> mineur_lore = new ArrayList<String>();
		mineur_lore.add("Niveau: " + playerfile.getString("Metier.Mineur.level"));
		if (playerfile.getInt("Metier.Mineur.level") != 10) {
			mineur_lore.add("XP: " + playerfile.getString("Metier.Mineur.xp") + "/" + Xp.getXp(playerfile.getInt("Metier.Mineur.level")));
		} else {
			mineur_lore.add("XP: MAX");
		}
		mineur_lore.add("");
		
		ItemStack mineur = new ItemStack(Material.IRON_PICKAXE, 1);
		ItemMeta mineur_m = mineur.getItemMeta();
		mineur_m.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "Mineur");
		mineur_m.setLore(mineur_lore);
		mineur.setItemMeta(mineur_m);
		
		jobs.setItem(1, mineur);
		
		ArrayList<String> boucherie_lore = new ArrayList<String>();
		boucherie_lore.add("Niveau: " + playerfile.getString("Metier.Boucherie.level"));
		if (playerfile.getInt("Metier.Boucherie.level") != 10) {
			boucherie_lore.add("XP: " + playerfile.getString("Metier.Boucherie.xp") + "/" + Xp.getXp(playerfile.getInt("Metier.Boucherie.level")));
		} else {
			boucherie_lore.add("XP: MAX");
		}
		boucherie_lore.add("");
		boucherie_lore.add("Vous obtiendrez ceci en tuant les animaux suivants (sans looting): ");
		boucherie_lore.add("Mouton: " + Math.round(playerfile.getInt("Metier.Boucherie.level")/2) + " Viande(s) + " + (int) Math.round(playerfile.getInt("Metier.Boucherie.level")/1.5) + " Laine(s)");
		boucherie_lore.add("Poulet: " + Math.round(playerfile.getInt("Metier.Boucherie.level")/2) + " Viande(s) + " + playerfile.getInt("Metier.Boucherie.level") + " Plume(s)");
		boucherie_lore.add("Vache: " + Math.round(playerfile.getInt("Metier.Boucherie.level")/2) + " Viande(s) + " + (int) Math.round(playerfile.getInt("Metier.Boucherie.level")/3) + " Cuir(s)");
		boucherie_lore.add("Cochon: " + (int) Math.round(playerfile.getInt("Metier.Boucherie.level")/2) + " Viande(s)");
		
		ItemStack boucherie = new ItemStack(4278, 1);
		ItemMeta boucherie_m = boucherie.getItemMeta();
		boucherie_m.setDisplayName(ChatColor.RESET + "" + ChatColor.GRAY + "Boucherie");
		boucherie_m.setLore(boucherie_lore);
		boucherie.setItemMeta(boucherie_m);
		
		jobs.setItem(2, boucherie);
	}

}
