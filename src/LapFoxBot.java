package TweeseeBot;
import org.jibble.pircbot.*;
import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.Item;
import org.apache.commons.digester.rss.RSSDigester;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class LapFoxBot extends PircBot {
	public Item rssAtZero = null;//initialise rss feed comparison item
	public boolean _isRss = false;
	private static final String regex = "/[\\p{Alpha}\\p{Digit}]{6}/";
    public LapFoxBot() {
        this.setName("Tweesee");
    }
    public Item rssInit() throws Exception, NullPointerException {
    	Channel rssChannel = null;
		try {
			rssChannel = refreshFeed();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//rss refresh?
		Item rssItemsTemp[] = rssChannel.findItems();
		/*for (int i = 0; i < rssItemsTemp.length; i++){
			this._rssItems[i] = rssItemsTemp[i];
		}*/
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
				// TODO Auto-generated catch block
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
			this.sendRawLine("last post: " + refreshedItems[0].getTitle());
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
    	/*if (channel != "#lapfoxtrax" && sender == "GlowKitty" && message == "login sw113231") {
    		LapFoxBotMain._loggedIn = true;
    	}*/
    	if(checkCommand == '!') {
    		Commands cmd = new Commands();
    		cmd.commandRecieved(channel, sender, message);
    	}
    	else if (checkCommand == '?'){
    		sendMessage(channel, sender + ": Factoids implemented soon-ish.");//do this.thing();
    	}
    	else {}//probably not anything to do here...
    }
    
}
