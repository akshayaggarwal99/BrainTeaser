package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.adapter.GridSpacingItemDecoration;
import in.co.appadda.brainteaser.adapter.TopicSetAdapter;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import in.co.appadda.brainteaser.data.api.model.DividerItemDecoration;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.allque;
import in.co.appadda.brainteaser.data.api.model.leaderboard;
import in.co.appadda.brainteaser.data.api.model.userstatus;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by dewangankisslove on 10-06-2016.
 */
public class Topics extends AppCompatActivity {
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseHandler db;
    int topicId = 0;
    String[] topics = {"Problem Solving", "Data Sufficiency", "Pattern Recognization", "Critical Thinking", "General Knowledge"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHandler(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_topics);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 2, false));

    }

    private void saveUserStatus() {
        QueryOptions queryOptions = new QueryOptions();
        BackendlessDataQuery queryStat = new BackendlessDataQuery();
        queryStat.setQueryOptions(queryOptions);
        queryStat.setPageSize(1);
        String whereClause = "userId = '" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "'";
        queryStat.setWhereClause(whereClause);
        userstatus.findAsync(queryStat, new AsyncCallback<BackendlessCollection<userstatus>>() {
            @Override
            public void handleResponse(BackendlessCollection<userstatus> userstatusBackendlessCollection) {
                // Toast.makeText(getBaseContext(), userstatusBackendlessCollection.getCurrentPage().size(), Toast.LENGTH_SHORT).show();
                if (userstatusBackendlessCollection.getData().size() == 0) {
                    String userStat = "";
                    for (int j = 0; j < topics.length; j++) {
                        userStat += topics[j] + "_id" + ":" + PrefUtils.getFromPrefs(getApplicationContext(), topics[j] + "_id", "0") + ";";
                        Log.d("questatus", PrefUtils.getFromPrefs(getApplicationContext(), topics[j] + "_id", "0"));
                    }
                    userstatus newStatus = new userstatus();
                    newStatus.setStatus(userStat);
                    newStatus.setUserId(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"));

                    final String finalUserStat = userStat;
                    Backendless.Data.of(userstatus.class).save(newStatus, new AsyncCallback<userstatus>() {
                        public void handleResponse(userstatus savedContact) {
                            savedContact.setStatus(finalUserStat);

                            Backendless.Data.of(userstatus.class).save(savedContact, new AsyncCallback<userstatus>() {
                                @Override
                                public void handleResponse(userstatus response) {
                                    // Contact instance has been updated
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    // an error has occurred, the error code can be retrieved with fault.getCode()
                                }
                            });
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            // an error has occurred, the error code can be retrieved with fault.getCode()
                        }
                    });
                } else {
                    String userStat = "";
                    for (int j = 0; j < topics.length; j++) {
                        userStat += topics[j] + "_id" + ":" + PrefUtils.getFromPrefs(getApplicationContext(), topics[j] + "_id", "0") + ";";
                        Log.d("questatus", PrefUtils.getFromPrefs(getApplicationContext(), topics[j] + "_id", "0"));
                    }
                    final AsyncCallback<userstatus> updateResponder = new AsyncCallback<userstatus>() {
                        @Override
                        public void handleResponse(userstatus updateduserstatus) {
                        }

                        @Override
                        public void handleFault(BackendlessFault backendlessFault) {
                        }
                    };

                    userstatusBackendlessCollection.getCurrentPage().get(0).setUserId(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"));
                    userstatusBackendlessCollection.getCurrentPage().get(0).setStatus(userStat);

                    Backendless.Data.of(userstatus.class).save(userstatusBackendlessCollection.getCurrentPage().get(0), updateResponder);
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new TopicSetAdapter(this, topics);
        recyclerView.setAdapter(mAdapter);
        ((TopicSetAdapter) mAdapter).setOnItemClickListener(new TopicSetAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (checkinternetservice())
                    retrieveQue(topics[position]);
                else
                    Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(Topics.this, Compete.class));
                PrefUtils.saveToPrefs(getApplicationContext(), "openCompete", "false");
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    private void retrieveQue(final String s) {
        topicId = Integer.parseInt(PrefUtils.getFromPrefs(Topics.this, s + "_id", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("id ASC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setWhereClause("topic = " + s);
        query.setWhereClause("id > " + topicId);
        query.setPageSize(100);
        allque.findAsync(query, new DefaultCallback<BackendlessCollection<allque>>(Topics.this) {
            @Override
            public void handleResponse(BackendlessCollection<allque> allque) {
                super.handleResponse(allque);
                for (int k = 0; k < allque.getData().size(); k++) {
                    db.addAllQue(allque.getCurrentPage().get(k).getId(), allque.getCurrentPage().get(k).getQue_id(), allque.getCurrentPage().get(k).getQuestion(), allque.getCurrentPage().get(k).getAnswer(), allque.getCurrentPage().get(k).getExplanation(), allque.getCurrentPage().get(k).getTopic(), allque.getCurrentPage().get(k).getPoint(), allque.getCurrentPage().get(k).getOption_one(), allque.getCurrentPage().get(k).getOption_two(), allque.getCurrentPage().get(k).getOption_three(), allque.getCurrentPage().get(k).getOption_four());
                    if (k == allque.getData().size() - 1)
                        topicId = allque.getCurrentPage().get(k).getId();
                }
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(topicId);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(Topics.this, s + "_id", ID);
//                for (int i = 0; i < allque.getCurrentPage().size(); i++) {
//                    db.addAllQue(allque.getCurrentPage().get(i).getId(), allque.getCurrentPage().get(i).getQue_id(), allque.getCurrentPage().get(i).getQuestion(), allque.getCurrentPage().get(i).getAnswer(), allque.getCurrentPage().get(i).getExplanation(), allque.getCurrentPage().get(i).getTopic(), allque.getCurrentPage().get(i).getPoint(), allque.getCurrentPage().get(i).getOption_one(), allque.getCurrentPage().get(i).getOption_two(), allque.getCurrentPage().get(i).getOption_three(), allque.getCurrentPage().get(i).getOption_four());
//                }
                PrefUtils.saveToPrefs(getApplicationContext(), "topicName", s);
                PrefUtils.saveToPrefs(getApplicationContext(), "openCompete", "true");
                saveUserStatus();
                Intent startCompete = new Intent(Topics.this, Compete.class);
                startActivity(startCompete);
                finish();
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
            }
        });
    }

    public boolean checkinternetservice() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Topics.this, Compete.class));
        PrefUtils.saveToPrefs(getApplicationContext(), "openCompete", "false");
        finish();
    }
}
