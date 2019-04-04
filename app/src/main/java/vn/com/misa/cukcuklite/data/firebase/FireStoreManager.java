package vn.com.misa.cukcuklite.data.firebase;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Lớp chứa các phương thức thao tác với cơ sở dữ liệu Firebase cloud fire store
 * Created_by Nguyễn Bá Linh on 04/04/2019
 */
public class FireStoreManager {
    private static FireStoreManager sInstance;
    private FirebaseFirestore mFirebaseFireStore;

    /**
     * Phương thức khởi tạo class FireStoreManager
     * Created_by Nguyễn Bá Linh on 04/04/2019
     */
    private FireStoreManager() {
        mFirebaseFireStore = FirebaseFirestore.getInstance();
    }

    /**
     * Phương thức khởi tạo đảm bảo duy nhất 1 đối tượng FireStoreManager tồn tại và sử dụng cho
     * toàn bộ ứng dụng
     * Created_by Nguyễn Bá Linh on 04/04/2019
     *
     * @return - đối tượng FireStoreManager
     */
    public static FireStoreManager getInstance() {
        if (sInstance == null) {
            synchronized (FireStoreManager.class) {
                if (sInstance == null) {
                    sInstance = new FireStoreManager();
                }
            }
        }
        return sInstance;
    }

}
