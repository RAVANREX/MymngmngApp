package com.example.mnymng.DB.logic;

import androidx.fragment.app.Fragment;

import com.example.mnymng.DB.AppDatabase;
import com.example.mnymng.DB.dao.RecurringDao;
import com.example.mnymng.DB.dao.TransactionDao;
import com.example.mnymng.DB.models.Recurring;
import com.example.mnymng.DB.models.Transaction;
// Import your Recurring and Transaction entities here
// import com.example.mnymng.model.Recurring;
// import com.example.mnymng.model.Transaction;
// Import your DAO for Recurring and Transaction entities here
// import com.example.mnymng.DB.dao.RecurringDao;
// import com.example.mnymng.DB.dao.TransactionDao;

import java.util.Date;
import java.util.List;

/**
 * Manages database operations on application start.
 */
public class DbOperationOnStart extends Fragment {

     private RecurringDao recurringDao;
     private TransactionDao transactionDao;

    public DbOperationOnStart(AppDatabase db) {
         this.recurringDao = db.recurringDao(); // Initialize your DAO
         this.transactionDao = db.transactionDao(); // Initialize your DAO
    }

    /**
     * 1. Fetches recurring transactions where recurring is completed
     * (recurring_edt < recurring_nxt_duedt) and deletes them.
     *
     * Note: The condition recurring_edt < recurring_nxt_duedt seems to imply that
     * the end date is before the next due date. Typically, a recurring task is completed
     * if the current date is past the recurring_edt OR if recurring_nxt_duedt is past recurring_edt.
     * Please verify the intended logic for "completed".
     * Assuming "completed" means the end date (recurring_edt) has passed.
     */
    public void deleteCompletedRecurringTransactions() {
//         Date currentDate = new Date();
//         List<Recurring> completedRecurring = recurringDao.getCompletedRecurring(); // Example DAO method
//         for (Recurring recurring : completedRecurring) {
//             recurringDao.delete(recurring);
//         }
//         System.out.println("Deleted " + completedRecurring.size() + " completed recurring transactions.");
        // TODO: Implement actual database logic
        // Example: Get all recurring items where recurring_edt < today_date and recurring_nxt_duedt is also past recurring_edt or irrelevant
         List<Recurring> completed = recurringDao.getCompletedRecurring();
         //recurringDao.deleteAll(completed);
        System.out.println("Placeholder: deleteCompletedRecurringTransactions() called");
    }

    /**
     * 2. Fetches all recurring transactions, then for each, checks lastTransactionId
     * and recurring_frequency to complete all remaining transactions till date.
     */
    public void completePendingRecurringTransactions() {
         List<Recurring> allRecurring = recurringDao.getAllRecurring(); // Example DAO method
         Date currentDate = new Date();

         for (Recurring recurring : allRecurring) {
             // Get the last transaction based on recurring.getLastTransactionId()
             if(recurring.getLastTransactionId() != null) {
                 Transaction lastTransaction = transactionDao.getTransactionByIdNoneLive((recurring.getLastTransactionId()));
                 System.out.println("Placeholder: completePendingRecurringTransactions() called" + lastTransaction);
             }
             // Date nextDueDate = recurring.getRecurringNextDueDate(); // Assuming a getter
             System.out.println("Placeholder: completePendingRecurringTransactions() called");
            // if (nextDueDate == null && lastTransaction != null) {
                // Calculate nextDueDate based on lastTransaction.getDate() and recurring.getRecurringFrequency()
                // nextDueDate = calculateNextDueDate(lastTransaction.getDate(), recurring.getRecurringFrequency());
            // } else if (nextDueDate == null && lastTransaction == null) {
                // This might be a new recurring item, decide how to handle its first transaction.
                // Or it means the recurring_start_date could be the first due date.
                // nextDueDate = recurring.getRecurringStartDate(); // Assuming a start date field
             }


            // while (nextDueDate != null && !nextDueDate.after(currentDate) && (recurring.getRecurringEndDate() == null || !nextDueDate.after(recurring.getRecurringEndDate()))) {
                // Create a new transaction for this nextDueDate
                // Transaction newTransaction = new Transaction();
                // newTransaction.setAmount(recurring.getAmount()); // Set relevant fields
                // newTransaction.setDescription("Recurring: " + recurring.getName());
                // newTransaction.setDate(nextDueDate);
                // newTransaction.setAccountId(recurring.getAccountId()); // or similar
                // newTransaction.setCategoryId(recurring.getCategoryId()); // or similar
                // ... set other transaction fields

                // long newTransactionId = transactionDao.insert(newTransaction);
                // recurring.setLastTransactionId(newTransactionId);
                // recurring.setRecurringNextDueDate(calculateNextDueDate(nextDueDate, recurring.getRecurringFrequency()));
                // recurringDao.update(recurring);

                // nextDueDate = recurring.getRecurringNextDueDate();
            // }
        // }
        // System.out.println("Processed pending recurring transactions.");
        // TODO: Implement actual database logic
        System.out.println("Placeholder: completePendingRecurringTransactions() called");
    }

    /**
     * Helper method to calculate the next due date.
     * You'll need to implement this based on your frequency logic (e.g., "monthly", "weekly").
     */
    // private Date calculateNextDueDate(Date fromDate, String frequency) {
    //     // Calendar calendar = Calendar.getInstance();
    //     // calendar.setTime(fromDate);
    //     // switch (frequency.toLowerCase()) {
    //     //     case "daily":
    //     //         calendar.add(Calendar.DAY_OF_MONTH, 1);
    //     //         break;
    //     //     case "weekly":
    //     //         calendar.add(Calendar.WEEK_OF_YEAR, 1);
    //     //         break;
    //     //     case "monthly":
    //     //         calendar.add(Calendar.MONTH, 1);
    //     //         break;
    //     //     case "yearly":
    //     //         calendar.add(Calendar.YEAR, 1);
    //     //         break;
    //     //     default:
    //     //         // Handle unknown or unsupported frequency
    //     //         return null;
    //     // }
    //     // return calendar.getTime();
    //     return null; // Placeholder
    // }
}
