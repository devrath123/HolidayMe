package com.holidayme.staycationindex;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.holidayme.Constants.Constant;
import com.holidayme.NavigationDrawerMVP.NavigationDrawerFragment;
import com.holidayme.activities.HomeActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.UserDTO;
import com.holidayme.staycationListing_mvp.StaycationListFragment;
import com.holidayme.staycationbooking.GetawaysBookingFragment;
import com.holidayme.widgets.CustomeDailogWithTwoButtons;

import java.util.ArrayList;

/**
 * Created by arshad.shaikh on 2/17/2017.
 */

public class StayCationIndexFragment extends Fragment implements IStayCationIndexView{

    private View rootView;
    private Context context;
    @SuppressLint("StaticFieldLeak")
    public static Toolbar toolbar;
    private  TextView gatewayCountTextView,gatewayNearYouTextView;
    private RecyclerView gatewaysRecyclerView;
    private LocalBroadcastManager refreshFragmentBroadcastManager;
    private IntentFilter refreshFragmentFilter;
    private  StayCationIndexPresenter stayCationIndexPresenter;
    private Dialog spinningDialog;
    private UserDTO userDTO;
    private ArrayList<GetawaysPackages> packagesArrayList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showActionBar();
        context= getActivity();
        userDTO = UserDTO.getUserDTO();
        stayCationIndexPresenter=new StayCationIndexPresenter(this);
        spinningDialog = CustomProgressDialog.showProgressDialog(getActivity());
        refreshFragmentBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        refreshFragmentFilter = new IntentFilter(Constant.REFRESH_FRAGMENT);
        getPackagesData();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.staycation_index_page, container, false);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.notifySelecterChange();
        ((HomeActivity)getActivity()).bottomNavigationVisibility(View.VISIBLE);

        showActionBar();
        setDrawer();
        initUI();

        setLayoutManager();
        return rootView;
    }

    private void getPackagesData() {



        if (NetworkUtilities.isInternet(getActivity())) {
            showDialog();
            stayCationIndexPresenter.getPackagesCount(context, Constant.GetawaysEndPointUrl+Constant.GetGetawaysURL + "" + userDTO.getLanguage());
        }
        else{


            final CustomeDailogWithTwoButtons customeDailogWithTwoButtons = new CustomeDailogWithTwoButtons(getActivity(), getString(R.string.Network_not_avilable), getString(R.string.please_check_your_internet_connection), getString(R.string.cancel),getString(R.string.Retry));
            customeDailogWithTwoButtons.setDialogExitListener(new CustomeDailogWithTwoButtons.DialogExitListener() {
                @Override
                public void dialogExitWithDone() {
                    customeDailogWithTwoButtons.dismiss();
                    getPackagesData();

                }

                @Override
                public void dialogExitWithDismissOrCancel() {
                    customeDailogWithTwoButtons.dismiss();
                }
            });
            if (!customeDailogWithTwoButtons.isShowing())
                customeDailogWithTwoButtons.show();
          //  Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), false, getFragmentManager());

        }



    }

    private void showActionBar() {

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((TextView) (HomeActivity.toolbar.findViewById(R.id.toolbar_title))).setText(getString(R.string.getaways_title));

    }

    private void setDrawer() {

        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        LinearLayout languageLinearLayout = (LinearLayout) drawerLayout.findViewById(R.id.languageLinearLayout);
        View view = drawerLayout.findViewById(R.id.topBarView);
        if (languageLinearLayout != null) {
            view.setVisibility(View.VISIBLE);
            languageLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void setLayoutManager() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gatewaysRecyclerView.setLayoutManager(gridLayoutManager);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                position=position+1;
                return  position%3==0 ? 2 : 1;
            }
        });
    }



    private void initUI() {

        gatewaysRecyclerView= (RecyclerView) rootView.findViewById(R.id.gatewaysRecyclerView);
        ((TextView) (HomeActivity.toolbar.findViewById(R.id.toolbar_title))).setText(getResources().getString(R.string.getaways_title));

        gatewayCountTextView= (TextView) rootView.findViewById(R.id.gatewayCountTextView);
        gatewayNearYouTextView= (TextView) rootView.findViewById(R.id.gatewayNearYouTextView);

        if(packagesArrayList!=null)
            setAdapter(packagesArrayList);

    }

    @Override
    public void onResume() {
        super.onResume();

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        refreshFragmentBroadcastManager.registerReceiver(refreshFragmentReceiver, refreshFragmentFilter);

        ((HomeActivity)getActivity()).bottomNavigationVisibility(View.VISIBLE);

        showActionBar();


    }

    private final BroadcastReceiver refreshFragmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String hotelTitle = getString(R.string.hotel);
            String getawaysTitle=getString(R.string.getaways_title);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            ((TextView) (HomeActivity.gatewaysTextView.findViewById(R.id.gatewaysTextView))).setText(getawaysTitle);
            ((TextView) (HomeActivity.hotelsTextView.findViewById(R.id.hotelsTextView))).setText(hotelTitle);

            try {
                final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.detach(StayCationIndexFragment.this).attach(StayCationIndexFragment.this).commit();
                getPackagesData();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



    @Override
    public void onPause() {
        super.onPause();
        ((HomeActivity)getActivity()).bottomNavigationVisibility(View.GONE);
        refreshFragmentBroadcastManager.unregisterReceiver(refreshFragmentReceiver);
    }


    @Override
    public void showDialog() {

        if(spinningDialog!=null&&!spinningDialog.isShowing()) {
            spinningDialog.show();
        }
    }

    @Override
    public void dismissDialog() {

        if(spinningDialog!=null&&spinningDialog.isShowing())
            spinningDialog.dismiss();

    }

    @Override
    public void setGetawaysPackageCount(ArrayList<GetawaysPackages> packagesArrayList) {
          this.packagesArrayList=packagesArrayList;

        setAdapter(packagesArrayList);

    }

    @Override
    public void retryGetawaysPackageCount() {

        final CustomeDailogWithTwoButtons customeDailogWithTwoButtons = new CustomeDailogWithTwoButtons(getActivity(), getString(R.string.app_name), getString(R.string.common_error_msg), getString(R.string.cancel),getString(R.string.Retry));
        customeDailogWithTwoButtons.setDialogExitListener(new CustomeDailogWithTwoButtons.DialogExitListener() {
            @Override
            public void dialogExitWithDone() {
                customeDailogWithTwoButtons.dismiss();
               getPackagesData();

            }

            @Override
            public void dialogExitWithDismissOrCancel() {
                customeDailogWithTwoButtons.dismiss();
            }
        });
        if (!customeDailogWithTwoButtons.isShowing())
            customeDailogWithTwoButtons.show();

    }


    private void setAdapter(ArrayList<GetawaysPackages> packagesArrayList){


        int count=0;
        for (int i = packagesArrayList.size() - 1; i >= 0; i--) {
            if (packagesArrayList.get(i).getPackageCount().equalsIgnoreCase("0")) {
                packagesArrayList.remove(i);
            }
        }


        for(int i=0;i<packagesArrayList.size();i++){

            count= count+Integer.parseInt(packagesArrayList.get(i).getPackageCount());
        }


    //    gatewayCountTextView.setText(count+" "+context.getString(R.string.getaways_title) );

        if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)){

            gatewayCountTextView.setText(context.getString(R.string.getaways_actionbar_title)+" "+count );

        }
        else{


            gatewayCountTextView.setText(count+" "+context.getString(R.string.getaways_actionbar_title) );
        }


        gatewayNearYouTextView.setText(context.getString(R.string.near_you));

        GatewaysRecyclerViewAdapter gatewaysRecyclerViewAdapter = new GatewaysRecyclerViewAdapter(context, packagesArrayList);
        gatewaysRecyclerView.setAdapter(gatewaysRecyclerViewAdapter);
        gatewaysRecyclerViewAdapter.notifyDataSetChanged();

        gatewaysRecyclerViewAdapter.setOnItemClickListener(new GatewaysRecyclerViewAdapter.OnRowItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long cityId, String packageCount) {

                    Fragment fragment = new StaycationListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putLong("cityId", cityId);
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment, StaycationListFragment.class.getName());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();

            }
        });
    }
}
