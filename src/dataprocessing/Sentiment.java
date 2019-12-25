package dataprocessing;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.FeatureMap;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author u124320
 */
public class Sentiment {
    
    public String ComputeSentiment(Document document) throws IOException{
        String sentimentResult;
        AnnotationSet annotationSet = document.getAnnotations();
        AnnotationSet docSent = annotationSet.get("Senti");
        
        AnnotationSet OM =  document.getAnnotations("Original markups");
        FeatureMap tweet = OM.get(0).getFeatures();
        String created_at = tweet.get("created_at").toString();
        //FeatureMap tweet2 = OM.get(12).getFeatures();
        //String user = tweet2.get("user").toString();
        /*
        String lang = tweet.get("lang").toString();
        
        switch (lang) {
            case "es":
                lang= "spanish";
                break;
            case "ca":
                lang= "catalan";
                break;
            case "en":
                lang= "english";
                break;
            default:
                break;
        }*/
        
        Iterator<Annotation> ite= docSent.iterator();
        
        Annotation currSent;
        int countSentiment= 0;
        while(ite.hasNext()){
            currSent = ite.next();
            if(currSent.getFeatures().get("majorType").equals("POSITIVE")){
                countSentiment += 1;               
            }else{
                countSentiment -= 1;
            } 
        }
        if(countSentiment > 0){ sentimentResult = "Positive"; }
        else if(countSentiment < 0){ sentimentResult = "Negative"; }
        else{ sentimentResult = "Neutral"; }        
       
        document.getFeatures().put("sentiment", sentimentResult);
        document.getFeatures().put("date", created_at);
        //document.getFeatures().put("user", user);
        
        return sentimentResult;
            
    }
       
}
