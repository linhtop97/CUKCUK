package vn.com.misa.cukcuklite.screen.reporttotal;

import java.util.List;

import vn.com.misa.cukcuklite.data.models.ParamReport;
import vn.com.misa.cukcuklite.data.models.ReportTotal;

/**
 * MVP interface cho màn hình báo cáo tổng quan
 * Created_by Nguyễn Bá Linh on 18/04/2019
 */
interface IReportTotalContract {

    interface IView {

        void onLoadDataDone(List<ReportTotal> reportTotals);
    }

    interface IPresenter {

        void loadData(ParamReport paramReport);
    }
}
