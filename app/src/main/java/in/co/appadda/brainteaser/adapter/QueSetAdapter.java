package in.co.appadda.brainteaser.adapter;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.appadda.brainteaser.MaterialRippleLayout;
import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.data.api.model.QuestionSets;

/**
 * Created by dewangankisslove on 09-03-2016.
 */
public class QueSetAdapter extends RecyclerView
        .Adapter<QueSetAdapter
        .DataObjectHolder> {
    private ArrayList<QuestionSets> mDataset;
    private static MyClickListener myClickListener;
    private int layoutR;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        ImageView setIcon;

        TextView setNo, setTotalQue, userSetStatus;

        public DataObjectHolder(View itemView) {
            super(itemView);
            setNo = (TextView) itemView.findViewById(R.id.tv_setNo);
            setTotalQue = (TextView) itemView.findViewById(R.id.total_que_set);
            userSetStatus = (TextView) itemView.findViewById(R.id.user_set_status);
            setIcon = (ImageView) itemView.findViewById(R.id.iv_setLogo);
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

    public QueSetAdapter(ArrayList<QuestionSets> myDataset, int layoutR) {
        mDataset = myDataset;
        this.layoutR = layoutR;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = MaterialRippleLayout.on(LayoutInflater.from(parent.getContext()).inflate(layoutR, parent, false))
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
        holder.setNo.setText(mDataset.get(position).getSetNo());
        holder.setTotalQue.setText(mDataset.get(position).getTotalNoQue());
        holder.userSetStatus.setText(mDataset.get(position).getUserStatus());
        holder.setIcon.setImageResource(mDataset.get(position).getImg());
    }

//    public void addItem(QuestionSets dataObj, int index) {
//        mDataset.add(dataObj);
//        notifyItemInserted(index);
//    }
//
//    public void deleteItem(int index) {
//        mDataset.remove(index);
//        notifyItemRemoved(index);
//    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
