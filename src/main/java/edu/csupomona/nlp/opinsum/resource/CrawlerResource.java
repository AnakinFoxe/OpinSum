package edu.csupomona.nlp.opinsum.resource;

import edu.csupomona.nlp.opinsum.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by Xing HU on 11/30/14.
 */
@Path("/crawl")
public class CrawlerResource {

    @Autowired
    CrawlerService crawlerService;

    public CrawlerResource() {

    }

    @Path("{productId}")
    @GET    // shouldn't use GET I think
    public String getCrawling(@PathParam("productId") String productId) {

        crawlerService.crawl(productId);

        return "Start crawling " + productId;
    }
}
