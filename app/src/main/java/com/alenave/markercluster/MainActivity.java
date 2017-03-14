package com.alenave.markercluster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alenave.markercluster.activities.ClusteringActivity;
import com.alenave.markercluster.utils.InternetConnection;
import com.alenave.markercluster.utils.LocationOn;
import com.alenave.markercluster.utils.Permission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private Button retry;
    private Button cluster;
    private EditText numberOfMarkers;
    public static int markersCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Permission.check(this);
        initViews();
        if (!InternetConnection.isInternetConnected(getApplicationContext())) {
            retry.setVisibility(View.VISIBLE);
            cluster.setVisibility(View.GONE);
            numberOfMarkers.setVisibility(View.GONE);
        }

    }

    private void initViews() {
        retry = (Button) findViewById(R.id.retry);
        retry.setOnClickListener(this);
        cluster = (Button) findViewById(R.id.cluster);
        cluster.setOnClickListener(this);
        numberOfMarkers = (EditText) findViewById(R.id.number_of_markers);
        numberOfMarkers.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retry:
                if (InternetConnection.isInternetConnected(getApplicationContext())) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                break;
            case R.id.cluster:
                LocationOn.getInstance(this).check();
                if (!numberOfMarkers.getText().toString().equalsIgnoreCase("")) {
                    markersCounter = Integer.valueOf(numberOfMarkers.getText().toString());
                    if (LocationOn.getInstance(this).getLatitude() > 0 && LocationOn.getInstance(this).getLongitude() > 0) {
                        startActivity(new Intent(this, ClusteringActivity.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter number of markers", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            cluster.performClick();
            return true;
        }
        return false;
    }
}