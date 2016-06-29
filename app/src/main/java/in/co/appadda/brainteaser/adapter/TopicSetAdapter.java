package in.co.appadda.brainteaser.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.co.appadda.brainteaser.MaterialRippleLayout;
import in.co.appadda.brainteaser.R;

/**
 * Created by dewangankisslove on 10-06-2016.
 */
public class TopicSetAdapter extends RecyclerView
        .Adapter<TopicSetAdapter
        .DataObjectHolder> {
    private String[] topics;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        ImageView topicIcon;
        TextView topicName;

        public DataObjectHolder(View itemView) {
            super(itemView);
            topicName = (TextView) itemView.findViewById(R.id.topics);
            topicIcon = (ImageView) itemView.findViewById(R.id.topic_img);
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

    public TopicSetAdapter(Activity activity, String[] topics) {
        this.topics = topics;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = MaterialRippleLayout.on(LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_object, parent, false))
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(Color.parseColor("#35ADCF"))
                .rippleHover(true)
                .create();


        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.topicName.setText(topics[position]);
    }

    @Override
    public int getItemCount() {
        return topics.length;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
