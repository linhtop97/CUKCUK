package vn.com.misa.cukcuklite.screen.main;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;

public interface IMainContract {
    interface IView extends IBaseView {
    }

    interface IPresenter extends IBasePresenter<IView> {

    }
}
