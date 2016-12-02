package MediaCollectie.util.search;

import MediaCollectie.UIMain;
import MediaCollectie.data.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchLocation<E> implements ISearch<E> {
    HashMap<E, Location> searchMap;

    double lat = UIMain.latitude;
    double lon = UIMain.longitude;

    public SearchLocation(HashMap<E, Location> list) {
        searchMap = list;
    }

    public SearchLocation(HashMap<E, Location> list, double latitude, double longitude) {
        this(list);
        lat = latitude;
        lon = longitude;
    }

    @Override
    public E run() {
        List<E> searchObjects = new ArrayList<E>(searchMap.keySet());
        List<Location> compareObjects = new ArrayList<>(searchMap.values());

        int indexClosest = 0;
        for(int i = 1; i < compareObjects.size(); ++i) {
            if(compareObjects.get(indexClosest).getDistance(lat, lon) > compareObjects.get(i).getDistance(lat, lon))
                indexClosest = i;
        }

        System.out.println(searchObjects.get(indexClosest));
        return searchObjects.get(indexClosest);
    }
}
