package vn.com.misa.cukcuklite.screen.dishorder;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.listeners.IOnItemClickListener;
import vn.com.misa.cukcuklite.data.models.BillDetail;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình thêm món ăn cho hóa đơn
 * Created_by Nguyễn Bá Linh on 12/04/2019
 */
public class DishOrderActivity extends AppCompatActivity implements DishOrderContract.IView, IOnItemClickListener<Integer> {

    private DishOrderPresenter mPresenter;
    private ProgressDialog mDialog;
    private Navigator mNavigator;
    private DishOrderAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_order);
        mNavigator = new Navigator(this);
        initViews();
        initEvents();
        mPresenter.onStart();
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 12/04/2019
     */
    private void initEvents() {
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 12/04/2019
     */
    private void initViews() {
        try {
            RecyclerView rvDishOrder = findViewById(R.id.rvDishOrder);
            rvDishOrder.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new DishOrderAdapter(this);
            mAdapter.setOnItemClickListener(this);
            rvDishOrder.setAdapter(mAdapter);
            initProgressBar();
            //kiểm tra đầu vào r mới khởi tạo presenter
            mPresenter = new DishOrderPresenter(UUID.randomUUID().toString());
            mPresenter.setView(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Phương thức khởi tạo progressbar
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    private void initProgressBar() {
        try {
            mDialog = new ProgressDialog(this) {
                @Override
                public void onBackPressed() {
                    super.onBackPressed();
                }
            };
            mDialog.setMessage(getString(R.string.init_dish_list));
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created_by Nguyễn Bá Linh on 09/04/2019
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
     * Created_by Nguyễn Bá Linh on 09/04/2019
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
     * Phương thức xử lý sự kiện khi item món ăn được click
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param data là vị trí item được click
     */
    @Override
    public void onItemClick(Integer data) {

    }

    /**
     * Phương thức hiển thị danh sách các món ăn của hóa đơn chi tiết
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param billDetails - danh sách món ăn của hóa đơn chi tiết
     */
    @Override
    public void showListDishOrder(List<BillDetail> billDetails) {
        try {
            if (billDetails != null) {
                mAdapter.setListData(billDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
