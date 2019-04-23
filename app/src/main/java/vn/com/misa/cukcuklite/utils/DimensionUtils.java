package vn.com.misa.cukcuklite.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Class chứa các phương thức tính toán kích thước
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class DimensionUtils {
    /**
     * lấy chiều rộng của màn hình
     * Created_by Nguyễn Bá Linh on 19/04/2019
     *
     * @param context - context
     */
    public static int getScreenWidthInPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * lấy chiều cao của màn hình
     * Created_by Nguyễn Bá Linh on 19/04/2019
     *
     * @param context - context
     */
    public static int getScreenHeightInPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * Chuyển đổi dp sang pixel
     * Created_by Nguyễn Bá Linh on 19/04/2019
     *
     * @param dp - dp
     */
    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
