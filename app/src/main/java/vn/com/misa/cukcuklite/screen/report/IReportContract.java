package vn.com.misa.cukcuklite.screen.report;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.ParamReport;

/**
 * MVP interface cho màn hình báo cáo
 * Created_by Nguyễn Bá Linh on 18/04/2019
 */
public interface IReportContract {
    interface IView extends IBaseView {
        void setParamReport(List<ParamReport> paramReports);
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
