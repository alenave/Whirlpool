package com.alenave.markercluster.utils;

import com.alenave.markercluster.MainActivity;
import com.alenave.markercluster.model.Item;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ItemReader {

    public List<Item> read(LatLng yourLocation) throws JSONException {
        List<Item> items = new ArrayList<>();
        double lat = yourLocation.latitude;
        double lng = yourLocation.longitude;
        int counter = 0;
        for (float i = 0; counter < MainActivity.markersCounter; i += 0.001) {
            for (float j = 0; j < .02 && counter < MainActivity.markersCounter; j += 0.001, counter++) {
                items.add(new Item((lat + i), (lng + j)));
            }
        }
        return items;
    }

}
