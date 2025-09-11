package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mnymng.DB.models.FuturePlan;

import java.util.List;

@Dao
public interface FuturePlanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FuturePlan futurePlan);

    @Update
    void update(FuturePlan futurePlan);

    @Delete
    void delete(FuturePlan futurePlan);

    @Query("SELECT * FROM future_plan WHERE fplan_id = :planId")
    LiveData<FuturePlan> getFuturePlanById(long planId);

    @Query("SELECT * FROM future_plan ORDER BY fplan_tgdt ASC")
    LiveData<List<FuturePlan>> getAllFuturePlans();

    @Query("SELECT * FROM future_plan WHERE fplan_status = :status ORDER BY fplan_tgdt ASC")
    LiveData<List<FuturePlan>> getFuturePlansByStatus(String status); // Consider using PlanStatus enum if possible with a TypeConverter
}
