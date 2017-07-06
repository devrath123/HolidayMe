package com.holidayme.staycationcustomcalender;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.GsonBuilder;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.staycation_calendar_mvp.StaycationCalendarActivity;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.StaycationAllocationAdapter;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.AllocationDTO;
import com.holidayme.data.CheckAvailabilityRequest;
import com.holidayme.data.UserDTO;
import com.holidayme.gtm.GTMAnalytics;
import com.holidayme.staycationbooking.GetawaysBookingFragment;
import com.holidayme.widgets.CustomDialog;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;


/**
 * Created by arshad.shaikh on 2/24/2017.
 */

public class DayDetailsAdapter extends RecyclerView.Adapter<DayDetailsAdapter.ViewHolder> {

    private ArrayList<DayDetails> dayDetailsArrayList = new ArrayList<>();
    private Context context;
    private OnItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private ArrayList<AllocationDTO> allocationDTOArrayList = new ArrayList<>();
    private String bookingPolicy, hotelTitle, hotelImageurl, packageId, subPackageId, checkInDate,
            checkOutDate, roomId, subPackage, packageName, cancellationPolicy;
    private int cutOffDays, selectedAllocation;
    private double totalBookingAmount, finalPriceDouble;
    private Dialog spinningDialog;
    private AlertDialog alertDialog;
    private GTMAnalytics gtmAnalytics;
   // private DecimalFormat formatter;
    private RelativeLayout bookNowRelativeLayout;
    //private NumberFormat numberFormat = NumberFormat.getInstance(new Locale("en","US"));

    public DayDetailsAdapter(Context context, int cutOffDays, ArrayList<DayDetails> dayDetailsArrayList, ArrayList<AllocationDTO> allocationDTOArrayList,
                             String bookingPolicy, String hotelTitle, String hotelImageUrl, String packageId, String subPackageId, String roomId,
                             String subPackage, GTMAnalytics gtmAnalytics, String packageName, String cancellationPolicy, Dialog spinningDialog) {

        this.context = context;
        this.cutOffDays = cutOffDays;
        this.dayDetailsArrayList = dayDetailsArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.allocationDTOArrayList = allocationDTOArrayList;
        this.bookingPolicy = bookingPolicy;
        this.hotelTitle = hotelTitle;
        this.hotelImageurl = hotelImageUrl;
        this.packageId = packageId;
        this.subPackageId = subPackageId;
        this.roomId = roomId;
        this.subPackage = subPackage;
        this.gtmAnalytics = gtmAnalytics;
        this.packageName = packageName;
        this.cancellationPolicy = cancellationPolicy;
        this.spinningDialog = spinningDialog;
       // formatter = new DecimalFormat("#,###,###.##");
       // numberFormat = NumberFormat.getInstance(new Locale("en","US"));

    }

    @Override
    public DayDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_details, parent, false);

        return new DayDetailsAdapter.ViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void unCheckBox() {
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final DayDetailsAdapter.ViewHolder holder, final int position) {

        if (dayDetailsArrayList.get(position).getPrice() > 0.0) {
            holder.priceTextView.setText(UserDTO.getUserDTO().getCurrency() + " " + String.format(Locale.US, "%.2f",dayDetailsArrayList.get(position).getPrice()));
        }

        for (AllocationDTO allocationDTO : allocationDTOArrayList) {
            if (allocationDTO.getAllocationDate().equals(dayDetailsArrayList.get(position).getAllocationDate())) {
                switch (allocationDTO.getAvailabilityStatus()) {

                    case 1:
                        holder.statusTextView.setText(context.getResources().getString(R.string.available));
                        dayDetailsArrayList.get(position).setAvailabilityStatus(1);
                        dayDetailsArrayList.get(position).setAllocations(allocationDTO.getAllocation());
                        holder.statusTextView.setBackgroundColor(context.getResources().getColor(R.color.tick_green));
                        holder.dayRowLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));

                        if (allocationDTO.getPrice() > 0) {
                            holder.priceTextView.setText(UserDTO.getUserDTO().getCurrency() + " " +  String.format(Locale.US, "%.2f",allocationDTO.getPrice()));
                            dayDetailsArrayList.get(position).setPrice(allocationDTO.getPrice());
                        }
                        break;

                    case 2:

                        //holder.statusTextView.setText(context.getResources().getString(R.string.available_on_request));
                        dayDetailsArrayList.get(position).setAvailabilityStatus(2);
                        dayDetailsArrayList.get(position).setAllocations(0);
                        holder.statusTextView.setBackgroundColor(context.getResources().getColor(R.color.light_orange));
                        holder.dayRowLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                        if (allocationDTO.getPrice() > 0) {
                            holder.priceTextView.setText(UserDTO.getUserDTO().getCurrency() + " " +  String.format(Locale.US, "%.2f",allocationDTO.getPrice()));
                            dayDetailsArrayList.get(position).setPrice(allocationDTO.getPrice());
                        }
                        break;

                    case 3:
                        holder.statusTextView.setText(context.getResources().getString(R.string.sold_out));
                        dayDetailsArrayList.get(position).setAvailabilityStatus(3);
                        dayDetailsArrayList.get(position).setAllocations(0);
                        holder.statusTextView.setBackgroundColor(context.getResources().getColor(R.color.dark_gray_text_color));
                        holder.dayRowLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.value_deal_bg));
                        break;

                }

            }

        }


        for (int i = 0; i < cutOffDays; i++) {
            if (position == i) {
                holder.statusTextView.setText(context.getResources().getString(R.string.available_on_request));
                dayDetailsArrayList.get(i).setAvailabilityStatus(2);
                dayDetailsArrayList.get(i).setAllocations(0);
                holder.statusTextView.setBackgroundColor(context.getResources().getColor(R.color.light_orange));
                holder.dayRowLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }

        holder.dateTextView.setText(dayDetailsArrayList.get(position).getDate());
        holder.dayNameTextView.setText(dayDetailsArrayList.get(position).getDayOfWeek());


        if(dayDetailsArrayList.get(position).getNightsCount() == 1)
            holder.nightsTextView.setText("1 " + context.getResources().getString(R.string.one_night));

        else if(dayDetailsArrayList.get(position).getNightsCount() == 2)
            holder.nightsTextView.setText("2 " + context.getResources().getString(R.string.two_night));

        else
            holder.nightsTextView.setText(dayDetailsArrayList.get(position).getNightsCount() + " " + context.getResources().getString(R.string.three_ten_night));


        for (int i = 0; i < dayDetailsArrayList.size(); i++)
            holder.dayCheckBox.setChecked(false);

        holder.dayCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.dayRowLinearLayout.performClick();
            }
        });

        holder.dayRowLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.dayCheckBox.setChecked(true);

                switch (dayDetailsArrayList.get(position).getAvailabilityStatus()) {

                    case 1:

                        final int nightsCount = dayDetailsArrayList.get(position).getNightsCount();

                        checkInDate = dayDetailsArrayList.get(position).getDayOfWeek() + ", " + dayDetailsArrayList.get(position).getDate() + " " + dayDetailsArrayList.get(position).getMonth() + " " + dayDetailsArrayList.get(position).getYear();
                        checkOutDate = dayDetailsArrayList.get(position + nightsCount).getDayOfWeek() + ", " + dayDetailsArrayList.get(position + nightsCount).getDate() + " " + dayDetailsArrayList.get(position + nightsCount).getMonth() + " " + dayDetailsArrayList.get(position + nightsCount).getYear();

                        gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Select Date " + subPackage, "Select Date " + subPackage);

                        int minimumAllocation = dayDetailsArrayList.get(position).getAllocations(), maxValue = position + (nightsCount - 1);

                        String clickedDays = "";
                        if (nightsCount > 1) {
                            for (int i = position; i <= maxValue; i++) {

                                if (dayDetailsArrayList.get(i).getAvailabilityStatus() == 2)
                                    clickedDays = clickedDays + dayDetailsArrayList.get(i).getMonth() + " " + dayDetailsArrayList.get(i).getDate() + ", " + dayDetailsArrayList.get(i).getYear() + " ";

                                for (int k = i + 1; k <= maxValue; k++) {
                                    if (dayDetailsArrayList.get(i).getAllocations() > dayDetailsArrayList.get(k).getAllocations())
                                        minimumAllocation = dayDetailsArrayList.get(k).getAllocations();

                                    else if (dayDetailsArrayList.get(i).getAllocations() < minimumAllocation)
                                        minimumAllocation = dayDetailsArrayList.get(i).getAllocations();

                                }
                            }
                        } else {
                            minimumAllocation = dayDetailsArrayList.get(position).getAllocations();

                           /* if (dayDetailsArrayList.get(position).getAllocations() > dayDetailsArrayList.get(position + 1).getAllocations())
                                minimumAllocation = dayDetailsArrayList.get(position + 1).getAllocations();

                            else
                                minimumAllocation = dayDetailsArrayList.get(position).getAllocations();*/
                        }

                        if (minimumAllocation > 0) {
                            View dialogView = layoutInflater.inflate(R.layout.calendar_dialog, null);
                            ((TextView) dialogView.findViewById(R.id.nightsDurationTextView)).setText(checkInDate + " - " + checkOutDate);

                            if(nightsCount == 1)
                                ((TextView) dialogView.findViewById(R.id.numberOfNightsTextView)).setText(nightsCount + " " + context.getResources().getString(R.string.one_night));


                            else if(nightsCount == 2)
                                ((TextView) dialogView.findViewById(R.id.numberOfNightsTextView)).setText(nightsCount + " " + context.getResources().getString(R.string.two_night));

                            else
                                ((TextView) dialogView.findViewById(R.id.numberOfNightsTextView)).setText(nightsCount + " " + context.getResources().getString(R.string.three_ten_night));

                            GridView allocationGridView = (GridView) dialogView.findViewById(R.id.allocationsGridView);
                            StaycationAllocationAdapter adapter = new StaycationAllocationAdapter(context, minimumAllocation);
                            allocationGridView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                            bookNowRelativeLayout = (RelativeLayout) dialogView.findViewById(R.id.bookNowRelativeLayout);
                            final TextView bookThisDealTextView = (TextView) dialogView.findViewById(R.id.bookDealTextView);
                            final TextView bookingAmountTextView = (TextView) dialogView.findViewById(R.id.totalBookingAmountTextView);

                            adapter.setSelectedAllocation(new StaycationAllocationAdapter.AllocationListener() {
                                @Override
                                public void onAllocationSelected(int currentPosition) {

                                    gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Select No. of Packages " + subPackage, "Select No. of Packages " + subPackage);
                                    selectedAllocation = currentPosition;
                                    String originalPrice = String.format(Locale.US, "%.2f", dayDetailsArrayList.get(position).getPrice());
                                    double singlePrice = Double.parseDouble(originalPrice);
                                    totalBookingAmount = singlePrice * currentPosition;
                                    String finalPrice = String.format(Locale.US, "%.2f", totalBookingAmount);
                                    finalPriceDouble = Double.parseDouble(finalPrice);
                                    bookThisDealTextView.setText(context.getResources().getString(R.string.book_this_deal_for) + " (" + UserDTO.getUserDTO().getCurrency() + " " + originalPrice + " X " + currentPosition + ")");
                                    bookingAmountTextView.setText(UserDTO.getUserDTO().getCurrency() + " " + finalPrice);
                                }
                            });


                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            alertDialog = alertDialogBuilder.create();
                            alertDialog.setView(dialogView);
                            alertDialog.show();

                            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    notifyDataSetChanged();
                                }
                            });

                            selectedAllocation = 1;
                            String originalPrice = String.format(Locale.US, "%.2f", dayDetailsArrayList.get(position).getPrice());
                            double singlePrice = Double.parseDouble(originalPrice);
                            totalBookingAmount = singlePrice * selectedAllocation;
                            finalPriceDouble = totalBookingAmount;

                            bookThisDealTextView.setText(context.getResources().getString(R.string.book_this_deal_for) + " (" + UserDTO.getUserDTO().getCurrency() + " " + originalPrice + " X " + selectedAllocation + ")");
                            bookingAmountTextView.setText(UserDTO.getUserDTO().getCurrency() + " " + totalBookingAmount);


                            bookNowRelativeLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    if (NetworkUtilities.isInternet(context)) {

                                        Log.i("Inside OnClick", "Book");
                                        bookNowRelativeLayout.setEnabled(false);

                                        gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Book this deal", "Book this deal");
                                        Bundle bundle = new Bundle();
                                        bundle.putString("Checkin", checkInDate);
                                        bundle.putString("Checkout", checkOutDate);
                                        bundle.putInt("nightcount", dayDetailsArrayList.get(position).getNightsCount());
                                        bundle.putString("booking policy", bookingPolicy);
                                        bundle.putDouble("amounts", finalPriceDouble);
                                        bundle.putString("hotelimageurl", hotelImageurl);
                                        bundle.putString("hoteltitle", hotelTitle);
                                        bundle.putString("packageid", packageId);
                                        bundle.putString("subpackageid", subPackageId);
                                        bundle.putString("cancellationPolicy",cancellationPolicy );
                                        bundle.putInt("selectedAllocation", selectedAllocation);
                                        bundle.putString("allocationDate", dayDetailsArrayList.get(position).getAllocationDate());
                                        bundle.putString("subPackage",subPackage);

                                        checkAvailabilityCall(bundle, position, position + nightsCount);
                                    } else
                                        Utilities.commonErrorMessage(context, context.getString(R.string.Network_not_avilable), context.getString(R.string.please_check_your_internet_connection), false, null);


                                }
                            });
                        } else {

                            final CustomDialog customDialog = new CustomDialog(context, context.getResources().getString(R.string.select_another_date), String.format(context.getResources().getString(R.string.hotel_rooms_are_unavailable), clickedDays), context.getString(R.string.ok));
                            customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
                                @Override
                                public void dialogExitWithDone() {

                                }

                                @Override
                                public void dialogExitWithDismissOrCancel() {
                                    notifyDataSetChanged();
                                    customDialog.dismiss();
                                    unCheckBox();

                                }
                            });
                            customDialog.show();


                            // Utilities.commonErrorMessage(context, "Select Another Dates","Unfortunately on "+ clickedDays + " hotel rooms are Unavailable. Kindly select another available dates", false, null);


                            //gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Request Form Popup", "Request Form Popup");
                            //((StaycationCalendarActivity) context).openRequestForm(dayDetailsArrayList.get(position));
                        }
                        break;

                    case 2:
                        gtmAnalytics.sendEvent("Getaway Details- " + packageName, "Request Form Popup", "Request Form Popup");
                        ((StaycationCalendarActivity) context).openRequestForm(dayDetailsArrayList.get(position));
                        break;

                    case 3:
                        holder.dayCheckBox.setChecked(false);
                        break;
                }

            }

        });

    }

    private ArrayList<String> getAllocationDates(int initialPos, int finalPos) {
        ArrayList<String> allocationArrayList = new ArrayList<>();
        for (int i = initialPos; i <= finalPos; i++) {
            allocationArrayList.add(dayDetailsArrayList.get(i).getAllocationDate().split("T")[0]);
        }

        return allocationArrayList;
    }

    private void bookingFragment(Bundle bundle) {
        Fragment myBookingFragment = new GetawaysBookingFragment();
        FragmentTransaction fragmentTransaction = ((StaycationCalendarActivity) context).getSupportFragmentManager().beginTransaction();
        myBookingFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.calenderRelativeLayout, myBookingFragment);
        fragmentTransaction.addToBackStack("GetawayBookingFragment");
        fragmentTransaction.commit();
    }

    private void checkAvailabilityCall(Bundle bundle, int initialPosition, int finalPosition) {


        CheckAvailabilityRequest checkAvailabilityRequest = new CheckAvailabilityRequest();
        checkAvailabilityRequest.setRoomId(Long.parseLong(roomId));
        checkAvailabilityRequest.setCurrencyCode(UserDTO.getInstance().getCurrency());
        checkAvailabilityRequest.setPackageId(Long.parseLong(packageId));
        checkAvailabilityRequest.setSubPackageId(Long.parseLong(subPackageId));
        checkAvailabilityRequest.setIpAddress(NetworkUtilities.getIp());
        checkAvailabilityRequest.setAllocationDates(getAllocationDates(initialPosition, finalPosition));
        checkAvailabilityRequest.setAllocation(selectedAllocation);

        try {


            String response = new checkAllocationAsyncTask(new GsonBuilder().serializeNulls().create().toJson(checkAvailabilityRequest), context).execute(Constant.GetawaysEndPointUrl + Constant.checkAvailabilityURL + UserDTO.getUserDTO().getLanguage()).get();
            bookNowRelativeLayout.setEnabled(true);
            spinningDialog.dismiss();

            if (response.equals("true")) {
                bundle.putLong("roomId", Long.parseLong(roomId));
                bundle.putStringArrayList("allocationArray", getAllocationDates(initialPosition, finalPosition));
                bundle.putInt("Allocation", selectedAllocation);
                bookingFragment(bundle);
            } else
                Utilities.commonErrorMessage(context, context.getResources().getString(R.string.app_name),context.getResources().getString(R.string.common_error_msg),false,null);

            alertDialog.dismiss();
        } catch (Exception e) {
            Utilities.commonErrorMessage(context, context.getResources().getString(R.string.app_name),context.getResources().getString(R.string.common_error_msg),false,null);
            alertDialog.dismiss();
            e.printStackTrace();
        }

    }

    public class checkAllocationAsyncTask extends AsyncTask<String, Void, String> {

        String mRequest;
        ProgressDialog progressDialog;

        public checkAllocationAsyncTask(String request, Context context) {
            mRequest = request;
            progressDialog = new ProgressDialog(context);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream;
            String response = null;
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(params[0]);

                StringEntity stringEntity = new StringEntity(mRequest);
                Map<String, String> headerMap = NetworkUtilities.getStayCationHeader();
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    if (!(entry.getKey().equals("Accept")))
                        httpPost.addHeader(entry.getKey(), entry.getValue());
                }
                stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();
                response = stringBuilder.toString();

            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }

    @Override
    public int getItemCount() {
        return dayDetailsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dayNameTextView, dateTextView, priceTextView, statusTextView, nightsTextView;
        RelativeLayout dayRowLinearLayout;
        CheckBox dayCheckBox;


        public ViewHolder(View itemView) {
            super(itemView);

            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            dayNameTextView = (TextView) itemView.findViewById(R.id.dayNameTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceTextView);
            statusTextView = (TextView) itemView.findViewById(R.id.statusTextView);
            nightsTextView = (TextView) itemView.findViewById(R.id.nightCountTextView);
            dayCheckBox = (CheckBox) itemView.findViewById(R.id.dayCheckBox);
            dayRowLinearLayout = (RelativeLayout) itemView.findViewById(R.id.dayRowLinearLayout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }

        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final DayDetailsAdapter.OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }
}
