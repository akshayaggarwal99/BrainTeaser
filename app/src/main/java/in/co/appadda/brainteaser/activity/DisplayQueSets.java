package in.co.appadda.brainteaser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.QueSetAdapter;
import in.co.appadda.brainteaser.data.api.model.DividerItemDecoration;
import in.co.appadda.brainteaser.data.api.model.QuestionSets;

/**
 * Created by dewangankisslove on 09-03-2016.
 */
public class DisplayQueSets extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_set_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.queSetList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new QueSetAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL) {
                };
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((QueSetAdapter) mAdapter).setOnItemClickListener(new QueSetAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                TextView textView = (TextView) v.findViewById(R.id.tv_setNo);
                Intent openAptitude = new Intent(DisplayQueSets.this, DisplayQue.class);
                switch (textView.getText().toString()) {
                    case "Set 1":
                        openAptitude.putExtra("openFragment", "openRiddle");
                        break;
                }
                startActivity(openAptitude);
            }
        });
    }

    private ArrayList<QuestionSets> getDataSet() {
        ArrayList results = new ArrayList<QuestionSets>();
        for (int i = 0; i < 10; i++) {
            int j=i+1;
            QuestionSets obj = new QuestionSets("Set " + j,
                    "Total Questions 20", "You have Completed !");
            results.add(i, obj);
        }
        return results;
    }
}
