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
import in.co.appadda.brainteaser.data.api.model.DataApplication;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import in.co.appadda.brainteaser.data.api.model.Defaults;
import in.co.appadda.brainteaser.data.api.model.aptitude;

/**
 * Created by dewangankisslove on 05-03-2016.
 */
public class Splash extends AppCompatActivity {

    private static BackendlessCollection resultCollection;
    Button clickToContinue, just;

    public static BackendlessCollection getResultCollection() {
        return resultCollection;
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

                resultCollection = response;

                Intent mainActivity = new Intent(Splash.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

//    private void retrieveBasicPuzzlesRecord() {
//        BackendlessDataQuery query = new BackendlessDataQuery();
//        puzzles.findAsync(query, new DefaultCallback<BackendlessCollection<puzzles>>(RetrieveRecordActivity.this) {
//            @Override
//            public void handleResponse(BackendlessCollection<puzzles> response) {
//                super.handleResponse(response);
//
//                resultCollection = response;
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(RetrieveRecordActivity.this);
//                builder.setTitle("Property to show:");
//                final String[] properties = {"hint", "question", "ownerId", "answer", "_id", "objectId", "solution", "created", "updated"};
//                builder.setItems(properties, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent nextIntent = new Intent(RetrieveRecordActivity.this, ShowByPropertyActivity.class);
//                        nextIntent.putExtra("type", type);
//                        nextIntent.putExtra("property", properties[i]);
//                        startActivity(nextIntent);
//                        dialogInterface.cancel();
//                    }
//                });
//                builder.create().show();
//            }
//        });
//    }
}
