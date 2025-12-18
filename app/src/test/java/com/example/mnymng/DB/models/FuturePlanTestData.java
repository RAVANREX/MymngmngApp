package com.example.mnymng.DB.models;

import com.example.mnymng.DB.enums.PlanPriority;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FuturePlanTestData {

    public static List<FuturePlan> getFuturePlans() {
        List<FuturePlan> futurePlans = new ArrayList<>();
        long currentTime = new Date().getTime();

        // Assuming constructor: FuturePlan(Long id, String planName, double targetAmount, double currentSavedAmount, Date targetDate, PlanPriority priority, String notes, Date createdAt, Long accountId, Long categoryId)
        // accountId might be the account where savings are held, categoryId for the goal type (e.g., travel, electronics)


        return futurePlans;
    }
}
