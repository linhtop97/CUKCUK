package vn.com.misa.cukcuklite.screen.choosedishdefault;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.Dish;

public interface IChooseDishDefaultContract {

    interface IView extends IBaseView {
        void showMainScreen();

        void showDish(List<Dish> dishes);
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
