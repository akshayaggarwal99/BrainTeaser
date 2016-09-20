package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.appadda.brainteaser.AnalyticsApplication;
import in.co.appadda.brainteaser.MaterialRippleLayout;
import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.attemptedcontest;

/**
 * Created by dewangankisslove on 27-07-2016.
 */
public class Contest extends AppCompatActivity {
    CardView queNoContainer, addScoreFrame;
    TextView question, questionNo;
    TextView forward;
    int que_no = 0;
    private List<String[]> getContestAllQue = new ArrayList<String[]>();
    DatabaseHandler db;
    private Tracker mTracker;
    ProgressBar pb;
    CountDownTimer countDownTimer;
    String textTimer;
    String[] oneQue;
    TextView addScore;
    int initialScore = 0;
    MediaPlayer lost, gain, tickTimer;
    Button ansSubmit;
    EditText userAns;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // Enable Advertising Features.
        gain = MediaPlayer.create(this, R.raw.gotitem);
        tickTimer = MediaPlayer.create(this, R.raw.timer);
        lost = MediaPlayer.create(this, R.raw.lostitem);

        initUI();
//        initViews();
//        initButtons("not");
    }

    public void initUI() {
        pb = (ProgressBar) findViewById(R.id.progressBar);
        queNoContainer = (CardView) findViewById(R.id.card_view);
        question = (TextView) findViewById(R.id.tv_question);
        questionNo = (TextView) findViewById(R.id.tv_question_number);
        forward = (TextView) findViewById(R.id.iv_forward);
        ansSubmit = (Button) findViewById(R.id.submit_puzzle);
        userAns = (EditText) findViewById(R.id.user_ans_puzzle);

        MaterialRippleLayout.on(ansSubmit)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#35ADCF"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                que_no++;
                initViews();
                initButtons("not");
            }
        });

        ansSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkinternetservice()) {
                    Intent intent = new Intent(Contest.this, CheckAnswer.class);
                    if (userAns.getText().toString().contentEquals(oneQue[3])) {
                        intent.putExtra("userCheckStatus", "Bravo ! Right Answer");
                        addContestAttemptQue("yes", oneQue[1]);
                        if (gain.isPlaying() == false) {
                            gain.setLooping(false);
                            gain.start();
                        }
                        userAns.setText("");
                        countDownTimer.cancel();
                        db.addAllQueStatus(oneQue[1]);
                    } else if (userAns.getText().toString().contentEquals("")) {
                        Toast.makeText(getApplicationContext(), "Answer can't be blank", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra("userCheckStatus", "Oops ! Wrong Answer");
                        if (lost.isPlaying() == false) {
                            lost.setLooping(false);
                            lost.start();
                        }
                        userAns.setText("");
                        addContestAttemptQue("no", oneQue[1]);
                        countDownTimer.cancel();
                        db.addAllQueStatus(oneQue[1]);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews() {
        db = new DatabaseHandler(this);
        getContestAllQue = db.getContestAllQue();
        oneQue = getContestAllQue.get(que_no);

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(oneQue[8]);
        questionNo.setText(sb.toString() + " pts");
        int stopwatchTimer = Integer.parseInt(oneQue[8]);
        startTimer((stopwatchTimer / 3) + 1);
        pb.setMax(((stopwatchTimer / 3) + 1) * 60);

        question.setText(oneQue[2]);


    }

    private void initButtons(String s) {
        forward.setEnabled(que_no < getContestAllQue.size() - 1);
        if (s.contentEquals("visible") && (que_no < getContestAllQue.size() - 1))
            forward.setVisibility(View.VISIBLE);
        else
            forward.setVisibility(View.GONE);


    }

    private void startTimer(final int minuti) {
        countDownTimer = new CountDownTimer(60 * minuti * 1000, 1000) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                if (tickTimer.isPlaying() == false) {
                    tickTimer.setLooping(false);
                    tickTimer.start();
                }
                long seconds = leftTimeInMilliseconds / 1000;
                if ((int) seconds == (15 * minuti * 3)) {
                    int y = Integer.parseInt(oneQue[8]) - 1;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(y);
                    questionNo.setText(sb.toString() + " pts");
                } else if ((int) seconds == (30 * minuti)) {
                    int y = Integer.parseInt(oneQue[8]) - 2;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(y);
                    questionNo.setText(sb.toString() + " pts");
                } else if ((int) seconds == (15 * minuti)) {
                    int y = Integer.parseInt(oneQue[8]) - 3;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(y);
                    questionNo.setText(sb.toString() + " pts");
                } else if ((int) seconds == 0 && Integer.parseInt(oneQue[8]) > 4) {
                    int y = Integer.parseInt(oneQue[8]) - 4;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(y);
                    questionNo.setText(sb.toString() + " pts");
                }
                pb.setProgress((int) seconds);
                textTimer = String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60);
                // format the textview to show the easily readable format


            }

            @Override
            public void onFinish() {
                if (textTimer.equals("00:00")) {
                    textTimer = "STOP";
                } else {
                    textTimer = "2:00";
                    pb.setProgress(60 * minuti);
                }
            }
        }.start();

    }

    private void addContestAttemptQue(String check, final String queId) {
        if (check.contentEquals("yes")) {
            attemptedcontest newAttempt = new attemptedcontest();
            newAttempt.setUsername(PrefUtils.getFromPrefs(getApplicationContext(), "username", "abc"));
            newAttempt.setUser_id(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"));
            newAttempt.setCorrect("yes");
            newAttempt.setPoint(Integer.valueOf(questionNo.getText().toString().split(" ")[0]));
            newAttempt.setQue_id(queId);

            Backendless.Data.of(attemptedcontest.class).save(newAttempt, new AsyncCallback<attemptedcontest>() {
                public void handleResponse(attemptedcontest savedContact) {
                    Date d1 = new Date();
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                    if (!db.checkAttemptedQueStatus(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "_" + queId))
                        db.addContestAttemptQue(dateformat.format(d1), PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "_" + queId, queId, PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"), PrefUtils.getFromPrefs(getApplicationContext(), "username", "abc"), Integer.valueOf(questionNo.getText().toString().split(" ")[0]), "yes");
                    initialScore += Integer.valueOf(questionNo.getText().toString().split(" ")[0]);
                    initButtons("visible");
                    setAddScore("+" + String.valueOf(initialScore) + " pts");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    //Toast.makeText(getBaseContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            attemptedcontest newAttempt = new attemptedcontest();
            newAttempt.setUsername(PrefUtils.getFromPrefs(getApplicationContext(), "username", "abc"));
            newAttempt.setUser_id(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"));
            newAttempt.setCorrect("no");
            newAttempt.setPoint(0);
            newAttempt.setQue_id(queId);

            Backendless.Data.of(attemptedcontest.class).save(newAttempt, new AsyncCallback<attemptedcontest>() {
                public void handleResponse(attemptedcontest savedContact) {
                    initButtons("visible");
                    Date d1 = new Date();
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                    if (!db.checkAttemptedQueStatus(PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "_" + queId))
                        db.addContestAttemptQue(dateformat.format(d1), PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "_" + queId, queId, PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null"), PrefUtils.getFromPrefs(getApplicationContext(), "username", "abc"), 0, "no");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    //Toast.makeText(getBaseContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // [START screen_view_hit]
        mTracker.setScreenName("contest_activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
        if (tickTimer.isPlaying() == true) {
            tickTimer.start();
        }
        if (gain.isPlaying() == true) {
            gain.start();
        }
        if (lost.isPlaying() == true) {
            lost.start();
        }
    }

    public boolean checkinternetservice() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void setAddScore(String s) {
        addScore = (TextView) findViewById(R.id.tv_total_score);
        addScoreFrame = (CardView) findViewById(R.id.card_view2);
        addScoreFrame.setVisibility(View.VISIBLE);
        addScore.setText(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tickTimer.isPlaying() == true) {
            tickTimer.stop();
        }
        if (gain.isPlaying() == true) {
            gain.stop();
        }
        if (lost.isPlaying() == true) {
            lost.stop();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info:
                break;
            case R.id.menu_leaderboard:
                startActivity(new Intent(Contest.this, ContestLeaderboard.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }
}
