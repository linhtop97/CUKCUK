package vn.com.misa.cukcuklite.screen.choosedishdefault;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.listeners.IOnItemClickListener;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.data.prefs.SharedPrefersManager;
import vn.com.misa.cukcuklite.screen.adddish.AddDishActivity;
import vn.com.misa.cukcuklite.screen.main.MainActivity;
import vn.com.misa.cukcuklite.utils.AppConstants;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình lựa chọn các món ăn mặc định cho quán ăn
 * Created_by Nguyễn Bá Linh on 08/04/2019
 */
public class ChooseDishDefaultActivity extends AppCompatActivity implements View.OnClickListener, IOnItemClickListener<Dish>, IChooseDishDefaultContract.IView {

    private ImageButton btnBack;
    private TextView tvDone;
    private Button btnDone;
    private DishAdapter mDishAdapter;
    private ChooseDishDefaultPresenter mPresenter;
    private Navigator mNavigator;
    private SharedPrefersManager mSharedPrefersManager;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                mPresenter.onStart();
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dish_default);
        mNavigator = new Navigator(this);
        mSharedPrefersManager = SharedPrefersManager.getInstance(this);
        mPresenter = new ChooseDishDefaultPresenter();
        mPresenter.setView(this);
        initViews();
        initEvents();
        mPresenter.onStart();
        //Đăng kí lắng nghe sự kiện sửa món ăn
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(AddDishActivity.ACTION_OK));
    }


    @Override
    protected void onDestroy() {
        //hủy đăng kí lắng nghe sự kiện hủy món ăn
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    private void initEvents() {
        tvDone.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvDone = findViewById(R.id.tvDone);
        btnDone = findViewById(R.id.btnDone);
        RecyclerView rvDish = findViewById(R.id.rvDish);
        rvDish.setLayoutManager(new LinearLayoutManager(this));
        mDishAdapter = new DishAdapter(this);
        mDishAdapter.setItemClickListener(this);
        rvDish.setAdapter(mDishAdapter);

    }


    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnDone:
            case R.id.tvDone:
                showMainScreen();
                break;
            default:
                break;
        }
    }

    /**
     * Phương thức xử lý khi item món ăn được tương tác, sẽ chuyển hướng tới màn hình chỉnh sửa món ăn
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param data - là món ăn được truyền sang màn hình sửa món ăn
     */
    @Override
    public void onItemClick(Dish data) {
        try {
            Intent intent = new Intent();
            intent.setClass(this, AddDishActivity.class);
            intent.putExtra(AppConstants.EXTRA_DISH, data);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToastOnTopScreen(message);
    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public void showLoading() {

    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public void hideLoading() {

    }

    /**
     * Phương thức hiển thị màn hình chính của ứng dụng sau khi đã khởi tạo các món ăn thành công
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    public void showMainScreen() {
        try {
            mSharedPrefersManager.setAlreadyHasData(true);
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            mNavigator.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Hiển thị danh sách món ăn của cửa hàng
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param dishes - danh sách món ăn của cửa hàng
     */
    @Override
    public void showDish(List<Dish> dishes) {
        try {
            mDishAdapter.setListData(dishes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
