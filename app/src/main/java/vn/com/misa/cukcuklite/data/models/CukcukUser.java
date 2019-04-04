package vn.com.misa.cukcuklite.data.models;

public class CukcukUser {
    private String mUserId;
    private String mName;
    private String mPassword;
    private String mEmail;
    private String mAvatarUrl;

    public CukcukUser(Builder builder) {
        mUserId = builder.mUserId;
        mName = builder.mName;
        mPassword = builder.mPassword;
        mEmail = builder.mEmail;
        mAvatarUrl = builder.mAvatarUrl;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public static class Builder {
        private String mUserId;
        private String mName;
        private String mPassword;
        private String mEmail;
        private String mAvatarUrl;

        public Builder setUserId(String userId) {
            mUserId = userId;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setPassword(String password) {
            mPassword = password;
            return this;
        }

        public Builder setEmail(String email) {
            mEmail = email;
            return this;
        }

        public Builder setAvatarUrl(String avatarUrl) {
            mAvatarUrl = avatarUrl;
            return this;
        }

        public CukcukUser build() {
            return new CukcukUser(this);
        }
    }
}
