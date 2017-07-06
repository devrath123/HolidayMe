package com.holidayme.fragments;


import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.holidayme.activities.R;
import com.holidayme.managers.SharedPreferenceManager;

/**
 * Created by supriya.sakore on 21-10-2015.
 */
public class MyProfile extends BaseFragment {

    private View rootView;
    private TextView firstNameTextView, lastNameTextView, emailTextView, displayNameTextView, mobileNumberTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initialiseUI() {

        firstNameTextView = (TextView) rootView.findViewById(R.id.txt_first_name);
        lastNameTextView = (TextView) rootView.findViewById(R.id.txt_last_name);
        emailTextView = (TextView) rootView.findViewById(R.id.txt_email);
        displayNameTextView = (TextView) rootView.findViewById(R.id.txt_display_name);
        mobileNumberTextView = (TextView) rootView.findViewById(R.id.txt_mob_no);

        ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(getActivity().getString(R.string.nav_item_profile));
        rootView.findViewById(R.id.toolbarBackImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        setUserProfile();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.my_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        LinearLayout languageLinearLayout = (LinearLayout) drawerLayout.findViewById(R.id.languageLinearLayout);
        View view = drawerLayout.findViewById(R.id.topBarView);
        if (languageLinearLayout != null) {
            languageLinearLayout.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

        initialiseUI();
        return rootView;
    }


    public void setUserProfile() {

        firstNameTextView.setText(SharedPreferenceManager.getInstance(getActivity()).getPreference("FirstName",""));
        lastNameTextView.setText(SharedPreferenceManager.getInstance(getActivity()).getPreference("LastName",""));
        displayNameTextView.setText(SharedPreferenceManager.getInstance(getActivity()).getPreference("DisplayName",""));
        emailTextView.setText(SharedPreferenceManager.getInstance(getActivity()).getPreference("emailid",""));
        mobileNumberTextView.setText(SharedPreferenceManager.getInstance(getActivity()).getPreference("phoneNumber",""));

    }

    @Override
    public void initUIElements() {
    }
}
