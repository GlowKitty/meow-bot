import java.io.IOException;
import org.jibble.pircbot.*;
/*
	Dependancies:
		commons-digester-2.1
		commons-digester-rss
		commons-logging-1.2
		commons-beanutils-1.9.2
*/
public class MeowBotMain {
	public static void main(String[] args) throws Exception, IOException, IrcException {
		String channel = "#friendshiptriangle";
		String network = "irc.synirc.net";
		MeowBot bot = new MeowBot();
		bot.setVerbose(true);
		bot.connect(network);
		bot.identify("sw113231");
		bot.joinChannel(channel);
		bot.lTInit(); //better way of doing this needed *soon*

		String channel2 = "#bmhsprogramming";//2 bots in one? MADNESS! LETS GO FOR 3!
		String network2 = "chat.freenode.net";
		MeowBotC botC = new MeowBotC();
		bot2.setVerbose(true);
		bot2.connect(network2);
		bot2.joinChannel(channel2);
		bot2.lTInit();

		LapFoxBot bot3 = new LapFoxBot();
		bot3.setVerbose(true);
		bot3.connect("irc.esper.net");
		bot3.joinChannel("lapfoxtrax");
		bot3.rssInit();
	}
}