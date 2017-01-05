package com.xuenen.njxe.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {


    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }
}
