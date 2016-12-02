package MediaCollectie.data;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Grayscale;
import Catalano.Imaging.Tools.ImageHistogram;
import Catalano.Imaging.Tools.ImageStatistics;
import MediaCollectie.util.HandlerLocation;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * MediaObject stores all the data for a picture.
 */
public class MediaObject implements Comparable{
    // =================================================================================================================
    // Static utilities:
    // =================================================================================================================
    public static HashMap<MediaObject, LocalDate> getLocalDateMap(List<MediaObject> mediaObjects) {
        HashMap<MediaObject, LocalDate> returnMap = new HashMap<>();
        mediaObjects.forEach((MediaObject m) -> returnMap.put(m, m.getDate()));
        return returnMap;
    }
    public static HashMap<MediaObject, Location> getLocationMap(List<MediaObject> mediaObjects) {
        HashMap<MediaObject, Location> returnMap = new HashMap<>();
        mediaObjects.forEach((MediaObject m) -> returnMap.put(m, new Location(m.getLatitude(), m.getLongitude())));
        return returnMap;
    }

    private String name;
    private Date datum;
    private File file;

    private double greyScaleMean;
    private double latitude;
    private double longitude;

    public MediaObject(String _name, Date _datum, File _file) {
        name = _name;
        datum = _datum;
        file = _file;

        FastBitmap fb = new FastBitmap(file.getAbsolutePath());
        fb.toGrayscale();

        Grayscale filter = new Grayscale(0.2125,0.7154,0.0721);
        filter.applyInPlace(fb);

        ImageStatistics stats = new ImageStatistics(fb);
        ImageHistogram hist = stats.getHistogramGray();
        greyScaleMean = hist.getMean();

        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory d : metadata.getDirectories()) {
                if(d.getName().equals("GPS")) {
                    for (Tag t : d.getTags()) {
                        if(t.getTagName().equals("GPS Longitude")) longitude = HandlerLocation.StringToRad(t.getDescription());
                        if(t.getTagName().equals("GPS Latitude")) latitude = HandlerLocation.StringToRad(t.getDescription());
                    }
                }
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =================================================================================================================
    // Properties:
    // =================================================================================================================
    public String getName() {
        return name;
    }
    public File getFile() {
        return file;
    }
    public LocalDate getDate() {
        return datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // =================================================================================================================
    // Calculation values:
    // =================================================================================================================
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getGreyScaleMean() {
        return greyScaleMean;
    }

    public String toString() {
        return "[" + getDate().toString() + "] " + getName();
    }
    public boolean equals(Object o) {
        return o instanceof MediaObject && (this.getFile().getAbsolutePath().equals(((MediaObject) o).getFile().getAbsolutePath()));
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof MediaObject) {
            if(this.getGreyScaleMean() > ((MediaObject) o).getGreyScaleMean()) return 1;
            else if(this.getGreyScaleMean() < ((MediaObject) o).getGreyScaleMean()) return -1;
            else return 0;
        } else throw new NotImplementedException();
    }
}