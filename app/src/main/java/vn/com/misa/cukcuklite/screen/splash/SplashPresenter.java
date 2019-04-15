package vn.com.misa.cukcuklite.screen.splash;

import android.content.Context;

import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.unit.UnitDataSource;

public class SplashPresenter implements ISplashContract.IPresenter {

    private Context mContext;
    private ISplashContract.IView mView;

    public SplashPresenter(Context context) {
        mContext = context;
    }

    /**
     * Phương thức đặt view cho presenter
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(ISplashContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 15/04/2019
     */
    @Override
    public void onStart() {
        try {
            //khởi tạo cấu trúc dữ liệu cho app
            initDataCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created_by Nguyễn Bá Linh on 15/04/2019
     */
    @Override
    public void onStop() {

    }

    /**
     * Phương thức khởi tạo dữ liệu cache cho ứng dụng
     * Created_by Nguyễn Bá Linh on 13/04/2019
     */
    private void initDataCache() {
        SQLiteDBManager.getInstance(mContext);
        DishDataSource.getInstance().getAllDish();
        UnitDataSource.getInstance().getAllUnit();
    }
}
