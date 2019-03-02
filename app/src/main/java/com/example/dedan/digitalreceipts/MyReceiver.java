package com.example.dedan.digitalreceipts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("meso", "receiver called");
        Toast.makeText(context.getApplicationContext(),"Please save pdf",Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences=context.getSharedPreferences("Data",Context.MODE_PRIVATE);
        int y=sharedPreferences.getInt("today",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("today",0);
        editor.putInt("yesterday",y);
        editor.apply();

    }
}
