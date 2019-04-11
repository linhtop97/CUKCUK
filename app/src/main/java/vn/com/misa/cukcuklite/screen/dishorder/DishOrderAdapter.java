package vn.com.misa.cukcuklite.screen.dishorder;

import android.content.Context;
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

    public class DishOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivICon, ivDefault;
        private TextView tvDishName, tvPrice, tvQuantity;
        private LinearLayout lnQuantity;
        private Button btnMinus, btnPlus;
        private ConstraintLayout clDishOrder;
        private BillDetail mBillDetail;
        private DishDataSource mDishDataSource;

        public DishOrderHolder(@NonNull View itemView) {
            super(itemView);
            mDishDataSource = new DishDataSource();
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
            int quantity = mListData.get(getAdapterPosition()).getQuantity();
            switch (v.getId()) {
                case R.id.clDishOrder:
                    if (quantity == 0) {
                        clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_button_gray));
                        mListData.get(getAdapterPosition()).setQuantity(++quantity);
                        lnQuantity.setVisibility(View.VISIBLE);
                        tvQuantity.setText(String.valueOf(quantity));
                        ivDefault.setVisibility(View.GONE);
                    }
                    break;
                case R.id.tvQuantity:
                    Toast.makeText(mContext, "nothing", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnPlus:
                    mListData.get(getAdapterPosition()).setQuantity(++quantity);
                    break;
                case R.id.btnMinus:
                    if (quantity - 1 == 0) {
                        clDishOrder.setBackground(mContext.getResources().getDrawable(R.drawable.selector_dish));
                        lnQuantity.setVisibility(View.GONE);
                        ivICon.setVisibility(View.GONE);
                    }
                    mListData.get(getAdapterPosition()).setQuantity(--quantity);
                    tvQuantity.setText(String.valueOf(quantity));
                    break;
            }
        }

        /**
         * Phương thức kết gán dữ liệu cho item
         * Created_by Nguyễn Bá Linh on 12/04/2019
         *
         * @param billDetail - hóa đơn chi tiết bao gồm các thông tin về món ăn trong hóa đơn
         */
        void bind(BillDetail billDetail) {
            if (billDetail == null) {
                return;
            }
            mBillDetail = billDetail;
            if (billDetail.getDishId() != null) {
                if (billDetail.getQuantity() == 0) {
                    lnQuantity.setVisibility(View.GONE);
                }
                Dish dish = mDishDataSource.getDishById(billDetail.getDishId());
                if (dish != null) {
                    tvDishName.setText(dish.getDishName());
                    tvPrice.setText(String.valueOf(dish.getPrice()));
                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);
                    drawable.setColorFilter(Color.parseColor(dish.getColorCode()), PorterDuff.Mode.SRC);
                    ivICon.setBackground(drawable);
                    ivICon.setImageDrawable(ImageUtils.getDrawableFromImageAssets(mContext, dish.getIconPath()));
                }
            }
        }
    }
}
