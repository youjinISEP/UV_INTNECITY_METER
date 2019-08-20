package com.example.uv_intencity_meter;
/*
@ Name : MainActivity
@ Type : Activity
@ Usage : Initial page of the application.
          If press the buttons activity will be convert to ContentActivity and show each fragment.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private AlertDialog dialog;
    final private String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    final private int MY_PERMISSION = 512;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_measurement = findViewById(R.id.Measurement);
        Button btn_analysis = findViewById(R.id.Analysis);
        Button btn_stats = findViewById(R.id.Stats);
        Button btn_calibration = findViewById(R.id.Calibration);

        btn_measurement.setOnClickListener(this);
        btn_analysis.setOnClickListener(this);
        btn_stats.setOnClickListener(this);
        btn_calibration.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (ActivityCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, MY_PERMISSION);
                    break;
                }
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allGranted = true;
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                // toast 띄워주기
                Toast toast = Toast.makeText(this, "Must Allow the permission. ", Toast.LENGTH_LONG);
                toast.show();

                allGranted = false;
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.setTitle("Terminate Application")
                .setMessage("Do you want to close the application?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .create();

        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Measurement:
                intent = new Intent(MainActivity.this, ContentActivity.class);
                intent.putExtra("Fragment", 0);
                startActivity(intent);
                break;
            case R.id.Analysis:
                intent = new Intent(MainActivity.this, ContentActivity.class);
                intent.putExtra("Fragment", 1);
                startActivity(intent);
                break;
            case R.id.Stats:
                intent = new Intent(MainActivity.this, ContentActivity.class);
                intent.putExtra("Fragment", 2);
                startActivity(intent);
                break;
            case R.id.Calibration:
                intent = new Intent(MainActivity.this, ContentActivity.class);
                intent.putExtra("Fragment", 3);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        dialog.show();
    }


}
