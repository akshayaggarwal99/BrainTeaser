package in.co.appadda.brainteaser.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.appadda.brainteaser.R;

/**
 * Created by dewangankisslove on 04-03-2016.
 */
public class PuzzleFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.puzzle_layout, container, false);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
