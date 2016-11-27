package com.vasanth.dev.aeriesorhs.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vasanth.dev.aeriesorhs.R;
import com.vasanth.dev.aeriesorhs.objects.Assignment;

import java.util.ArrayList;

import static android.graphics.Color.GRAY;

/**
 * Created by Admin on 11/27/2016.
 */

public class AdapterAssignment extends BaseAdapter {
    private Activity activity;
    private ArrayList<Assignment> assignmentArrayList;
    private static LayoutInflater inflater = null;
    private int color;

    public AdapterAssignment(Activity activity, ArrayList<Assignment> assignmentArrayList, int color) {
        try {
            this.activity = activity;
            this.assignmentArrayList = assignmentArrayList;
            this.color = color;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {
            Log.e("ERROR", "ERROR");
            e.printStackTrace();
        }
    }

    public int getCount() {
        return assignmentArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return assignmentArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView outOf;
        public TextView percentage;
        public TextView graded;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final AdapterAssignment.ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.assignment_row, parent, false);
            holder = new AdapterAssignment.ViewHolder();
            holder.display_name = (TextView) convertView.findViewById(R.id.assignmentName);
            holder.outOf = (TextView) convertView.findViewById(R.id.assignmentOutOf);
            holder.percentage = (TextView) convertView.findViewById(R.id.assignmentPercentage);
            holder.graded = (TextView) convertView.findViewById(R.id.assignmentIsGraded);

            convertView.setTag(holder);
        } else {
            holder = (AdapterAssignment.ViewHolder) convertView.getTag();
        }
        convertView.setBackgroundColor(color);
        holder.display_name.setText(assignmentArrayList.get(position).getName());
        holder.outOf.setText(assignmentArrayList.get(position).getWhatYouGot()+"/"+assignmentArrayList.get(position).getTotal());
        holder.percentage.setText(assignmentArrayList.get(position).getPercentage()*100+"%");
        if(assignmentArrayList.get(position).isCounted()) {
            holder.graded.setText("Yes");
            convertView.setAlpha(1f);
        }
        else {
            holder.graded.setText("No");
            convertView.setBackgroundColor(GRAY);
        }
        final View vi = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(vi.getContext(), ClassActivity.class);
                //intent.putExtra("class_index", position);
                //vi.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
    public static int darkenColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }
}
