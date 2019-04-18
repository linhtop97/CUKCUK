package vn.com.misa.cukcuklite.screen.adddish;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.data.models.Unit;

/**
 * MVP interface cho màn hình thêm món ăn
 * Created_by Nguyễn Bá Linh on 9/04/2019
 */
public interface IAddDishContract {
    interface IView extends IBaseView {

        void dishNameEmpty();

        void addDishSuccess();

        void addDishFailed(int error);

        void setUnit(Unit unit);

        void upDateDishSuccess();

        void deleteDishSuccess();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void addDish(Dish dish);

        void updateDish(Dish dish);

        void deleteDish(String dishId);

        String getUnitName(String unitId);
    }
}