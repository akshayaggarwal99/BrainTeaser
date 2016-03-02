package in.co.appadda.brainteaser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.appadda.brainteaser.R;

/**
 * Created by dewangankisslove on 02-03-2016.
 */
public class OptionItemAdapter extends BaseAdapter {
    Context context;
    ArrayList<OptionsItems> optionList;


    public OptionItemAdapter(Context context, ArrayList<OptionsItems> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    private class Myholder {
        TextView number, option;
        ImageView right;

        Myholder(View view) {
            number = (TextView) view.findViewById(R.id.tv_numbering);
            option = (TextView) view.findViewById(R.id.tv_options);
            right = (ImageView) view.findViewById(R.id.iv_right);
        }
    }

    @Override
    public int getCount() {
        return optionList.size();
    }

    @Override
    public Object getItem(int position) {
        return optionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Myholder myholder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.options_row_layout, parent, false);
            myholder = new Myholder(row);
            row.setTag(myholder);
        } else {
            myholder = (Myholder) row.getTag();
        }
        OptionsItems optionsItems = (OptionsItems) getItem(position);
        myholder.number.setText(optionsItems.getNumber());
        myholder.option.setText(optionsItems.getOption());

        return row;
    }
}
