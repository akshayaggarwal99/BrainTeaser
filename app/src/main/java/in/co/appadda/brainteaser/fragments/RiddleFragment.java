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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import in.co.appadda.brainteaser.AnalyticsApplication;
import in.co.appadda.brainteaser.MaterialRippleLayout;
import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.activity.PuzzleQuesGridActivity;
import in.co.appadda.brainteaser.activity.QuestionExplanation;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;

/**
 * Created by dewangankisslove on 12-03-2016.
 */
public class RiddleFragment extends Fragment {
    private Tracker mTracker;
    private static final String TAG = "RiddleFragment";

    TextView question, questionNo;
    ImageView forward, backward;
    CardView queNoContainer;
    Button answer;
    int que_no = 0;
    DatabaseHandler db;
    private Cursor cursor;
    int totalRiddleQue;

    public static RiddleFragment newInstance(int set_no) {
        RiddleFragment riddleFragment = new RiddleFragment();
        Bundle b = new Bundle();
        b.putInt("someInt", set_no);
        riddleFragment.setArguments(b);

        return riddleFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        que_no = getArguments().getInt("someInt");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.riddle_layout, container, false);
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
        mTracker.setScreenName("riddle_fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]
    }

    private void initUI(View v) {
        question = (TextView) v.findViewById(R.id.tv_que_riddle);
        questionNo = (TextView) v.findViewById(R.id.tv_question_number_riddle);
        forward = (ImageView) v.findViewById(R.id.iv_forward);
        backward = (ImageView) v.findViewById(R.id.iv_backward);
        answer = (Button) v.findViewById(R.id.b_answer_riddle);
        queNoContainer = (CardView) v.findViewById(R.id.que_no_riddle);

        totalRiddleQue = Integer.parseInt(PrefUtils.getFromPrefs(getActivity(), "_id_riddle", "0"));

        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = cursor.getString(2);
                db.addRiddleStatusCount(que_no + 1);
                Intent explanation = new Intent(getActivity(), QuestionExplanation.class);
                explanation.putExtra("explain", msg);
                startActivity(explanation);
            }
        });

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
                intent.putExtra("FragName", "Riddle");
                intent.putExtra("cancel", que_no);
                startActivity(intent);
            }
        });

    }

    private void initViews() {
        db = new DatabaseHandler(getActivity());


        cursor = db.getRiddle(que_no + 1);

        if (db.checkRiddleStatus(que_no + 1)) {
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
        forward.setEnabled(que_no != totalRiddleQue - 1);
        if (que_no == 0) {
            backward.setVisibility(View.INVISIBLE);


        } else {
            backward.setVisibility(View.VISIBLE);
        }
        if (que_no == totalRiddleQue - 1) {
            forward.setVisibility(View.INVISIBLE);
        } else {
            forward.setVisibility(View.VISIBLE);
        }
    }

}


