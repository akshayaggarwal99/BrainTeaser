package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.Defaults;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.userstatus;
import in.co.appadda.brainteaser.fragments.CompeteFragment;
import in.co.appadda.brainteaser.fragments.UserGuide;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by dewangankisslove on 08-06-2016.
 */
public class Compete extends AppCompatActivity {

    ImageView home, leaderboard, topics, assesment;
    String open, topicName;
    DatabaseHandler db;
    private static final String SHOWCASE_ID = "sequence example";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compete);
        db = new DatabaseHandler(this);

        Backendless.initApp(getBaseContext(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);

        open = PrefUtils.getFromPrefs(getApplicationContext(), "openCompete", "false");
        topicName = PrefUtils.getFromPrefs(getApplicationContext(), "topicName", "Calender");

        home = (ImageView) findViewById(R.id.home);
        leaderboard = (ImageView) findViewById(R.id.leaderboard);
        topics = (ImageView) findViewById(R.id.topics);
        assesment = (ImageView) findViewById(R.id.assesment);

        if (PrefUtils.getFromPrefs(getApplicationContext(), "firstTime", "null").contentEquals("null")) {
            String currentUserId = Backendless.UserService.loggedInUser();
            Backendless.UserService.findById(currentUserId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    String username = String.valueOf(backendlessUser.getProperty("username"));
                    PrefUtils.saveToPrefs(getApplicationContext(), "username", username);
                    PrefUtils.saveToPrefs(getApplicationContext(), "userIdentity", backendlessUser.getEmail());
                    //Log.d("value", PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + ":" + PrefUtils.getFromPrefs(getApplicationContext(), "username", "null"));
                    QueryOptions queryOpt = new QueryOptions();
                    BackendlessDataQuery prequeryStat = new BackendlessDataQuery();
                    prequeryStat.setQueryOptions(queryOpt);
                    prequeryStat.setPageSize(1);
                    String whereClau = "userId = '" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "'";
                    prequeryStat.setWhereClause(whereClau);
                    userstatus.findAsync(prequeryStat, new AsyncCallback<BackendlessCollection<userstatus>>() {
                        @Override
                        public void handleResponse(BackendlessCollection<userstatus> userstatusBackendlessCollection) {
                            if (userstatusBackendlessCollection.getData().size() != 0) {
                                String stat = userstatusBackendlessCollection.getCurrentPage().get(0).getStatus();
                                for (int l = 0; l < stat.split(";").length; l++) {
                                    PrefUtils.saveToPrefs(getApplicationContext(), stat.split(";")[l].split(":")[0], stat.split(";")[l].split(":")[1]);
                                }
                            }
                            PrefUtils.saveToPrefs(getApplicationContext(), "firstTime", "done");
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                            Log.d("fault", backendlessFault.getMessage());
                        }
                    });
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {

                }
            });
        }

        assesment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (open.contentEquals("true")) {
                    final List<String> date = new ArrayList<String>();
                    for (int j = 0; j < db.getAttemptedQues().size(); j++) {
                        if (date.contains(db.getAttemptedQues().get(j)[6])) {
                        } else {
                            date.add(db.getAttemptedQues().get(j)[6]);
                        }
                    }

                    final AlertDialog.Builder alertDiallogBuilder = new AlertDialog.Builder(Compete.this);
                    alertDiallogBuilder.setTitle(Html.fromHtml("<font color='#FFFFFF'><b>Alert"));
                    alertDiallogBuilder.setMessage(Html.fromHtml("<font color=\"#FFFFFF\">Are you sure you want to leave current session.</font>"));
                    alertDiallogBuilder.setCancelable(true);
                    alertDiallogBuilder.setPositiveButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Ok</b></font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (db.checkAllQueStatus()) {
                                db.removeAllQue();
                            }
                            if (date.size() > 3) {
                                startActivity(new Intent(Compete.this, Assesment.class));
                                finish();
                            } else {
                                final AlertDialog.Builder alertDiallogBuilder = new AlertDialog.Builder(Compete.this);
                                alertDiallogBuilder.setTitle(Html.fromHtml("<font color='#FFFFFF'><b>Info"));
                                alertDiallogBuilder.setMessage(Html.fromHtml("<font color=\"#FFFFFF\">You can acesss this assesment feature after solving competitive question for at least 4 days.</font>"));
                                alertDiallogBuilder.setCancelable(true);
                                alertDiallogBuilder.setPositiveButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Okay</b></font>"), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                final AlertDialog alertDialog = alertDiallogBuilder.create();
                                alertDialog.show();
                            }
                        }
                    });
                    alertDiallogBuilder.setNegativeButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Cancel</b></font>"), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    final AlertDialog alertDialog = alertDiallogBuilder.create();
                    alertDialog.show();
                } else {
                    final List<String> date = new ArrayList<String>();
                    for (int j = 0; j < db.getAttemptedQues().size(); j++) {
                        if (date.contains(db.getAttemptedQues().get(j)[6])) {
                        } else {
                            date.add(db.getAttemptedQues().get(j)[6]);
                        }
                    }
                    if (db.checkAllQueStatus()) {
                        db.removeAllQue();
                    }
                    if (date.size() > 3) {
                        startActivity(new Intent(Compete.this, Assesment.class));
                        finish();
                    } else {
                        final AlertDialog.Builder alertDiallogBuilder = new AlertDialog.Builder(Compete.this);
                        alertDiallogBuilder.setTitle(Html.fromHtml("<font color='#FFFFFF'><b>Info"));
                        alertDiallogBuilder.setMessage(Html.fromHtml("<font color=\"#FFFFFF\">You can acesss this assesment feature after solving competitive question for at least 4 days.</font>"));
                        alertDiallogBuilder.setCancelable(true);
                        alertDiallogBuilder.setPositiveButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Okay</b></font>"), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        final AlertDialog alertDialog = alertDiallogBuilder.create();
                        alertDialog.show();
                    }
                }
            }
        });

        if (open.contentEquals("true")) {
            Fragment competefragment = CompeteFragment.newInstance(topicName);
            android.support.v4.app.FragmentTransaction competefragmentTransaction = getSupportFragmentManager().beginTransaction();
            competefragmentTransaction.replace(R.id.activity_display_questions, competefragment);
            competefragmentTransaction.commit();
        } else {
            Fragment userGuide = new UserGuide();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.activity_display_questions, userGuide);
            fragmentTransaction.commit();
        }


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (open.contentEquals("true")) {
                    final AlertDialog.Builder alertDiallogBuilder = new AlertDialog.Builder(Compete.this);
                    alertDiallogBuilder.setTitle(Html.fromHtml("<font color='#FFFFFF'><b>Alert"));
                    alertDiallogBuilder.setMessage(Html.fromHtml("<font color=\"#FFFFFF\">Are you sure you want to leave current session for now.</font>"));
                    alertDiallogBuilder.setCancelable(true);
                    alertDiallogBuilder.setPositiveButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Ok</b></font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (checkinternetservice()) {
                                if (db.checkAllQueStatus()) {
                                    db.removeAllQue();
                                }
                                startActivity(new Intent(Compete.this, Leaderboard.class));
                                finish();
                            } else
                                Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDiallogBuilder.setNegativeButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Cancel</b></font>"), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    final AlertDialog alertDialog = alertDiallogBuilder.create();
                    alertDialog.show();
                } else {
                    if (checkinternetservice()) {
                        if (db.checkAllQueStatus()) {
                            db.removeAllQue();
                        }
                        startActivity(new Intent(Compete.this, Leaderboard.class));
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (open.contentEquals("true")) {
                    final AlertDialog.Builder alertDiallogBuilder = new AlertDialog.Builder(Compete.this);
                    alertDiallogBuilder.setTitle(Html.fromHtml("<font color='#FFFFFF'><b>Alert"));
                    alertDiallogBuilder.setMessage(Html.fromHtml("<font color=\"#FFFFFF\">Are you sure you want to leave current session.</font>"));
                    alertDiallogBuilder.setCancelable(true);
                    alertDiallogBuilder.setPositiveButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Ok</b></font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (checkinternetservice()) {
                                if (db.checkAllQueStatus()) {
                                    db.removeAllQue();
                                }
                                startActivity(new Intent(Compete.this, Topics.class));
                                finish();
                            } else
                                Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDiallogBuilder.setNegativeButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Cancel</b></font>"), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    final AlertDialog alertDialog = alertDiallogBuilder.create();
                    alertDialog.show();
                } else {
                    if (checkinternetservice()) {
                        if (db.checkAllQueStatus()) {
                            db.removeAllQue();
                        }
                        startActivity(new Intent(Compete.this, Topics.class));
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        presentShowcaseSequence();
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
        if (open.contentEquals("true")) {
            final AlertDialog.Builder alertDiallogBuilder = new AlertDialog.Builder(Compete.this);
            alertDiallogBuilder.setTitle(Html.fromHtml("<font color='#FFFFFF'><b>Alert"));
            alertDiallogBuilder.setMessage(Html.fromHtml("<font color=\"#FFFFFF\">Are you sure you want to leave current session.</font>"));
            alertDiallogBuilder.setCancelable(true);
            alertDiallogBuilder.setPositiveButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Ok</b></font>"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (db.checkAllQueStatus()) {
                        db.removeAllQue();
                    }
                    startActivity(new Intent(Compete.this, MainActivity.class));
                    finish();
                }
            });
            alertDiallogBuilder.setNegativeButton(Html.fromHtml("<font color=\"#FFFFFF\"><b>Cancel</b></font>"), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            final AlertDialog alertDialog = alertDiallogBuilder.create();
            alertDialog.show();
        } else {
            if (db.checkAllQueStatus()) {
                db.removeAllQue();
            }
            startActivity(new Intent(Compete.this, MainActivity.class));
            finish();
        }
    }

    private void presentShowcaseSequence() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setConfig(config);

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(topics)
                        .setDismissText("Next")
                        .setDismissTextColor(Color.parseColor("#FFFFFF"))
                        .setContentTextColor(Color.parseColor("#FFFFFF"))
                        .setTitleTextColor(Color.parseColor("#FFFFFF"))
                        .setMaskColour(Color.parseColor("#CC3A506B"))
                        .setContentText("Tap 'Topics' to select a particular topic and start solving questions.")
                        .setTitleText("Topic")
                        .setShapePadding(16)
                        .build()
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(leaderboard)
                        .setDismissText("Next")
                        .setDismissTextColor(Color.parseColor("#FFFFFF"))
                        .setContentTextColor(Color.parseColor("#FFFFFF"))
                        .setTitleTextColor(Color.parseColor("#FFFFFF"))
                        .setMaskColour(Color.parseColor("#CC3A506B"))
                        .setContentText("Tap 'Leaderboard' to see your ranking among all Brain Teaser users in the world.")
                        .setTitleText("Leaderboard")
                        .setShapePadding(16)
                        .build()
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(assesment)
                        .setDismissText("Done")
                        .setDismissTextColor(Color.parseColor("#FFFFFF"))
                        .setContentTextColor(Color.parseColor("#FFFFFF"))
                        .setTitleTextColor(Color.parseColor("#FFFFFF"))
                        .setMaskColour(Color.parseColor("#CC3A506B"))
                        .setContentText("Tap assesment to see your performance report. You can access this feature after solving competitive question for at least 4 days.")
                        .setTitleText("Assesment")
                        .setShapePadding(16)
                        .build()
        );

        sequence.start();

    }
}
