package edu.csupomona.nlp.opinsum.resource;

import edu.csupomona.nlp.opinsum.model.Device;
import edu.csupomona.nlp.opinsum.model.Sentence;
import edu.csupomona.nlp.opinsum.service.AspectService;
import edu.csupomona.nlp.opinsum.service.DeviceService;
import edu.csupomona.nlp.opinsum.service.SentenceService;
import edu.csupomona.nlp.opinsum.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xing HU on 11/30/14.
 */
@Path("/aspect")
public class AspectResource {

    @Autowired
    AspectService aspectService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    SentenceService sentenceService;

    public AspectResource() {

    }

    @Path("/train")
    @GET
    public String loadAspectNGram() {
        // temporary list
        List<String> aspects = new ArrayList<>();
        aspects.add("battery");
        aspects.add("camera");
        aspects.add("screen");
        aspects.add("process");
        aspects.add("weight");
        aspects.add("size");
        aspects.add("design");
        aspects.add("sensor");

        // call training service
        aspectService.train(aspects);

        return "Aspect Identification prepared.";
    }

    @Path("/batchClassify/{productId}")
    @GET
    public String batchClassify(@PathParam("productId") String productId) {
        // obtain device info
        Device device = deviceService.findDeviceByProductId(productId);

        if (device != null) {
            // find all sentences for this device
            List<Sentence> sentences = sentenceService.findAllSentencesByDevice(device);

            if (sentences != null) {
                // classify these sentences
                aspectService.batchClassify(sentences);

                return "Batch processing " + device.getName() + " finished.";
            }
        }

        throw new NotFoundException(Response
                .status(Response.Status.NOT_FOUND)
                .entity("Device: " + productId + ", is not found")
                .build());
    }

}
