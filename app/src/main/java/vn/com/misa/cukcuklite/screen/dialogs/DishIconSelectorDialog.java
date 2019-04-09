package vn.com.misa.cukcuklite.screen.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.List;
import java.util.Objects;

import vn.com.misa.cukcuklite.R;
import vn.com.misa.cukcuklite.base.listeners.IOnItemClickListener;
import vn.com.misa.cukcuklite.utils.AppConstants;
import vn.com.misa.cukcuklite.utils.ImageUtils;

/**
 * Dialog hiển thị danh sách icon cho món ăn
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class DishIconSelectorDialog extends DialogFragment implements IOnItemClickListener<String> {

    private List<String> mStringList;

    /**
     * Phương thức khởi tạo DishIconSelectorDialog
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return - DishIconSelectorDialog
     */
    public static DishIconSelectorDialog newInstance() {
        return new DishIconSelectorDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mStringList = ImageUtils.getAllImage(Objects.requireNonNull(getActivity()));
        View view = inflater.inflate(R.layout.dialog_dish_icon_selector, container, false);
        initViews(view);
        return view;
    }

    /**
     * Phương thức tham chiếu, khởi tạo view
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    private void initViews(View view) {

        Bundle bundle = getArguments();
        int lastPosition = 0;
        if (bundle != null) {
            lastPosition = bundle.getInt(AppConstants.ARG_DISH_COLOR_ID);
        }
//        IconAdapter adapter = new IconAdapter(getContext(), mStringList);
//        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 5);
//        mBinding.rvDishIcon.setLayoutManager(linearLayoutManager);
//        adapter.setItemClickListener(this);
//        mBinding.rvDishIcon.setAdapter(adapter);
//        mBinding.getRoot().findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        int width = getResources().getDimensionPixelSize(R.dimen.color_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.color_dialog_height);
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
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
     * Phương thức xử lý khi icon được lựa chọn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param fileName - tên file icon
     */
    @Override
    public void onItemClick(String fileName) {
        //  mAddDishActivity.setIconForDish(fileName);
        dismiss();
    }
}