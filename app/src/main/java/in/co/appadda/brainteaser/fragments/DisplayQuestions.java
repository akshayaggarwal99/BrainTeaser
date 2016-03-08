package in.co.appadda.brainteaser.fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
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
    int que_no = 0;
    Cursor cursor;


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
                optionsItemsArrayList.clear();
                initViews();
                initButtons();
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsItemsArrayList.clear();
                que_no--;
                initViews();
                initButtons();
            }
        });

    }

    private void initViews() {
        DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());


        cursor = db.getAptitude(que_no);

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(que_no + 1);
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
        backward.setEnabled(que_no != 0);
        forward.setEnabled(que_no != 9);
    }

    @Override
    public void onResume() {
        super.onResume();
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
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalMyholder.option.getText().toString().contentEquals(cursor.getString(6))) {
                        finalMyholder.right.setImageResource(R.drawable.check_right_animator);
                        ((Animatable) finalMyholder.right.getDrawable()).start();
                    }else {
                        finalMyholder.right.setImageResource(R.drawable.check_wrong_animator);
                        ((Animatable) finalMyholder.right.getDrawable()).start();
                    }

                }
            });

            return row;
        }
    }
}