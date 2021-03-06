package com.easyjp.hellowatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by b03023 on 2014/07/09.
 */
public class ConnectivityChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        long vibConnected[] = new long[] {0, 50};
        long vibDisconnected[] = new long[] {0,50,200,50,200,50,400,100};

        String action = intent.getAction();

        Log.d("Connectivity Changed", action);

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        String newState = info!=null? info.getState().toString() : "DISCONNECTED";

        Toast.makeText(context, newState, Toast.LENGTH_LONG).show();

        if(newState.equals("DISCONNECTED")){
            vibrator.vibrate(vibDisconnected, -1);
        } else {
            vibrator.vibrate(vibConnected, -1);
        }
    }
}
