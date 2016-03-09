package in.co.appadda.brainteaser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.DataApplication;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import in.co.appadda.brainteaser.data.api.model.Defaults;
import in.co.appadda.brainteaser.data.api.model.aptitude;
import in.co.appadda.brainteaser.data.api.model.puzzles;

/**
 * Created by dewangankisslove on 05-03-2016.
 */
public class Splash extends AppCompatActivity {

    private static BackendlessCollection aptitudeCollection;
    private static BackendlessCollection LogicalCollection;
    private static BackendlessCollection PuzzleCollection;
    private static BackendlessCollection RiddleCollection;
    Button clickToContinue, just;

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
        just = (Button) findViewById(R.id.just_button);

        DataApplication dataApplication = (DataApplication) getApplication();
        dataApplication.setChosenTable("aptitude");

        clickToContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveBasicAptitudeRecord();
            }
        });
        just.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(Splash.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

    }

    private void retrieveBasicAptitudeRecord() {
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setPageSize(10);
        aptitude.findAsync(query, new DefaultCallback<BackendlessCollection<aptitude>>(Splash.this) {
            @Override
            public void handleResponse(BackendlessCollection<aptitude> response) {
                super.handleResponse(response);

                aptitudeCollection = response;

                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addAptitude();

                Intent mainActivity = new Intent(Splash.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    private void retrieveBasicPuzzlesRecord() {
        BackendlessDataQuery query = new BackendlessDataQuery();
        puzzles.findAsync(query, new DefaultCallback<BackendlessCollection<puzzles>>(Splash.this) {
            @Override
            public void handleResponse(BackendlessCollection<puzzles> response) {
                super.handleResponse(response);

                PuzzleCollection = response;
            }
        });
    }
}
