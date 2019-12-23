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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author UPF
 */
public class SimpleHTMLExtractor {
    
    //CallSUMMAGapp constructorGapp = new CallSUMMAGapp();
    
        
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
    
    public String extractSecondContent(String urlWeb, String nameWeb, ConditionalSerialAnalyserController application) throws IOException, FeedException, IllegalArgumentException, ResourceInstantiationException {
        
        XmlReader reader = null;
        SyndFeed feed = null;
        URL url  = new URL(urlWeb);
        reader = new XmlReader(url);
        feed = new SyndFeedInput().build(reader);
        SimpleHTMLConstructor htmlConstructor = new SimpleHTMLConstructor();
        /*List<NewsSummarized> newsPreSummarizedList = new ArrayList<NewsSummarized>();
        List<NewsSummarized> newsSummarizedList = new ArrayList<NewsSummarized>();*/
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
                /*
                newsPreSummarized.setPageBody(content);
                newsPreSummarized.setPageLink(link);
                newsPreSummarized.setPageTitle(title);
                newsPreSummarized.setPageName(nameWeb);
                newsPreSummarizedList.add(newsPreSummarized);*/
            }   
        }
        /*
        for(NewsSummarized nPreSummarizedList : newsPreSummarizedList){
            NewsSummarized newsSummarized = new NewsSummarized();
            newsSummarized.setSummarizedContent(
                    constructorGapp.getTextNews(nPreSummarizedList.getPageBody()));
            newsSummarized.setPageTitle(nPreSummarizedList.getPageTitle());
            newsSummarized.setPageLink(nPreSummarizedList.getPageLink());
            newsSummarizedList.add(newsSummarized);
            
            
        }*/
        
        //body = htmlConstructor.getSecondBody(newsSummarizedList, nameWeb);
       
                
        return body;

    }
}

    