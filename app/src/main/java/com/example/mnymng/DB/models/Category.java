package com.example.mnymng.DB.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import com.example.mnymng.DB.enums.CategoryType;

import java.io.Serializable; // Added import

@Entity(tableName = "categories")
public class Category implements Serializable { // Added implements Serializable
    @PrimaryKey(autoGenerate = true)
    public long cata_id;

    @ColumnInfo(name = "cata_name")
    public String cata_name;
    @ColumnInfo(name = "cata_type")
    public CategoryType cata_type; // TODO: Replace with Enum (Income/Expense/Trip/Insurance/Investment) -> Done

    @ColumnInfo(name = "cata_icon")
    public String cata_icon;

    @ColumnInfo(name = "cata_desc")
    public String cata_desc;
    @ColumnInfo(name = "cata_value1")
    public String cata_value1;
    @ColumnInfo(name = "cata_value2")
    public String cata_value2;
    @ColumnInfo(name = "cata_value3")
    public String cata_value3;
    @ColumnInfo(name = "cata_value4")
    public String cata_value4;
    @ColumnInfo(name = "cata_value5")
    public String cata_value5;

    public long getCata_id() {
        return cata_id;
    }

    public void setCata_id(long cata_id) {
        this.cata_id = cata_id;
    }

    public String getCata_name() {
        return cata_name;
    }

    public void setCata_name(String cata_name) {
        this.cata_name = cata_name;
    }

    public CategoryType getCata_type() {
        return cata_type;
    }

    public void setCata_type(CategoryType cata_type) {
        this.cata_type = cata_type;
    }

    public String getCata_icon() {
        return cata_icon;
    }

    public void setCata_icon(String cata_icon) {
        this.cata_icon = cata_icon;
    }

    public String getCata_desc() {
        return cata_desc;
    }

    public void setCata_desc(String cata_desc) {
        this.cata_desc = cata_desc;
    }

    public String getCata_value1() {
        return cata_value1;
    }

    public void setCata_value1(String cata_value1) {
        this.cata_value1 = cata_value1;
    }

    public String getCata_value2() {
        return cata_value2;
    }

    public void setCata_value2(String cata_value2) {
        this.cata_value2 = cata_value2;
    }

    public String getCata_value3() {
        return cata_value3;
    }

    public void setCata_value3(String cata_value3) {
        this.cata_value3 = cata_value3;
    }

    public String getCata_value4() {
        return cata_value4;
    }

    public void setCata_value4(String cata_value4) {
        this.cata_value4 = cata_value4;
    }

    public String getCata_value5() {
        return cata_value5;
    }

    public void setCata_value5(String cata_value5) {
        this.cata_value5 = cata_value5;
    }

    public Category( String cata_name, CategoryType cata_type, String cata_icon, String cata_desc) {
        this.cata_name = cata_name;
        this.cata_type = cata_type;
        this.cata_icon = cata_icon;
        this.cata_desc = cata_desc;
    }

    @Ignore
    public Category(long cata_id, String cata_name, CategoryType cata_type, String cata_icon, String cata_desc) {
        this.cata_id = cata_id;
        this.cata_name = cata_name;
        this.cata_type = cata_type;
        this.cata_icon = cata_icon;
        this.cata_desc = cata_desc;
    }
}
