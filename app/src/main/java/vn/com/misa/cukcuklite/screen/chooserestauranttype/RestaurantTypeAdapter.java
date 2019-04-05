package vn.com.misa.cukcuklite.screen.chooserestauranttype;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.adapters.ListAdapter;
import vn.com.misa.cukcuklite.data.models.RestaurantType;

public class RestaurantTypeAdapter extends ListAdapter<RestaurantType> {

    private int mLastPositionSelected;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    RestaurantTypeAdapter(Context context) {
        super(context);
    }

    RestaurantType getRestaurantTypeIdSelected() {
        try {
            return mListData.get(mLastPositionSelected);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_restaurant_type, viewGroup, false);
        return new RestaurantTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        try {
            RestaurantTypeHolder holder = (RestaurantTypeHolder) viewHolder;
            holder.bind(mListData.get(i));
            if (i == mLastPositionSelected) {
                holder.ivSelected.setImageResource(R.drawable.ic_check_blue);
            } else {
                holder.ivSelected.setImageResource(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lớp item cho loại nhà hàng/quán ăn
     * Created_by Nguyễn Bá Linh on 05/04/2019
     */
    public class RestaurantTypeHolder extends RecyclerView.ViewHolder {
        private TextView tvRestaurantType;
        private ImageView ivSelected;

        public RestaurantTypeHolder(@NonNull View itemView) {
            super(itemView);
            tvRestaurantType = itemView.findViewById(R.id.tvRestaurantType);
            ivSelected = itemView.findViewById(R.id.ivSelected);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int beforePosition = mLastPositionSelected;
                        mLastPositionSelected = getAdapterPosition();
                        notifyItemChanged(beforePosition);
                        notifyItemChanged(mLastPositionSelected);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        /**
         * phương thức gán dữ liệu cho item
         * Created_by Nguyễn Bá Linh on 05/04/2019
         *
         * @param restaurantType - loại quán ăn/nhà hàng
         */
        void bind(RestaurantType restaurantType) {
            try {
                if (restaurantType == null) {
                    return;
                }
                tvRestaurantType.setText(restaurantType.getRestaurantName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
