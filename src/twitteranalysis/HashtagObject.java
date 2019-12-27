package twitteranalysis;

import java.util.ArrayList;
import java.util.List;


public class HashtagObject {
    public String topic;
    public List<String> hastagsNames = new ArrayList<String>();
    
    String nameHashtag;
   
    SentimentObject sentiments = new SentimentObject();
    
    int numberOfPositives;
    int numberOfNegatives;
    int numberOfNeutrals;

    public SentimentObject getSentiments() {
        return sentiments;
    }

    public void setSentiments(SentimentObject sentiments) {
        this.sentiments = sentiments;
    }

    public List<String> getHastagsNames() {
        return hastagsNames;
    }

    public void setHastagsNames(List<String> hastagsNames) {
        this.hastagsNames = hastagsNames;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getNameHashtag() {
        return nameHashtag;
    }

    public void setNameHashtag(String nameHashtag) {
        this.nameHashtag = nameHashtag;
    }

    public int getNumberOfPositives() {
        return numberOfPositives;
    }

    public void setNumberOfPositives(int numberOfPositives) {
        this.numberOfPositives = numberOfPositives;
    }

    public int getNumberOfNegatives() {
        return numberOfNegatives;
    }

    public void setNumberOfNegatives(int numberOfNegatives) {
        this.numberOfNegatives = numberOfNegatives;
    }

    public int getNumberOfNeutrals() {
        return numberOfNeutrals;
    }

    public void setNumberOfNeutrals(int numberOfNeutrals) {
        this.numberOfNeutrals = numberOfNeutrals;
    }
    
    
}
