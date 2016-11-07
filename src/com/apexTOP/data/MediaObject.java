package com.apexTOP.data;

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

    public long getLongitude() {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory d : metadata.getDirectories()) {
                if(d.getName().equals("GPS")) {
                    for (Tag t : d.getTags()) {
                        if(t.getTagName().equals("GPS Longitude")) return Long.parseLong(t.getDescription());
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
    public long getLatitude() {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory d : metadata.getDirectories()) {
                if(d.getName().equals("GPS")) {
                    for (Tag t : d.getTags()) {
                        if(t.getTagName().equals("GPS Latitude")) return Long.parseLong(t.getDescription());
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
}