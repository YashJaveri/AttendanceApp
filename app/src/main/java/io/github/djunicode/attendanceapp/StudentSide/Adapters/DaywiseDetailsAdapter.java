package io.github.djunicode.attendanceapp.StudentSide.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.djunicode.attendanceapp.R;
import io.github.djunicode.attendanceapp.StudentSide.Models.DaywiseDetailsModel;
import io.github.djunicode.attendanceapp.StudentSide.Models.SubjectAttendanceModel;

public class DaywiseDetailsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DaywiseDetailsModel> daywiseDetailsModelArrayList;

    public DaywiseDetailsAdapter(Context context, ArrayList<DaywiseDetailsModel> daywiseDetailsModelArrayList) {
        this.context = context;
        this.daywiseDetailsModelArrayList = daywiseDetailsModelArrayList;
    }

    @Override
    public int getCount() {
        return daywiseDetailsModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return daywiseDetailsModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DaywiseDetailsModel dam = daywiseDetailsModelArrayList.get(position);

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("ViewHolder") View item = inflater.inflate(R.layout.dateitem, parent, false);


        TextView dateView = item.findViewById(R.id.txt_date);
        TextView timeView = item.findViewById(R.id.txt_time);
        TextView statusView = item.findViewById(R.id.txt_status);

        if (dam.getStatus()) {
            statusView.setTextColor(context.getResources().getColor(R.color.green));
            statusView.setText("P");
        } else {
            statusView.setTextColor(context.getResources().getColor(R.color.errorWidgetColor));
            statusView.setText("A");
        }

        dateView.setText(dam.getDate());
        timeView.setText(dam.getTime());

        return item;
    }
}
