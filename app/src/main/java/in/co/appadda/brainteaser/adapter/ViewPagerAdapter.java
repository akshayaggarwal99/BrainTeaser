package in.co.appadda.brainteaser.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.co.appadda.brainteaser.fragments.DisplayQuestions;
import in.co.appadda.brainteaser.fragments.PuzzleFragment;

/**
 * Created by dewangankisslove on 08-03-2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{

    private Context _context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context=context;

    }
    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        switch(position){
            case 0:
                f= new DisplayQuestions();
                break;
            case 1:
                f= new PuzzleFragment();
                break;
        }
        return f;
    }
    @Override
    public int getCount() {
        return 2;
    }
}
