package com.banksystem;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testDefaultConstructor() {
        Customer customer = new Customer();
        assertEquals(0L, customer.getIdNumber());
        assertEquals("", customer.getFullName());
        assertNotNull(customer.getAccountList());
        assertTrue(customer.getAccountList().isEmpty());
    }

    @Test
    public void testParameterizedConstructorAndSetters() {
        Customer customer = new Customer(123456789L, "Nguyen");
        assertEquals(123456789L, customer.getIdNumber());
        assertEquals("Nguyen", customer.getFullName());

        customer.setIdNumber(987654321L);
        customer.setFullName("Phung");
        assertEquals(987654321L, customer.getIdNumber());
        assertEquals("Phung", customer.getFullName());
    }

    @Test
    public void testSetAccountList() {
        Customer customer = new Customer();

        // Nhánh 1: Truyền vào list null -> khởi tạo list rỗng
        customer.setAccountList(null);
        assertNotNull(customer.getAccountList());
        assertTrue(customer.getAccountList().isEmpty());

        // Nhánh 2: Truyền vào list hợp lệ -> gán thành công
        List<Account> list = new ArrayList<>();
        Account acc = new SavingsAccount(); // Dùng class con có sẵn của bạn
        list.add(acc);

        customer.setAccountList(list);
        assertEquals(1, customer.getAccountList().size());
        assertEquals(acc, customer.getAccountList().get(0));
    }

    @Test
    public void testAddAccount() {
        Customer customer = new Customer();

        // Nhánh 1: Thêm account null -> không làm gì cả
        customer.addAccount(null);
        assertTrue(customer.getAccountList().isEmpty());

        // Nhánh 2: Thêm account hợp lệ -> thêm thành công
        Account acc = new SavingsAccount();
        customer.addAccount(acc);
        assertEquals(1, customer.getAccountList().size());

        // Nhánh 3: Thêm trùng account đã có -> không thêm để tránh trùng lặp
        customer.addAccount(acc);
        assertEquals(1, customer.getAccountList().size());
    }

    @Test
    public void testRemoveAccount() {
        Customer customer = new Customer();
        Account acc = new SavingsAccount();
        customer.addAccount(acc);
        assertEquals(1, customer.getAccountList().size());

        // Nhánh 1: Truyền account null để xóa -> không làm gì cả
        customer.removeAccount(null);
        assertEquals(1, customer.getAccountList().size());

        // Nhánh 2: Xóa account hợp lệ -> xóa thành công
        customer.removeAccount(acc);
        assertTrue(customer.getAccountList().isEmpty());
    }

    @Test
    public void testGetCustomerInfo() {
        Customer customer = new Customer(123456789L, "Nguyen");
        String expected = "Số CMND: 123456789. Họ tên: Nguyen.";
        assertEquals(expected, customer.getCustomerInfo());
    }
}