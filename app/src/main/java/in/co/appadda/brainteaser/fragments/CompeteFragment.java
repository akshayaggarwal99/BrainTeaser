package in.co.appadda.brainteaser.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import in.co.appadda.brainteaser.activity.QuestionExplanation;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.OptionsItems;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.attempted;

/**
 * Created by dewangankisslove on 11-07-2016.
 */
public class CompeteFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ArrayList<OptionsItems> optionsItemsArrayList = new ArrayList<OptionsItems>();
    CardView queNoContainer, addScoreFrame;
    private OptionItemAdapter adapter;
    ListView optionListView;
    TextView question, questionNo;
    TextView forward;
    Button explanation;
    int que_no = 0;
    private List<String[]> getAllQue = new ArrayList<String[]>();
    DatabaseHandler db;
    private Tracker mTracker;
    ProgressBar pb;
    CountDownTimer countDownTimer;
    String textTimer;
    String topicName;
    String[] oneQue;
    TextView addScore;
    int initialScore = 0;
    MediaPlayer lost, gain, tickTimer;

    public static CompeteFragment newInstance(String topicName) {
        CompeteFragment competeFragment = new CompeteFragment();
        Bundle b = new Bundle();
        b.putString("topicName", topicName);
        competeFragment.setArguments(b);

        return competeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        // Enable Advertising Features.
        topicName = getArguments().getString("topicName");
        gain = MediaPlayer.create(getActivity(), R.raw.gotitem);
        tickTimer = MediaPlayer.create(getActivity(), R.raw.timer);
        lost = MediaPlayer.create(getActivity(), R.raw.lostitem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.compete_question, container, false);
        initUI(v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initButtons("not");


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
                    int y = Integer.parseInt(oneQue[9]) - 1;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(y);
                    questionNo.setText(sb.toString() + " pts");
                } else if ((int) seconds == (30 * minuti)) {
                    int y = Integer.parseInt(oneQue[9]) - 2;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(y);
                    questionNo.setText(sb.toString() + " pts");
                } else if ((int) seconds == (15 * minuti)) {
                    int y = Integer.parseInt(oneQue[9]) - 3;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(y);
                    questionNo.setText(sb.toString() + " pts");
                } else if ((int) seconds == 0 && Integer.parseInt(oneQue[9]) > 4) {
                    int y = Integer.parseInt(oneQue[9]) - 4;
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

    private void initUI(View v) {
        pb = (ProgressBar) v.findViewById(R.id.progressBar);
        queNoContainer = (CardView) v.findViewById(R.id.card_view);
        optionListView = (ListView) v.findViewById(R.id.lv_options);
        question = (TextView) v.findViewById(R.id.tv_question);
        questionNo = (TextView) v.findViewById(R.id.tv_question_number);
        forward = (TextView) v.findViewById(R.id.iv_forward);
        explanation = (Button) v.findViewById(R.id.b_explanation);
        MaterialRippleLayout.on(explanation)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#35ADCF"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                que_no++;
                explanation.setAlpha(0);
                explanation.setEnabled(false);
                optionsItemsArrayList.clear();
                initViews();
                initButtons("not");
                optionListView.setEnabled(true);
                optionListView.setClickable(true);
            }
        });

    }

    private void initViews() {
        db = new DatabaseHandler(getActivity());
        getAllQue = db.getAllQue(topicName);
        oneQue = getAllQue.get(que_no);

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(oneQue[9]);
        questionNo.setText(sb.toString() + " pts");
        int stopwatchTimer = Integer.parseInt(oneQue[9]);
        startTimer((stopwatchTimer / 3) + 1);
        pb.setMax(((stopwatchTimer / 3) + 1) * 60);

        question.setText(oneQue[2]);

        String[] options = {oneQue[5], oneQue[6], oneQue[7], oneQue[8]};
        for (int i = 0; i < 4; i++) {
            optionsItemsArrayList.add(new OptionsItems("0", options[i]));
        }
        adapter = new OptionItemAdapter(getActivity().getApplicationContext(), optionsItemsArrayList);
        optionListView.setAdapter(adapter);

        optionListView.setOnItemClickListener(this);

        explanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = oneQue[4];
                Intent explanation = new Intent(getActivity(), QuestionExplanation.class);
                explanation.putExtra("explain", msg);
                startActivity(explanation);
            }
        });


    }

    private void initButtons(String s) {
        forward.setEnabled(que_no < getAllQue.size() - 1);
        if (s.contentEquals("visible") && (que_no < getAllQue.size() - 1))
            forward.setVisibility(View.VISIBLE);
        else
            forward.setVisibility(View.GONE);


    }

    @Override
    public void onResume() {
        super.onResume();
        // [START screen_view_hit]
        mTracker.setScreenName("compete_fragment");
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        String[] ans = {"A","B","C","D"};
        if (checkinternetservice()) {
            View v;
            CardView cardView;
            TextView opt;
            int count = parent.getChildCount();
            v = parent.getChildAt(position);
            parent.requestChildFocus(v, view);
            cardView = (CardView) v.findViewById(R.id.card_option);
            opt = (TextView) v.findViewById(R.id.tv_options);
            if (ans[position].contentEquals(oneQue[3])) {
                addAttemptedQue("yes", oneQue[1]);
                cardView.setCardBackgroundColor(Color.parseColor("#0A8754"));
                opt.setTextColor(Color.parseColor("#FFFFFF"));
                if (gain.isPlaying() == false) {
                    gain.setLooping(false);
                    gain.start();
                }
            } else {
                if (lost.isPlaying() == false) {
                    lost.setLooping(false);
                    lost.start();
                }
                addAttemptedQue("no", oneQue[1]);
                cardView.setCardBackgroundColor(Color.parseColor("#ED6A5A"));
                opt.setTextColor(Color.parseColor("#FFFFFF"));
                for (int i = 0; i < count; i++) {
                    if (i != position) {
                        v = parent.getChildAt(i);
                        cardView = (CardView) v.findViewById(R.id.card_option);
                        opt = (TextView) v.findViewById(R.id.tv_options);
                        if (ans[position].contentEquals(oneQue[3])) {
                            cardView.setCardBackgroundColor(Color.parseColor("#0A8754"));
                            opt.setTextColor(Color.parseColor("#FFFFFF"));
                        }

                    }
                }
            }

            countDownTimer.cancel();
            db.addAllQueStatus(oneQue[1]);
            explanation.setEnabled(true);
            explanation.setAlpha(1);
            optionListView.setEnabled(false);
            optionListView.setClickable(false);
            optionListView.setOnItemClickListener(null);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
        }
    }

    class OptionItemAdapter extends BaseAdapter {
        Context context;
        ArrayList<OptionsItems> optionList;


        public OptionItemAdapter(Context context, ArrayList<OptionsItems> optionList) {
            this.context = context;
            this.optionList = optionList;
        }

        private class Myholder {
            TextView option;
            CardView cardOption;

            Myholder(View view) {
                option = (TextView) view.findViewById(R.id.tv_options);
                cardOption = (CardView) view.findViewById(R.id.card_option);
            }
        }

        @Override
        public int getCount() {
            return optionList.size();
        }

        @Override
        public Object getItem(int position) {
            return optionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            Myholder myholder = null;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.option_object, parent, false);
                myholder = new Myholder(row);
                row.setTag(myholder);
            } else {
                myholder = (Myholder) row.getTag();
            }
            OptionsItems optionsItems = (OptionsItems) getItem(position);
            myholder.option.setText(optionsItems.getOption());
            return row;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (tickTimer.isPlaying() == true) {
            tickTimer.pause();
        }
        if (gain.isPlaying() == true) {
            gain.pause();
        }
        if (lost.isPlaying() == true) {
            lost.pause();
        }
    }

    private void addAttemptedQue(String check, final String queId) {
        if (check.contentEquals("yes")) {
            attempted newAttempt = new attempted();
            newAttempt.setUsername(PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "username", "abc"));
            newAttempt.setUser_id(PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "userIdentity", "null"));
            newAttempt.setCorrect("yes");
            newAttempt.setPoint(Integer.valueOf(questionNo.getText().toString().split(" ")[0]));
            newAttempt.setQue_id(queId);
            newAttempt.setQue_type(topicName);

            Backendless.Data.of(attempted.class).save(newAttempt, new AsyncCallback<attempted>() {
                public void handleResponse(attempted savedContact) {
                    Date d1 = new Date();
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                    if (!db.checkAttemptedQueStatus(PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "userIdentity", "null") + "_" + queId))
                        db.addAttemptedQue(dateformat.format(d1), PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "userIdentity", "null") + "_" + queId, queId, PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "userIdentity", "null"), topicName, PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "username", "abc"), Integer.valueOf(questionNo.getText().toString().split(" ")[0]), "yes");
                    initialScore += Integer.valueOf(questionNo.getText().toString().split(" ")[0]);
                    initButtons("visible");
                    setAddScore("+" + String.valueOf(initialScore) + " pts");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    //Toast.makeText(getActivity().getBaseContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            attempted newAttempt = new attempted();
            newAttempt.setUsername(PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "username", "abc"));
            newAttempt.setUser_id(PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "userIdentity", "null"));
            newAttempt.setCorrect("no");
            newAttempt.setPoint(0);
            newAttempt.setQue_id(queId);
            newAttempt.setQue_type(topicName);

            Backendless.Data.of(attempted.class).save(newAttempt, new AsyncCallback<attempted>() {
                public void handleResponse(attempted savedContact) {
                    initButtons("visible");
                    Date d1 = new Date();
                    SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                    if (!db.checkAttemptedQueStatus(PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "userIdentity", "null") + "_" + queId))
                        db.addAttemptedQue(dateformat.format(d1), PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "userIdentity", "null") + "_" + queId, queId, PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "userIdentity", "null"), topicName, PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "username", "abc"), 0, "no");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    //Toast.makeText(getActivity().getBaseContext(), "Please connect to the internet!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public boolean checkinternetservice() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void setAddScore(String s) {
        addScore = (TextView) getActivity().findViewById(R.id.tv_total_score);
        addScoreFrame = (CardView) getActivity().findViewById(R.id.card_view2);
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
}
