package com.holidayme.activities.webservice;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by santosh.patar on 07-08-2015.
 */
public class GsonGetRequest<T> extends Request<T>
{
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Response.Listener<T> listener;
    private final Map<String, String> headers;


    public GsonGetRequest
    (Map<String, String> headers,String url,  Class<T> classtype,
     Response.Listener<T> listener, Response.ErrorListener errorListener)
    {
        super(Method.GET, url, errorListener);
        clazz = classtype;
        this.listener = listener;
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }


    @Override
    protected void deliverResponse(T response)
    {
        listener.onResponse(response);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response)
    {
        try
        {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            T parsedObject = gson.fromJson(json, clazz);
            return Response.success(parsedObject, HttpHeaderParser.parseCacheHeaders(response));

        }
        catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }
        catch (JsonSyntaxException e)
        {
            return Response.error(new ParseError(e));
        }

    }
}

