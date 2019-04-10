package vn.com.misa.cukcuklite.screen.dialogs.icon;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.util.List;
import java.util.Objects;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.listeners.IOnItemClickListener;
import vn.com.misa.cukcuklite.utils.ImageUtils;

/**
 * Dialog hiển thị danh sách icon cho món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class DishIconSelectorDialog extends DialogFragment implements IOnItemClickListener<String> {
    private Button btnCancel;
    private List<String> mStringList;
    private Context mContext;
    private IDishIconCallBack mCallBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mStringList = ImageUtils.getAllImage(mContext);
        View view = inflater.inflate(R.layout.dialog_dish_icon_selector, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 10/04/2019
     */
    private void initEvents() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 10/04/2019
     */
    private void initViews(View view) {
        btnCancel = view.findViewById(R.id.btnCancel);
        RecyclerView rvDishIcon = view.findViewById(R.id.rvDishIcon);
        IconAdapter adapter = new IconAdapter(getContext(), mStringList);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 5);
        rvDishIcon.setLayoutManager(linearLayoutManager);
        adapter.setItemClickListener(this);
        rvDishIcon.setAdapter(adapter);
    }

    /**
     * Khởi tạo callback cho dialog
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param callBack - callback
     */
    public void setCallBack(IDishIconCallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * Chỉnh lại kích thước dialog khi hiển thị trên thiết bị
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public void onResume() {
        super.onResume();
        try {
            Window window = getDialog().getWindow();
            int width = getResources().getDimensionPixelSize(R.dimen.color_dialog_width);
            int height = getResources().getDimensionPixelSize(R.dimen.color_dialog_height);
            assert window != null;
            window.setLayout(width, height);
            window.setGravity(Gravity.CENTER);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Ghi đè hàm khởi tạo dialog, thiết lập dialog
     * Created_by Nguyễn Bá Linh on 10/04/2019
     *
     * @param savedInstanceState - gói dữ liệu
     * @return - dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    // Disable Back key and Search key
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_SEARCH:
                            return true;
                        case KeyEvent.KEYCODE_BACK:
                            return true;
                        default:
                            return false;
                    }
                }
            });
            return dialog;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Phương thức xử lý khi icon được lựa chọn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param fileName - tên file icon
     */
    @Override
    public void onItemClick(String fileName) {
        try {
            mCallBack.onDishIconSelected(fileName);
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Call back được gọi để cập icon cho món ăn
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    public interface IDishIconCallBack {
        void onDishIconSelected(String iconPath);
    }
}