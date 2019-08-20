package com.example.uv_intencity_meter;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;

import com.example.uv_intencity_meter.BLE.BluetoothLeService;
import com.example.uv_intencity_meter.BLE.SampleGattAttributes;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener {
    public static Context context;
    public ImageButton btn_bluetooth;
    private EditText password;
    private Button btn_error;
    private TextView textView_uv, textView_temp, textView_hum, textView_charge, mTextMessage;
    private String topbar_uv, topbar_temp, topbar_hum, topbar_charge, topbar_error, realtimeValue;
    private String sub1, sub2, sub3, sub4, sub5, sub6;

    private String mDeviceName;
    private String mDeviceAddress;
    public BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<>();
    private boolean mConnected = false;

    public static int num_activity = 0;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView navigation;
    private AlertDialog dialog, redialog, maindialog;

    private Measurement fragment_measure = new Measurement();
    private Analysis fragment_analysis = new Analysis();
    private Stats fragment_stats = new Stats();
    private Calibration fragment_calibration = new Calibration();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    byte sendByte[] = new byte[20];

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.Content_homeBottom:
                    maindialog.show();
                    break;
                case R.id.Content_measurementBottom:
                    num_activity = 0;
                    transaction.replace(R.id.Content_frameLayout, fragment_measure).commitAllowingStateLoss();
                    break;
                case R.id.Content_analysisBottom:
                    num_activity = 1;
                    transaction.replace(R.id.Content_frameLayout, fragment_analysis).commitAllowingStateLoss();
                    break;
                case R.id.Content_statsBottom:
                    num_activity = 2;
                    transaction.replace(R.id.Content_frameLayout, fragment_stats).commitAllowingStateLoss();
                    break;
                case R.id.Content_calibrationBottom:
                    dialog.show();
                    num_activity = 3;
                    transaction.replace(R.id.Content_frameLayout, fragment_calibration).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    };

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();

            if (!mBluetoothLeService.initialize()) {
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            Log.d("####", "Disconnect");

        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                Log.d("####", "Connected ");
                btn_bluetooth.setImageResource(R.drawable.ic_bluetooth_connected_black_24dp);
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());

                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // notification enable
                        final BluetoothGattCharacteristic characteristic = mGattCharacteristics.get(3).get(1);
                        mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                    }
                }, 1000);

            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {

                byte[] sendByte = intent.getByteArrayExtra("init");

                if ((sendByte[0] == 0x55) && (sendByte[1] == 0x33)) {
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // notification enable
                            final BluetoothGattCharacteristic characteristic = mGattCharacteristics.get(3).get(1);
                            mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                        }
                    }, 1000);
                }
                if ((sendByte[0] == 0x55) && (sendByte[1] == 0x03)) {
                    byte notifyValue = sendByte[2];

                    String receive;
                    receive = unHex(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                    Log.d("####", receive);

                    if (receive.startsWith("@") && receive.endsWith("#")) {
                        if (receive.substring(1, 2).equals("R")) {
                            sub1 = receive.substring(2, 5);
                            sub2 = receive.substring(5, 8);
                            sub3 = receive.substring(8, 11);
                            sub4 = receive.substring(11, 14);
                            sub5 = receive.substring(14, 15);
                            sub6 = receive.substring(15, 18);

                            topbar_uv = sub1;
                            topbar_temp = sub2.substring(0, 2) + "." + sub2.substring(2);
                            topbar_hum = sub3.substring(0, 2) + "." + sub3.substring(2);
                            topbar_charge = sub4;
                            topbar_error = sub5;
                            realtimeValue = sub6.substring(0, 2) + "." + sub6.substring(2);

                            textView_uv.setText(topbar_uv);
                            textView_temp.setText(topbar_temp);
                            textView_hum.setText(topbar_hum);

                            if (Integer.parseInt(topbar_charge) < 30)
                                textView_charge.setTextColor(getResources().getColor(android.R.color.holo_red_light));

                            if (topbar_error.equals("1"))
                                btn_error.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

                            if (num_activity == 0) {
                                fragment_measure = (Measurement) getSupportFragmentManager().findFragmentById(R.id.Content_frameLayout);
                                fragment_measure.setTextChange(realtimeValue);
                            } else if (num_activity == 1) {
                                fragment_analysis = (Analysis) getSupportFragmentManager().findFragmentById(R.id.Content_frameLayout);
                                fragment_analysis.setTextChange(realtimeValue);
                            }

                        }else if(receive.substring(1,2).equals("r")){
                            if(receive.substring(2,3).equals("1")){
                                if (num_activity == 0) {
                                    fragment_measure = (Measurement) getSupportFragmentManager().findFragmentById(R.id.Content_frameLayout);
                                    fragment_measure.setToggleButton("1");
                                } else if (num_activity == 1) {
                                    fragment_analysis = (Analysis) getSupportFragmentManager().findFragmentById(R.id.Content_frameLayout);
                                    fragment_analysis.setToggleButton("1");
                                }
                            }else if(receive.substring(2,3).equals("2")){
                                if (num_activity == 0) {
                                    fragment_measure = (Measurement) getSupportFragmentManager().findFragmentById(R.id.Content_frameLayout);
                                    fragment_measure.setToggleButton("2");
                                } else if (num_activity == 1) {
                                    fragment_analysis = (Analysis) getSupportFragmentManager().findFragmentById(R.id.Content_frameLayout);
                                    fragment_analysis.setToggleButton("2");
                                }
                            }

                            if(num_activity==3) {
                                fragment_calibration = (Calibration) getSupportFragmentManager().findFragmentById(R.id.Content_frameLayout);
                                fragment_calibration.setTextChange(receive);
                            }else{

                            }
                        }else{

                        }
                    }else{

                    }


                }
            }
        }
    };

    public static String unHex(String arg) {
        String str = "";
        for (int i = 0; i < arg.length(); i += 3) {
            String s = arg.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            str = str + (char) decimal;
        }
        return str;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null)
            mBluetoothLeService.connect(mDeviceAddress);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothLeService != null){
            unbindService(mServiceConnection);
            mBluetoothLeService = null;
        }
    }
    public void unbindBluetooth() {
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();

            Log.d("####", "service uuid : " + uuid);

            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                Log.d("####", "gattCharacteristic uuid : " + uuid);


                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        context = this;

        //Initialize BottomNavigation
        BottomNavigationView navView = findViewById(R.id.Content_bottomNavigation);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent contentIntent = new Intent(this.getIntent());
        num_activity = contentIntent.getIntExtra("Fragment", 0);
        navigation = findViewById(R.id.Content_bottomNavigation);

        //Initialize user interface contents
        btn_bluetooth = findViewById(R.id.Content_btButton);
        password = new EditText(this);

        topbar_uv = "0";
        topbar_temp = "0.0";
        topbar_hum = "0.0";
        realtimeValue = "0.0";

        textView_uv = findViewById(R.id.Content_uvTextview);
        textView_temp = findViewById(R.id.Content_tempTextview);
        textView_hum = findViewById(R.id.Content_humidTextview);
        textView_charge = findViewById(R.id.Content_chargeTextview);

        btn_error = findViewById(R.id.Content_errorButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog.Builder rebuilder = new AlertDialog.Builder(this);
        AlertDialog.Builder mainbuilder = new AlertDialog.Builder(this);

        //Calibration Password Alert Dialog
        dialog = builder.setTitle("Account Information")
                .setMessage("Please Enter your PASSWORD")
                .setView(password)
                .setPositiveButton("Access", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (password.getText().toString().length() > 0) {
                            if (password.getText().toString().equals("7620")) {
                                dialog.cancel();
                            } else {
                                redialog.show();
                                dialog.cancel();
                                password.setText("");
                            }
                        } else {
                            redialog.show();
                            dialog.cancel();
                            password.setText("");
                        }
                        password.setText("");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create();

        dialog.setCanceledOnTouchOutside(false);

        redialog = rebuilder.setTitle("Wrong password")
                .setMessage("Do you want to retry?")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        redialog.cancel();
                        dialog.show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setCancelable(false)
                .create();
        redialog.setCanceledOnTouchOutside(false);

        maindialog = mainbuilder.setTitle("Disconnect Bluetooth Connection")
                .setMessage("If you go to MAIN screen, the bluetooth connection will be broken")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .create();

        maindialog.setCanceledOnTouchOutside(false);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (num_activity) {
            case 0:
                fragmentTransaction.replace(R.id.Content_frameLayout, fragment_measure).commitAllowingStateLoss();
                navigation.setSelectedItemId(R.id.Content_measurementBottom);
                break;
            case 1:
                fragmentTransaction.replace(R.id.Content_frameLayout, fragment_analysis).commitAllowingStateLoss();
                navigation.setSelectedItemId(R.id.Content_analysisBottom);
                break;
            case 2:
                fragmentTransaction.replace(R.id.Content_frameLayout, fragment_stats).commitAllowingStateLoss();
                navigation.setSelectedItemId(R.id.Content_statsBottom);
                break;
            case 3:
                dialog.show();
                fragmentTransaction.replace(R.id.Content_frameLayout, fragment_calibration).commitAllowingStateLoss();
                navigation.setSelectedItemId(R.id.Content_calibrationBottom);
                break;
        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_bluetooth, null);
        final Switch switch_bluetooth = view.findViewById(R.id.Bluetooth_switch);
        btn_bluetooth.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mBluetoothLeService != null){
                    final BluetoothAdapter bluetoothAdapter = ((Bluetooth)Bluetooth.context).bluetoothAdapter;
                    switch_bluetooth.setChecked(false);
                    bluetoothAdapter.disable();
                    btn_bluetooth.setImageResource(R.drawable.ic_bluetooth_disabled_black_24dp);
                    Toast.makeText(ContentActivity.this,"Disabled Bluetooth Connection",Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        btn_bluetooth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Content_btButton:
                Intent blueIntent = new Intent(ContentActivity.this, Bluetooth.class);
                startActivityForResult(blueIntent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                mDeviceName = data.getStringExtra(EXTRAS_DEVICE_NAME);
                mDeviceAddress = data.getStringExtra(EXTRAS_DEVICE_ADDRESS);

                Intent gattServiceIntent = new Intent(ContentActivity.this, BluetoothLeService.class);
                bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
                Log.d("####", "On resume");

                Log.d("####", "Return form Bluetooth");
                if (num_activity == 3) {
                    dialog.hide();
                }
            }
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    public void sendDataToDevice(String data) {
        if (!mConnected) return;
        if (data.length() < 1) {
            Toast.makeText(ContentActivity.this, "Unable to send the data.", Toast.LENGTH_SHORT).show();
            return;
        } else if (data.length() > 20) {
            Toast.makeText(ContentActivity.this, "There are too much data.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mGattCharacteristics != null) {
            //Write Data
            final BluetoothGattCharacteristic characteristic = mGattCharacteristics.get(3).get(0);
            mBluetoothLeService.writeCharacteristics(characteristic, data);
        }
    }
}
