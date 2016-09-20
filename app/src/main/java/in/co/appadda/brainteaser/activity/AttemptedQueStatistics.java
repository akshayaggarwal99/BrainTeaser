package in.co.appadda.brainteaser.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.adapter.DatabaseHandler;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.leaderboard;

/**
 * Created by dewangankisslove on 25-07-2016.
 */
public class AttemptedQueStatistics extends AppCompatActivity {
    RelativeLayout emptyScreen;
    TextView totalCorrectAns, bestArea, worstArea;
    DatabaseHandler db;
    int back = 0;

    private PieChart mChart, mChart2;
    String[] mParties = {"Problem Solving", "Data Sufficiency", "Pattern Recognization", "Number Series", "Others"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attempt_statistics);
        db = new DatabaseHandler(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        emptyScreen = (RelativeLayout) findViewById(R.id.rl_nodata);
        totalCorrectAns = (TextView) findViewById(R.id.tv_correct_ans);
        bestArea = (TextView) findViewById(R.id.tv_best_area);
        worstArea = (TextView) findViewById(R.id.tv_worst_area);

        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText("Attempted\nquestions\nstatistics");

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        mChart2 = (PieChart) findViewById(R.id.chart2);
        mChart2.setUsePercentValues(true);
        mChart2.setDescription("");
        mChart2.setExtraOffsets(5, 10, 5, 5);

        mChart2.setDragDecelerationFrictionCoef(0.95f);

        mChart2.setCenterText("Total\nattempted\nquestions");

        mChart2.setDrawHoleEnabled(true);
        mChart2.setHoleColor(Color.WHITE);

        mChart2.setTransparentCircleColor(Color.WHITE);
        mChart2.setTransparentCircleAlpha(110);

        mChart2.setHoleRadius(58f);
        mChart2.setTransparentCircleRadius(61f);

        mChart2.setDrawCenterText(true);

        mChart2.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart2.setRotationEnabled(true);
        mChart2.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        retrieveLeaderboard();

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);

        mChart2.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l2 = mChart2.getLegend();
        l2.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l2.setXEntrySpace(7f);
        l2.setYEntrySpace(0f);
        l2.setYOffset(0f);

        // entry label styling
        mChart2.setEntryLabelColor(Color.WHITE);
        mChart2.setEntryLabelTextSize(12f);
    }

    private void setDataOne(int count, List<String> noOfQue, List<String> topics) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry(Float.parseFloat(noOfQue.get(i)), topics.get(i)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private void setDataTwo(int rightAnswer, int AttemptedQue) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry((float) rightAnswer, "No. of given right answers"));
        entries.add(new PieEntry((float) AttemptedQue, "Total questions attempted"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart2.setData(data);

        // undo all highlights
        mChart2.highlightValues(null);

        mChart2.invalidate();
    }

    private void retrieveLeaderboard() {
        final BackendlessDataQuery query = new BackendlessDataQuery();
        query.setPageSize(1);
        String whereClau = "user_id = '" + PrefUtils.getFromPrefs(getApplicationContext(), "userIdentity", "null") + "'";
        query.setWhereClause(whereClau);
        leaderboard.findAsync(query, new AsyncCallback<BackendlessCollection<leaderboard>>() {
            @Override
            public void handleResponse(BackendlessCollection<leaderboard> leaderboardBackendlessCollection) {
                if (leaderboardBackendlessCollection.getData().size() != 0) {
                    List<String> topicName = new ArrayList<String>();
                    List<String> noOfQue = new ArrayList<String>();
                    List<String> rightAns = new ArrayList<String>();
                    List<Integer> cal = new ArrayList<Integer>();
                    emptyScreen.setVisibility(View.GONE);
                    totalCorrectAns.setText("You have given total right answer - " + leaderboardBackendlessCollection.getData().get(0).getTotal_right_ans().toString() + " out of " + leaderboardBackendlessCollection.getData().get(0).getTotal_attempted_que().toString());
                    bestArea.setText("Your best area is " + "" + ".");
                    for (int h = 0; h < leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";").length; h++) {
                        if (h > 0) {
                            int c = Integer.parseInt(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[2]) / Integer.parseInt(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[1]);
                            for (int g = topicName.size() - 1; g >= 0; g--) {
                                if (cal.get(g) < c) {
                                    String tempTopic = topicName.get(g);
                                    String tempNoQue = noOfQue.get(g);
                                    String tempRightAns = rightAns.get(g);
                                    int tempCal = cal.get(g);
                                    topicName.set(g, leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[0]);
                                    noOfQue.set(g, leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[1]);
                                    rightAns.set(g, leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[2]);
                                    cal.set(g, c);
                                    topicName.add(tempTopic);
                                    noOfQue.add(tempNoQue);
                                    rightAns.add(tempRightAns);
                                    cal.add(tempCal);
                                }else {
                                    topicName.add(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[0]);
                                    noOfQue.add(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[0]);
                                    rightAns.add(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[0]);
                                    //int v = Integer.parseInt(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[2]) / Integer.parseInt(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[1]);
                                    cal.add(c);
                                }
                            }
                        } else {
                            topicName.add(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[0]);
                            noOfQue.add(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[1]);
                            rightAns.add(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[2]);
                            int c = Integer.parseInt(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[2]) / Integer.parseInt(leaderboardBackendlessCollection.getData().get(0).getQue_type().split(";")[h].split(":")[1]);
                            cal.add(c);
                        }
                    }
                    setDataOne(topicName.size(), noOfQue, topicName);
                    setDataTwo(leaderboardBackendlessCollection.getData().get(0).getTotal_right_ans(), leaderboardBackendlessCollection.getData().get(0).getTotal_attempted_que());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    back = 1;
                } else {
                    emptyScreen.setVisibility(View.VISIBLE);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    back = 1;
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                back = 1;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (back == 1) {
            super.onBackPressed();
        }
    }
}
