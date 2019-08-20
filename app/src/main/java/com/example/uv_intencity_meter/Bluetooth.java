package com.example.uv_intencity_meter;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uv_intencity_meter.BLE.BluetoothLeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bluetooth extends AppCompatActivity {
    public BluetoothAdapter bluetoothAdapter;
    private SimpleAdapter scanAdapter;
    List<Map<String, String>> scanDeviceData;
    public static Context context;
    private int REQUEST_ENABLE_BT = 1;

    TextView textView_deviceName;
    ListView scanningListview;
    public static Switch switch_bluetooth;
    Button btn_scanning;
    ImageButton btn_bluetooth;

    private Handler handler;
    private static final long SCAN_PERIOD = 5000;
    private boolean mScanning;
    private BluetoothLeService bluetoothLeService;
    public int reNumActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        //Initialize user interface items
        textView_deviceName = findViewById(R.id.Bluetooth_deviceTextview);
        scanningListview = findViewById(R.id.Bluetooth_pairedListview);
        switch_bluetooth = findViewById(R.id.Bluetooth_switch);
        btn_scanning = findViewById(R.id.Bluetooth_searchButton);
      //  btn_bluetooth = ((ContentActivity)ContentActivity.context).btn_bluetooth;
        context=this;

        //Check the device support BLE
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "The device doesn't support BLE", Toast.LENGTH_SHORT).show();
            finish();
        }

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        handler = new Handler();

        textView_deviceName.setText(bluetoothAdapter.getName());

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled())
            switch_bluetooth.setChecked(false);
        else
            switch_bluetooth.setChecked(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        scanDeviceData = new ArrayList<>();
        scanAdapter = new SimpleAdapter(this, scanDeviceData, android.R.layout.simple_list_item_2, new String[]{"name", "address"}, new int[]{android.R.id.text1, android.R.id.text2});
        scanningListview.setAdapter(scanAdapter);

        switch_bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    }
                } else {
                    bluetoothAdapter.disable();
                    btn_scanning.setEnabled(false);
                    scanDeviceData.clear();
                    scanAdapter.notifyDataSetChanged();
                   // btn_bluetooth.setImageResource(R.drawable.ic_bluetooth_disabled_black_24dp);
                }
            }
        });

        btn_scanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               scanLeDevice(true);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanDeviceData.clear();
        scanLeDevice(false);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    bluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
            mScanning = true;
            bluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
            } else {
                btn_scanning.setEnabled(true);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                private ArrayList<BluetoothDevice> scanDevices = new ArrayList<>();

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!scanDevices.contains(device)) {
                                scanDevices.add(device);
                                Map map = new HashMap();
                                if (device.getName() != null && device.getName().length() > 0)
                                    map.put("name", device.getName());
                                else
                                    map.put("name", "Unknown Device");
                                map.put("address", device.getAddress());
                                scanDeviceData.add(map);
                            }
                            scanAdapter.notifyDataSetChanged();
                        }
                    });

                    final AlertDialog.Builder builder = new AlertDialog.Builder(Bluetooth.this);
                    scanningListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           if(mScanning){
                               bluetoothAdapter.stopLeScan(mLeScanCallback);
                               mScanning = false;
                           }
                            String deviceAddress;
                            deviceAddress = scanDeviceData.get(position).get("address");
                            for (final BluetoothDevice tempDevice : scanDevices) {
                                if (deviceAddress.equals(tempDevice.getAddress())) {
                                    builder.setTitle("Bluetooth Connection")
                                            .setMessage("Do you accept to connect with" + deviceAddress)
                                            .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                   Intent intent = new Intent();
                                                   intent.putExtra(ContentActivity.EXTRAS_DEVICE_NAME, tempDevice.getName());
                                                   intent.putExtra(ContentActivity.EXTRAS_DEVICE_ADDRESS, tempDevice.getAddress());
                                                   setResult(RESULT_OK,intent);
                                                   finish();
                                                }
                                            });
                                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                                    break;
                                }
                            }
                        }
                    });
                }

            };



    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothAdapter.stopLeScan(mLeScanCallback);

    }

}
