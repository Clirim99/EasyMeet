package com.example.easymeet.utility;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.easymeet.R;
import com.example.easymeet.model.Event;
import com.example.easymeet.model.EventRow;
import com.example.easymeet.model.ProfileData;
import com.example.easymeet.repository.ProfileDataRepository;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {
    public ArrayList<Event> events = new ArrayList<>();

    public ArrayList<Event> eventsJoined = new ArrayList<>();
    public CustomListener customListener;

    public GotoEventsListener gotoEventsListener ;
    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);  // Return the event object itself
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_event, viewGroup, false);
        EventRow eventRow = new EventRow(view);
        view.setTag(eventRow);

        Event event = events.get(position);
        eventRow.eventName.setText(event.eventName);

        // Convert eventCreator (assuming it's an ID or some non-string value) to String
        ProfileData profileData = ProfileDataRepository.getProfileDataByUserId(view.getContext(), event.eventCreator);
        eventRow.eventCreator.setText(String.valueOf(profileData.getUsername()));

        // Convert numMembers (assuming it's an integer) to String
        eventRow.numMembers.setText(String.valueOf(event.numMembers));

        eventRow.eventTime.setText(event.eventTime);

        if(event.eventCreator == SessionManager.getUserId(view.getContext())){
            eventRow.joinBtn.setVisibility(View.INVISIBLE);
        }
        if(eventsJoined.contains(event)){
            eventRow.joinBtn.setText("Cancel");
        }else{
            eventRow.joinBtn.setText("Join");
        }
        eventRow.joinBtn.setOnClickListener(v -> {
            customListener.joinListener(event);
        });
        view.setOnClickListener(v -> {
            gotoEventsListener.listener(event);
        });


        return view;
    }

    public interface CustomListener {
       // void listener(Event event);
        void joinListener(Event event);
    }
    public interface GotoEventsListener {
        // void listener(Event event);
        void listener(Event event);
    }

}
