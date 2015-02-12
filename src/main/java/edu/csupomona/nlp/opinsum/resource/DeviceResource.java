package edu.csupomona.nlp.opinsum.resource;

import edu.csupomona.nlp.opinsum.model.AspectSentiment;
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
                HashMap<String, List<Integer>> sentiments = new HashMap<>();
                HashMap<String, List<List<String>>> summaries = new HashMap<>();
                for (Sentence sentence : sentences) {
                    String aspect = sentence.getAspect();
                    String sentiment = sentence.getSentiment();
                    if ((aspect != null) && (sentiment != null) && (!aspect.equals("others"))) {
                        // prepare for aspect and sentiment results
                        List<Integer> posNeg;
                        if (sentiments.containsKey(aspect))
                            posNeg = sentiments.get(aspect);
                        else {
                            posNeg = new ArrayList<>();
                            posNeg.add(new Integer(0)); // for positive
                            posNeg.add(new Integer(0)); // for negative
                        }


                        if (sentiment.equals("positive")) {
                            posNeg.set(0, posNeg.get(0) + 1);
                        }
                        else {
                            posNeg.set(1, posNeg.get(1) + 1);
                        }

                        sentiments.put(aspect, posNeg);

                        // prepare for summaries (ranked sentences)
                        Integer rank = sentence.getRank();
                        if (rank != null && rank <= 3) {
                            List<List<String>> summariesForSentiment;
                            if (summaries.containsKey(aspect))
                                summariesForSentiment = summaries.get(aspect);
                            else {
                                summariesForSentiment = new ArrayList<>();
                                summariesForSentiment.add(new ArrayList<String>()); // for positive sentences
                                summariesForSentiment.add(new ArrayList<String>()); // for negative sentences
                            }

                            if (sentiment.equals("positive"))
                                summariesForSentiment.get(0).add(sentence.getProcSent());
                            else
                                summariesForSentiment.get(1).add(sentence.getProcSent());

                            summaries.put(aspect, summariesForSentiment);
                        }
                    }


                }

                // convert to DeviceSummary
                DeviceSummary deviceSummary = new DeviceSummary();
                deviceSummary.setId(device.getId());
                deviceSummary.setName(device.getName());
                deviceSummary.setImgUrl(device.getImgUrl());
                deviceSummary.setThumbnailUrl(device.getThumbnailUrl());
                deviceSummary.setProductId(device.getProductId());
                List<AspectSentiment> aspectSentiments = new ArrayList<>();
                for (Map.Entry<String, List<Integer>> entry : sentiments.entrySet()) {
                    String aspect = entry.getKey();
                    Integer pos = entry.getValue().get(0);
                    Integer neg = entry.getValue().get(1);

                    // record the counts
                    List<Integer> posNegCounts = new ArrayList<>();
                    posNegCounts.add(pos);
                    posNegCounts.add(neg);

                    // convert to percentage
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
                    AspectSentiment aspectSentiment = new AspectSentiment();
                    aspectSentiment.setName(aspect);
                    aspectSentiment.setSentimentCounts(posNegCounts);
                    aspectSentiment.setSentiment(posNeg);

                    if (summaries.containsKey(aspect))
                        aspectSentiment.setSummaries(summaries.get(aspect));
                    aspectSentiments.add(aspectSentiment);
                }
                deviceSummary.setAspectSentiment(aspectSentiments);

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
    @Path("/device/{deviceId}/{aspect}/{limit}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getSentenceList(@PathParam("deviceId") Long id,
                                          @PathParam("aspect") String aspect,
                                          @PathParam("limit") Integer limit) {
        // find device
        Device device = deviceService.findDeviceById(id);

        // find all related sentences
        if (device != null) {
            List<Sentence> sentences = sentenceService.findAllSentencesByDevice(device);

            if (sentences != null) {
                List<String> selected = new ArrayList<>();
                for (Sentence sentence : sentences) {
                    if ((sentence.getAspect() != null)  // temporary
                            && (sentence.getRank() != null)
                            && sentence.getAspect().equals(aspect)) {
                        if ((limit > 0 && sentence.getRank() <= limit)
                            || limit == 0)
                            selected.add(sentence.getProcSent());
                    }
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

    @Path("/query")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Device> searchDevice(@QueryParam("keyword") String keyword,
                                     @DefaultValue("smart_phone") @QueryParam("type") String type) {

        List<Device> devices = deviceService.findAllDevices();

        List<Device> selected = new ArrayList<>();

        for (Device device : devices) {
            if (device.getName().toLowerCase().contains(keyword.toLowerCase()))
                selected.add(device);
        }

        return selected;
//        throw new NotFoundException(Response
//                .status(Response.Status.NOT_FOUND)
//                .entity("Device relates to " + keyword + " is not found")
//                .build());
    }

}
