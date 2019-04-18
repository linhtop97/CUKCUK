package vn.com.misa.cukcuklite.screen.report;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.cukcukenum.ParamReportEnum;
import vn.com.misa.cukcuklite.data.cukcukenum.ReportTotalEnum;
import vn.com.misa.cukcuklite.data.models.ParamReport;
import vn.com.misa.cukcuklite.data.models.ReportCurrent;
import vn.com.misa.cukcuklite.data.models.ReportTotal;
import vn.com.misa.cukcuklite.screen.dialogs.dialogparamreport.ParamReportDialog;
import vn.com.misa.cukcuklite.screen.dialogs.dialogpickdate.FromToPickerDialog;
import vn.com.misa.cukcuklite.screen.main.MainActivity;
import vn.com.misa.cukcuklite.screen.reportcurrent.ReportCurrentFragment;
import vn.com.misa.cukcuklite.screen.reportdetail.ReportDetailActivity;
import vn.com.misa.cukcuklite.screen.reportdetail.ReportDetailFragment;
import vn.com.misa.cukcuklite.screen.reporttotal.ReportTotalFragment;
import vn.com.misa.cukcuklite.utils.Navigator;

import static android.provider.Settings.System.DATE_FORMAT;

public class ReportFragment extends Fragment implements IReportContract.IView, ReportCurrentFragment.OnClickCurrentReport, View.OnClickListener {

    private static final String TAG = "ReportFragment";
    private static final String DATE_PICKER = "DATE_PICKER";
    private TextView tvTimeValue;
    private List<ParamReport> mParamReports;
    private ReportPresenter mPresenter;
    private Navigator mNavigator;
    private MainActivity mActivity;
    private int lastParamSelected;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        try {
            View view = null;
            view = inflater.inflate(R.layout.fragment_report, container, false);
            view.findViewById(R.id.lnTime).setOnClickListener(this);
            mNavigator = new Navigator(this);
            mPresenter = new ReportPresenter();
            mPresenter.setView(this);
            mPresenter.onStart();
            initViews(view);
            return view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    private void initViews(View view) {
        try {
            Log.d(TAG, "initViews: ");
            tvTimeValue = view.findViewById(R.id.tvTimeValue);
            ReportCurrentFragment reportCurrentFragment = ReportCurrentFragment.newInstance();
            reportCurrentFragment.setOnClickCurrentReport(this);
            loadFragment(reportCurrentFragment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created_by Nguyễn Bá Linh on 18/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnTime:
                try {
                    final ParamReportDialog dialog = new ParamReportDialog();
                    dialog.setParamReports(mParamReports);
                    dialog.setCallBack(new ParamReportDialog.ParamCallBack() {
                        @Override
                        public void onClick(ParamReport paramReport) {
                            if (paramReport.getParamType() == ParamReportEnum.CURRENT) {
                                tvTimeValue.setText(paramReport.getTitleReportDetail());
                                ReportCurrentFragment reportCurrentFragment = ReportCurrentFragment.newInstance();
                                reportCurrentFragment.setOnClickCurrentReport(ReportFragment.this);
                                loadFragment(reportCurrentFragment);
                            } else if (paramReport.getParamType() == ParamReportEnum.OTHER) {
                                FromToPickerDialog fromToPickerDialog = new FromToPickerDialog();
                                fromToPickerDialog
                                        .setOnClickAcceptPickDate(new FromToPickerDialog.OnClickAcceptPickDate() {
                                            @Override
                                            public void onPickDate(Date fromDate, Date toDate) {
                                                Date[] dates = new Date[2];
                                                dates[0] = fromDate;
                                                dates[1] = toDate;
                                                loadFragment(ReportDetailFragment.newInstance(dates));
                                                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat(
                                                        DATE_FORMAT);
                                                tvTimeValue
                                                        .setText(dateFormat.format(fromDate).concat("-").concat(dateFormat.format(toDate)));
                                                setSelected(7, mParamReports);
                                            }
                                        });
                                fromToPickerDialog.show(mActivity.getSupportFragmentManager(), DATE_PICKER);
                            } else {
                                tvTimeValue.setText(paramReport.getTitleReportDetail());
                                loadFragment(ReportTotalFragment.newInstance(paramReport));
                            }
                        }
                    });
                    dialog.show(mActivity.getSupportFragmentManager(), getString(R.string.icon_fragment));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * Mục đích method: Replace Fragment
     * Created_by Nguyễn Bá Linh on 18/04/2019
     *
     * @param fragment Fragment cần thay thế
     */
    private void loadFragment(Fragment fragment) {
        try {
            mNavigator.addFragment(R.id.frContent, fragment, false, Navigator.NavigateAnim.NONE,
                    fragment.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Xử lý sự kiện
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    @Override
    public void onClick(ReportCurrent reportCurrent) {
        try {
            switch (reportCurrent.getParamType()) {
                case TODAY:
                    ReportTotal reportTotalToday = new ReportTotal(ReportTotalEnum.TODAY);
                    reportTotalToday.setTitleReportDetail(getString(R.string.param_report_today));
                    reportTotalToday.setFromDate(reportCurrent.getFromDate());
                    reportTotalToday.setToDate(reportCurrent.getToDate());
                    startActivity(ReportDetailActivity.getIntent(getContext(), reportTotalToday));
                    break;
                case THIS_WEEK:
                    setSelected(1, mParamReports);
                    tvTimeValue.setText(mParamReports.get(1).getTitleReportDetail());
                    loadFragment(ReportTotalFragment.newInstance(mParamReports.get(1)));
                    break;
                case THIS_YEAR:
                    setSelected(5, mParamReports);
                    tvTimeValue.setText(mParamReports.get(5).getTitleReportDetail());
                    loadFragment(ReportTotalFragment.newInstance(mParamReports.get(5)));
                    break;
                case YESTERDAY:
                    ReportTotal reportTotalYes = new ReportTotal(ReportTotalEnum.YESTERDAY);
                    reportTotalYes.setTitleReportDetail(getString(R.string.param_report_yesterday));
                    reportTotalYes.setFromDate(reportCurrent.getFromDate());
                    reportTotalYes.setToDate(reportCurrent.getToDate());
                    startActivity(ReportDetailActivity.getIntent(getContext(), reportTotalYes));
                    break;
                case THIS_MONTH:
                    setSelected(3, mParamReports);
                    tvTimeValue.setText(mParamReports.get(3).getTitleReportDetail());
                    loadFragment(ReportTotalFragment.newInstance(mParamReports.get(3)));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Set vị trí chọn của dialog chọn khoảng thời gian
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    private void setSelected(int position, List<ParamReport> mParamReports) {
        try {
            if (mParamReports != null && mParamReports.size() > 0) {
                mParamReports.get(lastParamSelected).setSelected(false);
                lastParamSelected = position;
                mParamReports.get(lastParamSelected).setSelected(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức gán các lựa chọn báo cáo
     * Created_by Nguyễn Bá Linh on 18/04/2019
     *
     * @param paramReports - danh sách các lựa chọn báo cáo
     */
    @Override
    public void setParamReport(List<ParamReport> paramReports) {
        if (paramReports != null) {
            mParamReports = paramReports;
        }
    }

    @Override
    public void receiveMessage(int message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }
}