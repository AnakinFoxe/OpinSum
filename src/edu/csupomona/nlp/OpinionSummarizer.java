/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.csupomona.nlp;

import edu.csupomona.nlp.test.AspectDetector;
import edu.csupomona.nlp.util.StanfordTools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

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
        
        return ad.testNB();
    }
    
    public String sentiment(String sentence) {
        String sentiment;
        switch (stanford.sentiment(sentence)) {
            case 0:
            case 1:
                sentiment = "Negative";
                break;
            case 2:
                sentiment = "Neutral";
                break;
            case 3:
            case 4:
                sentiment = "Positive";
                break;
            default:
                sentiment = "Cant Tell";
                break;
        }
        
        return sentiment;
    }
    
    public void summarize() {
        
    }
    
    public static void main(String[] args) throws IOException {
        OpinionSummarizer os = new OpinionSummarizer();
        
        // obtain the aspect <-> sentences mapping
        HashMap<String, List<String>> mapAspectSents = os.detectAspect();
        
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
                String sentiment = os.sentiment(sentence);
                
                // retrieve sentence list mapped to this sentiment
                if (mapSentiSent.containsKey(sentiment))
                    sentences = mapSentiSent.get(sentiment);
                else
                    sentences = new ArrayList<>();
                
                // add sentence to the list
                sentences.add(sentence);
                
                // update the sentiment <-> sentences mapping
                mapSentiSent.put(sentiment, sentences);
            }
            
            // update aspect <-> sentiment <-> sentences mapping
            mapAspectSentiment.put(aspect, mapSentiSent);    
        }
        
        
        // display
        for (String aspect : mapAspectSentiment.keySet()) {
            for (String sentiment : mapAspectSentiment.get(aspect).keySet()) {
                for (String sentence : mapAspectSentiment.get(aspect).get(sentiment)) {
                    System.out.println(aspect + "[" + sentiment + "]::" + sentence);
                }
            }
        }
    }
    
}
