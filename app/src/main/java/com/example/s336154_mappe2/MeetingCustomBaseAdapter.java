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
    private MeetingAdapter meetingAdapter;
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
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.list_meeting_layout, null);

        //   inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //  View rowView = inflater.inflate(R.layout.list_contact_layout, parent, false);

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
        TextView timeView = (TextView) convertView.findViewById(R.id.timeView);
        TextView dateView = (TextView) convertView.findViewById(R.id.dateView);
        TextView placeView = (TextView) convertView.findViewById(R.id.placeView);
        TextView commentView = (TextView) convertView.findViewById(R.id.commentView);


        // Set the icon image (you can load it from a resource or URL)
        iconImageView.setImageResource(R.drawable.img_4);

        Meeting meeting = meetings.get(position);

        Log.d("MeetingCustomAdapter", "Time: " + meeting.getTime());
        Log.d("MeetingCustomAdapter", "Date: " + meeting.getDate());
        Log.d("MeetingCustomAdapter", "Place: " + meeting.getPlace());
        Log.d("MeetingCustomAdapter", "Comment: " + meeting.getComment());

        // Set the text for the item

        timeView.setText(meeting.getTime());
        dateView.setText(meeting.getDate());
        placeView.setText(meeting.getPlace());
        commentView.setText(meeting.getComment());

        return convertView;
    }
}
