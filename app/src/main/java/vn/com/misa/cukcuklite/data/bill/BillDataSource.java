package vn.com.misa.cukcuklite.data.bill;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import vn.com.misa.cukcuklite.data.database.IDBUtils;
import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;
import vn.com.misa.cukcuklite.data.models.Order;
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

    private static final String TAG = "BillDataSource";
    private static BillDataSource sInstance;
    private SQLiteDBManager mSQLiteDBManager;
    private DishDataSource mDishDataSource;
    private HashMap<String, Order> orderHashMap;

    private BillDataSource() {
        mSQLiteDBManager = SQLiteDBManager.getInstance();
        mDishDataSource = DishDataSource.getInstance();
        orderHashMap = new HashMap<>();
    }

    public static BillDataSource getInstance() {
        if (sInstance == null) {
            synchronized (BillDataSource.class) {
                if (sInstance == null) {
                    sInstance = new BillDataSource();
                }
            }
        }
        return sInstance;
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
            if (billId != null) {
                List<BillDetail> billDetails = new ArrayList<>();
                List<String> dishIds = mDishDataSource.getAllDishId();
                if (dishIds != null) {
                    for (int i = 0; i < dishIds.size(); i++) {
                        BillDetail billDetail = new BillDetail.Builder()
                                .setBillDetailId(UUID.randomUUID().toString())
                                .setBillId(billId)
                                .setDishId(dishIds.get(i)).build();
                        billDetails.add(billDetail);
                    }
                    return billDetails;
                }
            } else {
                return null;
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
    public boolean addBill(Bill bill, List<BillDetail> billDetails) {
        try {
            if (bill != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_BILL_ID, bill.getBillId());
                contentValues.put(COLUMN_BILL_NUMBER, bill.getBillNumber());
                contentValues.put(COLUMN_DATE_CREATED, String.valueOf(bill.getDateCreated()));
                contentValues.put(COLUMN_TABLE_NUMBER, bill.getTableNumber());
                contentValues.put(COLUMN_NUMBER_CUSTOMER, bill.getNumberCustomer());
                contentValues.put(COLUMN_TOTAL_MONEY, bill.getTotalMoney());
                contentValues.put(COLUMN_CUSTOMER_PAY, bill.getCustomerPay());
                contentValues.put(COLUMN_STATE, AppConstants.UN_PAID);
                if (mSQLiteDBManager.addNewRecord(BILL_TBL_NAME, contentValues)) {
                    if (addBillDetailList(billDetails)) {
                        addOrderToCache(getOrderFromBill(bill));
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addOrderToCache(Order order) {
        orderHashMap.put(order.getBillId(), order);
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
        try {
            boolean addSuccess = true;
            if (billDetails != null) {
                int size = billDetails.size();
                for (int i = 0; i < size; i++) {
                    if (!addBillDetail(billDetails.get(i))) {
                        addSuccess = false;
                        break;
                    }
                }
                return addSuccess;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
            String sql = String.format(
                    "SELECT %s.%s,%s,%s,%s,%s,%s FROM " + BILL_DETAIL_TBL_NAME + "," + DishDataSource.DISH_TBL_NAME + " where " + COLUMN_BILL_ID + "=" + "'" + billId + "'" + " and %s.%s = %s.%s",
                    BILL_DETAIL_TBL_NAME,
                    COLUMN_DISH_ID,
                    DishDataSource.COLUMN_DISH_NAME,
                    COLUMN_BILL_ID,
                    COLUMN_QUANTITY,
                    COLUMN_TOTAL_MONEY,
                    COLUMN_BILL_DETAIL_ID,
                    BILL_DETAIL_TBL_NAME,
                    COLUMN_DISH_ID,
                    DishDataSource.DISH_TBL_NAME,
                    COLUMN_DISH_ID
            );
            Log.d(TAG, "getAllBillDeTailByBillId: " + sql);
            Cursor cursor = mSQLiteDBManager.getRecords(sql, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BillDetail billDetail = new BillDetail.Builder()
                        .setBillDetailId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_DETAIL_ID)))
                        .setBillId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)))
                        .setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)))
                        .setDishId(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_ID)))
                        .setTotalMoney(cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_MONEY)))
                        .setName(cursor.getString(cursor.getColumnIndex(DishDataSource.COLUMN_DISH_NAME)))
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

    /**
     * Phương thức lấy danh sách các hóa đơn
     * Created_by Nguyễn Bá Linh on 13/04/2019
     *
     * @return - danh sách hóa đơn
     */
    @Override
    public List<Bill> getAllBillUnpaid() {
        List<Bill> bills = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + BILL_TBL_NAME + " where " + COLUMN_STATE + "=0", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Bill bill = new Bill.Builder().setBillId(cursor.getString(cursor.getColumnIndex(COLUMN_BILL_ID)))
                        .setBillNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_BILL_NUMBER)))
                        .setTotalMoney(cursor.getInt((cursor.getColumnIndex(COLUMN_TOTAL_MONEY))))
                        .setNumberCustomer(cursor.getInt((cursor.getColumnIndex(COLUMN_NUMBER_CUSTOMER))))
                        .setTableNumber(cursor.getInt((cursor.getColumnIndex(COLUMN_TABLE_NUMBER))))
                        .setDateCreated(Long.parseLong(cursor.getString((cursor.getColumnIndex(COLUMN_DATE_CREATED)))))
                        .build();
                bills.add(bill);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bills;
    }

    /**
     * Lấy danh sách gọi món
     * Created_by Nguyễn Bá Linh on 13/04/2019
     *
     * @return - danh sách gọi món
     */
    @Override
    public List<Order> getAllOrder() {
        try {
            if (orderHashMap.size() > 0) {
                return new ArrayList<>(orderHashMap.values());
            }
            List<Bill> bills = getAllBillUnpaid();
            if (bills != null) {
                for (Bill bill : bills) {
                    addOrderToCache(getOrderFromBill(bill));
                }
                return new ArrayList<>(orderHashMap.values());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Order getOrderFromBill(Bill bill) {

        List<BillDetail> billDetails = getAllBillDeTailByBillId(bill.getBillId());
        StringBuilder content = new StringBuilder();
        if (billDetails != null) {
            for (BillDetail billDetail : billDetails) {
                content.append(billDetail.getName());
                content.append(" <font color=#0973b9>(").append(billDetail.getQuantity()).append(")</font>").append(", ");
            }
        }

        content = content.replace(content.length() - 2, content.length(), ".");
        return new Order.Builder().setBillId(bill.getBillId())
                .setContent(content.toString())
                .setNumberCustomer(bill.getNumberCustomer())
                .setTableNumber(bill.getTableNumber())
                .setTotalMoney(bill.getTotalMoney())
                .setDateCreated(bill.getDateCreated()).build();
    }


    @Override
    public boolean cancelOrder(String billId) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_STATE, AppConstants.CANCEL);
            if (mSQLiteDBManager.updateRecord(BILL_TBL_NAME, contentValues,
                    COLUMN_BILL_ID + "=?", new String[]{billId})) {
                removeOrderInCache(billId);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void getOrderUnpaidByBillId(String billId) {

    }

    /**
     * Loại bỏ order ra khỏi bộ nhớ cache
     * Created_by Nguyễn Bá Linh on 15/04/2019
     *
     * @param billId - hóa đơn id
     */
    private void removeOrderInCache(String billId) {
        if (orderHashMap != null && orderHashMap.size() > 0) {
            orderHashMap.remove(billId);
        }
    }
}
