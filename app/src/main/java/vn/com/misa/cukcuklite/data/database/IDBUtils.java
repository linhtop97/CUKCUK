package vn.com.misa.cukcuklite.data.database;

/**
 * Lớp chứa định danh cho bảng và cột dữ liệu cho database
 * Created_by Nguyễn Bá Linh on 20/03/2019
 */
public interface IDBUtils {
    String DB_NAME = "cukcuk_lite_v1.db";
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
        String COLUMN_UNIT_ID = "UnitId";
        String COLUMN_UNIT_NAME = "UnitName";
    }

    /**
     * Tên bảng, trường của bảng Món Ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    interface ITableDishUtils {
        String DISH_TBL_NAME = "tblDish";
        String COLUMN_DISH_ID = "DishId";
        String COLUMN_DISH_NAME = "DishName";
        String COLUMN_PRICE = "Price";
        String COLUMN_UNIT_ID = "UnitId";
        String COLUMN_COLOR_CODE = "ColorCode";
        String COLUMN_ICON_PATH = "IconPath";
        String COLUMN_IS_SALE = "IsSale";
    }

    /**
     * Tên bảng, trường của bảng Hóa Đơn
     * Created_by Nguyễn Bá Linh on 02/04/2019
     */
    interface ITableBillUtils {
        String BILL_TBL_NAME = "tblBill";
        String COLUMN_BILL_ID = "BillId";
        String COLUMN_BILL_NUMBER = "BillNumber";
        String COLUMN_DATE_CREATED = "DateCreated";
    }

    /**
     * Tên bảng, trường của bảng Chi tiết hóa đơn
     * Created_by Nguyễn Bá Linh on 02/04/2019
     */
    interface ITableBillDetailUtils {
        String BILL_DETAIL_TBL_NAME = "tblBillDetail";
        String COLUMN_BILL_ID = "BillId";
        String COLUMN_DISH_NAME = "DishName";
        String COLUMN_NUMBER_OF_DISH = "NumOfDish";
        String COLUMN_PRICE = "Price";
    }

    //sql query
    //select * FROM tblUnitDefault WHERE tblUnitDefault.PK_iUnitId in (select FK_iUnitId from tblRestaurantUnit WHERE tblRestaurantUnit.FK_iRestaurantId = 2)
}
