package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.fragments.AptitudeFragment;
import in.co.appadda.brainteaser.fragments.LogicalFragment;
import in.co.appadda.brainteaser.fragments.PuzzleFragment;
import in.co.appadda.brainteaser.fragments.RiddleFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by dewangankisslove on 05-03-2016.
 */
public class DisplayQue extends AppCompatActivity {
    //    private ViewPagerAdapter viewPagerAdapter;
//    private ViewPager viewPager;
    String frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_que);

        frag = getIntent().getStringExtra("openFragment");
        int set_no_aptitude = getIntent().getIntExtra("showAptitudeQue", 1);
        int set_no_logical = getIntent().getIntExtra("showLogicalQue", 1);
        int que_no_puzzle = getIntent().getIntExtra("que-no", 0);
        int que_no_riddle = getIntent().getIntExtra("que_no", 0);


        switch (frag) {
            case "openPuzzle":
                Fragment puzzlefragment = PuzzleFragment.newInstance(que_no_puzzle);
                android.support.v4.app.FragmentTransaction puzzlefragmentTransaction = getSupportFragmentManager().beginTransaction();
                puzzlefragmentTransaction.replace(R.id.activity_display_questions, puzzlefragment);
                puzzlefragmentTransaction.commit();
                break;
            case "openRiddle":
                Fragment riddlefragment = RiddleFragment.newInstance(que_no_riddle);
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

    @Override
    protected void onPause() {
        super.onPause();
        String destroy = PrefUtils.getFromPrefs(this, "DESTROY", "D");


        if ((frag.contentEquals("openPuzzle")&&destroy.contentEquals("destroy")) || (frag.contentEquals("openRiddle")&&destroy.contentEquals("destroy"))){

                finish();
        }


    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        PrefUtils.saveToPrefs(this,"DESTROY","D");
//
//    }


    @Override
    protected void onStop() {
        super.onStop();
        PrefUtils.saveToPrefs(this,"DESTROY","D");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        PrefUtils.saveToPrefs(this,"DESTROY","D");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
