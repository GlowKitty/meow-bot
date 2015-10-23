package org.meowbot;
import org.jibble.pircbot.*;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.Item;
import org.apache.commons.digester.rss.RSSDigester;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class LapFoxBot extends PircBot {
	public Item rssAtZero = null;//initialise rss feed comparison item
	public boolean _isRss = false;
	private static final String regex = "/[\\p{Alpha}\\p{Digit}]{6}/";
    public LapFoxBot() {
        this.setName("Tweesee");
    }
    ArrayList sportPoints = new ArrayList();
    ArrayList factoids = new ArrayList();
    String[][] lT = new String[100][2];
    public void lTInit() throws FileNotFoundException, IOException, ClassNotFoundException{
        for (int i = 0; i < 100; i++){
            lT[i][0] = "";
            lT[i][1] = "";
        }//wow this does so much more than just init latell
        File s = new File("sport.points");
        if (s.exists() == false){
            saveArray();
        }
        else{
            ObjectInput in = new ObjectInputStream(new FileInputStream("sport.points"));
            sportPoints = (ArrayList) in.readObject();
            in.close();
        }
        File f = new File("factoids");
        if (f.exists() == false){
            saveFactoids();
        }
        else{
            ObjectInput in2 = new ObjectInputStream(new FileInputStream("factoids"));
            factoids = (ArrayList) in2.readObject();
            in2.close();
        }
        System.out.println("init success for Tweesee");
    }
    public Item rssInit() throws Exception, NullPointerException {
        System.out.println(">>>Rss initialising");
    	Channel rssChannel = null;
		try {
			rssChannel = refreshFeed();
		} catch (Exception e1) {
            System.out.println("Shit.");
			e1.printStackTrace();
		}
		Item rssItemsTemp[] = rssChannel.findItems();
		this._isRss = true;
		sendMessage("#lapfoxtrax", "Rss successfully initialised.");
		return rssItemsTemp[0];
    }
    protected void onServerPing(String response) throws NullPointerException {
    	this.sendRawLine("PONG " + response);
    	if (_isRss == true){
    		try {
				rss();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    protected void rss() throws Exception, NullPointerException{ //checks reddits rss feed and compares to previous item
    		this.sendRawLine("checking rss feed");//
    		Channel rssChannelTemp = null;
			try {
				rssChannelTemp = refreshFeed();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Item refreshedItems[] = rssChannelTemp.findItems();//init and populate refreshedItems[]
			if (!refreshedItems[0].getTitle().equals(rssAtZero.getTitle())) {//comparisons and line prints
				String shortenedUrl = shortenUrl(refreshedItems[0].getLink());
	        	sendMessage("#lapfoxtrax","New post: " + refreshedItems[0].getTitle() + " " + shortenedUrl);
				System.out.println("New post on /r/lapfoxtrax: " + refreshedItems[0].getTitle() + " " + refreshedItems[0].getLink());
				rssAtZero = refreshedItems[0];
			}
			System.out.println("last post: " + refreshedItems[0].getTitle());
    }
    protected Channel refreshFeed() throws Exception {
		RSSDigester digester = new RSSDigester();
		String feed = "https://www.reddit.com/r/lapfoxtrax/new/.rss";
		URL url = new URL(feed);
		HttpURLConnection httpSource = 
			(HttpURLConnection)url.openConnection();
		Channel rssChannel=
				(Channel)digester.parse(httpSource.getInputStream());
		return rssChannel;
    }
    protected String shortenUrl(String url){
    	Pattern p = Pattern.compile(regex);
    	Matcher m = p.matcher(url);
    	int startR = 0;
    	int endR = 0;
    	m.find();
    	startR = m.start();
    	endR = m.end();
    	System.out.println(startR + " " + endR);
    	String regDone = "";
    	for (int o = startR + 1; o <= endR - 1; o++){
    		regDone = regDone + url.charAt(o);
    	}
    	System.out.println("url successfully shortened: http://redd.it/" + regDone);
    	return "http://redd.it/" + regDone;
    }
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
    	char checkCommand = message.charAt(0);
        String command = message.substring(1);
    	if(checkCommand == '!') {
    		String[] cmdSplit = command.split(" ");
            if(command.equalsIgnoreCase("test")){
                sendMessage(channel, sender + ": Test succeeded.");
            }
            else if(command.equalsIgnoreCase("help")){
                sendMessage(channel, sender + ": test, ping, version, one sport point <athlete>, minus one sport point <athlete>, score <athlete>, learn <thing> as <otherthing>, forget <thing> <number>, ?<thing>");
            }
            else if (command.equalsIgnoreCase("ping")){
                sendMessage(channel, sender + ": pong");
            }
            else if (command.equalsIgnoreCase("version")){
                sendMessage(channel, sender + ": MeowBot v2.0 with less bugs and actual features. Coding by GlowKitty.");
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
                String topic = factCmd[0].substring(6);
                try {
                    addFactoid(topic, factCmd[1]);
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
        }
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
        SportPoints sP = new SportPoints();
        sP.setName(sportsballPlayer);
        for (int i = 0; i < sportPoints.size(); i++){
            if (hashName(sportsballPlayer) == sP.getHash((SportPoints)sportPoints.get(i))){
                sP.setScore(sP.getPoints((SportPoints)sportPoints.get(i), sportsballPlayer) + 1);
                sportPoints.set(i, sP);
                saveArray();
                return;
            }
        }
        sP.setName(sportsballPlayer);
        sP.setScore(1);
        sportPoints.add(sP);
        saveArray();
        return;
    }
    private void minusSportPoint(String sportsballPlayer) throws IOException{
        SportPoints sP = new SportPoints();
        for (int i = 0; i < sportPoints.size(); i++){
            if (sP.getNick((SportPoints)sportPoints.get(i), sportsballPlayer) == true){
                sP.setName(sportsballPlayer);
                sP.setScore(sP.getPoints((SportPoints)sportPoints.get(i), sportsballPlayer) - 1);
                sportPoints.set(i, sP);
                saveArray();
                return;
            }
        }
    }
    private int countSportPoints(String sportsballPlayer){
        SportPoints sP = new SportPoints();
        for (int i = 0; i < sportPoints.size(); i++){
            if (sP.getNick((SportPoints)sportPoints.get(i), sportsballPlayer) == true){
                return sP.getPoints((SportPoints)sportPoints.get(i), sportsballPlayer);
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
            case 'a': hashR = hashR *  1^i;
                break;
            case 'b': hashR = hashR *  2^i;
                break;
            case 'c': hashR = hashR *  3^i;
                break;
            case 'd': hashR = hashR *  4^i;
                break;
            case 'e': hashR = hashR *  5^i;
                break;
            case 'f': hashR = hashR *  6^i;
                break;
            case 'g': hashR = hashR *  7^i;
                break;
            case 'h': hashR = hashR *  8^i;
                break;
            case 'i': hashR = hashR *  9^i;
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
    private void addFactoid(String topic, String fact) throws IOException{
        for(int i = 0; i < factoids.size(); i++){
            Factoids fct = (Factoids)factoids.get(i);
            if (fct.getTopic().equalsIgnoreCase(topic)){
                fct.newFactoid(fact);
                factoids.set(i, fct);
                saveFactoids();
                return;
            }
        }
        Factoids newFct = new Factoids();
        newFct.setTopic(topic);
        newFct.newFactoid(fact);
        factoids.add(newFct);
        saveFactoids();
        return;
    }
    private void removeFactoid(String topic, int factNum) throws IOException{
        for (int i = 0; i < factoids.size(); i++){
            Factoids fct = (Factoids)factoids.get(i);
            if (fct.getTopic().equalsIgnoreCase(topic)){
                fct.rmFactoid(factNum);
                factoids.set((int) i, (Factoids) fct);
                saveFactoids();
                return;
            }
        }
    }
    private String getFactoids(String topic){
        try{
            for (int i = 0; i < factoids.size(); i++){
                Factoids fct = (Factoids)factoids.get(i);
                if (fct.getTopic().equalsIgnoreCase(topic)){
                    return fct.viewFactoids();
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return "This is complete bullshit, youre bad at code";
        }
        return "No factoids for " + topic;
    }
    public void saveArray() throws IOException {
        ObjectOutput out = null;
        File f = new File("sport.points");
        if (!f.exists()){
            f.createNewFile();
        }
        try {
            out = new ObjectOutputStream(new FileOutputStream(f));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.writeObject(sportPoints);
        out.flush(); //flushes SPORTS toilet
        out.close();
    }
    public void saveFactoids() throws IOException {
        ObjectOutput out = null;
        File f = new File("factoids");
        if (!f.exists()){
            f.createNewFile();
        }
        try {
            out = new ObjectOutputStream(new FileOutputStream(f));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        out.writeObject(factoids);
        out.flush(); //flushes toilet
        out.close();
    }
}