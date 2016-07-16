package in.co.appadda.brainteaser.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.backendless.BackendlessCollection;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import in.co.appadda.brainteaser.AnalyticsApplication;
import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.ConnectionDetector;
import in.co.appadda.brainteaser.data.api.model.DefaultCallback;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.aptitude;
import in.co.appadda.brainteaser.data.api.model.logical;
import in.co.appadda.brainteaser.data.api.model.puzzles;
import in.co.appadda.brainteaser.data.api.model.riddles;
import in.co.appadda.brainteaser.fragments.HomeFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    private Tracker mTracker;

    private static BackendlessCollection aptitudeCollection;
    private static BackendlessCollection LogicalCollection;
    private static BackendlessCollection PuzzleCollection;
    private static BackendlessCollection RiddleCollection;
    int id_aptitude;
    int id_logical;
    int id_puzzle;
    int id_riddle;

    public static BackendlessCollection getAptitudeCollection() {
        return aptitudeCollection;
    }

    public static BackendlessCollection getLogicalCollection() {
        return LogicalCollection;
    }

    public static BackendlessCollection getPuzzleCollection() {
        return PuzzleCollection;
    }

    public static BackendlessCollection getRiddleCollection() {
        return RiddleCollection;
    }


    DatabaseHandler db;

    ActionBarDrawerToggle actionBarDrawerToggle;
    private static final String TAG = "MainActivity";
    InterstitialAd mInterstitialAd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5317774062113347/4580027719");
        requestNewInterstitial();

//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                requestNewInterstitial();
//            }
//        });
//
//
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);


        //Initializing NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        AppRate.with(this)
                .setInstallDays(0) // default 10, 0 means install day.
                .setLaunchTimes(2) // default 10
                .setRemindInterval(1) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(MainActivity.class.getName(), Integer.toString(which));
                    }
                })
                .monitor();

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this);


        HomeFragment homeFragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction homefragmentTransaction = getSupportFragmentManager().beginTransaction();
        homefragmentTransaction.replace(R.id.nav_contentframe, homeFragment);
        homefragmentTransaction.commit();


        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                mDrawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        android.support.v4.app.FragmentTransaction homefragmentTransaction = getSupportFragmentManager().beginTransaction();
                        homefragmentTransaction.replace(R.id.nav_contentframe, homeFragment);
                        homefragmentTransaction.commit();
                        return true;
                    case R.id.update:
                        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

                        Boolean isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            PrefUtils.saveToPrefs(MainActivity.this, "skip_update", "TRUTH");
                            retrieveBasicAptitudeRecord();
                        } else {
                            Toast.makeText(getBaseContext(), "Check Internet Connection!", Toast.LENGTH_SHORT).show();
                        }
                        return true;

                    case R.id.share:
                        shareapp();
                        return true;
                    case R.id.rate:
                        rate();
                        return true;
                    case R.id.feedback:
                        sendEmail();
                        return true;
                    case R.id.about_us:
                        Intent i = new Intent(MainActivity.this, AboutUsActivity.class);
                        startActivity(i);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }

    public void cardClickHandler(View view) {
        int i = view.getId();
        switch (i) {
            case R.id.card_aptitude:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Aptitude_card")
                        .build());

                Intent openAptitude = new Intent(MainActivity.this, DisplayAptitudeSets.class);
                startActivity(openAptitude);
                break;
            case R.id.card_logical:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Logical_card")
                        .build());

                Intent openLogical = new Intent(MainActivity.this, DisplayLogicalSets.class);
                startActivity(openLogical);
                break;
            case R.id.card_puzzle:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Puzzle_card")
                        .build());

                Intent openPuzzle = new Intent(MainActivity.this, DisplayQue.class);
                openPuzzle.putExtra("openFragment", "openPuzzle");
                startActivity(openPuzzle);
                break;
            case R.id.card_riddle:
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Action")
                        .setAction("Riddle_card")
                        .build());

                Intent openRiddle = new Intent(MainActivity.this, DisplayQue.class);
                openRiddle.putExtra("openFragment", "openRiddle");
                startActivity(openRiddle);
                break;
            case R.id.card_leader:
                Intent openLeader = new Intent(MainActivity.this, Compete.class);
                startActivity(openLeader);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("CD23BA7D9F6BC822032F55C89322D663")
                .addTestDevice("65B3B02302CD96F147516FC1A5E65FA1")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void sendEmail() {

        Intent email = new Intent(Intent.ACTION_SEND);
        // prompts email clients only

        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"opensoftlabs@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Brain Teaser Feedback");
        email.putExtra(Intent.EXTRA_TEXT, "");
        email.setType("message/rfc822");

        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(email, "Choose an email client"));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "No email client installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void shareapp() {
        try {

            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
            File srcFile = new File(ai.publicSourceDir);
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/vnd.android.package-archive");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(srcFile));
            startActivity(Intent.createChooser(share, "Share via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rate() {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void retrieveBasicAptitudeRecord() {
        id_aptitude = Integer.parseInt(PrefUtils.getFromPrefs(MainActivity.this, "_id_aptitude", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setPageSize(40);
        query.setWhereClause("_id > " + id_aptitude);
        aptitude.findAsync(query, new DefaultCallback<BackendlessCollection<aptitude>>(MainActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<aptitude> response) {
                id_aptitude = id_aptitude + response.getData().size();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_aptitude);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(MainActivity.this, "_id_aptitude", ID);

                aptitudeCollection = response;

                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addAptitude();

                retrieveBasicPuzzlesRecord();


            }
        });
    }

    private void retrieveBasicPuzzlesRecord() {
        id_puzzle = Integer.parseInt(PrefUtils.getFromPrefs(MainActivity.this, "_id_puzzle", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery queryPuzzle = new BackendlessDataQuery();
        queryPuzzle.setQueryOptions(queryOptions);
        queryPuzzle.setPageSize(20);
        queryPuzzle.setWhereClause("_id > " + id_puzzle);
        puzzles.findAsync(queryPuzzle, new DefaultCallback<BackendlessCollection<puzzles>>(MainActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<puzzles> response) {
                super.handleResponse(response);
                id_puzzle = id_puzzle + response.getData().size();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_puzzle);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(MainActivity.this, "_id_puzzle", ID);
                PuzzleCollection = response;
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addPuzzles();

                retrieveBasicLogicalRecord();
            }
        });
    }

    private void retrieveBasicLogicalRecord() {
        id_logical = Integer.parseInt(PrefUtils.getFromPrefs(MainActivity.this, "_id_logical", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setPageSize(40);
        query.setWhereClause("_id > " + id_logical);
        logical.findAsync(query, new DefaultCallback<BackendlessCollection<logical>>(MainActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<logical> response) {
                super.handleResponse(response);
                id_logical = id_logical + response.getData().size();
                Log.d("logical", "" + id_logical);
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_logical);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(MainActivity.this, "_id_logical", ID);
                LogicalCollection = response;
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addLogical();

//                PrefUtils.saveToPrefs(MainActivity.this, "skip_update", "TRUE");
                retrieveBasicRiddleRecord();
            }
        });
    }

    private void retrieveBasicRiddleRecord() {
        id_riddle = Integer.parseInt(PrefUtils.getFromPrefs(MainActivity.this, "_id_riddle", "0"));
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addSortByOption("_id ASC");
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setQueryOptions(queryOptions);
        query.setPageSize(20);
        query.setWhereClause("_id > " + id_riddle);
        riddles.findAsync(query, new DefaultCallback<BackendlessCollection<riddles>>(MainActivity.this) {
            @Override
            public void handleResponse(BackendlessCollection<riddles> response) {
                super.handleResponse(response);
                id_riddle = id_riddle + response.getData().size();
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(id_riddle);
                String ID = sb.toString();
                PrefUtils.saveToPrefs(MainActivity.this, "_id_riddle", ID);
                RiddleCollection = response;
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addRiddle();

                finish();
                startActivity(getIntent());


            }
        });
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}