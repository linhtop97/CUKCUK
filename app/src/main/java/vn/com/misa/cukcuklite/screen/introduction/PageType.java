package vn.com.misa.cukcuklite.screen.introduction;

import android.support.annotation.IntDef;

import static vn.com.misa.cukcuklite.screen.introduction.PageType.FIFTH;
import static vn.com.misa.cukcuklite.screen.introduction.PageType.FIRST;
import static vn.com.misa.cukcuklite.screen.introduction.PageType.FOURTH;
import static vn.com.misa.cukcuklite.screen.introduction.PageType.SECOND;
import static vn.com.misa.cukcuklite.screen.introduction.PageType.THIRD;

@IntDef({FIRST, SECOND, THIRD, FOURTH, FIFTH})
public @interface PageType {
    int FIRST = 0;
    int SECOND = 1;
    int THIRD = 2;
    int FOURTH = 3;
    int FIFTH = 4;
}
