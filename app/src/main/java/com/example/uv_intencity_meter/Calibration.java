package com.example.uv_intencity_meter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.anastr.speedviewlib.SpeedView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class Calibration extends Fragment implements View.OnClickListener {
    private TextView textView_wavelength, textView_realtimevalue, textView_value, textView_display, textView_status;
    private Button btn_transmit;
    private String wavelength, realtimevalue, value, displayvalue, sign;
    private TabLayout tabLayout;
    private TabLayout.Tab internal,external;
    public Calibration() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calibration, container, false);
        textView_wavelength = view.findViewById(R.id.Calibration_wlTextview);
        textView_realtimevalue = view.findViewById(R.id.Calibration_realtimeTextview);
        textView_value = view.findViewById(R.id.Calibration_valueTextview);
        textView_display = view.findViewById(R.id.Calibration_displayTextview);
        textView_status = view.findViewById(R.id.Calibration_statusTextview);
        btn_transmit = view.findViewById(R.id.Calibration_transmitButton);
        btn_transmit.setOnClickListener(this);

        tabLayout = view.findViewById(R.id.tab);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        if(((ContentActivity)getActivity()).mBluetoothLeService != null){
                            ((ContentActivity)getActivity()).sendDataToDevice("@TC1#");
                        }
                        textView_status.setText("Get value from Internal Sensor");
                        textView_wavelength.setText("0");
                        textView_realtimevalue.setText("0");
                        textView_value.setText("0");
                        textView_display.setText("0");
                        break;
                    case 1:
                        if(((ContentActivity)getActivity()).mBluetoothLeService != null){
                            ((ContentActivity)getActivity()).sendDataToDevice("@TC2#");
                        }
                        textView_status.setText("Get value from External Sensor");
                        textView_wavelength.setText("0");
                        textView_realtimevalue.setText("0");
                        textView_value.setText("0");
                        textView_display.setText("0");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    public void setTextChange(final String text) {
        //"@C111222+333#
        Log.d("####", text);
        String original;
        original = text;

        wavelength = original.substring(3, 6);
        realtimevalue = original.substring(6, 8) + "." + original.substring(8, 9);
        sign = original.substring(9, 10);
        value = original.substring(10, 12) + "." + original.substring(12, 13);

        Double calculate = 0.0;
        if(sign.equals("+")){
            calculate = Double.parseDouble(realtimevalue)+Double.parseDouble(value);
        }else if(sign.equals("-")){
            calculate = Double.parseDouble(realtimevalue)-Double.parseDouble(value);
        }
        displayvalue = String.format("%.1f",calculate);

        textView_wavelength.setText(wavelength);
        textView_realtimevalue.setText(realtimevalue);
        textView_value.setText(sign+value);
        textView_display.setText(displayvalue);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Calibration_transmitButton) {
            Calibration_transmitFragment transmitDialog = new Calibration_transmitFragment();
            transmitDialog.setTargetFragment(Calibration.this,0);
            transmitDialog.show(getFragmentManager(),"transmitDialog");
        }
    }
}
