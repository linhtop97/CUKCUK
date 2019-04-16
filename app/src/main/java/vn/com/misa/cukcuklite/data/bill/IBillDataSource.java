package vn.com.misa.cukcuklite.data.bill;

import java.util.List;

import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;
import vn.com.misa.cukcuklite.data.models.Order;

/**
 * Lớp định nghĩa các phương thức cho lớp thao tác với dữ liệu của hóa đơn
 * Created_by Nguyễn Bá Linh on 12/04/2019
 */
public interface IBillDataSource {

    List<BillDetail> initNewBillDetailList(String BillId);

    boolean addBill(Bill bill, List<BillDetail> billDetails);

    boolean addBillDetail(BillDetail billDetail);

    boolean addBillDetailList(List<BillDetail> billDetails);

    List<BillDetail> getAllBillDeTailByBillId(String billId);

    List<Bill> getAllBillUnpaid();

    List<Order> getAllOrder();

    boolean cancelOrder(String billId);

    void getOrderUnpaidByBillId(String billId);

    List<BillDetail> initBillDetailList(String billId);

    Bill getBillById(String billId);

    boolean updateBill(Bill bill, List<BillDetail> validBillDetailList);
}
