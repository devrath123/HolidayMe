package com.holidayme.managers;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by arshad.shaikh on 3/22/2017.
 */
public class GsonGetArrayRequest<T>  extends JsonRequest<JSONArray> {

    private final Gson gson;
    private JSONObject mRequestObject;
    private Response.Listener<JSONArray> mResponseListener;
    private final Map<String, String> headers;

    public GsonGetArrayRequest(Map<String, String> headers, String url, Gson gson, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {
        super(Method.GET,url,"",responseListener, errorListener);
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
