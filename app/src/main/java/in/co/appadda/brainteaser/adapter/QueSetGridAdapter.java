package in.co.appadda.brainteaser.adapter;

/**
 * Created by akshayaggarwal99 on 13-03-2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.co.appadda.brainteaser.R;
import in.co.appadda.brainteaser.activity.DisplayQue;
import in.co.appadda.brainteaser.data.api.model.puzzles;

public class QueSetGridAdapter extends RecyclerView.Adapter<QueSetGridAdapter.ViewHolder> {
    private ArrayList<Integer> mDataset;
    Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView que_no;

        public ViewHolder(View v) {
            super(v);
            que_no = (TextView) v.findViewById(R.id.que_no);
        }
    }




    // Provide a suitable constructor (depends on the kind of dataset)
    public QueSetGridAdapter(ArrayList<Integer> myDataset,Context context) {
        mDataset = myDataset;
        this.context= context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public QueSetGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_puzzle_que, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.que_no.setText(String.valueOf(mDataset.get(position)));
//        holder.que_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, DisplayQue.class);
//                i.putExtra("openFragment","openPuzzle");
//                i.putExtra("que-no",position);
//                context.startActivity(i);
//
//            }
//        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
