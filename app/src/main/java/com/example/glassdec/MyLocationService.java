package com.example.glassdec;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

public class MyLocationService extends BroadcastReceiver
{
    public static final String ACTION_PROCESS_UPDATE="com.example.glassdec.UPDATE_LOCATION";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent!=null)
        {
            final String action =intent.getAction();
            if(ACTION_PROCESS_UPDATE.equals(action))
            {
                LocationResult result=LocationResult.extractResult(intent);
                if (result!=null)
                {
                    Location location=result.getLastLocation();
                    String sLocation=new StringBuilder(""+location.getLatitude())
                            .append(",").append(location.getLongitude()).toString();
                    try
                    {
                   //     DriverHome.getInstance().updateTextView(sLocation);
//                        Toast.makeText(context,sLocation,Toast.LENGTH_SHORT).show();
                    }catch (Exception ex)
                    {
                        Toast.makeText(context,"" +ex,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}