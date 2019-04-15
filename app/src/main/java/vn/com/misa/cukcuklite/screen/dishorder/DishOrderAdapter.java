package vn.com.misa.cukcuklite.screen.dishorder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.adapters.ListAdapter;
import vn.com.misa.cukcuklite.base.listeners.IOnItemClickListener;
import vn.com.misa.cukcuklite.data.dish.DishDataSource;
import vn.com.misa.cukcuklite.data.models.BillDetail;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.utils.ImageUtils;

public class DishOrderAdapter extends ListAdapter<BillDetail> {

    private IOnItemClickListener<Integer> mOnItemClickListener;


    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public DishOrderAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dish_order, viewGroup, false);
        return new DishOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DishOrderHolder dishOrderHolder = (DishOrderHolder) viewHolder;
        dishOrderHolder.bind(mListData.get(i));
    }

    /**
     * Phương thức gán listener lắng nghe khi click vào item
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param onItemClickListener - listener
     */
    public void setOnItemClickListener(IOnItemClickListener<Integer> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * Tính tổng tiền hóa đơn
     * Created_by Nguyễn Bá Linh on 12/04/2019
     */
    private void totalMoney() {
        try {
            int totalMoney = 0;
            for (int i = 0; i < mListData.size(); i++) {
                totalMoney += mListData.get(i).getTotalMoney();
            }
            mOnItemClickListener.onItemClick(totalMoney);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class DishOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivICon, ivDefault;
        private TextView tvDishName, tvPrice, tvQuantity;
        private LinearLayout lnQuantity;
        private Button btnMinus, btnPlus;
        private ConstraintLayout clDishOrder;
        private BillDetail mBillDetail;
        private DishDataSource mDishDataSource;
        private int mPrice;

        public DishOrderHolder(@NonNull View itemView) {
            super(itemView);
            mDishDataSource = DishDataSource.getInstance();
            initViews(itemView);
            initEvents();
        }

        /**
         * Phương thức gắn sự kiện cho view
         * Created_by Nguyễn Bá Linh on 12/04/2019
         */
        private void initEvents() {
            clDishOrder.setOnClickListener(this);
            btnMinus.setOnClickListener(this);
            btnPlus.setOnClickListener(this);
            tvQuantity.setOnClickListener(this);
            ivDefault.setOnClickListener(this);
        }

        /**
         * Phương thức tham chiếu, khởi tạo view
         * Created_by Nguyễn Bá Linh on 12/04/2019
         */
        private void initViews(View view) {
            ivICon = view.findViewById(R.id.ivIcon);
            ivDefault = view.findViewById(R.id.ivDefault);
            tvDishName = view.findViewById(R.id.tvDishName);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            lnQuantity = view.findViewById(R.id.lnQuantity);
            tvPrice = view.findViewById(R.id.tvPrice);
            btnMinus = view.findViewById(R.id.btnMinus);
            btnPlus = view.findViewById(R.id.btnPlus);
            clDishOrder = view.findViewById(R.id.clDishOrder);
        }

        /**
         * Phương thức xử lý các sự kiện click cho các view được gắn sự kiện OnClick
         * Created_by Nguyễn Bá Linh on 12/04/2019
         *
         * @param v - view xảy ra sự kiện
         */
        @Override
        public void onClick(View v) {
            //lấy số lượng món ăn từ text view
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            switch (v.getId()) {
                case R.id.clDishOrder:
                    try {
                        if (quantity == 0) {
                            //khi click vào item, nếu hiện tại số lượng là 0 thì thay đổi background
                            clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_button_gray));
                            lnQuantity.setVisibility(View.VISIBLE);
                            ivDefault.setVisibility(View.VISIBLE);
                            ivICon.setVisibility(View.INVISIBLE);
                        }
                        //tăng số lượng lên 1
                        tvQuantity.setText(String.valueOf(++quantity));
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tvQuantity:
                    try {
                        Toast.makeText(mContext, "nothing", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.ivDefault:
                    try {
                        ivDefault.setVisibility(View.GONE);
                        ivICon.setVisibility(View.VISIBLE);
                        quantity = 0;
                        tvQuantity.setText(String.valueOf(0));
                        clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_dish));
                        lnQuantity.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btnPlus:
                    try {
                        //tăng số lượng lên 1
                        tvQuantity.setText(String.valueOf(++quantity));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btnMinus:
                    try {
                        --quantity;
                        if (quantity == 0) {
                            //giảm số lượng nếu bằng 0 thì thay đổi background và hiện icon phủ xanh
                            clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_dish));
                            lnQuantity.setVisibility(View.GONE);
                            ivDefault.setVisibility(View.INVISIBLE);
                            ivICon.setVisibility(View.VISIBLE);
                        } else if (quantity < 0) {
                            quantity = 0;
                        }
                        tvQuantity.setText(String.valueOf(quantity));
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            //gán số lượng và tổng tiền món ăn của hóa đơn chi tiết
            mBillDetail.setQuantity(quantity);
            mBillDetail.setTotalMoney(quantity * mPrice);
            mListData.set(getAdapterPosition(), mBillDetail);
            totalMoney();
        }

        /**
         * Phương thức kết gán dữ liệu cho item
         * Created_by Nguyễn Bá Linh on 12/04/2019
         *
         * @param billDetail - hóa đơn chi tiết bao gồm các thông tin về món ăn trong hóa đơn
         */
        void bind(BillDetail billDetail) {
            try {
                if (billDetail == null) {
                    return;
                }
                mBillDetail = billDetail;
                if (billDetail.getDishId() != null) {
                    int quantity = billDetail.getQuantity();
                    if (quantity == 0) {
                        lnQuantity.setVisibility(View.GONE);
                        clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_dish));
                    } else if (quantity > 0) {
                        clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_button_gray));
                        lnQuantity.setVisibility(View.VISIBLE);
                    }

                    Dish dish = mDishDataSource.getDishById(billDetail.getDishId());
                    if (dish != null) {
                        mPrice = dish.getPrice();
                        tvDishName.setText(dish.getDishName());
                        tvPrice.setText(String.valueOf(dish.getPrice()));
                        Drawable drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);
                        drawable.setColorFilter(Color.parseColor(dish.getColorCode()), PorterDuff.Mode.SRC);
                        ivICon.setBackground(drawable);
                        ivICon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(mContext, dish.getIconPath()));
                        if (billDetail.getQuantity() > 0) {
                            ivDefault.setVisibility(View.VISIBLE);
                            ivICon.setVisibility(View.INVISIBLE);
                        } else {
                            ivICon.setVisibility(View.VISIBLE);
                            ivDefault.setVisibility(View.GONE);
                        }
                    }
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
