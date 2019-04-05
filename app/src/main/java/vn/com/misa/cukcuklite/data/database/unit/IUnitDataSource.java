package vn.com.misa.cukcuklite.data.database.unit;

import java.util.List;

import vn.com.misa.cukcuklite.data.EnumResult;
import vn.com.misa.cukcuklite.data.models.Unit;

/**
 * Lớp định nghĩa các phương thức cho lớp thao tác với dữ liệu của bảng đơn vị
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public interface IUnitDataSource {
    boolean addUnit(Unit unit);

    EnumResult addUnitToDatabase(Unit unit);

    boolean deleteUnitById(String unitId);

    boolean updateUnit(Unit unit);

    EnumResult updateUnitToDatabase(Unit unit);

    List<String> getAllUnitName();

    List<Unit> getAllUnit();

    boolean isUnitIfExists(String dishName);

    Unit getUnitById(String unitId);
}
