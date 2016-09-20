package in.co.appadda.brainteaser.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;

/**
 * Created by dewangankisslove on 08-07-2016.
 */
public class Assesment extends AppCompatActivity {
    LineChart mChart;
    DatabaseHandler db;
    List<String> dateScore = new ArrayList<String>();
    List<String> date = new ArrayList<String>();
    List<String> totalQue = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment);
        db = new DatabaseHandler(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mChart = (LineChart) findViewById(R.id.linechart);
        //mChart.setViewPortOffsets(0, 0, 0, 0);
        mChart.setBackgroundColor(Color.parseColor("#7F7F7F"));

        // no description text
        mChart.setDescription("Your Progress");

        // enable touch gestures
        //mChart.setTouchEnabled(true);

        // enable scaling and dragging
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(true);

        // add data
        for (int j = 0; j < db.getAttemptedQues().size(); j++) {
            if (date.contains(db.getAttemptedQues().get(j)[6])) {
                dateScore.set(date.indexOf(db.getAttemptedQues().get(j)[6]), String.valueOf(Integer.parseInt(dateScore.get(date.indexOf(db.getAttemptedQues().get(j)[6]))) + Integer.parseInt(db.getAttemptedQues().get(j)[4])));
                totalQue.set(date.indexOf(db.getAttemptedQues().get(j)[6]), String.valueOf(Integer.parseInt(totalQue.get(date.indexOf(db.getAttemptedQues().get(j)[6]))) + 1));
            } else {
                date.add(db.getAttemptedQues().get(j)[6]);
                dateScore.add(db.getAttemptedQues().get(j)[4]);
                totalQue.add("1");
            }
        }
        setData(date, dateScore, totalQue);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);

        // dont forget to refresh the drawing
        mChart.invalidate();
    }

    private void setData(List<String> eachDate, List<String> eachScore, List<String> eachNoQue) {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int k = 0; k < eachDate.size(); k++) {
            float dat = Float.parseFloat(eachDate.get(k).split("/")[0]);
            float datScore = Float.parseFloat(eachScore.get(k));
            float noOfQue = Float.parseFloat(eachNoQue.get(k));
            float yAxisData = datScore / noOfQue;
            yVals1.add(new Entry(dat, yAxisData));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            //set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            //set2.setValues(yVals2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "DataSet 1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
//            set1.setFillFormatter(new MyFillFormatter(0f));
//            set1.setDrawHorizontalHighlightIndicator(false);
//            set1.setVisible(false);
//            set1.setCircleHoleColor(Color.WHITE);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            //dataSets.add(set2);

            // create a data object with the datasets
            LineData data = new LineData(dataSets);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);

            List<ILineDataSet> sets = mChart.getData()
                    .getDataSets();

            for (ILineDataSet iSet : sets) {

                LineDataSet set = (LineDataSet) iSet;
                set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                set.setDrawFilled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(Assesment.this, Compete.class));
                PrefUtils.saveToPrefs(getApplicationContext(), "openCompete", "false");
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Assesment.this, Compete.class));
        PrefUtils.saveToPrefs(getApplicationContext(), "openCompete", "false");
        finish();
    }
}
