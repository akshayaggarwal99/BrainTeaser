package in.co.appadda.brainteaser.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.Random;

import in.co.appadda.brainteaser.MaterialRippleLayout;
import in.co.appadda.brainteaser.R;

/**
 * Created by dewangankisslove on 08-07-2016.
 */
public class LeaderboardListAdapter extends RecyclerView
        .Adapter<LeaderboardListAdapter
        .DataObjectHolder> {
    private String[] rank;
    private String[] personName;
    private String[] personScore;
    private static MyClickListener myClickListener;
    Activity activity;
    int result;
    static int fixed = 0;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        ImageView setIcon;

        TextView setPersonName, setPersonScore;

        public DataObjectHolder(View itemView) {
            super(itemView);
            setPersonName = (TextView) itemView.findViewById(R.id.tv_username);
            setPersonScore = (TextView) itemView.findViewById(R.id.tv_userscore);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public LeaderboardListAdapter(Activity activity, String[] personName, String[] personScore, String[] rank) {
        this.rank = rank;
        this.personName = personName;
        this.personScore = personScore;
        this.activity = activity;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = MaterialRippleLayout.on(LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_list_object, parent, false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(Color.parseColor("#BDBDBD"))
                .rippleHover(true)
                .create();


        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.setPersonName.setText(personName[position]);
        holder.setPersonScore.setText(personScore[position]);
        Random rn = new Random();
        result = rn.nextInt(5) + 1;
        while (result == fixed) {
            result = rn.nextInt(5) + 1;
        }
        fixed = result;
        TextDrawable ic2;
        TextDrawable.IBuilder builder = TextDrawable.builder().beginConfig().endConfig().round();
        if (result == 1) {
            ic2 = builder.build(rank[position].charAt(0) + "", activity.getResources().getColor(R.color.color4));
        } else if (result == 2) {
            ic2 = builder.build(rank[position].charAt(0) + "", activity.getResources().getColor(R.color.color5));
        } else if (result == 3) {
            ic2 = builder.build(rank[position].charAt(0) + "", activity.getResources().getColor(R.color.color6));
        } else if (result == 4) {
            ic2 = builder.build(rank[position].charAt(0) + "", activity.getResources().getColor(R.color.color7));
        } else {
            ic2 = builder.build(rank[position].charAt(0) + "", activity.getResources().getColor(R.color.color8));
        }
    }

    @Override
    public int getItemCount() {
        return personName.length;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
