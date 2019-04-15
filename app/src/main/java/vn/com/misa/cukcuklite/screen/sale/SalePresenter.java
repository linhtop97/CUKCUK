package vn.com.misa.cukcuklite.screen.sale;

import vn.com.misa.cukcuklite.data.bill.BillDataSource;

public class SalePresenter implements ISaleContract.IPresenter {

    private ISaleContract.IView mView;
    private BillDataSource mBillDataSource;

    public SalePresenter() {
        mBillDataSource = BillDataSource.getInstance();
    }

    @Override
    public void setView(ISaleContract.IView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        try {
            mView.showLoading();
            mView.showListOrder(mBillDataSource.getAllOrder());
            mView.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
