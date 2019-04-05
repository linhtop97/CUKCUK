package vn.com.misa.cukcuklite.data.models;

import java.util.List;

/**
 * Lớp model kiểu quán ăn/nhà hàng
 * Created_by Nguyễn Bá Linh on 04/04/2019
 */
public class RestaurantType {
    private int RestaurantTypeId;
    private String RestaurantName;
    private List<Dish> Dishes;
    private List<Unit> Units;


    public RestaurantType(int restaurantTypeId, String restaurantName) {
        RestaurantTypeId = restaurantTypeId;
        RestaurantName = restaurantName;
    }

    public int getRestaurantTypeId() {
        return RestaurantTypeId;
    }

    public RestaurantType setRestaurantTypeId(int restaurantTypeId) {
        RestaurantTypeId = restaurantTypeId;
        return this;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public RestaurantType setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
        return this;
    }

    public List<Dish> getDishes() {
        return Dishes;
    }

    public RestaurantType setDishes(List<Dish> dishes) {
        Dishes = dishes;
        return this;
    }

    public List<Unit> getUnits() {
        return Units;
    }

    public RestaurantType setUnits(List<Unit> units) {
        Units = units;
        return this;
    }
}
