package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.Sentence;
import edu.csupomona.nlp.opinsum.model.SentimentCount;
import edu.csupomona.nlp.opinsum.model.SentimentNGram;
import edu.csupomona.nlp.opinsum.repository.SentenceRepository;
import edu.csupomona.nlp.opinsum.repository.SentimentCountRepository;
import edu.csupomona.nlp.opinsum.repository.SentimentNGramRepository;
import edu.csupomona.nlp.sentiment.SentimentAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Xing HU on 11/30/14.
 */
@Service("sentimentService")
public class SentimentServiceImpl implements SentimentService {

    @Autowired
    SentimentNGramRepository sentimentNGramRepository;

    @Autowired
    SentimentCountRepository sentimentCountRepository;

    @Autowired
    SentenceRepository sentenceRepository;

    @Transactional
    public void train() {
        SentimentAnalyzer sa = new SentimentAnalyzer();
        sa.setNeedRetrain(false);
        sa.setNeedReselectFeature(false);
        sa.setBasePath("/Users/xing/Downloads/FitnessTracker-master/data/sentiment/");

        try {
            // train the classifier
            sa.train();

            // feature selection
            sa.selectFeatures();

            // get features
            HashMap<String, List<Integer>> features = sa.getFeatures();

            // get total counts of features
            List<Long> counts = sa.getTotals();

            // save features to database
            for (Map.Entry<String, List<Integer>> entry : features.entrySet()) {
                SentimentNGram sentimentNGram = new SentimentNGram();
                sentimentNGram.setNgram(entry.getKey());
                sentimentNGram.setPosCount(entry.getValue().get(0));
                sentimentNGram.setNegCount(entry.getValue().get(1));

                sentimentNGramRepository.saveOrUpdate(sentimentNGram);
            }

            // save total counts to database
            SentimentCount posCount = new SentimentCount();
            posCount.setSentiment("positive");
            posCount.setCount(counts.get(0));
            sentimentCountRepository.saveOrUpdate(posCount);
            SentimentCount negCount = new SentimentCount();
            negCount.setSentiment("negative");
            negCount.setCount(counts.get(1));
            sentimentCountRepository.saveOrUpdate(negCount);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void batchClassify(List<Sentence> sentences) {
        SentimentAnalyzer sa = new SentimentAnalyzer();

        // load sentiment ngrams from database
        List<SentimentNGram> ngrams = sentimentNGramRepository.loadAll();

        // load sentiment counts from database
        List<SentimentCount> counts = sentimentCountRepository.loadAll();

        // create HashMap for pos and neg ngrams, and feature set
        HashMap<String, Integer> pos = new HashMap<>();
        HashMap<String, Integer> neg = new HashMap<>();
        HashSet<String> features = new HashSet<>();
        for (SentimentNGram ngram : ngrams) {
            pos.put(ngram.getNgram(), ngram.getPosCount());
            neg.put(ngram.getNgram(), ngram.getNegCount());

            features.add(ngram.getNgram());
        }

        // create total counts for pos and neg
        Long totalPos = counts.get(0).getCount();
        Long totalNeg = counts.get(1).getCount();

        // train classifier
        sa.simpleTrain(pos, neg, totalPos, totalNeg, features);

        // classify
        for (Sentence sentence : sentences) {
            String procSent = sentence.getProcSent();
            String sentiment = sa.classify(procSent) ? "positive" : "negative";

            // update sentence
            sentence.setSentiment(sentiment);

            // update database entry
            sentenceRepository.update(sentence);
        }
    }
}
