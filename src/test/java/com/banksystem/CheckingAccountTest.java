package com.banksystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckingAccountTest {

    @Test
    public void testConstructor() {
        CheckingAccount acc = new CheckingAccount(123456789L, 2000.0);
        assertEquals(123456789L, acc.getAccountNumber());
        assertEquals(2000.0, acc.getBalance());
    }

    @Test
    public void testDepositSuccess() {
        CheckingAccount acc = new CheckingAccount(123456789L, 2000.0);
        acc.deposit(1000.0);
        // Nạp hợp lệ, số dư tăng
        assertEquals(3000.0, acc.getBalance());
    }

    @Test
    public void testDepositFailure() {
        CheckingAccount acc = new CheckingAccount(123456789L, 2000.0);
        // Nạp số âm sẽ kích hoạt khối catch (BankException e)
        acc.deposit(-500.0);
        // Số dư phải được giữ nguyên
        assertEquals(2000.0, acc.getBalance());
    }

    @Test
    public void testWithdrawSuccess() {
        CheckingAccount acc = new CheckingAccount(123456789L, 2000.0);
        acc.withdraw(1500.0);
        // Rút hợp lệ, số dư giảm
        assertEquals(500.0, acc.getBalance());
    }

    @Test
    public void testWithdrawFailure() {
        CheckingAccount acc = new CheckingAccount(123456789L, 2000.0);
        // Rút quá số dư sẽ kích hoạt khối catch (BankException e)
        acc.withdraw(5000.0);
        // Số dư phải được giữ nguyên
        assertEquals(2000.0, acc.getBalance());
    }
}