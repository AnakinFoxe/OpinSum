/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.csupomona.nlp;

import edu.csupomona.nlp.util.FileProcessor;
import edu.csupomona.nlp.util.Preprocessor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xing
 */
public class PreprocessReviews extends FileProcessor {
    
    /**
     * Parse the file into a list of sentences
     * @param filePath      Path to the target file
     * @return              List of sentences from the file
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    protected List<String> processFile(String filePath) 
            throws FileNotFoundException, IOException  {
        List<String> content = new ArrayList<>();
        
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        
        // read first line to tell the type of file
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().length() > 0) {
                List<String> sentences = 
                        stan.sentence(Preprocessor.complex(line.trim()));
                content.addAll(sentences);
            }
        }
        
        return content;
    }
    
    public static void main(String[] args) throws IOException {
        PreprocessReviews fp = new PreprocessReviews();
        
        fp.process("./data/reviews/raw", 
                "./data/reviews/pre-processed");
    }
}
