package vn.com.misa.cukcuklite.screen.dishorder;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;

public interface DishOrderContract {
    interface IView extends IBaseView {

        void showListDishOrder(List<BillDetail> billDetails);

        void saveOrderSuccess();

        void saveOrderFailed();

        void setBill(Bill bill);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void saveOrder(Bill bill, List<BillDetail> billDetails);

        void pay();

        void setListDishOrder(String billId);

        void getBillById(String billId);

        void updateOrder(Bill bill, List<BillDetail> billDetails);
    }
}
