package aiw2019_p3_g12;

import java.util.Scanner;
import utils.ReadFile;
import java.io.FileNotFoundException;
import java.util.Properties;
import dataprocessing.CallGATEConditionalCorpusPipeline;
import gate.creole.ResourceInstantiationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import twitteranalysis.TweetTreatment;
import utils.ExtractSentiment;
import web.SimpleHTMLConstructor;

/**
 *
 * @author u124320
 */
public class AIW2019_P3_G12 {

    final static String JSONCOMPRESSEDPATH = "./jsonscompressed";
    final static String JSONUNCOMPRESSEDPATH = "./jsonsuncompressed";
    final static String JSONTOXMLPATH = "./dataprocessed";


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException, ResourceInstantiationException, IOException, ParseException {
        int userChoice;
        userChoice = menu();
        //System.out.print(userChoice);
    }
    
    
    public static int menu() throws FileNotFoundException, IOException, ParseException {

        int selection = 0;
        Scanner input = new Scanner(System.in);
        
        while(selection != 5){
            /***************************************************/

            System.out.println("Choose from these choices");
            System.out.println("-------------------------\n");
            System.out.println("0 - Generate sentiment lists of language");
            System.out.println("1 - Extract json lines");
            System.out.println("    (Remember put your zipped JSON inside jsonzipped/ folder)");
            System.out.println("2 - Put your own GAPP and process data (step 1 necessary)");
            System.out.println("3 - Process and treatment of tweets");
            System.out.println("4 - Generate a web page");
            System.out.println("5 - Quit\n\n");

            selection = input.nextInt();
            try {
                userAction(selection);
            } catch (XMLStreamException ex) {
                Logger.getLogger(AIW2019_P3_G12.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ResourceInstantiationException ex) {
                Logger.getLogger(AIW2019_P3_G12.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return selection;    
    }
    
    public static void userAction(int userChoice) throws FileNotFoundException, XMLStreamException, ResourceInstantiationException, IOException, ParseException{
        String gappPath = "";
        String twitterPluginPath = "";
        System.setProperty("log4j.debug", "");

        switch(userChoice){
            case 0:
                ExtractSentiment sentimentExtraction = new ExtractSentiment();
                sentimentExtraction.main();
                System.out.println("All Ok -> /Search files into resources/sample_gazetteer folder");
                break;
            case 1: 
                ReadFile rf = new ReadFile();
                rf.main(JSONCOMPRESSEDPATH, JSONUNCOMPRESSEDPATH);
                System.out.println("All Ok");
                break;
            case 2:
                try{
                    Properties props = System.getProperties();
                    Scanner keyboardGate = new Scanner(System.in);
                    System.out.println("Please enter your Gate-Home (variable) path: ");
                    //String gateHomePath = keyboardGate.nextLine();
                    String gateHomePath = "C:\\Program Files\\GATE_Developer_8.0";
                    props.setProperty("gate.home", gateHomePath);
                    
                    
                    Scanner keyboardGapp = new Scanner(System.in);
                    System.out.println("Please enter your gapp path: ");
                    //gappPath = keyboardGapp.nextLine();
                    gappPath = "\\\\dcloud.local\\public\\023\\u124320\\Documents\\NetBeansProjects\\AIW2019_P3_G12\\gapp\\MyTwitter.gapp";
                
                
                    Scanner keyboardTwitterPlugin = new Scanner(System.in);
                    System.out.println("Please enter your twitter plugin path: ");
                    //twitterPluginPath = keyboardTwitterPlugin.nextLine();
                    twitterPluginPath = "C:\\Program Files\\GATE_Developer_8.0\\plugins\\Twitter";
                               
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    break;
                }
                
                CallGATEConditionalCorpusPipeline corpusPipeline = new CallGATEConditionalCorpusPipeline();
                corpusPipeline.main(gappPath, twitterPluginPath);
   
                System.out.println("All Ok");
                break;
            case 3:
                TweetTreatment tTreatment = new TweetTreatment();
                tTreatment.setInformation();
                tTreatment.analyzeInformation(JSONTOXMLPATH);
                System.out.println("All Ok");
                break;
            case 4:
                SimpleHTMLConstructor sHC = new SimpleHTMLConstructor();
                sHC.makeWeb();
                System.out.println("All Ok");
                break;
            case 5:
                System.exit(0);
                break;

                
        }
    
    }
    
   
}
