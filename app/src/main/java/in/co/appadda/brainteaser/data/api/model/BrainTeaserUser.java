package in.co.appadda.brainteaser.data.api.model;

import com.backendless.BackendlessUser;

public class BrainTeaserUser extends BackendlessUser {

    public String getPassword() {
        return super.getPassword();
    }

    public String getAndroid_id() {
        return (String) super.getProperty("android_id");
    }

    public void setAndroid_id(String android_id) {
        super.setProperty("android_id", android_id);
    }

    public String getUsername() {
        return (String) super.getProperty("username");
    }

    public void setUsername(String username) {
        super.setProperty("username", username);
    }
}