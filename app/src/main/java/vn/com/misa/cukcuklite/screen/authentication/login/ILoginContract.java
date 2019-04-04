package vn.com.misa.cukcuklite.screen.authentication.login;

import com.facebook.AccessToken;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;

public interface ILoginContract {
    interface IView extends IBaseView {
        void loginSuccess();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void loginWithFacebook(AccessToken accessToken);

        void login(String emailPhone, String password);
    }
}
