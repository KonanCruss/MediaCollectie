package MediaCollectie.data;

import MediaCollectie.util.HandlerLocation;

public class Location {
    private double latitude;
    private double longitude;

    public Location(double lat, double lon) {
        latitude = lat;
        longitude = lon;
    }

    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getDistance(double lat, double lon) {
        return HandlerLocation.geoDistance(lat, lon, latitude, longitude);
    }
}
