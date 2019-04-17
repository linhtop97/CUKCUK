package vn.com.misa.cukcuklite.screen.reportdetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.cukcukenum.ReportTotalEnum;
import vn.com.misa.cukcuklite.data.models.ReportTotal;
import vn.com.misa.cukcuklite.utils.AppConstants;

/**
 * - Mục đích Class : Màn hình Báo các chi tiết - @created_by Hoàng Hiệp on 4/15/2019
 */
public class ReportDetailActivity extends AppCompatActivity implements View.OnClickListener {

  private TextView tvTitleReport;

  /**
   * Mục đích method: Lấy intent
   *
   * @param context Context
   * @param reportTotal Đối tượng báo cáo
   * @return Trả về intent trỏ tới ReportDetailActivity
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  public static Intent getIntent(Context context, ReportTotal reportTotal) {
    Intent intent = new Intent(context, ReportDetailActivity.class);
    intent.putExtra(AppConstants.EXTRA_REPORT_TOTAL, reportTotal);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_report_detail);
    initView();
    initListener();

  }

  /**
   * Mục đích method: Bắt sự kiện
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initListener() {
    findViewById(R.id.ivBack).setOnClickListener(this);
  }

  /**
   * Mục đích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initView() {
    tvTitleReport = findViewById(R.id.tvTitleReport);
    ReportTotal reportTotal = (ReportTotal) getIntent().getSerializableExtra(AppConstants.EXTRA_REPORT_TOTAL);
    Date[] dates = new Date[2];
    dates[0] = reportTotal.getFromDate();
    dates[1] = reportTotal.getToDate();
    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(
        AppConstants.DATE_FORMAT);
    if (reportTotal.getType() == ReportTotalEnum.YEAR) {
      tvTitleReport.setText(
          String.valueOf(
              reportTotal.getTitleReportDetail() + " (" + dateFormat
                  .format(reportTotal.getFromDate())
                  + " - " + dateFormat.format(reportTotal.getToDate()) + ")"));
    } else {
      tvTitleReport.setText(
          String.valueOf(
              reportTotal.getTitleReportDetail() + " (" + dateFormat
                  .format(reportTotal.getFromDate())
                  + ")"));
    }
    loadFragment(ReportDetailFragment.newInstance(dates));
  }

  /**
   * Mục đích method: Replace Fragment
   *
   * @param fragment Fragment cần thay thế
   * @created_by Hoàng Hiệp on 4/5/2019
   */
  private void loadFragment(Fragment fragment) {
    try {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
      transaction.replace(R.id.rlContent, fragment);
      transaction.addToBackStack(null);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Xử lý sự kiện
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.ivBack:
        finish();
        break;
    }
  }

  /**
   * Mục đích method: Xử lý xự kiện khi ấn nút back
   *
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  @Override
  public void onBackPressed() {
    finish();
  }
}
