package org.meowbot;
import java.io.IOException;
import org.jibble.pircbot.*;
public class MeowBotMain {
	public static void main(String[] args) throws Exception, IOException {
		MeowBot bot = new MeowBot();
		bot.setNameAgain("MeowBot");
		bot.lTInit(); //better way of doing this needed *soon*
		bot.setVerbose(true);
		bot.connect("irc.synirc.net");
		bot.identify("sw113231");
		bot.joinChannel("#friendshiptriangle");

		MeowBotC bot2 = new MeowBotC("GlowBot");
		bot2.lTInit();
		bot2.setVerbose(true);
		bot2.connect("chat.freenode.net");//chat.freenode.net
		bot2.joinChannel("#bmhsprogramming");

		LapFoxBot bot3 = new LapFoxBot();
		bot3.setNameAgain("Tweesee");
		bot3.rssInitAgain();
		bot3.lTInit();
		bot3.setVerbose(true);
		bot3.connect("irc.esper.net");
		bot3.joinChannel("#lapfoxtrax");

		while (true){
			if (!bot.isConnected()){
				bot.connect("irc.synirc.net");
				bot.identify("sw113231");
				bot.joinChannel("#friendshiptriangle");
			}
			
			if (!bot2.isConnected()){
				bot2.connect("chat.freenode.net");
				bot2.joinChannel("#bmhsprogramming");
			}

			if (!bot3.isConnected()){
				bot3.connect("irc.esper.net");
				bot3.joinChannel("#lapfoxtrax");
			}
		}
	}
}
