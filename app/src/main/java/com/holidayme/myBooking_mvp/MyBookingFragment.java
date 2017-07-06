
package com.holidayme.myBooking_mvp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.HomeActivity;
import com.holidayme.activities.R;
import com.holidayme.activities.util.EndlessRecyclerOnScrollListener;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.MyBookingAdapter;
import com.holidayme.common.CustomProgressDialog;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.BookingListDto;
import com.holidayme.data.RequestCancellationDto;
import com.holidayme.data.RequestCancellationRequest;
import com.holidayme.data.SendEmailToUserRequest;
import com.holidayme.data.UserDTO;
import com.holidayme.fragments.BaseFragment;

import com.holidayme.fragments.HotelBookingConfirmationFragment;
import com.holidayme.managers.SharedPreferenceManager;
import com.holidayme.request.BookingHistoryRequest;
import com.holidayme.response.BookHistoryResponse;
import com.holidayme.response.HotelBookConfirmationResponse;
import com.holidayme.widgets.CustomeDailogWithTwoButtons;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 23-10-2015.
 */
public class MyBookingFragment extends BaseFragment implements com.holidayme.myBooking_mvp.IMyBookingView.IMyBooking {
    private Dialog spinningDialog;
    private RecyclerView bookingListRecyclerView;
    private boolean isMoreRecordsExist;
    private ArrayList<BookingListDto> bookingListDtoArrayList;
    private MyBookingAdapter myBookingAdapter;
    private View rootView;
    private com.holidayme.myBooking_mvp.MyBookingPresenter myBookingPresenter;
    private int loadMoreLoading = 2002, normalLoading = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookingListDtoArrayList = new ArrayList<>();

        spinningDialog = CustomProgressDialog.showProgressDialog(getActivity());

        myBookingPresenter = new MyBookingPresenter(this);

        getBookingData(1, normalLoading);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_booking, container, false);
        ((HomeActivity)getActivity()).bottomNavigationVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void initUIElements() {
        bookingListRecyclerView = (RecyclerView) rootView.findViewById(R.id.booking_list);
        ((TextView) rootView.findViewById(R.id.toolbarTitleTextView)).setText(getResources().getString(R.string.nav_item_booking));
        rootView.findViewById(R.id.toolbarBackImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        DrawerLayout mDrawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookingListRecyclerView.setLayoutManager(linearLayoutManager);

        bookingListRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                if (isMoreRecordsExist)
                    getBookingData(current_page, loadMoreLoading);
            }
            @Override
            public void onHide() {}

            @Override
            public void onShow() {}
        });

    }
   // get booking history call

    private void getBookingData(int pageNumber, int type) {
        if (NetworkUtilities.isInternet(getActivity())) {

            if (type == normalLoading)
                showDialog();

            BookingHistoryRequest bookingHistoryRequest = new BookingHistoryRequest();
            bookingHistoryRequest.setHolzooBookingId((long)0);
            bookingHistoryRequest.setPageSize(20);
            bookingHistoryRequest.setPageNumber(pageNumber);
            bookingHistoryRequest.setBookingStatusType(0);
            bookingHistoryRequest.setEmail(UserDTO.getUserDTO().getEmailID());
            bookingHistoryRequest.setAccountId(SharedPreferenceManager.getInstance(getActivity()).getIntegerPreference("AccountId",0));

            myBookingPresenter.getBookingHistory(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.BOOKINGHISTORY, new GsonBuilder().serializeNulls().create().toJson(bookingHistoryRequest), type, getActivity(), getFragmentManager());
        } else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());

    }



    @Override
    public void setBookingHistoryResponse(BookHistoryResponse bookingHistoryResponse, int type) {

        if (bookingHistoryResponse != null && bookingHistoryResponse.getBookingList() != null) {

            isMoreRecordsExist = bookingHistoryResponse.isMoreRecordsExist();
            for (BookingListDto bookingListDto : bookingHistoryResponse.getBookingList()) {
                if (bookingListDto.getBookingType() == 1)
                    bookingListDtoArrayList.add(bookingListDto);
            }
            if (type == normalLoading)
                setBookingList();
            else {
                if (myBookingAdapter != null)
                    myBookingAdapter.notifyDataSetChanged();

            }
        } else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.My_Booking), getActivity().getString(R.string.no_booking), true, getFragmentManager());

    }


    private void bookingConfirmationCall(int position){
        if (NetworkUtilities.isInternet(getActivity()))
            myBookingPresenter.getBookedHotelDetail(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.GETHOTELBOOKDETAILMETHOD + "?bookingId=" +bookingListDtoArrayList.get(position).getHolzooBookingId() + "&bookingPropertyId=" + bookingListDtoArrayList.get(position).getEntityBookingId() + "&currencyCode=" + UserDTO.getUserDTO().getCurrency(), getActivity(), getFragmentManager());
        else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.My_Booking), getActivity().getString(R.string.no_booking), true, getFragmentManager());
   }

    @Override
    public void setBookedHotelDetailResponse(HotelBookConfirmationResponse hotelBookConfirmationResponse) {

        Fragment bookingConfirmationFragment = new HotelBookingConfirmationFragment();
        Bundle bookingConfirmationFragmentBundle = new Bundle();
        bookingConfirmationFragmentBundle.putParcelable("hotelBookConfirmationResponse", hotelBookConfirmationResponse);
        bookingConfirmationFragmentBundle.putParcelable("RoomRate", hotelBookConfirmationResponse);
        bookingConfirmationFragmentBundle.putBoolean("IsMyBooking",true);
        bookingConfirmationFragment.setArguments(bookingConfirmationFragmentBundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container_body, bookingConfirmationFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

// booking cancellation call
    private void requestCancellationCall(int position){
        if (NetworkUtilities.isInternet(getActivity())) {
            RequestCancellationRequest requestCancellationRequest=new RequestCancellationRequest();
            requestCancellationRequest.setEmail(UserDTO.getUserDTO().getEmailID());
            requestCancellationRequest.setCrmIncident(new RequestCancellationDto(UserDTO.getUserDTO().getEmailID(),1,bookingListDtoArrayList.get(position).getHolzooBookingId()));
            Log.d("Request for email:",new GsonBuilder().serializeNulls().create().toJson(requestCancellationRequest));
            myBookingPresenter.bookingRequestCancellation(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.REQUEST_CANCELLATION_METHOD, new GsonBuilder().serializeNulls().create().toJson(requestCancellationRequest), getActivity(), getFragmentManager());
        } else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());

    }

    private  void sendEmailToUserCall(int position){
        if (NetworkUtilities.isInternet(getActivity())) {
            SendEmailToUserRequest sendEmailToUserRequest=new SendEmailToUserRequest();
            sendEmailToUserRequest.setUserId(UserDTO.getUserDTO().getEmailID());
            sendEmailToUserRequest.setBookingEntityId(bookingListDtoArrayList.get(position).getEntityBookingId());
            sendEmailToUserRequest.setEntityTypes(1);
            sendEmailToUserRequest.setLanguageCode(UserDTO.getUserDTO().getLanguage());
            sendEmailToUserRequest.setBookingId(bookingListDtoArrayList.get(position).getHolzooBookingId());
            sendEmailToUserRequest.setUserMobileNumber(SharedPreferenceManager.getInstance(getActivity()).getPreference("phoneNumber",""));
            Log.d("Request for email:",new GsonBuilder().serializeNulls().create().toJson(sendEmailToUserRequest));
            myBookingPresenter.sendEmailToUser(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.SEND_EMAIL_TO_USER_METHOD, new GsonBuilder().serializeNulls().create().toJson(sendEmailToUserRequest), getActivity(), getFragmentManager());
        } else
            Utilities.commonErrorMessage(getActivity(), getActivity().getString(R.string.Network_not_avilable), getActivity().getString(R.string.please_check_your_internet_connection), true, getFragmentManager());

    }

    //set booking list to adapter
    private void setBookingList() {
        IMyBookingView.IMyBookingViewClickListener myBookingViewClickListener=new IMyBookingView.IMyBookingViewClickListener() {
            @Override
            public void viewVoucherClickListener(int position) {
                bookingConfirmationCall(position);
            }

            @Override
            public void sendVoucherClickListener(int position) {
                sendEmailToUserCall(position);
            }

            @Override
            public void requestCancellationClickListener(final int position) {
                final CustomeDailogWithTwoButtons customeDailogWithTwoButtons = new CustomeDailogWithTwoButtons(getActivity(), getString(R.string.app_name), getString(R.string.request_cancellation_massage), getString(R.string.No), getString(R.string.yes));
                customeDailogWithTwoButtons.setDialogExitListener(new CustomeDailogWithTwoButtons.DialogExitListener() {
                    @Override
                    public void dialogExitWithDone() {
                        requestCancellationCall(position);

                    }

                    @Override
                    public void dialogExitWithDismissOrCancel() {
                        customeDailogWithTwoButtons.dismiss();
                    }
                });
                customeDailogWithTwoButtons.show();
            }
        };
        myBookingAdapter = new MyBookingAdapter(bookingListDtoArrayList, getActivity(),myBookingViewClickListener);
        bookingListRecyclerView.setAdapter(myBookingAdapter);
        myBookingAdapter.notifyDataSetChanged();
    }


    @Override
    public void dismissDialog() {
        if (spinningDialog != null && spinningDialog.isShowing())
            spinningDialog.dismiss();
    }

    @Override
    public void showDialog() {
        if (spinningDialog != null && !spinningDialog.isShowing())
            spinningDialog.show();
    }




}
