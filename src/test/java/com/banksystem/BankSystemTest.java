package com.banksystem;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankSystemTest {

  @Test
  public void testBankConstructorsAndSetters() {
    Bank bank = new Bank();
    assertNotNull(bank.getCustomerList());
    assertTrue(bank.getCustomerList().isEmpty());

    // Nhánh 1: Set list null
    bank.setCustomerList(null);
    assertNotNull(bank.getCustomerList());

    // Nhánh 2: Set list hợp lệ
    List<Customer> list = new ArrayList<>();
    list.add(new Customer(123456789L, "Test Name"));
    bank.setCustomerList(list);
    assertEquals(1, bank.getCustomerList().size());
  }

  @Test
  public void testReadCustomerListWithNullStream() {
    Bank bank = new Bank();
    // Kiểm tra nhánh if (inputStream == null)
    bank.readCustomerList(null);
    assertTrue(bank.getCustomerList().isEmpty());
  }

  @Test
  public void testReadCustomerListWithValidAndInvalidData() {
    Bank bank = new Bank();

    // Chuỗi giả lập một file txt với đủ mọi trường hợp:
    // 1. Khách hàng chuẩn
    // 2. Tài khoản chuẩn (Checking và Savings)
    // 3. Dòng trống (sẽ bị bỏ qua)
    // 4. Dòng sai định dạng khoảng trắng (lastSpace <= 0)
    // 5. Dòng tài khoản nằm chơ vơ khi chưa có khách hàng
    // 6. Dòng tài khoản bị thiếu trường (parts < 3)
    // 7. Dòng sai loại tài khoản (UNKNOWN)
    // 8. Dòng sai định dạng số tiền (NumberFormatException)
    String mockFileData =
            "12345 CHECKING 5000\n" + // Chưa có current, sẽ bỏ qua
                    "Nguyen Van A 123456789\n" +
                    "111 CHECKING 1000\n" +
                    "222 SAVINGS 2000\n" +
                    "\n" + // Dòng trống
                    "   \n" + // Dòng chứa mỗi dấu cách
                    "NoSpaceLine\n" + // Không có dấu cách
                    "333 UNKNOWN 3000\n" + // Sai loại tài khoản
                    "444 CHECKING ABC\n" + // Lỗi NumberFormatException
                    "555 SAVINGS\n" + // Thiếu parts (parts < 3)
                    "Tran Thi B 987654321\n" +
                    "666 CHECKING 500\n";

    InputStream is = new ByteArrayInputStream(mockFileData.getBytes());
    bank.readCustomerList(is);

    List<Customer> list = bank.getCustomerList();

    // Sẽ chỉ có 2 khách hàng được thêm thành công
    assertEquals(2, list.size());

    // Kiểm tra khách hàng A và các tài khoản hợp lệ của A
    Customer cA = list.get(0);
    assertEquals("Nguyen Van A", cA.getFullName());
    assertEquals(2, cA.getAccountList().size()); // 111 CHECKING và 222 SAVINGS

    // Kiểm tra khách hàng B
    Customer cB = list.get(1);
    assertEquals("Tran Thi B", cB.getFullName());
    assertEquals(1, cB.getAccountList().size());
  }

  @Test
  public void testReadCustomerListWithException() {
    Bank bank = new Bank();
    // Giả lập một InputStream bị hỏng để test khối catch (Exception e)
    InputStream brokenStream = new InputStream() {
      @Override
      public int read() throws IOException {
        throw new IOException("Cố tình tạo lỗi IO để test block catch");
      }
    };

    // Hàm này bắt lỗi (try-catch) bên trong nên nó sẽ không throw ra ngoài
    assertDoesNotThrow(() -> bank.readCustomerList(brokenStream));
  }

  @Test
  public void testSortingMethods() {
    Bank bank = new Bank();
    Customer c1 = new Customer(333333333L, "B");
    Customer c2 = new Customer(111111111L, "C");
    Customer c3 = new Customer(222222222L, "A");
    Customer c4 = new Customer(444444444L, "A"); // Trùng tên, khác CMND

    bank.setCustomerList(Arrays.asList(c1, c2, c3, c4));

    String sortedById = bank.getCustomersInfoByIdOrder();
    String sortedByName = bank.getCustomersInfoByNameOrder();

    // Test Sort by ID (Kỳ vọng: 111 -> 222 -> 333 -> 444)
    assertTrue(sortedById.indexOf("111111111") < sortedById.indexOf("222222222"));
    assertTrue(sortedById.indexOf("222222222") < sortedById.indexOf("333333333"));
    assertTrue(sortedById.indexOf("333333333") < sortedById.indexOf("444444444"));

    // Test Sort by Name (Kỳ vọng: A(222) -> A(444) -> B(333) -> C(111))
    assertTrue(sortedByName.indexOf("222222222") < sortedByName.indexOf("444444444")); // Trùng tên, xếp theo ID
    assertTrue(sortedByName.indexOf("444444444") < sortedByName.indexOf("333333333")); // A lên trước B
    assertTrue(sortedByName.indexOf("333333333") < sortedByName.indexOf("111111111")); // B lên trước C
  }
}