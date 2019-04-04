package vn.com.misa.cukcuklite.screen.authentication.login;

import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import vn.com.misa.cukcuklite.R;

public class LoginPresenter implements ILoginContract.IPresenter {

    private ILoginContract.IView mView;

    @Override
    public void loginWithFacebook(AccessToken accessToken) {
        mView.showLoading();
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mView.loginSuccess();

                        } else {
                            mView.receiveMessage(R.string.something_went_wrong);
                        }
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void login(String emailPhone, String password) {

    }

    @Override
    public void setView(ILoginContract.IView view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
