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
import utils.MapsUtils;



public class CreateMapToWorkOut {
    
    private static String heatPage;
    private static String circlePage;
    

    public static String getHeatPage() {
        return heatPage;
    }

    public static String getCirclePage() {
        return circlePage;
    }


       
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
                String app="AIW-G12-TwitterAnalysis";
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
                String posColor="#43DA13";
                String negColor="#FF0F0C";
                String neuColor="#DFBB27";
                String textToWriteHeat = "";
                String textToWriteCircle = "";
                String sentiLabel;
                for(int f=0;f<flist.length;f++){
                    newTextHeat = "";
                    newTextCircle= "";
                    floc=flist[f].getAbsolutePath();
                    //System.out.println(floc);
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

                        id = (String) fm.get("id_str");
                        creation=(String) fm.get("created_at");
                        usrName=(String) mp_usr.get("screen_name");

                        // TO COMPLETE  change color according to sentiment
                        // IF CODE HERE 
                        if(sentiLabel.equals("positive")) {
                            color=posColor;
                        } else if(sentiLabel.equals("negative")) {
                             color=negColor;
                        } else {
                             color=neuColor;
                        }


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



                String inputFileHeat = "web/maps"+fs+ "heat-map.html";
                String inputFileCircle = "web/maps"+fs+ "circle-map.html";

                MapsUtils.createNewMap(inputFileHeat, textToWriteHeat);
                MapsUtils.createNewMap(inputFileCircle, textToWriteCircle);

                 

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