package com.boha.lifecycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aubreyM on 2014/08/06.
 */
public class AFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saved) {
        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_sample, container,
                false);

        return view;
    }
    View view;
}
