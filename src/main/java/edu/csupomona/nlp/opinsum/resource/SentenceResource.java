package edu.csupomona.nlp.opinsum.resource;

import edu.csupomona.nlp.opinsum.service.DeviceService;
import edu.csupomona.nlp.opinsum.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Xing HU on 11/24/14.
 */
@Path("/sentence")
public class SentenceResource {

    @Autowired
    DeviceService deviceService;

    @Autowired
    SentenceService sentenceService;

    public SentenceResource() {

    }

    /**
     * Update the sentiment information of the sentence
     * @param id
     * @return
     */
    @Path("{sentenceId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updateSentence(@PathParam("sentenceId") Long id) {


        throw new NotFoundException(Response
                .status(Response.Status.NOT_FOUND)
                .entity("Device, " + id + ", is not found")
                .build());
    }
}
