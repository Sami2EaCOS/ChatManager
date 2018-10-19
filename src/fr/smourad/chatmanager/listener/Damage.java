package fr.smourad.chatmanager.listener;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.PlayerData;

public class Damage implements Listener {
	
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	public HashMap<String, Long> deathTimer = new HashMap<String, Long>();
	
	public int task;
	
	ChatManager main;
	
	public Damage(ChatManager plugin) {
		this.main = plugin;
	}
	
	@EventHandler
	public void onFalling(EntityDamageEvent e) {
		
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		Player p = (Player) e.getEntity();
		Damageable dam = (Damageable) p;
		
		String playerName = p.getName();
		
		main.playerDataLoad(playerName, p);
		PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
		
		if (e.getCause() == DamageCause.FALL) {
			
			if (!(playerfile.getDouble("Stats.sensibility") < 10)) {
				double damageGet = e.getFinalDamage();
				e.isCancelled();
				e.setDamage(damageGet - playerfile.getDouble("Stats.sensibility")*0.1);
			}
			
		}
		
		if (dam.getHealth() <= 4 && playerfile.getString("Player.race").equalsIgnoreCase("humain")) {
			
			int cooldownTime = 60*5;
			
			if (cooldowns.containsKey(playerName)) {
				long secondsLeft = ((cooldowns.get(playerName)/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
				if (secondsLeft > 0) {
					// p.sendMessage(ChatColor.DARK_PURPLE + "Volonte ecrasante sera a nouveau disponible dans " + secondsLeft + " secondes!");
				} else {
					cooldowns.put(playerName, System.currentTimeMillis());
					
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 3, true));	
					p.sendMessage(ChatColor.DARK_PURPLE + "Votre Volonte écrasante renonce de mourir maintenant");
				}
			} else {
				cooldowns.put(playerName, System.currentTimeMillis());
					
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 3, true));	
				p.sendMessage(ChatColor.DARK_PURPLE + "Votre Volonte écrasante renonce de mourir maintenant");
					
			}
		}
		
		double trueHealth = dam.getHealth() - e.getFinalDamage();
		
		if (deathTimer.containsKey(playerName)) {
			
			int cooldownTime = 15;
			
			long secondsLeft = ((deathTimer.get(playerName)/1000+cooldownTime) - (System.currentTimeMillis()/1000));
			
			if (secondsLeft > 0) {
				if (trueHealth <= 0.0) {
					// p.sendMessage(ChatColor.DARK_AQUA + "Tu es mort ");
				}
				
				if (secondsLeft > 12.0) {
					e.setCancelled(true);				
				}
			} else {
				if (trueHealth <= 0.0 && e.getDamage() <= 20.0) {
					deathTimer.put(playerName, System.currentTimeMillis());
					
					e.setCancelled(true);
					
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3*20, 0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3*20, 9));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3*20, 9));
					dam.setHealth(1.0);
					
					p.sendMessage(ChatColor.DARK_AQUA + "Vous vous sentez faible, vous vous évanouissez soudainement");
					
					task = Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
			            
			            @Override
			            public void run() {
			            	p.chat("/crawl");
			                Bukkit.getScheduler().cancelTask(task);
			            }
			            
			        }, 2);
				}
			}
		} else {
			if (trueHealth <= 0.0 && e.getDamage() <= 20.0) {
				
				deathTimer.put(playerName, System.currentTimeMillis());
				
				e.setCancelled(true);
				
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3*20, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3*20, 9));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3*20, 9));
				dam.setHealth(1.0);
				
				p.sendMessage(ChatColor.DARK_AQUA + "Vous vous sentez faible, vous vous évanouissez soudainement");
				
				task = Bukkit.getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
		            
		            @Override
		            public void run() {
		            	p.chat("/crawl");
		                Bukkit.getScheduler().cancelTask(task);
		            }
		            
		        }, 2);
			} else if (trueHealth <= 0.0) {
				p.sendMessage(ChatColor.DARK_AQUA + "Tu es mort");
			}
		}
	}
}


