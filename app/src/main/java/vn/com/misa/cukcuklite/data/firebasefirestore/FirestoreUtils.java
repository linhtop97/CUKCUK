package vn.com.misa.cukcuklite.data.firebasefirestore;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import vn.com.misa.cukcuklite.utils.AppConstants;

/**
 * Class base thao tác dữ liệu với firestore trên firebase
 *
 * @created_by lxphuoc on 4/2/2019
 */
public class FirestoreUtils {
    private static FirestoreUtils ourInstance = null;

    private FirebaseFirestore mFirebaseFirestore;

    public static FirestoreUtils getInstance() {
        if(ourInstance == null){
            ourInstance = new FirestoreUtils();
        }
        return ourInstance;
    }

    private FirestoreUtils() {
        initialize();
    }

    /**
     * Phương thức khởi tạo firestore
     *
     * @created_by lxphuoc on 4/2/2019
     */
    private void initialize() {
        mFirebaseFirestore = FirebaseFirestore.getInstance();
    }

    /**
     * Phương thức lấy id mới từ firebase
     *
     * @created_by lxphuoc on 4/2/2019
     */
    public String getNewId() {
        return mFirebaseFirestore.collection("").document().getId();
    }

    /**
     * Phương thức thêm tài liệu vào firebase
     *
     * @param collectionPath - Đường dẫn đến bản ghi trên firestore
     * @param objectMap      - Dữ liệu muốn lưu
     * @created_by lxphuoc on 4/2/2019
     */
    public void addDocument(String collectionPath, Map<String, Object> objectMap, @Nullable String documentId, final IFirebaseResponse.IAddDocument listener) {
        try {
            if (documentId != null) {
                objectMap.put(AppConstants.FIREBASE_ID, documentId);
            } else {
                documentId = getNewId();
                objectMap.put(AppConstants.FIREBASE_ID, documentId);
            }

            final String newDocumentId = documentId;
            mFirebaseFirestore.collection(collectionPath).document(newDocumentId).set(objectMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            listener.onSuccess(newDocumentId);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFail();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa bản ghi trên firebase
     *
     * @param collectionPath - Đường dẫn đến đối tượng muốn xóa
     * @created_by lxphuoc on 4/2/2019
     */
    public void removeDocument(String collectionPath, final IFirebaseResponse.IDeleteDocument listener) {
        try {
            mFirebaseFirestore.document(collectionPath).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            listener.onSuccess();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFail();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update dữ liệu đến tài liệu
     *
     * @param objectMap      - Thông tin cần cập nhật
     * @param collectionPath - Đường dẫn đến đối tượng cần cập nhật
     * @created_by lxphuoc on 4/2/2019
     */
    public void updateDocument(String collectionPath, Map<String, Object> objectMap, final IFirebaseResponse.IUpdateDocument listener) {
        try {
            mFirebaseFirestore.document(collectionPath).update(objectMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            listener.onSuccess();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFail();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức lấy dữ liệu bản ghi từ firebase
     *
     * @param collectionPath - Đường dẫn đến dữ liệu muốn lấy
     * @created_by lxphuoc on 4/2/2019
     */
    public void getDocument(String collectionPath, final IFirebaseResponse.IGetDocument listener) {
        try {
            mFirebaseFirestore.document(collectionPath).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            listener.onSuccess(documentSnapshot.getData());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFail();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức lấy dữ liệu collection từ firebase
     *
     * @param collectionPath - Đường dẫn đến dữ liệu muốn lấy
     * @created_by lxphuoc on 4/2/2019
     */
    public void getCollection(String collectionPath, final IFirebaseResponse.IGetCollection listener) {
        try {
            mFirebaseFirestore.collection(collectionPath).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            listener.onSuccess(queryDocumentSnapshots.getDocuments());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.onFail();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
