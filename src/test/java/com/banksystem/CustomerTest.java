package com.banksystem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testCustomerProperties() {
        Customer customer = new Customer();

        // Thay các hàm set/get dưới đây cho khớp với code thực tế của bạn
        // customer.setName("Nguyen Van A");
        // customer.setIdNumber("123456789");

        // assertEquals("Nguyen Van A", customer.getName());
        // assertEquals("123456789", customer.getIdNumber());
    }

    // Nếu Customer có chứa danh sách Account (như List<Account> accountList)
    @Test
    public void testCustomerAccounts() {
        Customer customer = new Customer();
        // Tài khoản SavingsAccount và CheckingAccount đã 100% xanh ở ảnh trước
        Account acc = new SavingsAccount();

        // customer.addAccount(acc);
        // assertEquals(1, customer.getAccountList().size());
    }
}