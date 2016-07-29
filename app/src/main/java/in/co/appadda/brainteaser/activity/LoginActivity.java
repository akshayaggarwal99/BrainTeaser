package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.exceptions.ExceptionMessage;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.data.api.model.ConnectionDetector;
import in.co.appadda.brainteaser.data.api.model.Defaults;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    private ImageView facebookButton;
    Boolean isInternetPresent = false;
    CallbackManager callbackManager;
    TextView privacyPolicy;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this, Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        AccessToken accessToken = loginResult.getAccessToken();
                        Bundle params = new Bundle();
                        params.putString("fields", "id,email,picture.type(large)");
                        Log.d("wtf", String.valueOf(accessToken));
                        new GraphRequest(accessToken, "me", params, HttpMethod.GET,
                                new GraphRequest.Callback() {
                                    @Override
                                    public void onCompleted(GraphResponse response) {
                                        Log.d("wtf", "0");
                                        if (response != null) {
                                            try {
                                                JSONObject data = response.getJSONObject();
                                                if (data.has("picture")) {
                                                    Log.d("wtf", "1");
                                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                                    // set profile image to imageview using Picasso or Native methods
                                                    PrefUtils.saveToPrefs(getApplicationContext(), "userPic", profilePicUrl);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }).executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();

        initUI();
    }

    private void initUI() {
        facebookButton = (ImageView) findViewById(R.id.loginFacebookButton);
        privacyPolicy = (TextView) findViewById(R.id.tv_privacy_policy);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkinternetservice())
                    onLoginWithFacebookButtonClicked();
                else
                    Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
            }
        });

        privacyPolicy.setText(Html.fromHtml("By signing up, I agree to <font color='#424242'>OpenSoftLab's<br> Privacy Policy."));
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.opensoftlab.co.in/privacy";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
    public void onLoginWithFacebookButtonClicked() {
        Map<String, String> facebookFieldsMapping = new HashMap<>();
        facebookFieldsMapping.put("name", "username");
        facebookFieldsMapping.put("gender", "gender");
        facebookFieldsMapping.put("email", "email");

        List<String> facebookPermissions = new ArrayList<>();
        facebookPermissions.add("email");
        facebookPermissions.add("public_profile");


        Backendless.UserService.loginWithFacebookSdk(LoginActivity.this, facebookFieldsMapping, facebookPermissions, callbackManager, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {
                PrefUtils.saveToPrefs(getApplicationContext(), "username", String.valueOf(backendlessUser.getProperty("username")));
                Intent compete = new Intent(LoginActivity.this, Compete.class);
                compete.putExtra("compete", "false");
                startActivity(compete);
                finish();
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {

            }
        }, true);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    public boolean checkinternetservice() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}