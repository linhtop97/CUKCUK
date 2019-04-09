package vn.com.misa.cukcuklite.screen.choosedishdefault;

import vn.com.misa.cukcuklite.data.dish.DishDataSource;

public class ChooseDishDefaultPresenter implements IChooseDishDefaultContract.IPresenter {

    private IChooseDishDefaultContract.IView mView;
    private DishDataSource mDishDataSource;


    ChooseDishDefaultPresenter() {
        mDishDataSource = new DishDataSource();
    }

    /**
     * Phương thức đặt view cho presenter
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param view - view
     */
    @Override
    public void setView(IChooseDishDefaultContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public void onStart() {
        //lấy danh sách các món ăn và hiển thị ra màn hình
        try {
            mView.showDish(mDishDataSource.getAllDish());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public void onStop() {

    }
}
