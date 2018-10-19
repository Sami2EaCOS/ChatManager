package fr.smourad.chatmanager.helping;

public class HashMapBooster {

	public String playerName;
	public int number;
	public long long_number;
	
	public HashMapBooster(String playerName, int number) {
		this.number = number;
		this.playerName = playerName;
	}
	
	public HashMapBooster(long long_number, int number) {
		this.long_number = long_number;
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public long getLong() {
		return long_number;
	}
}
