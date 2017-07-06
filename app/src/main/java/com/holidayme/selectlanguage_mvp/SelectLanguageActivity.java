package com.holidayme.selectlanguage_mvp;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.facebook.appevents.AppEventsLogger;
import com.holidayme.Constants.Constant;
import com.holidayme.activities.BaseActivity;
import com.holidayme.activities.R;

import com.holidayme.activities.util.HolidayMeFont;
import com.holidayme.activities.util.Utilities;
import com.holidayme.adapter.CustomListView;
import com.holidayme.common.NetworkUtilities;
import com.holidayme.data.IsSelectAnyLanguage;
import com.holidayme.data.Item;
import com.holidayme.data.UserDTO;
import com.holidayme.login_mvp.LoginWebViewActivity;
import com.holidayme.managers.SharedPreferenceManager;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class SelectLanguageActivity extends BaseActivity implements ISelectLanguageView{

    private Button saveButton;
    private RadioButton englishLanguageRadioButton, arabicLanguageRadioButton;
    private TextView selectCurrencyTextView;
    private String selectCurrencyCode = "";
    private ListView listViewWithCheckBox;
    private IsSelectAnyLanguage selectAnyLanguageListener;
    private AppEventsLogger eventsLogger;
    private SelectLanguagePresenter selectLanguagePresenter;
    private final String APP_LAUNCH = "App Launch";
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_language);
        context=SelectLanguageActivity.this;

        eventsLogger = AppEventsLogger.newLogger(this);
        selectLanguagePresenter = new SelectLanguagePresenter(this);

        //Set Default Language and Currency to English & USD
        selectLanguagePresenter.setLanguage(Constant.ENGLISH_LANGUAGE_CODE);
        selectLanguagePresenter.setCurrency(Constant.CURRENCY_USA);
        initializeWidgets();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
       // new CheckURL().execute(Constant.CHECK_UPDATE_URL);
    }



    public void initializeWidgets() {

        selectCurrencyTextView = (TextView) findViewById(R.id.selectCurrencyTextView);
        HolidayMeFont.overrideFonts(this, selectCurrencyTextView, Constant.NotoKufiArabic_Regular);

        selectCurrencyTextView.setText(getResources().getString(R.string.select_currency));

        englishLanguageRadioButton = (RadioButton) findViewById(R.id.english_language);
        englishLanguageRadioButton.setChecked(true);

        HolidayMeFont.overrideFonts(this, englishLanguageRadioButton, Constant.HelveticaNeueRoman);


        arabicLanguageRadioButton = (RadioButton) findViewById(R.id.arabic_language);

        HolidayMeFont.overrideFonts(this, arabicLanguageRadioButton, Constant.NotoKufiArabic_Regular);

        saveButton = (Button) findViewById(R.id.saveButton);
        HolidayMeFont.overrideFonts(this, saveButton, Constant.NotoKufiArabic_Regular);
        saveButton.setText(getResources().getString(R.string.Skip));
        saveButton.setOnClickListener(saveButtonOnClick);

        


        listViewWithCheckBox = (ListView) findViewById(R.id.listView);

        selectAnyLanguageListener = new IsSelectAnyLanguage() {
            @Override
            public void isSelectLanguage(boolean ischeck, String mCurrencyCode) {
                selectCurrencyCode = mCurrencyCode;
                if (ischeck) {
                    saveButton.setText(getResources().getString(R.string.save_and_continue));
                } else if (!ischeck && !englishLanguageRadioButton.isChecked() && !arabicLanguageRadioButton.isChecked()) {
                    saveButton.setText(getResources().getString(R.string.Skip));
                } else {
                    saveButton.setText(getResources().getString(R.string.save_and_continue));
                }
            }
        };
        englishLanguageRadioButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {

                arabicLanguageRadioButton.setChecked(false);
                arabicLanguageRadioButton.setTextColor(ContextCompat.getColor(context,R.color.light_purple));
                englishLanguageRadioButton.setTextColor(ContextCompat.getColor(context,R.color.white));

                selectLanguagePresenter.setLanguage(Constant.ENGLISH_LANGUAGE_CODE);

                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                saveButton.setText(getResources().getString(R.string.save_and_continue));
                selectCurrencyTextView.setText(getResources().getString(R.string.select_currency));

                selectLanguagePresenter.setAdapter(Constant.CURRENCY_USA);

            }
        });
        arabicLanguageRadioButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {

               englishLanguageRadioButton.setChecked(false);
                englishLanguageRadioButton.setTextColor(ContextCompat.getColor(context,R.color.light_purple));
                arabicLanguageRadioButton.setTextColor(ContextCompat.getColor(context,R.color.white));

                selectLanguagePresenter.setLanguage(Constant.ARABIC_LANGUAGE_CODE);

                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                saveButton.setText(getResources().getString(R.string.save_and_continue));
                selectCurrencyTextView.setText(getResources().getString(R.string.select_currency));

                selectLanguagePresenter.setAdapter(Constant.CURRENCY_DUBAI);
            }
        });
        selectLanguagePresenter.setAdapter(UserDTO.getUserDTO().getCurrency());
    }

    @Override
    public void setAdapterData(List<Item> itemList) {
        listViewWithCheckBox.setAdapter(new CustomListView(this, itemList, selectAnyLanguageListener));
        listViewWithCheckBox.setChoiceMode(listViewWithCheckBox.CHOICE_MODE_SINGLE);
    }

    @Override
    public void traverseToLogin() {

        if(NetworkUtilities.isInternet(SelectLanguageActivity.this)) {
            Intent loginIntent = new Intent(SelectLanguageActivity.this, LoginWebViewActivity.class);
            Bundle loginBundle = new Bundle();
            loginBundle.putBoolean("ISFIRSTTIME", true);
            loginIntent.putExtras(loginBundle);
            startActivity(loginIntent);
            finish();
        }
        else
            Utilities.commonErrorMessage(this, this.getString(R.string.Network_not_avilable), this.getString(R.string.please_check_your_internet_connection), false,null);

    }

    public View.OnClickListener saveButtonOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveCurrencyLanguage(saveButton.getText().toString());
        }
    };


    /**
     * to save user default or selected currency and language
     */
    private void saveCurrencyLanguage(String buttonText) {

        if (buttonText.equals(getResources().getString(R.string.save_and_continue))) {

            Utilities.pushFacebookEvent(eventsLogger,APP_LAUNCH,"save & continue", "Save selection and proceed");

            if (englishLanguageRadioButton.isChecked() || arabicLanguageRadioButton.isChecked()) {

                if (englishLanguageRadioButton.isChecked()) {

          //          Utilities.pushFacebookEvent(eventsLogger,APP_LAUNCH,"Language", "English");
                    selectLanguagePresenter.setLanguage(Constant.ENGLISH_LANGUAGE_CODE);
                    arabicLanguageRadioButton.setChecked(false);
                } else {

                    selectLanguagePresenter.setLanguage(Constant.ARABIC_LANGUAGE_CODE);
                    englishLanguageRadioButton.setChecked(false);
                }

            } else {

                selectLanguagePresenter.setLanguage(Constant.ENGLISH_LANGUAGE_CODE);
                arabicLanguageRadioButton.setChecked(false);
            }


            if (selectCurrencyCode.equalsIgnoreCase("")) {
                if (UserDTO.getUserDTO().getLanguage().equalsIgnoreCase(Constant.ARABIC_LANGUAGE_CODE)) {
                    selectLanguagePresenter.setCurrency(Constant.CURRENCY_DUBAI);
                } else
                    selectLanguagePresenter.setCurrency(Constant.CURRENCY_USA);

            } else
                selectLanguagePresenter.setCurrency(selectCurrencyCode);


        } else {

            selectLanguagePresenter.setLanguage(Constant.ENGLISH_LANGUAGE_CODE);
            selectLanguagePresenter.setCurrency(Constant.CURRENCY_USA);
        }
        selectLanguagePresenter.setFirstTimePreference("isFirstTime",true);
    }


   /* private class CheckURL extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("HEAD");
                return (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result)
                SharedPreferenceManager.getInstance(SelectLanguageActivity.this).saveBooleanPreference(Constant.IS_UPDATE_AVAILABLE,true);
             else
                SharedPreferenceManager.getInstance(SelectLanguageActivity.this).saveBooleanPreference(Constant.IS_UPDATE_AVAILABLE,false);

        }
    }*/
}
