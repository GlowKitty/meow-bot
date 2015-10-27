package org.meowbot;
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
public class RssFeedUpdater{
	public RssFeedUpdater(){}
	public static Item rssAtZero = null;//initialise rss feed comparison item
	public static boolean _isRss = false;
	private static final String regex = "/[\\p{Alpha}\\p{Digit}]{6}/";
	public static void rssInit(){
		Channel rssChannel = null;
		try {
			rssChannel = refreshFeed();
		} catch (Exception e1) {
            System.out.println("Shit.");
			e1.printStackTrace();
		}
		Item rssItemsTemp[] = rssChannel.findItems();
		_isRss = true;
        rssAtZero = rssItemsTemp[0];
		System.out.println(">>Rss successfully initialised. *spellfixfix");
		try{
			rss();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
    public static String rss() throws Exception, NullPointerException{ //checks reddits rss feed and compares to previous item
    		Channel rssChannelTemp = null;
			try {
				rssChannelTemp = refreshFeed();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Item refreshedItems[] = rssChannelTemp.findItems();//init and populate refreshedItems[]
			if (!refreshedItems[0].getTitle().equals(rssAtZero.getTitle())) {//comparisons and line prints
				String shortenedUrl = shortenUrl(refreshedItems[0].getLink());
				rssAtZero = refreshedItems[0];
				return "New post: " + refreshedItems[0].getTitle() + " " + shortenedUrl;
			}
			System.out.println(">>>last post: " + refreshedItems[0].getTitle());
			return "";
    }
    protected static Channel refreshFeed() throws Exception {
		RSSDigester digester = new RSSDigester();
		String feed = "https://www.reddit.com/r/lapfoxtrax/new/.rss";
		URL url = new URL(feed);
		HttpURLConnection httpSource = (HttpURLConnection)url.openConnection();
		Channel rssChannel = (Channel)digester.parse(httpSource.getInputStream());
		return rssChannel;
    }
    protected static String shortenUrl(String url){
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
}