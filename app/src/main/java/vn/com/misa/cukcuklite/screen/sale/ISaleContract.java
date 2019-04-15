package vn.com.misa.cukcuklite.screen.sale;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.Order;

public interface ISaleContract {
    interface IView extends IBaseView {
        void showListOrder(List<Order> orders);
    }

    interface IPresenter extends IBasePresenter<IView> {

    }
}
