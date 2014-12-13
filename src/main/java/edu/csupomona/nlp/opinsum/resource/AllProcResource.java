package edu.csupomona.nlp.opinsum.resource;

import edu.csupomona.nlp.opinsum.model.Device;
import edu.csupomona.nlp.opinsum.model.Sentence;
import edu.csupomona.nlp.opinsum.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Xing HU on 12/1/14.
 */
@Path("/allproc")
public class AllProcResource {

    @Autowired
    AspectService aspectService;

    @Autowired
    SentimentService sentimentService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    SentenceService sentenceService;

    @Autowired
    RankService rankService;


    public AllProcResource() {

    }

    @Path("/{productId}")
    @GET
    public String process(@PathParam("productId") String productId) {
        // obtain device info
        Device device = deviceService.findDeviceByProductId(productId);

        if (device != null) {
            // find all sentences for this device
            List<Sentence> sentences = sentenceService.findAllSentencesByDevice(device);

            if (sentences != null) {
                // classify these sentences for aspects
                aspectService.batchClassify(sentences);

                // classify these sentences for sentiments
                sentimentService.batchClassify(sentences);

                // generate rank
                rankService.rank(sentences);

                return "Batch processing " + device.getName() + " finished.";
            }
        }

        throw new NotFoundException(Response
                .status(Response.Status.NOT_FOUND)
                .entity("Device: " + productId + ", is not found")
                .build());
    }
}
