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
            e.printStackTrace();
        }
        return null;
    }

    public static double geoDistance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double distance = Math.sin(D2R(lat1)) * Math.sin(D2R(lat2)) + Math.cos(D2R(lon1)) * Math.cos(D2R(lon2)) * Math.cos(D2R(theta));
        distance = Math.acos(distance);
        distance = R2D(distance);
        distance = distance * 60 * 1.1515;
        distance = distance * 1.609344;

        return distance;
    }

    public static double D2R(@NotNull double deg) {
        return (deg * Math.PI) / 180.0;
    }

    public static double R2D(@NotNull double rad) {
        return (rad * 180.0) / Math.PI;
    }

    public static InetAddress getLocalLANAddress() {
        return null;
    }
}
