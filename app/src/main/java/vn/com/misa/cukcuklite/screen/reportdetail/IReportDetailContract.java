package vn.com.misa.cukcuklite.screen.reportdetail;

import java.util.Date;
import java.util.List;

import vn.com.misa.cukcuklite.data.models.ReportDetail;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Báo cáo chi tiết ‐ @created_by Hoàng Hiệp on
 * 4/15/2019
 */
interface IReportDetailContract {

  interface IView {

    void onLoadDataDone(List<ReportDetail> reportDetails);
  }

  interface IPresenter {

    void loadData(Date[] dates);
  }
}
