package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import in.co.appadda.brainteaser.R;

/**
 * Created by dewangankisslove on 19-07-2016.
 */
public class Feedback extends AppCompatActivity {

    //    ImageView imageview;
//    LinearLayout waiting;
    WebView web;

    public boolean checkinternetservice() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        web = (WebView) findViewById(R.id.webView3);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
//        waiting = (LinearLayout) findViewById(R.id.waiting);
//        imageview = (ImageView) findViewById(R.id.logo);
//        Animation myRotation = AnimationUtils.loadAnimation(this, R.anim.logorotateinf);
//        myRotation.setFillAfter(true);
//        imageview.startAnimation(myRotation);
        if (checkinternetservice())
            web.loadUrl("https://kisslove.typeform.com/to/d4i8k7");
        else {
            Toast.makeText(getBaseContext(), "Please connect to the Internet!", Toast.LENGTH_SHORT).show();
        }

        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }
}
