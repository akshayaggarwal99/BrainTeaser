package in.co.appadda.brainteaser.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.appadda.brainteaser.data.api.model.OptionsItems;
import in.co.appadda.brainteaser.fragments.DisplayQuestions;
import in.co.appadda.brainteaser.fragments.PuzzleFragment;

/**
 * Created by dewangankisslove on 08-03-2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int slideCount;

    private Context _context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;

    }

    @Override
    public Fragment getItem(int position) {
        DisplayQuestions f = new DisplayQuestions();
        return f;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
