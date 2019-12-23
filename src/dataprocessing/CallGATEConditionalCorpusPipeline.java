package dataprocessing;

import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.Gate;
import gate.creole.ConditionalSerialAnalyserController;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.xml.stream.XMLStreamException;
import dataprocessing.Sentiment;


/**
 * Loading in JAVA a GATE gapp and executing it over a document
 * @author UPF and u124320
 */
public class CallGATEConditionalCorpusPipeline {
    
    public static String inDir = "jsonsuncompressed/";
    public static String outDir = "dataprocessed/";
    public ConditionalSerialAnalyserController application;
    
    
    public CallGATEConditionalCorpusPipeline(){ super(); }
    
    public void loadMyGapp(String pathToGapp) throws IOException, ResourceInstantiationException, PersistenceException {
        // load the GAPP 
        this.application =
            (ConditionalSerialAnalyserController)
                PersistenceManager.loadObjectFromFile(new File(pathToGapp));
    }
    
    public void setCorpus(Corpus c) {
        this.application.setCorpus(c); 
    }
    
    public void executeMyGapp() throws ExecutionException {
        this.application.execute();
    }
    
    public static void main(String gappPath, String twitterPluginPath) throws XMLStreamException {
      
        PrintWriter pw=null;
        Sentiment cSentiment = new Sentiment();
        try {
            // initialize GATE
            Gate.init();
            // HERE use your TWITTER plug-in
            Gate.getCreoleRegister().registerDirectories(new URL("file:///"+twitterPluginPath));
            // one instance of my class
            CallGATEConditionalCorpusPipeline myanalyser=new CallGATEConditionalCorpusPipeline();
            // load the application
            myanalyser.loadMyGapp(gappPath);
            // create a GATE corpus
            Corpus corpus=Factory.newCorpus("");
           
            Document document;
            String docName;
            File input=new File(inDir);
            File[] flist=input.listFiles();
            for(int f=0; f<flist.length ; f++) {
                docName=flist[f].getName().replace(".json","" );
                document=Factory.newDocument(new 
                URL("file:///"+flist[f].getAbsolutePath()
                ),"UTF-8");
                
                if(document.getAnnotationSetNames().contains("Original markups")) {
                    corpus.add(document);
                    // show annotations before call for English doc
                    myanalyser.setCorpus(corpus);
                    // execute app
                    myanalyser.executeMyGapp();
                    // Compute sentiment document
                    cSentiment.ComputeSentiment(document);
                    
                    
                    try {
                        pw=new PrintWriter(new FileWriter(outDir+File.separator+docName+".xml"));
                        pw.print(document.toXml());
                        pw.flush();
                        pw.close();

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    // do stuff with your document...

                    // release resources used for documents
                    Factory.deleteResource(document);
                }
            
            }            
        } catch(GateException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
            
    
}