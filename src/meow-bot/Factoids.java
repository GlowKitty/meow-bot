package org.meowbot;
import java.util.*;
import java.io.*;
public class Factoids implements Serializable{
	String topic;
	ArrayList facts = new ArrayList();
	public Factoids(){}
	public Factoids(String topic, String newFact){
		this.topic = topic;
		//this.facts = new ArrayList();
		this.facts.add((String) newFact);
	}
	public void setTopic(String topic){
		this.topic = topic;
	}
	public void newFactoid(String newFact){
		this.facts.add(newFact);
	}
	public void rmFactoid(int factNum){
		this.facts.remove(factNum);
	}
	public String viewFactoids(){
		String toReturn = this.topic + " is ";
		if (this.facts.size() == 1){
			toReturn = toReturn + this.facts.get(0);
		}
		else if (this.facts.size() == 0){
			return "No factoids for " + topic;
		}
		else{
			for (int i = 0; i < this.facts.size(); i++){
				int iTemp = i + 1;
				toReturn = toReturn + "(#" + iTemp + ") " + this.facts.get(i) + " ";
			}
		}
		return toReturn;
	}
	public String getTopic(){
		return this.topic;
	}
}