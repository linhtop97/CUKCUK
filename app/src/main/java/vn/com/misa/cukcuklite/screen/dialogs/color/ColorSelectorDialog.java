package vn.com.misa.cukcuklite.screen.dialogs.color;

import android.app.Dialog;
import android.content.DialogInterface;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.listeners.IOnItemClickListener;
import vn.com.misa.cukcuklite.utils.AppConstants;

/**
 * Dialog hiển thị lựa chọn background cho icon của món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class ColorSelectorDialog extends DialogFragment implements IOnItemClickListener<String> {
    Button btnCancel;
    private List<String> mListColorCode;
    private IColorSelectedCallBack mCallBack;

    public static ColorSelectorDialog newInstance(String colorCode) {
        ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ARG_DISH_COLOR_CODE, colorCode);
        colorSelectorDialog.setArguments(bundle);
        return colorSelectorDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListColorCode = Arrays.asList(getResources().getStringArray(R.array.color_list));
        View view = inflater.inflate(R.layout.dialog_color_selector, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 09/04/2019
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
     * Phương thức khởi tạo các view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initViews(View view) {
        RecyclerView rvColor = view.findViewById(R.id.rvColor);
        btnCancel = view.findViewById(R.id.btnCancel);
        Bundle bundle = getArguments();
        int lastPosition = AppConstants.POSITION_DEFAULT;
        //xác định vị trí màu của món ăn được chọn
        if (bundle != null) {
            String colorCode = bundle.getString(AppConstants.ARG_DISH_COLOR_CODE, "");
            String[] colorList = getResources().getStringArray(R.array.color_list);
            for (int i = 0; i < colorList.length; i++) {
                if (colorCode.equals(colorList[i])) {
                    lastPosition = i;
                    break;
                }
            }
        }
        ColorAdapter adapter = new ColorAdapter(getContext(), mListColorCode, lastPosition);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 4);
        rvColor.setLayoutManager(linearLayoutManager);
        adapter.setListener(this);
        rvColor.setAdapter(adapter);
    }

    /**
     * Khởi tạo callback cho dialog
     * Created_by Nguyễn Bá Linh on 09/04/2019
     *
     * @param callBack - callback
     */
    public void setCallBack(IColorSelectedCallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * Chỉnh lại kích thước dialog khi hiển thị trên thiết bị
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        int width = getResources().getDimensionPixelSize(R.dimen.color_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.color_dialog_height);
        assert window != null;
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
    }

    /**
     * Phương thức xử lý khi item color được click
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param colorCode - mã màu của món ăn
     */
    @Override
    public void onItemClick(String colorCode) {
        try {
            mCallBack.onColorSelected(colorCode);
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Call back được gọi để cập nhật màu cho món ăn
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    public interface IColorSelectedCallBack {
        void onColorSelected(String colorCode);
    }
}