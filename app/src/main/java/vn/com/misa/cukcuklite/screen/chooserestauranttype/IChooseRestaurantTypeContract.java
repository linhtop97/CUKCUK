package vn.com.misa.cukcuklite.screen.chooserestauranttype;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.data.models.RestaurantType;
import vn.com.misa.cukcuklite.data.models.Unit;

/**
 * MVP interface cho màn hình lựa chọn quán ăn/nhà hàng
 * Created_by Nguyễn Bá Linh on 18/04/2019
 */
public interface IChooseRestaurantTypeContract {
    interface IView extends IBaseView {
        void showListRestaurantType(List<RestaurantType> restaurantTypeList);

        void showDishDefaultActivity();
    }

    interface IPresenter extends IBasePresenter<IView> {

        void insertAllUnit(List<Unit> units);

        void insertAllDish(List<Dish> dishes);
    }
}
