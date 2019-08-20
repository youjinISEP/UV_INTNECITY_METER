package com.example.uv_intencity_meter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class Calibration_transmitFragment extends DialogFragment {

    private View view;
    private int select;

    public Calibration_transmitFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_calibration_transmit, null);

        final EditText editText_value = view.findViewById(R.id.Calibration_vtransmit);
        final EditText editText_wavelength = view.findViewById(R.id.Calibration_wvtransmit);
        builder.setView(view)
                .setTitle("Calibration : TRANSMIT")
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sendValue = editText_value.getText().toString();
                        String waveValue = editText_wavelength.getText().toString();
                        String sendData = "@w"+sendValue+waveValue+select+"#";
                        ((ContentActivity)getActivity()).sendDataToDevice(sendData);
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }



}
