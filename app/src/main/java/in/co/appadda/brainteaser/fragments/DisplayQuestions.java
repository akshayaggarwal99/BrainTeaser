package in.co.appadda.brainteaser.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.property.ObjectProperty;

import java.util.ArrayList;
import java.util.List;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.activity.RetrieveRecordActivity;
import in.co.appadda.brainteaser.activity.Splash;
import in.co.appadda.brainteaser.adapter.OptionItemAdapter;
import in.co.appadda.brainteaser.adapter.OptionsItems;
import in.co.appadda.brainteaser.data.api.model.DataApplication;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import in.co.appadda.brainteaser.data.api.model.aptitude;

/**
 * Created by dewangankisslove on 02-03-2016.
 */
public class DisplayQuestions extends Fragment {

    private BackendlessCollection collection;

    private int currentPage;
    private int totalPages;

    private ArrayList<OptionsItems> optionsItemsArrayList = new ArrayList<OptionsItems>();
    private OptionItemAdapter adapter;
    ListView optionListView;
    TextView question;
    ImageView forward, backward;
    Button explanation;
    private String[] items;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.questions_layout, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        collection = Splash.getResultCollection();
        currentPage = 1;
        totalPages = (int) Math.ceil(((double) collection.getTotalObjects()) / collection.getCurrentPage().size());

        initUI();
        initViews();
        initButtons();

    }

    private void initUI() {
        optionListView = (ListView) getActivity().findViewById(R.id.lv_options);
        question = (TextView) getActivity().findViewById(R.id.tv_question);
        forward = (ImageView) getActivity().findViewById(R.id.iv_forward);
        backward = (ImageView) getActivity().findViewById(R.id.iv_backward);
        explanation = (Button) getActivity().findViewById(R.id.b_explanation);

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collection.nextPage(new DefaultCallback<BackendlessCollection>(getActivity().getApplicationContext()) {
                    @Override
                    public void handleResponse(BackendlessCollection response) {
                        currentPage++;
                        collection = response;
                        initViews();
                        initButtons();
                        super.handleResponse(response);
                    }
                });
            }
        });

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collection.previousPage(new DefaultCallback<BackendlessCollection>(getActivity().getApplicationContext()) {
                    @Override
                    public void handleResponse(BackendlessCollection response) {
                        currentPage--;
                        collection = response;
                        initViews();
                        initButtons();
                        super.handleResponse(response);
                    }
                });
            }
        });
    }

    private void initViews() {
        items = new String[5];

        items[0] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getQuestions());
        items[1] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getOption_one());
        items[2] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getOption_two());
        items[3] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getOption_three());
        items[4] = String.valueOf(((aptitude) collection.getCurrentPage().get(0)).getOption_four());

        question.setText(items[0]);

        String[] numbering = {"A)", "B)", "C)", "D)"};
        String[] options = {items[1], items[2], items[3], items[4]};
        for (int i = 0; i < 4; i++) {
            optionsItemsArrayList.add(new OptionsItems(numbering[i], options[i]));
        }
        adapter = new OptionItemAdapter(getActivity().getApplicationContext(), optionsItemsArrayList);
        optionListView.setAdapter(adapter);

    }

    private void initButtons() {
        forward.setEnabled(currentPage != totalPages);
        backward.setEnabled(currentPage != 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
