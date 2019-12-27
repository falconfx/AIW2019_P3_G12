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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;    
import java.text.SimpleDateFormat;  
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
    
    static final int init_numbers = 0;
    
    public static HashMap<String, HashtagObject> hashTable;
    public static HashMap<String, SentimentObject> dateTable;


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
            "#boxing", "#fifa"
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
    
    public static void analyzeInformation(String computedPath) throws ResourceInstantiationException, IOException, ParseException{
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
        
       

        //tweetsTesting();
        
        //Probar hashmap
        hashTable = new HashMap<String, HashtagObject>();
        hashTable = ClassifierHashtags(finalListTweet,firstHashtags, secondHashtags, thirdHashtags);
        //printHashtagsHashMap(hashTable);
        
        //Probar datemap
        dateTable = new HashMap<String, SentimentObject>();
        dateTable = ClassifierDate(finalListTweet);
        printClassifierDate(dateTable);
        
        //Probar usersmap
        /*HashMap<String, SentimentObject> usersTable = new HashMap<String, SentimentObject>();
        usersTable = ClassifierUserMention(finalListTweet);
        printClassifierUserMention(usersTable);*/
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
        String userName = "";
        String dateTweet = "";
        Date date = new Date();

        AnnotationSet hashtagAnnotations = annotationSet.get("Hashtag");
        Iterator<Annotation> iteratorH = hashtagAnnotations.iterator();
        tweetObject.setSentence(contentOfDocument);
        List<String> hashtagsList = new ArrayList<String>();
        HashtagObject hashtagObject = new HashtagObject();
        Annotation simpleAnnotation = null;

        while(iteratorH.hasNext()){
            featureMap = document.getFeatures();
            sentiment = (String) featureMap.get("sentiment");
            dateTweet = (String) featureMap.get("date");
            //userName = (String) featureMap.get("user");
            
            simpleAnnotation = iteratorH.next();
            getStartNode = simpleAnnotation.getStartNode().getOffset();
            getFinalNode = simpleAnnotation.getEndNode().getOffset();
            hashtag = (contentOfDocument.substring(getStartNode.intValue(), getFinalNode.intValue())).toLowerCase();

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.UK);
            date = dateFormat.parse(dateTweet);
            
            if(hashtag != null && !hashtag.isEmpty()){
                hashtagsList.add(hashtag); 
            }
        }
        hashtagObject.setHastagsNames(hashtagsList);
        tweetObject.setHashtagObject(hashtagObject);
        tweetObject.setSentiment(sentiment);
        //tweetObject.setUserName(userName);
        tweetObject.setDateTime(date);

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
        
    
    
    public static void tweetsTesting() throws ParseException{
        for(TweetObject i : finalListTweet) {
            System.out.println("***************************");
            System.out.println("Tweet: " + i.getSentence());
            System.out.print("HashTags: ");
            for(String str : i.getHashtagObject().getHastagsNames()) {
                System.out.print(str +", ");
            }
            //Timestamp to datetime or date
            //Date date1 = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss").parse(i.getDateTime());
            //Timestamp ts = new Timestamp(date1.getTime());  
            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            //System.out.println("- -DATE:" + formatter.format(ts));
            System.out.println("- -DATE:" + i.getDateTime());
            System.out.println("");
            System.out.println("Tweet Sentiment: " + i.getSentiment());
            System.out.println("***************************");
            System.out.println("");
           
        }
    }
    
    public HashMap<String, HashtagObject> getHashtagSentimentInfo(){
        return hashTable;
    }
    
    public HashMap<String, SentimentObject> getHashtagDateInfo(){
        return dateTable;
    }

    
   public static HashMap ClassifierHashtags (List<TweetObject> tweetsList, List<String> HashtagsList1, List<String> HashtagsList2, List<String> HashtagsList3 ){
    
    List<String> AllHashtags = new ArrayList<String>();
    AllHashtags.addAll(HashtagsList1);
    AllHashtags.addAll(HashtagsList2);
    AllHashtags.addAll(HashtagsList3);
    
    HashMap<String, HashtagObject> hashTable = new HashMap<String, HashtagObject>();
    /*
    for(String hashtag : AllHashtags){
        HashtagObject hashtagObject = new HashtagObject();
        hashtagObject.setNameHashtag(hashtag);
        hashtagObject.getSentiments().setPositive(init_numbers);
        hashtagObject.getSentiments().setNegative(init_numbers);
        hashtagObject.getSentiments().setNeutral(init_numbers);
        //hashtagObject.setNumberOfPositives(init_numbers);
       //hashtagObject.setNumberOfNegatives(init_numbers);
        //hashtagObject.setNumberOfNeutrals(init_numbers);
        hashTable.put(hashtag, hashtagObject);
    }
    */
    for(TweetObject tweet : tweetsList){
        for(String hashtag : tweet.getHashtagObject().getHastagsNames()) {
            
            if(AllHashtags.contains(hashtag)){
                if(hashTable.containsKey(hashtag)){
                    if(tweet.getSentiment().equals("Positive")){
                        hashTable.get(hashtag).getSentiments().setPositive(hashTable.get(hashtag).getSentiments().getPositive() + 1);
                        //hashTable.get(hashtag).setNumberOfPositives(hashTable.get(hashtag).getNumberOfPositives() + 1);
                    }else if(tweet.getSentiment().equals("Negative")){
                        hashTable.get(hashtag).getSentiments().setNegative(hashTable.get(hashtag).getSentiments().getNegative() + 1);
                        //hashTable.get(hashtag).setNumberOfNegatives(hashTable.get(hashtag).getNumberOfNegatives() + 1);
                    }else if(tweet.getSentiment().equals("Neutral")){
                        hashTable.get(hashtag).getSentiments().setNeutral(hashTable.get(hashtag).getSentiments().getNeutral() + 1);
                        //hashTable.get(hashtag).setNumberOfNeutrals(hashTable.get(hashtag).getNumberOfNeutrals() + 1);
                    }
                }else{
                    HashtagObject hashtagObject = new HashtagObject();
                    hashtagObject.setNameHashtag(hashtag);


                    if(tweet.getSentiment().equals("Positive")){
                        hashtagObject.getSentiments().setPositive(1);
                        hashtagObject.getSentiments().setNegative(init_numbers);
                        hashtagObject.getSentiments().setNeutral(init_numbers);
                        hashTable.put(hashtag, hashtagObject);

                    }else if(tweet.getSentiment().equals("Negative")){
                        hashtagObject.getSentiments().setPositive(init_numbers);
                        hashtagObject.getSentiments().setNegative(1);
                        hashtagObject.getSentiments().setNeutral(init_numbers);
                        hashTable.put(hashtag, hashtagObject);

                    }else if(tweet.getSentiment().equals("Neutral")){

                        hashtagObject.getSentiments().setPositive(init_numbers);
                        hashtagObject.getSentiments().setNegative(init_numbers);
                        hashtagObject.getSentiments().setNeutral(1);
                        hashTable.put(hashtag, hashtagObject);
                    }
                }
            }
        }
    }
    
    return hashTable;
   }
   
   public static void printHashtagsHashMap(HashMap hashTable){
        
        System.out.println("\n"+"-------------------------------");
        System.out.println("| Sentiments for each hashtag |");
        System.out.println("-------------------------------");

        for(Object s: hashTable.keySet()){
            HashtagObject v = (HashtagObject) hashTable.get(s);
            int positive = v.getSentiments().getPositive();
            int negative = v.getSentiments().getNegative();
            int neutral = v.getSentiments().getNeutral();
            
            int addition = positive + negative + neutral;

            System.out.println( s + " (" + addition +") ->" + " Positive: "+ positive + " Negative: "+negative+ " Neutral: "+neutral);
        }
    }
   
   public static HashMap ClassifierDate (List<TweetObject> tweetsList){
        HashMap<String, SentimentObject> dateTable = new HashMap<String, SentimentObject>();
        
        for(TweetObject tweet : tweetsList){
            Date date = tweet.getDateTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            String strDate = dateFormat.format(date);
            if(!tweet.getSentiment().isEmpty()){
                if(dateTable.containsKey(strDate)){
                
                    if(tweet.getSentiment().equals("Positive")){
                        dateTable.get(strDate).setPositive(dateTable.get(strDate).getPositive() + 1);
                    }else if(tweet.getSentiment().equals("Negative")){
                        dateTable.get(strDate).setNegative(dateTable.get(strDate).getNegative() + 1);
                    }else if(tweet.getSentiment().equals("Neutral")){
                        dateTable.get(strDate).setNeutral(dateTable.get(strDate).getNeutral() + 1);
                    }
               
                }else{
                    SentimentObject sentiment = new SentimentObject();
                    if(tweet.getSentiment().equals("Positive")){
                        sentiment.setPositive(1);
                        sentiment.setNegative(init_numbers);
                        sentiment.setNeutral(init_numbers);
                    }else if(tweet.getSentiment().equals("Negative")){
                        sentiment.setPositive(init_numbers);
                        sentiment.setNegative(1);
                        sentiment.setNeutral(init_numbers);
                    }else if(tweet.getSentiment().equals("Neutral")){
                        sentiment.setPositive(init_numbers);
                        sentiment.setNegative(init_numbers);
                        sentiment.setNeutral(1);
                    }
                    dateTable.put(strDate, sentiment);
                } 
            }
        }
        
        return dateTable;
   }   
   
   public static void printClassifierDate(HashMap dateTable){
        
        System.out.println("\n"+"-------------------------------");
        System.out.println("| Date for each tweet |");
        System.out.println("-------------------------------");

        for(Object s: dateTable.keySet()){
            SentimentObject v = (SentimentObject) dateTable.get(s);
            int positive = v.getPositive();
            int negative = v.getNegative();
            int neutral = v.getNeutral();
            
            int addition = positive + negative + neutral;

            System.out.println( s + " (" + addition +") ->" + " Positive: "+ positive + " Negative: "+negative+ " Neutral: "+neutral);
        }
    }
   
   
   public static HashMap ClassifierUserMention (List<TweetObject> tweetsList){
        HashMap<String, SentimentObject> usersTable = new HashMap<String, SentimentObject>();
        
        for(TweetObject tweet : tweetsList){
            String name = tweet.getUserName();
            if(usersTable.containsKey(name)){
                
                if(tweet.getSentiment().equals("Positive")){
                    usersTable.get(name).setPositive(usersTable.get(name).getPositive() + 1);
                }else if(tweet.getSentiment().equals("Negative")){
                    usersTable.get(name).setNegative(usersTable.get(name).getNegative() + 1);
                }else if(tweet.getSentiment().equals("Neutral")){
                    usersTable.get(name).setNeutral(usersTable.get(name).getNeutral() + 1);
                }
               
            }else{
                SentimentObject sentiment = new SentimentObject();
                if(tweet.getSentiment().equals("Positive")){
                    sentiment.setPositive(1);
                    sentiment.setNegative(init_numbers);
                    sentiment.setNeutral(init_numbers);
                }else if(tweet.getSentiment().equals("Negative")){
                    sentiment.setPositive(init_numbers);
                    sentiment.setNegative(1);
                    sentiment.setNeutral(init_numbers);
                }else if(tweet.getSentiment().equals("Neutral")){
                    sentiment.setPositive(init_numbers);
                    sentiment.setNegative(init_numbers);
                    sentiment.setNeutral(1);
                }
                usersTable.put(name, sentiment);
            }
        }
        
        return usersTable;
   }
   
   public static void printClassifierUserMention(HashMap usersTable){
        
        System.out.println("\n"+"-------------------------------");
        System.out.println("| Sentiments for each user |");
        System.out.println("-------------------------------");

        for(Object s: usersTable.keySet()){
            SentimentObject v = (SentimentObject) usersTable.get(s);
            int positive = v.getPositive();
            int negative = v.getNegative();
            int neutral = v.getNeutral();
            
            int addition = positive + negative + neutral;

            System.out.println( s + " (" + addition +") ->" + " Positive: "+ positive + " Negative: "+negative+ " Neutral: "+neutral);
        }
    }
   
}
