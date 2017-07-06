package com.holidayme.hotelsearch;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holidayme.activities.R;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.activities.util.RegistrationIntentService;
import com.holidayme.activities.util.Utilities;
import com.holidayme.data.Destination;
import com.holidayme.data.UserDTO;
import com.holidayme.fragments.HotelListingFragment;
import com.holidayme.hoteldetail_mvp.HotelDetailsTabFragment;
import com.holidayme.managers.APIManager;
import com.holidayme.response.GetDestinationForHotelResponse;
import com.holidayme.response.HotelDetailInfoResponse;
import com.holidayme.response.HotelListingInfoResponse;
import com.holidayme.response.HotelRatesResponse;
import com.holidayme.response.NewGetNearByCityResponse;
import com.holidayme.response.TripAdviserDataResponse;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by devrath.rathee on 19-09-2016.
 */

public class HotelSearchPresenter implements IUserLandingPresenter.IHotelSearchPresenter, IUserLandingPresenter.IAutoCompletePresenter {

    private IUserLandingView mView;

    public HotelSearchPresenter(IUserLandingView hotelSearchView) {
        mView = hotelSearchView;
    }

    @Override
    public void checkDeepLinking(Destination destination, FragmentTransaction fragmentTransaction) {


        Fragment fragment;
        if (destination.isCity()) {
            fragment = new HotelListingFragment();
            HotelListingFragment.scrollToTop = true;
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);

        } else {

            fragment = new HotelDetailsTabFragment();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack("MapViewFragment");

        }
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void pushCleverTapEvent(String event, Map<String, Object> action) {


        AppController.getInstance().getCleverTapInstance().data.pushGcmRegistrationId(RegistrationIntentService.token, true);
        AppController.getInstance().getCleverTapInstance().event.push(event, action);

    }


    /**
     * Get NearBy City
     **/

    // For AutoComplete Fragment to find nearby city
    @Override
    public void getNearByCity(String url, Context context) {
        new APIManager<NewGetNearByCityResponse>().getNearByCity(url, nearByCitySuccessListener(context), nearByCityErrorListener(context), NewGetNearByCityResponse.class, context);
    }


    public Response.Listener<NewGetNearByCityResponse> nearByCitySuccessListener(final Context context) {
        return new Response.Listener<NewGetNearByCityResponse>() {
            @Override
            public void onResponse(NewGetNearByCityResponse newGetNearByCityResponse) {


                ((IUserLandingView.IAutoCompleteView) mView).hideShowProgressBar(View.INVISIBLE);

                if (newGetNearByCityResponse.getError() != null || newGetNearByCityResponse.getCity().getTitle() == null)
                     Utilities.commonErrorMessage(context,context.getString(R.string.app_name),context.getString(R.string.city_not_found),false,null);

                else {
                    ArrayList<Destination> destinationArrayList = new ArrayList<>();

                    destinationArrayList.add(new Destination(Integer.toString(newGetNearByCityResponse.getCity().getId()), null,
                            "City", 0, 0, newGetNearByCityResponse.getCity().getTitle(), null, null,
                            null, UserDTO.getUserDTO().getLatitude(), UserDTO.getUserDTO().getLongitude(), true, false));

                    ((IUserLandingView.IAutoCompleteView) mView).setRecentSearchAdapter(destinationArrayList);
                }

            }
        };
    }

    public Response.ErrorListener nearByCityErrorListener(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ((IUserLandingView.IAutoCompleteView) mView).hideShowProgressBar(View.INVISIBLE);
                Utilities.commonErrorMessage(context,context.getString(R.string.app_name),context.getString(R.string.server_is_not_responding_please_try_after_some_time),false,null);
            }
        };
    }


    /**
     * Get CityId
     **/
    // For HotelFragment_new
    @Override
    public void getCityId(String url, Context context) {

        new APIManager<GetDestinationForHotelResponse>().getCityId(url, getCityIdSuccessListener(context), getHotelIndexError(context), GetDestinationForHotelResponse.class, context);

    }

    @Override
    public Response.Listener<GetDestinationForHotelResponse> getCityIdSuccessListener(final  Context context) {
        return new Response.Listener<GetDestinationForHotelResponse>() {
            @Override
            public void onResponse(GetDestinationForHotelResponse getDestinationForHotelResponse) {



                if (getDestinationForHotelResponse.getError() == null) {
                    ((IUserLandingView.IHotelSearchView) mView).setGetDestinationForHotelResponse(getDestinationForHotelResponse);
                } else {
                    ((IUserLandingView.IHotelSearchView) mView).dismissDialog();
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg), false, null);
                }
            }
        };
    }



    /**
     * Get Hotel Room Rates
     **/

    @Override
    public void getHotelRoomRate(String url, String request, Context context) {
        new APIManager<HotelRatesResponse>().postHotelDetails(url, request, getHotelRoomRateSuccessListener(context), getHotelIndexError(context), HotelRatesResponse.class, context);

    }

    @Override
    public Response.Listener<HotelRatesResponse> getHotelRoomRateSuccessListener(final Context context) {

        return new Response.Listener<HotelRatesResponse>() {
            @Override
            public void onResponse(HotelRatesResponse hotelRatesResponse) {



                if (hotelRatesResponse.getError() == null)
                    ((IUserLandingView.IHotelSearchView) mView).setHotelRateResponse(hotelRatesResponse);

                else {
                    ((IUserLandingView.IHotelSearchView) mView).dismissDialog();
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg), false, null);
                }
            }
        };
    }




    /**
     * Get Hotel Details
     **/

    @Override
    public void getHotelDetails(String url, String request, Context context) {
        new APIManager<HotelDetailInfoResponse>().postHotelDetails(url, request, getHotelDetailsSuccessListener(context), getHotelIndexError(context), HotelDetailInfoResponse.class, context);

    }

    @Override
    public Response.Listener<HotelDetailInfoResponse> getHotelDetailsSuccessListener(final Context context) {
        return new Response.Listener<HotelDetailInfoResponse>() {
            @Override
            public void onResponse(HotelDetailInfoResponse hotelDetailInfoResponse) {


                if (hotelDetailInfoResponse.getError() == null)
                    ((IUserLandingView.IHotelSearchView) mView).setHotelDetailResponse(hotelDetailInfoResponse);

                else {
                    ((IUserLandingView.IHotelSearchView) mView).dismissDialog();
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg), false, null);
                }
            }
        };
    }


    /**
     * Get TripAdvisor Details
     **/

    @Override
    public void getTripAdvisorDetails(String url, Context context) {
        new APIManager<TripAdviserDataResponse>().getTripAdviserDetails(url, getTripAdvisorDetailsSuccessListener(context), getHotelIndexError(context), TripAdviserDataResponse.class, context);
    }

    @Override
    public Response.Listener<TripAdviserDataResponse> getTripAdvisorDetailsSuccessListener(final Context context) {
        return new Response.Listener<TripAdviserDataResponse>() {
            @Override
            public void onResponse(TripAdviserDataResponse tripAdviosrDataResponse) {


                if (tripAdviosrDataResponse.getError() == null)
                    ((IUserLandingView.IHotelSearchView) mView).setTripAdvisorDetailResponse(tripAdviosrDataResponse);

                else {
                    ((IUserLandingView.IHotelSearchView) mView).dismissDialog();
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg), false, null);
                }
            }
        };
    }

    /**
     * Get Hotel List Info
     **/

    @Override
    public void getHotelListInfo(String url, String request, Context context) {


        new APIManager<HotelListingInfoResponse>().getHotelListInfo(url,request, getHotelListInfoSuccessListener(context), getHotelIndexError(context), HotelListingInfoResponse.class, context);

    }

    @Override
    public Response.Listener<HotelListingInfoResponse> getHotelListInfoSuccessListener(final Context context) {
        return new Response.Listener<HotelListingInfoResponse>() {
            @Override
            public void onResponse(HotelListingInfoResponse hotelListingInfoResponse) {


                try {

                    ((IUserLandingView.IHotelSearchView) mView).dismissDialog();

                    if (hotelListingInfoResponse.getError() == null)
                        ((IUserLandingView.IHotelSearchView) mView).setHotelListData(hotelListingInfoResponse);

                    else

                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.no_hotel_available),false,null);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public Response.ErrorListener getHotelIndexError(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ((IUserLandingView.IHotelSearchView) mView).dismissDialog();
                Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg),false,null);
            }
        };
    }


}
