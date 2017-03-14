package com.alenave.markercluster.activities;

import android.widget.Toast;

import com.alenave.markercluster.model.Item;
import com.alenave.markercluster.utils.ItemReader;
import com.alenave.markercluster.utils.LocationOn;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;

import java.util.List;

public class ClusteringActivity extends BaseActivity {
    private ClusterManager<Item> mClusterManager;
    private LatLng yourLocation;

    @Override
    protected void startMap() {
        yourLocation = new LatLng(LocationOn.getInstance(this).getLatitude(), LocationOn.getInstance(this).getLongitude());
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 12));

        mClusterManager = new ClusterManager<Item>(this, getMap());
        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }

    private void readItems() throws JSONException {
        List<Item> items = new ItemReader().read(yourLocation);
        mClusterManager.addItems(items);
    }
}