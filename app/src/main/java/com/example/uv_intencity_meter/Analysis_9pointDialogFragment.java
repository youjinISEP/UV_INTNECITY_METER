package com.example.uv_intencity_meter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uv_intencity_meter.Database.ContentDAO;
import com.example.uv_intencity_meter.Database.ContentPoint9;
import com.example.uv_intencity_meter.Database.Point9ContListAdapter;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Analysis_9pointDialogFragment extends DialogFragment {
   private View view;
   private ContentPoint9 contentPoint9;
   private Point9ContListAdapter point9ContListAdapter;
   private ContentDAO contentDAO;

    public Analysis_9pointDialogFragment() {
    }

    public void setParameters(ContentPoint9 contentPoint9, Point9ContListAdapter point9ContListAdapter){
        this.contentPoint9 = contentPoint9;
        this.point9ContListAdapter = point9ContListAdapter;
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
        view = inflater.inflate(R.layout.fragment_analysis_9point_dialog, null);

        TextView a_date = view.findViewById(R.id.alist_date);
        TextView a_uv = view.findViewById(R.id.alist_uvSensor);
        TextView a_temp = view.findViewById(R.id.alist_temp);
        TextView a_hum = view.findViewById(R.id.alist_hum);
        TextView a1 = view.findViewById(R.id.alit_a1);
        TextView a2 = view.findViewById(R.id.alit_a2);
        TextView a3 = view.findViewById(R.id.alit_a3);
        TextView a4 = view.findViewById(R.id.alit_a4);
        TextView a5 = view.findViewById(R.id.alit_a5);
        TextView a6 = view.findViewById(R.id.alit_a6);
        TextView a7 = view.findViewById(R.id.alit_a7);
        TextView a8 = view.findViewById(R.id.alit_a8);
        TextView a9 = view.findViewById(R.id.alit_a9);
        TextView a_avg = view.findViewById(R.id.alit_avg);
        TextView a_uni = view.findViewById(R.id.alit_uni);

        Date date_old = new Date(contentPoint9.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String date_temp = sdf.format(date_old);

        a_date.setText(" 1. date : " + date_temp);
        a_uv.setText(" 2. UV_sensor : " + contentPoint9.getUvTopbar());
        a_temp.setText(" 3. Temperature : " + contentPoint9.getTempTopbar());
        a_hum.setText(" 4. Humidity : " + contentPoint9.getHumidTopbar());
        a1.setText(" 5. point1 : " + contentPoint9.getA1());
        a2.setText(" 6. point2 : " + contentPoint9.getA2());
        a3.setText(" 7. point3 : " + contentPoint9.getA3());
        a4.setText(" 8. point4 : " + contentPoint9.getA4());
        a5.setText(" 9. point5 : " + contentPoint9.getA5());
        a6.setText(" 10. point6 : " + contentPoint9.getA6());
        a7.setText(" 11. point7 : " + contentPoint9.getA7());
        a8.setText(" 12. point8 : " + contentPoint9.getA8());
        a9.setText(" 13. point9 : " + contentPoint9.getA9());
        a_avg.setText(" 14. AVG : " + contentPoint9.getAvg());
        a_uni.setText(" 15. Uni : " + contentPoint9.getUni());

        builder.setView(view)
                .setTitle(contentPoint9.getTitle())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        point9ContListAdapter.remove(contentPoint9);
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
            long result = contentDAO.deleteA(contentPoint9);
            return result;
        }

        protected void onPostExecute(Long result) {
            if (activityWeakReference.get() != null &&
                    !activityWeakReference.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakReference.get(), "Analysis9 Page Content Delete", Toast.LENGTH_LONG).show();
            }
        }
    }
}
