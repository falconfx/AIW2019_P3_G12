package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author u124320
 */
public class ExtractSentiment 
{
    public static void main( String[] args )
    {
    	ExtractCat();
    	ExtractEn();
    	ExtractEs();
    }
    
    
    public static void ExtractCat(){
    	List<String> positiveCa = new ArrayList<String>();
    	List<String> negativeCa = new ArrayList<String>();
    	List<String> neutralCa = new ArrayList<String>();
    	File file = new File("./resources/dictionaries/senticon.ca.xml");

    	try {
    		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		  Document doc = dBuilder.parse(file);
    		  
    		  NodeList nList = doc.getElementsByTagName("lemma");
    		  
    		  for(int temp = 0; temp < nList.getLength(); temp++) {
    			  Node nNode = nList.item(temp);

    			  if(nNode.getNodeType() == Node.ELEMENT_NODE) {
    			    Element eElement = (Element) nNode;
    			    String fififi = eElement.getAttribute("pol");
    			    Float result = Float.parseFloat(fififi);	
    			    
    			    if(result >= 0.5){
    			    	positiveCa.add(eElement.getTextContent().replace(" ", ""));
    			    }else if(result <= -0.5){
    			    	negativeCa.add(eElement.getTextContent().replace(" ", ""));
    			    }else{
    			    	neutralCa.add(eElement.getTextContent().replace(" ", ""));
    			    }
    			  }
    		  }
    		  
    		  System.out.println(positiveCa.size());
    		  System.out.println(negativeCa.size());
    		  System.out.println(neutralCa.size());
    		  
    		  String ruta = "./dictionaries/pos_cat.lst";
    	        File archivo = new File(ruta);
    	        BufferedWriter bw;
    	        if(archivo.exists()) {
    	            bw = new BufferedWriter(new FileWriter(archivo));
    	            for(String f : positiveCa){
    	            	bw.write(f + "\n");
    	            }
    	            
    	        } else {
    	            bw = new BufferedWriter(new FileWriter(archivo));
    	            for(String f : positiveCa){
    	            	bw.write(f + "\n");
    	            }
    	        }
    	        bw.close();
    	        
    	        String ruta2 = "./dictionaries/neg_cat.lst";
    	        File archivo2 = new File(ruta2);
    	        BufferedWriter bw2;
    	        if(archivo2.exists()) {
    	            bw2 = new BufferedWriter(new FileWriter(archivo2));
    	            for(String f : negativeCa){
    	            	bw2.write(f + "\n");
    	            }
    	            
    	        } else {
    	            bw2 = new BufferedWriter(new FileWriter(archivo2));
    	            for(String f : negativeCa){
    	            	bw2.write(f + "\n");
    	            }
    	        }
    	        bw2.close();
    	        
    	        String ruta3 = "./dictionaries/neu_cat.lst";
    	        File archivo3 = new File(ruta3);
    	        BufferedWriter bw3;
    	        if(archivo3.exists()) {
    	            bw3 = new BufferedWriter(new FileWriter(archivo3));
    	            for(String f : neutralCa){
    	            	bw3.write(f + "\n");
    	            }
    	            
    	        } else {
    	            bw3 = new BufferedWriter(new FileWriter(archivo3));
    	            for(String f : neutralCa){
    	            	bw3.write(f + "\n");
    	            }
    	        }
    	        bw3.close();
    		  
    		} catch(Exception e) {
    		  e.printStackTrace();
    		}
    }
    
    public static void ExtractEn(){
    	List<String> positiveEn = new ArrayList<String>();
    	List<String> negativeEn = new ArrayList<String>();
    	List<String> neutralEn = new ArrayList<String>();
    	File file = new File("./dictionaries/senticon.en.xml");

    	try {
    		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		  Document doc = dBuilder.parse(file);
    		  
    		  NodeList nList = doc.getElementsByTagName("lemma");
    		  
    		  for(int temp = 0; temp < nList.getLength(); temp++) {
    			  Node nNode = nList.item(temp);

    			  if(nNode.getNodeType() == Node.ELEMENT_NODE) {
    			    Element eElement = (Element) nNode;
    			    String fififi = eElement.getAttribute("pol");
    			    Float result = Float.parseFloat(fififi);	
    			    
    			    if(result >= 0.5){
    			    	positiveEn.add(eElement.getTextContent().replace(" ", ""));
    			    }else if(result <= -0.5){
    			    	negativeEn.add(eElement.getTextContent().replace(" ", ""));
    			    }else{
    			    	neutralEn.add(eElement.getTextContent().replace(" ", ""));
    			    }
    			  }
    		  }
    		  
    		  System.out.println(positiveEn.size());
    		  System.out.println(negativeEn.size());
    		  System.out.println(neutralEn.size());
    		  
    		  String ruta = "./dictionaries/pos_en.lst";
    	        File archivo = new File(ruta);
    	        BufferedWriter bw;
    	        if(archivo.exists()) {
    	            bw = new BufferedWriter(new FileWriter(archivo));
    	            for(String f : positiveEn){
    	            	bw.write(f + "\n");
    	            }
    	            
    	        } else {
    	            bw = new BufferedWriter(new FileWriter(archivo));
    	            for(String f : positiveEn){
    	            	bw.write(f + "\n");
    	            }
    	        }
    	        bw.close();
    	        
    	        String ruta2 = "./dictionaries/neg_en.lst";
    	        File archivo2 = new File(ruta2);
    	        BufferedWriter bw2;
    	        if(archivo2.exists()) {
    	            bw2 = new BufferedWriter(new FileWriter(archivo2));
    	            for(String f : negativeEn){
    	            	bw2.write(f + "\n");
    	            }
    	            
    	        } else {
    	            bw2 = new BufferedWriter(new FileWriter(archivo2));
    	            for(String f : negativeEn){
    	            	bw2.write(f + "\n");
    	            }
    	        }
    	        bw2.close();
    	        
    	        String ruta3 = "./dictionaries/neu_en.lst";
    	        File archivo3 = new File(ruta3);
    	        BufferedWriter bw3;
    	        if(archivo3.exists()) {
    	            bw3 = new BufferedWriter(new FileWriter(archivo3));
    	            for(String f : neutralEn){
    	            	bw3.write(f + "\n");
    	            }
    	            
    	        } else {
    	            bw3 = new BufferedWriter(new FileWriter(archivo3));
    	            for(String f : neutralEn){
    	            	bw3.write(f + "\n");
    	            }
    	        }
    	        bw3.close();
    		  
    		} catch(Exception e) {
    		  e.printStackTrace();
    		}
    }
    
    public static void ExtractEs(){
    	List<String> positiveEs = new ArrayList<String>();
    	List<String> negativeEs = new ArrayList<String>();
    	List<String> neutralEs = new ArrayList<String>();
    	File file = new File("./dictionaries/senticon.es.xml");

    	try {
    		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		  Document doc = dBuilder.parse(file);
    		  
    		  NodeList nList = doc.getElementsByTagName("lemma");
    		  
    		  for(int temp = 0; temp < nList.getLength(); temp++) {
    			  Node nNode = nList.item(temp);

    			  if(nNode.getNodeType() == Node.ELEMENT_NODE) {
    			    Element eElement = (Element) nNode;
    			    String fififi = eElement.getAttribute("pol");
    			    Float result = Float.parseFloat(fififi);	
    			    
    			    if(result >= 0.5){
    			    	positiveEs.add(eElement.getTextContent().replace(" ", ""));
    			    }else if(result <= -0.5){
    			    	negativeEs.add(eElement.getTextContent().replace(" ", ""));
    			    }else{
    			    	neutralEs.add(eElement.getTextContent().replace(" ", ""));
    			    }
    			  }
    		  }
    		  
    		  System.out.println(positiveEs.size());
    		  System.out.println(negativeEs.size());
    		  System.out.println(neutralEs.size());
    		  
    		  String ruta = "./dictionaries/pos_es.lst";
    	        File archivo = new File(ruta);
    	        BufferedWriter bw;
    	        if(archivo.exists()) {
    	            bw = new BufferedWriter(new FileWriter(archivo));
    	            for(String f : positiveEs){
    	            	bw.write(f + "\n");
    	            }
    	            
    	        } else {
    	            bw = new BufferedWriter(new FileWriter(archivo));
    	            for(String f : positiveEs){
    	            	bw.write(f + "\n");
    	            }
    	        }
    	        bw.close();
    	        
    	        String ruta2 = "./dictionaries/neg_es.lst";
    	        File archivo2 = new File(ruta2);
    	        BufferedWriter bw2;
    	        if(archivo2.exists()) {
    	            bw2 = new BufferedWriter(new FileWriter(archivo2));
    	            for(String f : negativeEs){
    	            	bw2.write(f + "\n");
    	            }
    	            
    	        } else {
    	            bw2 = new BufferedWriter(new FileWriter(archivo2));
    	            for(String f : negativeEs){
    	            	bw2.write(f + "\n");
    	            }
    	        }
    	        bw2.close();
    	        
    	        String ruta3 = "./dictionaries/neu_es.lst";
    	        File archivo3 = new File(ruta3);
    	        BufferedWriter bw3;
    	        if(archivo3.exists()) {
    	            bw3 = new BufferedWriter(new FileWriter(archivo3));
    	            for(String f : neutralEs){
    	            	bw3.write(f + "\n");
    	            }
    	            
    	        } else {
    	            bw3 = new BufferedWriter(new FileWriter(archivo3));
    	            for(String f : neutralEs){
    	            	bw3.write(f + "\n");
    	            }
    	        }
    	        bw3.close();
    		  
    		} catch(Exception e) {
    		  e.printStackTrace();
    		}
    }
}
