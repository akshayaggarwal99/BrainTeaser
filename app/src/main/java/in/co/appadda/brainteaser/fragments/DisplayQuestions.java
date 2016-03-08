package in.co.appadda.brainteaser.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.adapter.OptionItemAdapter;
import in.co.appadda.brainteaser.adapter.OptionsItems;

/**
 * Created by dewangankisslove on 02-03-2016.
 */
public class DisplayQuestions extends Fragment {

//    private BackendlessCollection collection;
//
//    private int currentPage;
//    private int totalPages;
//    private String[] items;

    private ArrayList<OptionsItems> optionsItemsArrayList = new ArrayList<OptionsItems>();
    private OptionItemAdapter adapter;
    ListView optionListView;
    TextView question, questionNo;
    ImageView forward, backward;
    Button explanation;
    int que_no = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.questions_layout, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        collection = Splash.getResultCollection();
//        currentPage = 1;
//        totalPages = (int) Math.ceil(((double) collection.getTotalObjects()) / collection.getCurrentPage().size());

        initUI();
        initViews();
        initButtons();

    }

    private void initUI() {
        optionListView = (ListView) getActivity().findViewById(R.id.lv_options);
        question = (TextView) getActivity().findViewById(R.id.tv_question);
        questionNo = (TextView) getActivity().findViewById(R.id.tv_question_number);
        forward = (ImageView) getActivity().findViewById(R.id.iv_forward);
        backward = (ImageView) getActivity().findViewById(R.id.iv_backward);
        explanation = (Button) getActivity().findViewById(R.id.b_explanation);

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                que_no++;
                initViews();
                initButtons();
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                que_no--;
                initViews();
                initButtons();
            }
        });
    }

    private void initViews() {
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());


        Cursor cursor = db.getAptitude(que_no);

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(que_no);
        questionNo.setText(sb.toString());

        question.setText(cursor.getString(1));
//        items = new String[5];
//
//        items[0] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getQuestions());
//        items[1] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getOption_one());
//        items[2] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getOption_two());
//        items[3] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getOption_three());
//        items[4] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getOption_four());

//        question.setText(items[0]);
//
        String[] numbering = {"A)", "B)", "C)", "D)"};
        String[] options = {cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)};
        for (int i = 0; i < 4; i++) {
            optionsItemsArrayList.add(new OptionsItems(numbering[i], options[i]));
        }
        adapter = new OptionItemAdapter(getActivity().getApplicationContext(), optionsItemsArrayList);
        optionListView.setAdapter(adapter);

    }

    private void initButtons() {
//        forward.setEnabled(currentPage != totalPages);
        backward.setEnabled(questionNo.getText().toString() != "1");
        forward.setEnabled(questionNo.getText().toString() != "10");
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
