package in.co.appadda.brainteaser.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.ViewPagerAdapter;
import in.co.appadda.brainteaser.fragments.AptitudeFragment;
import in.co.appadda.brainteaser.fragments.LogicalFragment;
import in.co.appadda.brainteaser.fragments.PuzzleFragment;
import in.co.appadda.brainteaser.fragments.RiddleFragment;

/**
 * Created by dewangankisslove on 05-03-2016.
 */
public class DisplayQue extends AppCompatActivity {
//    private ViewPagerAdapter viewPagerAdapter;
//    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_que);

        String frag = getIntent().getStringExtra("openFragment");
        int set_no_aptitude = getIntent().getIntExtra("showAptitudeQue", 1);
        int set_no_logical = getIntent().getIntExtra("showLogicalQue", 1);

        switch (frag) {
            case "openPuzzle":
                PuzzleFragment puzzlefragment = new PuzzleFragment();
                android.support.v4.app.FragmentTransaction puzzlefragmentTransaction = getSupportFragmentManager().beginTransaction();
                puzzlefragmentTransaction.replace(R.id.activity_display_questions, puzzlefragment);
                puzzlefragmentTransaction.commit();
                break;
            case "openRiddle":
                RiddleFragment riddlefragment = new RiddleFragment();
                android.support.v4.app.FragmentTransaction riddlefragmentTransaction = getSupportFragmentManager().beginTransaction();
                riddlefragmentTransaction.replace(R.id.activity_display_questions, riddlefragment);
                riddlefragmentTransaction.commit();
                break;
            case "openAptitude":
                Fragment aptitudefragment = AptitudeFragment.newInstance(set_no_aptitude);
                android.support.v4.app.FragmentTransaction aptitudefragmentTransaction = getSupportFragmentManager().beginTransaction();
                aptitudefragmentTransaction.replace(R.id.activity_display_questions, aptitudefragment);
                aptitudefragmentTransaction.commit();
                break;
            case "openLogical":
                Fragment logicalfragment = LogicalFragment.newInstance(set_no_logical);
                android.support.v4.app.FragmentTransaction logicalfragmentTransaction = getSupportFragmentManager().beginTransaction();
                logicalfragmentTransaction.replace(R.id.activity_display_questions, logicalfragment);
                logicalfragmentTransaction.commit();
                break;
        }


//        viewPagerAdapter = new ViewPagerAdapter(this ,10, getSupportFragmentManager());
//        viewPager = (ViewPager) findViewById(R.id.activity_que_pager);
//        viewPager.setAdapter(viewPagerAdapter);


    }

}
