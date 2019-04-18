package vn.com.misa.cukcuklite.screen.choosedishdefault;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.adapters.ListAdapter;
import vn.com.misa.cukcuklite.base.listeners.IOnItemClickListener;
import vn.com.misa.cukcuklite.data.models.Dish;
import vn.com.misa.cukcuklite.utils.ImageUtils;

/**
 * Adapter cho danh sách món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class DishAdapter extends ListAdapter<Dish> {

    private IOnItemClickListener<Dish> mItemClickListener;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public DishAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dish, viewGroup, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        DishViewHolder dishViewHolder = (DishViewHolder) viewHolder;
        final Dish dish = mListData.get(i);
        dishViewHolder.bind(dish);
        dishViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(dish);
            }
        });
    }

    /**
     * Phương thức truyền listener cho sự kiện onclick
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param itemClickListener - listener
     */
    public void setItemClickListener(IOnItemClickListener<Dish> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * Lớp Item cho danh sách
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    public class DishViewHolder extends RecyclerView.ViewHolder {
        private ImageButton btnDish;
        private TextView tvDishName, tvPrice, tvStopSale;
        private Drawable drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);

        DishViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDish = itemView.findViewById(R.id.btnDish);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStopSale = itemView.findViewById(R.id.tvState);
        }

        /**
         * Phương thức khởi tạo gắn các giá trị từ món ăn vào view
         * Created_by Nguyễn Bá Linh on 27/03/2019
         *
         * @param dish - món ăn
         */
        void bind(Dish dish) {
            if (dish != null) {
                if (dish.isSale()) {
                    tvStopSale.setVisibility(View.GONE);
                } else {
                    tvStopSale.setVisibility(View.VISIBLE);
                }
                tvDishName.setText(dish.getDishName());
                tvPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(dish.getPrice()));
                drawable.setColorFilter(Color.parseColor(dish.getColorCode()), PorterDuff.Mode.SRC);
                btnDish.setBackground(drawable);
                btnDish.setImageDrawable(ImageUtils.getDrawableFromImageAssets(mContext, dish.getIconPath()));
            }
        }
    }
}
