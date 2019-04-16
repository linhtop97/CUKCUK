package vn.com.misa.cukcuklite.utils;

import java.text.SimpleDateFormat;

/**
 * Lớp xử lý chuỗi cho ứng dụng
 * Created_by Nguyễn Bá Linh on 17/04/2019
 */
public class StringUtils {
    public static String getDate(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy (hh:mm a)");
        return dateFormat.format(time).replace("PM", "CH").replace("AM", "SA");
    }
}
