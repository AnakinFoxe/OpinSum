/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.csupomona.nlp.opinsum;

import edu.csupomona.nlp.aspectdetector.AspectDetector;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.csupomona.nlp.sentiment.SentimentAnalyzer;
import suk.code.SubjectiveLogic.MDS.SubSumGenericMDS;

/**
 *
 * @author Xing
 */
public class OpinionSummarizer {
    
    private final AspectDetector ad_;
    private final SentimentAnalyzer sa_;

    public OpinionSummarizer() {
        ad_ = new AspectDetector();
        sa_ = new SentimentAnalyzer();

        sa_.setNeedRetrain(false);
        sa_.setNeedReselectFeature(false);
    }

    public List<String> buildSentences(String path) throws IOException {
        List<String> sentences = new ArrayList<>();

        // read files and get sentences
        File[] files = new File(path).listFiles();
        for (File file : files) {
            FileReader fr = new FileReader(file);
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    // a few possible preprocessing

                    // create Sentence object and add to list
                    sentences.add(line.trim());
                }
            }
        }

        return sentences;
    }

    public void prepare() throws IOException {
        // train the aspect detector
        ad_.collectNGram(1);

        // train the sentiment analyzer
        sa_.train();
        sa_.selectFeatures();

        System.out.println("Finished training");
    }
    
    public String detectAspect(String sentence) throws IOException {
        return ad_.detectAspect(sentence);
    }
    
    
    public String detectSentiment(String sentence) {
        if (sa_.classify(sentence))
            return "positive";
        else
            return "negative";
    }



    public static void main(String[] args) throws IOException {
        OpinionSummarizer os = new OpinionSummarizer();

        os.prepare();
        
        List<String> sentences = os.buildSentences("./data/testing/nokia-lumia521-tmobile/");

        HashMap<String, HashMap<String, List<String>>> classifiedSent = new HashMap<>();
        for (String sentence : sentences) {
            String aspect = os.detectAspect(sentence);
            String sentiment = os.detectSentiment(sentence);

            if (!aspect.equals("others")) {
                HashMap<String, List<String>> sentiSent;
                if (classifiedSent.containsKey(aspect)) {
                    sentiSent = classifiedSent.get(aspect);
                } else {
                    sentiSent = new HashMap<>();
                }

                List<String> sentList;
                if (sentiSent.containsKey(sentiment)) {
                    sentList = sentiSent.get(sentiment);
                } else {
                    sentList = new ArrayList<>();
                }

                sentList.add(sentence);
                sentiSent.put(sentiment, sentList);
                classifiedSent.put(aspect, sentiSent);
            }
        }

        int limit = 3;
        for (String aspect : classifiedSent.keySet()) {
            for (String sentiment : classifiedSent.get(aspect).keySet()) {
                System.out.println(aspect + ", " + sentiment + ":");
                List<String> sentList = classifiedSent.get(aspect).get(sentiment);

                SubSumGenericMDS ssg = new SubSumGenericMDS(sentList, 10);
                ssg.assignScoreToSentences();
                List<String> summaries = ssg.getCandidateSentences();

                for (int idx = 0; idx < limit; ++idx)
                    System.out.print(summaries.get(idx));
            }
        }



    }
    
}
