package vn.com.misa.cukcuklite.screen.menu;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.Dish;

public interface IMenuContract {
    interface IView extends IBaseView {
        void showDish(List<Dish> dishes);
    }

    interface IPresenter extends IBasePresenter<IView> {

    }
}
