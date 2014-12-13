package edu.csupomona.nlp.opinsum.resource;

import edu.csupomona.nlp.opinsum.model.Device;
import edu.csupomona.nlp.opinsum.model.DeviceSummary;
import edu.csupomona.nlp.opinsum.model.Sentence;
import edu.csupomona.nlp.opinsum.service.DeviceService;
import edu.csupomona.nlp.opinsum.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Device resource
 */
@Path("/")
public class DeviceResource {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    SentenceService sentenceService;

    public DeviceResource() {

    }

    /**
     * Get information for all the devices
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getAll() {

        return deviceService.findAllDevices();
    }

    /**
     * Get information for a specific device using device id
     * @param id
     * @return
     */
    @Path("/device/{deviceId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDevice(@PathParam("deviceId") Long id) {
        Device device = deviceService.findDeviceById(id);

        if (device != null)
            return device;

        throw new NotFoundException(Response
                .status(Response.Status.NOT_FOUND)
                .entity("Device, " + id + ", is not found")
                .build());
    }

    /**
     * Get all the top sentences related to the device using device id
     * @param id
     * @return
     */
    @Path("/device/{deviceId}/sentences")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sentence> getSentences(@PathParam("deviceId") Long id) {
        // find device
        Device device = deviceService.findDeviceById(id);

        // find all related sentences
        if (device != null) {
            List<Sentence> sentences = sentenceService.findAllSentencesByDevice(device);

            if (sentences != null) {
                List<Sentence> selected = new ArrayList<>();
                for (Sentence sentence : sentences) {
                    if ((sentence.getAspect() != null)
                            && (sentence.getRank() != null) // temporary
                        && (!sentence.getAspect().equals("others"))
                        && (sentence.getRank() < 4)) // only select top three
                        selected.add(sentence);
                }

                return selected;
            }
        }

        throw new NotFoundException(Response
                .status(Response.Status.NOT_FOUND)
                .entity("Device, " + id + ", is not found")
                .build());
    }

    /**
     * Get general info, aspects, sentiments and summaries for the device
     * @param id                Device Id
     * @return
     */
    @Path("/device/{deviceId}/summaries")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public DeviceSummary getSummaries(@PathParam("deviceId") Long id) {
        // find device
        Device device = deviceService.findDeviceById(id);

        // find all related sentences
        if (device != null) {
            List<Sentence> sentences = sentenceService.findAllSentencesByDevice(device);

            if (sentences != null) {
                HashMap<String, List<Integer>> summaries = new HashMap<>();
                for (Sentence sentence : sentences) {
                    String aspect = sentence.getAspect();
                    String sentiment = sentence.getSentiment();
                    if ((aspect != null) && (sentiment != null) && (!aspect.equals("others"))) {
                        List<Integer> posNeg;
                        if (summaries.containsKey(aspect))
                            posNeg = summaries.get(aspect);
                        else {
                            posNeg = new ArrayList<>();
                            posNeg.add(new Integer(0)); // for positive
                            posNeg.add(new Integer(0)); // for negative
                        }


                        if (sentiment.equals("positive"))
                            posNeg.set(0, posNeg.get(0) + 1);
                        else
                            posNeg.set(1, posNeg.get(1) + 1);

                        summaries.put(aspect, posNeg);
                    }
                }

                // convert to DeviceSummary
                DeviceSummary deviceSummary = new DeviceSummary();
                deviceSummary.setId(device.getId());
                deviceSummary.setName(device.getName());
                deviceSummary.setImgUrl(device.getImgUrl());
                deviceSummary.setThumbnailUrl(device.getThumbnailUrl());
                deviceSummary.setProductId(device.getProductId());
                HashMap<String, List<Integer>> aspectSentiment = new HashMap<>();
                for (Map.Entry<String, List<Integer>> entry : summaries.entrySet()) {
                    Integer pos = entry.getValue().get(0);
                    Integer neg = entry.getValue().get(1);
                    Integer sum = pos + neg;
                    List<Integer> posNeg = new ArrayList<>();
                    if (sum > 0) {
                        pos = (pos * 100) / sum;
                        neg = 100 - pos;
                        posNeg.add(pos);
                        posNeg.add(neg);
                    } else {
                        posNeg.add(0);
                        posNeg.add(0);
                    }
                    aspectSentiment.put(entry.getKey(), posNeg);
                }
                deviceSummary.setAspectSentiment(aspectSentiment);

                return deviceSummary;
            }
        }

        throw new NotFoundException(Response
                .status(Response.Status.NOT_FOUND)
                .entity("Device, " + id + ", is not found")
                .build());
    }

    /**
     * Get all the sentences for the aspect of the device using device id and aspect name
     * @param id
     * @param aspect
     * @return
     */
    @Path("/device/{deviceId}/{aspect}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sentence> getSentenceList(@PathParam("deviceId") Long id,
                                          @PathParam("aspect") String aspect) {
        // find device
        Device device = deviceService.findDeviceById(id);

        // find all related sentences
        if (device != null) {
            List<Sentence> sentences = sentenceService.findAllSentencesByDevice(device);

            if (sentences != null) {
                List<Sentence> selected = new ArrayList<>();
                for (Sentence sentence : sentences) {
                    if ((sentence.getAspect() != null)  // temporary
                            && (sentence.getRank() != null)
                    && sentence.getAspect().equals(aspect))
                        selected.add(sentence);
                }

                return selected;
            }
        }

        throw new NotFoundException(Response
                .status(Response.Status.NOT_FOUND)
                .entity("Device, " + id + ", is not found")
                .build());
    }

    @Path("/deviceAdd/{name}:{imgUrl}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String addDevice(@PathParam("name") String name,
                          @PathParam("imgUrl") String imgUrl) {
        return (name + imgUrl);
    }

}
