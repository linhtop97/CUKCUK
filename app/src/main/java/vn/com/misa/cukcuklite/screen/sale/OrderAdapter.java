package vn.com.misa.cukcuklite.screen.sale;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.adapters.ListAdapter;
import vn.com.misa.cukcuklite.data.bill.BillDataSource;
import vn.com.misa.cukcuklite.data.models.Bill;

public class OrderAdapter extends ListAdapter<Bill> {

    private IOrderClickListener mIOrderClickListener;
    private BillDataSource mBillDataSource;

    /**
     * Là phương thức khởi tạo cho ListAdapter
     * Created_by Nguyễn Bá Linh on 25/03/2019
     *
     * @param context là được truyền tới từ context nơi khởi tạo thể hiện của lớp
     */
    public OrderAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return super.onCreateViewHolder(viewGroup, i);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
    }

    /**
     * Phương thức gán listener lắng nghe xử lý sự kiện cho item view
     * Created_by Nguyễn Bá Linh on 13/04/2019
     *
     * @param orderClickListener - listener
     */
    public void setIOrderClickListener(IOrderClickListener orderClickListener) {
        mIOrderClickListener = orderClickListener;
    }

    public interface IOrderClickListener {
        void orderClick(String billId);

        void cancelOrder(String billId);

        void payOrder(String billId);
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        private ImageView ivBackground;
        private TextView tvTable, tvPerson, tvContent, tvTotalMoney;
        private LinearLayout lnCancel, lnPay;
        private Drawable drawable = mContext.getResources().getDrawable(R.drawable.background_dish_icon);

        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
            initEvents();
        }


        /**
         * Phương thức khởi tạo gắn các giá trị từ hóa đơn vào view
         * Created_by Nguyễn Bá Linh on 27/03/2019
         *
         * @param bill - hóa đơn
         */
        void bind(Bill bill) {
            if (bill == null) {
                return;
            }
            if (bill.getBillId() != null) {
                //Lấy danh sách tên món ăn chi tiết, số lượng, tổng tiền của
                tvTotalMoney.setText(String.valueOf(bill.getTotalMoney()));
                drawable.setColorFilter(Color.parseColor(mContext.getResources().getStringArray(R.array.color_list)[new Random().nextInt(32)]), PorterDuff.Mode.SRC);
                ivBackground.setBackground(drawable);
            }
        }

        /**
         * Phương thức gắn sự kiện cho view
         * Created_by Nguyễn Bá Linh on 13/04/2019
         */
        private void initEvents() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mIOrderClickListener.orderClick(mListData.get(getAdapterPosition()).getBillId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            lnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mIOrderClickListener.cancelOrder(mListData.get(getAdapterPosition()).getBillId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            lnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mIOrderClickListener.payOrder(mListData.get(getAdapterPosition()).getBillId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        }

        /**
         * Phương thức tham chiếu, khởi tạo view
         * Created_by Nguyễn Bá Linh on 13/04/2019
         */
        private void initViews(View view) {
            try {
                ivBackground = view.findViewById(R.id.ivBackground);
                tvTable = view.findViewById(R.id.tvTable);
                tvPerson = view.findViewById(R.id.tvPerson);
                tvContent = view.findViewById(R.id.tvContent);
                tvTotalMoney = view.findViewById(R.id.tvTotalMoney);
                lnCancel = view.findViewById(R.id.lnCancel);
                lnPay = view.findViewById(R.id.lnPay);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
