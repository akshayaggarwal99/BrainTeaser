package in.co.appadda.brainteaser.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.co.appadda.brainteaser.R;

/**
 * Created by dewangankisslove on 29-02-2016.
 */
public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        final GestureDetector mGestureDetector = new GestureDetector(getActivity().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                return true;
//            }
//
//        });
//
//
//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
//                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
//
//
//                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
//                    int i = recyclerView.getChildAdapterPosition(child);
//                    return true;
//
//                }
//
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
    }

}
