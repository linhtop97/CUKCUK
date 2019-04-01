package vn.com.misa.cukcuklite.screen.splash;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;

public interface ISplashContract {
    interface IView extends IBaseView {
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
