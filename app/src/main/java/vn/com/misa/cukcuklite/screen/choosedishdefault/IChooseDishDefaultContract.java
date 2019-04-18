package vn.com.misa.cukcuklite.screen.choosedishdefault;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.Dish;

/**
 * MVP interface cho màn hình lựa chọn món ăn mặc định
 * Created_by Nguyễn Bá Linh on 18/04/2019
 */
public interface IChooseDishDefaultContract {

    interface IView extends IBaseView {
        void showMainScreen();

        void showDish(List<Dish> dishes);
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
