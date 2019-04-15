package vn.com.misa.cukcuklite.screen.adddish;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.database.IDBUtils;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.data.models.Unit;
import vn.com.misa.cukcuklite.screen.dialogs.color.ColorSelectorDialog;
import vn.com.misa.cukcuklite.screen.dialogs.delete.ConfirmDeleteDialog;
import vn.com.misa.cukcuklite.screen.dialogs.icon.DishIconSelectorDialog;
import vn.com.misa.cukcuklite.screen.selectunit.SelectUnitActivity;
import vn.com.misa.cukcuklite.utils.AppConstants;
import vn.com.misa.cukcuklite.utils.ImageUtils;
import vn.com.misa.cukcuklite.utils.Navigator;

/**
 * Màn hình thêm/sửa món ăn
 * Created_by Nguyễn Bá Linh on 09/04/2019
 */
public class AddDishActivity extends AppCompatActivity implements IAddDishContract.IView, View.OnClickListener, ColorSelectorDialog.IColorSelectedCallBack, DishIconSelectorDialog.IDishIconCallBack, ConfirmDeleteDialog.IConfirmDeleteCallBack {

    public static final String ACTION_OK = "ACTION_OK";
    public static final String EXTRA_UNIT_ID = "EXTRA_UNIT_ID";
    private static final String COLOR_DIALOG = "COLOR_DIALOG";
    private static final String DISH_ICON_DIALOG = "DISH_ICON_DIALOG";
    private static final String DELETE_DIALOG = "DELETE_DIALOG";
    private ImageButton btnBack;
    private TextView tvTitle, tvSave, tvUnit, tvStateTitle;
    private EditText etDishName, tvPrice;
    private ImageView ivSelectUnit, ivSelectPrice, ivColor, ivIcon;
    private CheckBox cbState;
    private Button btnDelete, btnSave;
    private ProgressDialog mDialog;

    private Dish mDish;
    private AddDishPresenter mPresenter;
    private boolean isEdit;
    private Navigator mNavigator;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction() != null) {
                    try {
                        String unitId = intent.getStringExtra(SelectUnitActivity.EXTRA_UNIT_SELECTED);
                        if (unitId != null) {
                            //thiết lập đơn vị cho món ăn
                            mDish.setUnitId(unitId);
                            tvUnit.setText(mPresenter.getUnitName(unitId));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        mNavigator = new Navigator(this);
        mPresenter = new AddDishPresenter();
        mPresenter.setView(this);
        initViews();
        initEvents();
        //đăng kí lắng nghe sự kiện thay đổi đơn vị của món ăn
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(SelectUnitActivity.ACTION_UNIT_SELECTED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //hủy đăng kí lắng nghe sự thay đôi của đơn vị món ăn
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    private void initEvents() {
        btnBack.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvStateTitle.setOnClickListener(this);
        tvUnit.setOnClickListener(this);
        etDishName.setOnClickListener(this);
        tvPrice.setOnClickListener(this);
        ivSelectUnit.setOnClickListener(this);
        ivSelectPrice.setOnClickListener(this);
        ivColor.setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        cbState.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvSave = findViewById(R.id.tvSave);
        tvStateTitle = findViewById(R.id.tvStateTitle);
        tvUnit = findViewById(R.id.tvUnit);
        etDishName = findViewById(R.id.etDishName);
        tvPrice = findViewById(R.id.tvPrice);
        ivSelectUnit = findViewById(R.id.ivSelectUnit);
        ivSelectPrice = findViewById(R.id.ivSelectPrice);
        ivColor = findViewById(R.id.ivColor);
        ivIcon = findViewById(R.id.ivIcon);
        cbState = findViewById(R.id.cbState);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);

        initProgressBar();
        setPaddingForTextToRightOfCheckBox();
        Dish dish = getIntent().getParcelableExtra(AppConstants.EXTRA_DISH);
        if (dish != null) {
            isEdit = true;
            tvTitle.setText(R.string.edit_dish);
            setUpView(dish);
        } else {
            hideViews();
            isEdit = false;
            mDish = new Dish.Builder().setColorCode(AppConstants.COLOR_DEFAULT)
                    .setIconPath(AppConstants.ICON_DEFAULT)
                    .build();
            mPresenter.onStart();
            tvTitle.setText(R.string.add_dish);

        }
    }

    /**
     * Tăng khoảng cách giữa text và icon của checkbox
     * Created_by Nguyễn Bá Linh on 20/03/2019
     */
    private void setPaddingForTextToRightOfCheckBox() {
        final float scale = this.getResources().getDisplayMetrics().density;
        cbState.setPadding(cbState.getPaddingLeft() + (int) (8.0f * scale + 0.5f),
                cbState.getPaddingTop(),
                cbState.getPaddingRight(),
                cbState.getPaddingBottom());
    }

    /**
     * Phương thức gán các giá trị cho view khi món ăn được chỉnh sửa
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     */
    private void setUpView(Dish dish) {
        if (dish != null) {
            mDish = dish;
            cbState.setChecked(!dish.isSale());
            etDishName.setText(dish.getDishName());
            tvPrice.setText(String.valueOf(dish.getPrice()));
            tvUnit.setText(mPresenter.getUnitName(dish.getUnitId()));
            Drawable drawable = getResources().getDrawable(R.drawable.background_dish_icon);
            drawable.setColorFilter(Color.parseColor(dish.getColorCode()), PorterDuff.Mode.SRC);
            ivColor.setBackground(drawable);
            ivIcon.setBackground(drawable);
            ivIcon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(this, dish.getIconPath()));
        }
    }

    /**
     * Ẩn view khi người dùng chọn chức năng thêm món
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void hideViews() {
        cbState.setVisibility(View.GONE);
        tvStateTitle.setVisibility(View.GONE);
        btnDelete.setVisibility(View.GONE);
    }

    /**
     * Thông báo khi người dùng không nhập tên món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void dishNameEmpty() {
        mNavigator.showToastOnTopScreen(R.string.dish_name_not_allow_empty);
        etDishName.requestFocus();
    }

    /**
     * Thông báo cho người dùng đã thêm món ăn thành công và quay về màn hình thực đơn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void addDishSuccess() {
        try {
            mNavigator.showToast(R.string.add_dish_success);
            finishTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addDishFailed(int error) {
        mNavigator.showToastOnTopScreen(error);
    }

    /**
     * Phương thức gán Đơn vị cho món ăn
     * Created_by Nguyễn Bá Linh on 10/04/2019
     *
     * @param unit - đơn vị
     */
    @Override
    public void setUnit(Unit unit) {
        try {
            if (unit != null) {
                String unitId = unit.getUnitId();
                mDish.setUnitId(unitId);
                tvUnit.setText(mPresenter.getUnitName(unitId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thông báo cho người dùng đã cập nhật món ăn thành công và quay về màn hình thực đơn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void upDateDishSuccess() {
        try {
            mNavigator.showToastOnTopScreen(R.string.update_dish_success);
            finishTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thoát màn hình và bắn sự kiện hoàn thành tác vụ
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void finishTask() {
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thông báo cho người dùng đã xóa món ăn thành công và quay về màn hình thực đơn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    @Override
    public void deleteDishSuccess() {
        try {
            mNavigator.showToastOnTopScreen(R.string.delete_success);
            finishTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Phương thức nhận 1 thông điệp
     * Created_by Nguyễn Bá Linh on 09/04/2019
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
     * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param v - view xảy ra sự kiện
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                //trở về màn hình trước đó
                finish();
                break;
            case R.id.ivColor:
                showColorSelector();
                break;
            case R.id.ivSelectPrice:
            case R.id.tvPrice:
                //   showCalculator();
                break;
            case R.id.ivSelectUnit:
            case R.id.tvUnit:
                selectUnit();
                break;
            case R.id.ivIcon:
                showDishIconSelector();
                break;
            case R.id.btnSave:
            case R.id.tvSave:
                save();
                break;
            case R.id.btnDelete:
                showDeleteDishDialogConfirm();
                break;
            default:
                break;
        }
    }


    /**
     * Phương thức hiển thị dialog xác nhận việc xóa món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void showDeleteDishDialogConfirm() {
        try {
            ConfirmDeleteDialog f = ConfirmDeleteDialog.newInstance(mDish.getDishName(), IDBUtils.ITableDishUtils.COLUMN_DISH_NAME);
            f.setCallBack(this);
            getSupportFragmentManager().beginTransaction().add(f, DELETE_DIALOG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức Cất với cả 2 trường hợp là sửa món và thêm món
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void save() {
        if (isEdit) {
            //sửa món
            updateDish();
        } else {
            //thêm món
            addDish();
        }
    }

    /**
     * Phương thức thêm món
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void addDish() {
        try {
            mDish.setDishId(UUID.randomUUID().toString());
            mDish.setDishName(etDishName.getText().toString().trim());
            mDish.setPrice(Integer.parseInt(tvPrice.getText().toString().trim()));
            mDish.setSale(!cbState.isChecked());
            mPresenter.addDish(mDish);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức sửa món
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void updateDish() {
        try {
            mDish.setDishName(etDishName.getText().toString().trim());
            mDish.setPrice(Integer.parseInt(tvPrice.getText().toString().trim()));
            mDish.setSale(!cbState.isChecked());
            mPresenter.updateDish(mDish);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Khởi chạy màn hình lựa chọn đơn vị cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void selectUnit() {
        try {
            Intent intent = new Intent();
            intent.setClass(this, SelectUnitActivity.class);
            intent.putExtra(EXTRA_UNIT_ID, mDish.getUnitId());
            mNavigator.startActivity(intent, Navigator.ActivityTransition.NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị dialog lựa chọn background cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void showColorSelector() {
        try {
            ColorSelectorDialog f = ColorSelectorDialog.newInstance(mDish.getColorCode());
            f.setCallBack(this);
            getSupportFragmentManager().beginTransaction().add(f, COLOR_DIALOG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị dialog lựa chọn icon cho món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void showDishIconSelector() {
        try {
            DishIconSelectorDialog f = new DishIconSelectorDialog();
            f.setCallBack(this);
            getSupportFragmentManager().beginTransaction().add(f, DISH_ICON_DIALOG).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cập nhật màu cho món ăn
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param colorCode - mã màu món ăn
     */
    @Override
    public void onColorSelected(String colorCode) {
        try {
            if (colorCode != null) {
                mDish.setColorCode(colorCode);
                Drawable drawable = getResources().getDrawable(R.drawable.background_dish_icon);
                drawable.setColorFilter(Color.parseColor(colorCode), PorterDuff.Mode.SRC);
                ivColor.setBackground(drawable);
                ivIcon.setBackground(drawable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cập nhật icon cho món ăn
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param iconPath - đường dẫn icon của món ăn
     */
    @Override
    public void onDishIconSelected(String iconPath) {
        try {
            if (iconPath != null) {
                mDish.setIconPath(iconPath);
                ivIcon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(this, iconPath));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void acceptDelete() {
        try {
            mPresenter.deleteDish(mDish.getDishId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
