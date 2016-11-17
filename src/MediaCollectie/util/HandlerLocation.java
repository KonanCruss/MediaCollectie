package MediaCollectie.util;

import com.drew.lang.annotations.NotNull;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.*;

public class HandlerLocation {
    /**
     * Gets the location in latitude and longitude based on IPAddress
     *
     * @param ipAddress IPAddress leave it as "" to use your Local LAN IPAddress.
     * @return Latitude and Longitude in a String array.
     */
    public static String[] getLatitudeAndLongitude(@NotNull String ipAddress) {
        String webLink = "http://ip-api.com/xml/" + ipAddress;
        try {
            URL urlLink = new URL(webLink);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(urlLink.openStream());

            System.out.println(doc.getTextContent());

            String lat = doc.getElementsByTagName("lat").item(0).getTextContent();
            String lon = doc.getElementsByTagName("lon").item(0).getTextContent();

            return new String[]{lat, lon};
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Error while connecting to the IP Location website getter");
        }
        return null;
    }

    /**
     * Calculates the distance between 2 sets of latitude and longitude parameters.
     *
     * @param lat1 First latitude
     * @param lon1 First longitude
     * @param lat2 Second latitude
     * @param lon2 Second longitude
     * @return Distance between the First and Second set of parameters.
     */
    public static double geoDistance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double distance = Math.sin(D2R(lat1)) * Math.sin(D2R(lat2)) + Math.cos(D2R(lon1)) * Math.cos(D2R(lon2)) * Math.cos(D2R(theta));
        distance = Math.acos(distance);
        distance = R2D(distance);
        distance = distance * 60 * 1.1515;
        distance = distance * 1.609344;

        return distance;
    }

    // =================================================================================================================
    // Angle conversion:
    // =================================================================================================================
    public static double D2R(@NotNull double deg) {
        return (deg * Math.PI) / 180.0;
    }
    public static double R2D(@NotNull double rad) {
        return (rad * 180.0) / Math.PI;
    }

}
