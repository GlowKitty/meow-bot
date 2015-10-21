package org.meowbot;
import java.io.IOException;
import org.jibble.pircbot.*;
public class MeowBotMain {
	public static void main(String[] args) throws Exception, IOException {
		/*MeowBot bot = new MeowBot();
		bot.setVerbose(true);
		bot.connect("irc.synirc.net");
		bot.identify("sw113231");
		bot.joinChannel("#friendshiptriangle");
		bot.lTInit(); //better way of doing this needed *soon*

		/*MeowBotC bot2 = new MeowBotC();
		bot2.setVerbose(true);
		bot2.connect("chat.freenode.net");
		bot2.joinChannel("#bmhsprogramming");
		bot2.lTInit();*/

		LapFoxBot bot3 = new LapFoxBot();
		bot3.setVerbose(true);
		bot3.connect("irc.esper.net");
		bot3.joinChannel("#lapfoxtrax");
		bot3.lTInit();
		bot3.rssInit();

		while (true){
			/*if (!bot.isConnected()){
				bot.connect("irc.synirc.net");
				bot.identify("sw113231");
				bot.joinChannel("#friendshiptriangle");
				bot.lTInit();
			}
			
			/*if (!bot2.isConnected()){
				bot2.connect("chat.freenode.net");
				bot2.joinChannel("#bmhsprogramming");
				bot2.lTInit();
			}*/
			
			if (!bot3.isConnected()){
				bot3.connect("irc.esper.net");
				bot3.joinChannel("#lapfoxtrax");
				bot3.rssInit();
			}
		}
	}
}
/*	TODO
		-factoids from array to arraylist of objects
		-latertell fixes
		-google integration
		-admin bot permissions
		-update lapfoxbot and meowbotc
*/
