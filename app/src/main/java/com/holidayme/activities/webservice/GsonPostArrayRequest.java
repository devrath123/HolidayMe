package com.holidayme.activities.webservice;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by santosh.patar on 30-07-2015.
 */
public class GsonPostArrayRequest<T> extends JsonRequest<JSONArray> {

    private final Gson gson;
    private JSONObject mRequestObject;
    private Response.Listener<JSONArray> mResponseListener;
    private final Map<String, String> headers;

    public GsonPostArrayRequest(Map<String, String> headers, String url, String body,Gson gson, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        super(Method.POST, url, body, responseListener, errorListener);
        this.headers=headers;
        this.gson=gson;
        mResponseListener = responseListener;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        mResponseListener.onResponse(response);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            try {

                return Response.success(new JSONArray(json),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (JSONException e) {
                return Response.error(new ParseError(e));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}