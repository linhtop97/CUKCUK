package vn.com.misa.cukcuklite.screen.main;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;

/**
 * MVP interface cho màn hình chính
 * Created_by Nguyễn Bá Linh on 18/04/2019
 */
public interface IMainContract {
    interface IView extends IBaseView {
        void goToLoginScreen();
    }

    interface IPresenter extends IBasePresenter<IView> {
        void clearData();
    }
}
