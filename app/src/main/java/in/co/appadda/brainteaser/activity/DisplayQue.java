package in.co.appadda.brainteaser.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.data.api.model.aptitude;
import in.co.appadda.brainteaser.fragments.DisplayQuestions;
import in.co.appadda.brainteaser.fragments.PuzzleFragment;

/**
 * Created by dewangankisslove on 05-03-2016.
 */
public class DisplayQue extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_que);

        String frag = getIntent().getStringExtra("openFragment");

        switch (frag) {
            case "openAptitude":
                DisplayQuestions displayQuestions = new DisplayQuestions();
                android.support.v4.app.FragmentTransaction aptitudefragmentTransaction = getSupportFragmentManager().beginTransaction();
                aptitudefragmentTransaction.replace(R.id.activity_display_question, displayQuestions);
                aptitudefragmentTransaction.commit();
                break;
            case "openPuzzle":
                PuzzleFragment puzzleFragment = new PuzzleFragment();
                android.support.v4.app.FragmentTransaction puzzlefragmentTransaction = getSupportFragmentManager().beginTransaction();
                puzzlefragmentTransaction.replace(R.id.activity_display_question, puzzleFragment);
                puzzlefragmentTransaction.commit();
                break;
        }


    }
}
