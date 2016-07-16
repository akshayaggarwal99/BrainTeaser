package in.co.appadda.brainteaser.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

import in.co.appadda.brainteaser.AnalyticsApplication;
import in.co.appadda.brainteaser.MaterialRippleLayout;
import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.activity.QuestionExplanation;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.OptionsItems;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;

/**
 * Created by dewangankisslove on 12-03-2016.
 */
public class LogicalFragment extends Fragment {

    private ArrayList<OptionsItems> optionsItemsArrayList = new ArrayList<OptionsItems>();
    CardView queNoContainer;
    private OptionItemAdapter adapter;
    ListView optionListView;
    TextView question, questionNo;
    ImageView forward, backward;
    Button explanation;
    int que_no = 0;
    private List<String> cursor;
    int set_no;
    DatabaseHandler db;
    int set_que_no = 0;
    private Tracker mTracker;

    public static LogicalFragment newInstance(int set_no) {
        LogicalFragment logicalFragment = new LogicalFragment();
        Bundle b = new Bundle();
        b.putInt("someInt", set_no);
        logicalFragment.setArguments(b);

        return logicalFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        set_no = getArguments().getInt("someInt");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.questions_layout, container, false);
        initUI(v);


        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        totalPages = (int) Math.ceil(((double) collection.getTotalObjects()) / collection.getCurrentPage().size());
        que_no = Integer.parseInt(PrefUtils.getFromPrefs(getActivity().getApplicationContext(), "logical_no_" + set_no + "_que", "0"));
        initViews();
        initButtons();


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initUI(View v) {
        queNoContainer = (CardView) v.findViewById(R.id.card_view);
        optionListView = (ListView) v.findViewById(R.id.lv_options);
        question = (TextView) v.findViewById(R.id.tv_question);
        questionNo = (TextView) v.findViewById(R.id.tv_question_number);
        forward = (ImageView) v.findViewById(R.id.iv_forward);
        backward = (ImageView) v.findViewById(R.id.iv_backward);
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
                initButtons();
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explanation.setAlpha(0);
                explanation.setEnabled(false);
                optionsItemsArrayList.clear();
                que_no--;
                initViews();
                initButtons();
            }
        });

    }

    private void initViews() {
        db = new DatabaseHandler(getActivity());
        int totalnoofLogicalSets;
        totalnoofLogicalSets = db.getLogicalCount() / 20;
        for (int i = 1; i <= totalnoofLogicalSets; i++) {
            if (set_no == i) {
                set_que_no = que_no + (20 * (i - 1));
            }
        }

        cursor = db.getLogical(set_no, set_que_no + 1);

        if (db.checkLogicalStatus(set_que_no + 1, set_no)) {
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

        question.setText(cursor.get(0));

        String[] numbering = {"A)", "B)", "C)", "D)"};
        String[] options = {cursor.get(1), cursor.get(2), cursor.get(3), cursor.get(4)};
        for (int i = 0; i < 4; i++) {
            optionsItemsArrayList.add(new OptionsItems(numbering[i], options[i]));
        }
        adapter = new OptionItemAdapter(getActivity().getApplicationContext(), optionsItemsArrayList);
        optionListView.setAdapter(adapter);

        explanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = cursor.get(6);
                Intent explanation = new Intent(getActivity(), QuestionExplanation.class);
                explanation.putExtra("explain", msg);
                startActivity(explanation);
            }
        });


    }

    private void initButtons() {
        backward.setEnabled(que_no != 0);
        forward.setEnabled(que_no != 19);
        if (que_no == 0) {
            backward.setVisibility(View.INVISIBLE);
        } else {
            backward.setVisibility(View.VISIBLE);
        }
        if (que_no == 19) {
            forward.setVisibility(View.INVISIBLE);
        } else {
            forward.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("logical_fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        adapter.notifyDataSetChanged();
    }

    class OptionItemAdapter extends BaseAdapter {
        Context context;
        ArrayList<OptionsItems> optionList;


        public OptionItemAdapter(Context context, ArrayList<OptionsItems> optionList) {
            this.context = context;
            this.optionList = optionList;
        }

        private class Myholder {
            TextView number, option;
            ImageView right;

            Myholder(View view) {
                number = (TextView) view.findViewById(R.id.tv_numbering);
                option = (TextView) view.findViewById(R.id.tv_options);
                right = (ImageView) view.findViewById(R.id.iv_right);
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
                row = inflater.inflate(R.layout.options_row_layout, parent, false);
                myholder = new Myholder(row);
                row.setTag(myholder);
            } else {
                myholder = (Myholder) row.getTag();
            }
            OptionsItems optionsItems = (OptionsItems) getItem(position);
            myholder.number.setText(optionsItems.getNumber());
            myholder.option.setText(optionsItems.getOption());
            myholder.right.setImageResource(optionsItems.getRight());

            final Myholder finalMyholder = myholder;
            final View finalRow = row;

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalMyholder.option.getText().toString().contentEquals(cursor.get(5))) {
                        finalMyholder.right.setImageResource(R.drawable.check_right_animator);
                        ((Animatable) finalMyholder.right.getDrawable()).start();
                    } else {
                        finalMyholder.right.setImageResource(R.drawable.check_wrong_animator);
                        ((Animatable) finalMyholder.right.getDrawable()).start();

                    }
                    if (que_no < 19) {
                        PrefUtils.saveToPrefs(getActivity().getApplicationContext(), "logical_no_" + set_no + "_que", String.valueOf(que_no + 1));
                    }
                    if (que_no == 19) {
                        PrefUtils.saveToPrefs(getActivity().getApplicationContext(), "logical_no_" + set_no + "_que", "0");
                    }
                    db.addLogicalStatusCount(set_que_no + 1);
                    finalRow.setClickable(false);
                    explanation.setEnabled(true);
                    explanation.setAlpha(1);

                }
            });

            return row;
        }
    }
}
