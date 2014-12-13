package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.Device;
import edu.csupomona.nlp.opinsum.model.Sentence;
import edu.csupomona.nlp.opinsum.repository.SentenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Xing HU on 11/26/14.
 */
@Service("sentenceService")
public class SentenceServiceImpl implements SentenceService {

    @Autowired
    private SentenceRepository sentenceRepository;

    @Transactional
    public Sentence save(Sentence sentence) {
        return sentenceRepository.save(sentence);
    }

    @Transactional
    public List<Sentence> findAllSentencesByDevice(Device device) {
        return sentenceRepository.loadAllByDevice(device);
    }

    @Transactional
    public List<Sentence> findAllSentences() {
        return sentenceRepository.loadAll();
    }
}
