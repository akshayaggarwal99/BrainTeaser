package in.co.appadda.brainteaser.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import in.co.appadda.brainteaser.AnalyticsApplication;
import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.adapter.QueSetAdapter;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.QuestionSets;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by dewangankisslove on 09-03-2016.
 */
public class DisplayAptitudeSets extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = "DisplayAptitudeSets";
    private Tracker mTracker;


    int layoutR = R.layout.each_aptitude_set_layout;
    private int totalAptitudeQue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aptitude_set_layout);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        totalAptitudeQue = Integer.parseInt(PrefUtils.getFromPrefs(DisplayAptitudeSets.this, "_id_aptitude", "20"));

        mRecyclerView = (RecyclerView) findViewById(R.id.queSetList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new QueSetAdapter(getDataSet(), layoutR);
        mRecyclerView.setAdapter(mAdapter);



    }

    @Override
    protected void onResume() {
        super.onResume();

        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: " );
        mTracker.setScreenName("Que-Sets"  );
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
        ((QueSetAdapter) mAdapter).setOnItemClickListener(new QueSetAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                TextView textView = (TextView) v.findViewById(R.id.tv_setNo);
                Intent openAptitude = new Intent(DisplayAptitudeSets.this, DisplayQue.class);
                openAptitude.putExtra("openFragment", "openAptitude");
                for (int i = 1; i <= totalAptitudeQue / 20; i++) {
                    if (textView.getText().toString().contentEquals("Set " + i)) {
                        openAptitude.putExtra("showAptitudeQue", i);
                    }
                }
                startActivity(openAptitude);
            }
        });
    }

    private ArrayList<QuestionSets> getDataSet() {
        DatabaseHandler db = new DatabaseHandler(this);
        int totalAptitudeQueDone;
        String exact;
        ArrayList results = new ArrayList<QuestionSets>();
        for (int i = 0; i < (totalAptitudeQue / 20); i++) {
            int j = i + 1;
            totalAptitudeQueDone = db.getAptitudeSetStatusCount(j);
            Log.d("quesdone",String.valueOf(totalAptitudeQueDone));
            if (totalAptitudeQueDone == 20) {
                exact = "You have completed this set";
            } else {
                exact = totalAptitudeQueDone + " questions done";
            }

            QuestionSets obj = new QuestionSets(R.drawable.study, "Set " + j,
                    "Total Questions 20", exact);
            results.add(i, obj);
        }
        return results;
    }
}
