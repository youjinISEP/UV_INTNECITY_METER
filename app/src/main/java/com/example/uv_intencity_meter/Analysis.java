package com.example.uv_intencity_meter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.uv_intencity_meter.Database.ContentDAO;
import com.example.uv_intencity_meter.Database.ContentPoint5;
import com.example.uv_intencity_meter.Database.ContentPoint9;

import java.lang.ref.WeakReference;


public class Analysis extends Fragment implements View.OnClickListener {
    private Button btn_save, btn_reset, btn_calculate, btn_changePoint;
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private TableRow layout6, layout7, layout8, layout9;
    private TextView textView_point1Num, textView_point2Num, textView_point3Num, textView_point4Num, textView_point5Num, textView_point6Num, textView_point7Num, textView_point8Num, textView_point9Num;
    private TextView textView_point1Value, textView_point2Value, textView_point3Value, textView_point4Value, textView_point5Value, textView_point6Value, textView_point7Value, textView_point8Value, textView_point9Value;
    private TextView textView_uv, textView_avg, textView_uni;
    private EditText editText_title;
    private ToggleButton btn_type;

    private ContentDAO contentDAO;
    private ContentPoint5 contentPoint5 = null;
    private ContentPoint9 contentPoint9 = null;
    private AlertDialog dialog;
    private int status;
    private String topuv, toptemp, tophum;
    private Double Min, Max;
    private int TYPE;

    private Context context;

    public Analysis() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentDAO = new ContentDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        status = 9;

        topuv = "0.0";
        toptemp = "0.0";
        tophum = "0.0";

        Min = 1000.0;
        Max = 0.0;

        context = container.getContext();
        btn_type = view.findViewById(R.id.Analysis_typeButton);

        editText_title = new EditText(getActivity());
        textView_uv = view.findViewById(R.id.Analysis_uvTextview);
        textView_avg = view.findViewById(R.id.Analysis_avgTextview);
        textView_uni = view.findViewById(R.id.Analysis_uniTextview);

        layout6 = view.findViewById(R.id.row6);
        layout7 = view.findViewById(R.id.row7);
        layout8 = view.findViewById(R.id.row8);
        layout9 = view.findViewById(R.id.row9);

        textView_point1Num = view.findViewById(R.id.Analysis_point1Num);
        textView_point2Num = view.findViewById(R.id.Analysis_point2Num);
        textView_point3Num = view.findViewById(R.id.Analysis_point3Num);
        textView_point4Num = view.findViewById(R.id.Analysis_point4Num);
        textView_point5Num = view.findViewById(R.id.Analysis_point5Num);
        textView_point6Num = view.findViewById(R.id.Analysis_point6Num);
        textView_point7Num = view.findViewById(R.id.Analysis_point7Num);
        textView_point8Num = view.findViewById(R.id.Analysis_point8Num);
        textView_point9Num = view.findViewById(R.id.Analysis_point9Num);

        textView_point1Value = view.findViewById(R.id.Analysis_poin1Textview);
        textView_point2Value = view.findViewById(R.id.Analysis_poin2Textview);
        textView_point3Value = view.findViewById(R.id.Analysis_poin3Textview);
        textView_point4Value = view.findViewById(R.id.Analysis_poin4Textview);
        textView_point5Value = view.findViewById(R.id.Analysis_poin5Textview);
        textView_point6Value = view.findViewById(R.id.Analysis_poin6Textview);
        textView_point7Value = view.findViewById(R.id.Analysis_poin7Textview);
        textView_point8Value = view.findViewById(R.id.Analysis_poin8Textview);
        textView_point9Value = view.findViewById(R.id.Analysis_poin9Textview);

        btn_save = view.findViewById(R.id.Analysis_saveButton);
        btn_changePoint = view.findViewById(R.id.Analysis_pointButton);
        btn_calculate = view.findViewById(R.id.Analysis_calculateButton);
        btn_reset = view.findViewById(R.id.Analysis_resetButton);

        button1 = view.findViewById(R.id.Analysis_point1Button);
        button2 = view.findViewById(R.id.Analysis_point2Button);
        button3 = view.findViewById(R.id.Analysis_point3Button);
        button4 = view.findViewById(R.id.Analysis_point4Button);
        button5 = view.findViewById(R.id.Analysis_point5Button);
        button6 = view.findViewById(R.id.Analysis_point6Button);
        button7 = view.findViewById(R.id.Analysis_point7Button);
        button8 = view.findViewById(R.id.Analysis_point8Button);
        button9 = view.findViewById(R.id.Analysis_point9Button);

        btn_changePoint.setOnClickListener(this);
        btn_calculate.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialog = builder.setTitle("Title of the file")
                .setMessage("Write a editText_title of the file")
                .setView(editText_title)
                .setPositiveButton("Enroll", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText_title.getText().toString().length() > 0) {
                            if (status == 9) {
                                Thread ntask = new Thread(new SavingPoint9AnalysisThread(getActivity()));
                                ntask.start();
                            } else if (status == 5) {
                                Thread ftask = new Thread(new SavingPoint5AnalysisThread(getActivity()));
                                ftask.start();
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText_title.setText("");
                        dialog.cancel();
                    }
                })
                .create();
        dialog.setCanceledOnTouchOutside(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        btn_type.setChecked(false);
        if(((ContentActivity)getActivity()).mBluetoothLeService != null)
            btn_type.setOnClickListener(this);
    }

    public void setTextChange(final String text) {
        textView_uv.setText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Analysis_typeButton:
                if (btn_type.isChecked()) {
                    ((ContentActivity)getActivity()).sendDataToDevice("@T1#");
                }
                else {
                    ((ContentActivity)getActivity()).sendDataToDevice("@T2#");
                }
                break;
            case R.id.Analysis_saveButton:
                dialog.show();
                break;
            case R.id.Analysis_pointButton:
                if (status == 5) {
                    status = 9;
                    btn_changePoint.setText("CHANGE TO POINT : 5");
                    button3.setVisibility(View.VISIBLE);
                    button4.setText(Integer.toString(4));
                    button5.setVisibility(View.VISIBLE);
                    button6.setText(Integer.toString(6));
                    button7.setVisibility(View.VISIBLE);
                    button8.setText(Integer.toString(8));
                    button9.setVisibility(View.VISIBLE);

                    layout6.setVisibility(View.VISIBLE);
                    layout7.setVisibility(View.VISIBLE);
                    layout8.setVisibility(View.VISIBLE);
                    layout9.setVisibility(View.VISIBLE);

                    textView_point1Value.setText("0.0");
                    textView_point2Value.setText("0.0");
                    textView_point3Value.setText("0.0");
                    textView_point4Value.setText("0.0");
                    textView_point5Value.setText("0.0");
                    textView_point6Value.setText("0.0");
                    textView_point7Value.setText("0.0");
                    textView_point8Value.setText("0.0");
                    textView_point9Value.setText("0.0");

                    textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));

                    textView_avg.setText("0.0");
                    textView_uni.setText("0.0");

                    Min = 1000.0;
                    Max = 0.0;

                } else if (status == 9) {
                    status = 5;
                    btn_changePoint.setText("CHANGE TO POINT : 9");
                    button3.setVisibility(View.INVISIBLE);
                    button4.setText(Integer.toString(3));
                    button5.setVisibility(View.INVISIBLE);
                    button6.setText(Integer.toString(4));
                    button7.setVisibility(View.INVISIBLE);
                    button8.setText(Integer.toString(5));
                    button9.setVisibility(View.INVISIBLE);

                    layout6.setVisibility(View.INVISIBLE);
                    layout7.setVisibility(View.INVISIBLE);
                    layout8.setVisibility(View.INVISIBLE);
                    layout9.setVisibility(View.INVISIBLE);

                    textView_point1Value.setText("0.0");
                    textView_point2Value.setText("0.0");
                    textView_point3Value.setText("0.0");
                    textView_point4Value.setText("0.0");
                    textView_point5Value.setText("0.0");
                    textView_point6Value.setText("0.0");
                    textView_point7Value.setText("0.0");
                    textView_point8Value.setText("0.0");
                    textView_point9Value.setText("0.0");

                    textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));

                    textView_avg.setText("0.0");
                    textView_uni.setText("0.0");

                    Min = 1000.0;
                    Max = 0.0;
                }
                break;
            case R.id.Analysis_resetButton:
                textView_point1Value.setText("0.0");
                textView_point2Value.setText("0.0");
                textView_point3Value.setText("0.0");
                textView_point4Value.setText("0.0");
                textView_point5Value.setText("0.0");
                textView_point6Value.setText("0.0");
                textView_point7Value.setText("0.0");
                textView_point8Value.setText("0.0");
                textView_point9Value.setText("0.0");

                textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));

                textView_avg.setText("0.0");
                textView_uni.setText("0.0");

                Min = 1000.0;
                Max = 0.0;

                Toast.makeText(context,"RESET: reset all values",Toast.LENGTH_SHORT).show();

                break;
            case R.id.Analysis_calculateButton:
                Double sum, average, uniformity, divideTwo;

                average = 0.0;
                uniformity = 0.0;
                divideTwo = 0.0;

                if (status == 9) {
                    sum = Double.parseDouble(textView_point1Value.getText().toString()) +
                            Double.parseDouble(textView_point2Value.getText().toString()) +
                            Double.parseDouble(textView_point3Value.getText().toString()) +
                            Double.parseDouble(textView_point4Value.getText().toString()) +
                            Double.parseDouble(textView_point5Value.getText().toString()) +
                            Double.parseDouble(textView_point6Value.getText().toString()) +
                            Double.parseDouble(textView_point7Value.getText().toString()) +
                            Double.parseDouble(textView_point8Value.getText().toString()) +
                            Double.parseDouble(textView_point9Value.getText().toString());
                    average = sum / 9;
                    divideTwo = (Max-Min)/2;
                    uniformity = (divideTwo / average) * 100;
                } else if (status == 5) {
                    sum = Double.parseDouble(textView_point1Value.getText().toString()) +
                            Double.parseDouble(textView_point2Value.getText().toString()) +
                            Double.parseDouble(textView_point3Value.getText().toString()) +
                            Double.parseDouble(textView_point4Value.getText().toString()) +
                            Double.parseDouble(textView_point5Value.getText().toString());
                    average = sum / 5;
                    divideTwo = (Max-Min)/2;
                    uniformity = (divideTwo / average) * 100;
                }

                if(average == 0){
                    Toast.makeText(context,"Error: Can't divide by ZERO",Toast.LENGTH_SHORT).show();
                }else{
                    textView_avg.setText(String.format("%.1f",average));
                    textView_uni.setText(String.format("%.1f",uniformity));
                }

                break;
            case R.id.Analysis_point1Button:
                textView_point1Value.setText(textView_uv.getText());
                textView_point1Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));
                if (Double.parseDouble(textView_point1Value.getText().toString()) > Max)
                    Max = Double.parseDouble(textView_point1Value.getText().toString());
                if (Double.parseDouble(textView_point1Value.getText().toString()) < Min)
                    Min = Double.parseDouble(textView_point1Value.getText().toString());
                break;
            case R.id.Analysis_point2Button:
                textView_point2Value.setText(textView_uv.getText());
                textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point2Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));
                if (Double.parseDouble(textView_point2Value.getText().toString()) > Max)
                    Max = Double.parseDouble(textView_point2Value.getText().toString());
                if (Double.parseDouble(textView_point2Value.getText().toString()) < Min)
                    Min = Double.parseDouble(textView_point2Value.getText().toString());
                break;
            case R.id.Analysis_point3Button:
                textView_point3Value.setText(textView_uv.getText());
                textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point3Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));
                if (Double.parseDouble(textView_point3Value.getText().toString()) > Max)
                    Max = Double.parseDouble(textView_point3Value.getText().toString());
                if (Double.parseDouble(textView_point3Value.getText().toString()) < Min)
                    Min = Double.parseDouble(textView_point3Value.getText().toString());
                break;
            case R.id.Analysis_point4Button:
                if (status == 5) {
                    textView_point3Value.setText(textView_uv.getText());
                    textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point3Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                    if (Double.parseDouble(textView_point3Value.getText().toString()) > Max)
                        Max = Double.parseDouble(textView_point3Value.getText().toString());
                    if (Double.parseDouble(textView_point3Value.getText().toString()) < Min)
                        Min = Double.parseDouble(textView_point3Value.getText().toString());
                } else if (status == 9) {
                    textView_point4Value.setText(textView_uv.getText());
                    textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point4Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));
                    if (Double.parseDouble(textView_point4Value.getText().toString()) > Max)
                        Max = Double.parseDouble(textView_point4Value.getText().toString());
                    if (Double.parseDouble(textView_point4Value.getText().toString()) < Min)
                        Min = Double.parseDouble(textView_point4Value.getText().toString());
                }
                break;
            case R.id.Analysis_point5Button:
                textView_point5Value.setText(textView_uv.getText());
                textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point5Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));
                if (Double.parseDouble(textView_point5Value.getText().toString()) > Max)
                    Max = Double.parseDouble(textView_point5Value.getText().toString());
                if (Double.parseDouble(textView_point5Value.getText().toString()) < Min)
                    Min = Double.parseDouble(textView_point5Value.getText().toString());
                break;
            case R.id.Analysis_point6Button:
                if (status == 5) {
                    textView_point4Value.setText(textView_uv.getText());
                    textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point4Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                    if (Double.parseDouble(textView_point4Value.getText().toString()) > Max)
                        Max = Double.parseDouble(textView_point4Value.getText().toString());
                    if (Double.parseDouble(textView_point4Value.getText().toString()) < Min)
                        Min = Double.parseDouble(textView_point4Value.getText().toString());
                } else if (status == 9) {
                    textView_point6Value.setText(textView_uv.getText());
                    textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point5Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));
                    if (Double.parseDouble(textView_point6Value.getText().toString()) > Max)
                        Max = Double.parseDouble(textView_point6Value.getText().toString());
                    if (Double.parseDouble(textView_point6Value.getText().toString()) < Min)
                        Min = Double.parseDouble(textView_point6Value.getText().toString());
                }
                break;
            case R.id.Analysis_point7Button:
                textView_point7Value.setText(textView_uv.getText());
                textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point7Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));
                if (Double.parseDouble(textView_point7Value.getText().toString()) > Max)
                    Max = Double.parseDouble(textView_point7Value.getText().toString());
                if (Double.parseDouble(textView_point7Value.getText().toString()) < Min)
                    Min = Double.parseDouble(textView_point7Value.getText().toString());
                break;
            case R.id.Analysis_point8Button:
                if (status == 5) {
                    textView_point5Value.setText(textView_uv.getText());
                    textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point5Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    if (Double.parseDouble(textView_point5Value.getText().toString()) > Max)
                        Max = Double.parseDouble(textView_point5Value.getText().toString());
                    if (Double.parseDouble(textView_point5Value.getText().toString()) < Min)
                        Min = Double.parseDouble(textView_point5Value.getText().toString());
                } else if (status == 9) {
                    textView_point8Value.setText(textView_uv.getText());
                    textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                    textView_point8Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                    textView_point9Num.setTextColor(getResources().getColor(android.R.color.white));
                    if (Double.parseDouble(textView_point8Value.getText().toString()) > Max)
                        Max = Double.parseDouble(textView_point8Value.getText().toString());
                    if (Double.parseDouble(textView_point8Value.getText().toString()) < Min)
                        Min = Double.parseDouble(textView_point8Value.getText().toString());
                }
                break;
            case R.id.Analysis_point9Button:
                textView_point9Value.setText(textView_uv.getText());
                textView_point1Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point2Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point3Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point4Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point5Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point6Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point7Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point8Num.setTextColor(getResources().getColor(android.R.color.white));
                textView_point9Num.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
                if (Double.parseDouble(textView_point9Value.getText().toString()) > Max)
                    Max = Double.parseDouble(textView_point9Value.getText().toString());
                if (Double.parseDouble(textView_point9Value.getText().toString()) < Min)
                    Min = Double.parseDouble(textView_point9Value.getText().toString());
                break;
        }
    }

    private void setFiveContent() {
        contentPoint5 = new ContentPoint5();
        contentPoint5.setTitle(editText_title.getText().toString());
        contentPoint5.setUvTopbar(Double.parseDouble(topuv));
        contentPoint5.setTempTopbar(Double.parseDouble(toptemp));
        contentPoint5.setHumidTopbar(Double.parseDouble(tophum));
        contentPoint5.setA1(Double.parseDouble(textView_point1Value.getText().toString()));
        contentPoint5.setA2(Double.parseDouble(textView_point2Value.getText().toString()));
        contentPoint5.setA3(Double.parseDouble(textView_point3Value.getText().toString()));
        contentPoint5.setA4(Double.parseDouble(textView_point4Value.getText().toString()));
        contentPoint5.setA5(Double.parseDouble(textView_point5Value.getText().toString()));
        contentPoint5.setAvg(Double.parseDouble(textView_avg.getText().toString()));
        contentPoint5.setUni(Double.parseDouble(textView_uni.getText().toString()));
    }

    private void setNineContent() {
        Log.d("####", "setNineContent, " + editText_title.getText().toString());
        contentPoint9 = new ContentPoint9();
        contentPoint9.setTitle(editText_title.getText().toString());
        contentPoint9.setUvTopbar(Double.parseDouble(topuv));
        contentPoint9.setTempTopbar(Double.parseDouble(toptemp));
        contentPoint9.setHumidTopbar(Double.parseDouble(tophum));
        contentPoint9.setA1(Double.parseDouble(textView_point1Value.getText().toString()));
        contentPoint9.setA2(Double.parseDouble(textView_point2Value.getText().toString()));
        contentPoint9.setA3(Double.parseDouble(textView_point3Value.getText().toString()));
        contentPoint9.setA4(Double.parseDouble(textView_point4Value.getText().toString()));
        contentPoint9.setA5(Double.parseDouble(textView_point5Value.getText().toString()));
        contentPoint9.setA6(Double.parseDouble(textView_point6Value.getText().toString()));
        contentPoint9.setA7(Double.parseDouble(textView_point7Value.getText().toString()));
        contentPoint9.setA8(Double.parseDouble(textView_point8Value.getText().toString()));
        contentPoint9.setA9(Double.parseDouble(textView_point9Value.getText().toString()));
        contentPoint9.setAvg(Double.parseDouble(textView_avg.getText().toString()));
        contentPoint9.setUni(Double.parseDouble(textView_uni.getText().toString()));
    }

    public class SavingPoint5AnalysisThread implements Runnable {

        private final WeakReference<Activity> activityWeakReference;

        public SavingPoint5AnalysisThread(Activity context) {
            this.activityWeakReference = new WeakReference<>(context);
        }

        @Override
        public void run() {
            setFiveContent();
            long result = contentDAO.saveM(contentPoint5);

            if (activityWeakReference.get() != null
                    && !activityWeakReference.get().isFinishing()) {
                if (result != -1) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activityWeakReference.get(), "Analysis5 Page Content Saved",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            editText_title.setText("");
        }
    }

    public class SavingPoint9AnalysisThread implements Runnable {

        private final WeakReference<Activity> activityWeakReference;

        public SavingPoint9AnalysisThread(Activity context) {
            this.activityWeakReference = new WeakReference<>(context);
        }

        @Override
        public void run() {
            setNineContent();
            Log.d("####", "save DB 9, " + contentPoint9.getTitle());
            long result = contentDAO.saveA(contentPoint9);

            if (activityWeakReference.get() != null
                    && !activityWeakReference.get().isFinishing()) {
                if (result != -1) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activityWeakReference.get(), "Analysis9 Page Content Saved",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            editText_title.setText("");
        }
    }

    public void setToggleButton(final String text){
        if(text.equals("1"))
            btn_type.setChecked(false);
        else if(text.equals("2"))
            btn_type.setChecked(true);
    }
}

