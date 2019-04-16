package vn.com.misa.cukcuklite.screen.pay;

import java.util.List;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.bill.BillDataSource;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;

public class PayPresenter implements IPayContract.IPresenter {

    private IPayContract.IView mView;
    private BillDataSource mBillDataSource;
    private String mBillId;

    public PayPresenter(String billId) {
        mBillDataSource = BillDataSource.getInstance();
        mBillId = billId;
    }

    /**
     * Phương thức thanh toán hóa đơn
     * Created_by Nguyễn Bá Linh on 17/04/2019
     *
     * @param bill - hóa đơn
     */
    @Override
    public void pay(Bill bill) {
        try {
            mView.showLoading();
            if (mBillDataSource.payBill(bill)) {
                mView.paySuccess();
            } else {
                mView.receiveMessage(R.string.something_went_wrong);
            }
            mView.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức đặt view cho presenter
     * Created_by Nguyễn Bá Linh on 16/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IPayContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 16/04/2019
     */
    @Override
    public void onStart() {
        try {
            mView.showLoading();
            if (mBillId != null) {
                List<BillDetail> billDetailList = mBillDataSource.getAllBillDeTailByBillId(mBillId);
                Bill bill = mBillDataSource.getBillById(mBillId);
                int billNumber = mBillDataSource.countBillWasPaid();
                if (billDetailList != null && bill != null) {
                    mView.setBill(bill, billDetailList, billNumber);
                }
            } else {
                mView.receiveMessage(R.string.something_went_wrong);
            }
            mView.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created_by Nguyễn Bá Linh on 16/04/2019
     */
    @Override
    public void onStop() {

    }
}
