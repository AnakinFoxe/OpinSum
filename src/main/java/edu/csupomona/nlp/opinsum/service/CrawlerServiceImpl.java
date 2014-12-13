package edu.csupomona.nlp.opinsum.service;


import edu.csupomona.nlp.opinsum.model.Device;
import edu.csupomona.nlp.opinsum.model.Sentence;
import edu.csupomona.nlp.opinsum.repository.DeviceRepository;
import edu.csupomona.nlp.opinsum.repository.SentenceRepository;
import edu.csupomona.nlp.opinsum.utils.DeviceMetaData;
import edu.csupomona.nlp.tool.crawler.Amazon;
import edu.csupomona.nlp.util.Preprocessor;
import edu.csupomona.nlp.util.StanfordTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Xing HU on 11/30/14.
 */
@Service("crawlerService")
public class CrawlerServiceImpl implements CrawlerService {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    SentenceRepository sentenceRepository;

    @Transactional
    public void crawl(String productId) {
        DeviceMetaData metaDataCrawler = new DeviceMetaData();
        Amazon crawler = new Amazon();

        try {
            // crawl device information
            Device device = metaDataCrawler.crawlDevice(productId);

            // save to database
            device = deviceRepository.save(device);

            // crawl user reviews for the product id
            List<String> reviews = crawler.crawl(productId);

            // prepare Stanford sentence splitter
            Properties props = new Properties();
            props.put("annotators", "tokenize, ssplit");
            StanfordTools stan = new StanfordTools(props);
            for (String review : reviews) {
                if (review.length() > 0) {
                    // split into sentences
                    List<String> sentences = stan.sentence(review);

                    // for each valid sentence (char length > 10)
                    for (String sentence : sentences) {
                        sentence = sentence.trim();

                        if (sentence.length() > 10) {
                            // preprocess the sentence
                            String procSent = Preprocessor.complex(sentence);

                            // construct Sentence obj
                            Sentence valSent = new Sentence();
                            valSent.setOrigSent(sentence);
                            valSent.setProcSent(procSent);
                            valSent.setDevice(device);

                            // save into database
                            sentenceRepository.save(valSent);
                        }
                    }
                }
            }
        } catch (IOException e ) {
            e.printStackTrace();
            System.out.println("Problem encountered. Crawling failed.");
        }
    }
}
