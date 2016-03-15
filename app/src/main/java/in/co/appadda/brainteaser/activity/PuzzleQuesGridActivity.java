package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.adapter.GridSpacingItemDecoration;
import in.co.appadda.brainteaser.adapter.QueSetGridAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PuzzleQuesGridActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    int spanCount = 4;
    int spacing = 50;
    boolean includeEdge = true;
    int totalPuzzleQue;
    String fragName;
    int cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_ques_grid);

        fragName = getIntent().getStringExtra("FragName");
        cancel = getIntent().getIntExtra("cancel", 1);


        mRecyclerView = (RecyclerView) findViewById(R.id.queSetListPuzzle);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        // specify an adapter (see also next example)
        mAdapter = new QueSetGridAdapter(getDataSet(), this);
        mRecyclerView.setAdapter(mAdapter);

    }

    private ArrayList<Integer> getDataSet() {
        DatabaseHandler db = new DatabaseHandler(this);

        int i;
        totalPuzzleQue = db.getPuzzleCount();

        ArrayList results = new ArrayList<Integer>();
        for (int index = 0; index < totalPuzzleQue; index++) {
            i = index + 1;

            results.add(index, i);
        }
        return results;
    }

    public void onPuzzleQueClick(View view) {
        TextView textView = (TextView) view.findViewById(R.id.que_no);


        int i = Integer.parseInt(textView.getText().toString());

        Intent j = new Intent(PuzzleQuesGridActivity.this, DisplayQue.class);

        if (fragName.contentEquals("Puzzle")) {
            j.putExtra("openFragment", "openPuzzle");
            j.putExtra("que-no", i - 1);
            startActivity(j);
        } else if (fragName.contentEquals("Riddle")) {
            j.putExtra("openFragment", "openRiddle");
            j.putExtra("que_no", i - 1);
            startActivity(j);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PuzzleQuesGridActivity.this, DisplayQue.class);
        if (fragName.contentEquals("Puzzle")) {
            intent.putExtra("openFragment", "openPuzzle");
            intent.putExtra("que-no", cancel);
            startActivity(intent);
        } else if (fragName.contentEquals("Riddle")) {
            intent.putExtra("openFragment", "openRiddle");
            intent.putExtra("que_no", cancel);
            startActivity(intent);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
