package org.meowbot;
import java.io.IOException;
import org.jibble.pircbot.*;
/*
	Dependancies:
		pircbot
		commons-digester-2.1
		commons-digester-rss
*/
public class MeowBotMain {
	public static void main(String[] args) throws Exception, IOException {
		MeowBot bot = new MeowBot();
		bot.setVerbose(true);
		bot.connect("irc.synirc.net");
		bot.identify("sw113231");
		bot.joinChannel("#friendshiptriangle");
		bot.lTInit(); //better way of doing this needed *soon*

		MeowBotC bot2 = new MeowBotC();
		bot2.setVerbose(true);
		bot2.connect("chat.freenode.net");
		bot2.joinChannel("#bmhsprogramming");
		bot2.lTInit();

		LapFoxBot bot3 = new LapFoxBot();
		bot3.setVerbose(true);
		bot3.connect("irc.esper.net");
		bot3.joinChannel("lapfoxtrax");
		bot3.rssInit();
	}
}