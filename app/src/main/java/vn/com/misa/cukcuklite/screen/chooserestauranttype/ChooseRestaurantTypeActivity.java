package vn.com.misa.cukcuklite.screen.chooserestauranttype;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.models.RestaurantType;
import vn.com.misa.cukcuklite.utils.AppConstants;

/**
 * Màn hình lựa chọn loại nhà hàng
 * Created_by Nguyễn Bá Linh on 05/04/2019
 */
public class ChooseRestaurantTypeActivity extends AppCompatActivity implements IChooseRestaurantTypeContract.IView, View.OnClickListener {

    private ImageButton btnBack;
    private TextView tvContinue;
    private boolean mIsLoginBefore;
    private ChooseRestaurantTypePresenter mPresenter;
    private Button btnContinue;
    private RecyclerView rvRestaurantType;
    private RestaurantTypeAdapter mRestaurantTypeAdapter;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_restaurant_type);
        mPresenter = new ChooseRestaurantTypePresenter(this);
        mPresenter.setView(this);
        initViews();
        initEvents();
        mPresenter.onStart();
    }


    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 03/04/2019
     */
    private void initEvents() {
        try {
            btnBack.setOnClickListener(this);
            btnContinue.setOnClickListener(this);
            tvContinue.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 03/04/2019
     */
    private void initViews() {
        try {
            btnBack = findViewById(R.id.btnBack);
            tvContinue = findViewById(R.id.tvContinue);
            btnContinue = findViewById(R.id.btnContinue);
            rvRestaurantType = findViewById(R.id.rvRestaurantType);
            Intent intent = getIntent();
            mIsLoginBefore = intent.getBooleanExtra(AppConstants.EXTRA_LOGIN_SUCCESS, false);
            if (mIsLoginBefore) {
                btnBack.setVisibility(View.GONE);
            }

            rvRestaurantType.setLayoutManager(new LinearLayoutManager(this));
            mRestaurantTypeAdapter = new RestaurantTypeAdapter(this);
            rvRestaurantType.setAdapter(mRestaurantTypeAdapter);
            initProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức khởi tạo progressbar
     * Created_by Nguyễn Bá Linh on 05/04/2019
     */
    private void initProgressBar() {
        try {
            mDialog = new ProgressDialog(this) {
                @Override
                public void onBackPressed() {
                    super.onBackPressed();
                }
            };
            mDialog.setMessage(getString(R.string.login_in_process));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showListRestaurantType(List<RestaurantType> restaurantTypeList) {
        mRestaurantTypeAdapter.setListData(restaurantTypeList);
    }

    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created_by Nguyễn Bá Linh on 05/04/2019
     */
    @Override
    public void showLoading() {
        try {
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created_by Nguyễn Bá Linh on 05/04/2019
     */
    @Override
    public void hideLoading() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created_by Nguyễn Bá Linh on 05/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnContinue:
            case R.id.tvContinue:
                try {
                    //bắt đầu main activity
                    int restaurantTypeId = mRestaurantTypeAdapter.getRestaurantTypeIdSelected();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
