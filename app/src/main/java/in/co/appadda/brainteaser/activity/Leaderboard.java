package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.leaderboard;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by dewangankisslove on 10-06-2016.
 */
public class Leaderboard extends AppCompatActivity implements View.OnClickListener {
    //    RecyclerView recyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
    int result;
    static int fixed = 0;
    CollapsingToolbarLayout collapsingToolbar;
    TextView totalScore;
    int back = 0;
    ArrayList<String[]> allAttemptedQue = new ArrayList<String[]>();
    DatabaseHandler db;
    int m = 100;
    FloatingActionButton userReport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        db = new DatabaseHandler(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        userReport = (FloatingActionButton) findViewById(R.id.report);

        userReport.setOnClickListener(null);

        allAttemptedQue = db.getAttemptedQues();

        if (allAttemptedQue.size() != 0) {
            QueryOptions queryOptions = new QueryOptions();
            BackendlessDataQuery queryStat = new BackendlessDataQuery();
            queryStat.setQueryOptions(queryOptions);
            queryStat.setPageSize(1);
            String whereClause = "user_id = '" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "'";
            queryStat.setWhereClause(whereClause);
            leaderboard.findAsync(queryStat, new AsyncCallback<BackendlessCollection<leaderboard>>() {
                @Override
                public void handleResponse(BackendlessCollection<leaderboard> leaderboardBackendlessCollection) {
                    if (leaderboardBackendlessCollection.getData().size() == 0) {
                        leaderboard newLeader = new leaderboard();
                        newLeader.setUser_id(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"));
                        newLeader.setUsername(allAttemptedQue.get(allAttemptedQue.size() - 1)[3]);
                        newLeader.setTotal_attempted_que(allAttemptedQue.size());
                        newLeader.setProfileurl(PrefUtils.getFromPrefs(getApplicationContext(), "userPic", "null"));
                        int j = 0;
                        int p = 0;
                        List<String> topicName = new ArrayList<String>();
                        List<String> topicNo = new ArrayList<String>();
                        List<String> topicCheck = new ArrayList<String>();
                        for (int k = 0; k < allAttemptedQue.size(); k++) {
                            if (allAttemptedQue.get(k)[5].contentEquals("yes"))
                                j += 1;
                            p += Integer.parseInt(allAttemptedQue.get(k)[4]);
                            if (topicName.contains(allAttemptedQue.get(k)[2])) {
                                topicNo.set(topicName.indexOf(allAttemptedQue.get(k)[2]), String.valueOf(Integer.parseInt(topicNo.get(topicName.indexOf(allAttemptedQue.get(k)[2]))) + 1));
                                if (allAttemptedQue.get(k)[5].contentEquals("yes"))
                                    topicCheck.set(topicName.indexOf(allAttemptedQue.get(k)[2]), String.valueOf(Integer.parseInt(topicCheck.get(topicName.indexOf(allAttemptedQue.get(k)[2]))) + 1));
                            } else {
                                topicName.add(allAttemptedQue.get(k)[2]);
                                topicNo.add("1");
                                if (allAttemptedQue.get(k)[5].contentEquals("yes"))
                                    topicCheck.add("1");
                                else
                                    topicCheck.add("0");
                            }


                        }
//                        for (int f = 0; f < leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";").length; f++) {
//                            if (topicName.contains(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0])) {
//                                topicNo.set(topicName.indexOf(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]), String.valueOf(Integer.parseInt(topicNo.get(topicName.indexOf(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]))) + Integer.parseInt(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[1])));
//                                topicCheck.set(topicName.indexOf(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]), String.valueOf(Integer.parseInt(topicCheck.get(topicName.indexOf(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]))) + Integer.parseInt(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[2])));
//                            } else {
//                                topicName.add(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]);
//                                topicNo.add(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[1]);
//                                topicCheck.add(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[2]);
//                            }
//                        }
                        newLeader.setTotal_right_ans(j);
                        newLeader.setTotal_points(p);
                        String setQueInfo = "";
                        for (int a = 0; a < topicName.size(); a++) {
                            setQueInfo += topicName.get(a) + ":" + topicNo.get(a) + ":" + topicCheck.get(a) + ";";
                        }
                        newLeader.setQue_type(setQueInfo);

                        Backendless.Data.of(leaderboard.class).save(newLeader, new AsyncCallback<leaderboard>() {
                            public void handleResponse(leaderboard savedContact) {
                                retrieveLeaderboard();
                                db.removeAttemptedQues();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                // an error has occurred, the error code can be retrieved with fault.getCode()
                            }
                        });
                    } else {
                        final AsyncCallback<leaderboard> updateResponder = new AsyncCallback<leaderboard>() {
                            @Override
                            public void handleResponse(leaderboard updatedleaderboard) {
                                retrieveLeaderboard();
                                db.removeAttemptedQues();
                            }

                            @Override
                            public void handleFault(BackendlessFault backendlessFault) {
                            }
                        };
                        leaderboardBackendlessCollection.getCurrentPage().get(0).setUser_id(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"));
                        leaderboardBackendlessCollection.getCurrentPage().get(0).setUsername(allAttemptedQue.get(allAttemptedQue.size() - 1)[3]);
                        leaderboardBackendlessCollection.getCurrentPage().get(0).setTotal_attempted_que(leaderboardBackendlessCollection.getCurrentPage().get(0).getTotal_attempted_que() + allAttemptedQue.size());
                        int j = 0;
                        int p = 0;
                        List<String> topicName = new ArrayList<String>();
                        List<String> topicNo = new ArrayList<String>();
                        List<String> topicCheck = new ArrayList<String>();
                        for (int k = 0; k < allAttemptedQue.size(); k++) {
                            if (allAttemptedQue.get(k)[5].contentEquals("yes"))
                                j += 1;
                            p += Integer.parseInt(allAttemptedQue.get(k)[4]);
                            if (topicName.contains(allAttemptedQue.get(k)[2])) {
                                topicNo.set(topicName.indexOf(allAttemptedQue.get(k)[2]), String.valueOf(Integer.parseInt(topicNo.get(topicName.indexOf(allAttemptedQue.get(k)[2]))) + 1));
                                if (allAttemptedQue.get(k)[5].contentEquals("yes"))
                                    topicCheck.set(topicName.indexOf(allAttemptedQue.get(k)[2]), String.valueOf(Integer.parseInt(topicCheck.get(topicName.indexOf(allAttemptedQue.get(k)[2]))) + 1));
                            } else {
                                topicName.add(allAttemptedQue.get(k)[2]);
                                topicNo.add("1");
                                if (allAttemptedQue.get(k)[5].contentEquals("yes"))
                                    topicCheck.add("1");
                                else
                                    topicCheck.add("0");
                            }


                        }
                        for (int f = 0; f < leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";").length; f++) {
                            if (topicName.contains(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0])) {
                                topicNo.set(topicName.indexOf(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]), String.valueOf(Integer.parseInt(topicNo.get(topicName.indexOf(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]))) + Integer.parseInt(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[1])));
                                topicCheck.set(topicName.indexOf(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]), String.valueOf(Integer.parseInt(topicCheck.get(topicName.indexOf(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]))) + Integer.parseInt(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[2])));
                            } else {
                                topicName.add(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[0]);
                                topicNo.add(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[1]);
                                topicCheck.add(leaderboardBackendlessCollection.getCurrentPage().get(0).getQue_type().split(";")[f].split(":")[2]);
                            }
                        }
                        leaderboardBackendlessCollection.getCurrentPage().get(0).setTotal_right_ans(leaderboardBackendlessCollection.getCurrentPage().get(0).getTotal_right_ans() + j);
                        leaderboardBackendlessCollection.getCurrentPage().get(0).setTotal_points(leaderboardBackendlessCollection.getCurrentPage().get(0).getTotal_points() + p);
                        String setQueInfo = "";
                        for (int a = 0; a < topicName.size(); a++) {
                            setQueInfo += topicName.get(a) + ":" + topicNo.get(a) + ":" + topicCheck.get(a) + ";";
                        }
                        leaderboardBackendlessCollection.getCurrentPage().get(0).setQue_type(setQueInfo);

                        Backendless.Data.of(leaderboard.class).save(leaderboardBackendlessCollection.getCurrentPage().get(0), updateResponder);
                    }
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {

                }
            });
        } else {
            retrieveLeaderboard();
        }


//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mAdapter = new LeaderboardListAdapter(Leaderboard.this, personName, personScore, rank);
//        recyclerView.setAdapter(mAdapter);
//        ((LeaderboardListAdapter) mAdapter).setOnItemClickListener(new LeaderboardListAdapter.MyClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(Leaderboard.this, Compete.class));
                PrefUtils.saveToPrefs(getApplicationContext(), "openCompete", "false");
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBackground(String s) {
        final ImageView imageView = (ImageView) findViewById(R.id.leaderboard_bg);
        totalScore = (TextView) findViewById(R.id.tv_total_score);
        totalScore.setText("Your total score is " + s);
        Glide.with(this).load(R.drawable.gradestar).centerCrop().into(imageView);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    private void retrieveLeaderboard() {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("total_points DESC");
        final BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setPageSize(100);
        leaderboard.findAsync(query, new AsyncCallback<BackendlessCollection<leaderboard>>() {
            @Override
            public void handleResponse(BackendlessCollection<leaderboard> leaderboardBackendlessCollection) {
                LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_rank_list);
                collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                for (int k = 0; k < leaderboardBackendlessCollection.getCurrentPage().size(); k++) {
                    int j = k + 1;
                    //Log.d("userIdentity", leaderboardBackendlessCollection.getCurrentPage().get(k).getUser_id() + ":" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null@gmail.com"));
                    if (leaderboardBackendlessCollection.getData().get(k).getUser_id().contentEquals(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null")) && PrefUtils.getFromPrefs(getApplicationContext(), "chutiyap", "null").contentEquals("null")) {
                        collapsingToolbar.setTitle("Rank #" + j);
                        loadBackground(leaderboardBackendlessCollection.getCurrentPage().get(k).getTotal_points().toString());
                        PrefUtils.saveToPrefs(getApplicationContext(), "chutiyap", "xyz");
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        back = 1;
                    }
                    if (k < 50) {
                        View hiddenInfo = getLayoutInflater().inflate(R.layout.leaderboard_list_object, myLayout, false);
                        TextView person_name = (TextView) hiddenInfo.findViewById(R.id.tv_username);
                        TextView person_score = (TextView) hiddenInfo.findViewById(R.id.tv_userscore);
                        TextView personRank = (TextView) hiddenInfo.findViewById(R.id.tv_rank);
                        ImageView proPic = (ImageView) hiddenInfo.findViewById(R.id.image);

                        Picasso.with(getApplicationContext())
                                .load(leaderboardBackendlessCollection.getCurrentPage().get(k).getProfileurl())
                                .error(R.drawable.person_icon)
                                .noFade()
                                .into(proPic);

                        personRank.setText("Rank #" + j);
                        person_name.setText(leaderboardBackendlessCollection.getCurrentPage().get(k).getUsername());
                        person_score.setText("Total score: " + leaderboardBackendlessCollection.getCurrentPage().get(k).getTotal_points());
                        myLayout.addView(hiddenInfo);
                    }
                }
                QueryOptions queryOptions = new QueryOptions();
                queryOptions.addSortByOption("total_points DESC");
                final BackendlessDataQuery query = new BackendlessDataQuery();
                query.setQueryOptions(queryOptions);
                String whereClau = "user_id = '" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "'";
                query.setWhereClause(whereClau);
                query.setPageSize(1);
                leaderboard.findAsync(query, new AsyncCallback<BackendlessCollection<leaderboard>>() {
                    @Override
                    public void handleResponse(BackendlessCollection<leaderboard> leaderboardBackendlessCollection) {

                        if (!PrefUtils.getFromPrefs(getApplicationContext(), "chutiyap", "null").contentEquals("xyz") && leaderboardBackendlessCollection.getData().size() != 0) {
                            PrefUtils.saveToPrefs(getApplicationContext(), "chutiyap", "null");
                            querySecond();
                        } else {
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            back = 1;
                            userReport.setOnClickListener(Leaderboard.this);
                            PrefUtils.saveToPrefs(getApplicationContext(), "chutiyap", "null");
                        }

                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        back = 1;
                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                back = 1;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (back == 1) {
            startActivity(new Intent(Leaderboard.this, Compete.class));
            PrefUtils.saveToPrefs(getApplicationContext(), "openCompete", "false");
            finish();
        }
    }

    public void querySecond() {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("total_points DESC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setOffset(m);
        query.setPageSize(100);

        leaderboard.findAsync(query, new AsyncCallback<BackendlessCollection<leaderboard>>() {
            @Override
            public void handleResponse(BackendlessCollection<leaderboard> leaderboardBackendlessCollection) {
                for (int k = 0; k < leaderboardBackendlessCollection.getCurrentPage().size(); k++) {
                    int j = k + m + 1;
                    Log.d("userIdentity", leaderboardBackendlessCollection.getCurrentPage().get(k).getUser_id() + ":" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null@gmail.com"));
                    if (leaderboardBackendlessCollection.getData().get(k).getUser_id().contentEquals(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"))) {
                        collapsingToolbar.setTitle("Rank #" + j);
                        loadBackground(leaderboardBackendlessCollection.getCurrentPage().get(k).getTotal_points().toString());
                        PrefUtils.saveToPrefs(getApplicationContext(), "chutiyap", "xyz");
                    }
                }
                if (!PrefUtils.getFromPrefs(getApplicationContext(), "chutiyap", "null").contentEquals("xyz")) {
                    m += 100;
                    PrefUtils.saveToPrefs(getApplicationContext(), "chutiyap", "null");
                    querySecond();
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    back = 1;
                    userReport.setOnClickListener(Leaderboard.this);
                    PrefUtils.saveToPrefs(getApplicationContext(), "chutiyap", "null");
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                back = 1;
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
    public void onClick(View view) {
        if (checkinternetservice())
            startActivity(new Intent(Leaderboard.this, AttemptedQueStatistics.class));
        else
            Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
    }
}
