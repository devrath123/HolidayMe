package com.holidayme.activities.webservice;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.holidayme.common.LOGG;
import com.holidayme.common.Log;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by santosh.patar on 30-07-2015.
 */
public class GsonPostRequest<T> extends JsonRequest<T> {

    private final Gson gson;
    private final Class<T> clazz;
    private final Response.Listener<T> listener;
    private final Map<String, String> headers;


    public GsonPostRequest
            (Map<String, String> headers, String url, String body, Class<T> classtype, Gson gson,
             Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, body, listener, errorListener);
        this.gson = gson;
        clazz = classtype;
        this.listener = listener;
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }


    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T parsedObject = gson.fromJson(json, clazz);
            LOGG.d("response",""+json);
            return Response.success(parsedObject, HttpHeaderParser.parseCacheHeaders(response));


        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedException ", e.toString());
            return Response.error(new ParseError(e));


        } catch (JsonSyntaxException e) {
            Log.e("JsonSyntaxException ", e.toString());
            return Response.error(new ParseError(e));
        }

    }
}
