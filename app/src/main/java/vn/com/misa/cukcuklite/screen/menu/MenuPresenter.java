package vn.com.misa.cukcuklite.screen.menu;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vn.com.misa.cukcuklite.data.local.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.data.remote.firebase.FireStoreManager;
import vn.com.misa.cukcuklite.data.remote.firebase.IFirebaseResponse;

/**
 * Presenter cho màn hình menu
 */
public class MenuPresenter implements IMenuContract.IPresenter {
    private IMenuContract.IView mView;
    private DishDataSource mDishDataSource;
    private FireStoreManager mFireStoreManager;

    /**
     * Phương thức khởi tạo presenter
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    MenuPresenter() {
        mDishDataSource = DishDataSource.getInstance();
        mFireStoreManager = FireStoreManager.getInstance();
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
            final List<Dish> dishes = mDishDataSource.getAllDish();
            if (dishes != null && dishes.size() > 2) {
                Collections.sort(dishes, new Comparator<Dish>() {
                    @Override
                    public int compare(Dish o1, Dish o2) {
                        return o1.getDishName().compareToIgnoreCase(o2.getDishName());
                    }
                });
                mView.showDish(dishes);
//                mFireStoreManager.getCollection("users/" + FirebaseAuth.getInstance().getUid() + "/dish", new IFirebaseResponse.IGetCollection() {
////                    @Override
////                    public void onSuccess(List<DocumentSnapshot> listDocumentSnapshot) {
////                        List<Dish> dishList = new ArrayList<>();
////                        for (DocumentSnapshot doc : listDocumentSnapshot) {
////                            dishList.add(doc.toObject(Dish.class));
////                        }
////                        dishList.size();
////                    }
////
////                    @Override
////                    public void onFailed() {
////
////                    }
////                });
                mFireStoreManager.getDocument("users/" + FirebaseAuth.getInstance().getUid(), new IFirebaseResponse.IGetDocument() {
                    @Override
                    public void onSuccess(DocumentSnapshot objectMap) {
                        Log.d("map", "onSuccess: " + objectMap.toString());
                    }

                    @Override
                    public void onFailed() {
                        Log.d("map", "onFailed: ");
                    }
                });
            }
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
