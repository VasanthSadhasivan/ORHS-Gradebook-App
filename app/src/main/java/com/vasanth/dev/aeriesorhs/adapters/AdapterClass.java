package com.vasanth.dev.aeriesorhs.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vasanth.dev.aeriesorhs.activities.ClassActivity;
import com.vasanth.dev.aeriesorhs.objects.Class;
import com.vasanth.dev.aeriesorhs.R;

import java.util.ArrayList;

/**
 * Created by Admin on 11/27/2016.
 */

public class AdapterClass extends BaseAdapter {
    private Activity activity;
    private ArrayList<Class> classArrayList;
    private static LayoutInflater inflater = null;

    public AdapterClass(Activity activity, ArrayList<Class> _Class) {
        try {
            this.activity = activity;
            this.classArrayList = _Class;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return classArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return classArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView gradePercentage;
        public TextView gradeLetter;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        Log.v("AdapterClass", position+"");
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.class_row, parent, false);
            holder = new ViewHolder();
            holder.display_name = (TextView) convertView.findViewById(R.id.name);
            holder.gradePercentage = (TextView) convertView.findViewById(R.id.gradePercentage);
            holder.gradeLetter = (TextView) convertView.findViewById(R.id.gradeLetter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.display_name.setText(classArrayList.get(position).getName());
        holder.gradeLetter.setText(classArrayList.get(position).getGrade());
        holder.gradePercentage.setText(classArrayList.get(position).getPercentage()+"%");
        convertView.setBackgroundColor(classArrayList.get(position).getColor());
        final View vi = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vi.getContext(), ClassActivity.class);
                intent.putExtra("class_index", position);
                vi.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}