package vn.com.misa.cukcuklite.screen.dishorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.listeners.IOnItemClickListener;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;
import vn.com.misa.cukcuklite.screen.main.MainActivity;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình thêm món ăn cho hóa đơn
 * Created_by Nguyễn Bá Linh on 12/04/2019
 */
public class DishOrderActivity extends AppCompatActivity implements DishOrderContract.IView, IOnItemClickListener<Integer>, View.OnClickListener {

    private DishOrderPresenter mPresenter;
    private ProgressDialog mDialog;
    private Navigator mNavigator;
    private DishOrderAdapter mAdapter;
    private TextView tvTotalMoney, tvPay;
    private EditText tvTable, tvPerson;
    private ImageButton btnBack;
    private ConstraintLayout clWaterMark;
    private Button btnSave, btnPay;
    private Bill mBill;

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
        tvPay.setOnClickListener(this);
        tvTable.setOnClickListener(this);
        tvPerson.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 12/04/2019
     */
    private void initViews() {
        try {
            tvTotalMoney = findViewById(R.id.tvTotalMoney);
            tvPay = findViewById(R.id.tvPay);
            tvTable = findViewById(R.id.tvTable);
            tvPerson = findViewById(R.id.tvPerson);
            btnBack = findViewById(R.id.btnBack);
            clWaterMark = findViewById(R.id.clWaterMark);
            btnSave = findViewById(R.id.btnSave);
            btnPay = findViewById(R.id.btnPay);
            //khởi tạo recycler view
            RecyclerView rvDishOrder = findViewById(R.id.rvDishOrder);
            rvDishOrder.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new DishOrderAdapter(this);
            mAdapter.setOnItemClickListener(this);
            rvDishOrder.setAdapter(mAdapter);
            initProgressBar();
            //kiểm tra đầu vào r mới khởi tạo presenter
            mBill = new Bill();
            mPresenter = new DishOrderPresenter(mBill.getBillId());
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
        tvTotalMoney.setText(String.valueOf(data));
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

    /**
     * Phương thức xử lý khi thêm Order thành công
     * Created_by Nguyễn Bá Linh on 12/04/2019
     */
    @Override
    public void saveOrderSuccess() {
        try {
            checkIfIsOnlyActivity();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý khi thêm Order thất bại
     * Created_by Nguyễn Bá Linh on 12/04/2019
     */
    @Override
    public void saveOrderFailed() {
        mNavigator.showToastOnTopScreen(R.string.something_went_wrong);
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPay:
                break;
            case R.id.btnSave:
                try {
                    int totalMoney = Integer.parseInt(tvTotalMoney.getText().toString());
                    if (totalMoney > 0) {
                        mBill.setTotalMoney(totalMoney);
                        if (!tvTable.getText().toString().isEmpty()) {
                            mBill.setTableNumber(Integer.parseInt(tvTable.getText().toString()));
                        }
                        if (!tvPerson.getText().toString().isEmpty()) {
                            mBill.setNumberCustomer(Integer.parseInt(tvPerson.getText().toString()));
                        }
                        mPresenter.saveOrder(mBill, mAdapter.getListData());
                    } else {
                        mNavigator.showToastOnTopScreen(R.string.you_have_not_select_dish_yet);
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvPerson:
                break;
            case R.id.tvTable:
                break;
            case R.id.btnBack:
                try {
                    checkIfIsOnlyActivity();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnPay:
                break;
            default:
                break;
        }
    }

    /**
     * Phương thức kiểm tra liệu đây có phải là activity duy nhất đang hoạt động
     * nếu đúng thì khi bấm nút trở về sẽ mở màn hình main activity
     * Created_by Nguyễn Bá Linh on 15/04/2019
     */
    private void checkIfIsOnlyActivity() {
        try {
            if (isTaskRoot()) {
                startActivity(new Intent(this, MainActivity.class));
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        checkIfIsOnlyActivity();
        super.onBackPressed();
    }
}
