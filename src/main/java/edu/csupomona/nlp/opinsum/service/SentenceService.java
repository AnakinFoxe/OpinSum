package edu.csupomona.nlp.opinsum.service;

import edu.csupomona.nlp.opinsum.model.Device;
import edu.csupomona.nlp.opinsum.model.Sentence;

import java.util.List;

/**
 * Created by Xing HU on 11/26/14.
 */
public interface SentenceService {

    /**
     * Save sentence information
     * @param sentence
     * @return
     */
    public Sentence save(Sentence sentence);

    /**
     * Grab sentences that belong to the device
     * @param device
     * @return
     */
    public List<Sentence> findAllSentencesByDevice(Device device);

    /**
     * Grab all the sentences
     * @return
     */
    public List<Sentence> findAllSentences();
}
