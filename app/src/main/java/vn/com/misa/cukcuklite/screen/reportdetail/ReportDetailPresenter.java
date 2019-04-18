package vn.com.misa.cukcuklite.screen.reportdetail;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import vn.com.misa.cukcuklite.data.models.ReportDetail;

public class ReportDetailPresenter implements IReportDetailContract.IPresenter {

    private static final String TAG = "ReportDetailPresenter";
    private IReportDetailContract.IView mView;

    /**
     * Load dữ liệu từ 2 mốc thời gian
     * Created_by Nguyễn Bá Linh on 18/04/2019
     *
     * @param dates: mảng 2 mốc thời gian
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadData(final Date[] dates) {
    }

    /**
     * Mục đích method: Sắp xếp lại list
     * Created_by Nguyễn Bá Linh on 18/04/2019
     *
     * @param reportDetails: mảng truyền vào
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

    /**
     * Phương thức đặt view cho presenter
     * Created_by Nguyễn Bá Linh on 18/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IReportDetailContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    @Override
    public void onStart() {

    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    @Override
    public void onStop() {

    }
}
