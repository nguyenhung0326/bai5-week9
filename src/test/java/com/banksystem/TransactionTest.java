package com.banksystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testConstructorAndGetters() {
        Transaction t = new Transaction(Transaction.TYPE_DEPOSIT_CHECKING, 1000.0, 500.0, 1500.0);

        assertEquals(Transaction.TYPE_DEPOSIT_CHECKING, t.getType());
        assertEquals(1000.0, t.getAmount());
        assertEquals(500.0, t.getInitialBalance());
        assertEquals(1500.0, t.getFinalBalance());
    }

    @Test
    public void testSetters() {
        Transaction t = new Transaction(0, 0, 0, 0);

        t.setType(Transaction.TYPE_WITHDRAW_SAVINGS);
        t.setAmount(200.5);
        t.setInitialBalance(1000.0);
        t.setFinalBalance(799.5);

        assertEquals(Transaction.TYPE_WITHDRAW_SAVINGS, t.getType());
        assertEquals(200.5, t.getAmount());
        assertEquals(1000.0, t.getInitialBalance());
        assertEquals(799.5, t.getFinalBalance());
    }

    @Test
    public void testGetTypeString() {
        // Test tất cả các nhánh (branches) của switch-case
        assertEquals("Nạp tiền vãng lai", Transaction.getTypeString(Transaction.TYPE_DEPOSIT_CHECKING));
        assertEquals("Rút tiền vãng lai", Transaction.getTypeString(Transaction.TYPE_WITHDRAW_CHECKING));
        assertEquals("Nạp tiền tiết kiệm", Transaction.getTypeString(Transaction.TYPE_DEPOSIT_SAVINGS));
        assertEquals("Rút tiền tiết kiệm", Transaction.getTypeString(Transaction.TYPE_WITHDRAW_SAVINGS));

        // Test nhánh default
        assertEquals("Không rõ", Transaction.getTypeString(999));
    }

    @Test
    public void testGetTransactionSummary() {
        Transaction t = new Transaction(Transaction.TYPE_DEPOSIT_CHECKING, 1000.5, 500.25, 1500.75);

        // Kiểm tra xem hàm có format đúng Locale.US (dấu chấm thập phân) và 2 chữ số sau dấu phẩy không
        String expected = "- Kiểu giao dịch: Nạp tiền vãng lai. Số dư ban đầu: $500.25. Số tiền: $1000.50. Số dư cuối: $1500.75.";
        assertEquals(expected, t.getTransactionSummary());
    }
}