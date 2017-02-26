package com.kiit.klx.Servicenbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kiit.klx.Utils.NetworkUtil;

public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {

        if (!intent.getAction().equals(ACTION)) {            //Executed when phone boots up
            String status = NetworkUtil.getConnectivityStatusString(context);       //Gets the network status

            if (status.equals("Wifi enabled") || status.equals("Mobile data enabled")) {
                Intent serviceIntent = new Intent(context, MyService.class);        //Starts the service whenever connected
                context.startService(serviceIntent);
            }
            if (status.equals("Not connected to Internet")) {
                Intent serviceIntent = new Intent(context, MyService.class);
                context.stopService(serviceIntent);                                 //Stops the service whenever disconnected
            }

        }
    }
}