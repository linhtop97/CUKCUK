package vn.com.misa.cukcuklite.screen.selectunit;

import java.util.List;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.data.models.Unit;
import vn.com.misa.cukcuklite.data.unit.UnitDataSource;


/**
 * Presenter cho Select Screen
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class SelectUnitPresenter implements IUnitContract.IPresenter {

    private IUnitContract.IView mView;
    private UnitDataSource mUnitDataSource;
    private DishDataSource mDishDataSource;

    /**
     * Phương thức khởi tạo presenter
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    SelectUnitPresenter() {
        mUnitDataSource = new UnitDataSource();
        mDishDataSource = new DishDataSource();
    }

    @Override
    public void setView(IUnitContract.IView view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }

    /**
     * Phương thức khởi tạo đầu tiên khi vào màn hình, lấy các thông số mặc định để view hiển thị
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitId - id của đơn vị
     */
    @Override
    public void onStart(String unitId) {
        try {
            List<Unit> units = mUnitDataSource.getAllUnit();
            int size = units.size();
            int lastSelectPosition = 0;
            for (int i = 0; i < size; i++) {
                if (units.get(i).getUnitId().equals(unitId)) {
                    lastSelectPosition = i;
                    break;
                }
            }
            mView.showUnit(units, lastSelectPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức thêm mới đơn vị cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void addUnit(Unit unit) {
        try {
            if (unit.getUnitName().equals("")) {
                mView.unitNameEmpty();
                return;
            }
            switch (mUnitDataSource.addUnitToDatabase(unit)) {
                case Exists:
                    mView.addUnitFailed(R.string.unit_name_is_exists);
                    break;
                case Success:
                    mView.addUnitSuccess(unit.getUnitId());
                    break;
                case SomethingWentWrong:
                    mView.addUnitFailed(R.string.something_went_wrong);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cập nhật tên đơn vị cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void updateUnit(Unit unit) {
        try {
            if (unit.getUnitName().equals("")) {
                mView.unitNameEmpty();
                return;
            }
            switch (mUnitDataSource.updateUnitToDatabase(unit)) {
                case Exists:
                    mView.receiveMessage(R.string.unit_name_is_exists);
                    break;
                case Success:
                    mView.updateUnitSuccess(unit.getUnitId());
                    break;
                case SomethingWentWrong:
                    mView.receiveMessage(R.string.something_went_wrong);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xóa đơn vị có kiểm tra đơn vị đã được sử dụng hay chưa
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param unitId - id của đơn vị
     */
    @Override
    public void deleteUnit(String unitId) {
        try {
            List<Dish> dishes = mDishDataSource.getAllDish();
            int size = dishes.size();
            boolean unitIsUsed = false;
            for (int i = 0; i < size; i++) {
                if (dishes.get(i).getUnitId().equals(unitId)) {
                    unitIsUsed = true;
                    break;
                }
            }
            if (unitIsUsed) {
                mView.receiveMessage(R.string.unit_name_is_used);
            } else {
                if (mUnitDataSource.deleteUnitById(unitId)) {
                    mView.deleteUnitSuccess();
                } else {
                    mView.receiveMessage(R.string.something_went_wrong);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
