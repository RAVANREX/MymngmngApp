package com.example.mnymng.DB.models;

import androidx.room.ColumnInfo;

public class CategorySpending {

    @ColumnInfo(name = "cata_name")
    public String categoryName;

    @ColumnInfo(name = "total")
    public Double totalAmount;

    public Double getTotal() {
        return totalAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
