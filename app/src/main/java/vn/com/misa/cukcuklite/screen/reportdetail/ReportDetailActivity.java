package vn.com.misa.cukcuklite.screen.reportdetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình Báo các chi tiết
 * Created_by Nguyễn Bá Linh on 18/04/2019
 */
public class ReportDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitleReport;
    private Navigator mNavigator;

    /**
     * Mục đích method: Lấy intent
     * Created_by Nguyễn Bá Linh on 18/04/2019
     *
     * @param context     Context
     * @param reportTotal Đối tượng báo cáo
     * @return Trả về intent trỏ tới ReportDetailActivity
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
        mNavigator = new Navigator(this);
        initViews();
        initEvents();

    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    private void initEvents() {
        findViewById(R.id.ivBack).setOnClickListener(this);
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    private void initViews() {
        try {
            tvTitleReport = findViewById(R.id.tvTitleReport);
            ReportTotal reportTotal = (ReportTotal) getIntent().getSerializableExtra(AppConstants.EXTRA_REPORT_TOTAL);
            if (reportTotal != null) {
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
                mNavigator.addFragment(R.id.rlContent, ReportDetailFragment.newInstance(dates),
                        false, Navigator.NavigateAnim.NONE, ReportDetailFragment.class.getSimpleName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created_by Nguyễn Bá Linh on 18/04/2019
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
