package vn.com.misa.cukcuklite.screen.reporttotal;
import java.util.List;

import vn.com.misa.cukcuklite.data.models.ParamReport;
import vn.com.misa.cukcuklite.data.models.ReportTotal;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Báo cáo
 * <p>
 * ‐ @created_by Hoàng Hiệp on 7/4/2019
 */
interface IReportTotalContract {

  interface IView {

    void onLoadDataDone(List<ReportTotal> reportTotals);
  }

  interface IPresenter {

    void loadData(ParamReport paramReport);
  }
}
