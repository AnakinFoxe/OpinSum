/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.csupomona.nlp;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Xing
 */
public class Sentence2Clause {
    
    
    public static void main(String[] args) {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, parse");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable
        String text = "I have this phone linked to my business Exchange account, and use this phone for work regularly."; // Add your text here!

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(CollapsedDependenciesAnnotation.class);

            System.out.println(dependencies.toString("plain"));
//            for (SemanticGraphEdge edge : dependencies.getEdgeSet()) {
//                System.out.println(edge.toString());
//            }
        }

    }
}
