package in.co.appadda.brainteaser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import in.co.appadda.brainteaser.data.api.model.BrainTeaserUser;
import in.co.appadda.brainteaser.utils.DefaultCallback;
import in.co.appadda.brainteaser.R;

public class RegisterActivity extends AppCompatActivity {
    private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy/MM/dd");

    private EditText emailField;
    private EditText firstnameField;
    private EditText lastnameField;
    private EditText passwordField;

    private Button registerButton;

    private String email;
    private String firstname;
    private String lastname;
    private String password;

    private BrainTeaserUser user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        initUI();
    }

    private void initUI() {
        emailField = (EditText) findViewById(R.id.emailField);
        firstnameField = (EditText) findViewById(R.id.firstnameField);
        lastnameField = (EditText) findViewById(R.id.lastnameField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        registerButton = (Button) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterButtonClicked();
            }
        });
    }

    private void onRegisterButtonClicked() {
        String emailText = emailField.getText().toString().trim();
        String firstnameText = firstnameField.getText().toString().trim();
        String lastnameText = lastnameField.getText().toString().trim();
        String passwordText = passwordField.getText().toString().trim();

        if (emailText.isEmpty()) {
            showToast("Field 'email' cannot be empty.");
            return;
        }

        if (passwordText.isEmpty()) {
            showToast("Field 'password' cannot be empty.");
            return;
        }

        if (!emailText.isEmpty()) {
            email = emailText;
        }

        if (!firstnameText.isEmpty()) {
            firstname = firstnameText;
        }

        if (!lastnameText.isEmpty()) {
            lastname = lastnameText;
        }

        if (!passwordText.isEmpty()) {
            password = passwordText;
        }

        user = new BrainTeaserUser();

        if (email != null) {
            user.setEmail(email);
        }

        if (firstname != null) {
            user.setFirstname(firstname);
        }

        if (lastname != null) {
            user.setLastname(lastname);
        }

        if (password != null) {
            user.setPassword(password);
        }

        Backendless.UserService.register(user, new DefaultCallback<BackendlessUser>(RegisterActivity.this) {
            @Override
            public void handleResponse(BackendlessUser response) {
                super.handleResponse(response);
                startActivity(new Intent(RegisterActivity.this, RegistrationSuccessActivity.class));
                finish();
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}