package vn.com.misa.cukcuklite.screen.menu;

import vn.com.misa.cukcuklite.data.dish.DishDataSource;

/**
 * Presenter cho màn hình menu
 */
public class MenuPresenter implements IMenuContract.IPresenter {
    private IMenuContract.IView mView;
    private DishDataSource mDishDataSource;

    /**
     * Phương thức khởi tạo presenter
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    MenuPresenter() {
        mDishDataSource = DishDataSource.getInstance();
    }

    /**
     * Phương thức đặt view cho presenter
     * Created_by Nguyễn Bá Linh on 10/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IMenuContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 10/04/2019
     */
    @Override
    public void onStart() {
        //lấy danh sách các món ăn và hiển thị ra màn hình
        try {
            mView.showLoading();
            mView.showDish(mDishDataSource.getAllDish());
            mView.hideLoading();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created_by Nguyễn Bá Linh on 10/04/2019
     */
    @Override
    public void onStop() {

    }
}
