package vn.com.misa.cukcuklite.screen.authentication.login;

import com.facebook.AccessToken;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;

/**
 * MVP interface cho màn hình đăng nhập
 * Created_by Nguyễn Bá Linh on 9/04/2019
 */
public interface ILoginContract {
    interface IView extends IBaseView {
        void loginSuccess();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void loginWithFacebook(AccessToken accessToken);

        void login(String emailPhone, String password);
    }
}
