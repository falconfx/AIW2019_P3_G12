package web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//import aiw2019_p2_g12.NewsSummarized;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import gate.creole.ConditionalSerialAnalyserController;
import gate.creole.ResourceInstantiationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Iterator;
import java.util.List;
import twitteranalysis.HashtagObject;
import twitteranalysis.SentimentObject;
import twitteranalysis.TweetTreatment;


/**
 *
 * @author UPF
 */
public class SimpleHTMLExtractor {
    
    //CallSUMMAGapp constructorGapp = new CallSUMMAGapp();
    /*
        
    public static String extractFromLaVanguardia(String url) throws IOException {
        String text="";
        Document doc;
        doc = (Document) Jsoup.connect(url).get();

        Elements els = doc.select("div [itemprop=articleBody]");
       
        for(Element item : els){
            text = text + " " + item.select("p").text() ;
        }
        
        return text;
    }
    
    
    public static String extractFromTheGuardian(String url) throws IOException {
        String text="";
        Document doc = (Document) Jsoup.connect(url).get();
        Elements els;
       
        els=doc.getElementsByTag("div");
        String txt;
        for(Element ele: els) {
            if(ele.hasAttr("itemprop")) {
                if(ele.attributes().get("itemprop").equals("articleBody")) {
                    txt=ele.text();
                    text=text+ " " + txt ;
                }
            }
            
        }
        
        return text;
    }
    
    public String extractFirstContent(String nameWeb){
        String firstBody = "";
        SimpleHTMLConstructor htmlConstructor = new SimpleHTMLConstructor();

        firstBody = htmlConstructor.getFirstBody(nameWeb);
        
        return firstBody;
    }
    */
    
    /*
    
    public String extractSecondContent(String urlWeb, String nameWeb, ConditionalSerialAnalyserController application) throws IOException, FeedException, IllegalArgumentException, ResourceInstantiationException {
        
        XmlReader reader = null;
        SyndFeed feed = null;
        URL url  = new URL(urlWeb);
        reader = new XmlReader(url);
        feed = new SyndFeedInput().build(reader);
        SimpleHTMLConstructor htmlConstructor = new SimpleHTMLConstructor();

        String content = "";
        String body = "";
       

        Iterator iterator = feed.getEntries().iterator();
        while(iterator.hasNext()) {        
            //NewsSummarized newsPreSummarized = new NewsSummarized();
            SyndEntry entry = (SyndEntry) iterator.next();
            String link = entry.getLink();
            String title = entry.getTitle();
            content = nameWeb.equals("LaVanguardia") ? 
                        extractFromLaVanguardia(link) : extractFromTheGuardian(link);
            if(!content.equals("")){

            }   
        }

        //body = htmlConstructor.getSecondBody(newsSummarizedList, nameWeb);
       
                
        return body;

    }*/
    
    
    public static String extractContent(String tabName){
    
        String content = "";
        switch(tabName){
            case "Hashtags":
                content += extractHashtags();
                break;
            case "UsersMentioned":
                content += extractUsersMentioned();
                break;
            case "Date":
                content += extractDate();
                break;
            case "HeatMap":
                content += extractHeatMap();
                break;
            case "CircleMap":
                content += extractCircleMap();
                break;
        }
        
        return content;
    }
    

    public static String extractHashtags(){
        String content = "";
        TweetTreatment tTreatment = new TweetTreatment();
        HashMap<String, HashtagObject> hashMap = tTreatment.getHashtagSentimentInfo();
        content += "<div class=\"contentTabulator\" id=\"Hashtags\" style=\"display:none;padding:2%;\">\n<center>";
            for(Object s: hashMap.keySet()){
                content += "<div id=\"chart_div" + s + "\" class=\"chart\" ></div>\n";
            }
        content += "</center>\n</div>\n\n";
        content += "<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>\n";

        for(Object s: hashMap.keySet()){
            HashtagObject v = (HashtagObject) hashMap.get(s);
            int positive = v.getSentiments().getPositive();
            int negative = v.getSentiments().getNegative();
            int neutral = v.getSentiments().getNeutral();
        
        
        content +=   "<script type='text/javascript'>\n" +
                    "\n" +
                    "      google.charts.load('current', {'packages':['corechart']});\n" +
                    "\n" +
                    "      google.charts.setOnLoadCallback(drawChart);\n" +
                    "\n" +
                    "      function drawChart() {\n" +
                    "\n" +
                    "        var data = new google.visualization.DataTable();\n" +
                    "        data.addColumn('string', 'Type');\n" +
                    "        data.addColumn('number', 'Quantity');\n" +
                    "        data.addRows([\n" +
                    "          ['Positive', "+ positive + "],\n" +
                    "          ['Negative', "+ negative + "],\n" +
                    "          ['Neutrals', "+ neutral + "],\n" +
                    "        ]);\n" +
                    "\n" +
                    "        // Set chart options\n" +
                    "        var options = {'title':'"+ s + "',\n" +
                    "                       'width':400,\n" +
                    "                       'height':300};\n" +
                    "\n" +
                    "        // Instantiate and draw our chart, passing in some options.\n" +
                    "        var chart = new google.visualization.PieChart(document.getElementById('chart_div"+s+"'));\n" +
                    "        chart.draw(data, options);\n" +
                    "      }\n" +
                    "    </script>\n";
        
        }
        return content;

    
    
    }
    
        
    public static String extractUsersMentioned(){
    
        return "";
    }
    
        
    public static String extractDate(){
    
        String content = "";
        TweetTreatment tTreatment = new TweetTreatment();
        HashMap<String, SentimentObject> hashMapDate = tTreatment.getHashtagDateInfo();
        content += "<div class=\"contentTabulator\" id=\"Date\" style=\"display:none;padding:2%;\">\n<center>";
            for(Object s: hashMapDate.keySet()){
                content += "<div id=\"chart_div" + s + "\" class=\"chart\" ></div>\n";
            }
        content += "</center>\n</div>\n\n";
        content += "<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>\n";

        for(Object s: hashMapDate.keySet()){
            SentimentObject v = (SentimentObject) hashMapDate.get(s);
            int positive = v.getPositive();
            int negative = v.getNegative();
            int neutral = v.getNeutral();
        
        
        content +=   "<script type='text/javascript'>\n" +
                    "\n" +
                    "      google.charts.load('current', {'packages':['corechart']});\n" +
                    "\n" +
                    "      google.charts.setOnLoadCallback(drawChart);\n" +
                    "\n" +
                    "      function drawChart() {\n" +
                    "\n" +
                    "        var data = new google.visualization.DataTable();\n" +
                    "        data.addColumn('string', 'Type');\n" +
                    "        data.addColumn('number', 'Quantity');\n" +
                    "        data.addRows([\n" +
                    "          ['Positive', "+ positive + "],\n" +
                    "          ['Negative', "+ negative + "],\n" +
                    "          ['Neutrals', "+ neutral + "],\n" +
                    "        ]);\n" +
                    "\n" +
                    "        // Set chart options\n" +
                    "        var options = {'title':'"+ s + "',\n" +
                    "                       'width':400,\n" +
                    "                       'height':300};\n" +
                    "\n" +
                    "        // Instantiate and draw our chart, passing in some options.\n" +
                    "        var chart = new google.visualization.PieChart(document.getElementById('chart_div"+s+"'));\n" +
                    "        chart.draw(data, options);\n" +
                    "      }\n" +
                    "    </script>\n";
        
        }
        return content;

    }
    
        
    public static String extractHeatMap(){
    
        return "";
    }
    
    public static String extractCircleMap(){
    
        return "";
    }
    
    

}

    