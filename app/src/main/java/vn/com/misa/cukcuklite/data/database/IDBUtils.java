package vn.com.misa.cukcuklite.data.database;

/**
 * Lớp chứa định danh cho bảng và cột dữ liệu cho database
 * Created_by Nguyễn Bá Linh on 20/03/2019
 */
public interface IDBUtils {
    String DB_NAME = "cukcuk_lite.db";
    String DB_LOCATION = "/data/data/vn.com.misa.cukcuklite/databases/";
    int DB_VERSION = 1;
    
    String PRIMARY_KEY = "PRIMARY KEY";
    String AUTOINCREMENT = "AUTOINCREMENT";
    String NOT_NULL = "NOT NULL";
    String NULL = "NULL";
    String DEFAULT = "DEFAULT";

    String TEXT_DATA_TYPE = "TEXT";
    String INTEGER_DATA_TYPE = "INTEGER";
    String REAL_DATA_TYPE = "REAL";
    String BLOB_DATA_TYPE = "BLOB";

    /**
     * Tên bảng, trường của bảng Tài khoản
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    interface ITableAccountUtils {
        String ACCOUNT_TBL_NAME = "tblAccount";
        String COLUMN_ACCOUNT_ID = "PK_iAccountId";
        String COLUMN_USER_NAME = "sUsername";
        String COLUMN_PASSWORD = "sPassword";
    }

    /**
     * Tên bảng, trường của bảng Đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    interface ITableUnitUtils {
        String UNIT_TBL_NAME = "tblUnit";
        String COLUMN_UNIT_ID = "PK_iUnitId";
        String COLUMN_UNIT_NAME = "sUnitName";
    }


    /**
     * Tên bảng, trường của bảng Icon
     * Created_by Nguyễn Bá Linh on 02/04/2019
     */
    interface ITableIconUtils {
        String ICON_TBL_NAME = "tblIcon";
        String COLUMN_ICON_ID = "PK_iIconId";
        String COLUMN_ICON_FILE_PATH = "sIconFilePath";
    }

    /**
     * Tên bảng, trường của bảng Màu Nền của món ăn
     * Created_by Nguyễn Bá Linh on 02/04/2019
     */
    interface ITableColorUtils {
        String COLOR_TBL_NAME = "tblColor";
        String COLUMN_COLOR_ID = "PK_iColorId";
        String COLUMN_COLOR_CODE = "sColorCode";
    }

    /**
     * Tên bảng, trường của bảng Món Ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    interface ITableDishUtils {
        String DISH_TBL_NAME = "tblDish";
        String COLUMN_DISH_ID = "PK_iDishId";
        String COLUMN_DISH_NAME = "sName";
        String COLUMN_PRICE = "iPrice";
        String COLUMN_UNIT_ID = "FK_iUnitId";
        String COLUMN_COLOR_ID = "FK_iColorId";
        String COLUMN_ICON_ID = "FK_iIconId";
        String COLUMN_IS_SALE = "bIsSale";
    }

    /**
     * Tên bảng, trường của bảng Hóa Đơn
     * Created_by Nguyễn Bá Linh on 02/04/2019
     */
    interface ITableBillUtils {
        String BILL_TBL_NAME = "tblBill";
        String COLUMN_BILL_ID = "PK_sBillId";
        String COLUMN_BILL_NUMBER = "iBillNumber";
        String COLUMN_DATE_CREATED = "sDateCreated";
    }

    /**
     * Tên bảng, trường của bảng Chi tiết hóa đơn
     * Created_by Nguyễn Bá Linh on 02/04/2019
     */
    interface ITableBillDetailUtils {
        String BILL_DETAIL_TBL_NAME = "tblBillDetail";
        String COLUMN_BILL_ID = "FK_sBillId";
        String COLUMN_DISH_NAME = "sDishName";
        String COLUMN_NUMBER_OF_DISH = "iNumberOfDish";
        String COLUMN_PRICE = "iPrice";
    }

}
