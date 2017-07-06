package com.holidayme.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.HotelDetail;
import com.holidayme.data.UserDTO;
import com.holidayme.request.AutoCompleteRequest;
import com.holidayme.response.AutoCompletResponsde;
import com.holidayme.response.HotelSearchAutoCompletResponse;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by santosh.patar on 21-08-2015.
 */
public class HotelTextSearchAutoCompletAdapter extends BaseAdapter implements Filterable {

    AutoCompletResponsde autoCompletResponsde = null;

    private String TAG = "HotelTextSearchAutoCompletAdapter";

    private long cityId;

    private static final int MAX_RESULTS = 10;
    private Context context;
    private List<HotelDetail> resultList = new ArrayList<HotelDetail>();
    FragmentManager fragmentManager;

    public HotelTextSearchAutoCompletAdapter(Context context, long cityId, FragmentManager fragmentManager) {
        this.context = context;
        this.cityId = cityId;
        this.fragmentManager =fragmentManager;
    }



    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public HotelDetail getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_input_text_hotel_search, parent, false);
        }


        //((TextView) convertView.findViewById(R.id.title)).setText(getItem(position).getTitle());

        ((TextView) convertView.findViewById(R.id.text2)).setText(getItem(position).getHotelName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null && constraint.length() < 25) {

                    List<HotelDetail> hotels = findHotes(context, constraint.toString());

                    // Assign the data to the FilterResults
                    if (hotels != null) {
                        filterResults.values = hotels;
                        filterResults.count = hotels.size();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<HotelDetail>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<HotelDetail> findHotes(Context context, String query) {
        if (NetworkUtilities.isInternet(context)) {
            return autoSeasrchrequest(query);
        } else {


            Utilities.commonErrorMessage(this.context, this.context.getString(R.string.Network_not_avilable), this.context.getString(R.string.please_check_your_internet_connection),false, fragmentManager);

        }
        return null;
    }

    private List<HotelDetail> autoSeasrchrequest(String query) {

        List<HotelDetail> list=new ArrayList<>();
        String url = Constant.API_URL + Constant.HOTELSEARCHMETHOD;
        AutoCompleteRequest request = new AutoCompleteRequest();
        request.setHotelNameSearchKey(query);
        request.setLanguageCode(UserDTO.getUserDTO().getLanguage());
        request.setCityId(cityId);

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            URL url1 = new URL(url);
            conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", Constant.ContentType);
            conn.setRequestProperty("SessionId",  Utilities.gettSharedPreference(context, Constant.SESSION_ID));
            conn.setRequestProperty("UserAgent", Constant.UserAgent);
            conn.setRequestProperty("IpAddress", NetworkUtilities.getIp());
            conn.setRequestProperty("UserTrackingId",  Utilities.gettSharedPreference(context, Constant.USER_TRACKING_ID));
            conn.setRequestProperty("Referrer", Constant.Referrer);
            conn.setRequestProperty("AppToken", Constant.ApplicationToken_new);
            if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                conn.setRequestProperty("AffiliateId", Constant.AffilateId_ar);
            else
                conn.setRequestProperty("AffiliateId", Constant.AffilateId_en);
            OutputStream os = conn.getOutputStream();
            os.write(new Gson().toJson(request).toString().getBytes());
            os.flush();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (Exception e) {
            if (context != null) {
            }
            return list;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        HotelSearchAutoCompletResponse autoCompletResponsde=null;
        Gson gson = new Gson();
        try {
            autoCompletResponsde = gson.fromJson(jsonResults.toString(), HotelSearchAutoCompletResponse.class);
            if (autoCompletResponsde != null) {
                list=autoCompletResponsde.getHotelDetails();
            }
        } catch (JsonSyntaxException e) {
            if (context != null) {


                Utilities.commonErrorMessage(context, context.getString(R.string.app_name), context.getString(R.string.sorry_unable_to_process_your_request_please_try_after_some_time),false, fragmentManager);

            }
            e.printStackTrace();
        }
        return list;
    }
}