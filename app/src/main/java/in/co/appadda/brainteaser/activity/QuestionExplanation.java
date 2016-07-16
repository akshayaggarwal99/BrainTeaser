package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.analytics.Tracker;

import in.co.appadda.brainteaser.AnalyticsApplication;
import in.co.appadda.brainteaser.MaterialRippleLayout;
import in.co.appadda.brainteaser.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by dewangankisslove on 13-03-2016.
 */
public class QuestionExplanation extends AppCompatActivity {

    private Tracker mTracker;
    InterstitialAd mInterstitialAd;
    Button gotIt;
    String explain;
    TextView explanation;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_explanation);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5317774062113347/4580027719");
        requestNewInterstitial();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Explanation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        explain = getIntent().getStringExtra("explain");

        gotIt = (Button) findViewById(R.id.b_got_it);
        explanation = (TextView) findViewById(R.id.tv_explain);
        explanation.setText(explain);

        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("CD23BA7D9F6BC822032F55C89322D663")
                .addTestDevice("65B3B02302CD96F147516FC1A5E65FA1")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
