package vn.com.misa.cukcuklite;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;

import static vn.com.misa.cukcuklite.data.database.IDBUtils.DB_NAME;

public class CukCukLiteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            initDatabaseStructure();
            //cho phép đặt nguồn ảnh là vector
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            //đặt font chữ mặc định cho thư viện
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath(getString(R.string.nunito_regular))
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo các database cho ứng dụng
     * Created_by Nguyễn Bá Linh on 02/04/2019
     */
    private void initDatabaseStructure() {
        try {
            File file = this.getDatabasePath(DB_NAME);
            if (!file.exists()) {
                SQLiteDBManager manager = SQLiteDBManager.getInstance(this);
                manager.getWritableDatabase();
                manager.close();
                manager.copyDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
