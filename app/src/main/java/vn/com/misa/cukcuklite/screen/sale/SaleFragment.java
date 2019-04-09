package vn.com.misa.cukcuklite.screen.sale;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.misa.cukcuklite.R;

/**
 * Màn hình bán hàng
 * Created_by Nguyễn Bá Linh on 09/04/2019s
 */
public class SaleFragment extends Fragment {

    public static SaleFragment newInstance() {
        return new SaleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale, container, false);
    }
}
