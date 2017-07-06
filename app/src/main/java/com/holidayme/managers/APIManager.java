package com.holidayme.managers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.webservice.GsonGetRequest;
import com.holidayme.activities.webservice.GsonPostArrayRequest;
import com.holidayme.activities.webservice.GsonPostRequest;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by devrath.rathee on 20-09-2016.
 */

public class APIManager<T> {

    GsonGetRequest<T> gsonGetRequest;
    GsonPostRequest<T> gsonPostRequest;
    GsonPostArrayRequest<T>gsonPostArrayRequest;
    GsonGetArrayRequest<T>gsonGetArrayRequest;


    public void getUserAccountInfo(String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classType, Context mContext) {
        gsonPostRequest = new GsonPostRequest(NetworkUtilities.getHeaders(mContext), url, "", classType, new Gson(), listener, errorListener);

        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(gsonPostRequest);

    }

    public void getNearByCity(String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {

        gsonGetRequest = new GsonGetRequest<T>(NetworkUtilities.getHeaders(mContext),
                url,
                classtype,
                listener,
                errorListener);

        gsonGetRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(gsonGetRequest);

    }


    public void getCityId(String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {

        gsonGetRequest = new GsonGetRequest<T>(NetworkUtilities.getHeaders(mContext),
                url,
                classtype,
                listener,
                errorListener);

        gsonGetRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(gsonGetRequest);

    }


    public void postHotelRates(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {


        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getHeaders(mContext),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);

        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(gsonPostRequest);
    }


    public void postHotelDetails(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {


        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getHeaders(mContext),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);


        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);
    }


    public void getTripAdviserDetails(String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {


        gsonGetRequest = new GsonGetRequest<T>(NetworkUtilities.getHeaders(mContext),
                url,
                classtype,
                listener,
                errorListener);

        gsonGetRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonGetRequest);
    }


    public void getHotelListInfo(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {


        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getHeaders(mContext),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);


        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);
    }

    public void postHotelRoomDetails(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {


        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getHeaders(mContext),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);

        Log.d("hotel room rate", url + " " + request);


        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);

    }


    public void postGenerateTemporaryHotelBooking(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {


        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getHeaders(mContext),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);

        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);

    }


    public void getBookingDetail(String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {


        gsonGetRequest = new GsonGetRequest<T>(NetworkUtilities.getSignatureHeader(Constant.GETBOOKINGDETAIL),
                url,
                classtype,
                listener,
                errorListener);

        gsonGetRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(gsonGetRequest);

    }


    public void getBookedHotelDetail(String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {


        gsonGetRequest = new GsonGetRequest<T>(NetworkUtilities.getSignatureHeader(Constant.GETHOTELBOOKDETAILMETHOD),
                url,
                classtype,
                listener,
                errorListener);

        gsonGetRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(gsonGetRequest);

    }


    public void getAreaByCityId(String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {

        gsonGetRequest = new GsonGetRequest<>(NetworkUtilities.getSignatureHeader(Constant.GETAREA),
                url,
                classtype,
                listener,
                errorListener);
        gsonGetRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(gsonGetRequest);

    }

    public void postSearchProperties(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {

        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getHeaders(mContext),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);


        Log.d("request", "" + request);
        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);
    }

    public void applyCouponCode(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {
        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getSignatureHeader(Constant.APPLYCOUPNCODE),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);


        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);

    }

    public void insertBookingDetails(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {
        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getSignatureHeader(Constant.INSERTBOOKINGDETAIL),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);

        Log.d("Insert Booking Request", request);

        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);

    }


    public void postBookingHistory(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {

        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getSignatureHeader(Constant.BOOKINGHISTORY),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);


        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);
    }

    public void postRequestCancellation(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {

        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getSignatureHeader(Constant.REQUEST_CANCELLATION_METHOD),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);


        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);
    }

    public void postSendEmailToUser(String url, String request, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {

        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getSignatureHeader(Constant.SEND_EMAIL_TO_USER_METHOD),
                url,
                request,
                classtype,
                new Gson(),
                listener,
                errorListener);


        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);
    }

    public void getawaysListing(String url, String request, Response.Listener listener, Response.ErrorListener errorListener,Class<T> classType, Context mContext) {

        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getStayCationHeader(),
                url,
                request,
                classType,
                new Gson(),
                listener,
                errorListener);

        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(mContext).add(gsonPostRequest);

    }


    public void packageDetails(String url, String request, Response.Listener listener, Response.ErrorListener errorListener,Class<T> classType,Context context)
    {

        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getStayCationHeader(),
                url,
                request,
                classType,
                new Gson(),
                listener,
                errorListener);

        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(context).add(gsonPostRequest);

    }

    public void amenitiesDetails(String url, String request, Response.Listener listener, Response.ErrorListener errorListener,Class<T> classType,Context context)
    {
        gsonPostRequest = new GsonPostRequest<T>(NetworkUtilities.getHeaders(context),
                url,
                request,
                classType,
                new Gson(),
                listener,
                errorListener);

        gsonPostRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(context).add(gsonPostRequest);
    }

    public void getAllocations(String url,String request,Response.Listener listener,Response.ErrorListener errorListener,Class<T> classType,Context context)
    {
        gsonPostArrayRequest = new GsonPostArrayRequest<>(NetworkUtilities.getStayCationHeader(),
                url,
                request,
                new Gson(),
                listener,
                errorListener);

        gsonPostArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(context).add(gsonPostArrayRequest);
    }


    public  void getGetawaysPackages(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener, Context context){

        gsonGetArrayRequest = new GsonGetArrayRequest<>(NetworkUtilities.getStayCationHeader(),
                url,
                new Gson(),
                listener,
                errorListener);

        gsonGetArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(context).add(gsonGetArrayRequest);

    }


    public void getFourSquare(String url,Response.Listener listener,Response.ErrorListener errorListener,Class<T> classType,Context context)
    {

        gsonGetRequest = new GsonGetRequest<T>(NetworkUtilities.getHeaders(context),
                url,
                classType,
                listener,
                errorListener);

        gsonGetRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        Volley.newRequestQueue(context).add(gsonGetRequest);
    }
    public void getGetawaysBookingConfirmationDetail(String url, Response.Listener<T> listener, Response.ErrorListener errorListener, Class<T> classtype, Context mContext) {


        gsonGetRequest = new GsonGetRequest<T>(NetworkUtilities.getStayCationHeader(),
                url,
                classtype,
                listener,
                errorListener);

        gsonGetRequest.setRetryPolicy(new DefaultRetryPolicy(
                NetworkUtilities.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(mContext).add(gsonGetRequest);

    }

}
