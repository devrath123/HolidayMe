package com.holidayme.fragments;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.holidayme.AppInterface.BackPressInterFace;
import com.holidayme.activities.HomeActivity;
import com.holidayme.activities.R;

/**
 * Created by shaikh.salim on 3/10/2016.
 */
public abstract class BaseFragment extends Fragment {
    public abstract void initUIElements();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUIElements();
    }

    public void hideActionBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        DrawerLayout mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

}
