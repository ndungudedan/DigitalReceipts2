package com.example.dedan.digitalreceipts;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class BTDeviceList extends ListActivity {
     public final int REQUEST_CONNECT_BT = 0 * 2300;
     private final int REQUEST_ENABLE_BT = 0 * 1000;
     private BluetoothAdapter bluetoothAdapter = null;
     private ArrayAdapter<String> arrayAdapter = null;
     private ArrayAdapter<BluetoothDevice> btDevices = null;
    private  final UUID SPP_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private static BluetoothSocket btSocket = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btdevice_list);
        setTitle("Bluetooth Devices");

        try {
            if (initDevicesList() != 0) {
                this.finish();
                return;
            }
        } catch (Exception e) {
            this.finish();
            return;
        }

        IntentFilter btintentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(BTReceiver, btintentFilter);
    }

    public static BluetoothSocket getSocket() {
        return btSocket;
    }

    private void flushData() {
        try {
            if (btSocket != null) {
                btSocket.close();
                btSocket = null;
            }
            if (bluetoothAdapter != null) {
                bluetoothAdapter.cancelDiscovery();
            }
            if (btDevices != null) {
                btDevices.clear();
                btDevices = null;
            }
            if (arrayAdapter != null) {
                arrayAdapter.clear();
                arrayAdapter.notifyDataSetChanged();
                arrayAdapter.notifyDataSetInvalidated();
                arrayAdapter = null;
            }
            finalize();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private int initDevicesList() {
        flushData();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Not Supported!", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        setListAdapter(arrayAdapter);

        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        try {
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } catch (Exception e) {
            return -2;
        }
        Toast.makeText(getApplicationContext(), "Getting all available BT devices!", Toast.LENGTH_SHORT).show();
        return 0;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    Set<BluetoothDevice> btDeviceList = bluetoothAdapter.getBondedDevices();
                    try {
                        if (btDeviceList.size() > 0) {
                            for (BluetoothDevice device : btDeviceList) {
                                if (btDeviceList.contains(device) == false) {
                                    btDevices.add(device);
                                    arrayAdapter.add(device.getName() + "\n" + device.getAddress());
                                    arrayAdapter.notifyDataSetInvalidated();
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }
                break;
        }
        bluetoothAdapter.startDiscovery();
    }

    private final BroadcastReceiver BTReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                try {
                    if (btDevices == null) {
                        btDevices = new ArrayAdapter<BluetoothDevice>(
                                getApplicationContext(), android.R.layout.simple_list_item_1);
                    }

                    if (btDevices.getPosition(device) < 0) {
                        btDevices.add(device);
                        arrayAdapter.add(device.getName() + "\n"
                                + device.getAddress() + "\n" );
                        arrayAdapter.notifyDataSetInvalidated();
                    }
                } catch (Exception ex) {
// ex.fillInStackTrace();
                }
            }
        }
    };
    @Override
    protected void onListItemClick(ListView l, View v, final int position,
                                   long id) {
        super.onListItemClick(l, v, position, id);

        if (bluetoothAdapter == null) {
            return;
        }

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        Toast.makeText(
                getApplicationContext(),
                "Connecting to " + btDevices.getItem(position).getName() + ","
        + btDevices.getItem(position).getAddress(),
                Toast.LENGTH_SHORT).show();

        Thread connectThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    boolean gotuuid = btDevices.getItem(position)
                            .fetchUuidsWithSdp();
                    UUID uuid = btDevices.getItem(position).getUuids()[0]
                            .getUuid();
                    btSocket = btDevices.getItem(position)
                            .createRfcommSocketToServiceRecord(uuid);

                    btSocket.connect();
                } catch (IOException ex) {
                    runOnUiThread(socketErrorRunnable);
                    try {
                        btSocket.close();
                    } catch (IOException e) {
// e.printStackTrace();
                    }
                    btSocket = null;
                    return;
                } finally {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            finish();

                        }
                    });
                }
            }
        });

        connectThread.start();
    }

    private Runnable socketErrorRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(),"Cannot establish connection", Toast.LENGTH_SHORT).show();
            BluetoothAdapter.getDefaultAdapter().startDiscovery();

        }
    };

}

