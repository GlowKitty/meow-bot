#!/bin/bash
echo ">>>Compiling MeowBot"
javac -cp .:lib/pircbot.jar:lib/commons-digester-2.1.jar:lib/commons-digester-rss.jar -d . @.sources -Xlint:unchecked
echo ">>>Compiling completed"
echo ">>>Running MeowBot"
java -cp .:lib/pircbot.jar:lib/commons-digester-2.1.jar:lib/commons-digester-rss.jar org.meowbot.MeowBotMain
echo ">>>End"