package vn.com.misa.cukcuklite.screen.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.utils.AppConstants;

/**
 * Dialog hiển thị xác nhận xóa món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class ConfirmDeleteDialog extends DialogFragment implements View.OnClickListener {
    private IConfirmDeleteCallBack mCallBack;
    private Button btnClose, btnYes, btnNo;

    public static ConfirmDeleteDialog newInstance(String dishName) {
        ConfirmDeleteDialog colorSelectorDialog = new ConfirmDeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ARG_DISH_NAME, dishName);
        colorSelectorDialog.setArguments(bundle);
        return colorSelectorDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    /**
     * Phương thức gắn sự kiện cho view
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    private void initEvents() {
        try {
            btnClose.setOnClickListener(this);
            btnNo.setOnClickListener(this);
            btnYes.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức khởi tạo các view
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private void initViews(View view) {
        TextView tvConfirm = view.findViewById(R.id.tvConfirm);
        btnClose = view.findViewById(R.id.btnClose);
        btnYes = view.findViewById(R.id.btnYes);
        btnNo = view.findViewById(R.id.btnNo);
        Bundle bundle = getArguments();
        String dishName = "";
        if (bundle != null) {
            dishName = bundle.getString(AppConstants.ARG_DISH_NAME);
        }
        tvConfirm.setText(Html.fromHtml(getString(R.string.confirm_delete_dish, dishName)));

    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        int width = getResources().getDimensionPixelSize(R.dimen.color_dialog_width);
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNo:
            case R.id.btnClose:
                dismiss();
                break;
            case R.id.btnYes:
                mCallBack.acceptDelete();
                dismiss();
                break;
        }
    }

    public void setCallBack(IConfirmDeleteCallBack callBack) {
        mCallBack = callBack;
    }
}