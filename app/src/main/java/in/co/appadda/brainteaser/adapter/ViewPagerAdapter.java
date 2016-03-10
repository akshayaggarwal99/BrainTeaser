package in.co.appadda.brainteaser.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.co.appadda.brainteaser.fragments.DisplayQuestions;

/**
 * Created by dewangankisslove on 08-03-2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int slideCount;
    private Context _context;

    public ViewPagerAdapter(Context context, int slideCount, FragmentManager fm) {
        super(fm);
        _context = context;
        this.slideCount = slideCount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = new DisplayQuestions();
        return f;
    }

    @Override
    public int getCount() {
        return slideCount;
    }
}
