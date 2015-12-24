package com.hyogij.weathermap.Helpers;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.hyogij.weathermap.R;

/**
 * A class to provide access to the system location services
 */
public class LocationHelper extends Service implements LocationListener {
    private static final String CLASS_NAME = LocationHelper.class.getCanonicalName();

    private static final float MIN_DISTANCE_UPDATES = 10;
    private static final long MIN_TIME_UPDATES = 1000 * 60 * 1;

    private Context context = null;
    private Handler handler = null;

    private Location location = null;
    private LocationManager locationManager = null;

    public LocationHelper(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;

        initLocationManager();
    }

    public void initLocationManager() {
        locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);

        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager
                .NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            showSettingsAlert();
        }

        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(CLASS_NAME, "Missing permissions : ACCESS_FINE_LOCATION, " +
                    "ACCESS_COARSE_LOCATION");
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                MIN_TIME_UPDATES, MIN_DISTANCE_UPDATES, this);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATES,
                MIN_DISTANCE_UPDATES, this);
    }

    // Prompt the user with a dialog and ask if he wants to manually enable it.
    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(context.getString(R.string.gps_dialog_content));
        alertDialog.setPositiveButton(context.getString(R.string.setting),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                });
        alertDialog.setNegativeButton(context.getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(CLASS_NAME, "onLocationChanged ");
        this.location = location;

        // Send message to caller to notify changing location
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onStatusChanged(String provider, int status,
                                Bundle extras) {
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                Toast.makeText(context, context.getString(R.string
                        .error_location_provider_outofservice), Toast.LENGTH_SHORT).show();
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Toast.makeText(this, context.getString(R.string
                        .error_location_provider_unavailable), Toast.LENGTH_SHORT).show();
                break;
            case LocationProvider.AVAILABLE:
                Toast.makeText(this, context.getString(R.string
                        .error_location_provider_available), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(CLASS_NAME, "onProviderEnabled ");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(CLASS_NAME, "onProviderDisabled");
    }
}
