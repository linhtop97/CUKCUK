package vn.com.misa.cukcuklite.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.prefs.SharedPrefersManager;
import vn.com.misa.cukcuklite.screen.chooserestauranttype.ChooseRestaurantTypeActivity;
import vn.com.misa.cukcuklite.screen.introduction.IntroductionActivity;
import vn.com.misa.cukcuklite.utils.AppConstants;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình chờ
 * Created_by Nguyễn Bá Linh on 03/04/2019
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private Navigator mNavigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mNavigator = new Navigator(this);
        startMainScreen();
    }

    /**
     * Phương thức hiển thị màn hình chính của ứng dụng sau khi màn hình splash kết thúc
     * Created_by Nguyễn Bá Linh on 03/04/2019
     */
    private void startMainScreen() {
        try {
            final boolean isLoginBefore = SharedPrefersManager.getInstance(this).getIsLoginSuccess();
            int SPLASH_DISPLAY_LENGTH = 1500;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //kiểm tra user dã đăng nhập trước đó hay chưa, nếu chưa thì hiển thị màn hình
                    //giới thiệu, rồi thì lấy thông tin đăng nhập
                    if (!isLoginBefore) {
                        mNavigator.startActivity(IntroductionActivity.class, Navigator.ActivityTransition.NONE);
                    } else {
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setClass(getApplicationContext(), ChooseRestaurantTypeActivity.class);
                        intent.putExtra(AppConstants.EXTRA_LOGIN_SUCCESS, true);
                        mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
                    }
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
