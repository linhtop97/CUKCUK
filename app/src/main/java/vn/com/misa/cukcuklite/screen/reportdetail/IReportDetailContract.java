package vn.com.misa.cukcuklite.screen.reportdetail;

import java.util.Date;
import java.util.List;

import vn.com.misa.cukcuklite.base.IBasePresenter;
import vn.com.misa.cukcuklite.base.IBaseView;
import vn.com.misa.cukcuklite.data.models.ReportDetail;

interface IReportDetailContract {

  interface IView extends IBaseView {

    void onLoadDataDone(List<ReportDetail> reportDetails);
  }

  interface IPresenter extends IBasePresenter<IView> {

    void loadData(Date[] dates);
  }
}
