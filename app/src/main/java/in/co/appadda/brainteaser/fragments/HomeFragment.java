package in.co.appadda.brainteaser.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;

/**
 * Created by dewangankisslove on 29-02-2016.
 */
public class HomeFragment extends Fragment {

    TextView progress_aptitude_text, progress_logical_text, progress_puzzle_text, progress_riddle_text;
    ContentLoadingProgressBar progressBar_aptitude, progressBar_logical, progressBar_puzzle, progressBar_riddle;

    int userProgressAptitude, userProgressLogical, userProgressPuzzle, userProgressRiddle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(v);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void initUI(View v) {
        progress_aptitude_text = (TextView) v.findViewById(R.id.tv_aptitude_progress);
        progress_logical_text = (TextView) v.findViewById(R.id.tv_logical_progress);
        progress_puzzle_text = (TextView) v.findViewById(R.id.tv_puzzle_progress);
        progress_riddle_text = (TextView) v.findViewById(R.id.tv_riddle_progress);

        progressBar_aptitude = (ContentLoadingProgressBar) v.findViewById(R.id.progress_bar_aptitude);
        progressBar_logical = (ContentLoadingProgressBar) v.findViewById(R.id.progress_bar_logical);
        progressBar_puzzle = (ContentLoadingProgressBar) v.findViewById(R.id.progress_bar_puzzle);
        progressBar_riddle = (ContentLoadingProgressBar) v.findViewById(R.id.progress_bar_riddle);

    }

    private void initViews() {
        DatabaseHandler db = new DatabaseHandler(getActivity());

        userProgressAptitude = db.getAptitudeUserStatusCount();
        userProgressLogical = db.getLogicalUserStatusCount();
        userProgressPuzzle = db.getPuzzleUserStatusCount();
        userProgressRiddle = db.getRiddleUserStatusCount();

        progress_aptitude_text.setText("Completed: " + String.valueOf(userProgressAptitude) + " %");
        progress_logical_text.setText("Completed: " + String.valueOf(userProgressLogical) + " %");
        progress_puzzle_text.setText("Completed: " + String.valueOf(userProgressPuzzle) + " %");
        progress_riddle_text.setText("Completed: " + String.valueOf(userProgressRiddle) + " %");

        progressBar_aptitude.setProgress(db.getAptitudeUserStatusCount());
        progressBar_logical.setProgress(db.getLogicalUserStatusCount());
        progressBar_puzzle.setProgress(db.getPuzzleUserStatusCount());
        progressBar_riddle.setProgress(db.getRiddleUserStatusCount());

        progressBar_aptitude.show();
        progressBar_logical.show();
        progressBar_puzzle.show();
        progressBar_riddle.show();
    }

    @Override
    public void onResume() {
        super.onResume();

        initViews();

    }
}
