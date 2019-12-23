/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package web;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.creole.ResourceInstantiationException;
import gate.util.GateException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import maps.MapsUtils;



public class CreateMapToWorkOut {

       
	@SuppressWarnings("deprecation")
        public static String getMapsCode() {
            String finalCode ="";

		try {
                    Gate.init();
                    String encoding = "UTF-8";
                    File inDir=new File("./dataprocessed");
                    File[] flist=inDir.listFiles();
                    String floc;
               
                 
                    Document d;
                   
                    Annotation tweet;
                    AnnotationSet tweets;
                    AnnotationSet locations;
                    AnnotationSet lookups;
                    AnnotationSet orgs;
                    AnnotationSet URLs;
                    String app="???";
                    AnnotationSet persons;
                    FeatureMap fm;
                    
                    Map<Object,Object> mp;
                    Map<Object,Object> mp_usr;
                    ArrayList<Double> coordinates;
                    Double lati, longi;
                    String usrName;
                   
                    String id;
                    String creation;
                    String text;
                    
                    String newTextHeat = "";
                    String newTextCircle = "";
                    String color;
                    color = "#FF00FF";
                    String posColor="";
                    String negColor="";
                    String neuColor="";
                    String textToWriteHeat = "";
                    String textToWriteCircle = "";
                    String sentiLabel;
                    for(int f=0;f<flist.length;f++){
                        newTextHeat = "";
                        newTextCircle= "";
                        floc=flist[f].getAbsolutePath();
                        System.out.println(floc);
                        d=Factory.newDocument(new URL("file:///"+floc), encoding);
                        tweets=d.getAnnotations("Original markups").get("Tweet");
                        tweet=tweets.iterator().next();
                        fm=tweet.getFeatures();
                        mp=(Map<Object, Object>) fm.get("geo");
                        mp_usr=(Map<Object, Object>) fm.get("user");
                        id="";
                        creation="";
                        usrName="";
                       
             
                        // TO COMPLETE: extract app used to tweet!!!
                        
                        
                        text=d.getContent().toString();
                        if(mp!=null) {
                            coordinates=(ArrayList<Double>)mp.get("coordinates");
                            lati=coordinates.get(0);
                            longi=coordinates.get(1);
                           
                            sentiLabel=(String)d.getFeatures().get("sentiment");
                            
                            if(sentiLabel.equals("positive")) {
                                color=posColor;
                            } else if(sentiLabel.equals("negative")) {
                                 color=negColor;
                            } else {
                                 color=neuColor;
                            }
                            
                            //---- HEAT MAP ----
                            newTextHeat = "new google.maps.LatLng("+lati+","+longi+"),";
                            
                            //---- CRCL MAP ----
                           
                            String newtext = "";
                            text=text.replace("\n", " ").replace("'", " ");
                            for(String token : text.split(" ")){
                                    if(token.contains("http")) {
                                            newtext += "<a href=\""+token+"\" target=\"_blank\"> link </a>";
                                    }
                                    else newtext += token + " ";
                            }
                            // TO COMPLETE  change color according to sentiment
                            // IF CODE HERE 
                                   
                        
                            newTextCircle = "  id"+ id +": {center: {lat: "+ lati +", lng: "+longi+"},"+
                                    
							"color: '" +color+"',"+
							"user: '"  +usrName+" "+id+" ·+',"+
                                   
                                    "application: '"+app+"',"+
							"time: '"  +creation+"',"+
							"text: '(SENT: "+sentiLabel+") "  +newtext+"',"+
                                                    
							"},";
                                                
                        }
                        textToWriteHeat=textToWriteHeat +  newTextHeat + "\n";
                        textToWriteCircle=textToWriteCircle +  newTextCircle + "\n";
                    }
                    
                    String fs = System.getProperty("file.separator");
                    
                   
                    
                    String inputFileHeat = "maps"+fs+ "heat-map.html";
                    String inputFileCircle = "maps"+fs+ "circle-map.html";
                    
   
                } catch (ResourceInstantiationException ex) {
                    ex.printStackTrace();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (GateException ex) {
                 ex.printStackTrace();
            }
            
            return finalCode;
	}
        
}