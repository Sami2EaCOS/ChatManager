package fr.smourad.chatmanager.helping;

public class Xp {
	
	public static int getXp(int args) {
		switch (args) {
		case 1:
			return 12;
			
		case 2:
			return 30;
			
		case 3:
			return 42;
			
		case 4:
			return 70;
			
		case 5:
			return 130;
			
		case 6:
			return 256;
			
		case 7:
			return 510;
			
		case 8:
			return 1020;

		case 9:
			return 2020;
		
		default:
			return 0;
		}
	}
}
