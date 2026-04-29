package com.banksystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {

    @Test
    public void testConstructors() {
        // Constructor mặc định
        SavingsAccount acc1 = new SavingsAccount();
        assertNotNull(acc1);

        // Constructor có tham số
        SavingsAccount acc2 = new SavingsAccount(987654321L, 6000.0);
        assertEquals(987654321L, acc2.getAccountNumber());
        assertEquals(6000.0, acc2.getBalance());
    }

    @Test
    public void testDepositSuccess() {
        SavingsAccount acc = new SavingsAccount(987654321L, 6000.0);
        acc.deposit(2000.0);
        assertEquals(8000.0, acc.getBalance());
    }

    @Test
    public void testDepositFailure() {
        SavingsAccount acc = new SavingsAccount(987654321L, 6000.0);
        // Nạp số âm -> nhảy vào catch
        acc.deposit(-100.0);
        assertEquals(6000.0, acc.getBalance());
    }

    @Test
    public void testWithdrawSuccess() {
        SavingsAccount acc = new SavingsAccount(987654321L, 6000.0);
        // Rút 1000 (<= MAX_WITHDRAWAL_AMOUNT) và số dư còn lại 5000 (>= MIN_BALANCE)
        acc.withdraw(1000.0);
        assertEquals(5000.0, acc.getBalance());
    }

    @Test
    public void testWithdrawExceedsMaxAmount() {
        SavingsAccount acc = new SavingsAccount(987654321L, 10000.0);
        // Rút quá 1000 -> văng InvalidFundingAmountException và bị catch
        acc.withdraw(1001.0);
        assertEquals(10000.0, acc.getBalance());
    }

    @Test
    public void testWithdrawViolatesMinBalance() {
        SavingsAccount acc = new SavingsAccount(987654321L, 5500.0);
        // Rút 600 (<= 1000 hợp lệ), nhưng số dư còn 4900 (< 5000)
        // -> văng InsufficientFundsException và bị catch
        acc.withdraw(600.0);
        assertEquals(5500.0, acc.getBalance());
    }

    @Test
    public void testWithdrawNegativeAmount() {
        SavingsAccount acc = new SavingsAccount(987654321L, 6000.0);
        // Rút số âm -> văng lỗi trong doWithdrawing() và bị catch
        acc.withdraw(-50.0);
        assertEquals(6000.0, acc.getBalance());
    }
}