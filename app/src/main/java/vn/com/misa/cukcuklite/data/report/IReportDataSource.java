package vn.com.misa.cukcuklite.data.report;

import java.util.ArrayList;

import vn.com.misa.cukcuklite.data.models.ReportCurrent;
import vn.com.misa.cukcuklite.data.models.ReportDetail;

/**
 * Danh sách các phương thức cung cấp cho việc lấy báo cáo
 *
 * @created_by nblinh on 4/17/2019
 */
public interface  IReportDataSource {

    // Hôm nay
    ArrayList<ReportDetail> getReportToday();

    // Hôm qua
    ArrayList<ReportDetail> getReportYeaterday();

    // Tuần này
    ArrayList<ReportDetail> getReportThisWeek();

    // Tuần trước
    ArrayList<ReportDetail> getReportLastWeek();

    // Tháng này
    ArrayList<ReportDetail> getReportThisMonth();

    // Tháng trước
    ArrayList<ReportDetail> getReportLastMonth();

    // Năm nay
    ArrayList<ReportDetail> getReportThisYear();

    // Năm trước
    ArrayList<ReportDetail> getReportLastYear();

    // Gần đây filter
    ArrayList<ReportCurrent> getOverviewReport();

    // Lấy thông tin báo cáo theo khoảng thời gian
    ArrayList<ReportDetail> getReportByDate(String startDate, String endDate);

    // Lấy thông tin báo cáo theo ngày xác định
    ArrayList<ReportDetail> getReportDetailDate(String date);

}
