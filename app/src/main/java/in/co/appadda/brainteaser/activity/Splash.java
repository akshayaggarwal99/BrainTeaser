package in.co.appadda.brainteaser.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import in.co.appadda.brainteaser.data.api.model.Defaults;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.aptitude;
import in.co.appadda.brainteaser.data.api.model.logical;
import in.co.appadda.brainteaser.data.api.model.puzzles;
import in.co.appadda.brainteaser.data.api.model.riddles;

/**
 * Created by dewangankisslove on 05-03-2016.
 */
public class Splash extends AppCompatActivity {

    private static BackendlessCollection aptitudeCollection;
    private static BackendlessCollection LogicalCollection;
    private static BackendlessCollection PuzzleCollection;
    private static BackendlessCollection RiddleCollection;
    Button clickToContinue;
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

        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(getBaseContext(), Defaults.APPLICATION_ID, Defaults.SECRET_KEY, Defaults.VERSION);

        clickToContinue = (Button) findViewById(R.id.splash_button);
        skip_update = PrefUtils.getFromPrefs(Splash.this, "skip_update", "FALSE");
        if (skip_update.contentEquals("TRUE")) {
            clickToContinue.setClickable(false);
            clickToContinue.setAlpha(0);
            Thread thread = new Thread() {
                public void run() {
                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent mainActivity = new Intent(Splash.this, MainActivity.class);
                        startActivity(mainActivity);
                    }
                }
            };
            thread.start();
        } else {
            clickToContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    retrieveBasicAptitudeRecord();


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
                super.handleResponse(response);
                id_aptitude = id_aptitude + 40;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_aptitude);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(Splash.this, "_id_aptitude", ID);

                aptitudeCollection = response;

                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addAptitude();

                retrieveBasicLogicalRecord();
            }
        });
    }

    private void retrieveBasicPuzzlesRecord() {
        id_puzzle = Integer.parseInt(PrefUtils.getFromPrefs(Splash.this, "_id_puzzle", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery queryPuzzle = new BackendlessDataQuery();
        queryPuzzle.setQueryOptions(queryOptions);
        queryPuzzle.setPageSize(10);
        queryPuzzle.setWhereClause("_id > " + id_puzzle);
        puzzles.findAsync(queryPuzzle, new DefaultCallback<BackendlessCollection<puzzles>>(Splash.this) {
            @Override
            public void handleResponse(BackendlessCollection<puzzles> response) {
                super.handleResponse(response);
                id_puzzle = id_puzzle + 10;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_puzzle);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(Splash.this, "_id_puzzle", ID);
                PuzzleCollection = response;
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addPuzzles();

                retrieveBasicRiddleRecord();

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
                id_logical = id_logical + 40;
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_logical);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(Splash.this, "_id_puzzle", ID);
                LogicalCollection = response;
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addLogical();

                retrieveBasicPuzzlesRecord();

            }
        });
    }

    private void retrieveBasicRiddleRecord() {
        id_riddle = Integer.parseInt(PrefUtils.getFromPrefs(Splash.this, "_id_riddle", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setPageSize(10);
        query.setWhereClause("_id > " + id_riddle);
        riddles.findAsync(query, new DefaultCallback<BackendlessCollection<riddles>>(Splash.this) {
            @Override
            public void handleResponse(BackendlessCollection<riddles> response) {
                super.handleResponse(response);
                id_riddle = id_riddle + 10;
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
}
