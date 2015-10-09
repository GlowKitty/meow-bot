#!/bin/bash
javac -cp .:lib/pircbot.jar:lib/commons-digester-2.1.jar:lib/commons-digester-rss.jar -d . @source.txt -Xlint:unchecked
java -cp .:lib/pircbot.jar:lib/commons-digester-2.1.jar:lib/commons-digester-rss.jar org.meowbot.MeowBotMain