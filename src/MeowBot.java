import org.jibble.pircbot.*;
import java.io.*;
import java.util.*;
public class MeowBot extends PircBot{ //WARNING REMOVE THE FILE SPORT.POINTS BECAUSE ITS CHANGED
	public MeowBot(){
		this.setName("MeowBotTesting");//GlowBot for robotics, MeowBot for tringle
	}
	ArrayList sportPoints = new ArrayList();
	String[][] lT = new String[100][2];
	String[][] factoids = new String[100][100];
	public void lTInit() throws FileNotFoundException, IOException, ClassNotFoundException{
		for (int i = 0; i < 100; i++){
			lT[i][0] = "";
			lT[i][1] = "";
		}//wow this does so much more than just init latell
		File s = new File("sport.points");
		if (s.exists() == false){
			saveArray();
		}
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("sport.points"));
		ArrayList sPT = in.readObject();
		for (int i = 0; i < sPT.size(); i++){
			sportPoints.add(sPT.get(i));
		}
		for (int i = 0; i < 100; i++){//factoids
			for (int x = 0; x < 100; x++){
				factoids[i][x] = "";
			}
		}
		File f = new File("factoids.triangle");//factoids.triange
		if (f.exists() == false){
			saveFactoids();
		}
		ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("factoids.robot"));//factoids.triangle
		String[][] fTemp = (String[][]) in2.readObject();
		in2.close();
		for (int i = 0; i < 100; i++){
			for (int x = 0; x < 100; x++){
				factoids[i][x] = fTemp[i][x];
			}
		}
		System.out.println("init success");
	}
	protected void onMessage(String channel, String sender, String login, String hostname, String message){
    	char checkCommand = message.charAt(0);
		String command = message.substring(1);
    	if(checkCommand == '!') {
    		String[] cmdSplit = command.split(" ");
    		if(command.equalsIgnoreCase("test")){
    			sendMessage(channel, sender + ": Test succeeded.");
    		}
    		else if(command.equalsIgnoreCase("help")){
    			sendMessage(channel, sender + ": test, ping, version, one sport point <athlete>, minus one sport point <athlete>, score <athlete>, learn <thing> as <otherthing>,");
    		}
    		else if (command.equalsIgnoreCase("ping")){
    			sendMessage(channel, sender + ": pong");
    		}
    		else if (command.equalsIgnoreCase("version")){
    			sendMessage(channel, sender + ": MeowBot v1.4.2, latell works right, maybe! Coding by GlowKitty.");
    		}
    		else if (cmdSplit[0].equalsIgnoreCase("one") && cmdSplit[1].equalsIgnoreCase("sport") && cmdSplit[2].equalsIgnoreCase("point")){
    			sendMessage(channel, sender + ": adding one sport point to " + cmdSplit[3] + "'s score");
    			try {
					addSportPoint(cmdSplit[3]);//adds ONE sport point
				} catch (IOException e) {
					// TODO Auto-generated catch block
					sendMessage(channel, sender + ": well we fucked something up..........");
					e.printStackTrace();
				}
    			//sendMessage(channel, sender + ": " + cmdSplit[1] + " now has " + countSportPoints(cmdSplit[1]) + " sport points. What an athlete!");
    		}
    		else if (cmdSplit[0].equalsIgnoreCase("minus") && cmdSplit[1].equalsIgnoreCase("one") && cmdSplit[2].equalsIgnoreCase("sport") && cmdSplit[3].equalsIgnoreCase("point")){
    			sendMessage(channel, sender + ": subtracting one sport point from " + cmdSplit[4] + "'s score");
    			try {
					minusSportPoint(cmdSplit[4]);//sbtracts ONE sport point
				} catch (IOException e) {
					// TODO Auto-generated catch block
					sendMessage(channel, sender + ": well we fucked something up..........");
					e.printStackTrace();
				}
    			sendMessage(channel, sender + ": " + cmdSplit[4] + " now has " + countSportPoints(cmdSplit[4]) + " sport points. What an athlete!");
    		}
    		else if (cmdSplit[0].equalsIgnoreCase("score")){
    			sendMessage(channel, sender + ": " + cmdSplit[1] + " has " + countSportPoints(cmdSplit[1]) + " sport points. What an athlete!");
    		}
    		else if (cmdSplit[0].equalsIgnoreCase("sport") && cmdSplit[1].equalsIgnoreCase("points")){
    			sendMessage(channel, sender + ": " + cmdSplit[2] + " has " + countSportPoints(cmdSplit[2]) + " sport points. What an athlete!");
    		}
    		else if (cmdSplit[0].equalsIgnoreCase("latell")){
    			try{
    				laterTell(channel, sender, message);
    			}
    			catch(NullPointerException e){
    				try {
						lTInit();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						sendMessage(channel, sender + ": well we fucked something up..........");
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						sendMessage(channel, sender + ": well we fucked something up..........");
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						sendMessage(channel, sender + ": well we fucked something up..........");
						e1.printStackTrace();
					}
    				laterTell(channel, sender, message);
    			} 
    		}
    		else if (cmdSplit[0].equalsIgnoreCase("learn")){
    			String[] factCmd = command.split(" as ");
    			String userToLearn = factCmd[0].substring(6);
    			try {
					addFactoid(userToLearn, factCmd[1], channel);
					sendMessage(channel, sender + ": You did the good thing!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					sendMessage(channel, sender + ": well we fucked something up..........");
					e.printStackTrace();

				}
    		}
    		else if (cmdSplit[0].equalsIgnoreCase("forget")){
    			try {
					removeFactoid(command.substring(7, command.length()-2), Integer.parseInt(cmdSplit[cmdSplit.length - 1]));
					sendMessage(channel, sender + ": fucking fine");
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					sendMessage(channel, sender + ": well we fucked something up..........");
					e.printStackTrace();
				}
    		}
    		else {
    			sendMessage(channel, sender + ": No you're " + command + "-ing wrong!");
    		}
    	}
    	else if (checkCommand == '?'){
    		if (command.equals("")){
    			sendMessage(channel, "???");
    		}
    		else{
    			sendMessage(channel, getFactoids(command));
    		}
    	}
    	else {
    		laterTellSend(channel, sender);
    	}//probably not anything to do here...
    }
    protected void onJoin(String channel, String sender, String login, String hostname){
    	laterTellSend(channel, sender);
    }
	protected void laterTell(String channel, String sender, String message) throws NullPointerException{
		String[] splitMsg =  message.split(" ");
		String toBeTold = "";
		for (int i = 2; i < splitMsg.length; i++){
			toBeTold = toBeTold + splitMsg[i] + " ";
		}
		for (int x = 0; x < 50; x++){
			if(lT[x][0].equals("")){
				lT[x][0] = splitMsg[1];
				lT[x][1] = "<" + sender + "> " + toBeTold;
				x = 200;
			}
			else if (x == 49){
				lT[0][0] = splitMsg[1];
				lT[0][1] = toBeTold;
				x = 200;
			}
		}
		sendMessage(channel, sender + ": Ok, I'll let them know");
	}
	private void laterTellSend(String channel, String sender)throws NullPointerException{
    	for (int i = 0; i < 50; i++){
    		if (lT[i][0].equalsIgnoreCase(sender)){
    			sendMessage(channel, sender + ": " + lT[i][1]);
    			lT[i][0] = "";
    			lT[i][1] = "";
    		}
    	}
    	return;
    }
	private void addSportPoint(String sportsballPlayer) throws IOException{
		for (int i = 0; i < sportPoints.size; i++){
			if (SportPoints.hash(sportPoints.get(i), sportsballPlayer) == true){
				sportPoints.set(i, SportPoints(sportsballplayer, sportPoints.get(i).sportPoint + 1));
			}
		}
		return;
	}
	private void minusSportPoint(String sportsballPlayer) throws IOException{
		for (int i = 0; i < sportPoints.size(); i++){
			if (SportPoints.hash(sportPoints.get(i), sportsballPlayer) == true){
				sportPoints.set(i, SportPoints(sportsballplayer, sportPoints.get(i).sportPoint - 1));
			}
		}
		return;
	}
	private int countSportPoints(String sportsballPlayer){
		for (int i = 0; i < sportPoints.size(); i++)
		//int hashedName = hashName(sportsballPlayer);
		for (int i = 0; i < 100; i++){
			SportPoints.getNick(sportPoints.get(i), sportsballPlayer);
			if (sportPoints[i][0] == hashedName(sportsballplayer)){
				return sportPoints[i][1];
			}
		}
		return 0;
	}
	public int hashName(String name){
		char[] nameC = new char[name.length()];
		for (int i = 0; i < name.length(); i++){
			nameC[i] = Character.toLowerCase(name.charAt(i));
		}
		int hashR = 1;
		for (int i = 0; i < nameC.length; i++){
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
	private void addFactoid(String user, String fact, String channel) throws IOException{
		for (int i = 0; i < 100; i++){
			if (factoids[i][0].equalsIgnoreCase(user) || factoids[i][0].equals("")){
				factoids[i][0] = user;
				for (int x = 1; x < 100; x++){
					if (factoids[i][x].equalsIgnoreCase("")){
						factoids[i][x] = fact;
						saveFactoids();
						return;
					}
				}
			}
		}
		sendMessage(channel, "we've run out.. ... (extend yr damn arrays)");
	}
	private void removeFactoid(String user, int fact) throws IOException{
		for (int i = 0; i < 100; i++){
			if (factoids[i][0].equalsIgnoreCase(user)){
				factoids[i][fact] = "";
				for (int x = fact; x < 99; x++){
					factoids[i][x] = factoids[i][x + 1];
				}
				saveFactoids();
				return;
			}
		}
	}
	private String getFactoids(String user){
		String messageReturned = user + " is ";
		for (int i = 0; i < 100; i++){
			if (factoids[i][0].equalsIgnoreCase(user)){
				messageReturned = messageReturned + "(#1) " + factoids[i][1];
				for (int x = 2; x < 100; x++){
					if (factoids[i][x].equals("")){
						x = 200;
						i = 200;
					}
					else {
						messageReturned = messageReturned + ", (#" + x + ") " + factoids[i][x];
					}
				}
			}
		}
		return messageReturned;
	}
	public void saveArray() throws IOException {
    	ObjectOutputStream out = null;
    	try {
			out = new ObjectOutputStream(
					new FileOutputStream("sport.points")
					);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	out.writeObject(sportPoints);
		out.flush();
	    out.close();
	}
	public void saveFactoids() throws IOException {
    	ObjectOutputStream out = null;
    	try {
			out = new ObjectOutputStream(
					new FileOutputStream("factoids.robot")//factoids.triangle
					);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	out.writeObject(factoids);
		out.flush();
	    out.close();
	}
}