package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import in.co.appadda.brainteaser.data.api.model.Defaults;
import in.co.appadda.brainteaser.data.api.model.SocialCallback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    private TextView registerLink, restoreLink;
    private EditText usernameField, passwordField;
    private Button loginButton;
    private ImageView facebookButton;
    private ImageView googleButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initUI();

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this, Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);

        Backendless.UserService.isValidLogin(new DefaultCallback<Boolean>(this) {
            @Override
            public void handleResponse(Boolean isValidLogin) {
                if (isValidLogin && Backendless.UserService.CurrentUser() == null) {
                    String currentUserId = Backendless.UserService.loggedInUser();

                    if (currentUserId.contentEquals(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID).trim())) {
                        Backendless.UserService.findById(currentUserId, new DefaultCallback<BackendlessUser>(LoginActivity.this, "Logging in...") {
                            @Override
                            public void handleResponse(BackendlessUser currentUser) {
                                super.handleResponse(currentUser);
                                Backendless.UserService.setCurrentUser(currentUser);
                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                finish();
                            }
                        });
                    }
                }

                super.handleResponse(isValidLogin);
            }
        });
    }

    private void initUI() {
        registerLink = (TextView) findViewById(R.id.registerLink);
        restoreLink = (TextView) findViewById(R.id.restoreLink);
        usernameField = (EditText) findViewById(R.id.usernameField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        loginButton = (Button) findViewById(R.id.loginButton);
        facebookButton = (ImageView) findViewById(R.id.loginFacebookButton);
        googleButton = (ImageView) findViewById(R.id.loginGoogleButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!usernameField.getText().toString().contentEquals("") && !passwordField.getText().toString().contentEquals("")) {
                    onLoginButtonClicked();
                } else if (usernameField.getText().toString().contentEquals("")) {
                    Toast.makeText(getBaseContext(), "Username can't be left blank", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Password can't be left blank", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterLinkClicked();
            }
        });

        restoreLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestoreLinkClicked();
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginWithFacebookButtonClicked();
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginWithGoogleButtonClicked();
            }
        });

    }

    public void onLoginButtonClicked() {
        String identity = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID).trim();
        String password = passwordField.getText().toString();

        Backendless.UserService.login(identity, password, new DefaultCallback<BackendlessUser>(LoginActivity.this) {
            public void handleResponse(BackendlessUser backendlessUser) {
                super.handleResponse(backendlessUser);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }, true);
    }

    public void onRegisterLinkClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    public void onRestoreLinkClicked() {
        startActivity(new Intent(this, RestorePasswordActivity.class));
        finish();
    }

    public void onLoginWithFacebookButtonClicked() {
        Map<String, String> facebookFieldsMapping = new HashMap<>();
        facebookFieldsMapping.put("name", "name");
        facebookFieldsMapping.put("gender", "gender");
        facebookFieldsMapping.put("email", "email");

        List<String> facebookPermissions = new ArrayList<>();
        facebookPermissions.add("email");

        Backendless.UserService.loginWithFacebook(LoginActivity.this, null, facebookFieldsMapping, facebookPermissions, new SocialCallback<BackendlessUser>(LoginActivity.this) {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });
    }

    public void onLoginWithGoogleButtonClicked() {
        Map<String, String> googleFieldsMapping = new HashMap<>();
        googleFieldsMapping.put("name", "name");
        googleFieldsMapping.put("gender", "gender");
        googleFieldsMapping.put("email", "email");

        List<String> googlePermissions = new ArrayList<>();

        Backendless.UserService.loginWithGooglePlus(LoginActivity.this, null, googleFieldsMapping, googlePermissions, new SocialCallback<BackendlessUser>(LoginActivity.this) {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

}