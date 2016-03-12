package in.co.appadda.brainteaser.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import in.co.appadda.brainteaser.fragments.AptitudeFragment;

/**
 * Created by dewangankisslove on 08-03-2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int slideCount;
    private Context context;

    public ViewPagerAdapter(Context context ,int slideCount, FragmentManager fm) {
        super(fm);
        this.slideCount = slideCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new AptitudeFragment();
    }

    @Override
    public int getCount() {
        return slideCount;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
