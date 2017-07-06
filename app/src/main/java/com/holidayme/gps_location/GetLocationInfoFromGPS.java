package com.holidayme.gps_location;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;

import com.holidayme.common.EventsToHandle;

import java.util.Timer;
import java.util.TimerTask;


public class GetLocationInfoFromGPS {

    private Context context = null;
    private static double latitude = 0, longitude = 0;
    private Handler mHandler = null;
    private Timer timer;
    private LocationManager locationManager;
    private boolean gpsEnabled,networkEnabled ,isRunning=true;


    public GetLocationInfoFromGPS(Context context, Handler handler) {
        this.context = context;
        mHandler = handler;
    }


    public void setFlagStatus(boolean status) {
        isRunning = status;
    }

    /**
     * Finds the location through GPS or Network.
     */
    public void getLocationInfo() {

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gpsEnabled && !networkEnabled) {
            latitude = 0.0;
            longitude = 0.0;
            Message message = new Message();
            message.what = EventsToHandle.PROVIDER_DISABLE.ordinal();
            Bundle bundle = new Bundle();
            bundle.putDouble("LATITUDE", 0.0);
            bundle.putDouble("LONGITUDE", 0.0);
            message.setData(bundle);
            mHandler.sendMessage(message);

        }

        if (gpsEnabled)
            if ( ContextCompat.checkSelfPermission( context, LocationManager.GPS_PROVIDER) == PackageManager.PERMISSION_GRANTED ) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
            }

        if (networkEnabled)
            if ( ContextCompat.checkSelfPermission( context, LocationManager.NETWORK_PROVIDER) == PackageManager.PERMISSION_GRANTED )
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,locationListenerNetwork);

        timer = new Timer();
        timer.schedule(new GetLastLocation(), 9000);

    }

    /**
     * if location found by GPS ,cancel the timer, and remove update and send location to calling Activity
     */

  private   LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if ( ContextCompat.checkSelfPermission( context, LocationManager.GPS_PROVIDER) == PackageManager.PERMISSION_GRANTED )
                locationManager.removeUpdates(this);

            if ( ContextCompat.checkSelfPermission( context, LocationManager.NETWORK_PROVIDER) == PackageManager.PERMISSION_GRANTED )
                locationManager.removeUpdates(locationListenerNetwork);
            Message message = new Message();
            message.what = EventsToHandle.LATLONG_FOUND.ordinal();
            Bundle bundle = new Bundle();
            bundle.putDouble("LATITUDE", latitude);
            bundle.putDouble("LONGITUDE", longitude);
            message.setData(bundle);
            if (isRunning)
            mHandler.sendMessage(message);


        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };


    /**
     * if location found by Network ,cancel the timer, and remove update and send location to calling Activity
     */

  private   LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if ( ContextCompat.checkSelfPermission(context, LocationManager.NETWORK_PROVIDER) == PackageManager.PERMISSION_GRANTED )
            locationManager.removeUpdates(this);

            if ( ContextCompat.checkSelfPermission( context, LocationManager.GPS_PROVIDER) == PackageManager.PERMISSION_GRANTED )
            locationManager.removeUpdates(locationListenerGps);

            Message message = new Message();
            message.what = EventsToHandle.LATLONG_FOUND.ordinal();
            Bundle bundle = new Bundle();
            bundle.putDouble("LATITUDE", latitude);
            bundle.putDouble("LONGITUDE", longitude);
            message.setData(bundle);
            if (isRunning)
            mHandler.sendMessage(message);


        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private class GetLastLocation extends TimerTask {

        @Override
        public void run() {

            if ( ContextCompat.checkSelfPermission(context, LocationManager.GPS_PROVIDER) == PackageManager.PERMISSION_GRANTED )
                locationManager.removeUpdates(locationListenerGps);

            if ( ContextCompat.checkSelfPermission(context, LocationManager.NETWORK_PROVIDER) == PackageManager.PERMISSION_GRANTED )
                locationManager.removeUpdates(locationListenerNetwork);

            Location net_loc = null, gps_loc = null;
            if (gpsEnabled)
                gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (networkEnabled)
                net_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            /**
             *  if location found by both provider, use latest location
             */

            // if there are both values use the latest one
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime()) {
                    latitude = gps_loc.getLatitude();
                    longitude = gps_loc.getLongitude();
                    Message message = new Message();
                    message.what = EventsToHandle.LATLONG_FOUND.ordinal();
                    Bundle bundle = new Bundle();
                    bundle.putDouble("LATITUDE", latitude);
                    bundle.putDouble("LONGITUDE", longitude);
                    message.setData(bundle);
                    if (isRunning)
                        mHandler.sendMessage(message);

                } else {
                    latitude = net_loc.getLatitude();
                    longitude = net_loc.getLongitude();
                    Message message = new Message();
                    message.what = EventsToHandle.LATLONG_FOUND.ordinal();
                    Bundle bundle = new Bundle();
                    bundle.putDouble("LATITUDE", latitude);
                    bundle.putDouble("LONGITUDE", longitude);
                    message.setData(bundle);
                    if (isRunning)
                        mHandler.sendMessage(message);


                }

            } else if (gps_loc != null) {
                {
                    latitude = gps_loc.getLatitude();
                    longitude = gps_loc.getLongitude();
                    Message message = new Message();
                    message.what = EventsToHandle.LATLONG_FOUND.ordinal();
                    Bundle bundle = new Bundle();
                    bundle.putDouble("LATITUDE", latitude);
                    bundle.putDouble("LONGITUDE", longitude);
                    message.setData(bundle);
                    if (isRunning)
                        mHandler.sendMessage(message);

                }

            } else if (net_loc != null) {
                {
                    latitude = net_loc.getLatitude();
                    longitude = net_loc.getLongitude();
                    Message message = new Message();
                    message.what = EventsToHandle.LATLONG_FOUND.ordinal();
                    Bundle bundle = new Bundle();
                    bundle.putDouble("LATITUDE", latitude);
                    bundle.putDouble("LONGITUDE", longitude);
                    message.setData(bundle);
                    if (isRunning)
                        mHandler.sendMessage(message);


                }
            } else {
                latitude = 0.0;
                longitude = 0.0;
                Message message = new Message();
                message.what = EventsToHandle.FAILURE.ordinal();
                Bundle bundle = new Bundle();
                bundle.putDouble("LATITUDE", 0.0);
                bundle.putDouble("LONGITUDE", 0.0);
                message.setData(bundle);
                if (isRunning)
                    mHandler.sendMessage(message);
            }

        }
    }


}
