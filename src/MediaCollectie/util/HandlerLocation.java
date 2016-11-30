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
    public static final double EARTH_RADIUS = 6371e3;

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

            //System.out.println(doc.getTextContent());

            String lat = doc.getElementsByTagName("lat").item(0).getTextContent();

            String lon = doc.getElementsByTagName("lon").item(0).getTextContent();

            return new String[]{lat, lon};
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Error while connecting to the IP Location website");
        }
        return null;
    }

    /**
     * Calculates the distance between 2 sets of latitude and longitude parameters.
     *
     * @param lat1 First latitude in radians
     * @param lon1 First longitude in radians
     * @param lat2 Second latitude in radians
     * @param lon2 Second longitude in radians
     * @return Distance between the First and Second set of parameters.
     */
    public static double geoDistance(double lat1, double lon1, double lat2, double lon2){
        double lambda = lon1 - lon2;
        double phi = lat1 - lat2;

        double a = Math.pow(Math.sin(phi/2), 2) + Math.pow(Math.sin(lambda / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return EARTH_RADIUS * c;
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
    public static double StringToRad(@NotNull String angleDegrees) {
        int degreesSign = angleDegrees.indexOf("°");
        double degrees = Double.parseDouble(angleDegrees.substring(0, degreesSign)) / 180.0 * Math.PI;

        int minuteSign = angleDegrees.indexOf("'");
        double minutes = Double.parseDouble(angleDegrees.substring(degreesSign + 2, minuteSign)) / (60.0 * 180.0) * Math.PI;

        int secondsSign = angleDegrees.indexOf("\"");
        double seconds = Double.parseDouble(angleDegrees.substring(minuteSign + 2, secondsSign).replace(",", ".")) / (360.0 * 180.0) * Math.PI;

        return degrees + minutes + seconds;
    }
}
