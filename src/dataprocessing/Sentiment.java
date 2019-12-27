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
    
    public void ComputeSentiment(Document document) throws IOException{
        String sentimentResult;
        AnnotationSet annotationSet = document.getAnnotations();
        AnnotationSet sentimentAnnotation = annotationSet.get("Senti");
        AnnotationSet mentionedUAnnotation = document.getAnnotations().get("Token");
        AnnotationSet dateAnnotations =  document.getAnnotations("Original markups");

        Annotation auxiliarAnnotation;
        FeatureMap auxiliarFeatureMap;
        Long initNode, endNode;
        Iterator<Annotation> tokensIte = mentionedUAnnotation.iterator();
        String mentionedUsers="";
        String patternMentionedUsers="";
        String content=document.getContent().toString();
        while(tokensIte.hasNext()){
            auxiliarAnnotation = tokensIte.next();
            auxiliarFeatureMap = auxiliarAnnotation.getFeatures();
            initNode=auxiliarAnnotation.getStartNode().getOffset();
            endNode=auxiliarAnnotation.getEndNode().getOffset();
            patternMentionedUsers = content.substring(initNode.intValue(), endNode.intValue());
            // In order to get name of mentioned user
            if(patternMentionedUsers.contains("@")){ mentionedUsers = patternMentionedUsers; }
        }
        
        FeatureMap tweet = dateAnnotations.get(0).getFeatures();
        String created_at = tweet.get("created_at").toString();  
        Iterator<Annotation> ite= sentimentAnnotation.iterator();
        Annotation currSent;
        int countSentiment= 0;
        while(ite.hasNext()){
            currSent = ite.next();
            if(currSent.getFeatures().get("majorType").equals("POSITIVE")){ countSentiment += 1; }
            else{ countSentiment -= 1; } 
        }
        if(countSentiment > 0){ sentimentResult = "Positive"; }
        else if(countSentiment < 0){ sentimentResult = "Negative"; }
        else{ sentimentResult = "Neutral"; }        
       
        
        
        
        
        
        document.getFeatures().put("sentiment", sentimentResult);
        document.getFeatures().put("date", created_at);
        document.getFeatures().put("mentionedUsers", mentionedUsers);
        
           
    }
       
}
