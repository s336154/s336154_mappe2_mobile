package com.example.s336154_mappe2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MeetingCustomBaseAdapter extends BaseAdapter {
    private final Context context;
    private final List<Meeting> meetings;
    LayoutInflater inflater;

    public MeetingCustomBaseAdapter(Context context, List<Meeting> meetings) {
        super();
        this.context = context;
        this.meetings = meetings;
        inflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return meetings.size();
    }

    @Override
    // Add a method to get an item at a specific position
    public Meeting getItem(int position) {
        if (position >= 0 && position < meetings.size()) {
            return meetings.get(position);
        }
        return null; // Handle out-of-bounds positions
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void add(Meeting meeting) {
        meetings.clear();
        meetings.add(meeting);
        notifyDataSetChanged();
    }

    public void remove(Meeting meeting) {
        meetings.clear();
        meetings.remove(meeting);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.list_meeting_layout, null);


        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
        TextView timeView = (TextView) convertView.findViewById(R.id.timeView);
        TextView dateView = (TextView) convertView.findViewById(R.id.dateView);
        TextView placeView = (TextView) convertView.findViewById(R.id.placeView);
        TextView commentView = (TextView) convertView.findViewById(R.id.commentView);


        iconImageView.setImageResource(R.drawable.img_2);

        Meeting meeting = meetings.get(position);

        Log.d("MeetingCustomAdapter", "Time: " + meeting.getTime());
        Log.d("MeetingCustomAdapter", "Date: " + meeting.getDate());
        Log.d("MeetingCustomAdapter", "Place: " + meeting.getPlace());
        Log.d("MeetingCustomAdapter", "Comment: " + meeting.getComment());



        timeView.setText(meeting.getTime());
        dateView.setText(meeting.getDate());
        placeView.setText(meeting.getPlace());
        commentView.setText(meeting.getComment());

        return convertView;
    }
}
