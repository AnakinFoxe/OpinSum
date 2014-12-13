package edu.csupomona.nlp.opinsum.repository;

import edu.csupomona.nlp.opinsum.model.Device;
import edu.csupomona.nlp.opinsum.model.Sentence;

import java.util.List;

/**
 * Created by Xing HU on 11/26/14.
 */
public interface SentenceRepository {

    public Sentence save(Sentence sentence);

    public Sentence update(Sentence sentence);

    public List<Sentence> loadAllByDevice(Device device);

    public List<Sentence> loadAll();
}
