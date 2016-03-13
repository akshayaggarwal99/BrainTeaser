package in.co.appadda.brainteaser.activity;

import android.content.Intent;
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

/**
 * Created by dewangankisslove on 09-03-2016.
 */
public class DisplayLogicalSets extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = "DisplayLogicalSets";
    private Tracker mTracker;

    int layoutR = R.layout.each_logical_set_layout;
    private int totalLogicalQue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logical_set_layout);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // Enable Advertising Features.

        totalLogicalQue = Integer.parseInt(PrefUtils.getFromPrefs(DisplayLogicalSets.this, "_id_logical", "0"));

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
        Log.i(TAG, "Setting screen name: " );
        mTracker.setScreenName("Que-Sets"  );
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        ((QueSetAdapter) mAdapter).setOnItemClickListener(new QueSetAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                TextView textView = (TextView) v.findViewById(R.id.tv_setNo);
                Intent openAptitude = new Intent(DisplayLogicalSets.this, DisplayQue.class);
                openAptitude.putExtra("openFragment", "openLogical");
                for (int i = 1; i <= totalLogicalQue / 20; i++) {
                    if (textView.getText().toString().contentEquals("Set " + i)) {
                        openAptitude.putExtra("showLogicalQue", i);
                    }
                }
                startActivity(openAptitude);
            }
        });
    }

    private ArrayList<QuestionSets> getDataSet() {
        DatabaseHandler db = new DatabaseHandler(this);
        int totalLogicalQueDone;
        String exact;
        ArrayList results = new ArrayList<QuestionSets>();
        for (int i = 0; i < (totalLogicalQue / 20); i++) {
            int j = i + 1;
            totalLogicalQueDone = db.getLogicalSetStatusCount(j);
            if (totalLogicalQueDone == 20) {
                exact = "You have completed this set";
            } else {
                exact = totalLogicalQueDone + " questions done";
            }

            QuestionSets obj = new QuestionSets(R.drawable.studyz, "Set " + j,
                    "Total Questions 20", exact);
            results.add(i, obj);
        }
        return results;
    }
}
