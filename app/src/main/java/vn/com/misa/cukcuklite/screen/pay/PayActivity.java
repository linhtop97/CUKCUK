package vn.com.misa.cukcuklite.screen.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.models.Bill;
import vn.com.misa.cukcuklite.data.models.BillDetail;
import vn.com.misa.cukcuklite.screen.dishorder.DishOrderActivity;
import vn.com.misa.cukcuklite.utils.AppConstants;
import vn.com.misa.cukcuklite.utils.Navigator;
import vn.com.misa.cukcuklite.utils.StringUtils;

/**
 * Màn hình thanh toán hóa đơn
 * Created_by Nguyễn Bá Linh on 16/04/2019
 */
public class PayActivity extends AppCompatActivity implements View.OnClickListener, IPayContract.IView {
    private ImageButton btnBack;
    private Button btnDone;
    private TextView tvMoneyHaveToPay, tvBillNumber, tvDone, tvDateCreated, tvMoneyReturn;
    private EditText etMoneyGuestPay;
    private RecyclerView rvBill;
    private PayPresenter mPresenter;
    private BillDetailAdapter mAdapter;
    private Navigator mNavigator;
    private Bill mBill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        mNavigator = new Navigator(this);
        initViews();
        initEvents();
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 16/04/2019
     */
    private void initViews() {
        try {
            btnBack = findViewById(R.id.btnBack);
            btnDone = findViewById(R.id.btnDone);
            tvMoneyHaveToPay = findViewById(R.id.tvMoneyHaveToPay);
            tvBillNumber = findViewById(R.id.tvBillNumber);
            tvDone = findViewById(R.id.tvDone);
            tvDateCreated = findViewById(R.id.tvDateCreated);
            tvMoneyReturn = findViewById(R.id.tvMoneyReturn);
            etMoneyGuestPay = findViewById(R.id.etMoneyGuestPay);
            rvBill = findViewById(R.id.rvBill);
            rvBill.setLayoutManager(new LinearLayoutManager(this));
            mAdapter = new BillDetailAdapter(this);
            rvBill.setAdapter(mAdapter);
            getBillId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức lấy hóa đơn id để lấy và gán dữ liệu cho view
     * Created_by Nguyễn Bá Linh on 16/04/2019
     */
    private void getBillId() {
        Intent intent = getIntent();
        String billId = intent.getStringExtra(AppConstants.EXTRA_BILL_ID);
        if (billId != null) {
            mPresenter = new PayPresenter(billId);
            mPresenter.setView(this);
            mPresenter.onStart();
        }
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 16/04/2019
     */
    private void initEvents() {
        try {
            btnBack.setOnClickListener(this);
            btnDone.setOnClickListener(this);
            tvDone.setOnClickListener(this);
            tvMoneyReturn.setOnClickListener(this);
            etMoneyGuestPay.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created_by Nguyễn Bá Linh on 16/04/2019
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
            case R.id.tvDone:
            case R.id.btnDone:
                try {
                    payBill();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * Phương thức thanh toán hóa đơn
     * Created_by Nguyễn Bá Linh on 16/04/2019
     */
    private void payBill() {
        try {
            int moneyHaveToPay = Integer.parseInt(tvMoneyHaveToPay.getText().toString());
            String moneyGuestPay = etMoneyGuestPay.getText().toString();
            int moneyGuestPay2 = 0;
            if (!moneyGuestPay.equals("")) {
                moneyGuestPay2 = Integer.parseInt(moneyGuestPay);
            }
            if (moneyHaveToPay > moneyGuestPay2) {
                mNavigator.showToastOnTopScreen(R.string.the_amount_of_guest_money_must_not_be_less_than_the_amount_payable);
            } else {
                mBill.setCustomerPay(moneyGuestPay2);
                mPresenter.pay(mBill);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức xử lý khi thanh toán thành công
     * Created_by Nguyễn Bá Linh on 17/04/2019
     */
    @Override
    public void paySuccess() {
        try {
            //Đóng màn hình thanh toán và trở về màn hình trước
            Intent intent = new Intent(this, DishOrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị, gán danh sách hóa đơn chi tiết vào hóa đơn
     * Created_by Nguyễn Bá Linh on 16/04/2019
     *
     * @param bill           - hóa đơn
     * @param billDetailList - danh sách hóa đơn chi tiết
     * @param billNumber     - số hóa đơn
     */
    @Override
    public void setBill(Bill bill, List<BillDetail> billDetailList, int billNumber) {
        try {
            if (bill != null && billDetailList != null) {
                mBill = bill;
                mBill.setBillNumber(billNumber + 1);
                mAdapter.setListData(billDetailList);
                String money = String.valueOf(bill.getTotalMoney());
                tvMoneyHaveToPay.setText(money);
                etMoneyGuestPay.setText(money);
                DecimalFormat decimalFormat = new DecimalFormat("00000");
                tvBillNumber.setText(decimalFormat.format(billNumber + 1));
                long dateCreated = Calendar.getInstance().getTimeInMillis();
                mBill.setDateCreated(dateCreated);
                tvDateCreated.setText(StringUtils.getDate(dateCreated));
            } else {
                mNavigator.showToastOnTopScreen(R.string.something_went_wrong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created_by Nguyễn Bá Linh on 16/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created_by Nguyễn Bá Linh on 16/04/2019
     */
    @Override
    public void showLoading() {

    }

    /**
     * Phương thức ẩn/đóng dialog đang chờ xử lý khi thực hiện xong tác vụ
     * Created_by Nguyễn Bá Linh on 16/04/2019
     */
    @Override
    public void hideLoading() {

    }
}
