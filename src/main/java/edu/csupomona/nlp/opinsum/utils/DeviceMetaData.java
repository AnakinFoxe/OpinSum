package edu.csupomona.nlp.opinsum.utils;

import edu.csupomona.nlp.opinsum.model.Device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Xing HU on 11/30/14.
 */
public class DeviceMetaData {

    private final String BASE_URL_ = "http://www.amazon.com/gp/product/";

    private final Pattern PATT_NAME
            = Pattern.compile("<span id=\"productTitle\" class=\"a-size-large\">([\\w\\W]*?)</span>");

    private final Pattern PATT_IMG
            = Pattern.compile("'colorImages': \\{ 'initial':.*?\"hiRes\":\"([\\w\\W]*?)\"");

    private final Pattern PATT_THUMB
            = Pattern.compile("'colorImages': \\{ 'initial':.*?\"thumb\":\"([\\w\\W]*?)\"");

    public Device crawlDevice(String productId) {
        String mainPageUrl = BASE_URL_ + productId;

        try {
            Device device = new Device();
            device.setProductId(productId);

            URL url = new URL(mainPageUrl);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(url.openStream(), "UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {

                if (line.length() > 10) {
                    // match the name
                    Matcher nameMatcher = PATT_NAME.matcher(line);
                    if (nameMatcher.find())
                        device.setName(nameMatcher.group(1).trim());

                    // match the main image url
                    Matcher imgMatcher = PATT_IMG.matcher(line);
                    if (imgMatcher.find())
                        device.setImgUrl(imgMatcher.group(1).trim());

                    // match the main thumbnail url
                    Matcher thumbMatcher = PATT_THUMB.matcher(line);
                    if (thumbMatcher.find())
                        device.setThumbnailUrl(thumbMatcher.group(1).trim());
                }
            }

            return device;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
