package vn.com.misa.cukcuklite.screen.introduction;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;

/**
 * MVP interface cho màn hình giới thiệu ứng dụng
 * Created_by Nguyễn Bá Linh on 18/04/2019
 */
public interface IIntroductionContract {
    interface IView extends IBaseView {
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
