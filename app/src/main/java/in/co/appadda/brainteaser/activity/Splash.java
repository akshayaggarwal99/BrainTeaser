package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import in.co.appadda.brainteaser.MaterialRippleLayout;
import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.ConnectionDetector;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import in.co.appadda.brainteaser.data.api.model.Defaults;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.aptitude;
import in.co.appadda.brainteaser.data.api.model.logical;
import in.co.appadda.brainteaser.data.api.model.puzzles;
import in.co.appadda.brainteaser.data.api.model.riddles;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by dewangankisslove on 05-03-2016.
 */
public class Splash extends AppCompatActivity {

    private static BackendlessCollection aptitudeCollection;
    private static BackendlessCollection LogicalCollection;
    private static BackendlessCollection PuzzleCollection;
    private static BackendlessCollection RiddleCollection;
    Button clickToContinue, skipUpdate;
    int id_aptitude = 0;
    int id_logical = 0;
    int id_puzzle = 0;
    int id_riddle = 0;
    String skip_update = "FALSE";

    public static BackendlessCollection getAptitudeCollection() {
        return aptitudeCollection;
    }

    public static BackendlessCollection getLogicalCollection() {
        return LogicalCollection;
    }

    public static BackendlessCollection getPuzzleCollection() {
        return PuzzleCollection;
    }

    public static BackendlessCollection getRiddleCollection() {
        return RiddleCollection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DatabaseHandler db = new DatabaseHandler(this);
        db.close();

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(getBaseContext(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);

        Backendless.Messaging.registerDevice(Defaults.gcmSenderID);

        skip_update = PrefUtils.getFromPrefs(Splash.this, "skip_update", "FALSE");
        if (skip_update.contentEquals("TRUE") || skip_update.contentEquals("TRUTH")) {
            Intent mainActivity = new Intent(Splash.this, MainActivity.class);
            startActivity(mainActivity);
        } else {

            skipUpdate = (Button) findViewById(R.id.splash_skip);
            clickToContinue = (Button) findViewById(R.id.splash_button);
            MaterialRippleLayout.on(skipUpdate)
                    .rippleOverlay(true)
                    .rippleColor(Color.parseColor("#35ADCF"))
                    .rippleAlpha(0.2f)
                    .rippleHover(true)
                    .create();
            MaterialRippleLayout.on(clickToContinue)
                    .rippleOverlay(true)
                    .rippleColor(Color.parseColor("#35ADCF"))
                    .rippleAlpha(0.2f)
                    .rippleHover(true)
                    .create();


            clickToContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

                    Boolean isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {
                        retrieveBasicAptitudeRecord();
                    } else {
                        Toast.makeText(getBaseContext(), "Check Internet Connection!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }

    private void retrieveBasicAptitudeRecord() {
        id_aptitude = Integer.parseInt(PrefUtils.getFromPrefs(Splash.this, "_id_aptitude", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setPageSize(40);
        query.setWhereClause("_id > " + id_aptitude);
        aptitude.findAsync(query, new DefaultCallback<BackendlessCollection<aptitude>>(Splash.this) {
            @Override
            public void handleResponse(BackendlessCollection<aptitude> response) {
                id_aptitude = id_aptitude + response.getData().size();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_aptitude);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(Splash.this, "_id_aptitude", ID);

                aptitudeCollection = response;

                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addAptitude();

                retrieveBasicPuzzlesRecord();


            }
        });
    }

    private void retrieveBasicPuzzlesRecord() {
        id_puzzle = Integer.parseInt(PrefUtils.getFromPrefs(Splash.this, "_id_puzzle", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery queryPuzzle = new BackendlessDataQuery();
        queryPuzzle.setQueryOptions(queryOptions);
        queryPuzzle.setPageSize(20);
        queryPuzzle.setWhereClause("_id > " + id_puzzle);
        puzzles.findAsync(queryPuzzle, new DefaultCallback<BackendlessCollection<puzzles>>(Splash.this) {
            @Override
            public void handleResponse(BackendlessCollection<puzzles> response) {
                super.handleResponse(response);
                id_puzzle = id_puzzle + response.getData().size();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_puzzle);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(Splash.this, "_id_puzzle", ID);
                PuzzleCollection = response;
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addPuzzles();

                retrieveBasicLogicalRecord();
            }
        });
    }

    private void retrieveBasicLogicalRecord() {
        id_logical = Integer.parseInt(PrefUtils.getFromPrefs(Splash.this, "_id_logical", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setPageSize(40);
        query.setWhereClause("_id > " + id_logical);
        logical.findAsync(query, new DefaultCallback<BackendlessCollection<logical>>(Splash.this) {
            @Override
            public void handleResponse(BackendlessCollection<logical> response) {
                super.handleResponse(response);
                id_logical = id_logical + response.getData().size();
                Log.d("logical", "" + id_logical);
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_logical);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(Splash.this, "_id_logical", ID);
                LogicalCollection = response;
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addLogical();

//                PrefUtils.saveToPrefs(Splash.this, "skip_update", "TRUE");
                retrieveBasicRiddleRecord();
            }
        });
    }

    private void retrieveBasicRiddleRecord() {
        id_riddle = Integer.parseInt(PrefUtils.getFromPrefs(Splash.this, "_id_riddle", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setPageSize(20);
        query.setWhereClause("_id > " + id_riddle);
        riddles.findAsync(query, new DefaultCallback<BackendlessCollection<riddles>>(Splash.this) {
            @Override
            public void handleResponse(BackendlessCollection<riddles> response) {
                super.handleResponse(response);
                id_riddle = id_riddle + response.getData().size();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_riddle);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(Splash.this, "_id_riddle", ID);
                RiddleCollection = response;
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addRiddle();

                PrefUtils.saveToPrefs(Splash.this, "skip_update", "TRUE");

                Intent mainActivity = new Intent(Splash.this, MainActivity.class);
                startActivity(mainActivity);


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
