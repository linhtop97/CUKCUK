package vn.com.misa.cukcuklite.screen.report;

import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.ParamReport;

public interface IReportContract {
    interface IView extends IBaseView {
        void setParamReport(List<ParamReport> paramReports);
    }

    interface IPresenter extends IBasePresenter<IView> {
    }
}
