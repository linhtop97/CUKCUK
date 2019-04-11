package vn.com.misa.cukcuklite.screen.dishorder;

import java.util.List;

import vn.com.misa.cukcuklite.data.bill.BillDataSource;
import vn.com.misa.cukcuklite.data.models.BillDetail;

public class DishOrderPresenter implements DishOrderContract.IPresenter {

    private DishOrderContract.IView mView;
    private BillDataSource mBillDataSource;
    private String mBillId;

    DishOrderPresenter(String billId) {
        mBillDataSource = new BillDataSource();
        mBillId = billId;
    }

    @Override
    public void saveOrder() {

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
