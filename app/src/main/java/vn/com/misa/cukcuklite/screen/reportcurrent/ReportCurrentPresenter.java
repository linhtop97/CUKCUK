package vn.com.misa.cukcuklite.screen.reportcurrent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcuklite.data.dao.IReportDataSource;
import vn.com.misa.cukcuklite.data.dao.ReportDAO;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.ReportCurrent;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Báo cáo
 * <p>
 * ‐ @created_by Hoàng Hiệp on 7/4/2019
 */
public class ReportCurrentPresenter implements IReportCurrentContract.IPresenter {

  private static final String TAG = ReportCurrentPresenter.class.getName();

  private IReportCurrentContract.IView mView;
  private Context mContext;

  IReportDataSource mDataReportSource;

  public ReportCurrentPresenter(IReportCurrentContract.IView view, Context context) {
    mView = view;
    mContext = context;
    mDataReportSource = new ReportDAO();
  }

  /**
   * Mục đích method: Trả về danh sách ReportCurrent
   *
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  @SuppressLint("StaticFieldLeak")
  @Override
  public void getListReportCurrent() {
    new AsyncTask<Void, Void, List<ReportCurrent>>() {
      @Override
      protected List<ReportCurrent> doInBackground(Void... voids) {
        List<ReportCurrent> reportCurrentList = mDataReportSource.getOverviewReport();
        Log.d(TAG, "doInBackground: " + reportCurrentList.size());
        if(reportCurrentList != null && reportCurrentList.size() > 0){
          return reportCurrentList;
        }else{
          return new ArrayList<>();
        }
      }

      @Override
      protected void onPostExecute(List<ReportCurrent> reportCurrents) {
        super.onPostExecute(reportCurrents);
        mView.onLoadReportCurrentDone(reportCurrents);
      }
    }.execute();


  }

  /**
   * Mục đích method: Tính tổng số tiền
   *
   * @param bills : danh sách hóa đơn
   * @return amount: tổng só tiền của hóa đơn
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  private long getAmount(List<Bill> bills) {
    try {
      long amount = 0;
      for (Bill bill : bills) {
       amount += bill.getTotalMoney();
      }
      return amount;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }
}
