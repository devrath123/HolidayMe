package com.holidayme.booking_mvp;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.data.UserDTO;
import com.holidayme.managers.APIManager;
import com.holidayme.response.ApplyCouponCodeResponse;
import com.holidayme.response.GetAreaByCityIdResponse;
import com.holidayme.response.HotelBookConfirmationResponse;
import com.holidayme.response.InsertBookingDetailResponse;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by devrath.rathee on 14-11-2016.
 */

public class BookingDetailsPresenter implements IBookingPresenter.IBookingDetailsPresenter {

    IBookingView.IBookingDetailsView mView;

    public BookingDetailsPresenter(IBookingView.IBookingDetailsView view) {
        mView = view;
    }

    @Override
    public void getAreaByCityId(String url, Context context) {
        new APIManager<GetAreaByCityIdResponse>().getAreaByCityId(url, getAreaByCityIdSuccessListener(context), commonErrorListener(context), GetAreaByCityIdResponse.class, context);
    }

    @Override
    public Response.Listener<GetAreaByCityIdResponse> getAreaByCityIdSuccessListener(final Context context) {
        return new Response.Listener<GetAreaByCityIdResponse>() {
            @Override
            public void onResponse(GetAreaByCityIdResponse getAreaByCityIdResponse) {
                mView.dismissDialog();
                if (getAreaByCityIdResponse.getError() == null) {
                    mView.setArea((String) getAreaByCityIdResponse.getCodCityAreas().keySet().toArray()[0],
                            getAreaByCityIdResponse.getCodCityAreas().get(getAreaByCityIdResponse.getCodCityAreas().keySet().toArray()[0]));
                } else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg), false, null);
            }
        };
    }

    @Override
    public void applyCouponCode(String url, String request, Context context) {
        new APIManager<ApplyCouponCodeResponse>().applyCouponCode(url, request, applyCouponCodeSuccessListener(context), commonErrorListener(context),
                ApplyCouponCodeResponse.class, context);
    }

    @Override
    public Response.Listener<ApplyCouponCodeResponse> applyCouponCodeSuccessListener(Context context) {
        return new Response.Listener<ApplyCouponCodeResponse>() {
            @Override
            public void onResponse(ApplyCouponCodeResponse applyCouponCodeResponse) {
                mView.dismissDialog();
                mView.setApplyCoupon(applyCouponCodeResponse);
            }
        };
    }


    @Override
    public void insertBookingDetails(String url, String request, Context context) {
        new APIManager<InsertBookingDetailResponse>().insertBookingDetails(url, request, insertBookingDetailsSuccessListener(context), commonErrorListener(context), InsertBookingDetailResponse.class, context);
    }

    @Override
    public Response.Listener<InsertBookingDetailResponse> insertBookingDetailsSuccessListener(final Context context) {
        return new Response.Listener<InsertBookingDetailResponse>() {
            @Override
            public void onResponse(InsertBookingDetailResponse insertBookingDetailResponse) {
                mView.dismissDialog();
                if (insertBookingDetailResponse.getError() == null) {

                    if (insertBookingDetailResponse.getPaymentGatewayPostRequest() != null) {
                        StringBuilder paymentResponseStringBuilder = new StringBuilder();
                        paymentResponseStringBuilder.append("<html><body onload=\"document.forms[0].submit()\">");
                        if (insertBookingDetailResponse.getPaymentGatewayPostRequest().getScriptToRender() != null)
                            paymentResponseStringBuilder.append(String.format("%s<form method=\"post\" action=\"%s\">", insertBookingDetailResponse.getPaymentGatewayPostRequest().getScriptToRender(), insertBookingDetailResponse.getPaymentGatewayPostRequest().getPostUrl()));
                        else
                            paymentResponseStringBuilder.append(String.format("<form method=\"post\" action=\"%s\">", insertBookingDetailResponse.getPaymentGatewayPostRequest().getPostUrl()));

                        Type type = new TypeToken<Map<String, String>>() {
                        }.getType();

                        Map<String, String> postDataHashMap = new Gson().fromJson(new Gson().toJson(insertBookingDetailResponse.getPaymentGatewayPostRequest().getPostData()), type);
                        for (Map.Entry<String, String> item : postDataHashMap.entrySet()) {
                            paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", item.getKey(), item.getValue()));
                        }

                        paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "ApplicationToken", Constant.ApplicationToken_new));

                        //added on 21-11-2016..by Supriya
                        paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "UserTrackingId", UserDTO.getUserDTO().getUserTrackingId()));
                        paymentResponseStringBuilder.append(String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\">\n", "SessionId", UserDTO.getUserDTO().getSessionId()));

                        paymentResponseStringBuilder.append("</form></body></html>");

                        mView.setInsertBookingDetailsResponse(paymentResponseStringBuilder);

                    } else
                        new APIManager<HotelBookConfirmationResponse>().getBookedHotelDetail(Constant.HOTEL_BOOKING_CONFORMATION_ENDPOINT + Constant.GETHOTELBOOKDETAILMETHOD + "?bookingId=" + insertBookingDetailResponse.getHolzooBookingId() + "&bookingPropertyId=" + insertBookingDetailResponse.getBookingPropertyId() + "&currencyCode=" + UserDTO.getUserDTO().getCurrency(), getBookedHotelDetailSuccessListener(context), commonErrorListener(context), HotelBookConfirmationResponse.class, context);


                } else
                    Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg), false, null);


            }
        };
    }


    @Override
    public Response.Listener<HotelBookConfirmationResponse> getBookedHotelDetailSuccessListener(Context context) {
        return new Response.Listener<HotelBookConfirmationResponse>() {
            @Override
            public void onResponse(HotelBookConfirmationResponse hotelBookConfirmationResponse) {
                mView.dismissDialog();
                if (hotelBookConfirmationResponse.getError() == null)
                    mView.setBookedHotelDetailsResponse(hotelBookConfirmationResponse);
            }
        };
    }

    @Override
    public Response.ErrorListener commonErrorListener(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.dismissDialog();
                Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.common_error_msg), false, null);

            }
        };
    }
}
