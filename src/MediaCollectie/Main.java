package MediaCollectie;

import MediaCollectie.data.MediaObject;
import MediaCollectie.util.HandlerLocation;

import java.io.File;
import java.util.Date;

/**
 * Main to test everything.
 */
public class Main {

    public static void main(String[] args) {
        String URL = "D:/Documenten/School/Datastructuren/Images/DSC_0441.jpg";
        File file = new File(URL);
        MediaObject image = new MediaObject(file.getName(), new Date(file.lastModified()), file);
        System.out.println("Image: " + HandlerLocation.R2D(image.getLatitude()) + " , " + HandlerLocation.R2D(image.getLongitude()));

        String[] location = HandlerLocation.getLatitudeAndLongitude("");
        if(location == null) System.out.println("No connection established");
        else System.out.println("Length: " + location.length + "\nLatitiude: " + location[0] + "\nLongitude: " + location[1]);

        System.out.println(HandlerLocation.geoDistance(image.getLatitude(), image.getLongitude(), HandlerLocation.D2R(Double.parseDouble(location[0])), HandlerLocation.D2R(Double.parseDouble(location[1]))));
    }
}