package com.example.mnymng.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mnymng.R;
import com.example.mnymng.adapters.NotificationAdapter;
import com.example.mnymng.DB.models.Notification;
import com.example.mnymng.viewmodel.NotificationViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executors;

public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotificationAdapter();
        recyclerView.setAdapter(adapter);

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        notificationViewModel.getAllNotifications().observe(getViewLifecycleOwner(), notifications -> {
            adapter.setNotifications(notifications);
        });

        adapter.setOnItemClickListener(notification -> {
            NotificationDetailFragment.newInstance(notification).show(getChildFragmentManager(), "NotificationDetailFragment");
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Notification notification = adapter.getNotificationAt(position);
                notificationViewModel.markAsRead(notification);
                Snackbar.make(view, "Notification marked as read", Snackbar.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        Button clearAllButton = view.findViewById(R.id.button_clear_all);
        clearAllButton.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                notificationViewModel.clearAllNotifications();
                Snackbar.make(view, "All notifications cleared", Snackbar.LENGTH_LONG).show();
            });
        });

        return view;
    }
}
