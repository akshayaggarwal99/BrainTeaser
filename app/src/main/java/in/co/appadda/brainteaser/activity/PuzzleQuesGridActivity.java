package in.co.appadda.brainteaser.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.adapter.GridSpacingItemDecoration;
import in.co.appadda.brainteaser.adapter.QueSetGridAdapter;

public class PuzzleQuesGridActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int spanCount = 4;
    int spacing = 50;
    boolean includeEdge = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_ques_grid);


        mRecyclerView = (RecyclerView) findViewById(R.id.queSetListPuzzle);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this,4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        // specify an adapter (see also next example)
        mAdapter = new QueSetGridAdapter(getDataSet(),this);
        mRecyclerView.setAdapter(mAdapter);

    }
    private ArrayList<Integer> getDataSet() {
        DatabaseHandler db = new DatabaseHandler(this);
        int totalPuzzleQue;
        int i;
        totalPuzzleQue=db.getPuzzleCount();

        ArrayList results = new ArrayList<Integer>();
        for (int index = 0; index < totalPuzzleQue; index++) {
            i=index+1;

            results.add(index,i);
        }
        return results;
    }
}
