package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.AspectNGram;
import edu.csupomona.nlp.opinsum.model.AspectSentenceCount;
import edu.csupomona.nlp.opinsum.model.Sentence;
import edu.csupomona.nlp.opinsum.repository.AspectNGramRepository;
import edu.csupomona.nlp.opinsum.repository.AspectSCRepository;
import edu.csupomona.nlp.opinsum.repository.SentenceRepository;
import edu.csupomona.nlp.opinsum.utils.Map2AspectNGram;
import edu.csupomona.nlp.aspectdetector.AspectDetector;
import edu.csupomona.nlp.ml.supervised.NaiveBayes;
import edu.csupomona.nlp.ml.supervised.NaiveBayesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xing HU on 11/30/14.
 */
@Service("aspectService")
public class AspectServiceImpl implements AspectService {

    @Autowired
    AspectSCRepository aspectSCRepository;

    @Autowired
    AspectNGramRepository aspectNGramRepository;

    @Autowired
    SentenceRepository sentenceRepository;

    @Transactional
    public void initTraining() {
        // remove all the entries in AspectSentenceCount
        int deletedNum = aspectSCRepository.deleteAll();
        System.out.println(deletedNum + " entries deleted from AspectSentenceCount");

        deletedNum = aspectNGramRepository.deleteAll();
        System.out.println(deletedNum + " entries deleted from AspectNGram");
    }

    @Transactional
    public void train(List<String> aspects, List<List<String>> aspectWords) {
        AspectDetector ad = new AspectDetector();

        HashMap<String, List<Integer>> freqMap = new HashMap<>();

        try {
            // compute frequency map and aspect sentences count
            // i.e. train the aspect classifier
            Long[] aspectSentences = ad.parse(99, 1, aspects, aspectWords,
                    "/Users/xing/Projects/Opinion_Mining/Data/amazon/smart_phones/pre-processed/", freqMap);

            // save aspect sentences count to database
            for (int idx = 0; idx < aspects.size(); ++idx) {
                AspectSentenceCount asc = new AspectSentenceCount();
                asc.setAspect(aspects.get(idx));
                asc.setCount(aspectSentences[idx]);

                aspectSCRepository.saveOrUpdate(asc);
            }

            // save "other" aspect
            AspectSentenceCount asc = new AspectSentenceCount();
            asc.setAspect("other");
            asc.setCount(aspectSentences[aspectSentences.length - 1]);
            aspectSCRepository.saveOrUpdate(asc);

            // save ngram information to database
            for (Map.Entry<String, List<Integer>> entry : freqMap.entrySet()) {
                AspectNGram aspectNGram = Map2AspectNGram.convert(entry.getKey(), entry.getValue());

                aspectNGramRepository.saveOrUpdate(aspectNGram);
            }

            System.out.println("Finally done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void batchClassify(List<Sentence> sentences) {
        NaiveBayes nb = new NaiveBayes();

        // load aspect sentence count from database
        List<AspectSentenceCount> ascs = aspectSCRepository.loadAll();

        // load aspect ngram information from database
        List<AspectNGram> ngrams = aspectNGramRepository.loadAll();

        // create list of aspects and aspect sentences count array
        List<String> aspects = new ArrayList<>();
        Long[] aspectSentences = new Long[ascs.size()];
        int idx = 0;
        for (AspectSentenceCount asc : ascs) {
            aspects.add(asc.getAspect());
            aspectSentences[idx] = asc.getCount();
            ++idx;
        }

        // create frequency map
        HashMap<String, List<Integer>> freqMap = new HashMap<>();
        for (AspectNGram ngram : ngrams) {
            List<Integer> freq = Map2AspectNGram.convertBack(ngram, aspects.size());
            freqMap.put(ngram.getNgram(), freq);
        }

        // remove the last "aspect" ("other") from aspect list
        aspects.remove(aspects.size() - 1);

        // train the Naive Bayes classifier
        nb.train(aspects, freqMap, 3, 1, aspectSentences);

        // classify
        for (Sentence sentence : sentences) {
            String procSent = sentence.getProcSent();
            NaiveBayesResult nbRet = nb.classify(procSent);
            String aspect = nbRet.getLabel();

            // update sentence
            sentence.setAspect(aspect);

            // update database entry
            sentenceRepository.update(sentence);
        }
    }
}
