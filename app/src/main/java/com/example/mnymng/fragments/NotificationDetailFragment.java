package com.example.mnymng.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mnymng.R;
import com.example.mnymng.DB.models.Notification;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotificationDetailFragment extends DialogFragment {

    private static final String ARG_NOTIFICATION = "notification";

    public static NotificationDetailFragment newInstance(Notification notification) {
        NotificationDetailFragment fragment = new NotificationDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTIFICATION, notification);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = view.findViewById(R.id.notification_title);
        TextView timestamp = view.findViewById(R.id.notification_timestamp);

        if (getArguments() != null) {
            Notification notification = (Notification) getArguments().getSerializable(ARG_NOTIFICATION);
            if (notification != null) {
                title.setText(notification.getTitle());
                timestamp.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(String.valueOf(notification.getTimestamp()))));
            }
        }
    }
}
