package com.example.mnymng.DB.enums;

import androidx.room.TypeConverter;

public class CategoryTypeConverter {
    @TypeConverter
    public static String fromCategoryType(CategoryType categoryType) {
        return categoryType == null ? null : categoryType.name();
    }

    @TypeConverter
    public static CategoryType toCategoryType(String categoryTypeName) {
        return categoryTypeName == null ? null : CategoryType.valueOf(categoryTypeName);
    }
}