package com.example.uv_intencity_meter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.anastr.speedviewlib.SpeedView;

public class Measurement extends Fragment implements View.OnClickListener {

    private TextView textView_time;
    private EditText editView_energy;
    private SpeedView speedView;
    private Float calculateTime;
    private Context context;
    private ToggleButton btn_type;

    public Measurement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measurement, container, false);
        speedView = view.findViewById(R.id.Measurement_speedView);
        Button btn_calculate = view.findViewById(R.id.Measurement_calculateButton);
        Button btn_reset = view.findViewById(R.id.Measurement_resetButton);
        textView_time = view.findViewById(R.id.Measurement_timeValue);
        editView_energy = view.findViewById(R.id.Measurement_energyEdit);
        btn_type = view.findViewById(R.id.Measurment_typeButton);
        context = container.getContext();
        speedView.setWithTremble(false);

        btn_calculate.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        btn_type.setChecked(false);
        if(((ContentActivity)getActivity()).mBluetoothLeService != null)
            btn_type.setOnClickListener(this);

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Measurement_calculateButton:
                if (speedView.getSpeed() == 0.0) {
                    Toast.makeText(context, "Error: Can't divide by ZERO", Toast.LENGTH_SHORT).show();
                } else {
                    calculateTime = Float.parseFloat(editView_energy.getText().toString()) / speedView.getSpeed();
                    textView_time.setText(String.format("%.1f",calculateTime));
                }
                break;
            case R.id.Measurement_resetButton:
                textView_time.setText("0.0");
                editView_energy.setText("0");
                Toast.makeText(context, "RESET : reset all values", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Measurment_typeButton:
                if (btn_type.isChecked()) {
                    ((ContentActivity)getActivity()).sendDataToDevice("@T1#");
                }
                else {
                    ((ContentActivity)getActivity()).sendDataToDevice("@T2#");
                }
                break;
        }
    }

    public void setTextChange(final String text) {
        speedView.setSpeedAt(Float.parseFloat(text));
        speedView.setWithTremble(false);
    }

    public void setToggleButton(final String text){
        if(text.equals("1"))
            btn_type.setChecked(false);
        else if(text.equals("2"))
            btn_type.setChecked(true);
    }
}
