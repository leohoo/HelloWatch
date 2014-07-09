package com.easyjp.hellowatch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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

        vibrator.vibrate(new long[] {0,50,200,50,200,50,400,100}, -1);

        String action = intent.getAction();

        Log.d("Connectivity Changed", action);

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        String newState = manager.getActiveNetworkInfo().getState().toString();

        Toast.makeText(context, newState, Toast.LENGTH_LONG).show();
    }
}
