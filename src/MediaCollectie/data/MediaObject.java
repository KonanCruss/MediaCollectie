package MediaCollectie.data;

import Catalano.Imaging.FastBitmap;
import Catalano.Imaging.Filters.Grayscale;
import Catalano.Imaging.Tools.ImageHistogram;
import Catalano.Imaging.Tools.ImageStatistics;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * MediaObject stores all the data for a picture.
 */
public class MediaObject {
    private String name;
    private int size;
    private Date datum;
    private File file;

    public MediaObject(String _name, int _size, Date _datum, File _file) {
        name = _name;
        size = _size;
        datum = _datum;
        file = _file;
    }

    // =================================================================================================================
    // Properties:
    // =================================================================================================================
    public String getName() {
        return name;
    }
    public int getSize() {
        return size;
    }
    public Date getDatum() {
        return datum;
    }
    public File getFile() {
        return file;
    }

    // =================================================================================================================
    // Calculation values:
    // =================================================================================================================
    public double getLongitude() {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory d : metadata.getDirectories()) {
                if(d.getName().equals("GPS")) {
                    for (Tag t : d.getTags()) {
                        if(t.getTagName().equals("GPS Longitude")) return Double.parseDouble(t.getDescription());
                    }
                }
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public double getLatitude() {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory d : metadata.getDirectories()) {
                if(d.getName().equals("GPS")) {
                    for (Tag t : d.getTags()) {
                        if(t.getTagName().equals("GPS Latitude")) return Double.parseDouble(t.getDescription());
                    }
                }
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public double getGreyScaleMean() {
        FastBitmap fb = new FastBitmap(file.getAbsolutePath());
        fb.toGrayscale();

        Grayscale filter = new Grayscale(0.2125,0.7154,0.0721);
        filter.applyInPlace(fb);

        ImageStatistics stats = new ImageStatistics(fb);
        ImageHistogram hist = stats.getHistogramGray();

        return hist.getMean();
    }

    public String toString() {
        return "[" + getDatum().toString() + "] " + getName();
    }
    public boolean equals(Object o) {
        return o instanceof MediaObject && (this.getFile().getAbsolutePath().equals(((MediaObject) o).getFile().getAbsolutePath()));
    }
}