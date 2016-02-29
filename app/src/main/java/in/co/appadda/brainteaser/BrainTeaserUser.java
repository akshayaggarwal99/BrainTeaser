package in.co.appadda.brainteaser;

import com.backendless.BackendlessUser;

public class BrainTeaserUser extends BackendlessUser {
    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public String getFirstname() {
        return (String) super.getProperty("firstname");
    }

    public void setFirstname(String firstname) {
        super.setProperty("firstname", firstname);
    }

    public String getLastname() {
        return (String) super.getProperty("lastname");
    }

    public void setLastname(String lastname) {
        super.setProperty("lastname", lastname);
    }
}