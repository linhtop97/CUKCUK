package vn.com.misa.cukcuklite.screen.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.databinding.ActivityMainBinding;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình khởi động ứng dụng có chức năng giới thiệu ứng dụng, tải dữ liệu, thông tin cho ứng dụng
 * Created_by Nguyễn Bá Linh on 01/04/2019
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mMainBinding;
    private Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigator = new Navigator(this);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

}
