package vn.com.misa.cukcuklite.screen.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.local.prefs.SharedPrefersManager;
import vn.com.misa.cukcuklite.screen.adddish.AddDishActivity;
import vn.com.misa.cukcuklite.screen.authentication.login.LoginActivity;
import vn.com.misa.cukcuklite.screen.dishorder.DishOrderActivity;
import vn.com.misa.cukcuklite.screen.menu.MenuFragment;
import vn.com.misa.cukcuklite.screen.report.ReportFragment;
import vn.com.misa.cukcuklite.screen.sale.SaleFragment;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình khởi động ứng dụng có chức năng giới thiệu ứng dụng, tải dữ liệu, thông tin cho ứng dụng
 * Created_by Nguyễn Bá Linh on 01/04/2019
 */
public class MainActivity extends AppCompatActivity implements IMainContract.IView, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private Navigator mNavigator;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar mToolbar;
    private TextView tvTitle;
    private ImageView btnAdd;
    private boolean mIsSale;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigator = new Navigator(this);
        mPresenter = new MainPresenter(this);
        mPresenter.setView(this);
        initViews();
        initEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 05/04/2019
     */
    private void initEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mIsSale) {
                        //thêm oder
                        mNavigator.startActivity(DishOrderActivity.class);
                    } else {
                        //vào màn hình món ăn, thêm món ăn
                        mNavigator.startActivity(AddDishActivity.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 05/04/2019
     */
    private void initViews() {
        try {
            tvTitle = findViewById(R.id.tvTitle);
            btnAdd = findViewById(R.id.btnAdd);
            mToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
            drawerLayout = findViewById(R.id.drawerLayout);
            drawerToggle = new ActionBarDrawerToggle(
                    this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
            navView = findViewById(R.id.navView);
            navView.setVerticalScrollBarEnabled(false);
            navView.setNavigationItemSelectedListener(this);
            mIsSale = true;
            mNavigator.addFragment(R.id.flMainContainer, SaleFragment.newInstance(), false, Navigator.NavigateAnim.NONE, SaleFragment.class.getSimpleName());
            tvTitle.setText(R.string.sale);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        //nếu ấn nút back của thiết bị thì sẽ kiểm tra nếu dang mở thì sẽ đóng Nav lại
        try {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navSale:
                try {
                    btnAdd.setClickable(true);
                    btnAdd.setVisibility(View.VISIBLE);
                    tvTitle.setText(R.string.sale);
                    mIsSale = true;
                    mNavigator.addFragment(R.id.flMainContainer, SaleFragment.newInstance(),
                            false, Navigator.NavigateAnim.NONE, SaleFragment.class.getSimpleName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.navMenu:
                try {
                    btnAdd.setClickable(true);
                    btnAdd.setVisibility(View.VISIBLE);
                    tvTitle.setText(R.string.menu_title);
                    mIsSale = false;
                    mNavigator.addFragment(R.id.flMainContainer, MenuFragment.newInstance(),
                            false, Navigator.NavigateAnim.NONE, MenuFragment.class.getSimpleName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.navReport:
                try {
                    if (SharedPrefersManager.getInstance(this).getIsLoginSuccess()) {
                        btnAdd.setClickable(false);
                        btnAdd.setVisibility(View.INVISIBLE);
                        tvTitle.setText(R.string.evenue);
                        mNavigator.addFragment(R.id.flMainContainer, ReportFragment.newInstance(),
                                false, Navigator.NavigateAnim.NONE, "REPORT_TAG");
                    } else {
                        mNavigator.startActivity(LoginActivity.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.navShare:
                try {
                    ShareCompat.IntentBuilder.from(this)
                            .setType("text/plain")
                            .setChooserTitle(getString(R.string.share_app))
                            .setText("market://details?id=vn.com.misa.android_cukcuklite")
                            .startChooser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.navRate:
                try {
                    Uri myUri = Uri.parse("market://details?id=vn.com.misa.android_cukcuklite");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(myUri);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.navInfo:

                break;
            case R.id.navLogout:
                try {
                    mPresenter.clearData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SaleFragment.REQUEST_PAY && resultCode == Activity.RESULT_OK) {
            mNavigator.startActivity(DishOrderActivity.class);
        }
    }

    @Override
    public void goToLoginScreen() {
        try {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created_by Nguyễn Bá Linh on 25/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToastOnTopScreen(message);
    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created_by Nguyễn Bá Linh on 25/04/2019
     */
    @Override
    public void showLoading() {

    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created_by Nguyễn Bá Linh on 25/04/2019
     */
    @Override
    public void hideLoading() {

    }
}
