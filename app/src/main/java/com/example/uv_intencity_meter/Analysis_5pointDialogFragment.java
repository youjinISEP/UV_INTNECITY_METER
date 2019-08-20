package com.example.uv_intencity_meter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.uv_intencity_meter.Database.ContentDAO;
import com.example.uv_intencity_meter.Database.ContentPoint5;
import com.example.uv_intencity_meter.Database.Point5ContListAdapter;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Analysis_5pointDialogFragment extends DialogFragment {

    private View view;
    private ContentPoint5 contentPoint5;
    private Point5ContListAdapter point5ContListAdapter;
    private ContentDAO contentDAO;

    public Analysis_5pointDialogFragment() {
    }

    public void setParameters(ContentPoint5 contentPoint5, Point5ContListAdapter point5ContListAdapter){
        this.contentPoint5 = contentPoint5;
        this.point5ContListAdapter = point5ContListAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        contentDAO = new ContentDAO(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_analysis_5point_dialog, null);

        TextView date = view.findViewById(R.id.mlist_date);
        TextView uv_sensor = view.findViewById(R.id.mlist_uvSensor);
        TextView temp = view.findViewById(R.id.mlist_temp);
        TextView hum = view.findViewById(R.id.mlist_hum);
        TextView f1 = view.findViewById(R.id.mlit_m1);
        TextView f2 = view.findViewById(R.id.mlit_m2);
        TextView f3 = view.findViewById(R.id.mlit_m3);
        TextView f4 = view.findViewById(R.id.mlit_m4);
        TextView f5 = view.findViewById(R.id.mlit_m5);
        TextView avg = view.findViewById(R.id.point_avg);
        TextView uni = view.findViewById(R.id.point_uni);

        Date date_old = new Date(contentPoint5.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String date_temp = sdf.format(date_old);

        date.setText(" 1. date : " + date_temp);
        uv_sensor.setText(" 2. UV_sensor : " + contentPoint5.getUvTopbar());
        temp.setText(" 3. Temperature : " + contentPoint5.getTempTopbar());
        hum.setText(" 4. Humidity : " + contentPoint5.getHumidTopbar());
        f1.setText(" 5. point1 : " + contentPoint5.getA1());
        f2.setText(" 6. point2 : " + contentPoint5.getA2());
        f3.setText(" 7. point3 : " + contentPoint5.getA3());
        f4.setText(" 8. point4 : " + contentPoint5.getA4());
        f5.setText(" 9. point5 : " + contentPoint5.getA5());
        avg.setText(" 10. AVG : " + contentPoint5.getAvg());
        uni.setText(" 11. Uni : " + contentPoint5.getUni());

        builder.setView(view)
                .setTitle(contentPoint5.getTitle())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        point5ContListAdapter.remove(contentPoint5);
                        DeleteTask task = new DeleteTask(getActivity());
                        task.execute((Void) null);
                    }
                });

        return builder.create();
    }

    public class DeleteTask extends AsyncTask<Void, Void, Long> {
        private final WeakReference<Activity> activityWeakReference;

        public DeleteTask(Activity context) {
            this.activityWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Long doInBackground(Void... voids) {
            long result = contentDAO.deleteM(contentPoint5);
            return result;
        }

        protected void onPostExecute(Long result) {
            if (activityWeakReference.get() != null &&
                    !activityWeakReference.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakReference.get(), "Measurement Page Content Delete", Toast.LENGTH_LONG).show();
            }
        }
    }

}
