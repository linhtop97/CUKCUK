package vn.com.misa.cukcuklite.screen.sale;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.models.Order;
import vn.com.misa.cukcuklite.screen.dishorder.DishOrderActivity;

/**
 * Màn hình bán hàng
 * Created_by Nguyễn Bá Linh on 09/04/2019s
 */
public class SaleFragment extends Fragment implements ISaleContract.IView {

    private RecyclerView rvOrder;
    private ConstraintLayout clWaterMark;
    private Context mContext;
    private SalePresenter mPresenter;
    private OrderAdapter mAdapter;
    private ProgressDialog mDialog;
    private TextView tvAddOrder;

    public static SaleFragment newInstance() {
        return new SaleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        mPresenter = new SalePresenter();
        mPresenter.setView(this);
        initViews(view);
        initEvents();
        return view;
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 15/04/2019
     */
    private void initEvents() {
        tvAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(mContext, DishOrderActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 15/04/2019
     */
    private void initViews(View view) {
        try {
            clWaterMark = view.findViewById(R.id.clWaterMark);
            rvOrder = view.findViewById(R.id.rvOrder);
            tvAddOrder = view.findViewById(R.id.tvAddOrder);
            rvOrder.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter = new OrderAdapter(mContext);
            rvOrder.setAdapter(mAdapter);
            initProgressBar();
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
            mDialog = new ProgressDialog(mContext) {
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

    @Override
    public void onResume() {
        super.onResume();
        //khi màn hình được tương tác thì sẽ load lại dữ liệu
        mPresenter.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void showListOrder(List<Order> orders) {
        try {
            if (orders != null && orders.size() > 0) {
                clWaterMark.setVisibility(View.GONE);
                mAdapter.setListData(orders);
            } else {
                clWaterMark.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created_by Nguyễn Bá Linh on 15/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {

    }

    /**
     * Phương thức hiển thị 1 dialog chờ xử lý tác vụ với 1 thông điệp
     * Created_by Nguyễn Bá Linh on 15/04/2019
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
     * Created_by Nguyễn Bá Linh on 15/04/2019
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
}
