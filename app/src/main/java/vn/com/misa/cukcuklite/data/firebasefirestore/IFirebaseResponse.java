package vn.com.misa.cukcuklite.data.firebasefirestore;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Map;

/**
 * Inteface callback dữ liệu thao tác trên firebase
 *
 * @created_by lxphuoc on 4/2/2019
 */
public interface IFirebaseResponse {

    // .. Interface callback cho thao tác Thêm dữ liệu
    interface IAddDocument {
        void onSuccess(String firebaseId);

        void onFail();
    }
    // .. Interface callback cho thao tác Cập nhật dữ liệu
    interface IUpdateDocument {
        void onSuccess();

        void onFail();
    }
    // .. Interface callback cho thao tác Xóa dữ liệu
    interface IDeleteDocument {
        void onSuccess();

        void onFail();
    }
    // .. Interface callback cho thao tác Lấy dữ liệu
    interface IGetDocument {
        void onSuccess(Map<String, Object> objectMap);

        void onFail();
    }
    // .. Interface callback cho thao tác Lấy collection
    interface IGetCollection {
        void onSuccess(List<DocumentSnapshot> listDocumentSnapshot);

        void onFail();
    }
}
