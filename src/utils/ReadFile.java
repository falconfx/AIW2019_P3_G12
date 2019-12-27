/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author horacio
 */
public class ReadFile {
    
    public void main(String compressedPath, String uncompressedPath ) throws FileNotFoundException {        
            ArrayList<String> topics = new ArrayList<String> ();
            //topics.add("politics");
            //topics.add("champions");
            //topics.add("games");
            //topics.add("championsGamesPoliticsAnyFilter");
            topics.add("championsGamesPolitics");

            
            for(String topicElement : topics){
                extractJsonInformation(compressedPath, uncompressedPath, topicElement);
            }        
    }
    
    
    
    public void extractJsonInformation(String compressedPath, String uncompressedPath, String topic) throws FileNotFoundException{
        String line;
        PrintWriter pw;
        char[] animationChars = new char[]{'|', '/', '-', '\\'};

        BufferedReader reader =new BufferedReader(new FileReader(compressedPath+"/"+topic+".json"));
        
        try {
            int f=0;
            while((line=reader.readLine()) != null) {
                pw=new PrintWriter(new File(uncompressedPath+"/tweet_"+f+".json"));
                pw.print(line);
                pw.flush();
                pw.close();
                f++;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
