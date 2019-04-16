package vn.com.misa.cukcuklite.screen.pay;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;

public interface IPayContract {
    interface IView extends IBaseView {

        void paySuccess();

        void setBill(Bill bill, List<BillDetail> billDetailList, int billNumber);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void pay(Bill bill);
    }
}
