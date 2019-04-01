package vn.com.misa.cukcuklite.data.prefs;

import android.content.Context;

public class SharedPrefersManager {

    public static SharedPrefersManager sInstance;
    private SharedPrefsHelper mSharedPrefsHelper;

    /**
     * Phương thức khởi tạo đối tượng SharedPrefsHelper
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param context - context
     */
    private SharedPrefersManager(Context context) {
        mSharedPrefsHelper = new SharedPrefsHelper(context);
    }

    /**
     * Phương thức khởi tạo và nhận đối tượng SharedPrefersManager
     *
     * @param context
     * @return
     */
    public static synchronized SharedPrefersManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefersManager(context);
        }
        return sInstance;
    }


}
