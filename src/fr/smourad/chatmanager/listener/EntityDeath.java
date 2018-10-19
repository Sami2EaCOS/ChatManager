package fr.smourad.chatmanager.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;
import fr.smourad.chatmanager.helping.Xp;

public class EntityDeath implements Listener {
	
	ChatManager plugin;
	
	public EntityDeath(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void entityDeath(EntityDeathEvent e) {
		LivingEntity entity = e.getEntity();
		if (!(entity.getKiller() instanceof Player)) {
			return;
		}
		
		if (!(entity instanceof Ageable)) {
			return;
		}
		
		Ageable age = (Ageable) entity;
		
		if (!(age.isAdult())) {
			return;
		}
		
		Player p = entity.getKiller();
		plugin.playerDataLoad(p.getName(), p);
		PlayerData playerData = ChatManager.PlayerDataMap.get(p);
		
		int boucherie_level = playerData.getInt("Metier.Boucherie.level");
		
		int xp = Xp.getXp(boucherie_level);
		
		Material viande;
    	
    	switch (entity.getType()) {
    	
		case COW:
			if (entity.getFireTicks() != 0) {
				viande = Material.COOKED_BEEF;
			} else {
				viande = Material.RAW_BEEF;
			}
			
			e.getDrops().clear();
			if (p.getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
				e.getDrops().add(new ItemStack(viande, (int) Math.round(boucherie_level/2)+1*p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS)));
				e.getDrops().add(new ItemStack(Material.LEATHER, (int) Math.round(boucherie_level/3+0.7*p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS))));
			} else {
				e.getDrops().add(new ItemStack(viande, (int) Math.round(boucherie_level/2)));
				e.getDrops().add(new ItemStack(Material.LEATHER, (int) Math.round(boucherie_level/3)));
			}
			
			if (boucherie_level < 10) {
				playerData.setInt("Metier.Boucherie.xp", playerData.getInt("Metier.Boucherie.xp")+3);
				p.sendMessage(ChatColor.AQUA + "Boucherie + 3 XP");
				
				if (playerData.getInt("Metier.Boucherie.xp") >= xp) {
					playerData.setInt("Metier.Boucherie.level", playerData.getInt("Metier.Boucherie.level")+1);
					playerData.setInt("Metier.Boucherie.xp", 0);
					p.sendMessage(ChatColor.AQUA + "Vous passez niveau " + playerData.getInt("Metier.Boucherie.level") + "en Boucherie.");
				}
			}
			break;
			
		case SHEEP:
			if (entity.getFireTicks() != 0) {
				viande = Material.getMaterial(4698);
			} else {
				viande = Material.getMaterial(4697);
			}
			
			e.getDrops().clear();
			if (p.getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
				e.getDrops().add(new ItemStack(Material.WOOL, (int) Math.round(boucherie_level/1.5+1.5*p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS))));
				e.getDrops().add(new ItemStack(viande, (int) Math.round(boucherie_level/2)+1*p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS)));
			} else {
				e.getDrops().add(new ItemStack(Material.WOOL, (int) Math.round(boucherie_level/1.5)));
				e.getDrops().add(new ItemStack(viande, Math.round(boucherie_level/2)));
			}
			
			
			if (boucherie_level < 10) {
				playerData.setInt("Metier.Boucherie.xp", playerData.getInt("Metier.Boucherie.xp")+2);
				p.sendMessage(ChatColor.AQUA + "Boucherie + 2 XP");
				
				if (playerData.getInt("Metier.Boucherie.xp") >= xp) {
					playerData.setInt("Metier.Boucherie.level", playerData.getInt("Metier.Boucherie.level")+1);
					playerData.setInt("Metier.Boucherie.xp", 0);
					p.sendMessage(ChatColor.AQUA + "Vous passez niveau " + playerData.getInt("Metier.Boucherie.level") + "en Boucherie.");
				}
			}
			break;
			
		case PIG:
			if (entity.getFireTicks() != 0) {
				viande = Material.GRILLED_PORK;
			} else {
				viande = Material.PORK;
			}
			
			e.getDrops().clear();
			if (p.getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
				e.getDrops().add(new ItemStack(viande, (int) Math.round(boucherie_level/2)+1*p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS)));
			} else {
				e.getDrops().add(new ItemStack(viande, (int) Math.round(boucherie_level/2)));
			}
			
			if (boucherie_level < 10) {
				playerData.setInt("Metier.Boucherie.xp", playerData.getInt("Metier.Boucherie.xp")+2);
				p.sendMessage(ChatColor.AQUA + "Boucherie + 2 XP");
				
				if (playerData.getInt("Metier.Boucherie.xp") >= xp) {
					playerData.setInt("Metier.Boucherie.level", playerData.getInt("Metier.Boucherie.level")+1);
					playerData.setInt("Metier.Boucherie.xp", 0);
					p.sendMessage(ChatColor.AQUA + "Vous passez niveau " + playerData.getInt("Metier.Boucherie.level") + "en Boucherie.");
				}
			}
			break;
			
		case CHICKEN:
			if (entity.getFireTicks() != 0) {
				viande = Material.COOKED_CHICKEN;
			} else {
				viande = Material.RAW_CHICKEN;
			}
			
			e.getDrops().clear();
			if (p.getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
				e.getDrops().add(new ItemStack(Material.FEATHER, boucherie_level+2*p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS)));
				e.getDrops().add(new ItemStack(viande, (int) Math.round(boucherie_level/2)+1*p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS)));
			} else {
				e.getDrops().add(new ItemStack(Material.FEATHER, boucherie_level));
				e.getDrops().add(new ItemStack(viande, (int) Math.round(boucherie_level/2)));
			}
			
			if (boucherie_level < 10) {
				playerData.setInt("Metier.Boucherie.xp", playerData.getInt("Metier.Boucherie.xp")+1);
				p.sendMessage(ChatColor.AQUA + "Boucherie + 1 XP");
				
				if (playerData.getInt("Metier.Boucherie.xp") >= xp) {
					playerData.setInt("Metier.Boucherie.level", playerData.getInt("Metier.Boucherie.level")+1);
					playerData.setInt("Metier.Boucherie.xp", 0);
					p.sendMessage(ChatColor.AQUA + "Vous passez niveau " + playerData.getInt("Metier.Boucherie.level") + "en Boucherie.");
				}
			}
			break;
			
		default:
			break;
			
		}		
    }
}
