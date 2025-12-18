package com.example.mnymng.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.mnymng.DB.AppDatabase;
import com.example.mnymng.DB.dao.NotificationDao;
import com.example.mnymng.DB.models.Notification;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NotificationViewModel extends AndroidViewModel {

    private final NotificationDao notificationDao;
//    private final LiveData<List<Notification>> allNotifications;
//    private final ExecutorService databaseWriteExecutor;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        notificationDao = db.notificationDao();
//        allNotifications = notificationDao.getAllNotifications();
//        databaseWriteExecutor = AppDatabase.databaseWriteExecutor;
    }

    public LiveData<List<Notification>> getAllNotifications() {
        return notificationDao.getAllNotifications();
    }

    public void insert(Notification notification) {
//        databaseWriteExecutor.execute(() -> {
//            notificationDao.insert(notification);
//        });
        notificationDao.insert(notification);
    }

    public void markAsRead(Notification notification) {
//        notification.setRead(true);
//        databaseWriteExecutor.execute(() -> {
//            notificationDao.update(notification);
//        });
        notificationDao.update(notification);
    }

    public void clearAllNotifications() {
        notificationDao.deleteAll();
    }

    public void deleteNotification(long notification) {
        notificationDao.delete(notification);
    }

    public void updateNotification(Notification notification) {
        notificationDao.update(notification);
    }
}
