package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.Sentence;
import edu.csupomona.nlp.opinsum.repository.SentenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suk.code.SubjectiveLogic.MDS.SubSumGenericMDS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Xing HU on 12/1/14.
 */
@Service("rankService")
public class RankServiceImpl implements RankService {

    @Autowired
    SentenceRepository sentenceRepository;

    @Transactional
    public void rank(List<Sentence> sentences) {
        // create pure sentence list
        HashMap<String, List<String>> sents = new HashMap<>();
        for (Sentence sentence : sentences) {
            String aspect = sentence.getAspect();
            if (!aspect.equals("others")) {
                List<String> sentList;
                if (sents.containsKey(aspect))
                    sentList = sents.get(aspect);
                else
                    sentList = new ArrayList<>();
                sentList.add(sentence.getProcSent());
                sents.put(aspect, sentList);
            }
        }

        // use SubSum to generate ranked list
        for (String aspect : sents.keySet()) {
            List<String> sentList = sents.get(aspect);

            SubSumGenericMDS ssg = new SubSumGenericMDS(sentList, 100);
            ssg.assignScoreToSentences();
            List<String> rankedSents = ssg.getCandidateSentences();

            // update rank information
            for (int idx = 0; idx < rankedSents.size(); ++idx) {
                int rank = idx + 1;

                String rankedSent = rankedSents.get(idx).trim();
                // search for match
                for (Sentence sentence : sentences)
                    if (sentence.getProcSent().contains(rankedSent)) {
                        sentence.setRank(rank);

                        sentenceRepository.update(sentence);
                    }
            }
        }
    }
}
