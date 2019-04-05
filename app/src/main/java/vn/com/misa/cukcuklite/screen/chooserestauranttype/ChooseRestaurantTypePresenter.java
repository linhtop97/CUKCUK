package vn.com.misa.cukcuklite.screen.chooserestauranttype;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.database.unit.UnitDataSource;
import vn.com.misa.cukcuklite.data.models.RestaurantType;
import vn.com.misa.cukcuklite.data.models.Unit;
import vn.com.misa.cukcuklite.utils.CommonsUtils;

public class ChooseRestaurantTypePresenter implements IChooseRestaurantTypeContract.IPresenter {

    private IChooseRestaurantTypeContract.IView mView;
    private Context mContext;
    private SQLiteDBManager mSQLiteDBManager;
    private UnitDataSource mUnitDataSource;

    ChooseRestaurantTypePresenter(Context context) {
        mContext = context;
        mUnitDataSource = new UnitDataSource();
    }

    @Override
    public void setView(IChooseRestaurantTypeContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 05/04/2019
     */
    @Override
    public void onStart() {
        mView.showLoading();
        //lấy danh sách loại quán ăn/nhà hàng và hiển thị ra màn hình
        try {
            List<RestaurantType> restaurantTypes = getListRestaurant();
            mView.hideLoading();
            mView.showListRestaurantType(restaurantTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public void insertAllData(RestaurantType restaurantType) {
        mView.showLoading();
        List<Unit> units = restaurantType.getUnits();
        int unitSize = units.size();
        for (int i = 0; i < unitSize; i++) {
            mUnitDataSource.addUnit(units.get(i));
        }
        mView.hideLoading();
        mView.startMainActivity();
    }

    /**
     * Phương thức lấy danh sách nhà hàng/quán ăn mặc định cho ứng dụng
     * Created_by Nguyễn Bá Linh on 05/04/2019
     */
    private List<RestaurantType> getListRestaurant() {
        try {
            String jsonRestaurantType = CommonsUtils.loadJSONFromAsset(mContext, "jsons/restaurant_type.json");
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<RestaurantType>>() {
            }.getType();
            return gson.fromJson(jsonRestaurantType, collectionType);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
