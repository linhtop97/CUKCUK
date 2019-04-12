package vn.com.misa.cukcuklite.data.bill;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import vn.com.misa.cukcuklite.data.database.IDBUtils;
import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;
import vn.com.misa.cukcuklite.utils.AppConstants;

import static vn.com.misa.cukcuklite.data.database.IDBUtils.ITableBillDetailUtils.BILL_DETAIL_TBL_NAME;
import static vn.com.misa.cukcuklite.data.database.IDBUtils.ITableBillDetailUtils.COLUMN_BILL_DETAIL_ID;
import static vn.com.misa.cukcuklite.data.database.IDBUtils.ITableBillDetailUtils.COLUMN_DISH_ID;
import static vn.com.misa.cukcuklite.data.database.IDBUtils.ITableBillDetailUtils.COLUMN_QUANTITY;

/**
 * Lớp thao tác truy xuất hóa đơn và hóa đơn chi tiết trong cơ sở dữ liệu
 * Created_by Nguyễn Bá Linh on 12/04/2019
 */
public class BillDataSource implements IBillDataSource, IDBUtils.ITableBillUtils {

    private SQLiteDBManager mSQLiteDBManager;
    private DishDataSource mDishDataSource;

    public BillDataSource() {
        mSQLiteDBManager = SQLiteDBManager.getInstance();
        mDishDataSource = new DishDataSource();
    }

    /**
     * Phương thức khởi tạo danh sách hóa đơn chi tiết mặc định gồm
     * id của hóa đơn và các món ăn có thể có trong hóa đơn
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param billId - id của hóa đơn
     * @return - danh sách hóa đơn chi tiết mặc định
     */
    @Override
    public List<BillDetail> initNewBillDetailList(String billId) {
        try {
            List<BillDetail> billDetails = new ArrayList<>();
            List<String> dishIds = mDishDataSource.getAllDishId();
            if (dishIds != null) {
                for (int i = 0; i < dishIds.size(); i++) {
                    BillDetail billDetail = new BillDetail.Builder().
                            setBillDetailId(UUID.randomUUID().toString())
                            .setBillId(billId)
                            .setDishId(dishIds.get(i)).build();
                    billDetails.add(billDetail);
                }
                return billDetails;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Thêm hóa đơn vào cơ sở dữ liệu
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param bill - hóa đơn
     * @return - thêm hóa đơn thành công/thất bại
     */
    @Override
    public boolean addBill(Bill bill) {
        try {
            if (bill != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_BILL_ID, bill.getBillId());
                contentValues.put(COLUMN_BILL_NUMBER, bill.getBillNumber());
                contentValues.put(COLUMN_DATE_CREATED, bill.getDateCreated());
                contentValues.put(COLUMN_TABLE_NUMBER, bill.getTableNumber());
                contentValues.put(COLUMN_NUMBER_CUSTOMER, bill.getNumberCustomer());
                contentValues.put(COLUMN_TOTAL_MONEY, bill.getTotalMoney());
                contentValues.put(COLUMN_CUSTOMER_PAY, bill.getCustomerPay());
                //kiểm tra trạng thái của hóa đơn để cập nhật vào cơ sở dữ liệu dữ liệu hợp lý
                int state = AppConstants.UN_PAID;
                switch (bill.getState()) {
                    case PAID:
                        state = AppConstants.PAID;
                        break;
                    case CANCEL:
                        state = AppConstants.CANCEL;
                        break;
                }
                contentValues.put(COLUMN_STATE, state);
                return mSQLiteDBManager.addNewRecord(BILL_TBL_NAME, contentValues);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Thêm hóa đơn chi tiết vào cơ sở dữ liệu
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param billDetail - hóa đơn chi tiết
     * @return - thêm hóa đơn chi tiết thành công/thất bại
     */
    @Override
    public boolean addBillDetail(BillDetail billDetail) {
        try {
            if (billDetail != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_BILL_DETAIL_ID, billDetail.getBillDetailId());
                contentValues.put(COLUMN_BILL_ID, billDetail.getBillId());
                contentValues.put(COLUMN_DISH_ID, billDetail.getDishId());
                contentValues.put(COLUMN_QUANTITY, billDetail.getQuantity());
                contentValues.put(COLUMN_TOTAL_MONEY, billDetail.getTotalMoney());
                return mSQLiteDBManager.addNewRecord(BILL_DETAIL_TBL_NAME, contentValues);
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Thêm danh sách hóa đơn chi tiết vào cơ sở dữ liệu
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param billDetails - danh sách hóa đơn chi tiết
     * @return - thêm danh sách hóa đơn chi tiết thành công hay thất bại
     */
    @Override
    public boolean addBillDetailList(List<BillDetail> billDetails) {
        boolean addSuccess = true;
        try {
            if (billDetails != null) {
                int size = billDetails.size();
                for (int i = 0; i < size; i++) {
                    if (!addBillDetail(billDetails.get(i))) {
                        addSuccess = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addSuccess;
    }

    /**
     * Phương thức lấy hết danh sách hóa đơn chi tiết thông qua id của hóa đơn
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param billId - id của của hóa đơn
     * @return - danh sách hóa đơn chi tiết
     */
    @Override
    public List<BillDetail> getAllBillDeTailByBillId(String billId) {
        List<BillDetail> billDetails = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + BILL_DETAIL_TBL_NAME + " where " + COLUMN_BILL_ID + "=" + "'" + billId + "'", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BillDetail billDetail = new BillDetail.Builder().setBillDetailId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_DETAIL_ID)))
                        .setBillId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)))
                        .setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                        .setDishId(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_ID)))
                        .setTotalMoney(cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_MONEY)))
                        .build();
                billDetails.add(billDetail);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billDetails;
    }
}
