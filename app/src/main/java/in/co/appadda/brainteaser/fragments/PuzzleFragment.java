package in.co.appadda.brainteaser.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import in.co.appadda.brainteaser.AnalyticsApplication;
import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.OptionsItems;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        // Enable Advertising Features.
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
        Log.i(TAG, "Setting screen name: " );
        mTracker.setScreenName("puzzle_fragment" );
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // [END screen_view_hit]

    }

    private void initUI(View v) {
        question = (TextView) v.findViewById(R.id.tv_puzzle_que);
        questionNo = (TextView) v.findViewById(R.id.tv_question_number_puzzle);
        forward = (ImageView) v.findViewById(R.id.iv_forward);
        backward = (ImageView) v.findViewById(R.id.iv_backward);

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

    }

    private void initViews() {
        DatabaseHandler db = new DatabaseHandler(getActivity());


        cursor = db.getPuzzle(que_no+1);

        StringBuilder sb = new StringBuilder();

        sb.append("");
        sb.append(que_no + 1);
        questionNo.setText(sb.toString());

        question.setText(cursor.getString(1));


    }

    private void initButtons() {
        backward.setEnabled(que_no != 0);
        forward.setEnabled(que_no != 10);
    }

}
