package vn.com.misa.cukcuklite.screen.menu;

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
import vn.com.misa.cukcuklite.base.listeners.IOnItemClickListener;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.screen.adddish.AddDishActivity;
import vn.com.misa.cukcuklite.screen.choosedishdefault.DishAdapter;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình thực đơn
 * Created_by Nguyễn Bá Linh on 09/04/2019
 */
public class MenuFragment extends Fragment implements IMenuContract.IView, IOnItemClickListener<Dish> {

    public static final String EXTRA_DISH = "EXTRA_DISH";

    private Context mContext;
    private Navigator mNavigator;
    private MenuPresenter mPresenter;
    private DishAdapter mAdapter;
    private ProgressDialog mDialog;
    private ConstraintLayout clWaterMark;
    private TextView tvAddDish;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mNavigator = new Navigator(this);
        mPresenter = new MenuPresenter();
        mPresenter.setView(this);
        initViews(view);
        initEvents();
        return view;
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 11/04/2019
     */
    private void initEvents() {
        tvAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //khởi động màn hình thêm món ăn
                    mNavigator.startActivity(AddDishActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //load dữ liệu khi màn hình trở lại
        mPresenter.onStart();
    }

    /**
     * Phương thức khởi tạo view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initViews(View view) {
        try {
            RecyclerView rvDish = view.findViewById(R.id.rvDish);
            clWaterMark = view.findViewById(R.id.clWaterMark);
            tvAddDish = view.findViewById(R.id.tvAddDish);
            mAdapter = new DishAdapter(mContext);
            mAdapter.setItemClickListener(this);
            rvDish.setLayoutManager(new LinearLayoutManager(mContext));
            rvDish.setAdapter(mAdapter);
            initProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị danh sách món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dishes - danh sách món ăn
     */
    @Override
    public void showDish(List<Dish> dishes) {
        try {
            if (dishes != null && dishes.size() > 0) {
                clWaterMark.setVisibility(View.GONE);
                mAdapter.setListData(dishes);
            } else {
                mAdapter.clearData();
                clWaterMark.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức nhận 1 thông điệp
     * Created_by Nguyễn Bá Linh on 10/04/2019
     *
     * @param message - thông điệp được nhận
     */
    @Override
    public void receiveMessage(int message) {
        mNavigator.showToastOnTopScreen(message);
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
     * Phương thức gán context ứng dụng cho biến của fragment
     * Created_by Nguyễn Bá Linh on 10/04/2019
     *
     * @param context - context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Phương thức xử lý sự kiện khi item món ăn được click
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param data là tham số được truyền vào khi View được click
     */
    @Override
    public void onItemClick(Dish data) {
        try {
            Intent intent = new Intent();
            intent.setClass(mContext, AddDishActivity.class);
            intent.putExtra(EXTRA_DISH, data);
            mNavigator.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

