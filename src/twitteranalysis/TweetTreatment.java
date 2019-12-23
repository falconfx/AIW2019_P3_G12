package twitteranalysis;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.creole.ResourceInstantiationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
//import static web.SimpleHTMLConstructor;


/**
 *
 * @author u124320
 */
public class TweetTreatment {
    
    //public static TweetObject tweetObject = new TweetObject();
    //public static HashtagObject hashtagObject = new HashtagObject();
    
    public static List<TweetObject> listTweetObject = new ArrayList<TweetObject>();
    public static List<HashtagObject> listHashtagObject = new ArrayList<HashtagObject>();
    
    public static List<TweetObject> finalListTweet = new ArrayList<TweetObject>();
    
    


    // Champions hashtags
    public static List<String> firstHashtags = Arrays.asList(
        new String[]{
            "#champions", "#football", "#championsleague", 
            "#soccer", "#laliga", "#realmadrid", 
            "#sport", "#ucl", "#chelsea", 
            "#a", "#champion", "#like", 
            "#premierleague", "#futbol", "#o", 
            "#team", "#sports", "#juventus",
            "#barcelona", "#follow", "#basketball", 
            "#winners", "#win", "#liverpool", 
            "#pfc", "#fitness", "#love", 
            "#boxing", "#fifa", "#bhfyp"
        }
    );
    
    // Games hashtags
    public static List<String> secondHashtags = Arrays.asList(
        new String[]{
            "#gaming", "#gamer", "#ps", 
            "#videogames", "#playstation", "#game", 
            "#games", "#xbox", "#fortnite", 
            "#twitch", "#pc", "#xboxone", 
            "#gta", "#gamers", "#youtube", 
            "#memes", "#pcgaming", "#gamergirl", 
            "#nintendo", "#pubg", "#videogame", 
            "#instagaming", "#callofduty", "#follow", 
            "#instagamer", "#gaminglife", "#streamer", 
            "#like", "#meme", "#bhfyp"
        }
    );
    
    // Politics hashtags
    public static List<String> thirdHashtags = Arrays.asList(
        new String[]{
            "#america",  "#bhfyp",  "#bjp",  
            "#brexit",  "#catalunya",  "#cdr",  
            "#congress",  "#conservative",  "#democrat",  
            "#democrats",  "#donaldtrump",  "#election",  
            "#elections",  "#españa",  "#follow",  
            "#freedom",  "#generalitat",  "#government",  
            "#history",  "#india",  "#liberal",  
            "#love",  "#maga",  "#meme",  
            "#memes",  "#news",  "#politica",  
            "#politicalmemes",  "#politics",  "#pp",  
            "#president",  "#pressos",  "#proces",  
            "#republican",  "#trump",  "#usa",  
            "#vote",  "#vox"
        }
    );

    
    
    public static void setInformation(){
        // First we declare hashtags by topic
        // Champios Object
        HashtagObject firstHObject = new HashtagObject();
        TweetObject firstTObject = new TweetObject();
        
        // Games Object
        HashtagObject secondHObject = new HashtagObject();
        TweetObject secondTObject = new TweetObject();
        
        // Politics Object
        HashtagObject thirdHObject = new HashtagObject();
        TweetObject thirdTObject = new TweetObject();
       

        // Champions Hashtags
        firstHObject.setHastagsNames(firstHashtags);
        firstHObject.setTopic("champions");
        firstTObject.setHashtagObject(firstHObject);
        listTweetObject.add(firstTObject);

        // Games Hashtags
        secondHObject.setHastagsNames(secondHashtags);
        secondHObject.setTopic("games");
        secondTObject.setHashtagObject(secondHObject);
        listTweetObject.add(secondTObject);
        
        // Politics Hashtags
        thirdHObject.setHastagsNames(thirdHashtags);
        thirdHObject.setTopic("politics");
        thirdTObject.setHashtagObject(thirdHObject);
        listTweetObject.add(thirdTObject);
                   
    }
    
    public static void analyzeInformation(String computedPath) throws ResourceInstantiationException, IOException{
        File computedPathDir = new File(computedPath);
        File[] computedFiles = computedPathDir.listFiles();
        Document document;
        FeatureMap featureMap = null;
        Long getStartNode = null, getFinalNode = null;
        TweetObject tweetObject = null;

        for (File computedFile : computedFiles){    
            tweetObject = new TweetObject();
            
            try {
                document = Factory.newDocument(new URL("file:///"+computedFile.getAbsolutePath()),"UTF-8");
                AnnotationSet annotationSet = document.getAnnotations();
                String contentOfDocument = document.getContent().toString();
                try {
                    setHashtag( getStartNode,
                            getFinalNode,
                            featureMap,
                            annotationSet,
                            contentOfDocument, 
                            document,
                            tweetObject);
                } catch (ParseException ex) {
                    Logger.getLogger(TweetTreatment.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(TweetTreatment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
       

        tweetsTesting();

    }

    public static void setHashtag(  Long getStartNode,
                                    Long getFinalNode, 
                                    FeatureMap featureMap, 
                                    AnnotationSet annotationSet, 
                                    String contentOfDocument, 
                                    Document document,  
                                    TweetObject tweetObject) throws IOException, ParseException{
        
        String hashtag = "";
        String sentiment = "";

        AnnotationSet hashtagAnnotations = annotationSet.get("Hashtag");
        Iterator<Annotation> iteratorH = hashtagAnnotations.iterator();
        tweetObject.setSentence(contentOfDocument);
        List<String> hashtagsList = new ArrayList<String>();
        HashtagObject hashtagObject = new HashtagObject();
        Annotation simpleAnnotation = null;

        while(iteratorH.hasNext()){
            featureMap = document.getFeatures();
            sentiment = (String) featureMap.get("sentiment");
            simpleAnnotation = iteratorH.next();
            getStartNode = simpleAnnotation.getStartNode().getOffset();
            getFinalNode = simpleAnnotation.getEndNode().getOffset();
            hashtag = (contentOfDocument.substring(getStartNode.intValue(), getFinalNode.intValue())).toLowerCase();

            if(hashtag != null && !hashtag.isEmpty()){
                hashtagsList.add(hashtag); 
            }
        }
        hashtagObject.setHastagsNames(hashtagsList);
        tweetObject.setHashtagObject(hashtagObject);
        tweetObject.setSentiment(sentiment);

        listTweetObject.add(tweetObject);
        
        Set<TweetObject> listTweetObjectHash = new HashSet<>(listTweetObject);
        listTweetObject.clear();
        listTweetObject.addAll(listTweetObjectHash);
        
        
        for(TweetObject tObject : listTweetObject){
            if( tObject.getHashtag() != null &&
                tObject.getSentence() != null && 
                !tObject.getHashtag().isEmpty() &&
                !tObject.getSentence().isEmpty() &&
                !tObject.getHashtag().equals(" ") &&
                !tObject.getSentence().equals(" ")
            )
            {
                tweetObject.setSentence(tObject.getSentence());
                tweetObject.setHashtagObject(tObject.getHashtagObject());
                tweetObject.setSentiment(tObject.getSentiment());
            }

        }
        finalListTweet.add(tweetObject);

    }
        
    
    
    public static void tweetsTesting(){
        for(TweetObject i : finalListTweet) {
            System.out.println("***************************");
            System.out.println("Tweet: " + i.getSentence());
            System.out.print("HashTags: ");
            for(String str : i.getHashtagObject().getHastagsNames()) {
                System.out.print(str +", ");
            }
            System.out.println("");
            System.out.println("Tweet Sentiment: " + i.getSentiment());
            System.out.println("***************************");
            System.out.println("");
        }
    }
    
    public List<TweetObject> getTweetsPrint(){
        return finalListTweet;
        /*
        List<String> tweets = new ArrayList<String>();
        
        
        return tweets;*/
    
    }
    
   
  
}
