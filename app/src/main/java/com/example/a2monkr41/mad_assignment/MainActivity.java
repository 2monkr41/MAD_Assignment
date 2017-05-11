package com.example.a2monkr41.mad_assignment;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import android.widget.Toast;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.activity_main);

        mv = (MapView) findViewById(R.id.map1);

        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(50.9, -1.4));

        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>()
        {
            public boolean onItemLongPress(int i, OverlayItem item)
            {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onItemSingleTapUp(int i, OverlayItem item)
            {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }


        };

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        OverlayItem solent = new OverlayItem("Solent University", "Main campus of solent university", new GeoPoint(50.907984, -1.400195));
        items.addItem(solent);
        mv.getOverlays().add(items);

    }

    public void onStart()
    {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean uploadtoweb = prefs.getBoolean("uploadtoweb", true);

        if(uploadtoweb == true){
            Toast.makeText(MainActivity.this, "Preference is set", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Preference is not set", Toast.LENGTH_SHORT).show();
        }


    }

    public void onDestroy()
    {
            super.onDestroy();

        try {
            PrintWriter pw = new PrintWriter((new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/filesave.txt")));
            for (int i = 0; i < items.size(); i++) {
                OverlayItem itemm = items.getItem(i);
                pw.println(itemm.getTitle() + "," + itemm.getSnippet() + "," + itemm.getPoint() + "");
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error" + e);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.poi_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.choosemap) {

            Intent intent = new Intent(this, AddPoiActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        if (item.getItemId() == R.id.savefile)
            try {
                PrintWriter pw = new PrintWriter((new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/filesave.txt")));
                for (int i = 0; i < items.size(); i++) {
                    OverlayItem itemm = items.getItem(i);
                    pw.println(itemm.getTitle() + "," + itemm.getSnippet() + "," + itemm.getPoint() + "");
                }
                pw.close();
            } catch (IOException e) {
                System.out.println("Error" + e);
                return true;
            }
        if (item.getItemId() == R.id.uploadtoweb) {
            Intent intent = new Intent(this, MyPrefsActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }

        return false;
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        if(requestCode==0)
        {

            if (resultCode==RESULT_OK)
            {
                Bundle bundle = intent.getExtras();
                String poiName = bundle.getString("com.example.poiname");
                String poiType = bundle.getString("com.example.poitype");
                String poiDDescription = bundle.getString("com.example.poidescription");
                double lat = mv.getMapCenter().getLatitude();
                double lon = mv.getMapCenter().getLongitude();
                OverlayItem item = new OverlayItem(poiName, poiType, poiDDescription, new GeoPoint(lat, lon));
                items.addItem(item);

                mv.invalidate();
            }
        }
    }
}

