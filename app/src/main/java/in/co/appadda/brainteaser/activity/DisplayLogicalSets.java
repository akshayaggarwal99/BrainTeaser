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
import in.co.appadda.brainteaser.adapter.QueSetAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logical_set_layout);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // Enable Advertising Features.

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
                switch (textView.getText().toString()) {
                    case "Set 1":

                        break;
                    case "Set 2":

                        break;
                    case "Set 3":

                        break;
                }
                startActivity(openAptitude);
            }
        });
    }

    private ArrayList<QuestionSets> getDataSet() {
        ArrayList results = new ArrayList<QuestionSets>();
        for (int i = 0; i < 10; i++) {
            int j = i + 1;
            QuestionSets obj = new QuestionSets(R.drawable.studyz,"Set " + j,
                    "Total Questions 20", "You have Completed !");
            results.add(i, obj);
        }
        return results;
    }
}
