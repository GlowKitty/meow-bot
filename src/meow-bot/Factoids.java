package org.meow_bot;
import java.util.*;
public class Factoids{
	String topic;
	//String[] facts = new String[20];
	ArrayList facts = new ArrayList();
	public Factoids()
	public Factoids(String topic, String newFact){
		this.topic = topic;
		this.facts.add(newFact);
	}
	public setTopic(String topic){
		this.topic = topic;
	}
	public newFactoid(String newFact){
		this.facts.add(newFact);
	}
	public rmFactoid(Factoid fact, int factNum){
		fact.facts.remove(factNum);
	}
	public String viewFactoids(Factoid fact){
		String toReturn = fact.topic + " is ";
		if (fact.facts.size() = 1){
			toReturn = toReturn + fact.facts.get(i);
		}
		else{
			for (int i = 0; i < fact.facts.size(); i++){
				int iTemp = i + 1;
				toReturn = toReturn + "(#" + iTemp + ") " + fact.facts.get(i) + " ";
			}
		}
		return toReturn;
	}
}