package com.holidayme.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.holidayme.AppInterface.AutoCompleteClickListener;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.activities.util.Utilities;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.AutoCompleteData;
import com.holidayme.data.Destination;
import com.holidayme.data.UserDTO;
import com.holidayme.request.AutoCompleteRequest;
import com.holidayme.response.CitySearchAutoCompletResponsde;
import com.holidayme.response.HotelSearchAutoCompletResponse;
import com.holidayme.widgets.CustomDialog;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaikh.salim on 3/11/2016.
 */
public class AutoCompleteSearchAdapter extends BaseAdapter implements Filterable {

    private ViewHolder viewHolder;
    private Context mContext;
    private List<AutoCompleteData> autoCompleteDataArrayList = new ArrayList<>();
    private List<AutoCompleteData> completeDataArrayList = new ArrayList<>();
    private CitySearchAutoCompletResponsde autoCompletResponsde = null;
    private HotelSearchAutoCompletResponse hotelSearchAutoCompletResponsde = null;
    private List<AutoCompleteData> autoCompleteDataList = null;
    private CharSequence mConstraint;
    private int latestCount = 0;
    private AutoCompleteClickListener autoCompleteClickListener;
    private  FragmentManager fragmentManager;

    public AutoCompleteSearchAdapter(Context context, Activity _Activity, AutoCompleteClickListener autoCompleteClickListener, FragmentManager fragmentManager) {
        mContext = context;
        mConstraint = null;
        this.autoCompleteClickListener = autoCompleteClickListener;
        this.fragmentManager=fragmentManager;
    }

    @Override
    public int getCount() {
        return autoCompleteDataArrayList.size();
    }

    @Override
    public AutoCompleteData getItem(int index) {
        if (autoCompleteDataArrayList.isEmpty())
            return null;
        return autoCompleteDataArrayList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.recentsearch_rowlayout, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.destination = (TextView) convertView.findViewById(R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)){
            HolidayMeFont.overrideFonts(mContext, viewHolder.title, Constant.NotoKufiArabic_Regular);
            HolidayMeFont.overrideFonts(mContext, viewHolder.destination, Constant.NotoKufiArabic_Regular);
        }else{
            HolidayMeFont.overrideFonts(mContext, viewHolder.destination, Constant.HelveticaNeueRoman);
            HolidayMeFont.overrideFonts(mContext, viewHolder.title, Constant.HelveticaNeueRoman);
        }

        AutoCompleteData autoCompleteData = getItem(position);
        if (autoCompleteData != null) {


            if (autoCompleteData.isCityHeader()) {
                viewHolder.title.setVisibility(View.VISIBLE);
                viewHolder.title.setText(mContext.getResources().getString(R.string.City));
                if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                    viewHolder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.city, 0);
                else
                    viewHolder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.city, 0, 0, 0);


            }
            else if(autoCompleteData.isAreaHeader()){


                viewHolder.title.setVisibility(View.VISIBLE);
                viewHolder.title.setText(mContext.getResources().getString(R.string.Area));
                if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                    viewHolder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.location2, 0);
                else
                    viewHolder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.location2, 0, 0, 0);


            }



            else if (autoCompleteData.isHotelHeader()) {
                viewHolder.title.setVisibility(View.VISIBLE);
                viewHolder.title.setText(mContext.getResources().getString(R.string.hotel));
                if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                    viewHolder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hotel2, 0);
                else
                    viewHolder.title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hotel2, 0, 0, 0);

            }

            else {
                viewHolder.title.setVisibility(View.GONE);

            }
            viewHolder.destination.setText(autoCompleteData.getDisplayName());
            viewHolder.destination.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (autoCompleteClickListener != null) {
                        AutoCompleteData autoCompleteData = getItem(position);
                        Destination destination = new Destination();
                        if (autoCompleteData.isHotel()) {
                            destination.setCategory("Hotel");
                            destination.setKey(Long.toString(autoCompleteData.getHotelId()));
                            destination.setIsHotel(true);
                            destination.setDestinationName(autoCompleteData.getDisplayName());
                        }

                        else if(autoCompleteData.isArea()){

                            destination.setCategory("Area");
                            destination.setKey(autoCompleteData.getCityId());
                            destination.setIsCity(true);
                            destination.setDestinationName(autoCompleteData.getTitle());

                        }

                        else {
                            destination.setCategory("City");
                            destination.setKey(autoCompleteData.getCityId());
                            destination.setIsCity(true);
                            destination.setDestinationName(autoCompleteData.getTitle());
                        }



                        autoCompleteClickListener.selectedItem(destination);
                    }
                }
            });
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new ItemFilter();
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() < 25 && constraint.length() > 2) {
                mConstraint = constraint;
                latestCount = constraint.length();
                autoCompleteDataArrayList = completeDataArrayList;
                List<AutoCompleteData> autoCompleteArrayList = autoCompleteSearch(mContext, constraint.toString(), latestCount);
                filterResults.values = autoCompleteArrayList;
                filterResults.count = autoCompleteArrayList.size();

            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results != null && constraint != null && constraint.length() > 2 && results.count > 0) {
                autoCompleteDataArrayList = (List<AutoCompleteData>) results.values;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<AutoCompleteData> autoCompleteSearch(Context context, String query, int count) {
        if (NetworkUtilities.isInternet(context)) {
            return autoSeasrchCityrequest(query, count);
        } else {

            Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable),context.getString(R.string.please_check_your_internet_connection),true,fragmentManager);
        }
        return null;
    }


    private List<AutoCompleteData> autoSeasrchCityrequest(String query, int count) {
        String url;
        autoCompleteDataList = new ArrayList<>();
        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            url = Constant.REGION_API_ENDPOINT + Constant.CITY_AUTO_COMPLETE_ARABIC_URL;
        } else {
            url = Constant.REGION_API_ENDPOINT + Constant.CITY_AUTO_COMPLETE_ENG_URL;
        }

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            URL url1 = new URL(url);
            conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", Constant.ContentType);
            conn.setRequestProperty("SessionId", Utilities.gettSharedPreference(mContext, Constant.SESSION_ID));
            conn.setRequestProperty("UserAgent", Constant.UserAgent);
            conn.setRequestProperty("IpAddress", NetworkUtilities.getIp());
            conn.setRequestProperty("UserTrackingId", Utilities.gettSharedPreference(mContext, Constant.USER_TRACKING_ID));
            conn.setRequestProperty("Referrer", Constant.Referrer);
            conn.setRequestProperty("AppToken", Constant.ApplicationToken_new);
            if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
            conn.setRequestProperty("AffiliateId", Constant.AffilateId_ar);
            else
                conn.setRequestProperty("AffiliateId", Constant.AffilateId_en);

            conn.setRequestProperty("Accept", "application/json");

            AutoCompleteRequest request = new AutoCompleteRequest();
            request.setCityCount(5);
            request.setAreaCount(5);
            request.setKey(query);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(new Gson().toJson(request).toString().getBytes());
            outputStream.flush();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        autoCompletResponsde = new CitySearchAutoCompletResponsde();
        Gson gson = new Gson();
        try {
            autoCompletResponsde = gson.fromJson(jsonResults.toString(), CitySearchAutoCompletResponsde.class);
            if (autoCompletResponsde != null) {
                if (latestCount == count) {
                    if (autoCompletResponsde.getError() == null) {

                        int area=0 ;
                        if (autoCompletResponsde.getAutoComplete() != null) {



                            for(int i = 0; i < autoCompletResponsde.getAutoComplete().size(); i++){


                                if(autoCompletResponsde.getAutoComplete().get(i).getEntity()==5){

                                    area=i;
                                    break;
                                }

                            }


                            for (int i = 0; i < autoCompletResponsde.getAutoComplete().size(); i++) {


                                AutoCompleteData autoCompleteData=null;

                                if(autoCompletResponsde.getAutoComplete().get(i).getEntity()==4){


                                    autoCompleteData = new AutoCompleteData(Integer.toString(autoCompletResponsde.getAutoComplete().get(i).getId()), autoCompletResponsde.getAutoComplete().get(i).getDisplayName(), autoCompletResponsde.getAutoComplete().get(i).getTitle(), 0, false, true,false);
                                    if (i == 0) {

                                        autoCompleteData.setCityHeader(true);
                                    }
                                }

                                else if(autoCompletResponsde.getAutoComplete().get(i).getEntity()==5){

                                    autoCompleteData = new AutoCompleteData(Integer.toString(autoCompletResponsde.getAutoComplete().get(i).getId()), autoCompletResponsde.getAutoComplete().get(i).getDisplayName(), autoCompletResponsde.getAutoComplete().get(i).getTitle(), 0, false, false,true);

                                    if(area==i){
                                        autoCompleteData.setAreaHeader(true);

                                    }else{
                                        autoCompleteData.setAreaHeader(false);

                                    }

                                }

                                autoCompleteDataList.add(autoCompleteData);
                            }
                        }
                    }
                }

            }
        } catch (JsonSyntaxException e) {
            return autoSeasrchHotelrequest(mConstraint.toString(), count);
        }
        return autoSeasrchHotelrequest(mConstraint.toString(), count);
    }




    class ViewHolder {
        TextView title,destination;
    }


    private List<AutoCompleteData> autoSeasrchHotelrequest(String query, int count) {
        String url = Constant.API_URL + Constant.HOTEL_AUTO_COMPLETE_URL;
        String lang;

        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
            lang = Constant.ARABIC_LANGUAGE_CODE;
//            HolidayMeFont.overrideFonts(mContext, viewHolder.destination, Constant.NotoKufiArabic_Bold);
//            HolidayMeFont.overrideFonts(mContext, viewHolder.title, Constant.NotoKufiArabic_Bold);

        } else {
            lang = Constant.ENGLISH_LANGUAGE_CODE;
        }

        AutoCompleteRequest request = new AutoCompleteRequest();
        request.setCityCount(5);
        request.setHotelNameSearchKey(query);
        request.setLanguageCode(lang);

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            URL url1 = new URL(url);
            conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", Constant.ContentType);
            conn.setRequestProperty("SessionId", Utilities.gettSharedPreference(mContext, Constant.SESSION_ID));
            conn.setRequestProperty("UserAgent", Constant.UserAgent);
            conn.setRequestProperty("IpAddress", NetworkUtilities.getIp());
            conn.setRequestProperty("UserTrackingId", Utilities.gettSharedPreference(mContext, Constant.USER_TRACKING_ID));
            conn.setRequestProperty("Referrer", Constant.Referrer);
            conn.setRequestProperty("AppToken", Constant.ApplicationToken_new);
            if(UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE))
                conn.setRequestProperty("AffiliateId", Constant.AffilateId_ar);
            else
                conn.setRequestProperty("AffiliateId", Constant.AffilateId_en);
            conn.setRequestProperty("Accept", "application/json");
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
            if (mContext != null) {

            }
            return autoCompleteDataList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        hotelSearchAutoCompletResponsde = new HotelSearchAutoCompletResponse();
        Gson gson = new Gson();
        try {
            hotelSearchAutoCompletResponsde = gson.fromJson(jsonResults.toString(), HotelSearchAutoCompletResponse.class);
            if (hotelSearchAutoCompletResponsde != null) {
                if (latestCount == count) {
                    if (hotelSearchAutoCompletResponsde.getError() == null) {
                        if (hotelSearchAutoCompletResponsde.getHotelDetails() != null) {
                            for (int i = 0; i < hotelSearchAutoCompletResponsde.getHotelDetails().size(); i++) {
                                AutoCompleteData autoCompleteData = new AutoCompleteData(null, hotelSearchAutoCompletResponsde.getHotelDetails().get(i).getHotelName(), null , hotelSearchAutoCompletResponsde.getHotelDetails().get(i).getHotelId(), true, false,false);
                                if (i == 0) {
                                    autoCompleteData.setHotelHeader(true);
                                }
                                autoCompleteDataList.add(autoCompleteData);
                            }
                        }
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            // TODO Auto-generated catch block
            if (mContext != null) {

                final CustomDialog customeDailog = new CustomDialog(mContext, mContext.getString(R.string.app_name), mContext.getString(R.string.sorry_unable_to_process_your_request_please_try_after_some_time), mContext.getString(R.string.ok));
                customeDailog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                    @Override
                    public void dialogExitWithDone() {

                    }

                    @Override
                    public void dialogExitWithDismissOrCancel() {
                        customeDailog.dismiss();
                    }

                });
                customeDailog.show();
            }
            e.printStackTrace();

        }
        return autoCompleteDataList;


    }
}

