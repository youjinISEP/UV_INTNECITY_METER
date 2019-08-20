package com.example.uv_intencity_meter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uv_intencity_meter.Database.ContentDAO;
import com.example.uv_intencity_meter.Database.ContentPoint5;
import com.example.uv_intencity_meter.Database.ContentPoint9;
import com.example.uv_intencity_meter.Database.Point5ContListAdapter;
import com.example.uv_intencity_meter.Database.Point9ContListAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class Stats extends Fragment {
    private Activity activity;
    private ListView point5Listview, point9Listview;
    private ArrayList<ContentPoint5> list_content5;
    private ArrayList<ContentPoint9> list_content9;

    int rowPosition;
    Point5ContListAdapter point5ContListAdapter;
    Point9ContListAdapter point9ContListAdapter;
    ContentDAO contentDAO;

    private getfiveConTask ftask;
    private getnineConTask ntask;

    public Stats() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        contentDAO = new ContentDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats,container,false);
        point5Listview = view.findViewById(R.id.Analysis_point5Listview);
        point9Listview = view.findViewById(R.id.Analysis_point9Listview);

        ftask = new getfiveConTask(activity);
        ftask.execute((Void)null);

        ntask = new getnineConTask(activity);
        ntask.execute((Void) null);

        point5Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rowPosition = position;
                final ContentPoint5 contentPoint5 = (ContentPoint5)parent.getItemAtPosition(position);

                Analysis_5pointDialogFragment fiveDialog = new Analysis_5pointDialogFragment();
                fiveDialog.setTargetFragment(Stats.this,0);
                fiveDialog.show(getFragmentManager(),"5pointDialog");
                fiveDialog.setParameters(contentPoint5,point5ContListAdapter);
            }
        });

        point9Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rowPosition = position;
                final ContentPoint9 contentPoint9 = (ContentPoint9)parent.getItemAtPosition(position);

                Analysis_9pointDialogFragment nineDialog = new Analysis_9pointDialogFragment();
                nineDialog.setTargetFragment(Stats.this,0);
                nineDialog.show(getFragmentManager(), "9pointDialog");
                nineDialog.setParameters(contentPoint9, point9ContListAdapter);
            }
        });
        return view;
    }

    public class getfiveConTask extends AsyncTask<Void, Void, ArrayList<ContentPoint5>> {
        private final WeakReference<Activity> activityWeakReference;

        public getfiveConTask(Activity context) {
            this.activityWeakReference = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<ContentPoint5> doInBackground(Void... voids) {
            ArrayList<ContentPoint5> fList = contentDAO.getContentPoint5();
            return fList;
        }

        @Override
        protected void onPostExecute(final ArrayList<ContentPoint5> fList) {
            if (activityWeakReference.get() != null
                    && !activityWeakReference.get().isFinishing()) {
                Log.d("success", fList.toString());
                list_content5 = fList;

                if (fList.size() != 0) {
                    point5ContListAdapter = new Point5ContListAdapter(activity, fList);
                    point5Listview.setAdapter(point5ContListAdapter);
                } else
                    Toast.makeText(activity, "No Analysis5 Records", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class getnineConTask extends AsyncTask<Void, Void, ArrayList<ContentPoint9>> {
        private final WeakReference<Activity> activityWeakReference;

        public getnineConTask(Activity context) {
            this.activityWeakReference = new WeakReference<>(context);
        }

        @Override
        protected ArrayList<ContentPoint9> doInBackground(Void... voids) {
            ArrayList<ContentPoint9> nList = contentDAO.getContentPoint9();
            Log.d("####", "task background");
            return nList;
        }

        @Override
        protected void onPostExecute(final ArrayList<ContentPoint9> nList) {
            if (activityWeakReference.get() != null
                    && !activityWeakReference.get().isFinishing()) {
                Log.d("success", nList.toString());
                list_content9 = nList;

                if (nList.size() != 0) {
                    point9ContListAdapter = new Point9ContListAdapter(activity, nList);
                    point9Listview.setAdapter(point9ContListAdapter);
                } else
                    Toast.makeText(activity, "No Analysis9 Records", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
