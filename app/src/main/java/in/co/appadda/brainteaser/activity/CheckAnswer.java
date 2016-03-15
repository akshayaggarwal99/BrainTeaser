package in.co.appadda.brainteaser.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CheckAnswer extends AppCompatActivity {

    Button okay, explain;
    TextView user_check;
    DatabaseHandler db;
    Cursor cursor;
    int queNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_answer);
        okay = (Button) findViewById(R.id.okay);
        explain = (Button) findViewById(R.id.explain);
        user_check = (TextView) findViewById(R.id.tv_check_user);
        String check = getIntent().getStringExtra("userCheckStatus");
        queNo = getIntent().getIntExtra("queNo", 1);

        db = new DatabaseHandler(this);
        cursor = db.getPuzzle(queNo);

        if (check.contentEquals("Bravo ! Right Answer")) {
            user_check.setTextColor(Color.parseColor("#0edd15"));
            user_check.setText(check);
        } else {
            user_check.setTextColor(Color.parseColor("#ff291a"));
            okay.setText("Try Again");
            user_check.setText(check);
        }

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckAnswer.this, QuestionExplanation.class);
                i.putExtra("explain", cursor.getString(3));
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
