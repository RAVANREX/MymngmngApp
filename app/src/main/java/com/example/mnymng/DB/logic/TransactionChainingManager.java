package com.example.mnymng.DB.logic;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData; // Keep for other methods if they still use it temporarily
import com.example.mnymng.DB.AppDatabase;
import com.example.mnymng.DB.dao.AccountDao;
import com.example.mnymng.DB.dao.TransactionDao;
import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.enums.TransactionType;
import com.example.mnymng.DB.models.Account;
import com.example.mnymng.DB.models.Transaction;

import java.util.Date;
import java.util.List; // Import for List
import java.util.Objects;

/**
 * Manages chaining of operations that occur as a result of transactions.
 * For example, updating account balances after a transaction or handling
 * transfers between accounts.
 * This class is implemented as a singleton.
 */
public class TransactionChainingManager {

    private static volatile TransactionChainingManager INSTANCE;
    private final AccountDao accountDao;
    private final TransactionDao transactionDao;

    // Private constructor for singleton pattern
    private TransactionChainingManager(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    /**
     * Gets the single instance of TransactionChainingManager.
     *
     * @param context The application context.
     * @return The singleton instance of TransactionChainingManager.
     */
    public static TransactionChainingManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TransactionChainingManager.class) {
                if (INSTANCE == null) {
                    AppDatabase db = AppDatabase.getDatabase(context.getApplicationContext());
                    INSTANCE = new TransactionChainingManager(db.accountDao(), db.transactionDao());
                }
            }
        }
        return INSTANCE;
    }

    // --- General Transaction Methods ---

    public void createTransaction(Transaction transaction) {
        AccountType accountType = null;
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction and AccountType cannot be null.");
        }else {
            Log.d("TransactionChainingManager", "Account ID: " + transaction.getAccount_id());
            Log.d("TransactionChainingManager", "Account: " + accountDao.getAccountById(transaction.getAccount_id()));
            Log.d("TransactionChainingManager", "Account: " + accountDao.getAccountById(transaction.getAccount_id()));
            Log.d("TransactionChainingManager", "AccountType : " + accountDao.getAccountById(transaction.getAccount_id()).getAccount_type());

            accountType = Objects.requireNonNull(accountDao.getAccountById(transaction.getAccount_id())).getAccount_type();
        }
        switch (accountType) {
            case BANK:
                createBankTransaction(transaction);
                break;
            case WALLET:
                createWalletTransaction(transaction);
                break;
            case LOAN:
                createLoanTransaction(transaction);
                break;
            case LENDING:
                createLendingTransaction(transaction);
                break;
            case CREDIT_CARD:
                createCreditCardTransaction(transaction);
                break;
            case INSURANCE:
                createInsuranceTransaction(transaction);
                break;
            case INVESTMENT:
                createInvestmentTransaction(transaction);
                break;
            case E_WALLET:
                createEWalletTransaction(transaction);
                break;
            case OTHER_ASSET:
                createOtherAssetTransaction(transaction);
                break;
            default:
                throw new IllegalArgumentException("Unsupported account type: " + accountType);
        }
    }

    public void updateTransaction(Transaction transactionToUpdate, Transaction oldTransactionState ) {
        AccountType accountType = null;
        if (transactionToUpdate == null || oldTransactionState == null) {
            throw new IllegalArgumentException("Transactions and AccountType cannot be null.");
        }else {
            accountType = Objects.requireNonNull(accountDao.getAccountById(transactionToUpdate.getAccount_id())).getAccount_type();
        }
        switch (accountType) {
            case BANK:
                updateBankTransaction(transactionToUpdate, oldTransactionState);
                break;
            case WALLET:
                updateWalletTransaction(transactionToUpdate, oldTransactionState);
                break;
            case LOAN:
                updateLoanTransaction(transactionToUpdate, oldTransactionState);
                break;
            case LENDING:
                updateLendingTransaction(transactionToUpdate, oldTransactionState);
                break;
            case CREDIT_CARD:
                updateCreditCardTransaction(transactionToUpdate, oldTransactionState);
                break;
            case INSURANCE:
                updateInsuranceTransaction(transactionToUpdate, oldTransactionState);
                break;
            case INVESTMENT:
                updateInvestmentTransaction(transactionToUpdate, oldTransactionState);
                break;
            case E_WALLET:
                updateEWalletTransaction(transactionToUpdate, oldTransactionState);
                break;
            case OTHER_ASSET:
                updateOtherAssetTransaction(transactionToUpdate, oldTransactionState);
                break;
            default:
                throw new IllegalArgumentException("Unsupported account type: " + accountType);
        }
    }

    public void deleteTransaction(Transaction transactionToDelete ) {
        AccountType accountType =null;
        if (transactionToDelete == null) {
            throw new IllegalArgumentException("Transaction and AccountType cannot be null.");
        }else {
            accountType = Objects.requireNonNull(accountDao.getAccountById(transactionToDelete.getAccount_id())).getAccount_type();
        }
        switch (accountType) {
            case BANK:
                deleteBankTransaction(transactionToDelete);
                break;
            case WALLET:
                deleteWalletTransaction(transactionToDelete);
                break;
            case LOAN:
                deleteLoanTransaction(transactionToDelete);
                break;
            case LENDING:
                deleteLendingTransaction(transactionToDelete);
                break;
            case CREDIT_CARD:
                deleteCreditCardTransaction(transactionToDelete);
                break;
            case INSURANCE:
                deleteInsuranceTransaction(transactionToDelete);
                break;
            case INVESTMENT:
                deleteInvestmentTransaction(transactionToDelete);
                break;
            case E_WALLET:
                deleteEWalletTransaction(transactionToDelete);
                break;
            case OTHER_ASSET:
                deleteOtherAssetTransaction(transactionToDelete);
                break;
            default:
                throw new IllegalArgumentException("Unsupported account type: " + accountType);
        }
    }

    // --- Start of generated methods for AccountType specific transactions ---

    // BANK Account Transactions
    public void createBankTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transaction.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Bank account not found for ID: " + transaction.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.BANK) {
            throw new IllegalArgumentException("Account type must be BANK. Found: " + account.getAccount_type());
        }
        transactionDao.insert(transaction);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transaction.getTrns_amount();
        TransactionType type = transaction.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance + transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for simple balance update: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void updateBankTransaction(Transaction transactionToUpdate, Transaction oldTransactionState) {
        if (transactionToUpdate == null || transactionToUpdate.getTrns_id() == 0 || transactionToUpdate.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to update cannot be null and must have valid trns_id and account_id.");
        }
        if (oldTransactionState == null || oldTransactionState.getTrns_id() != transactionToUpdate.getTrns_id()) {
            throw new IllegalArgumentException("Old transaction state is invalid or does not match the transaction to update.");
        }
        if (transactionToUpdate.getAccount_id() != oldTransactionState.getAccount_id()){
            throw new IllegalArgumentException("Account ID cannot be changed during an update via this method.");
        }
        Account account = accountDao.getAccountById(transactionToUpdate.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Bank account not found for ID: " + transactionToUpdate.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.BANK) {
            throw new IllegalArgumentException("Account type must be BANK. Found: " + account.getAccount_type());
        }
        double oldAmount = oldTransactionState.getTrns_amount();
        TransactionType oldType = oldTransactionState.getTrns_type();
        double effectiveOldAmount = (oldType == TransactionType.CREDIT) ? oldAmount : -oldAmount;
        double newAmount = transactionToUpdate.getTrns_amount();
        TransactionType newType = transactionToUpdate.getTrns_type();
        double effectiveNewAmount = (newType == TransactionType.CREDIT) ? newAmount : -newAmount;
        double balanceChange = effectiveNewAmount - effectiveOldAmount;
        transactionDao.update(transactionToUpdate);
        account.setAccount_current_balance(account.getAccount_current_balance() + balanceChange);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void deleteBankTransaction(Transaction transactionToDelete) {
        if (transactionToDelete == null || transactionToDelete.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to delete cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transactionToDelete.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Bank account not found for ID: " + transactionToDelete.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.BANK) {
            throw new IllegalArgumentException("Account type must be BANK. Found: " + account.getAccount_type());
        }
        transactionDao.delete(transactionToDelete);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transactionToDelete.getTrns_amount();
        TransactionType type = transactionToDelete.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance - transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for balance reversion: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    // WALLET Account Transactions
    public void createWalletTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transaction.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Wallet account not found for ID: " + transaction.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.WALLET) {
            throw new IllegalArgumentException("Account type must be WALLET. Found: " + account.getAccount_type());
        }
        transactionDao.insert(transaction);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transaction.getTrns_amount();
        TransactionType type = transaction.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance + transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance - transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for WALLET balance update: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void updateWalletTransaction(Transaction transactionToUpdate, Transaction oldTransactionState) {
        if (transactionToUpdate == null || transactionToUpdate.getTrns_id() == 0 || transactionToUpdate.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to update cannot be null and must have valid trns_id and account_id.");
        }
        if (oldTransactionState == null || oldTransactionState.getTrns_id() != transactionToUpdate.getTrns_id()) {
            throw new IllegalArgumentException("Old transaction state is invalid or does not match the transaction to update.");
        }
        if (transactionToUpdate.getAccount_id() != oldTransactionState.getAccount_id()){
            throw new IllegalArgumentException("Account ID cannot be changed during an update via this method.");
        }
        Account account = accountDao.getAccountById(transactionToUpdate.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Wallet account not found for ID: " + transactionToUpdate.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.WALLET) {
            throw new IllegalArgumentException("Account type must be WALLET. Found: " + account.getAccount_type());
        }
        double oldAmount = oldTransactionState.getTrns_amount();
        TransactionType oldType = oldTransactionState.getTrns_type();
        double effectiveOldAmount = (oldType == TransactionType.CREDIT) ? oldAmount : -oldAmount;
        double newAmount = transactionToUpdate.getTrns_amount();
        TransactionType newType = transactionToUpdate.getTrns_type();
        double effectiveNewAmount = (newType == TransactionType.CREDIT) ? newAmount : -newAmount;
        double balanceChange = effectiveNewAmount - effectiveOldAmount;
        transactionDao.update(transactionToUpdate);
        account.setAccount_current_balance(account.getAccount_current_balance() + balanceChange);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void deleteWalletTransaction(Transaction transactionToDelete) {
        if (transactionToDelete == null || transactionToDelete.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to delete cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transactionToDelete.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Wallet account not found for ID: " + transactionToDelete.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.WALLET) {
            throw new IllegalArgumentException("Account type must be WALLET. Found: " + account.getAccount_type());
        }
        transactionDao.delete(transactionToDelete);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transactionToDelete.getTrns_amount();
        TransactionType type = transactionToDelete.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance - transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for WALLET balance reversion: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    // LOAN Account Transactions
    public void createLoanTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transaction.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Loan account not found for ID: " + transaction.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.LOAN) {
            throw new IllegalArgumentException("Account type must be LOAN. Found: " + account.getAccount_type());
        }
        transactionDao.insert(transaction);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transaction.getTrns_amount();
        TransactionType type = transaction.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance + transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance - transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for LOAN balance update: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void updateLoanTransaction(Transaction transactionToUpdate, Transaction oldTransactionState) {
        if (transactionToUpdate == null || transactionToUpdate.getTrns_id() == 0 || transactionToUpdate.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to update cannot be null and must have valid trns_id and account_id.");
        }
        if (oldTransactionState == null || oldTransactionState.getTrns_id() != transactionToUpdate.getTrns_id()) {
            throw new IllegalArgumentException("Old transaction state is invalid or does not match the transaction to update.");
        }
        if (transactionToUpdate.getAccount_id() != oldTransactionState.getAccount_id()){
            throw new IllegalArgumentException("Account ID cannot be changed during an update via this method.");
        }
        Account account = accountDao.getAccountById(transactionToUpdate.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Loan account not found for ID: " + transactionToUpdate.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.LOAN) {
            throw new IllegalArgumentException("Account type must be LOAN. Found: " + account.getAccount_type());
        }
        double oldAmount = oldTransactionState.getTrns_amount();
        TransactionType oldType = oldTransactionState.getTrns_type();
        double effectiveOldAmount = (oldType == TransactionType.CREDIT) ? oldAmount : -oldAmount;
        double newAmount = transactionToUpdate.getTrns_amount();
        TransactionType newType = transactionToUpdate.getTrns_type();
        double effectiveNewAmount = (newType == TransactionType.CREDIT) ? newAmount : -newAmount;
        double balanceChange = effectiveNewAmount - effectiveOldAmount;
        transactionDao.update(transactionToUpdate);
        account.setAccount_current_balance(account.getAccount_current_balance() + balanceChange);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void deleteLoanTransaction(Transaction transactionToDelete) {
        if (transactionToDelete == null || transactionToDelete.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to delete cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transactionToDelete.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Loan account not found for ID: " + transactionToDelete.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.LOAN) {
            throw new IllegalArgumentException("Account type must be LOAN. Found: " + account.getAccount_type());
        }
        transactionDao.delete(transactionToDelete);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transactionToDelete.getTrns_amount();
        TransactionType type = transactionToDelete.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance - transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for LOAN balance reversion: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    // LENDING Account Transactions
    public void createLendingTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transaction.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Lending account not found for ID: " + transaction.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.LENDING) {
            throw new IllegalArgumentException("Account type must be LENDING. Found: " + account.getAccount_type());
        }
        transactionDao.insert(transaction);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transaction.getTrns_amount();
        TransactionType type = transaction.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance + transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance - transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for LENDING balance update: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void updateLendingTransaction(Transaction transactionToUpdate, Transaction oldTransactionState) {
        if (transactionToUpdate == null || transactionToUpdate.getTrns_id() == 0 || transactionToUpdate.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to update cannot be null and must have valid trns_id and account_id.");
        }
        if (oldTransactionState == null || oldTransactionState.getTrns_id() != transactionToUpdate.getTrns_id()) {
            throw new IllegalArgumentException("Old transaction state is invalid or does not match the transaction to update.");
        }
        if (transactionToUpdate.getAccount_id() != oldTransactionState.getAccount_id()){
            throw new IllegalArgumentException("Account ID cannot be changed during an update via this method.");
        }
        Account account = accountDao.getAccountById(transactionToUpdate.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Lending account not found for ID: " + transactionToUpdate.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.LENDING) {
            throw new IllegalArgumentException("Account type must be LENDING. Found: " + account.getAccount_type());
        }
        double oldAmount = oldTransactionState.getTrns_amount();
        TransactionType oldType = oldTransactionState.getTrns_type();
        double effectiveOldAmount = (oldType == TransactionType.CREDIT) ? oldAmount : -oldAmount;
        double newAmount = transactionToUpdate.getTrns_amount();
        TransactionType newType = transactionToUpdate.getTrns_type();
        double effectiveNewAmount = (newType == TransactionType.CREDIT) ? newAmount : -newAmount;
        double balanceChange = effectiveNewAmount - effectiveOldAmount;
        transactionDao.update(transactionToUpdate);
        account.setAccount_current_balance(account.getAccount_current_balance() + balanceChange);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void deleteLendingTransaction(Transaction transactionToDelete) {
        if (transactionToDelete == null || transactionToDelete.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to delete cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transactionToDelete.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Lending account not found for ID: " + transactionToDelete.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.LENDING) {
            throw new IllegalArgumentException("Account type must be LENDING. Found: " + account.getAccount_type());
        }
        transactionDao.delete(transactionToDelete);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transactionToDelete.getTrns_amount();
        TransactionType type = transactionToDelete.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance - transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for LENDING balance reversion: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    // CREDIT_CARD Account Transactions
    public void createCreditCardTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transaction.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Credit Card account not found for ID: " + transaction.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.CREDIT_CARD) {
            throw new IllegalArgumentException("Account type must be CREDIT_CARD. Found: " + account.getAccount_type());
        }
        transactionDao.insert(transaction);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transaction.getTrns_amount();
        TransactionType type = transaction.getTrns_type();
        double newBalance;
        if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else if (type == TransactionType.CREDIT) {
            newBalance = currentBalance - transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for CREDIT_CARD balance update: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void updateCreditCardTransaction(Transaction transactionToUpdate, Transaction oldTransactionState) {
        if (transactionToUpdate == null || transactionToUpdate.getTrns_id() == 0 || transactionToUpdate.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to update cannot be null and must have valid trns_id and account_id.");
        }
        if (oldTransactionState == null || oldTransactionState.getTrns_id() != transactionToUpdate.getTrns_id()) {
            throw new IllegalArgumentException("Old transaction state is invalid or does not match the transaction to update.");
        }
        if (transactionToUpdate.getAccount_id() != oldTransactionState.getAccount_id()){
            throw new IllegalArgumentException("Account ID cannot be changed during an update via this method.");
        }
        Account account = accountDao.getAccountById(transactionToUpdate.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Credit Card account not found for ID: " + transactionToUpdate.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.CREDIT_CARD) {
            throw new IllegalArgumentException("Account type must be CREDIT_CARD. Found: " + account.getAccount_type());
        }
        double oldAmount = oldTransactionState.getTrns_amount();
        TransactionType oldType = oldTransactionState.getTrns_type();
        double effectiveOldImpact = (oldType == TransactionType.DEBIT) ? oldAmount : -oldAmount;
        double newAmount = transactionToUpdate.getTrns_amount();
        TransactionType newType = transactionToUpdate.getTrns_type();
        double effectiveNewImpact = (newType == TransactionType.DEBIT) ? newAmount : -newAmount;
        double balanceChange = effectiveNewImpact - effectiveOldImpact;
        transactionDao.update(transactionToUpdate);
        account.setAccount_current_balance(account.getAccount_current_balance() + balanceChange);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void deleteCreditCardTransaction(Transaction transactionToDelete) {
        if (transactionToDelete == null || transactionToDelete.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to delete cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transactionToDelete.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Credit Card account not found for ID: " + transactionToDelete.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.CREDIT_CARD) {
            throw new IllegalArgumentException("Account type must be CREDIT_CARD. Found: " + account.getAccount_type());
        }
        transactionDao.delete(transactionToDelete);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transactionToDelete.getTrns_amount();
        TransactionType type = transactionToDelete.getTrns_type();
        double newBalance;
        if (type == TransactionType.DEBIT) {
            newBalance = currentBalance - transactionAmount;
        } else if (type == TransactionType.CREDIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for CREDIT_CARD balance reversion: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    // INSURANCE Account Transactions
    public void createInsuranceTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transaction.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Insurance account not found for ID: " + transaction.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.INSURANCE) {
            throw new IllegalArgumentException("Account type must be INSURANCE. Found: " + account.getAccount_type());
        }
        transactionDao.insert(transaction);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transaction.getTrns_amount();
        TransactionType type = transaction.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance + transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance - transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for INSURANCE balance update: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void updateInsuranceTransaction(Transaction transactionToUpdate, Transaction oldTransactionState) {
        if (transactionToUpdate == null || transactionToUpdate.getTrns_id() == 0 || transactionToUpdate.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to update cannot be null and must have valid trns_id and account_id.");
        }
        if (oldTransactionState == null || oldTransactionState.getTrns_id() != transactionToUpdate.getTrns_id()) {
            throw new IllegalArgumentException("Old transaction state is invalid or does not match the transaction to update.");
        }
        if (transactionToUpdate.getAccount_id() != oldTransactionState.getAccount_id()){
            throw new IllegalArgumentException("Account ID cannot be changed during an update via this method.");
        }
        Account account = accountDao.getAccountById(transactionToUpdate.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Insurance account not found for ID: " + transactionToUpdate.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.INSURANCE) {
            throw new IllegalArgumentException("Account type must be INSURANCE. Found: " + account.getAccount_type());
        }
        double oldAmount = oldTransactionState.getTrns_amount();
        TransactionType oldType = oldTransactionState.getTrns_type();
        double effectiveOldAmount = (oldType == TransactionType.CREDIT) ? oldAmount : -oldAmount;
        double newAmount = transactionToUpdate.getTrns_amount();
        TransactionType newType = transactionToUpdate.getTrns_type();
        double effectiveNewAmount = (newType == TransactionType.CREDIT) ? newAmount : -newAmount;
        double balanceChange = effectiveNewAmount - effectiveOldAmount;
        transactionDao.update(transactionToUpdate);
        account.setAccount_current_balance(account.getAccount_current_balance() + balanceChange);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void deleteInsuranceTransaction(Transaction transactionToDelete) {
        if (transactionToDelete == null || transactionToDelete.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to delete cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transactionToDelete.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Insurance account not found for ID: " + transactionToDelete.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.INSURANCE) {
            throw new IllegalArgumentException("Account type must be INSURANCE. Found: " + account.getAccount_type());
        }
        transactionDao.delete(transactionToDelete);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transactionToDelete.getTrns_amount();
        TransactionType type = transactionToDelete.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance - transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for INSURANCE balance reversion: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    // INVESTMENT Account Transactions
    public void createInvestmentTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transaction.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Investment account not found for ID: " + transaction.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.INVESTMENT) {
            throw new IllegalArgumentException("Account type must be INVESTMENT. Found: " + account.getAccount_type());
        }
        transactionDao.insert(transaction);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transaction.getTrns_amount();
        TransactionType type = transaction.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance + transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance - transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for INVESTMENT balance update: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void updateInvestmentTransaction(Transaction transactionToUpdate, Transaction oldTransactionState) {
        if (transactionToUpdate == null || transactionToUpdate.getTrns_id() == 0 || transactionToUpdate.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to update cannot be null and must have valid trns_id and account_id.");
        }
        if (oldTransactionState == null || oldTransactionState.getTrns_id() != transactionToUpdate.getTrns_id()) {
            throw new IllegalArgumentException("Old transaction state is invalid or does not match the transaction to update.");
        }
        if (transactionToUpdate.getAccount_id() != oldTransactionState.getAccount_id()){
            throw new IllegalArgumentException("Account ID cannot be changed during an update via this method.");
        }
        Account account = accountDao.getAccountById(transactionToUpdate.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Investment account not found for ID: " + transactionToUpdate.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.INVESTMENT) {
            throw new IllegalArgumentException("Account type must be INVESTMENT. Found: " + account.getAccount_type());
        }
        double oldAmount = oldTransactionState.getTrns_amount();
        TransactionType oldType = oldTransactionState.getTrns_type();
        double effectiveOldAmount = (oldType == TransactionType.CREDIT) ? oldAmount : -oldAmount;
        double newAmount = transactionToUpdate.getTrns_amount();
        TransactionType newType = transactionToUpdate.getTrns_type();
        double effectiveNewAmount = (newType == TransactionType.CREDIT) ? newAmount : -newAmount;
        double balanceChange = effectiveNewAmount - effectiveOldAmount;
        transactionDao.update(transactionToUpdate);
        account.setAccount_current_balance(account.getAccount_current_balance() + balanceChange);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void deleteInvestmentTransaction(Transaction transactionToDelete) {
        if (transactionToDelete == null || transactionToDelete.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to delete cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transactionToDelete.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Investment account not found for ID: " + transactionToDelete.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.INVESTMENT) {
            throw new IllegalArgumentException("Account type must be INVESTMENT. Found: " + account.getAccount_type());
        }
        transactionDao.delete(transactionToDelete);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transactionToDelete.getTrns_amount();
        TransactionType type = transactionToDelete.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance - transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for INVESTMENT balance reversion: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    // E_WALLET Account Transactions
    public void createEWalletTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transaction.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("E-Wallet account not found for ID: " + transaction.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.E_WALLET) {
            throw new IllegalArgumentException("Account type must be E_WALLET. Found: " + account.getAccount_type());
        }
        transactionDao.insert(transaction);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transaction.getTrns_amount();
        TransactionType type = transaction.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance + transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance - transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for E_WALLET balance update: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void updateEWalletTransaction(Transaction transactionToUpdate, Transaction oldTransactionState) {
        if (transactionToUpdate == null || transactionToUpdate.getTrns_id() == 0 || transactionToUpdate.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to update cannot be null and must have valid trns_id and account_id.");
        }
        if (oldTransactionState == null || oldTransactionState.getTrns_id() != transactionToUpdate.getTrns_id()) {
            throw new IllegalArgumentException("Old transaction state is invalid or does not match the transaction to update.");
        }
        if (transactionToUpdate.getAccount_id() != oldTransactionState.getAccount_id()){
            throw new IllegalArgumentException("Account ID cannot be changed during an update via this method.");
        }
        Account account = accountDao.getAccountById(transactionToUpdate.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("E-Wallet account not found for ID: " + transactionToUpdate.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.E_WALLET) {
            throw new IllegalArgumentException("Account type must be E_WALLET. Found: " + account.getAccount_type());
        }
        double oldAmount = oldTransactionState.getTrns_amount();
        TransactionType oldType = oldTransactionState.getTrns_type();
        double effectiveOldAmount = (oldType == TransactionType.CREDIT) ? oldAmount : -oldAmount;
        double newAmount = transactionToUpdate.getTrns_amount();
        TransactionType newType = transactionToUpdate.getTrns_type();
        double effectiveNewAmount = (newType == TransactionType.CREDIT) ? newAmount : -newAmount;
        double balanceChange = effectiveNewAmount - effectiveOldAmount;
        transactionDao.update(transactionToUpdate);
        account.setAccount_current_balance(account.getAccount_current_balance() + balanceChange);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void deleteEWalletTransaction(Transaction transactionToDelete) {
        if (transactionToDelete == null || transactionToDelete.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to delete cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transactionToDelete.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("E-Wallet account not found for ID: " + transactionToDelete.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.E_WALLET) {
            throw new IllegalArgumentException("Account type must be E_WALLET. Found: " + account.getAccount_type());
        }
        transactionDao.delete(transactionToDelete);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transactionToDelete.getTrns_amount();
        TransactionType type = transactionToDelete.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance - transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for E_WALLET balance reversion: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    // OTHER_ASSET Account Transactions
    public void createOtherAssetTransaction(Transaction transaction) {
        if (transaction == null || transaction.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transaction.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Other Asset account not found for ID: " + transaction.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.OTHER_ASSET) {
            throw new IllegalArgumentException("Account type must be OTHER_ASSET. Found: " + account.getAccount_type());
        }
        transactionDao.insert(transaction);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transaction.getTrns_amount();
        TransactionType type = transaction.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance + transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance - transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for OTHER_ASSET balance update: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void updateOtherAssetTransaction(Transaction transactionToUpdate, Transaction oldTransactionState) {
        if (transactionToUpdate == null || transactionToUpdate.getTrns_id() == 0 || transactionToUpdate.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to update cannot be null and must have valid trns_id and account_id.");
        }
        if (oldTransactionState == null || oldTransactionState.getTrns_id() != transactionToUpdate.getTrns_id()) {
            throw new IllegalArgumentException("Old transaction state is invalid or does not match the transaction to update.");
        }
        if (transactionToUpdate.getAccount_id() != oldTransactionState.getAccount_id()){
            throw new IllegalArgumentException("Account ID cannot be changed during an update via this method.");
        }
        Account account = accountDao.getAccountById(transactionToUpdate.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Other Asset account not found for ID: " + transactionToUpdate.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.OTHER_ASSET) {
            throw new IllegalArgumentException("Account type must be OTHER_ASSET. Found: " + account.getAccount_type());
        }
        double oldAmount = oldTransactionState.getTrns_amount();
        TransactionType oldType = oldTransactionState.getTrns_type();
        double effectiveOldAmount = (oldType == TransactionType.CREDIT) ? oldAmount : -oldAmount;
        double newAmount = transactionToUpdate.getTrns_amount();
        TransactionType newType = transactionToUpdate.getTrns_type();
        double effectiveNewAmount = (newType == TransactionType.CREDIT) ? newAmount : -newAmount;
        double balanceChange = effectiveNewAmount - effectiveOldAmount;
        transactionDao.update(transactionToUpdate);
        account.setAccount_current_balance(account.getAccount_current_balance() + balanceChange);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    public void deleteOtherAssetTransaction(Transaction transactionToDelete) {
        if (transactionToDelete == null || transactionToDelete.getAccount_id() == 0) {
            throw new IllegalArgumentException("Transaction to delete cannot be null and must have a valid account_id.");
        }
        Account account = accountDao.getAccountById(transactionToDelete.getAccount_id()); // TODO: Use sync version
        if (account == null) {
            throw new IllegalArgumentException("Other Asset account not found for ID: " + transactionToDelete.getAccount_id());
        }
        if (account.getAccount_type() != AccountType.OTHER_ASSET) {
            throw new IllegalArgumentException("Account type must be OTHER_ASSET. Found: " + account.getAccount_type());
        }
        transactionDao.delete(transactionToDelete);
        double currentBalance = account.getAccount_current_balance();
        double transactionAmount = transactionToDelete.getTrns_amount();
        TransactionType type = transactionToDelete.getTrns_type();
        double newBalance;
        if (type == TransactionType.CREDIT) {
            newBalance = currentBalance - transactionAmount;
        } else if (type == TransactionType.DEBIT) {
            newBalance = currentBalance + transactionAmount;
        } else {
            System.err.println("TransactionChainingManager: Unsupported transaction type for OTHER_ASSET balance reversion: " + type);
            return;
        }
        account.setAccount_current_balance(newBalance);
        account.setAccount_updated_date(new Date());
        accountDao.update(account);
    }

    // --- End of generated methods ---

    /**
     * Deletes an account and all its associated transactions.
     * IMPORTANT: This method should be called from a background thread.
     *
     * @param accountToDelete The account to delete.
     * @throws IllegalArgumentException if the account is null.
     */
    public void deleteAccountAndTransactions(Account accountToDelete) {
        if (accountToDelete == null) {
            throw new IllegalArgumentException("Account to delete cannot be null.");
        }

        long accountId = accountToDelete.getAccount_id();

        // Use the synchronous DAO method to fetch transactions
        List<Transaction> transactions = transactionDao.getTransactionsByAccountIdSync(accountId);

        if (transactions != null) {
            for (Transaction transaction : transactions) {
                transactionDao.delete(transaction);
            }
        }
        // No need for an else case for null transactions if getTransactionsByAccountIdSync returns empty list for no transactions

        // Finally, delete the account
        accountDao.delete(accountToDelete);
        System.out.println("TransactionChainingManager: Account and its associated transactions deleted for account ID: " + accountId);
    }
}
