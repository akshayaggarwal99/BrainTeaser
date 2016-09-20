package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.contestboard;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by dewangankisslove on 28-07-2016.
 */
public class ContestLeaderboard extends AppCompatActivity{
    
    CollapsingToolbarLayout collapsingToolbar;
    TextView totalScore;
    int back = 0;
    ArrayList<String[]> allContestAttemptedQue = new ArrayList<String[]>();
    DatabaseHandler db;
    int m = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        db = new DatabaseHandler(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        allContestAttemptedQue = db.getContestAttemptedQues();

        if (allContestAttemptedQue.size() != 0) {
            QueryOptions queryOptions = new QueryOptions();
            BackendlessDataQuery queryStat = new BackendlessDataQuery();
            queryStat.setQueryOptions(queryOptions);
            queryStat.setPageSize(1);
            String whereClause = "user_id = '" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "'";
            queryStat.setWhereClause(whereClause);
            contestboard.findAsync(queryStat, new AsyncCallback<BackendlessCollection<contestboard>>() {
                @Override
                public void handleResponse(BackendlessCollection<contestboard> contestboardCollection) {
                    if (contestboardCollection.getData().size() == 0) {
                        contestboard newLeader = new contestboard();
                        newLeader.setUser_id(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"));
                        newLeader.setUsername(allContestAttemptedQue.get(allContestAttemptedQue.size() - 1)[2]);
                        newLeader.setAttemptque(String.valueOf(allContestAttemptedQue.size()));
                        newLeader.setProfileurl(PrefUtils.getFromPrefs(getApplicationContext(), "userPic", "null"));
                        int j = 0;
                        int p = 0;
                        for (int k = 0; k < allContestAttemptedQue.size(); k++) {
                            if (allContestAttemptedQue.get(k)[4].contentEquals("yes"))
                                j += 1;
                            p += Integer.parseInt(allContestAttemptedQue.get(k)[3]);
                            
                        }
                        newLeader.setRightans(String.valueOf(j));
                        newLeader.setTotal_points(String.valueOf(p));

                        Backendless.Data.of(contestboard.class).save(newLeader, new AsyncCallback<contestboard>() {
                            public void handleResponse(contestboard savedContact) {
                                retrieveContestboard();
                                db.removeContestAttemptedQues();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                // an error has occurred, the error code can be retrieved with fault.getCode()
                            }
                        });
                    } else {
                        final AsyncCallback<contestboard> updateResponder = new AsyncCallback<contestboard>() {
                            @Override
                            public void handleResponse(contestboard updatedleaderboard) {
                                retrieveContestboard();
                                db.removeContestAttemptedQues();
                            }

                            @Override
                            public void handleFault(BackendlessFault backendlessFault) {
                            }
                        };
                        contestboardCollection.getCurrentPage().get(0).setUser_id(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"));
                        contestboardCollection.getCurrentPage().get(0).setUsername(allContestAttemptedQue.get(allContestAttemptedQue.size() - 1)[2]);
                        contestboardCollection.getCurrentPage().get(0).setAttemptque(String.valueOf(Integer.parseInt(contestboardCollection.getCurrentPage().get(0).getAttemptque()) + allContestAttemptedQue.size()));
                        int j = 0;
                        int p = 0;
                        for (int k = 0; k < allContestAttemptedQue.size(); k++) {
                            if (allContestAttemptedQue.get(k)[4].contentEquals("yes"))
                                j += 1;
                            p += Integer.parseInt(allContestAttemptedQue.get(k)[3]);


                        }
                        contestboardCollection.getCurrentPage().get(0).setRightans(String.valueOf(Integer.parseInt(contestboardCollection.getCurrentPage().get(0).getRightans()) + j));
                        contestboardCollection.getCurrentPage().get(0).setTotal_points(String.valueOf(Integer.parseInt(contestboardCollection.getCurrentPage().get(0).getTotal_points()) + p));

                        Backendless.Data.of(contestboard.class).save(contestboardCollection.getCurrentPage().get(0), updateResponder);
                    }
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {

                }
            });
        } else {
            retrieveContestboard();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void retrieveContestboard() {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("total_points DESC");
        final BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setPageSize(100);
        contestboard.findAsync(query, new AsyncCallback<BackendlessCollection<contestboard>>() {
            @Override
            public void handleResponse(BackendlessCollection<contestboard> contestboardCollection) {
                LinearLayout myLayout = (LinearLayout) findViewById(R.id.ll_rank_list);
                collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                for (int k = 0; k < contestboardCollection.getCurrentPage().size(); k++) {
                    int j = k + 1;
                    //Log.d("userIdentity", contestboardCollection.getCurrentPage().get(k).getUser_id() + ":" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null@gmail.com"));
                    if (contestboardCollection.getData().get(k).getUser_id().contentEquals(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null")) && PrefUtils.getFromPrefs(getApplicationContext(), "fucked", "null").contentEquals("null")) {
                        collapsingToolbar.setTitle("Rank #" + j);
                        loadBackground(contestboardCollection.getCurrentPage().get(k).getTotal_points());
                        PrefUtils.saveToPrefs(getApplicationContext(), "fucked", "xyz");
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
                                .load(contestboardCollection.getCurrentPage().get(k).getProfileurl())
                                .error(R.drawable.person_icon)
                                .noFade()
                                .into(proPic);

                        personRank.setText("Rank #" + j);
                        person_name.setText(contestboardCollection.getCurrentPage().get(k).getUsername());
                        person_score.setText("Total score: " + contestboardCollection.getCurrentPage().get(k).getTotal_points());
                        myLayout.addView(hiddenInfo);
                    }
                }
                if (!PrefUtils.getFromPrefs(getApplicationContext(), "fucked", "null").contentEquals("xyz")) {
                    PrefUtils.saveToPrefs(getApplicationContext(), "fucked", "null");
                    querySecond();
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    back = 1;
                    PrefUtils.saveToPrefs(getApplicationContext(), "fucked", "null");
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
    public void onBackPressed() {
        if (back == 1) {
            super.onBackPressed();
        }
    }

    public void querySecond() {
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("total_points DESC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setOffset(m);
        query.setPageSize(100);

        contestboard.findAsync(query, new AsyncCallback<BackendlessCollection<contestboard>>() {
            @Override
            public void handleResponse(BackendlessCollection<contestboard> contestboardCollection) {
                for (int k = 0; k < contestboardCollection.getCurrentPage().size(); k++) {
                    int j = k + m + 1;
                    Log.d("userIdentity", contestboardCollection.getCurrentPage().get(k).getUser_id() + ":" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null@gmail.com"));
                    if (contestboardCollection.getData().get(k).getUser_id().contentEquals(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"))) {
                        collapsingToolbar.setTitle("Rank #" + j);
                        loadBackground(contestboardCollection.getCurrentPage().get(k).getTotal_points());
                        PrefUtils.saveToPrefs(getApplicationContext(), "fucked", "xyz");
                    }
                }
                if (!PrefUtils.getFromPrefs(getApplicationContext(), "fucked", "null").contentEquals("xyz")) {
                    m += 100;
                    PrefUtils.saveToPrefs(getApplicationContext(), "fucked", "null");
                    querySecond();
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    back = 1;
                    PrefUtils.saveToPrefs(getApplicationContext(), "fucked", "null");
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                back = 1;
            }
        });
    }
}
