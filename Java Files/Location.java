package com.example.surrogateshopper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class Location implements LocationListener {
        Context c;
       Location(Context x){
           c=x;
       }

       public android.location.Location getlocation(){
           if(ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
               Toast.makeText(c,"Permission Not Granted",Toast.LENGTH_SHORT).show();
               return null;
           }
           LocationManager lm= (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
           boolean isGPSEnabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
           if(isGPSEnabled) {
               lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
               android.location.Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
               return l;
           }
           else{
               Toast.makeText(c,"Please Enable GPS",Toast.LENGTH_LONG).show();
               return null;
           }

       }
    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
