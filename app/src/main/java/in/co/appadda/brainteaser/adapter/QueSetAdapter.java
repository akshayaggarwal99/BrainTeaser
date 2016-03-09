package in.co.appadda.brainteaser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.appadda.brainteaser.R;

/**
 * Created by dewangankisslove on 09-03-2016.
 */
public class QueSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Return a new holder instance
        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder();
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        // Get the data model based on position

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mContacts.size();
    }
}
