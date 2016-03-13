package in.co.appadda.brainteaser.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.co.appadda.brainteaser.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by dewangankisslove on 13-03-2016.
 */
public class QuestionExplanation extends AppCompatActivity {

    Button gotIt;
    String explain;
    TextView explanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_que_explanation);
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
}
