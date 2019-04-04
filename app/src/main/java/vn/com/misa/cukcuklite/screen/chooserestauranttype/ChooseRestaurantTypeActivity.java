package vn.com.misa.cukcuklite.screen.chooserestauranttype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.utils.AppConstants;

public class ChooseRestaurantTypeActivity extends AppCompatActivity {

    private ImageButton btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_restaurant_type);
        initViews();
        initEvents();
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
        if (intent.getBooleanExtra(AppConstants.EXTRA_LOGIN_SUCCESS, false)) {
            btnBack.setVisibility(View.GONE);
        }
    }
}
