package in.co.appadda.brainteaser.adapter;

/**
 * Created by HP on 18-12-2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.appadda.brainteaser.R;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    List<HomeItems> mItems;

    public HomeItemAdapter() {
        super();
        mItems = new ArrayList<HomeItems>();
        HomeItems homeItems = new HomeItems();
        homeItems.setName("Logical Questions");
        homeItems.setThumbnail(R.drawable.think);
        homeItems.setnColor(R.color.colorAccent);
        homeItems.settColor(R.color.colorAccent);
        mItems.add(homeItems);

        homeItems = new HomeItems();
        homeItems.setName("Aptitude Questions");
        homeItems.setThumbnail(R.drawable.think);
        homeItems.setnColor(R.color.colorAccent);
        homeItems.settColor(R.color.colorAccent);
        mItems.add(homeItems);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_card_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        HomeItems homeItems = mItems.get(i);
        viewHolder.stateName.setText(homeItems.getName());
        viewHolder.imgThumbnail.setImageResource(homeItems.getThumbnail());
        viewHolder.stateName.setBackgroundColor(homeItems.getnColor());
        viewHolder.imgThumbnail.setBackgroundColor(homeItems.gettColor());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumbnail;
        public TextView stateName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            stateName = (TextView) itemView.findViewById(R.id.tv_cardview);
        }
    }


}
