package vn.com.misa.cukcuklite.screen.dishorder;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.BillDetail;

public interface DishOrderContract {
    interface IView extends IBaseView {

        void showListDishOrder(List<BillDetail> billDetails);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void saveOrder();

        void pay();
    }
}
