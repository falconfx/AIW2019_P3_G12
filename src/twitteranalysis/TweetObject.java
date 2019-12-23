package twitteranalysis;

import java.util.List;

/**
 *
 * @author u124320
 */
public class TweetObject {
    public HashtagObject hashtagObject;
    public String userName;
    public String dateTime;
    public String sentiment;
    public String hashtag;
    public String sentence;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }


    public HashtagObject getHashtagObject() {
        return hashtagObject;
    }

    public void setHashtagObject(HashtagObject hashtag) {
        this.hashtagObject = hashtag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    
     public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
    
    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
    

    
    
}
