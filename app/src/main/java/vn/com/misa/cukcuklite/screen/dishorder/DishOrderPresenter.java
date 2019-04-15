package vn.com.misa.cukcuklite.screen.dishorder;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcuklite.data.bill.BillDataSource;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;

public class DishOrderPresenter implements DishOrderContract.IPresenter {

    private static final String TAG = "DishOrderPresenter";
    private DishOrderContract.IView mView;
    private BillDataSource mBillDataSource;
    private String mBillId;

    DishOrderPresenter(String billId) {
        mBillDataSource = BillDataSource.getInstance();
        mBillId = billId;
    }

    /**
     * Phương thức lưu trữ hóa đơn và danh sách hóa đơn chi tiết
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param bill        - hóa đơn
     * @param billDetails - danh sách hóa đơn chi tiết
     */
    @Override
    public void saveOrder(Bill bill, List<BillDetail> billDetails) {
        try {
            mView.showLoading();
            if (mBillDataSource.addBill(bill)) {
                int size = billDetails.size();
                List<BillDetail> billDetailList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    //nếu như danh sách món ăn trong hóa đơn chi tiết có số lượng lớn hơn 0
                    //thì sẽ thêm vào danh sách món ăn trong hóa đơn chi tiết mới để lưu trữ
                    if (billDetails.get(i).getQuantity() > 0) {
                        billDetailList.add(billDetails.get(i));
                    }
                }
                //Kiểm tra việc lưu trữ danh sách hóa đơn chi tiết và xử lý kết quả
                if (mBillDataSource.addBillDetailList(billDetailList)) {
                    mView.saveOrderSuccess();
                } else {
                    mView.saveOrderFailed();
                }
            }
            mView.hideLoading();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        mView.saveOrderFailed();
    }

    @Override
    public void pay() {

    }

    /**
     * Phương thức đặt view cho presenter
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(DishOrderContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 12/04/2019
     */
    @Override
    public void onStart() {
        try {
            mView.showLoading();
            List<BillDetail> billDetails = mBillDataSource.initNewBillDetailList(mBillId);
            if (billDetails != null) {
                mView.showListDishOrder(billDetails);
            }
            mView.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created_by Nguyễn Bá Linh on 12/04/2019
     */
    @Override
    public void onStop() {

    }
}
