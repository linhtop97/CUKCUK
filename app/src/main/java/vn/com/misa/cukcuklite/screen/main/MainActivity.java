package vn.com.misa.cukcuklite.screen.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import vn.com.misa.cukcuklite.screen.menu.MenuFragment;
import vn.com.misa.cukcuklite.screen.sale.SaleFragment;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình khởi động ứng dụng có chức năng giới thiệu ứng dụng, tải dữ liệu, thông tin cho ứng dụng
 * Created_by Nguyễn Bá Linh on 01/04/2019
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private Navigator mNavigator;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar mToolbar;
    private TextView tvTitle;
    private ImageView btnAdd;
    private boolean mIsSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigator = new Navigator(this);
        initViews();
        initEvents();
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
                    } else {
                        //thêm món ăn

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
                    tvTitle.setText(R.string.sale);
                    mIsSale = true;
                    mNavigator.addFragment(R.id.flMainContainer, SaleFragment.newInstance(), false, Navigator.NavigateAnim.NONE, SaleFragment.class.getSimpleName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.navMenu:
                try {
                    tvTitle.setText(R.string.menu_title);
                    mIsSale = false;
                    mNavigator.addFragment(R.id.flMainContainer, MenuFragment.newInstance(), false, Navigator.NavigateAnim.NONE, SaleFragment.class.getSimpleName());
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


}
