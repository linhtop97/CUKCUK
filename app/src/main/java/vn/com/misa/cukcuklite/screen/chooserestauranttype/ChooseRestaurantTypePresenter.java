package vn.com.misa.cukcuklite.screen.chooserestauranttype;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcuklite.data.database.IDBUtils;
import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.models.RestaurantType;

public class ChooseRestaurantTypePresenter implements IChooseRestaurantTypeContract.IPresenter, IDBUtils.ITableRestaurantTypeUtils {

    private IChooseRestaurantTypeContract.IView mView;
    private Context mContext;
    private SQLiteDBManager mSQLiteDBManager;


    public ChooseRestaurantTypePresenter(Context context) {
        mContext = context;
        mSQLiteDBManager = SQLiteDBManager.getInstance();
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
        List<RestaurantType> restaurantTypes = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from tblRestaurantType", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                RestaurantType restaurantType = new RestaurantType(cursor.getInt(cursor.getColumnIndex(RESTAURANT_TYPE_ID)),
                        cursor.getString(cursor.getColumnIndex(RESTAURANT_TYPE_NAME)));
                restaurantTypes.add(restaurantType);
                cursor.moveToNext();
            }
            cursor.close();
            mView.hideLoading();
            mView.showListRestaurantType(restaurantTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
