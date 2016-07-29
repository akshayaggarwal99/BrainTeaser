package in.co.appadda.brainteaser.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.appadda.brainteaser.R;

/**
 * Created by dewangankisslove on 22-07-2016.
 */
public class UserGuide extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userguide, container, false);
        return view;
    }
}
