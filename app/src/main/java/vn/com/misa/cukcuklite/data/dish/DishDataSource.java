package vn.com.misa.cukcuklite.data.dish;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import vn.com.misa.cukcuklite.data.bill.BillDataSource;
import vn.com.misa.cukcuklite.data.cukcukenum.EnumResult;
import vn.com.misa.cukcuklite.data.database.IDBUtils;
import vn.com.misa.cukcuklite.data.database.SQLiteDBManager;
import vn.com.misa.cukcuklite.data.models.Dish;


/**
 * Lớp thao tác với các món ăn trong cơ sở dữ liệu
 * Created_by Nguyễn Bá Linh on 27/03/2019
 */
public class DishDataSource implements IDishDataSource, IDBUtils.ITableDishUtils {

    private static final String TAG = "DishDataSource";

    private static DishDataSource sInstance;

    private SQLiteDBManager mSQLiteDBManager;

    private BillDataSource mBillDataSource;

    private List<Dish> mDishes;

    /**
     * Phương thức khởi tạo cho đối tượng DishDataSource, và danh sách món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    private DishDataSource() {
        mSQLiteDBManager = SQLiteDBManager.getInstance();
        mDishes = new ArrayList<>();
    }

    /**
     * Phương thức khởi tạo cho đối tượng DishDataSource truy cập mọi nơi
     * Created_by Nguyễn Bá Linh on 27/03/2019
     */
    public static DishDataSource getInstance() {
        if (sInstance == null) {
            synchronized (SQLiteDBManager.class) {
                if (sInstance == null) {
                    sInstance = new DishDataSource();
                }
            }
        }
        return sInstance;
    }

    /**
     * Phương thức thêm món ăn vào cơ sở dữ liệu
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     * @return - thêm món ăn thành công hay thất bại
     */
    @Override
    public boolean addDish(Dish dish) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_DISH_ID, dish.getDishId());
            contentValues.put(COLUMN_DISH_NAME, dish.getDishName());
            contentValues.put(COLUMN_PRICE, dish.getPrice());
            contentValues.put(COLUMN_UNIT_ID, dish.getUnitId());
            contentValues.put(COLUMN_COLOR_CODE, dish.getColorCode());
            contentValues.put(COLUMN_ICON_PATH, dish.getIconPath());
            contentValues.put(COLUMN_IS_SALE, dish.isSale() ? 1 : 0);
            return mSQLiteDBManager.addNewRecord(DISH_TBL_NAME, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức thêm mới món ăn vào cơ sở dữ liệu có kiểm tra sự tồn tại của món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     * @return - thêm món ăn thành công, thất bại, hay món ăn đã tồn tại trong thực đơn
     */
    @Override
    public EnumResult addDishToDatabase(Dish dish) {
        String dishName;
        if (dish != null && !dish.getDishId().isEmpty() && dish.getDishId() != null) {
            dishName = dish.getDishName();
            //kiểm tra tên món ăn đã tồn tại hay chưa
            if (isDishIfExists(dishName)) {
                return EnumResult.Exists;
            } else {
                if (addDish(dish)) {
                    mDishes.add(dish);
                    return EnumResult.Success;
                }
            }
        }
        return EnumResult.SomethingWentWrong;
    }

    /**
     * Phương thức cập nhật món ăn có kiểm tra tên của món ăn
     *
     * @param dish - món ăn
     * @return - cập nhật món ăn thành công, thất bại
     */
    @Override
    public EnumResult updateDishToDatabase(Dish dish) {
        try {
            if (mDishes != null) {
                int size = mDishes.size();
                boolean dishNameIsExists = false;
                for (int i = 0; i < size; i++) {
                    if (mDishes.get(i).getDishName().toLowerCase().equals(dish.getDishName())
                            && (!mDishes.get(i).getDishId().equals(dish.getDishId()))) {
                        dishNameIsExists = true;
                        break;
                    }
                }
                if (dishNameIsExists) {
                    return EnumResult.Exists;
                } else {
                    if (updateDish(dish)) {
                        for (int i = 0; i < size; i++) {
                            if (mDishes.get(i).getDishId().equals(dish.getDishId())) {
                                mDishes.set(i, dish);
                                break;
                            }
                        }
                        return EnumResult.Success;
                    } else {
                        return EnumResult.SomethingWentWrong;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EnumResult.SomethingWentWrong;
    }

    /**
     * Phương thức xóa món ăn thông qua Id của món ăn
     * Nhưng bản chất chỉ thay đổi trạng thái của món ăn. không xóa món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dishId - id của món ăn
     * @return - xóa món ăn thành công hay thất bại
     */
    @Override
    public boolean deleteDishById(String dishId) {
        try {
            mBillDataSource = BillDataSource.getInstance();
            boolean isUsing = false;
            List<String> dishIdUsing = mBillDataSource.getAllDishIdFromAllBillDetail();
            if (dishIdUsing != null) {
                int size = dishIdUsing.size();
                if (size > 0) {
                    for (String id : dishIdUsing) {
                        if (id.equals(dishId)) {
                            isUsing = true;
                            break;
                        }
                    }
                }
            }
            if (isUsing) {
                return false;
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_STATE, 0);
                if (mSQLiteDBManager.updateRecord(DISH_TBL_NAME, contentValues,
                        COLUMN_DISH_ID + "=?", new String[]{dishId})) {
                    if (mDishes != null) {
                        int size = mDishes.size();
                        for (int i = 0; i < size; i++) {
                            if (mDishes.get(i).getDishId().equals(dishId)) {
                                mDishes.remove(i);
                                break;
                            }
                        }
                    }
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức cập nhật món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dish - món ăn
     * @return - cập nhật món ăn thành công hay thất bại
     */
    @Override
    public boolean updateDish(Dish dish) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_DISH_NAME, dish.getDishName());
            contentValues.put(COLUMN_PRICE, dish.getPrice());
            contentValues.put(COLUMN_UNIT_ID, dish.getUnitId());
            contentValues.put(COLUMN_COLOR_CODE, dish.getColorCode());
            contentValues.put(COLUMN_ICON_PATH, dish.getIconPath());
            contentValues.put(COLUMN_IS_SALE, dish.isSale() ? 1 : 0);
            return mSQLiteDBManager.updateRecord(DISH_TBL_NAME, contentValues,
                    COLUMN_DISH_ID + "=?", new String[]{dish.getDishId()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức lấy toàn bộ tên các món ăn
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @return - danh sách tên các món ăn
     */
    @Override
    public List<String> getAllDishName() {
        List<String> dishNames = new ArrayList<>();
        try {
            Cursor cursor = mSQLiteDBManager.getRecords("select " + COLUMN_DISH_NAME +
                    " from " + DISH_TBL_NAME + " where " + COLUMN_STATE + "=1", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                dishNames.add(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_NAME)).toLowerCase());
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dishNames;
    }

    /**
     * Phương thức lấy toàn bộ danh sách món ăn đang có
     * Created_by Nguyễn Bá Linh on 11/04/2019
     *
     * @return - danh sách món ăn
     */
    @Override
    public List<Dish> getAllDish() {
        if (mDishes != null && mDishes.size() > 0) {
            return mDishes;
        } else {
            List<Dish> dishes = new ArrayList<>();
            try {
                Cursor cursor = mSQLiteDBManager.getRecords("select * from " + DISH_TBL_NAME + " where " + COLUMN_STATE + "=1", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Dish dish = new Dish.Builder().setDishId(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_ID)))
                            .setDishName(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_NAME)))
                            .setPrice(cursor.getInt((cursor.getColumnIndex(COLUMN_PRICE))))
                            .setUnitId(cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_ID)))
                            .setColorCode(cursor.getString(cursor.getColumnIndex(COLUMN_COLOR_CODE)))
                            .setIconPath(cursor.getString(cursor.getColumnIndex(COLUMN_ICON_PATH)))
                            .setSale(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_SALE)) == 1)
                            .build();
                    dishes.add(dish);
                    cursor.moveToNext();
                }
                cursor.close();
                mDishes.clear();
                mDishes.addAll(dishes);
                return dishes;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Phương thức kiểm tra rằng tên món ăn đã tồn tại hay chưa
     * Created_by Nguyễn Bá Linh on 27/03/2019
     *
     * @param dishName - tên món ăn được thêm mới
     * @return - tên món ăn đã có hay chưa có
     */
    @Override
    public boolean isDishIfExists(String dishName) {
        try {
            boolean isExists = false;
            if (mDishes != null) {
                for (Dish d : mDishes) {
                    if (d.getDishName().equalsIgnoreCase(dishName)) {
                        isExists = true;
                        break;
                    }
                }
                return isExists;
            }
            Cursor cursor = mSQLiteDBManager.getRecords(DISH_TBL_NAME, new String[]{COLUMN_DISH_NAME},
                    "lower(" + COLUMN_DISH_NAME + ")" + " = ? ", new String[]{dishName.toLowerCase()},
                    null, null, null);
            int dishNameRecord = cursor.getCount();
            cursor.close();
            return dishNameRecord > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Phương thức xóa toàn bộ danh sách các món ăn có sẵn trong cơ sở dữ liệu
     * Created_by Nguyễn Bá Linh on 09/04/2019
     */
    @Override
    public boolean deleteAllDish() {
        try {
            if (mSQLiteDBManager.deleteRecord(DISH_TBL_NAME, null, null)) {
                if(mDishes!=null){
                mDishes.clear();
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Phương thức lấy món ăn theo id của món ăn
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @param dishId - id của món ăn
     * @return - món ăn
     */
    @Override
    public Dish getDishById(String dishId) {
        try {
            Dish dish = null;
            if (mDishes != null && mDishes.size() > 0) {
                for (Dish d : mDishes) {
                    if (d.getDishId().equals(dishId)) {
                        dish = d;
                        break;
                    }
                }
                return dish;
            }
            Cursor cursor = mSQLiteDBManager.getRecords("select * from " + DISH_TBL_NAME + " where " + COLUMN_DISH_ID + "=" + "'" + dishId + "'", null);
            cursor.moveToFirst();
            dish = new Dish.Builder().setDishId(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_ID)))
                    .setDishName(cursor.getString(cursor.getColumnIndex(COLUMN_DISH_NAME)))
                    .setPrice(cursor.getInt((cursor.getColumnIndex(COLUMN_PRICE))))
                    .setUnitId(cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_ID)))
                    .setColorCode(cursor.getString(cursor.getColumnIndex(COLUMN_COLOR_CODE)))
                    .setIconPath(cursor.getString(cursor.getColumnIndex(COLUMN_ICON_PATH)))
                    .setSale(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_SALE)) == 1)
                    .build();
            cursor.close();
            return dish;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy tất cả danh sách id của món ăn
     * Created_by Nguyễn Bá Linh on 12/04/2019
     *
     * @return - danh sách id của món ăn
     */
    @Override
    public List<String> getAllDishId() {
        List<String> dishIds = new ArrayList<>();
        try {
            if (mDishes != null) {
                for (Dish dish : mDishes) {
                    if (dish.isSale()) {
                        dishIds.add(dish.getDishId());
                    }
                }
            } else {
                Cursor cursor = mSQLiteDBManager.getRecords("select * from " + DISH_TBL_NAME + " where " + COLUMN_STATE + "=1", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String dishId = cursor.getString(cursor.getColumnIndex(COLUMN_DISH_ID));
                    dishIds.add(dishId);
                    cursor.moveToNext();
                }
                cursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dishIds;
    }
}
