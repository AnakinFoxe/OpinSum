/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.csupomona.nlp.opinsum;

import com.microgen.application.ApplicationMain;
import edu.csupomona.nlp.aspectdetector.AspectDetector;
import edu.csupomona.nlp.util.StanfordTools;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import suk.code.SubjectiveLogic.MDS.SubSumGenericMDS;

/**
 *
 * @author Xing
 */
public class OpinionSummarizer {
    
    private final AspectDetector ad;
    
    private final StanfordTools stanford;
    
    private HashMap<String, HashMap<String, List<String>>> result;

    public OpinionSummarizer() {
        ad = new AspectDetector();
        
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, parse, sentiment");
        stanford = new StanfordTools(props);
    }
    
    public HashMap<String, List<String>> detectAspect() throws IOException {
        ad.collectNGram(1);
        
        return ad.classifyNB();
    }
    
    
    private String sentiment(String sentence) {
        String sentiment;
        switch (stanford.sentiment(sentence)) {
            case 0:
            case 1:
                sentiment = "negative";
                break;
            case 2:
                sentiment = "neutral";
                break;
            case 3:
            case 4:
                sentiment = "positive";
                break;
            default:
                sentiment = "Cant Tell";
                break;
        }
        
        return sentiment;
    }
    
    public HashMap<String, HashMap<String, List<String>>> detectSentiment(
        HashMap<String, List<String>> mapAspectSents) {
        // aspect <-> sentiment <-> sentences mapping
        HashMap<String, HashMap<String, List<String>>> mapAspectSentiment =
                new HashMap<>();
        
        // for each aspect, determine sentiment of each sentence
        for (String aspect : mapAspectSents.keySet()) {
            HashMap<String, List<String>> mapSentiSent;
            
            // retrieve sentiment <-> sentences mapping
            if (mapAspectSentiment.containsKey(aspect))
                mapSentiSent = mapAspectSentiment.get(aspect);
            else
                mapSentiSent = new HashMap<>();
            
            // for each sentence, update sentiment <-> sentences mapping
            for (String sentence : mapAspectSents.get(aspect)) {
                List<String> sentences;
                
                // compute sentiment
                String sentiment = sentiment(sentence);
                
                // retrieve sentence list mapped to this sentiment
                if (mapSentiSent.containsKey(sentiment))
                    sentences = mapSentiSent.get(sentiment);
                else
                    sentences = new ArrayList<>();
                
                // add sentence to the list
                sentences.add(sentence);
                
                // update the sentiment <-> sentences mapping
                mapSentiSent.put(sentiment, sentences);
                
                // display progress
                System.out.println(aspect + "[" + sentiment + "]" + sentence);
            }
            
            // update aspect <-> sentiment <-> sentences mapping
            mapAspectSentiment.put(aspect, mapSentiSent);    
        }
        
        return mapAspectSentiment;
    }
    
    
    public void writeAspectSentiment(
            HashMap<String, HashMap<String, List<String>>> mapAspectSentiment,
            String outPath) 
            throws IOException {
        for (String aspect : mapAspectSentiment.keySet()) {
            for (String sentiment : mapAspectSentiment.get(aspect).keySet()) {
                String filename = outPath + aspect + "[" + sentiment + "].txt";
                FileWriter fw = new FileWriter(filename);
                try (BufferedWriter bw = new BufferedWriter(fw)) {
                    for (String sentence : mapAspectSentiment
                            .get(aspect).get(sentiment)) {
                        bw.write(sentence + "\n");
                    }
                }
            }
        }
    }
    
    private List<String> readText(File file) 
            throws IOException {
        List<String> text = new ArrayList<>();
        
        FileReader fr = new FileReader(file);
        try (BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) 
                text.add(line);
        }
        
        return text;
    }
    
    private void useSubSum(String inPath, String outPath) 
            throws IOException {
        File[] files = new File(inPath).listFiles();
        
        for (File file : files) {
            List<String> text = readText(file);
            // generate summaries
            SubSumGenericMDS ssg = new SubSumGenericMDS(text, 10);
            ssg.assignScoreToSentences();
            List<String> summaries = ssg.getCandidateSentences();
                
            // write summaries to files
            String filename = outPath + file.getName();
            FileWriter fw = new FileWriter(filename);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                for (String summary : summaries)
                    bw.write(summary);
            }
                
            
        }
    }
    
    private void useMG(String path) {
        ApplicationMain am = new ApplicationMain();
        
        String[] args = {"-b", path};
        am.start(args);
    }
    
    public void summarize(String inPath, String outPath) 
            throws IOException {
        // SubSum
//        useSubSum(inPath, outPath + "SubSum/");
        
        // Micropinion Generation
        useMG("data/summaries");
        
        
    }
    
    public static void main(String[] args) throws IOException {
        OpinionSummarizer os = new OpinionSummarizer();
        
        // obtain the aspect <-> sentences mapping
//        HashMap<String, List<String>> mapAspectSents = os.detectAspect();
//        
//        // obtain aspect <-> sentiment <-> sentence mapping
//        HashMap<String, HashMap<String, List<String>>> mapAspectSentiment
//                = os.detectSentiment(mapAspectSents);
//        
//        // write result as input files for summarization
//        os.writeAspectSentiment(mapAspectSentiment,
//                "./data/summaries/input/");        
        
        // SubSum
        os.summarize("./data/summaries/input/", "./data/summaries/output/");
                
    }
    
}
