package vn.com.misa.cukcuklite.data.dao;

import java.util.ArrayList;

/**
 * Danh sách các phương thức cung cấp cho việc lấy báo cáo
 *
 * @created_by nblinh on 4/17/2019
 */
public interface IReportDataSource {

    // Hôm nay
    ArrayList<ReportDayItem> getReportToday();

    // Hôm qua
    ArrayList<ReportDayItem> getReportYeaterday();

    // Tuần này
    ArrayList<ReportDayItem> getReportThisWeek();

    // Tuần trước
    ArrayList<ReportDayItem> getReportLastWeek();

    // Tháng này
    ArrayList<ReportDayItem> getReportThisMonth();

    // Tháng trước
    ArrayList<ReportDayItem> getReportLastMonth();

    // Năm nay
    ArrayList<ReportDayItem> getReportThisYear();

    // Năm trước
    ArrayList<ReportDayItem> getReportLastYear();

    // Gần đây filter
    ArrayList<ReportOverviewItem> getOverviewReport();

    // Lấy thông tin báo cáo theo khoảng thời gian
    ArrayList<ReportDayItem> getReportByDate(String startDate, String endDate);

    // Lấy thông tin báo cáo theo ngày xác định
    ArrayList<ReportDayItem> getReportDetailDate(String date);

}
