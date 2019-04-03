package vn.com.misa.cukcuklite.screen.introduction.introfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.misa.cukcuklite.R;

/**
 * Màn hình giới thiệu thứ tư
 * Created_by Nguyễn Bá Linh on 03/04/2019
 */
public class IntroFourthFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro_fourth, container, false);
    }
}
