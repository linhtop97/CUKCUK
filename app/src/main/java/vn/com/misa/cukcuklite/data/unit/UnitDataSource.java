package vn.com.misa.cukcuklite.data.unit;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcuklite.data.cukcukenum.EnumResult;
import vn.com.misa.cukcuklite.data.database.IDBUtils;
import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.models.Unit;

/**
 * Lớp thao tác với các đơn vị món ăn trong cơ sở dũ liệu
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class UnitDataSource implements IUnitDataSource, IDBUtils.ITableUnitUtils {

    private static final String TAG = "UnitDataSource";
    private SQLiteDBManager mSQLiteDBManager;

    /**
     * Phương thức khởi tạo cho đối tượng UnitDataSource
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    public UnitDataSource() {
        mSQLiteDBManager = SQLiteDBManager.getInstance();
    }

    /**
     * Phương thức thêm đơn vị món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị món ăn
     * @return - thêm đơn vị món ăn thàn công hay thất bại
     */
    @Override
    public boolean addUnit(Unit unit) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_UNIT_ID, unit.getUnitId());
            contentValues.put(COLUMN_UNIT_NAME, unit.getUnitName());
            return mSQLiteDBManager.addNewRecord(UNIT_TBL_NAME, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức thêm mới đơn vị món ăn có kiểm tra sự tồn tại của đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - Đơn vị
     * @return - thêm mới đơn vị món ăn thành công, thất bại hay đã tồn tại đơn vị
     */
    @Override
    public EnumResult addUnitToDatabase(Unit unit) {
        if (unit != null) {
            //kiểm tra tên đơn vị đã tồn tại hay chưa
            if (isUnitIfExists(unit.getUnitName())) {
                return EnumResult.Exists;
            } else {
                if (addUnit(unit)) {
                    return EnumResult.Success;
                }
            }
        }
        return EnumResult.SomethingWentWrong;
    }

    /**
     * Phương thức xóa đơn vị thông qua id của đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitId - đơn vị
     * @return - xóa đơn vị thành công hay thất bại
     */
    @Override
    public boolean deleteUnitById(String unitId) {
        try {
            return mSQLiteDBManager.deleteRecord(UNIT_TBL_NAME,
                    COLUMN_UNIT_ID + "=?", new String[]{unitId});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức cập nhật đơn vị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị
     * @return - cập nhật đơn vị thành công hay thất bại
     */
    @Override
    public boolean updateUnit(Unit unit) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_UNIT_NAME, unit.getUnitName());
            return mSQLiteDBManager.updateRecord(UNIT_TBL_NAME, contentValues,
                    COLUMN_UNIT_ID + "=?", new String[]{unit.getUnitId()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức cập nhật đơn vị có kiểm tra tên của đơn vị
     *
     * @param unit - đơn vị
     * @return - cập nhật đơn vị thành công, thất bại
     */
    @Override
    public EnumResult updateUnitToDatabase(Unit unit) {
        List<Unit> units = getAllUnit();
        int size = units.size();
        boolean unitNameIsExists = false;
        for (int i = 0; i < size; i++) {
            if (units.get(i).getUnitName().toLowerCase().equals(unit.getUnitName())
                    && (!units.get(i).getUnitId().equals(unit.getUnitId()))) {
                unitNameIsExists = true;
                break;
            }
        }
        if (unitNameIsExists) {
            return EnumResult.Exists;
        } else {
            if (updateUnit(unit)) {
                return EnumResult.Success;
            }
        }
        return EnumResult.SomethingWentWrong;
    }

    /**
     * Phương thức lấy tất cả tên các đơn vị trong đã có trong cơ sở dữ liệu
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return - danh sách tên các đơn vị
     */
    @Override
    public List<String> getAllUnitName() {
        List<String> unitNames = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select " + COLUMN_UNIT_NAME + " from " + UNIT_TBL_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String unitName = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME));
                unitNames.add(unitName);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitNames;
    }

    /**
     * Phương thức lấy ra tất cả các đơn vị có trong cở sở dữ liệu
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return - danh sách đơn vị
     */
    @Override
    public List<Unit> getAllUnit() {
        List<Unit> units = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + UNIT_TBL_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Unit unit = new Unit(cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME)));
                units.add(unit);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return units;
    }

    /**
     * Phương thức kiểm tra đơn vị đã tồn tại trong cơ sở dữ liệu hay chưa
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitName - đơn vị
     * @return - đơn vị đã hay chưa tồn tại
     */
    @Override
    public boolean isUnitIfExists(String unitName) {
        try {
            Cursor cursor = mSQLiteDBManager.getRecords(UNIT_TBL_NAME, new String[]{COLUMN_UNIT_NAME},
                    "lower(" + COLUMN_UNIT_NAME + ")" + " = ? ", new String[]{unitName.toLowerCase()},
                    null, null, null);
            int unitNameRecord = cursor.getCount();
            cursor.close();
            return unitNameRecord > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức lấy đơn vị với id của dơn vị
     * Created_by Nguyễn Bá Linh on 05/04/2019
     *
     * @param unitId - id của đơn vị
     * @return - đơn vị
     */
    @Override
    public Unit getUnitById(String unitId) {
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + UNIT_TBL_NAME + " where " + COLUMN_UNIT_ID + "=" + unitId, null);
            cursor.moveToFirst();
            Unit unit = new Unit(cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME)));
            cursor.close();
            return unit;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức lấy tên đơn vị qua Id
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param unitId - id của đơn vị
     * @return - tên đơn vị
     */
    @Override
    public String getUnitNameById(String unitId) {
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select " + COLUMN_UNIT_NAME + " from " + UNIT_TBL_NAME + " where " + COLUMN_UNIT_ID + "='" + unitId + "'", null);
            cursor.moveToFirst();
            String unitName = cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_NAME));
            cursor.close();
            return unitName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Phương thức xóa toàn bộ đơn vị có sẵn trong database
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public boolean deleteAllUnit() {
        boolean deleteSuccess = false;
        try {
            deleteSuccess = mSQLiteDBManager.deleteRecord(UNIT_TBL_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteSuccess;
    }
}
