package vn.com.misa.cukcuklite.screen.reportcurrent;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.ReportCurrent;

/**
 * Contract  trong mô hình MVP cho màn hình Báo cáo Gần đây
 * Created_by Nguyễn Bá Linh on 18/04/2019
 */
interface IReportCurrentContract {

    interface IView extends IBaseView {

        void onLoadReportCurrentDone(List<ReportCurrent> reportCurrents);
    }

    interface IPresenter extends IBasePresenter<IView> {

        void getListReportCurrent();
    }
}
