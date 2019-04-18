package vn.com.misa.cukcuklite.screen.authentication.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.unit.UnitDataSource;

public class LoginPresenter implements ILoginContract.IPresenter {

    private ILoginContract.IView mView;
    private Context mContext;
    private DishDataSource mDishDataSource;
    private UnitDataSource mUnitDataSource;

    public LoginPresenter(Context context) {
        mContext = context;
        mDishDataSource = DishDataSource.getInstance();
        mUnitDataSource = UnitDataSource.getInstance();
    }

    @Override
    public void loginWithFacebook(AccessToken accessToken) {
        mView.showLoading();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            mDishDataSource.deleteAllDish();
//                            mUnitDataSource.deleteAllUnit();
//                            SharedPrefersManager.getInstance(mContext).setAlreadyHasData(false);
                            mView.loginSuccess();

                        } else {
                            mView.receiveMessage(R.string.something_went_wrong);
                        }
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void login(String emailPhone, String password) {

    }

    @Override
    public void setView(ILoginContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    @Override
    public void onStart() {

    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    @Override
    public void onStop() {

    }
}
