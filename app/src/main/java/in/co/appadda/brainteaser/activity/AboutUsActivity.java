package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import in.co.appadda.brainteaser.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author akshayaggarwal
 * @since 13-03-2016
 */
public class AboutUsActivity extends AppCompatActivity {

    ImageView facebook, twitter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        facebook = (ImageView) findViewById(R.id.iv_facebook);
        twitter = (ImageView) findViewById(R.id.iv_twitter);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.facebook.com/BrainTeaserrr/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://twitter.com/opensoftlabs";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
