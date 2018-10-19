package fr.smourad.chatmanager;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	private YamlConfiguration config;
	private File configFile;
	
	public Config(File configfile) {
		this.config = new YamlConfiguration();
		this.configFile = configfile;
	}
	
	public boolean load() {
		try {
			if (!configFile.exists()) {
				configFile.createNewFile();
				
				loadDefaults();
			}
			config.load(configFile);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private void loadDefaults() {
	
		config.addDefault("Type.crier.signe", "!");
		config.addDefault("Type.crier.distance", 30);
		
		config.addDefault("Type.daction.signe", "_");
		config.addDefault("Type.daction.distance", 3);
		
		config.addDefault("Type.chuchoter.signe", "#");
		config.addDefault("Type.chuchoter.distance", 3);
		
		config.addDefault("Type.hrp.signe", "(");
		config.addDefault("Type.hrp.distance", 30);
		
		config.addDefault("Type.lore.signe", "$");
		config.addDefault("Type.lore.distance", 20);
		
		config.addDefault("Type.absolu.signe", ":");
		
		config.addDefault("Type.action.signe", "*");
		config.addDefault("Type.action.distance", 15);
		
		config.addDefault("Type.parler.distance", 15);
		
		config.addDefault("Type.roll.distance", 15);
		
		config.options().copyDefaults(true);
		save();
	}
	
	public boolean save() {
		try {
			config.save(configFile);
		} catch (Exception e) {
			
		}
		return true;
	}
	
	public void setString(String path, String nick) {
		config.set(path, nick);
		save();
	}
	
	public List<?> getList(String path) {
		return config.getList(path);
	}
	
	public void addToList(String path, String nick) {
		List<String> l = config.getStringList(path);
		l.add(nick);
		config.set(path, l);
		save();
	}
	
	
}
