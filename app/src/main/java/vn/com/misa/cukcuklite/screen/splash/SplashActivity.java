package vn.com.misa.cukcuklite.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.prefs.SharedPrefersManager;
import vn.com.misa.cukcuklite.screen.chooserestauranttype.ChooseRestaurantTypeActivity;
import vn.com.misa.cukcuklite.screen.dishorder.DishOrderActivity;
import vn.com.misa.cukcuklite.screen.introduction.IntroductionActivity;
import vn.com.misa.cukcuklite.utils.AppConstants;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình chờ
 * Created_by Nguyễn Bá Linh on 03/04/2019
 */
public class SplashActivity extends AppCompatActivity implements ISplashContract.IView {

    private static final String TAG = "SplashActivity";
    private Navigator mNavigator;
    private SplashPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPresenter = new SplashPresenter(this);
        mPresenter.setView(this);
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
            final boolean isAlreadyHasData = SharedPrefersManager.getInstance(this).getIsAlreadyHasData();
            int SPLASH_DISPLAY_LENGTH = 1500;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //kiểm tra user chưa đăng nhập và chưa chọn loại nhà hàng(chưa có dữ liệu gì cả)
                    //thì hiển thị màn hình giới thiệu
                    if (!isLoginBefore && !isAlreadyHasData) {
                        mNavigator.startActivity(IntroductionActivity.class, Navigator.ActivityTransition.NONE);
                        finish();
                    }
                    //kiểm tra user chưa đăng nhập và và đã chọn loại nhà hàng
                    //thì hiển thị màn hình chính của ứng dụng
                    if (!isLoginBefore && isAlreadyHasData) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), DishOrderActivity.class);
                        mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
                        finish();
                    }

                    //kiểm tra user đã đăng nhập và chưa chọn loại nhà hàng(chưa có dữ liệu gì cả)
                    //thì hiển thị màn hình chọn loại nhà hàng
                    if (isLoginBefore && !isAlreadyHasData) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), ChooseRestaurantTypeActivity.class);
                        intent.putExtra(AppConstants.EXTRA_LOGIN_SUCCESS, true);
                        mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
                        finish();
                    }
                    //kiểm tra user đã đăng nhập và đã chọn loại nhà hàng
                    //hiển thị màn hình chính của ứng dụng
                    if (isLoginBefore && isAlreadyHasData) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), DishOrderActivity.class);
                        mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
                        finish();
                    }
                }
            }, SPLASH_DISPLAY_LENGTH);
            //
            mPresenter.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created_by Nguyễn Bá Linh on 15/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created_by Nguyễn Bá Linh on 15/04/2019
     */
    @Override
    public void showLoading() {

    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created_by Nguyễn Bá Linh on 15/04/2019
     */
    @Override
    public void hideLoading() {

    }
}
