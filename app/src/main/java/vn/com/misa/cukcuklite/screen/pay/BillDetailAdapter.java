package vn.com.misa.cukcuklite.screen.pay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.adapters.ListAdapter;
import vn.com.misa.cukcuklite.data.models.BillDetail;

public class BillDetailAdapter extends ListAdapter<BillDetail> {

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public BillDetailAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bill, viewGroup, false);
        return new BillDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BillDetailHolder billDetailHolder = (BillDetailHolder) viewHolder;
        billDetailHolder.bind(mListData.get(i));
    }

    public class BillDetailHolder extends RecyclerView.ViewHolder {

        private TextView tvDishName, tvQuantity, tvPrice, tvTotalMoney;

        public BillDetailHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        /**
         * Phương thức tham chiếu, khởi tạo view
         * Created_by Nguyễn Bá Linh on 16/04/2019
         */
        private void initViews(View itemView) {
            try {
                tvDishName = itemView.findViewById(R.id.tvDishName);
                tvQuantity = itemView.findViewById(R.id.tvQuantity);
                tvPrice = itemView.findViewById(R.id.tvPrice);
                tvTotalMoney = itemView.findViewById(R.id.tvTotalMoney);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void bind(BillDetail billDetail) {
            try {
                if (billDetail == null) {
                    return;
                }
                tvDishName.setText(billDetail.getName());
                int quantity = billDetail.getQuantity();
                int totalMoney = billDetail.getTotalMoney();
                tvQuantity.setText(String.valueOf(quantity));
                tvPrice.setText(NumberFormat.getNumberInstance(Locale.US).format(totalMoney / quantity));
                tvTotalMoney.setText(NumberFormat.getNumberInstance(Locale.US).format(totalMoney));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
