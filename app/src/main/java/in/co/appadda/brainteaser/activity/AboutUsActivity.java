package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import in.co.appadda.brainteaser.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author akshayaggarwal
 * @since 13-03-2016
 */
public class AboutUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
