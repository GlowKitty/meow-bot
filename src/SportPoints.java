public class SportPoints{
	private long athleteHash;
	private int sportPoint;
	public SportPoints(){}
	public SportPoints(String athlete, int sportPoint){
		this.athleteHash = hash(athlete);
		this.sportPoint = sportPoint;

	}
	public long getPoints(SportPoints points, String user){
		if (points.athleteHash == hash(user)){
			return points.sportPoint;
		}
		else{
			return 0;
		}
	}
	public boolean getNick(SportPoints points, String user){
		if (points.athleteHash == hash(user)){
			return true;
		}
		else{
			return false;
		}
	}
	private long hash(String name){
		char[] nameC = new char[name.length()];
		for (int i = 0; i < name.length(); i++){
			nameC[i] = Character.toLowerCase(name.charAt(i));
		}
		long hashR = 1;
		for (int i = 0; i < nameC.length; i++){//long code that hashes the name with a simple hash (maybe better one later?)
			switch (nameC[i]){
			case 'a': hashR = hashR * 1^i;
				break;
			case 'b': hashR = hashR * 2^i;
				break;
			case 'c': hashR = hashR * 3^i;
				break;
			case 'd': hashR = hashR * 4^i;
				break;
			case 'e': hashR = hashR * 5^i;
		    	break;
			case 'f': hashR = hashR * 6^i;
		    	break;
			case 'g': hashR = hashR * 7^i;
				break;
			case 'h': hashR = hashR * 8^i;
				break;
			case 'i': hashR = hashR * 9^i;
				break;
			case 'j': hashR = hashR * 10^i;
				break;
			case 'k': hashR = hashR * 11^i;
				break;
			case 'l': hashR = hashR * 12^i;
				break;
			case 'm': hashR = hashR * 13^i;
				break;
			case 'n': hashR = hashR * 14^i;
				break;
			case 'o': hashR = hashR * 15^i;
				break;
			case 'p': hashR = hashR * 16^i;
				break;
			case 'q': hashR = hashR * 17^i;
				break;
			case 'r': hashR = hashR * 18^i;
				break;
			case 's': hashR = hashR * 19^i;
				break;
			case 't': hashR = hashR * 20^i;
				break;
			case 'u': hashR = hashR * 21^i;
				break;
			case 'v': hashR = hashR * 22^i;
				break;
			case 'w': hashR = hashR * 23^i;
				break;
			case 'x': hashR = hashR * 24^i;
				break;
			case 'y': hashR = hashR * 25^i;
				break;
			case 'z': hashR = hashR * 26^i;
				break;
			default:  hashR = hashR * 27^i;
				break;
			}
		}
		return hashR;
	}
}