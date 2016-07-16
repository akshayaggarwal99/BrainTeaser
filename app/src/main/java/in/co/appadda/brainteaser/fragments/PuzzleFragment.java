package in.co.appadda.brainteaser.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import in.co.appadda.brainteaser.AnalyticsApplication;
import in.co.appadda.brainteaser.MaterialRippleLayout;
import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.activity.CheckAnswer;
import in.co.appadda.brainteaser.activity.PuzzleQuesGridActivity;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.OptionsItems;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;

/**
 * Created by dewangankisslove on 04-03-2016.
 */
public class PuzzleFragment extends Fragment {

    TextView question, questionNo;
    ImageView forward, backward;
    int que_no = 0;
    private Cursor cursor;
    Tracker mTracker;
    private static final String TAG = "PuzzleFragment";

    int totalPuzzleQue;
    CardView queNoContainer;
    EditText userAns;
    Button submitPuzzle;
    DatabaseHandler db;

    public static PuzzleFragment newInstance(int set_no) {
        PuzzleFragment puzzleFragment = new PuzzleFragment();
        Bundle b = new Bundle();
        b.putInt("someInt", set_no);
        puzzleFragment.setArguments(b);

        return puzzleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        // Enable Advertising Features.
        que_no = getArguments().getInt("someInt");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.puzzle_layout, container, false);
        initUI(v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        totalPages = (int) Math.ceil(((double) collection.getTotalObjects()) / collection.getCurrentPage().size());
        initViews();
        initButtons();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();

        // [START screen_view_hit]
        Log.i(TAG, "Setting screen name: ");
        mTracker.setScreenName("puzzle_fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]

    }

    private void initUI(View v) {
        question = (TextView) v.findViewById(R.id.tv_puzzle_que);
        questionNo = (TextView) v.findViewById(R.id.tv_question_number_puzzle);
        forward = (ImageView) v.findViewById(R.id.iv_forward);
        backward = (ImageView) v.findViewById(R.id.iv_backward);
        queNoContainer = (CardView) v.findViewById(R.id.que_no_puzzle);
        userAns = (EditText) v.findViewById(R.id.user_ans_puzzle);
        submitPuzzle = (Button) v.findViewById(R.id.submit_puzzle);

        MaterialRippleLayout.on(submitPuzzle)
                .rippleOverlay(true)
                .rippleColor(Color.parseColor("#35ADCF"))
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();

        submitPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckAnswer.class);


                if (userAns.getText().toString().contentEquals(db.getPuzzle(que_no + 1).getString(2))) {
                    intent.putExtra("userCheckStatus", "Bravo ! Right Answer");
                    db.addPuzzleStatusCount(que_no + 1);
                    intent.putExtra("queNo", que_no + 1);
                    userAns.setText("");
                    startActivity(intent);

                } else if (userAns.getText().toString().contentEquals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Answer can't be blank", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra("userCheckStatus", "Oops ! Wrong Answer");
                    db.addPuzzleStatusCount(que_no + 1);
                    intent.putExtra("queNo", que_no + 1);
                    userAns.setText("");
                    startActivity(intent);

                }


            }

        });

        totalPuzzleQue = Integer.parseInt(PrefUtils.getFromPrefs(getActivity(), "_id_puzzle", "0"));

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                que_no++;
//                explanation.setAlpha(0);
//                explanation.setEnabled(false);
                initViews();
                initButtons();
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                explanation.setAlpha(0);
//                explanation.setEnabled(false);
                que_no--;
                initViews();
                initButtons();
            }
        });

        queNoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.saveToPrefs(getActivity(), "DESTROY", "destroy");
                Intent intent = new Intent(getActivity().getApplicationContext(), PuzzleQuesGridActivity.class);
                intent.putExtra("FragName", "Puzzle");
                intent.putExtra("cancel", que_no);
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        db = new DatabaseHandler(getActivity());


        cursor = db.getPuzzle(que_no + 1);

        if (db.checkPuzzleStatus(que_no + 1)) {
            queNoContainer.setCardBackgroundColor(Color.parseColor("#72CEE7"));
            questionNo.setTextColor(Color.parseColor("#ffffff"));
        } else {
            queNoContainer.setCardBackgroundColor(Color.parseColor("#ffffff"));
            questionNo.setTextColor(Color.parseColor("#72CEE7"));
        }

        StringBuilder sb = new StringBuilder();

        sb.append("");
        sb.append(que_no + 1);
        questionNo.setText(sb.toString());

        question.setText(cursor.getString(1));


    }

    private void initButtons() {
        backward.setEnabled(que_no != 0);
        forward.setEnabled(que_no != totalPuzzleQue - 1);

        if (que_no == 0) {
            backward.setVisibility(View.INVISIBLE);
        } else {
            backward.setVisibility(View.VISIBLE);
        }
        if (que_no == totalPuzzleQue - 1) {
            forward.setVisibility(View.INVISIBLE);
        } else {
            forward.setVisibility(View.VISIBLE);
        }
    }


}

