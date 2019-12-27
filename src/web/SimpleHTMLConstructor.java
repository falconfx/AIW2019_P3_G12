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
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitteranalysis.TweetObject;
import static web.CreateMapToWorkOut.getMapsCode;


/**
 *
 * @author u124320
 */
public class SimpleHTMLConstructor {
    
    public static List<String> webTags = Arrays.asList(
        new String[]{
            "About", "Hashtags", "UsersMentioned", 
            "Date", "HeatMap", "CircleMap"
        }
    );    
    
    public void makeWeb() throws FileNotFoundException, UnsupportedEncodingException, IOException{
        String header, footer;
        OutputStreamWriter osw;
        File fout=new File("."+File.separator+"web"+File.separator+
                "index.html");
        FileOutputStream writer=new FileOutputStream(fout);
        osw=new OutputStreamWriter(writer,"utf-8");
        header = getHeader();
        footer = getFooter();

        try {
            osw.append(header+"\n");
            osw.flush();
            
            osw.append(makeBody());

            osw.append(footer+"\n");
            osw.flush();        


            osw.close();

     
            
        } catch (IOException ex) {
            Logger.getLogger(SimpleHTMLConstructor.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    
    }
    
    public String getHeader(){
        String filePath = "./web/components/header.html";
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

    public static String getFirstBody(String nameWeb){
        String body = "";
        

        if(nameWeb.equals("HeatMap")){
            body += "<li> <a class=\"page-scroll\" href=\"maps/heat-map-out.html\" target=\"_blank\">"+nameWeb+"</a> </li>\n";
        }
        else if(nameWeb.equals("CircleMap")){
            body += "<li> <a class=\"page-scroll\" href=\"maps/circle-map-out.html\" target=\"_blank\">"+nameWeb+"</a> </li>\n";
        }else{
            body += "<li> <a class=\"page-scroll\" href=\"#\" onclick=\"showStuff('"+nameWeb+"')\">"+nameWeb+"</a> </li>\n";
        }
        
        return body;
    }
   
     
    public String getFooter(){
        String filePath = "./web/components/footer.html";
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
    
    public static String makeBody(){
        SimpleHTMLExtractor htmlExtractor = new SimpleHTMLExtractor();
        String content = "";
        
       
 
        for(String tabName : webTags){
            content +=  getFirstBody(tabName);
        }


        content += "         </ul>\n" +
            "            </div>\n" +            
            "        </div>\n" +
            "    </nav>\n" +
            "</div>\n" +
            "<div class=\"contentTabulator\" id=\"About\" style=\"display:none;padding:2%;\">\n" +
            "    <div class=\"notice\"><h2>About</h2></a>\n" +
            "        <p>Somos el equipo 12:</p>\n" +
            "        <br>\n" +
            "        <p>Formado por: Kevin Romero, Víctor Jordán y Sergi Linares</p>\n" +
            "    </div>\n" +
            "</div>\n";
        
        // Extract HastagContent
        content += htmlExtractor.extractHashtags();
        // Extract Mentioned Users
        content += htmlExtractor.extractUsersMentioned();
        // Extract Mentioned Users
        content += htmlExtractor.extractDate();
        
        // Heat and Circle Map Extraction
        getMapsCode();


        
        return content;
    
    }


}
