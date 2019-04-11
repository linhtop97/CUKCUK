package vn.com.misa.cukcuklite.data.bill;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.models.BillDetail;

public class BillDataSource implements IBillDataSource {

    private SQLiteDBManager mSQLiteDBManager;
    private DishDataSource mDishDataSource;

    public BillDataSource() {
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
}
