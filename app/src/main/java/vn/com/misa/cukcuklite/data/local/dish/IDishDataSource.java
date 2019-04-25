package vn.com.misa.cukcuklite.data.local.dish;

import java.util.List;

import vn.com.misa.cukcuklite.data.cukcukenum.ResultEnum;
import vn.com.misa.cukcuklite.data.models.Dish;

/**
 * Lớp định nghĩa các phương thức cho lớp thao tác với dữ liệu của bảng món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public interface IDishDataSource {
    boolean addDish(Dish dish);

    ResultEnum addDishToDatabase(Dish dish);

    ResultEnum updateDishToDatabase(Dish dish);

    boolean deleteDishById(String dishId);

    boolean updateDish(Dish dish);

    List<String> getAllDishName();

    List<Dish> getAllDish();

    boolean isDishIfExists(String dishName);

    boolean deleteAllDish();

    Dish getDishById(String dishId);

    List<String> getAllDishId();
}