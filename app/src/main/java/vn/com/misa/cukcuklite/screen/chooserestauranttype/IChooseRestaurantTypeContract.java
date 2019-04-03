package vn.com.misa.cukcuklite.screen.chooserestauranttype;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.RestaurantType;

public class IChooseRestaurantTypeContract {
    interface IView extends IBaseView {

        void showLoadingRestaurantType();

        void hideLoadingRestaurantType();

        void showListRestaurantType(List<RestaurantType> restaurantTypeList);

    }

    interface IPresenter extends IBasePresenter<IView> {

    }
}
