package edu.csupomona.nlp.opinsum.utils;

import edu.csupomona.nlp.opinsum.model.AspectNGram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xing HU on 11/30/14.
 */
public class Map2AspectNGram {

    public static AspectNGram convert(String ngram, List<Integer> counts) {
        AspectNGram aspectNGram = new AspectNGram();

        aspectNGram.setNgram(ngram);

        aspectNGram.setAspect1(counts.size() > 0 ? counts.get(0) : 0);
        aspectNGram.setAspect2(counts.size() > 1 ? counts.get(1) : 0);
        aspectNGram.setAspect3(counts.size() > 2 ? counts.get(2) : 0);
        aspectNGram.setAspect4(counts.size() > 3 ? counts.get(3) : 0);
        aspectNGram.setAspect5(counts.size() > 4 ? counts.get(4) : 0);
        aspectNGram.setAspect6(counts.size() > 5 ? counts.get(5) : 0);
        aspectNGram.setAspect7(counts.size() > 6 ? counts.get(6) : 0);
        aspectNGram.setAspect8(counts.size() > 7 ? counts.get(7) : 0);
        aspectNGram.setAspect9(counts.size() > 8 ? counts.get(8) : 0);

        aspectNGram.setOther(counts.get(counts.size()-1));

        return aspectNGram;
    }

    public static List<Integer> convertBack(AspectNGram ngram, Integer numOfAspect) {
        List<Integer> aspectCounts = new ArrayList<>();

        if (numOfAspect > 0) {
            aspectCounts.add(ngram.getAspect1());
        }
        if (numOfAspect > 1) {
            aspectCounts.add(ngram.getAspect2());
        }
        if (numOfAspect > 2) {
            aspectCounts.add(ngram.getAspect3());
        }
        if (numOfAspect > 3) {
            aspectCounts.add(ngram.getAspect4());
        }
        if (numOfAspect > 4) {
            aspectCounts.add(ngram.getAspect5());
        }
        if (numOfAspect > 5) {
            aspectCounts.add(ngram.getAspect6());
        }
        if (numOfAspect > 6) {
            aspectCounts.add(ngram.getAspect7());
        }
        if (numOfAspect > 7) {
            aspectCounts.add(ngram.getAspect8());
        }
        if (numOfAspect > 8) {
            aspectCounts.add(ngram.getAspect9());
        }

        return aspectCounts;
    }
}
