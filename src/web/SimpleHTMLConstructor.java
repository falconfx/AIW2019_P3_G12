/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
//import aiw2019_p2_g12.NewsSummarized;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitteranalysis.TweetObject;
import twitteranalysis.TweetTreatment;

/**
 *
 * @author u124320
 */
public class SimpleHTMLConstructor {
    
    public void makeWeb() throws FileNotFoundException, UnsupportedEncodingException, IOException{
        String header, body, footer;
        List<TweetObject> tOBody = new ArrayList<TweetObject>();
        OutputStreamWriter osw;
        TweetTreatment tweetT = new TweetTreatment();
        File fout=new File("."+File.separator+"output"+File.separator+
                "index.html");
        FileOutputStream writer=new FileOutputStream(fout);
        osw=new OutputStreamWriter(writer,"utf-8");
        header = getHeader();
        tOBody =  tweetT.getTweetsPrint();
                //workWithCorpus(newsList, gappFilePath);
        footer = getFooter();

        try {
            osw.append(header+"\n");
            osw.flush();

            //osw.append(tOBody);

            osw.append(footer+"\n");
            osw.flush();


            osw.close();
        } catch (IOException ex) {
            Logger.getLogger(SimpleHTMLConstructor.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    
    }
    
    public String getHeader(){
        String filePath = "./output/components/header.html";
        StringBuilder contentBuilder = new StringBuilder();
 
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
 
        return contentBuilder.toString();
    }

    public String getFirstBody(String nameWeb){
        String body = "";
        
        body += "<li> <a class=\"page-scroll\" href=\"#\" onclick=\"showStuff('"+nameWeb+"')\">"+nameWeb+"</a> </li>\n";
        
        return body;
    }
    
    public String getSecondBody(/*List<NewsSummarized> webContent,*/ String nameWeb){
        String body = "";

        body  = body + "<div style=\"display:none;\" class=\"contentTabulator\" id=\""+nameWeb+"\">";
                /*for(NewsSummarized nSummarizedList : webContent){
                    body +=  "<div class=\"notice\">";
                    body += "<a href="+ nSummarizedList.getPageLink() +" target=\"_blank\"><h2>"+nSummarizedList.getPageTitle()+"</h2></a>"+"\n";
                    body +=  "<p>" + nSummarizedList.getSummarizedContent() + "</p>";
                    body += "<a href="+ nSummarizedList.getPageLink() +" class=\"btn btn-default btn-lg page-scroll\">Read More</a>";
                    body +=  "</div>";
                }*/
                body +=  "</div>";
        
        return body;
    }
    
     
    public String getFooter(){
        String filePath = "./output/components/footer.html";
        StringBuilder contentBuilder = new StringBuilder();
 
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
 
        return contentBuilder.toString();
    
    }
}
