package fr.smourad.chatmanager;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerData {
	
	public YamlConfiguration Playerdata;
	public File Playerdatafile;
	private String playername;
	
	public PlayerData(File Playerdatafile, String playername) {
		this.Playerdata = new YamlConfiguration();
		this.Playerdatafile = Playerdatafile;
		this.playername = playername;
	}
	
	public boolean load() {
		try {
			if (!Playerdatafile.exists()) {
				Playerdatafile.createNewFile();
				loadDefaults();
			}
			Playerdata.load(Playerdatafile);
			return true;
		} catch (Exception e) {
			
			return false;
		}
	}
	
	public boolean save() {
		try {
			Playerdata.save(Playerdatafile);
		} catch (Exception e) {
			
		}
		return true;
	}
	
	private void loadDefaults() {
		Playerdata.addDefault("Player.name", playername);
		Playerdata.addDefault("Player.nick", "reset");
		Playerdata.addDefault("Player.race", "Humain");
		Playerdata.addDefault("Player.desc", "none");
		
		Playerdata.addDefault("Stats.strength", 0);
		Playerdata.addDefault("Stats.constitution", 0);
		Playerdata.addDefault("Stats.dexterity", 0);
		Playerdata.addDefault("Stats.sensibility", 0);
		Playerdata.addDefault("Stats.cleverness", 0);
		
		Playerdata.addDefault("Metier.Bucheron.level", 1);
		Playerdata.addDefault("Metier.Bucheron.xp", 0);
		
		Playerdata.addDefault("Metier.Mineur.level", 1);
		Playerdata.addDefault("Metier.Mineur.xp", 0);
		
		Playerdata.addDefault("Metier.Boucherie.level", 1);
		Playerdata.addDefault("Metier.Boucherie.xp", 0);
		
		Playerdata.options().copyDefaults(true);
		save();
	}
	
	public void setString(String path, String nick) {
		Playerdata.set(path, nick);
		save();
	}
	
	
	public String getString(String path) {
		return Playerdata.getString(path);
	}	
	
	public void setFloat(String path, float stats) {
		Playerdata.set(path, stats);
		save();
	}
	
	public float getFloat(String path) {
		return Playerdata.getInt(path);
	}
	
	public void setInt(String path, int stats) {
		Playerdata.set(path, stats);
		save();
	}
	
	public int getInt(String path) {
		return Playerdata.getInt(path);
	}
	
	public void setDouble(String path, double stats) {
		Playerdata.set(path, stats);
		save();
	}
	
	public double getDouble(String path) {
		return Playerdata.getDouble(path);
	}
	
}
