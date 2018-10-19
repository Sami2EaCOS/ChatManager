package fr.smourad.chatmanager.commaner;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
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
import org.bukkit.inventory.meta.SkullMeta;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class InventoryProfile implements Listener, CommandExecutor {

	public String inv_name;
	
	ChatManager plugin;
	
	public InventoryProfile(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Inventory inv = (Inventory) e.getInventory();
		
		if (!(inv.getName().equals(inv_name))) {
			return;
		}
		
		e.setCancelled(true);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("profil")) {			
			if (!(sender instanceof Player)) {
				return true;
			}
			
			Player p = (Player) sender;
			
			if (!(p.hasPermission("ChatManager.admin") || p.isOp())) {
				p.sendMessage(ChatColor.RED + "Vous n'avez pas la permission de faire cette commande.");
				return true;
			}
			
			String playerName = p.getName();
			
			if (args.length == 0) {					
				plugin.playerDataLoad(playerName, p);
				PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
				
				Inventory jobs = Bukkit.getServer().createInventory(null, 9*4, "Mon profil");		
				inv_name = "Mon profil";
				
				inventory(p, playerfile, jobs);
				
				p.openInventory(jobs);
				return true;
			}
			
			if (args.length == 1) {
				if (Bukkit.getPlayer(args[0]) == null) {
					p.sendMessage(ChatColor.RED + "Le joueur n'est pas connecté.");
					return true;
				}
				
				plugin.playerDataLoad(playerName, Bukkit.getPlayer(args[0]));
				PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
				
				Inventory jobs = Bukkit.getServer().createInventory(null, 9*4, "Profil de " + args[0]);	
				inv_name = "Profil de " + args[0];
				
				inventory(Bukkit.getPlayer(args[0]), playerfile, jobs);
				
				p.openInventory(jobs);
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void inventory(Player p, PlayerData playerfile, Inventory jobs) {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Joueur: " + p.getName());
		
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta skull_m = (SkullMeta) skull.getItemMeta();
		
		skull_m.setDisplayName(ChatColor.WHITE + p.getName());
		skull_m.setOwner(p.getName());
		skull_m.setLore(lore);
		skull.setItemMeta(skull_m);
		
		jobs.setItem(0, skull);
		
		
		lore.clear();
		lore.add("Race: " + playerfile.getString("Player.race"));
		
		ItemStack race = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta race_m = race.getItemMeta();
		race_m.setDisplayName(ChatColor.WHITE + "Race");
		race_m.setLore(lore);
		race.setItemMeta(race_m);
		
		jobs.setItem(35, race);
		
		
		lore.clear();
		lore.add("Force: " + playerfile.getInt("Stats.strength"));
		
		ItemStack force = new ItemStack(Material.WOOL, 1, (short) DyeColor.RED.getWoolData());
		ItemMeta force_m = force.getItemMeta();
		force_m.setDisplayName(ChatColor.WHITE + "Force");
		force_m.setLore(lore);
		force.setItemMeta(force_m);
		
		jobs.setItem(11, force);
		
		
		lore.clear();
		lore.add("Intelligence: " + playerfile.getInt("Stats.cleverness"));
		
		ItemStack intelligence = new ItemStack(Material.WOOL, 1, (short) DyeColor.BLUE.getWoolData());
		ItemMeta intelligence_m = intelligence.getItemMeta();
		intelligence_m.setDisplayName(ChatColor.WHITE + "Intelligence");
		intelligence_m.setLore(lore);
		intelligence.setItemMeta(intelligence_m);
		
		jobs.setItem(12, intelligence);
		
		
		lore.clear();
		lore.add("Constitution: " + playerfile.getInt("Stats.constitution"));
		
		ItemStack constitution = new ItemStack(Material.WOOL, 1, (short) DyeColor.GRAY.getWoolData());
		ItemMeta constitution_m = constitution.getItemMeta();
		constitution_m.setDisplayName(ChatColor.WHITE + "Constitution");
		constitution_m.setLore(lore);
		constitution.setItemMeta(constitution_m);
		
		jobs.setItem(13, constitution);
		
		
		lore.clear();
		lore.add("Sensibilité: " + playerfile.getInt("Stats.sensibility"));
		
		ItemStack sensibility = new ItemStack(Material.WOOL, 1, (short) DyeColor.PURPLE.getWoolData());
		ItemMeta sensibility_m = sensibility.getItemMeta();
		sensibility_m.setDisplayName(ChatColor.WHITE + "Sensibilité");
		sensibility_m.setLore(lore);
		sensibility.setItemMeta(sensibility_m);
		
		jobs.setItem(14, sensibility);
		
		
		lore.clear();
		lore.add("Dextérité: " + playerfile.getInt("Stats.dexterity"));
		
		ItemStack dexterity = new ItemStack(Material.WOOL, 1, (short) DyeColor.ORANGE.getWoolData());
		ItemMeta dexterity_m = dexterity.getItemMeta();
		dexterity_m.setDisplayName(ChatColor.WHITE + "Dextérité");
		dexterity_m.setLore(lore);
		dexterity.setItemMeta(dexterity_m);
		
		jobs.setItem(15, dexterity);
		
		
		lore.clear();
		lore.add("Niveau: " + playerfile.getInt("Metier.Bucheron.level"));
		
		ItemStack bucheron = new ItemStack(Material.DIAMOND_AXE, 1);
		ItemMeta bucheron_m = bucheron.getItemMeta();
		bucheron_m.setDisplayName(ChatColor.WHITE + "Bucheron");
		bucheron_m.setLore(lore);
		bucheron.setItemMeta(bucheron_m);
		
		jobs.setItem(20, bucheron);
		
		
		lore.clear();
		lore.add("Niveau: " + playerfile.getInt("Metier.Mineur.level"));
		
		ItemStack mineur = new ItemStack(Material.IRON_PICKAXE, 1);
		ItemMeta mineur_m = mineur.getItemMeta();
		mineur_m.setDisplayName(ChatColor.WHITE + "Mineur");
		mineur_m.setLore(lore);
		mineur.setItemMeta(mineur_m);
		
		jobs.setItem(21, mineur);
		
		
		lore.clear();
		lore.add("Niveau: " + playerfile.getInt("Metier.Boucherie.level"));
		
		ItemStack boucherie = new ItemStack(4278, 1);
		ItemMeta boucherie_m = boucherie.getItemMeta();
		boucherie_m.setDisplayName(ChatColor.WHITE + "Boucherie");
		boucherie_m.setLore(lore);
		boucherie.setItemMeta(boucherie_m);
		
		jobs.setItem(22, boucherie);
		
		ItemStack non = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		ItemMeta non_m = non.getItemMeta();
		non_m.setDisplayName(ChatColor.MAGIC + "Secret");
		non.setItemMeta(non_m);
		
		jobs.setItem(23, non);
		jobs.setItem(24, non);
	}
}
