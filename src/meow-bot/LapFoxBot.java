package org.meowbot;
import java.io.IOException;
import java.io.FileNotFoundException;
public class LapFoxBot extends MeowBot {
    public RssFeedUpdater feed = null;
    public void rssInitAgain() throws FileNotFoundException, IOException, ClassNotFoundException{
        feed = new RssFeedUpdater();
        feed.rssInit();
        System.out.println("init success for Tweesee");
    }
    protected void onServerPing(String response) throws NullPointerException {
    	this.sendRawLine("PONG " + response);
        String tmp = "";
        try{
            tmp = feed.rss();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            if (tmp.length() > 0){
                sendMessage("#lapfoxtrax", tmp);
            }
        }
    }
}