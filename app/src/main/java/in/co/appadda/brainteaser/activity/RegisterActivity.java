package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.data.api.model.BrainTeaserUser;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends Activity {
    private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy/MM/dd");

    private EditText passwordField;
    private EditText usernameField;

    private Button registerButton;
    private String android_id;
    private String password;
    private String username;

    private BrainTeaserUser user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        initUI();
    }

    private void initUI() {
        passwordField = (EditText) findViewById(R.id.passwordField);
        usernameField = (EditText) findViewById(R.id.usernameField);
        registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterButtonClicked();
            }
        });
    }

    private void onRegisterButtonClicked() {
        String android_idText = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID).trim();
        String passwordText = passwordField.getText().toString().trim();
        String usernameText = usernameField.getText().toString().trim();

        if (android_idText.isEmpty()) {
            showToast("This device can't be registered.");
            return;
        }

        if (passwordText.isEmpty()) {
            showToast("password can't be left blank");
            return;
        }

        if (usernameText.isEmpty()) {
            showToast("Field 'username' cannot be empty.");
            return;
        }

        if (!android_idText.isEmpty()) {
            android_id = android_idText;
        }

        if (!passwordText.isEmpty()) {
            password = passwordText;
        }

        if (!usernameText.isEmpty()) {
            username = usernameText;
        }

        user = new BrainTeaserUser();

        if (android_id != null) {
            user.setAndroid_id(android_id);
        }

        if (password != null) {
            user.setPassword(password);
        }

        if (username != null) {
            user.setUsername(username);
        }

        Backendless.UserService.register(user, new DefaultCallback<BackendlessUser>(RegisterActivity.this) {
            @Override
            public void handleResponse(BackendlessUser response) {
                super.handleResponse(response);
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}