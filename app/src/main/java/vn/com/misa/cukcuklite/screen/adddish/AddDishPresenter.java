package vn.com.misa.cukcuklite.screen.adddish;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.data.unit.UnitDataSource;

public class AddDishPresenter implements IAddDishContract.IPresenter {
    private IAddDishContract.IView mView;
    private DishDataSource mDishDataSource;
    private UnitDataSource mUnitDataSource;

    /**
     * Phương thức khởi tạo AddDishPresenter
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    AddDishPresenter() {
        mDishDataSource = new DishDataSource();
        mUnitDataSource = new UnitDataSource();
    }

    /**
     * Phương thức thêm mới món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     */
    @Override
    public void addDish(Dish dish) {
//        try {
//            if (name.equals("")) {
//                mView.dishNameEmpty();
//                return;
//            }
//            Dish dish = new Dish.Builder().setDishName(name)
//                    .setPrice(Integer.parseInt(price))
//                    .setUnitId(unit)
//                    .setColorCode(background)
//                    .setIconPath(icon).build();
//            switch (mDishDataSource.addDishToDatabase(dish)) {
//                case Exists:
//                    mView.receiveMessage(R.string.dish_name_is_exists);
//                    break;
//                case Success:
//                    mView.addDishSuccess();
//                    break;
//                case SomethingWentWrong:
//                    mView.receiveMessage(R.string.something_went_wrong);
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    /**
     * Phương thức chỉnh sửa món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     */
    @Override
    public void updateDish(Dish dish) {
        try {
            if (dish.getDishName().equals("")) {
                mView.dishNameEmpty();
                return;
            }
            switch (mDishDataSource.updateDishToDatabase(dish)) {
                case Exists:
                    mView.receiveMessage(R.string.dish_name_is_exists);
                    break;
                case Success:
                    mView.upDateDishSuccess();
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
     * Phương thức xóa món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dishId - id của món ăn
     */
    @Override
    public void deleteDish(String dishId) {
        if (mDishDataSource.deleteDishById(dishId)) {
            mView.deleteDishSuccess();
        } else {
            mView.receiveMessage(R.string.something_went_wrong);
        }
    }

    /**
     * Phương thức lấy tên đơn vị qua Id
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param unitId - id của đơn vị
     * @return - tên đơn vị
     */
    @Override
    public String getUnitName(String unitId) {
        String unitName = "";
        try {
            if (unitId != null) {
                unitName = mUnitDataSource.getUnitNameById(unitId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitName;
    }


    /**
     * Phương thức đặt view cho presenter
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IAddDishContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public void onStart() {
        //gán đơn vị mặc định cho món ăn khi vào tính năng thêm mới
        String unit = mUnitDataSource.getAllUnitName().get(0);
        mView.setUnit(unit);

    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public void onStop() {

    }
}
