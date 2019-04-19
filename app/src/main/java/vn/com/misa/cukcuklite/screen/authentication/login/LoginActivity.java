package vn.com.misa.cukcuklite.screen.authentication.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Collections;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.prefs.SharedPrefersManager;
import vn.com.misa.cukcuklite.screen.chooserestauranttype.ChooseRestaurantTypeActivity;
import vn.com.misa.cukcuklite.screen.main.MainActivity;
import vn.com.misa.cukcuklite.utils.AppConstants;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình đăng nhập
 * Created_by Nguyễn Bá Linh on 03/04/2019
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginContract.IView {

    private static final String TAG = "LoginActivity";
    private ImageView ivBack;
    private LinearLayout llFacebookLogin;
    private CallbackManager callbackManager;
    private LoginPresenter mPresenter;
    private Navigator mNavigator;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNavigator = new Navigator(this);
        mPresenter = new LoginPresenter(this);
        mPresenter.setView(this);
        initViews();
        initEvents();
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    private void initEvents() {
        try {
            ivBack.setOnClickListener(this);
            llFacebookLogin.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    private void initViews() {
        try {
            ivBack = findViewById(R.id.ivBack);
            llFacebookLogin = findViewById(R.id.llFacebookLogin);
            initFacebookLoginCallback();
            initProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo progressbar
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    private void initProgressBar() {
        try {
            mDialog = new ProgressDialog(this) {
                @Override
                public void onBackPressed() {
                    super.onBackPressed();
                }
            };
            mDialog.setMessage(getString(R.string.login_in_process));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo trình đăng nhập facebook
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    private void initFacebookLoginCallback() {
        try {
            callbackManager = CallbackManager.Factory.create();
            LoginManager loginManager = LoginManager.getInstance();
            //đăng kí callback
            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    mPresenter.loginWithFacebook(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    //nếu người dùng dừng việc đăng nhập thì không làm gì cả
                }

                @Override
                public void onError(FacebookException exception) {
                    exception.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created_by Nguyễn Bá Linh on 04/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.llFacebookLogin:
                loginWithFacebook();
                break;
        }
    }


    /**
     * Phương thức đăng nhập với facebook
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    private void loginWithFacebook() {
        LoginManager.getInstance()
                .logInWithReadPermissions(this, Collections.singleton("public_profile"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created_by Nguyễn Bá Linh on 04/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToastOnTopScreen(message);
    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    @Override
    public void showLoading() {
        try {
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    @Override
    public void hideLoading() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý khi đăng nhập thành công
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    @Override
    public void loginSuccess() {
        try {
            SharedPrefersManager.getInstance(this).setIsLoginSuccess(true);
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            if (SharedPrefersManager.getInstance(this).getIsAlreadyHasData()) {
                intent.setClass(this, MainActivity.class);
            } else {
                intent.setClass(this, ChooseRestaurantTypeActivity.class);
            }
            intent.putExtra(AppConstants.EXTRA_LOGIN_SUCCESS, true);
            mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
