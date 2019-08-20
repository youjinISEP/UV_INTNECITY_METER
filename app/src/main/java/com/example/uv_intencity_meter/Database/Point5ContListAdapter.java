package com.example.uv_intencity_meter.Database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.uv_intencity_meter.R;

import java.util.List;

public class Point5ContListAdapter extends ArrayAdapter<ContentPoint5> {

    private Context context;
    List<ContentPoint5> contentList;

    public Point5ContListAdapter(Context context, List<ContentPoint5> contentList) {
        super(context, R.layout.list_item, contentList);
        this.context = context;
        this.contentList = contentList;
    }

    private class ViewHolder {
        TextView mTitle;
    }

    public int getCount() {
        return contentList.size();
    }

    public ContentPoint5 getItem(int position) {
        return contentList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            Log.d("####", "if null, point5 save");
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.mTitle = convertView.findViewById(R.id.mtitle);

            convertView.setTag(holder);
        } else {
            Log.d("####", "not null, point5 save");

            holder = (ViewHolder) convertView.getTag();
        }
        Log.d("####", "point5 finish");


        ContentPoint5 content = getItem(position);
        holder.mTitle.setText(content.getTitle() + "");
        Log.d("####", "size: " + contentList.size()+ "    position :" + position);
        Log.d("####", "5:" + content.getTitle());

        return convertView;
    }

    public void add(ContentPoint5 content) {
        contentList.add(content);
        notifyDataSetChanged();
        super.add(content);
    }

    public void remove(ContentPoint5 content) {
        contentList.remove(content);
        notifyDataSetChanged();
        super.remove(content);
    }
}
