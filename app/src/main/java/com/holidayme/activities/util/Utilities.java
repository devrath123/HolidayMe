package com.holidayme.activities.util;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;
import com.holidayme.activities.SplashScreenActivity;
import com.holidayme.activities.VolleySupport.AppController;
import com.holidayme.common.Log;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.CurrencyObject;
import com.holidayme.data.Item;
import com.holidayme.data.UserDTO;
import com.holidayme.response.CurrentLocationDetail;
import com.holidayme.widgets.ContactUsCustomDialog;
import com.holidayme.widgets.CustomDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import static com.holidayme.activities.HomeActivity.spinningDialog;

/**
 * Created by supriya.sakore on 18-02-2016.
 */
public class Utilities {


    static Bundle bundle;

    public static HashMap<String, String> splitDate(String date) {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] parts = date.split("-");
        hashMap.put(Constant.Date.DATE.name(), parts[0]);
        hashMap.put(Constant.Date.MONTH.name(), parts[1]);
        hashMap.put(Constant.Date.YEAR.name(), parts[2]);
        hashMap.put(Constant.Date.DAY.name(), parts[3]);
        return hashMap;
    }

    public static boolean isPassedDate(String checkInDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date date1 = sdf.parse(checkInDate);
            Date date2 = sdf.parse(sdf.format(Calendar.getInstance().getTime()));

            if (date1.equals(date2)) {
                return false;
            }
            if (date1.after(date2)) {
                return false;
            }

            if (date1.before(date2)) {
                return true;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static void setSharedPreference(Context context, String key,
                                           String value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static boolean gettBooleanSharedPreference(Context context, String key) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        boolean prefData = prefs.getBoolean(key, false);
        return prefData;
    }

    public static void setBooleanSharedPreference(Context context, String key,
                                                  boolean value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public static String gettSharedPreference(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String prefData = prefs.getString(key, "0");
        return prefData;
    }




    public static List<Item> getCurrencyList(String currency) {
        List<Item> listViewItems = null;


        if (listViewItems == null)
            listViewItems = new ArrayList<>();

        listViewItems.clear();

        if (currency.equalsIgnoreCase(Constant.CURRENCY_DUBAI)) {
            CurrencyObject currencyObject = new CurrencyObject("U.A.E. Dirham", "AED", "  الدرهم الإماراتي");
            currencyObject.setIsCheck(true);
            listViewItems.add(currencyObject);
        } else {
            listViewItems.add(new CurrencyObject("U.A.E. Dirham", "AED", "  الدرهم الإماراتي"));

        }
        listViewItems.add(new CurrencyObject("Saudi Arabian Riyal", "SAR", "  الريال السعودي"));

        if (currency.equalsIgnoreCase(Constant.CURRENCY_USA)) {
            CurrencyObject currencyObject = new CurrencyObject("U. S. Dollar", "USD", "  الدولار الأمريكي");
            currencyObject.setIsCheck(true);
            listViewItems.add(currencyObject);
        } else {
            listViewItems.add(new CurrencyObject("U. S. Dollar", "USD", "  الدولار الأمريكي"));

        }
        listViewItems.add(new CurrencyObject("Euro", "EUR", "  اليورو"));
        listViewItems.add(new CurrencyObject("Australian dollar", "AUD", "  الدولار الأسترالي"));
        listViewItems.add(new CurrencyObject("Indian Rupee", "INR", "  الروبية الهندية"));
        listViewItems.add(new CurrencyObject("Korean Won", "KRW", "الوون الكوري"));
        listViewItems.add(new CurrencyObject("NewZealand Dollar", "NZD", "الدولار النيوزلندي"));
        listViewItems.add(new CurrencyObject("Thai Baht", "THB", "البات التايلندي"));
        listViewItems.add(new CurrencyObject("HongKong Dollar", "HKD", "  الدولار الهونغ كونغي"));
        listViewItems.add(new CurrencyObject("Japanese Yen", "JPY", "  الين الياباني"));
        listViewItems.add(new CurrencyObject("Kazakhstani Tenge", "KZT", "  التينغ الكازاخستاني"));
        listViewItems.add(new CurrencyObject("Malaysian Ringgit", "MYR", "  الرينغيت الماليزي"));
        listViewItems.add(new CurrencyObject("Russian Ruble", "RUB", "  الروبل الروسي"));
        listViewItems.add(new CurrencyObject("Canadian Dollar", "CAD", "  الدولار الكندي"));
        listViewItems.add(new CurrencyObject("Indonesian Rupiah", "IDR", "  الروبية الإندونيسية"));
        listViewItems.add(new CurrencyObject("Jordanian Dinar", "JOD", "  الدينار الأردني"));
        listViewItems.add(new CurrencyObject("Singapore Dollar", "SGD", "  الدولار السنغافوري"));
        listViewItems.add(new CurrencyObject("Taiwan Dollar", "TWD", "  الدولار التايواني"));
        listViewItems.add(new CurrencyObject("Hungarian Forint", "HUF", "  الفورنت المجري"));
        listViewItems.add(new CurrencyObject("Polish Zloty", "PLN", "  الزلوتي البولندي"));
        listViewItems.add(new CurrencyObject("Lithuanian Litas", "LTL", "  الليتاس الليتوانية"));
        listViewItems.add(new CurrencyObject("Pound Sterling", "GBP", "  الجنيه الإسترليني"));
        listViewItems.add(new CurrencyObject("Ukraine Hryvnia", "UAH", "  الهريفنيا الأوكرانية"));
        listViewItems.add(new CurrencyObject("Norwegian Krone", "NOK", "  الكرونة النرويجية"));
        listViewItems.add(new CurrencyObject("Swedish Krona", "SEK", "  الكرونة السويدية"));
        listViewItems.add(new CurrencyObject("Swiss Franc", "CHF", "  الفرنك السويسري"));
        listViewItems.add(new CurrencyObject("Argentine Peso", "ARS", "  البيزو الأرجنتيني"));
        listViewItems.add(new CurrencyObject("Chilean Peso", "CLP", "  البيزو التشيلي"));
        listViewItems.add(new CurrencyObject("East Caribbean Dollar", "XCD", "  الدولار الشرق كاريبي"));
        listViewItems.add(new CurrencyObject("Bermuda Dollar", "BMD", "  الدولار البرمودي"));
        listViewItems.add(new CurrencyObject("Mexican Peso", "MXN", " البيزو المكسيكي"));
        listViewItems.add(new CurrencyObject("Brazilian Real", "BRL", "  الريال البرازيلي"));
        listViewItems.add(new CurrencyObject("Israeli Shekel", "ILS", "Israeli Shekel"));
        listViewItems.add(new CurrencyObject("South African Rand", "ZAR", "  الراند الجنوب أفريقي"));
        listViewItems.add(new CurrencyObject("Burundi Franc", "BIF", " الفرنك البوروندي"));
        listViewItems.add(new CurrencyObject("Kuwaiti Dinar", "KWD", "  الدينار الكويتي"));
        listViewItems.add(new CurrencyObject("Omani Rial", "OMR", "  الريال العماني"));
        listViewItems.add(new CurrencyObject("Bahraini Dinar", "BHD", "  الدينار البحريني"));
        listViewItems.add(new CurrencyObject("Egyptian Pound", "EGP", "  الجنيه المصري"));
        listViewItems.add(new CurrencyObject("Namibian Dollar", "NAD", "  الدولار الناميبي"));
        listViewItems.add(new CurrencyObject("Qatari Riyal", "QAR", "  الريال القطري"));

        return listViewItems;
    }

    public static void restartApp(Activity activity) {
        try {
            Utilities.setSharedPreference(activity, "isDestroyed", "0");
            Intent intent = new Intent(activity, SplashScreenActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    AppController.getInstance().getBaseContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            //Following code will restart your application after 2 seconds
            AlarmManager mgr = (AlarmManager) AppController.getInstance().getBaseContext()
                    .getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, 0,
                    pendingIntent);

            //This will finish your activity manually
            activity.finish();

            //This will stop your application and take out from it.
            System.exit(2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void navigateToPlayStore(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


    public static Bitmap createBitmap_ScriptIntrinsicBlur(Bitmap src, float r, Context context) {

        //Radius range (0 < r <= 25)

        if (r <= 0) {
            r = 0.1f;

        } else if (r > 25) {
            r = 25.0f;
        }

        Bitmap bitmap = Bitmap.createBitmap(
                src.getWidth(), src.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, src);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(r);
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();
        return bitmap;
    }

    public static void setStarRating(double star_count, Context context, View rootView) {

        ImageView star1, star2, star3, star4, star5;
        TextView txt_other;

        star1 = (ImageView) rootView.findViewById(R.id.star1);
        star2 = (ImageView) rootView.findViewById(R.id.star2);
        star3 = (ImageView) rootView.findViewById(R.id.star3);
        star4 = (ImageView) rootView.findViewById(R.id.star4);
        star5 = (ImageView) rootView.findViewById(R.id.star5);


        if(star_count == 0.0 || star_count == 0.5){
            if (rootView.findViewById(R.id.txt_other) != null)
                ((TextView) rootView.findViewById(R.id.txt_other)).setText(context.getString(R.string.other));
            star1.setImageDrawable(null);
            star2.setImageDrawable(null);
            star3.setImageDrawable(null);
            star4.setImageDrawable(null);
            star5.setImageDrawable(null);
        } else if (star_count == 1 || star_count == 1.5) {
            star1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star2.setImageDrawable(null);
            star3.setImageDrawable(null);
            star4.setImageDrawable(null);
            star5.setImageDrawable(null);

        } else if (star_count == 2 || star_count == 2.5) {
            star1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star3.setImageDrawable(null);
            star4.setImageDrawable(null);
            star5.setImageDrawable(null);

        } else if (star_count == 3 || star_count == 3.5) {
            star1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star4.setImageDrawable(null);
            star5.setImageDrawable(null);

        } else if (star_count == 4 || star_count == 4.5) {
            star1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star5.setImageDrawable(null);
        } else if (star_count == 5) {
            star1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star4.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));
            star5.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.full_filled_star));

        }

    }


    public static void setTripAdviserRating(ImageView imageView, int rat_count) {
        switch (rat_count) {
            case 1:
                imageView.setImageResource(R.drawable.ta_1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ta_2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.ta_3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.ta_4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.ta_5);
                break;
            default:
                break;
        }
    }


    public static void commonErrorMessage(final Context context, String title, String message, final boolean isPopBack, final FragmentManager fragmentManager) {
        final CustomDialog customDialog = new CustomDialog(context, title, message, context.getString(R.string.ok));
        customDialog.setDialogExitListener(new CustomDialog.DialogExitListener() {
            @Override
            public void dialogExitWithDone() {

            }

            @Override
            public void dialogExitWithDismissOrCancel() {
                if (spinningDialog != null && spinningDialog.isShowing()) {
                    spinningDialog.dismiss();
                }

                if (isPopBack)
                    fragmentManager.popBackStack();
                else
                    customDialog.dismiss();

            }
        });
        customDialog.show();
    }

   
    public String occupancyInfo(Context context, String checkInDate, String checkOutDate, int adultCount, int childCount, int roomCount) {

        int night = 0;
        String checkinDate = "", checkoutDate = "", checkinDate_ar = "", checkoutDate_ar = "";
        String headerText;

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        Date date1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy", Locale.US);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy-EEE", new Locale("ar"));

        try {
            date1 = sdf.parse(sdf.format(new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(checkInDate)));
            checkinDate = sdf.format(date1);
            checkinDate_ar = df1.format(date1);
            cal1.setTime(date1);
            date1 = sdf.parse(sdf.format(new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(checkOutDate)));
            checkoutDate = sdf.format(date1);
            checkoutDate_ar = df1.format(date1);
            cal2.setTime(date1);

            night = NetworkUtilities.daysBetween(cal2.getTime(), cal1.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }


        HashMap<String, String> hashMap = Utilities.splitDate(checkinDate_ar);
        String checkinMonth = (hashMap.get(Constant.Date.MONTH.name()));

        String string = checkinDate;
        String[] parts = string.split("-");
        String checkinday = parts[0];
        String checkinyear = parts[2];

        String string1 = checkoutDate;
        String[] part = string1.split("-");
        String checkoutday = part[0];
        String checkoutyear = part[2];


        hashMap = Utilities.splitDate(checkoutDate_ar);
        String checkoutMonth = (hashMap.get(Constant.Date.MONTH.name()));


        String checkInar = checkinday + "-" + checkinMonth + "-" + checkinyear;
        String checkOutar = checkoutday + "-" + checkoutMonth + "-" + checkoutyear;




        if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {


            if (childCount == 0) {
                if (roomCount == 1) {

                    headerText = checkInar + " - " + checkOutar + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.one_room);


                } else if (roomCount == 2) {

                    headerText = checkInar + " - " + checkOutar + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.two_room);


                } else {

                    headerText = checkInar + " - " + checkOutar + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.three_ten_room) + " " + roomCount;


                }

            } else {
                if (roomCount == 1) {

                    headerText = checkInar + " - " + checkOutar + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.Kids) + " " + childCount + " | " + context.getString(R.string.one_room);


                } else if (roomCount == 2) {

                    headerText = checkInar + " - " + checkOutar + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.Kids) + " " + childCount + " | " + context.getString(R.string.two_room);

                } else {

                    headerText = checkInar + " - " + checkOutar + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.Kids) + " " + childCount + " | " + context.getString(R.string.three_ten_room) + " " + roomCount;

                }
            }

        } else {

            if (childCount == 0) {
                if (roomCount == 1) {
                    if (adultCount == 1)
                        headerText = checkinDate + " - " + checkoutDate + " " + context.getString(R.string.Adult) + " " + adultCount + " | " + context.getString(R.string.room) + " " + roomCount;
                    else
                        headerText = checkinDate + " - " + checkoutDate + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.room) + " " + roomCount;

                } else {
                    headerText = checkinDate + " - " + checkoutDate + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.Room) + " " + roomCount;
                }
            } else {
                if (roomCount == 1) {
                    if (childCount == 1) {
                        if (adultCount == 1) {
                            headerText = checkinDate + " - " + checkoutDate + " " + context.getString(R.string.Adult) + " " + adultCount + " | " + context.getString(R.string.Kid) + " " + childCount + " | " + context.getString(R.string.room) + " " + roomCount;

                        } else {
                            headerText = checkinDate + " - " + checkoutDate + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.Kid) + " " + childCount + " | " + context.getString(R.string.room) + " " + roomCount;

                        }
                    } else {
                        if (adultCount == 1) {
                            headerText = checkinDate + " - " + checkoutDate + " " + context.getString(R.string.Adult) + " " + adultCount + " | " + context.getString(R.string.Kids) + " " + childCount + " | " + context.getString(R.string.room) + " " + roomCount;

                        } else {
                            headerText = checkinDate + " - " + checkoutDate + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.Kids) + " " + childCount + " | " + context.getString(R.string.room) + " " + roomCount;

                        }
                    }


                } else {
                    if (childCount == 1)
                        headerText = checkinDate + " - " + checkoutDate + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.Kid) + " " + childCount + " | " + context.getString(R.string.Room) + " " + roomCount;
                    else
                        headerText = checkinDate + " - " + checkoutDate + " " + context.getString(R.string.Adults) + " " + adultCount + " | " + context.getString(R.string.Kids) + " " + childCount + " | " + context.getString(R.string.Room) + " " + roomCount;

                }

            }
        }

        return headerText;
    }
    public static void pushFacebookEvent(AppEventsLogger appEventsLogger, String eventName, String key, String value) {
        if (bundle == null)
            bundle = new Bundle();

    }

    public String[] getEnglishCountryCode() {
        return new String[]{"United Arab Emirates +971", "Saudi Arabia +966",
                "Kuwait +965", "Oman +968", "Bahrain +973", "Egypt +20",
                "Qatar +974", "Albania +355", "Algeria +213", "Andorra +376",
                "Antigua and Barbuda +1", "Argentina +54", "Armenia +374",
                "Aruba +297", "Australia +61", "Austria +43", "Azerbaijan +994",
                "Bahamas +1", "Bangladesh +880", "Barbados +1", "Belarus +375",
                "Belgium +32", "Belize +501", "Benin +229", "Bermuda  +1", "Bhutan (+975)",
                "Bolivia +591", "Bosnia and Herzegovina +387", "Botswana +267",
                "Brazil +55", "British Virgin Islands", "Brunei Darussalam +673",
                "Bulgaria +359", "Cambodia +885", "Cameroon +237", "Canada +1",
                "Cayman Islands +1", "Chile +56", "China +86", "Colombia +57",
                "Cook Islands +682", "Costa Rica +506", "Cote dIvoire +225",
                "Croatia +385", "Curacao +599", "Cyprus +357", "Czech Republic +420",
                "Denmark +45", "Djibouti +253", "Dominica (+1)", "Dominican Republic +1809",
                "Ecuador +593", "El Salvador +503", "Estonia +372", "Ethiopia +251", "Fiji +679",
                "Finland +358", "France +33", "French Polynesia +689", "Gambia +220", "Georgia +995",
                "Germany +49", "Ghana +233", "Greece +30", "Grenada +1", "Guadeloupe +590", "Guam +1",
                "Guatemala +502", "Haiti +590", "Honduras +504", "Hungary +36", "Iceland +354",
                "India +91", "Indonesia +62", "Iraq +964", "Ireland +353", "Israel +972", "Italy +39",
                "Jamaica +1", "Japan +81", "Jordan +962", "Kazakhstan +7", "Kenya +254", "Laos +856",
                "Latvia +371", "Lebanon +961", "Lesotho +266", "Libya +281", "Lithuania +370", "Luxembourg +352",
                "Macedonia +389", "Madagascar +261", "Malawi (+265)", "Malaysia +60", "Maldives +960",
                "Mali +223", "Malta +356", "Mauritania +222", "Mauritius +230", "Mexico +52", "Moldova +373",
                "Monaco +377", "Mongolia +976", "Montenegro +382", "Morocco +212", "Mozambique +258", "Myanmar +95",
                "Namibia +264", "Nepal +977", "Netherlands +31", "New Caledonia +687", "New Zealand +64",
                "Nicaragua +505", "Niger +227", "Nigeria +234", "Northern Mariana Islands +1670", "Norway +47",
                "Pakistan +92", "Palau +680", "Palestine 970", "Panama +507", "Papua New Guinea +675",
                "Paraguay +595", "Peru +51", "Philippines +63", "Poland +48", "Portugal +351", "Puerto Rico",
                "Reunion +262", "Romania +40", "Russia +7", "Saint Kitts and Nevis +1", "Saint Lucia +1", "Samoa +685",
                "San Marino +378", "Sao Tome and Principe +678", "Senegal +221", "Serbia +381", "Seychelles +248",
                "Singapore +65", "Slovakia +421", "Slovenia +386", "South Africa +27", "South Korea +82", "Spain +34",
                "Sri Lanka +94", "Swaziland +268", "Sweden +46", "Switzerland +41", "Syria +963", "Taiwan +886",
                "Tanzania +255", "Thailand +66", "Trinidad and Tobago", "Tunisia +2", "Turkey +90",
                "Turks and Caicos Islands +1‑649", "Uganda +256", "Ukraine +380", "United Kingdom +44",
                "United States of America +1", "Uruguay +598", "US Virgin Islands +1", "Uzbekistan +998",
                "Vanuatu +678", "Venezuela +58", "Vietnam +84", "Zambia +260", "Zimbabwe +263"};

    }

    public String[] getArabicCountryCode() {
        return new String[]{
                "971+ الامارات العربية المتحدة",
                "966+ المملكة العربية السعودية",
                "965+ الكويت",
                "968+ عمان",
                "973+ البحرين",
                "20+ مصر",
                "974+ قطر",
                "355+ البانيا",
                "213+ الجزائر",
                "376+ أندورا",
                "1+ انتيغوا وباربودا",
                "54+ الأرجنتين",
                "374+ ارمينيا",
                "297+ اروبا",
                "61+ استراليا",
                "43+ النمسا",
                "994+ اذربيجان",
                "1+ باهاماس",
                "880+ بنغلاديش",
                "1+ باربادوس",
                "375+ روسيا البيضاء",
                "32+ بلجيكا",
                "501+ بليز",
                "229+ بنين",
                "1+ برمودا",
                "975+ بوتان",
                "591+ بوليفيا",
                "387+ البوسنة والهرسك",
                "267+ بوتسوانا",
                "55+ البرازيل",
                "1+ الجزر العذراء البريطانية",
                "673+ بروناي دار السلام",
                "359+ بلغاريا",
                "885+ كمبوديا",
                "237+ الكاميرون",
                "1+ كندا",
                "1+ جزر كايمان",
                "56+ تشيلي",
                "86+ الصين",
                "57+ كولومبيا",
                "682+ ماكاو",
                "506+ كوستاريكا",
                "225+ ساحل العاج",
                "385+ كرواتيا",
                "599+ هونغ كونغ",
                "357+ قبرص",
                "420+ التشيك",
                "45+ الدنمارك",
                "253+ جيبوتي",
                "1+ دومينيكا",
                "1809+ جمهورية الدومينيكان",
                "593+ الاكوادور",
                "503+ السلفادور",
                "372+ استونيا",
                "251+ اثيوبيا",
                "679+ فيجي",
                "358+ فنلندا",
                "33+ فرنسا",
                "689+ بولينزيا الفرنسية",
                "220+ غامبيا",
                "995+ جورجيا",
                "49+ المانيا",
                "233+ غانا",
                "30+ اليونان",
                "1+ جرينادا",
                "590+ غوادلوب",
                "1+ غوام",
                "502+ غواتيمالا",
                "590+ هايتي",
                "504+ هندوراس",
                "36+ المجر",
                "354+ ايسلندا",
                "91+ الهند",
                "62+ اندونيسيا",
                "964+ العراق",
                "353+ ايرلندا",
                "972+ اسرائيل",
                "39+ ايطاليا",
                "1+ جامايكا",
                "81+ اليابان",
                "962+ الاردن",
                "7+ كازاخستان",
                "254+ كينيا",
                "856+ لاوس",
                "371+ لاتفيا",
                "961+ لبنان",
                "266+ ليسوتو",
                "281+ ليبيا",
                "370+ ليتوانيا",
                "352+ لوكسمبورغ",
                "389+ جمهورية مقدونيا",
                "261+ مدغشقر",
                "265+ مالاوي",
                "60+ ماليزيا",
                "960+ جزر المالديف",
                "223+ مالي",
                "356+ مالطا",
                "222+ موريتانيا",
                "230+ موريشيوس",
                "52+ المكسيك",
                "373+ مولدوفا",
                "377+ موناكو",
                "976+ منغوليا",
                "382+ مونتينيغرو",
                "212+ المغرب",
                "258+ موزمبيق",
                "95+ ميانمار",
                "264+ ناميبيا",
                "977+ نيبال",
                "31+ هولندا",
                "687+ كاليدونيا الجديدة",
                "64+ نيوزيلندا",
                "505+ نيكاراجوا",
                "227+ النيجر",
                "234+ نيجيريا",
                "1670+ جزر ماريانا الشمالية",
                "47+ النرويج",
                "92+ باكستان",
                "680+ بالاو",
                "Palestine +970",
                "507+ بنما",
                "675+ بابوا غينيا الجديدة",
                "595+ باراغواي",
                "51+ بيرو",
                "63+ الفلبين",
                "48+ بولندا",
                "351+ البرتغال",
                "1+ بورتوريكو",
                "262+ لا ريونيون",
                "40+ رومانيا",
                "7+ روسيا",
                "1+ سانت كيتس ونيفيس",
                "1+ سانت لوسيا",
                "685+ مايوت",
                "378+ سان مارينو",
                "678+ ساو تومي وبرينسيب",
                "221+ السنغال",
                "381+ صربيا",
                "248+ سيشل",
                "65+ سنغافورة",
                "421+ سلوفاكيا",
                "386+ سلوفينيا",
                "27+ جنوب أفريقيا",
                "82+ كوريا",
                "34+ اسبانيا",
                "94+ سريلانكا",
                "268+ سوازيلاند",
                "46+ السويد",
                "41+ سويسرا",
                "963+ سوريا",
                "886+ تايوان",
                "255+ تنزانيا",
                "66+ تايلاند",
                "1+ ترينيداد وتوباغو",
                "2+ تونس",
                "90+ تركيا",
                "1‑649+ جزر توركس وكايكوس",
                "256+ أوغندا",
                "380+ اوكرانيا",
                "44+ انجلترا",
                "1+ الولايات المتحدة الأمريكية",
                "598+ الاوروغواي",
                "1+ الجزر العذراء الأمريكية",
                "998+ أوزبكستان",
                "678+ فانواتو",
                "58+ فنزويلا",
                "84+ فيتنام",
                "260+ زامبيا",
                "263+ زيمبابوي"
        };
    }

    public String[] getCountryCodes() {
        return new String[]{
                "+971", "+966", "+965", "+968", "+973", "+20", "+974", "+355", "+213", "+376", "+1", "+54", "+374", "+297", "+61", "+43", "+994", "+1", "+880", "+1", "+375", "+32", "+501", "+229", "+1", "+975", "+591", "+387", "+267", "+55", "+1", "+673", "+359", "+885", "+237", "+1", "+1", "+56", "+86", "+57", "+682", "+506", "+225", "+385", "+599", "+357", "+420", "+45", "+253", "+1", "+1809", "+593", "+503", "+372", "+251", "+679", "+358", "+33", "+689", "+220", "+995", "+49", "+233", "+30", "+1", "+590", "+1", "+502", "+590", "+504", "+36", "+354", "+91", "+62", "+964", "+353", "+972", "+39", "+1", "+81", "+962", "+7", "+254", "+856", "+371", "+961", "+266", "+281", "+370", "+352", "+389", "+261", "+265", "+60", "+960", "+223", "+356", "+222", "+230", "+52", "+373", "+377", "+976", "+382", "+212", "+258", "+95", "+264", "+977", "+31", "+687", "+64", "+505", "+227", "+234", "+1670", "+47", "+92", "+680", "+970", "+507", "+675", "+595", "+51", "+63", "+48", "+351", "+1", "+262", "+40", "+7", "+1", "+1", "+685", "+378", "+678", "+221", "+381", "+248", "+65", "+421", "+386", "+27", "+82", "+34", "+94", "+268", "+46", "+41", "+963", "+886", "+255", "+66", "+1", "+2", "+90", "+1‑649", "+256", "+380", "+44", "+1", "+598", "+1", "+998", "+678", "+58", "+84", "+260", "+263"
        };
    }

    public String[] getCountries() {
        return new String[]{"United Arab Emirates", "Saudi Arabia", "Kuwait", "Oman", "Bahrain", "Egypt", "Qatar", "Albania", "Algeria", "Andorra", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei Darussalam", "Bulgaria", "Cambodia", "Cameroon", "Canada", "Cayman Islands", "Chile", "China", "Colombia", "Cook Islands", "Costa Rica", "Cote dIvoire", "Croatia", "Curacao", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "El Salvador", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "French Polynesia", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Laos", "Latvia", "Lebanon", "Lesotho", "Libya", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Mauritania", "Mauritius", "Mexico", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Northern Mariana Islands", "Norway", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Puerto Rico", "Reunion", "Romania", "Russia", "Saint Kitts and Nevis", "Saint Lucia", "Samoa", "San Marino", "Sao Tome and Principe", "Senegal", "Serbia", "Seychelles", "Singapore", "Slovakia", "Slovenia", "South Africa", "South Korea", "Spain", "Sri Lanka", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tanzania", "Thailand", "Trinidad and Tobago", "Tunisia", "Turkey", "Turks and Caicos Islands", "Uganda", "Ukraine", "United Kingdom", "United States of America", "Uruguay", "US Virgin Islands", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Zambia", "Zimbabwe"
        };
    }

   // "الامارات العربية المتحد"

    public String[] getCountriesArabic() {
        return new String[]
                {"الإمارات العربية المتحدة",
                        "المملكة العربية السعودية",
                        "الكويت",
                        "عمان",
                        "البحرين",
                        "مصر",
                        "قطر",
                        "البانيا",
                        "الجزائر",
                        "أندورا",
                        "انتيغوا وباربودا",
                        "الأرجنتين",
                        "ارمينيا",
                        "اروبا",
                        "استراليا",
                        "النمسا",
                        "اذربيجان",
                        "باهاماس",
                        "بنغلاديش",
                        "باربادوس",
                        "روسيا البيضاء",
                        "بلجيكا",
                        "بليز",
                        "بنين",
                        "برمودا",
                        "بوتان",
                        "بوليفيا",
                        "البوسنة والهرسك",
                        "بوتسوانا",
                        "البرازيل",
                        "الجزر العذراء البريطانية",
                        "بروناي دار السلام",
                        "بلغاريا",
                        "كمبوديا",
                        "الكاميرون",
                        "كندا",
                        "جزر كايمان",
                        "تشيلي",
                        "الصين",
                        "كولومبيا",
                        "ماكاو",
                        "كوستاريكا",
                        "ساحل العاج",
                        "كرواتيا",
                        "هونغ كونغ",
                        "قبرص",
                        "التشيك",
                        "الدنمارك",
                        "جيبوتي",
                        "دومينيكا",
                        "جمهورية الدومينيكان",
                        "الاكوادور",
                        "السلفادور",
                        "استونيا",
                        "اثيوبيا",
                        "فيجي",
                        "فنلندا",
                        "فرنسا",
                        "بولينزيا الفرنسية",
                        "غامبيا",
                        "جورجيا",
                        "المانيا",
                        "غانا",
                        "اليونان",
                        "جرينادا",
                        "غوادلوب",
                        "غوام",
                        "غواتيمالا",
                        "هايتي",
                        "هندوراس",
                        "المجر",
                        "ايسلندا",
                        "الهند",
                        "اندونيسيا",
                        "العراق",
                        "ايرلندا",
                        "اسرائيل",
                        "ايطاليا",
                        "جامايكا",
                        "اليابان",
                        "الاردن",
                        "كازاخستان",
                        "كينيا",
                        "لاوس",
                        "لاتفيا",
                        "لبنان",
                        "ليسوتو",
                        "ليبيا",
                        "ليتوانيا",
                        "لوكسمبورغ",
                        "جمهورية مقدونيا",
                        "مدغشقر",
                        "مالاوي",
                        "ماليزيا",
                        "جزر المالديف",
                        "مالي",
                        "مالطا",
                        "موريتانيا",
                        "موريشيوس",
                        "المكسيك",
                        "مولدوفا",
                        "موناكو",
                        "منغوليا",
                        "مونتينيغرو",
                        "المغرب",
                        "موزمبيق",
                        "ميانمار",
                        "ناميبيا",
                        "نيبال",
                        "هولندا",
                        "كاليدونيا الجديدة",
                        "نيوزيلندا",
                        "نيكاراجوا",
                        "النيجر",
                        "نيجيريا",
                        "جزر ماريانا الشمالية",
                        "النرويج",
                        "باكستان",
                        "بالاو",
                        "Palestine",
                        "بنما",
                        "بابوا غينيا الجديدة",
                        "باراغواي",
                        "بيرو",
                        "الفلبين",
                        "بولندا",
                        "البرتغال",
                        "بورتوريكو",
                        "لا ريونيون",
                        "رومانيا",
                        "روسيا",
                        "سانت كيتس ونيفيس",
                        "سانت لوسيا",
                        "مايوت",
                        "سان مارينو",
                        "ساو تومي وبرينسيب",
                        "السنغال",
                        "صربيا",
                        "سيشل",
                        "سنغافورة",
                        "سلوفاكيا",
                        "سلوفينيا",
                        "جنوب أفريقيا",
                        "كوريا",
                        "اسبانيا",
                        "سريلانكا",
                        "سوازيلاند",
                        "السويد",
                        "سويسرا",
                        "سوريا",
                        "تايوان",
                        "تنزانيا",
                        "تايلاند",
                        "ترينيداد وتوباغو",
                        "تونس",
                        "تركيا",
                        "جزر توركس وكايكوس",
                        "أوغندا",
                        "اوكرانيا",
                        "انجلترا",
                        "الولايات المتحدة الأمريكية",
                        "الاوروغواي",
                        "الجزر العذراء الأمريكية",
                        "أوزبكستان",
                        "فانواتو",
                        "فنزويلا",
                        "فيتنام",
                        "زامبيا",
                        "زيمبابوي"

                };
    }

    public String[] getCountriesISD()
    {
        return new String[]{"AE", "SA", "KW", "OM", "BH", "EG", "QA", "AL", "DZ", "AD", "AG",
                            "AR", "AM", "AW", "AU", "AT", "AZ", "BS", "BD", "BB", "BY", "BE",
                            "BZ", "BJ", "BM", "BT", "BO", "BA", "BW", "BR", "VG", "BN", "BG",
                            "KH", "CM", "CA", "KY", "CL", "CN", "CO", "CK", "CR", "CI", "HR",
                            "CW", "CY", "CZ", "DK", "DJ", "DM", "DO", "EC", "SV", "EE", "ET",
                            "FJ", "FI", "FR", "PF", "GM", "GE", "DE", "GH", "GR", "GD", "GP",
                            "GU", "GT", "HT", "HN", "HU", "IS", "IN", "ID", "IQ", "IE", "IL",
                            "IT", "JM", "JP", "jo", "KZ", "KE", "LA", "LV", "LB", "LS", "LY",
                            "LT", "LU", "MK", "MG", "MW", "MY", "MV", "ML", "MT", "MR", "MU",
                            "MX", "MD", "MC", "MN", "ME", "MA", "MZ", "MM", "NA", "NP", "NL",
                            "NC", "NZ", "NI", "NE", "NG", "MP", "No", "PK", "PW", "PS", "PA",
                            "PG", "PY", "PE", "PH", "PL", "PT", "PR", "RE", "RO", "RU", "KN",
                            "LC", "WS", "SM", "ST", "SN", "RS", "SC", "SG", "SK", "SI", "ZA",
                            "KR", "ES", "LK", "SZ", "SE", "CH", "SY", "TW", "TZ", "TH", "TT",
                            "TN", "TR", "TC", "UG", "UA", "GB", "US", "UY", "VI", "UZ", "VU",
                            "VE", "VN", "ZM", "ZW"};
    }

    public void setArrowPosition(ImageView leftArrowImageView, ImageView rightArrowImageView, int arrayListSize, int selectedImagePosition, Resources resources) {
        if (selectedImagePosition > 0 && selectedImagePosition < arrayListSize - 1) {
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                leftArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.icn_arrow_right,null));
                rightArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.icn_arrow_left,null));

            } else {
                leftArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.icn_arrow_left,null));
                rightArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.icn_arrow_right,null));

            }
            leftArrowImageView.setVisibility(View.VISIBLE);

        } else if (selectedImagePosition == 0) {

            leftArrowImageView.setVisibility(View.GONE);

        } else if (selectedImagePosition == arrayListSize- 1) {
            if (UserDTO.getUserDTO().getLanguage().equals(Constant.ARABIC_LANGUAGE_CODE)) {
                rightArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.icn_arrow_left,null));

            } else {
                rightArrowImageView.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.icn_arrow_right,null));

            }
            leftArrowImageView.setVisibility(View.VISIBLE);

        }
    }

    public static  HashMap<String, ArrayList<Item>> getDrawerCurrencyList( Context context){
        ArrayList<String>listDataHeader = new ArrayList<String>();
        HashMap<String, ArrayList<Item>> listDataChild=null;

        listDataHeader.add(context.getString(R.string.AFRICA_MIDDLE_EAST));
        listDataHeader.add(context.getString(R.string.Asia_Pacific));
        listDataHeader.add(context.getString(R.string.EUROPE));
        listDataHeader.add(context.getString(R.string.AMERICAS));


        listDataChild = new HashMap<String, ArrayList<Item>>();

        listDataChild.clear();

        //Asia
        ArrayList<Item> list1 = new ArrayList<Item>();

        list1.add(new CurrencyObject("Australian dollar", "AUD", "  الدولار الأسترالي"));
        list1.add(new CurrencyObject("Indian Rupee", "INR", "  الروبية الهندية"));
        list1.add(new CurrencyObject("Korean Won", "KRW", "الوون الكوري"));
        list1.add(new CurrencyObject("NewZealand Dollar", "NZD", "الدولار النيوزلندي"));
        list1.add(new CurrencyObject("Thai Baht", "THB", "البات التايلندي"));
        list1.add(new CurrencyObject("HongKong Dollar", "HKD", "  الدولار الهونغ كونغي"));
        list1.add(new CurrencyObject("Japanese Yen", "JPY", "  الين الياباني"));
        list1.add(new CurrencyObject("Kazakhstani Tenge", "KZT", "  التينغ الكازاخستاني"));
        list1.add(new CurrencyObject("Malaysian Ringgit", "MYR", "  الرينغيت الماليزي"));
        list1.add(new CurrencyObject("Russian Ruble", "RUB", "  الروبل الروسي"));
        list1.add(new CurrencyObject("Canadian Dollar", "CAD", "  الدولار الكندي"));
        list1.add(new CurrencyObject("Indonesian Rupiah", "IDR", "  الروبية الإندونيسية"));
        list1.add(new CurrencyObject("Jordanian Dinar", "JOD", "  الدينار الأردني"));
        list1.add(new CurrencyObject("Singapore Dollar", "SGD", "  الدولار السنغافوري"));
        list1.add(new CurrencyObject("Taiwan Dollar", "TWD", "  الدولار التايواني"));
        //===========================================================

        // Europe
        ArrayList<Item> list2 = new ArrayList<Item>();
        list2.add(new CurrencyObject("Euro", "EUR", "  اليورو"));
        list2.add(new CurrencyObject("Hungarian Forint", "HUF", "  الفورنت المجري"));
        list2.add(new CurrencyObject("Polish Zloty", "PLN", "  الزلوتي البولندي"));
        list2.add(new CurrencyObject("Lithuanian Litas", "LTL", "  الليتاس الليتوانية"));
        list2.add(new CurrencyObject("Pound Sterling", "GBP", "  الجنيه الإسترليني"));
        list2.add(new CurrencyObject("Ukraine Hryvnia", "UAH", "  الهريفنيا الأوكرانية"));
        list2.add(new CurrencyObject("Norwegian Krone", "NOK", "  الكرونة النرويجية"));
        list2.add(new CurrencyObject("Swedish Krona", "SEK", "  الكرونة السويدية"));
        list2.add(new CurrencyObject("Swiss Franc", "CHF", "  الفرنك السويسري"));

        //==============================================================

        //Americas
        ArrayList<Item> list3 = new ArrayList<Item>();
        list3.add(new CurrencyObject("Argentine Peso", "ARS", "  البيزو الأرجنتيني"));
        list3.add(new CurrencyObject("Chilean Peso", "CLP", "  البيزو التشيلي"));
        list3.add(new CurrencyObject("East Caribbean Dollar", "XCD", "  الدولار الشرق كاريبي"));
        list3.add(new CurrencyObject("Bermuda Dollar", "BMD", "  الدولار البرمودي"));
        list3.add(new CurrencyObject("Mexican Peso", "MXN", " البيزو المكسيكي"));
        list3.add(new CurrencyObject("Brazilian Real", "BRL", "  الريال البرازيلي"));
        list3.add(new CurrencyObject("U. S. Dollar", "USD", "  الدولار الأمريكي"));

        //===============================================================

        //Africa
        ArrayList<Item> list4 = new ArrayList<Item>();
        list4.add(new CurrencyObject("U.A.E. Dirham", "AED", "  الدرهم الإماراتي"));
        list4.add(new CurrencyObject("Saudi Arabian Riyal", "SAR", "  الريال السعودي"));
        list4.add(new CurrencyObject("Israeli Shekel", "ILS", "Israeli Shekel"));
        list4.add(new CurrencyObject("South African Rand", "ZAR", "  الراند الجنوب أفريقي"));
        list4.add(new CurrencyObject("Burundi Franc", "BIF", " الفرنك البوروندي"));
        list4.add(new CurrencyObject("Kuwaiti Dinar", "KWD", "  الدينار الكويتي"));
        list4.add(new CurrencyObject("Omani Rial", "OMR", "  الريال العماني"));
        list4.add(new CurrencyObject("Bahraini Dinar", "BHD", "  الدينار البحريني"));
        list4.add(new CurrencyObject("Egyptian Pound", "EGP", "  الجنيه المصري"));
        list4.add(new CurrencyObject("Namibian Dollar", "NAD", "  الدولار الناميبي"));
        list4.add(new CurrencyObject("Qatari Riyal", "QAR", "  الريال القطري"));

        listDataChild.put(listDataHeader.get(0), list4);// Header, Child data
        listDataChild.put(listDataHeader.get(1), list1);
        listDataChild.put(listDataHeader.get(2), list2);
        listDataChild.put(listDataHeader.get(3), list3);

        return listDataChild;
    }

    public void getCurrentLocationDetailByIp() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected String doInBackground(final Void... params) {
                String location = "";
                try {
                    URL url = new URL(Constant.GET_MY_DETAIL_FROM_IP);
                    // read text returned by server
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                    while ((location = bufferedReader.readLine()) != null) {
                        return location;
                    }
                    bufferedReader.close();
                } catch (MalformedURLException e) {
                    Log.e("error on fetching detail from ip", "Malformed URL: " + e.getMessage());
                } catch (IOException e) {
                    Log.e("error on fetching detail from ip", "I/O Error: " + e.getMessage());
                }
                return location;
            }

            @Override
            protected void onPostExecute(String location) {

                if (!TextUtils.equals(location, "")) {
                    CurrentLocationDetail currentLocationDetail = new Gson().fromJson(location, CurrentLocationDetail.class);
                    UserDTO.getUserDTO().setCountryCode(currentLocationDetail.getCountry_code());
                }else
                    UserDTO.getUserDTO().setCountryCode("IN");


            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public void contactUsDialog(Context context){

        ContactUsCustomDialog contactUsCustomDialog = null;

        contactUsCustomDialog = new ContactUsCustomDialog(context,UserDTO.getUserDTO().getCountryCode());

        final ContactUsCustomDialog finalContactUsCustomDialog = contactUsCustomDialog;
        contactUsCustomDialog.setDialogExitListner(new ContactUsCustomDialog.DialogExitListener() {
            @Override
            public void dialogExitWithDone() {
            }

            @Override
            public void dialogExitWithDismissOrCancel() {
                finalContactUsCustomDialog.dismiss();
            }
        });
        contactUsCustomDialog.show();
    }

    public void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation transformation) {
                v.getLayoutParams().height = interpolatedTime == 1 ? ViewGroup.LayoutParams.MATCH_PARENT : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(500);
        v.startAnimation(animation);
    }

    /**
     * This function will collapse the images container with animation
     */

    public void collapse(final View v) {

        final int initialHeight = v.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation transformation) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        animation.setDuration(500);
        v.startAnimation(animation);
    }

    /*public void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


}
