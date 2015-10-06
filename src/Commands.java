package TweeseeBot;
//import org.jsoup.*;
//import java.net.URLEncoder;
//import org.jibble.pircbot.*;

public class Commands {
	public static String Test(){
		return "Test success";
	}
	public static String Help(){
		return "There is no help for you, yet";
	}
	/*public static String GoogleLucky(String searchTerms){
		String google = "http://www.google.com/search?q=";
		String search = searchTerms.substring(2);
		String charset = "UTF-8";
		String userAgent = "TweeseeBot 1.0";
		Elements links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).get().select("li.g>h3>a");

		for (Element link : links) {
		    String title = link.text();
		    String url = link.absUrl("href"); // Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
		    url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");

		    if (!url.startsWith("http")) {
		        continue; // Ads/news/etc.
		    }
		return "searching..";
	}*/
	
}
