package com.example.mnymng.DB.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mnymng.DB.utils.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "notifications")
@TypeConverters(DateConverter.class)
public class Notification implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notification_id")
    public long notification_id;

    @ColumnInfo(name = "notification_title")
    public String title;

    @ColumnInfo(name = "notification_message")
    public String message;

    @ColumnInfo(name = "notification_timestamp")
    public Date timestamp;

    @ColumnInfo(name = "notification_is_read")
    public boolean isRead;

    @Ignore
    public Notification() {
    }

    public Notification(String title, String message, Date timestamp, boolean isRead) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public Notification(String testNotification, long l) {
    }

    public long getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(long notification_id) {
        this.notification_id = notification_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
