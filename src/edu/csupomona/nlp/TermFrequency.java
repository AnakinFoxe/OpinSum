/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.csupomona.nlp;

import edu.csupomona.nlp.util.NGram;
import edu.csupomona.nlp.util.Preprocessor;
import edu.csupomona.nlp.util.Stopword;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Xing
 */
public class TermFrequency {
    
    public static void main(String[] args) throws IOException {
        
        Stopword sw = new Stopword("en");
        
        File[] files = new File("./lol/raw/").listFiles();
        
        for (File file : files) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            List<String> content = new ArrayList<>();
            while ((line = br.readLine()) != null) 
                content.add(Preprocessor.complex(line));
            
            HashMap<String, Integer> map = new HashMap<>();
            List<String> swList = new ArrayList<>();
            for (String sent : content) {
                String[] words = sent.split(" ");

                for (String word : words) {
                    if (sw.isStopword(word))
                        swList.add(word);
                }
            }
            NGram.updateNGram(1, map, swList);
            
            
            
            FileWriter fw = new FileWriter("./lol/processed/" + file.getName(), false);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                for (String key : map.keySet()) {
                    bw.write(key + "," + map.get(key).toString() + "\n");
                }
            }
        }
        
        
        
    }
    
}
