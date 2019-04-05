package vn.com.misa.cukcuklite.data.models;

/**
 * Lớp model kiểu quán ăn/nhà hàng
 * Created_by Nguyễn Bá Linh on 04/04/2019
 */
public class RestaurantType {
    private int mRestaurantTypeId;
    private String mRestaurantTypeName;

    public RestaurantType() {

    }

    public RestaurantType(String restaurantTypeName) {
        mRestaurantTypeName = restaurantTypeName;
    }

    public RestaurantType(int restaurantTypeId, String restaurantTypeName) {
        mRestaurantTypeId = restaurantTypeId;
        mRestaurantTypeName = restaurantTypeName;
    }

    public int getRestaurantTypeId() {
        return mRestaurantTypeId;
    }

    public void setRestaurantTypeId(int restaurantTypeId) {
        mRestaurantTypeId = restaurantTypeId;
    }

    public String getRestaurantTypeName() {
        return mRestaurantTypeName;
    }

    public void setRestaurantTypeName(String restaurantTypeName) {
        mRestaurantTypeName = restaurantTypeName;
    }
}
