package vn.com.misa.cukcuklite.screen.authentication.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import vn.com.misa.cukcuklite.CukCukLiteApplication;
import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.listeners.IDataCallBack;
import vn.com.misa.cukcuklite.data.local.bill.BillDataSource;
import vn.com.misa.cukcuklite.data.local.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.local.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.local.prefs.SharedPrefersManager;
import vn.com.misa.cukcuklite.data.local.unit.UnitDataSource;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.data.models.Unit;
import vn.com.misa.cukcuklite.data.remote.firebase.firebaserealtime.FirebaseManager;
import vn.com.misa.cukcuklite.data.remote.firebase.firebaserealtime.IFirebaseRealTime;

public class LoginPresenter implements ILoginContract.IPresenter {

    private static final String TAG = "LoginPresenter";
    private ILoginContract.IView mView;
    private Context mContext;
    private FirebaseManager mFirebaseManager;
    private SQLiteDBManager mSQLiteDBManager;
    private UnitDataSource mUnitDataSource;
    private DishDataSource mDishDataSource;
    private BillDataSource mBillDataSource;
    private List<Unit> mUnits;
    private List<Dish> mDishes;
    private List<BillDetail> mBillDetails;
    private List<Bill> mBills;

    public LoginPresenter(Context context) {
        mContext = context;
        mFirebaseManager = FirebaseManager.getInstance();
        mSQLiteDBManager = SQLiteDBManager.getInstance();
        mUnitDataSource = UnitDataSource.getInstance();
        mDishDataSource = DishDataSource.getInstance();
        mBillDataSource = BillDataSource.getInstance();
    }

    /**
     * Phương thức đăng nhập với facebook
     * Created_by Nguyễn Bá Linh on 05/04/2019
     * @param accessToken
     */
    @Override
    public void loginWithFacebook(AccessToken accessToken) {
        mView.showLoading();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mSQLiteDBManager.clearDatabase();
                            mUnitDataSource.removeAllCache();
                            mDishDataSource.removeAllCache();
                            mBillDataSource.removeAllCache();
                            SharedPrefersManager.getInstance(CukCukLiteApplication.getInstance()).setIsLoginSuccess(true);
                            SharedPrefersManager.getInstance(mContext).setAlreadyHasData(false);
                            mView.loginSuccess();

                        } else {
                            mView.receiveMessage(R.string.something_went_wrong);
                        }
                        mView.hideLoading();
                    }
                });
    }


    /**
     * Phương thức lấy toàn bộ dữ liệu từ fire base về local
     * Created_by Nguyễn Bá Linh on 25/04/2019
     */
    @Override
    public void getAllDataFromFireBase() {
        try {
            boolean isGetSuccess = true;
            mFirebaseManager.getAllUnitFromFirebase(new IDataCallBack<List<Unit>>() {
                @Override
                public void onDataSuccess(List<Unit> data) {
                    if (data != null) {
                        mUnits = data;
                        mFirebaseManager.getAllDishFromFirebase(new IDataCallBack<List<Dish>>() {
                            @Override
                            public void onDataSuccess(List<Dish> data) {
                                if (data != null) {
                                    mDishes = data;
                                    mFirebaseManager.getAllBillDetailFromFirebase(new IDataCallBack<List<BillDetail>>() {
                                        @Override
                                        public void onDataSuccess(List<BillDetail> data) {
                                            if (data != null) {
                                                mBillDetails = data;
                                                mFirebaseManager.getAllBillFromFirebase(new IDataCallBack<List<Bill>>() {
                                                    @Override
                                                    public void onDataSuccess(List<Bill> data) {
                                                        if (data != null) {
                                                            mBills = data;
                                                        }
                                                    }
    
                                                    @Override
                                                    public void onDataFailed(int msg) {
    
                                                    }
                                                });
                                            }
                                        }
    
                                        @Override
                                        public void onDataFailed(int msg) {
    
                                        }
                                    });
                                }
                            }
    
                            @Override
                            public void onDataFailed(int msg) {
                                mView.receiveMessage(R.string.something_went_wrong);
                            }
                        });
                    }
                }
    
                @Override
                public void onDataFailed(int msg) {
                    mView.receiveMessage(R.string.something_went_wrong);
                }
            });
            insertAllDataToDBLocal();
        } catch (Exception e) {
             
        }
    }

    /**
     * Phương thức thêm toàn bộ dữ liệu user đã có trên server về local
     * Created_by Nguyễn Bá Linh on 25/04/2019
     */
    private void insertAllDataToDBLocal() {
        try {
            if (mUnits != null && mDishes != null && mBills != null && mBillDetails != null) {
                for (Unit unit : mUnits) {
                    mUnitDataSource.addUnit(unit);
                }
                for (Dish dish : mDishes) {
                    mDishDataSource.addDish(dish);
                }
                for (BillDetail billDetail : mBillDetails) {
                    mBillDataSource.addBillDetail(billDetail);
                }
                for (Bill bill : mBills) {
                    mBillDataSource.addBill(bill);
                }
                mView.goToMainScreen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login(String emailPhone, String password) {

    }

    @Override
    public void checkUserHasDataBefore() {
        mFirebaseManager.userHasData(new IFirebaseRealTime.IFirebaseDataCallBack() {
            @Override
            public void onSuccess() {
                getAllDataFromFireBase();
            }

            @Override
            public void onFailed() {
                mView.goToChooseRestaurentType();
            }
        });
    }

    @Override
    public void setView(ILoginContract.IView view) {
        mView = view;
    }

    /**
     * Phương thức khởi chạy đầu tiên khi màn hình được hiển thị
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    @Override
    public void onStart() {

    }

    /**
     * Phương thức giải phóng, lưu dữ liệu khi màn hình trong trạng thái không còn hoạt động với người dùng
     * Created_by Nguyễn Bá Linh on 18/04/2019
     */
    @Override
    public void onStop() {

    }
}
