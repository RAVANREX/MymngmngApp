package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mnymng.DB.models.Notification;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notification notification);

    @Update
    void update(Notification notification);

    @Query("SELECT * FROM notifications ORDER BY notification_timestamp DESC")
    LiveData<List<Notification>> getAllNotifications();

    @Query("SELECT * FROM notifications WHERE notification_is_read = 0 ORDER BY notification_timestamp DESC")
    LiveData<List<Notification>> getUnreadNotifications();

    @Query("UPDATE notifications SET notification_is_read = 1 WHERE notification_id = :notificationId")
    void markAsRead(long notificationId);

    @Query("DELETE FROM notifications")
    void deleteAll();

    @Query("DELETE FROM notifications WHERE notification_id = :notificationId")
    void delete(long notificationId);
}
