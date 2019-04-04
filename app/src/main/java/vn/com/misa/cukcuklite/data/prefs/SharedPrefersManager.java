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

    /**
     * Phương thức câp nhật trạng thái đăng nhập của ứng dụng
     * Created_by Nguyễn Bá Linh on 04/04/2019
     *
     * @param isLoginSuccess - trạng thái đăng nhập
     */
    public void setIsLoginSuccess(Boolean isLoginSuccess) {
        mSharedPrefsHelper.put(SharedPrefsKey.PREF_IS_LOGIN_SUCCESS, isLoginSuccess);
    }

    /**
     * Phương thức kiểm tra trạng thái đăng nhập của ứng dụng
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    public boolean getIsLoginSuccess() {
       return mSharedPrefsHelper.get(SharedPrefsKey.PREF_IS_LOGIN_SUCCESS, Boolean.class);
    }
}
