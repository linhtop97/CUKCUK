package vn.com.misa.cukcuklite.screen.reporttotal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.data.models.ReportTotal;

/**
 * Adapter báo cáo gần đây
 * Created_by Nguyễn Bá Linh on 18/04/2019
 */
public class ReportTotalAdapter extends RecyclerView.Adapter<ReportTotalAdapter.ViewHolder> {

    private Context mContext;

    private List<ReportTotal> mReportTotals;
    private LayoutInflater mLayoutInflater;
    private OnClickItemTotalReport mOnClickItemTotalReport;

    public ReportTotalAdapter(Context context, List<ReportTotal> reportTotals) {
        mContext = context;
        mReportTotals = reportTotals;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnClickItemTotalReport(OnClickItemTotalReport onClickItemTotalReport) {
        mOnClickItemTotalReport = onClickItemTotalReport;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.item_report_total, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            final ReportTotal reportTotal = mReportTotals.get(position);
            holder.tvTitle.setText(reportTotal.getTitleReportDetail());
            holder.tvAmount
                    .setText(NumberFormat.getNumberInstance(Locale.US).format(reportTotal.getAmount()));

            holder.lnContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (reportTotal.getAmount() > 0) {
                        mOnClickItemTotalReport.onClickItem(reportTotal);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return mReportTotals != null ? mReportTotals.size() : 0;
    }

    public void setData(List<ReportTotal> reportTotals) {
        if (reportTotals == null) {
            return;
        }
        mReportTotals.clear();
        mReportTotals.addAll(reportTotals);
        notifyDataSetChanged();
    }

    public interface OnClickItemTotalReport {

        void onClickItem(ReportTotal reportTotal);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout lnContent;
        private TextView tvTitle, tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lnContent = itemView.findViewById(R.id.lnContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
