package fr.smourad.chatmanager.listener;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.smourad.chatmanager.ChatManager;
import fr.smourad.chatmanager.Config;
import fr.smourad.chatmanager.PlayerData;

public class ReloadConfig implements Listener {
	
	Config config;
	ChatManager plugin;
	
	public ReloadConfig(ChatManager plugin, Config config) {
		this.config = config;
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		String playerName = p.getName();
		
		plugin.playerDataLoad(playerName, p);
		PlayerData playerfile = ChatManager.PlayerDataMap.get(p);
		
		Stats(playerfile, p);
		Race(playerfile, p);
		Nick(playerfile, p, playerName);
		
	}
	
	private void Stats(PlayerData playerfile, Player p) {
		if (playerfile.getFloat("Stats.dexterity") >= 10) {
			float moreSpeed = (float) (playerfile.getFloat("Stats.dexterity")*0.002);
			p.setWalkSpeed(0.2f + moreSpeed);
		} else {
			p.setWalkSpeed(0.2f);
		}
		
		if (playerfile.getDouble("Stats.constitution") >= 10) {
			if (playerfile.getDouble("Stats.constitution")%2 == 0) {
				p.setMaxHealth(20 + playerfile.getDouble("Stats.constitution")*0.25);
				p.setHealth(20 + playerfile.getDouble("Stats.constitution")*0.25);
			} else {
				p.setMaxHealth(20 + (playerfile.getDouble("Stats.constitution")-1)*0.5);
				p.setHealth(20 + (playerfile.getDouble("Stats.constitution")-1)*0.5);
			}	
			
		} else {
			p.setMaxHealth(20.0);
			p.setHealth(20.0);
		}
	}
	
	private void Nick(PlayerData playerfile, Player p, String playerName) {
		if (playerfile.getString("Player.nick").equals("reset")) {
			p.setDisplayName(playerName);
			p.setPlayerListName(playerName);
		} else {
			p.setDisplayName(playerfile.getString("Player.nick"));
			p.setPlayerListName(playerfile.getString("Player.nick"));
		}
	}

	private void Race(PlayerData playerfile, Player p) {
		Damageable dam = (Damageable) p;
		double life = dam.getMaxHealth();
		for (PotionEffect effect : p.getActivePotionEffects()) {
			p.removePotionEffect(effect.getType());
		}
		
		if (playerfile.getString("Player.race").equalsIgnoreCase("crapahuteur")) {
			p.setMaxHealth(life + 6);
			p.setHealth(life + 6);
			p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1, true));
			
		} else if (playerfile.getString("Player.race").equalsIgnoreCase("humain")) {
			p.setMaxHealth(life + 2);
			p.setHealth(life + 2);
			
		} else if (playerfile.getString("Player.race").equalsIgnoreCase("nain")) {
			p.setMaxHealth(life + 6);
			p.setHealth(life + 6);
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0, true));
			
		} else if (playerfile.getString("Player.race").equalsIgnoreCase("elfe")) {
			p.setMaxHealth(life + 4);
			p.setHealth(life + 4);
			
		} else if (playerfile.getString("Player.race").equalsIgnoreCase("gobelin")) {
			p.setMaxHealth(life - 2);
			p.setHealth(life - 2);
			double speed = p.getWalkSpeed();
			p.setWalkSpeed((float) (speed + 0.05));
			
		} else if (playerfile.getString("Player.race").equalsIgnoreCase("naga")) {
			p.setMaxHealth(life + 4);
			p.setHealth(life + 4);
			p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0, true));
			
		} else if (playerfile.getString("Player.race").equalsIgnoreCase("transcende")) {
			p.setMaxHealth(life - 4);
			p.setHealth(life - 4);
			
		} else if (playerfile.getString("Player.race").equalsIgnoreCase("arachnide")) {
			p.setMaxHealth(life + 2);
			p.setHealth(life + 2);
			double speed = p.getWalkSpeed();
			p.setWalkSpeed((float) (speed + 0.05f));
			
		}
	}
}
