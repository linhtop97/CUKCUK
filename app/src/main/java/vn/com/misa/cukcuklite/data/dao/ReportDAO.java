package vn.com.misa.cukcuklite.data.dao;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import vn.com.misa.cukcuklite.data.cukcukenum.ParamReportEnum;
import vn.com.misa.cukcuklite.data.database.IDBUtils;
import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.models.ReportCurrent;
import vn.com.misa.cukcuklite.data.models.ReportDetail;
import vn.com.misa.cukcuklite.utils.DateUtil;

/**
 * Class xử lý các truy vấn lấy báo cáo thống kê
 *
 * @created_by nblinh on 4/17/2019
 */
public class ReportDAO implements IDBUtils.ITableBillDetailUtils, IDBUtils.ITableBillUtils, IReportDataSource {

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo theo khoảng thời gian
     *
     * @param startDate - ngày giờ bắt đầu
     * @param endDate   - ngày giờ kết thúc
     * @return - danh sách báo cáo thống kê theo từng ngày
     * @created_by nblinh on 4/17/2019
     */
    private static ArrayList<ReportDetail> getReportOverViewByDate(String startDate, String endDate) {
        try {
            ArrayList<ReportDetail> list = new ArrayList<>();

            String sql = String.format("SELECT %s,%s,sum(%s.%s) as totalMoney FROM " + BILL_TBL_NAME + "," + BILL_DETAIL_TBL_NAME + " WHERE " +
                            " %s.%s = %s.%s " + " AND " + COLUMN_STATE + " = '1' " + " AND " +
                            " date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'" + startDate + "'" + " AND " + "'" + endDate + "'" +
                            " GROUP BY " + COLUMN_DATE_CREATED,
                    COLUMN_DATE_CREATED, COLUMN_DISH_ID, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_TOTAL_MONEY,
                    BILL_TBL_NAME, IDBUtils.ITableBillUtils.COLUMN_BILL_ID, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_BILL_ID
            );

            Log.d("sql", "getReportByDate: " + sql);

            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    list.add(parseCursorToReportDetail(cursor));
                    cursor.moveToNext();
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Phương thức xử lý: Bóc dữ liệu báo cáo theo từng ngày
     *
     * @param cursor - giá trị đối tượng lấy từ sqlite
     * @return đối tượng báo cáo từng ngày
     * @created_by nblinh on 4/17/2019
     */
    private static ReportDetail parseCursorToReportDetail(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            return new ReportDetail.Builder()
                    .setDishId(cursor.getColumnIndex(COLUMN_DISH_ID) > -1 ? cursor.getString(cursor.getColumnIndex(COLUMN_DISH_ID)) : "")
                    .setDateCreated(cursor.getColumnIndex(COLUMN_DATE_CREATED) > -1 ? cursor.getString(cursor.getColumnIndex(COLUMN_DATE_CREATED)) : "")
                    .setQuantity(cursor.getColumnIndex("quantity") > -1 ? cursor.getInt(cursor.getColumnIndex("quantity")) : 0)
                    .setAmount(cursor.getColumnIndex("totalMoney") > -1 ? (long) cursor.getInt(cursor.getColumnIndex("totalMoney")) : 0)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong năm nay
     *
     * @return Giá trị tổng doanh thu trong năm nay
     * @created_by nblinh on 4/17/2019
     */
    private static int getReportTotalMoneyThisYear() {
        try {
            Date[] dates = DateUtil.getThisYear();

            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'" + DateUtil.getDateFormat(dates[0]) + "'" + " AND " + "'" + DateUtil.getDateFormat(dates[1]) + "'";


            Log.d("sql", "getReportTotalMoneyThisYear: " + sql);

            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);

            if (cursor != null) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong tháng này
     *
     * @return Giá trị tổng doanh thu trong tháng này
     * @created_by nblinh on 4/17/2019
     */
    private static int getReportTotalMoneyThisMonth() {
        try {
            Date[] dates = DateUtil.getThisMonth();

            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'" + DateUtil.getDateFormat(dates[0]) + "'" + " AND " + "'" + DateUtil.getDateFormat(dates[1]) + "'";

            Log.d("sql", "getReportTotalMoneyThisMonth: " + sql);

            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong tuần này
     *
     * @return Giá trị tổng doanh thu trong tuần này
     * @created_by nblinh on 4/17/2019
     */
    private static int getReportTotalMoneyThisWeek() {
        try {
            Date[] dates = DateUtil.getThisWeek();

            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'" + DateUtil.getDateFormat(dates[0]) + "'" + " AND " + "'" + DateUtil.getDateFormat(dates[1]) + "'";


            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong hôm nay
     *
     * @return Giá trị tổng doanh thu trong hôm nay
     * @created_by nblinh on 4/17/2019
     */
    private static int getReportTotalMoneyToday() {
        try {
            Date[] dates = DateUtil.getToday();

            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " = " + "'" + DateUtil.getDateFormat(dates[0]) + "'";

            Log.d("sql", "getReportTotalMoneyToday: " + sql);

            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo tổng quan doanh thu trong hôm qua
     *
     * @return Giá trị tổng doanh thu trong hôm qua
     * @created_by nblinh on 4/17/2019
     */
    private static int getReportTotalMoneyYesterday() {
        try {
            Date[] dates = DateUtil.getYesterday();

            String sql = "SELECT sum(" + IDBUtils.ITableBillUtils.COLUMN_TOTAL_MONEY + ") FROM " + BILL_TBL_NAME +
                    " WHERE " + COLUMN_STATE + " = '1' " + " AND " + "date(" + COLUMN_DATE_CREATED + ")" + " = " + "'" + DateUtil.getDateFormat(dates[0]) + "'";


            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết hôm nay
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportToday() {
        Date[] dates = DateUtil.getToday();
        try {
            return getReportDetailDate(DateUtil.getDateFormat(dates[0]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết hôm qua
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportYeaterday() {
        Date[] dates = DateUtil.getYesterday();
        try {
            return getReportDetailDate(DateUtil.getDateFormat(dates[0]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết tuần này
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportThisWeek() {
        Date[] dates = DateUtil.getThisWeek();
        try {
            return getReportOverViewByDate(DateUtil.getDateFormat(dates[0]), DateUtil.getDateFormat(dates[1]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết tuần trước
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportLastWeek() {
        Date[] dates = DateUtil.getLastWeek();
        try {
            return getReportOverViewByDate(DateUtil.getDateFormat(dates[0]), DateUtil.getDateFormat(dates[1]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết tháng này
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportThisMonth() {
        Date[] dates = DateUtil.getThisMonth();
        try {
            return getReportOverViewByDate(DateUtil.getDateFormat(dates[0]), DateUtil.getDateFormat(dates[1]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết tháng trước
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportLastMonth() {
        Date[] dates = DateUtil.getLastMonth();
        try {
            return getReportOverViewByDate(DateUtil.getDateFormat(dates[0]), DateUtil.getDateFormat(dates[1]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết năm nay
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportThisYear() {
        Date[] dates = DateUtil.getThisYear();
        try {
            return getReportOverViewByDate(DateUtil.getDateFormat(dates[0]), DateUtil.getDateFormat(dates[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo chi tiết năm trước
     *
     * @return - Danh sách thông kê báo cáo theo từng món ăn - có thể trả về null khi có lỗi xảy ra
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportLastYear() {
        Date[] dates = DateUtil.getLastYear();
        try {
            return getReportOverViewByDate(DateUtil.getDateFormat(dates[0]), DateUtil.getDateFormat(dates[1]));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo theo khoảng thời gian
     *
     * @param startDate - ngày giờ bắt đầu
     * @param endDate   - ngày giờ kết thúc
     * @return - danh sách báo cáo thống kê theo từng ngày
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportByDate(String startDate, String endDate) {
        try {
            ArrayList<ReportDetail> list = new ArrayList<>();

            String sql = String.format("SELECT %s,%s,sum(%s) as quantity,sum(%s.%s) as totalMoney FROM " + BILL_TBL_NAME + "," + BILL_DETAIL_TBL_NAME + " WHERE " +
                            " %s.%s = %s.%s " + " AND " + COLUMN_STATE + " = '1' " + " AND " +
                            " date(" + COLUMN_DATE_CREATED + ")" + " BETWEEN " + "'" + startDate + "'" + " AND " + "'" + endDate + "'" +
                            " GROUP BY " + COLUMN_DISH_ID + "," + COLUMN_DATE_CREATED,
                    COLUMN_DATE_CREATED, COLUMN_DISH_ID, COLUMN_QUANTITY, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_TOTAL_MONEY,
                    BILL_TBL_NAME, IDBUtils.ITableBillUtils.COLUMN_BILL_ID, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_BILL_ID
            );

            Log.d("sql", "getReportByDate: " + sql);

            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    list.add(parseCursorToReportDetail(cursor));
                    cursor.moveToNext();
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Phương thức xử lý: Lấy thống kê báo cáo theo khoảng thời gian
     *
     * @param date - ngày muốn lấy báo cáo - Định dạng là yyyy-MM-dd
     * @return - danh sách báo cáo thống kê theo từng ngày
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportDetail> getReportDetailDate(String date) {
        try {
            ArrayList<ReportDetail> list = new ArrayList<>();

            String sql = String.format("SELECT %s,%s,sum(%s) as quantity,sum(%s.%s) as totalMoney FROM " + BILL_TBL_NAME + "," + BILL_DETAIL_TBL_NAME + " WHERE " +
                            " %s.%s = %s.%s " + " AND " + COLUMN_STATE + " = '1' " + " AND " +
                            " date(" + COLUMN_DATE_CREATED + ")" + " = " + "'" + date + "'" +
                            " GROUP BY " + COLUMN_DISH_ID + "," + COLUMN_DATE_CREATED,
                    COLUMN_DATE_CREATED, COLUMN_DISH_ID, COLUMN_QUANTITY, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_TOTAL_MONEY,
                    BILL_TBL_NAME, IDBUtils.ITableBillUtils.COLUMN_BILL_ID, BILL_DETAIL_TBL_NAME, IDBUtils.ITableBillDetailUtils.COLUMN_BILL_ID
            );

            Log.d("sql", "getReportByDate: " + sql);

            Cursor cursor = SQLiteDBManager.getInstance().getRecords(sql, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    list.add(parseCursorToReportDetail(cursor));
                    cursor.moveToNext();
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Phương thức xử lý: Lấy thông tin báo cáo tổng quan
     *
     * @return - báo cáo tổng quan doanh thu
     * @created_by nblinh on 4/17/2019
     */
    @Override
    public ArrayList<ReportCurrent> getOverviewReport() {
        try {
            ArrayList<ReportCurrent> list = new ArrayList<>();
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.YESTERDAY)
                    .setAmount((long) getReportTotalMoneyYesterday())
                    .build());
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.TODAY)
                    .setAmount((long) getReportTotalMoneyToday())
                    .build());
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.THIS_WEEK)
                    .setAmount((long) getReportTotalMoneyThisWeek())
                    .build());
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.THIS_MONTH)
                    .setAmount((long) getReportTotalMoneyThisMonth())
                    .build());
            list.add(new ReportCurrent.Builder()
                    .setParamType(ParamReportEnum.THIS_YEAR)
                    .setAmount((long) getReportTotalMoneyThisYear())
                    .build());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
