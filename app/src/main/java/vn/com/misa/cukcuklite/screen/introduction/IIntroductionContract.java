package vn.com.misa.cukcuklite.screen.introduction;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;

public interface IIntroductionContract {
    interface IView extends IBaseView {
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
