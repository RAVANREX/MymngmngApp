package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.TypeConverters;

import com.example.mnymng.DB.models.Category;
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.enums.CategoryTypeConverter;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM categories WHERE cata_id = :categoryId")
    LiveData<Category> getCategoryById(long categoryId);

    @Query("SELECT * FROM categories ORDER BY cata_name ASC")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM categories WHERE cata_type = :categoryType ORDER BY cata_name ASC")
    @TypeConverters(CategoryTypeConverter.class)
    LiveData<List<Category>> getCategoriesByType(CategoryType categoryType);
}
