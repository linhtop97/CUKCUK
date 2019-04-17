package vn.com.misa.cukcuklite.screen.reportdetail;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import vn.com.misa.cukcuklite.data.models.ReportDetail;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Báo cáo chi tiết ‐ @created_by Hoàng Hiệp on
 * 4/15/2019
 */
public class ReportDetailPresenter implements IReportDetailContract.IPresenter {

  private static final String TAG = ReportDetailPresenter.class.getName();
  private Context mContext;
  private IReportDetailContract.IView mView;

  public ReportDetailPresenter(Context context, IReportDetailContract.IView view) {
    mView = view;
    mContext = context;
  }

  /**
   * Mục đích method: Laod dữ liệu từ 2 mốc thời gian
   *
   * @param dates: mảng 2 mốc thời gian
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  @SuppressLint("StaticFieldLeak")
  @Override
  public void loadData(final Date[] dates) {
  }

  /**
   * Mục đích method: Sắp xếp lại list
   *
   * @param reportDetails: mảng truyền vào
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  private List<ReportDetail> makeListBeautiful(List<ReportDetail> reportDetails) {
    List<ReportDetail> list = new ArrayList<>();
    for (int i = 0; i < reportDetails.size(); i++) {
      ReportDetail reportDetail = reportDetails.get(i);
      if (i == 0) {
        list.add(reportDetail);
      } else {
        boolean isExist = false;
        for (ReportDetail detail : list) {
          if (reportDetail.getName().equals(detail.getName())) {
            isExist = true;
            detail.addQuantity(reportDetail.getQuantity());
          }
        }
        if (!isExist) {
          list.add(reportDetail);
        }
      }

    }
    Collections.sort(list, new Comparator<ReportDetail>() {
      @Override
      public int compare(ReportDetail o1, ReportDetail o2) {
        if (o1.getAmount() < o2.getAmount()) {
          return 1;
        }
        if (o1.getAmount() > o2.getAmount()) {
          return -1;
        }
        return 0;
      }
    });
    return list;
  }
}
