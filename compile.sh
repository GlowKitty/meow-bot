#!/bin/bash
echo ">>>Compiling MeowBot"
javac -cp .:lib/pircbot.jar:lib/commons-logging-1.2.jar:lib/commons-beanutils-1.9.2.jar:lib/commons-digester-2.1.jar:lib/commons-digester-rss.jar -d . @.sources -Xlint:unchecked
echo ">>>Creating .jar file"
jar cfm MeowBot.jar manifest org/meowbot/*.class
echo ">>>End"