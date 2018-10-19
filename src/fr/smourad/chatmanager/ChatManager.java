package fr.smourad.chatmanager;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.smourad.chatmanager.commands.Stats;
import fr.smourad.chatmanager.commaner.InventoryJobs;
import fr.smourad.chatmanager.commaner.InventoryProfile;
import fr.smourad.chatmanager.commands.Desc;
import fr.smourad.chatmanager.commands.Nick;
import fr.smourad.chatmanager.commands.Race;
import fr.smourad.chatmanager.commands.Roll;
import fr.smourad.chatmanager.listener.BlockBreak;
import fr.smourad.chatmanager.listener.Chat;
import fr.smourad.chatmanager.listener.Damage;
import fr.smourad.chatmanager.listener.EntityDamageByEntity;
import fr.smourad.chatmanager.listener.EntityDeath;
import fr.smourad.chatmanager.listener.FoodLevelChange;
import fr.smourad.chatmanager.listener.Move;
import fr.smourad.chatmanager.listener.ReloadConfig;
import fr.smourad.chatmanager.listener.RightClick;
import fr.smourad.chatmanager.listener.ToggleSneak;

public class ChatManager extends JavaPlugin {
	
	public static Config config = null;
	public static HashMap<Player, PlayerData> PlayerDataMap = new HashMap<Player, PlayerData>();
	
	public Permission playerPermissionLore = new Permission("chatManager.lore");
	public Permission playerPermissionAbsolu = new Permission("chatManager.absolu");
	public Permission playerPermissionAdmin = new Permission("chatManager.admin");
	
	@Override
	public void onEnable() {
		
		loadConfig();
		loadCommands();
		loadListeners();
		loadCommaner();
		
		PluginManager pm = getServer().getPluginManager();
		pm.addPermission(playerPermissionLore);
		pm.addPermission(playerPermissionAbsolu);
		pm.addPermission(playerPermissionAdmin);
		
	}
	
	@Override
	public void onDisable() {

	}
										
	public void playerDataLoad(String playername, Player p) {
		File dir = new File(Bukkit.getPluginManager().getPlugin("ChatManager").getDataFolder() + "/" + "Playerdata");
		if (!dir.exists()) dir.mkdir();
		
		File file = new File(dir, playername + ".yml");
		PlayerData playerdata = new PlayerData(file, playername);
		
		if (!playerdata.load()) {
			throw new IllegalStateException("The player data file for player " + playername + " was not loaded correctly.");
		} else {			
			PlayerDataMap.put(p, playerdata);
		}
	}

	public void loadConfig() {
		File dir = this.getDataFolder();
		if(!dir.exists()) dir.mkdir();
		
		File file = new File(dir, "config.yml");
		config = new Config(file);
		
		if (!config.load()) {
			this.getServer().getPluginManager().disablePlugin(this);
			throw new IllegalStateException("The config was not loaded correctly!");
		}
	}

	public void loadCommands() {
		this.getCommand("nick").setExecutor(new Nick(this));
		this.getCommand("roll").setExecutor(new Roll(this));
		this.getCommand("stats").setExecutor(new Stats(this));
		this.getCommand("race").setExecutor(new Race(this));
		this.getCommand("desc").setExecutor(new Desc(this));
	}
	
	public void loadListeners() {
		this.getServer().getPluginManager().registerEvents(new ReloadConfig(this, config), this);
		this.getServer().getPluginManager().registerEvents(new Chat(this), this);
		this.getServer().getPluginManager().registerEvents(new Damage(this), this);
		this.getServer().getPluginManager().registerEvents(new Move(this), this);
		this.getServer().getPluginManager().registerEvents(new RightClick(this), this);
		this.getServer().getPluginManager().registerEvents(new FoodLevelChange(this), this);
		this.getServer().getPluginManager().registerEvents(new ToggleSneak(this), this);
		this.getServer().getPluginManager().registerEvents(new BlockBreak(this), this);
		this.getServer().getPluginManager().registerEvents(new EntityDamageByEntity(this), this);
		this.getServer().getPluginManager().registerEvents(new EntityDeath(this), this);
	}
	
	public void loadCommaner() {
		InventoryJobs ij = new InventoryJobs(this);
		
		this.getCommand("metier").setExecutor(ij);
		this.getServer().getPluginManager().registerEvents(ij, this);
		
		InventoryProfile ip = new InventoryProfile(this);
		
		this.getCommand("profil").setExecutor(ip);
		this.getServer().getPluginManager().registerEvents(ip, this);
	}
}
	
	