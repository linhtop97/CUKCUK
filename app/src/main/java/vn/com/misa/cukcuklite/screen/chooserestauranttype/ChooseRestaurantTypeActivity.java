package vn.com.misa.cukcuklite.screen.chooserestauranttype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.models.RestaurantType;
import vn.com.misa.cukcuklite.utils.AppConstants;

public class ChooseRestaurantTypeActivity extends AppCompatActivity implements IChooseRestaurantTypeContract.IView {

    private ImageButton btnBack;
    private boolean mIsLoginBefore;
    private ChooseRestaurantTypePresenter mPresenter;

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

    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 03/04/2019
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        Intent intent = getIntent();
        mIsLoginBefore = intent.getBooleanExtra(AppConstants.EXTRA_LOGIN_SUCCESS, false);
        if (mIsLoginBefore) {
            btnBack.setVisibility(View.GONE);
        }

    }


    @Override
    public void showListRestaurantType(List<RestaurantType> restaurantTypeList) {

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

    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created_by Nguyễn Bá Linh on 05/04/2019
     */
    @Override
    public void hideLoading() {

    }
}
