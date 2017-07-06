package com.holidayme.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.EditText;

import com.holidayme.Constants.Constant;
import com.holidayme.activities.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by santosh.patar on 06-08-2015.
 */
public class Validation {

    public static final String mEmaiValidate = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z‌​]{2,4})$";
    private static final String REGEX_NAME = "[a-zA-Z ][a-zA-Z]*";
    private static final String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";


    /*public static boolean HolidayMeLoginvalidate(String emailId, EditText editText, String Password, EditText epassword, Context mContext) {

        if (emailId.trim().equals("")) {
            editText.setError(mContext.getResources().getString(R.string.please_enter_EmailID));
            return false;
        } else if (Password.trim().equals("")) {
            epassword.setError(mContext.getResources().getString(R.string.Please_enter_password));
            return false;
        }

        return true;
    }*/


    @SuppressLint("StringFormatInvalid")
    public static boolean bookingNameValidation(String firstName, EditText FirstName, Context mContext, String error_msg) {
        if (firstName.trim().equals("")) {
            FirstName.setError(mContext.getResources().getString(R.string.name_error_msg1) + " " + error_msg);
            return false;
        } else if (firstName.trim().length() < 3) {
            FirstName.setError(mContext.getResources().getString(R.string.name_error_msg3) + " " + error_msg);
            return false;
        } else if (!isValidName(firstName.trim())) {
            FirstName.setError(String.format(mContext.getResources().getString(R.string.name_error_msg2), error_msg));
            return false;
        }
        return true;
    }

   /* public static boolean bookingCodValidation(String name, EditText Name, Context mContext, String error_msg) {
        if (name.trim().equals("")) {
            Name.setError(mContext.getResources().getString(R.string.name_error_msg1) + " " + error_msg);
            return false;
        }
        return true;
    }*/

    public static boolean EmailValidation(String email, EditText Email, Context mContext) {

        if (!email.trim().matches(mEmaiValidate) && email.trim().equals("")) {
            Email.setError(mContext.getResources().getString(R.string.Please_enter_a_valid_email_ID));
            return false;
        }
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email.trim());
        if (!m.matches()) {
            Email.setError(mContext.getResources().getString(R.string.Please_enter_a_valid_email_ID));
        }
        return m.matches();
    }

    public static boolean PhoneNumberValidation(String phoneno, EditText Phoneno, Context mContext) {

        if ((phoneno.trim().equals("") || phoneno.trim().length() < 4 || phoneno.trim().length() > 15)) {
            Phoneno.setError(mContext.getResources().getString(R.string.phone_validation));
            return false;
        }

        if (!isValidMobileNumber(phoneno.trim())) {
            Phoneno.setError(mContext.getResources().getString(R.string.phone_validation));
            return false;
        }

        return true;
    }

    public static boolean CreditCardValidation(String CC, EditText CreditCard, Context mContext) {

        if (CC.trim().equals("") || CC.trim().length() < 13 || CC.trim().length() > 19) {
            CreditCard.setError(mContext.getResources().getString(R.string.creditcard_validation));
            return false;
        }

        if (!Check(CC.trim())) {
            CreditCard.setError(mContext.getResources().getString(R.string.creditcard_validation));
            return false;
        }

        String regex = "\\d+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(CC.trim());
        if (!m.find()) {
            CreditCard.setError(mContext.getResources().getString(R.string.creditcard_validation));
            return false;
        }

        Pattern p1 = Pattern.compile(Constant.regVisa);
        Pattern p2 = Pattern.compile(Constant.regMaster);
        Matcher m1 = p1.matcher(CC.trim());
        Matcher m2 = p2.matcher(CC.trim());

        if (!m1.matches() && !m2.matches()) {
            CreditCard.setError(mContext.getResources().getString(R.string.creditcard_type_validation));
            return false;
        }
        return true;
    }

    public static boolean cvvValidation(String cvv, EditText CVC, Context mContext) {

        if (cvv.trim().equals("") || cvv.trim().length() < 3 || cvv.trim().length() > 3) {
            CVC.setError(mContext.getResources().getString(R.string.cvv_validation));
            return false;
        }

        String regex = "\\d+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(cvv.trim());
        if (!m.find()) {
            CVC.setError(mContext.getResources().getString(R.string.cvv_validation));
            return false;
        }
        return true;

    }


   /* public static boolean forgotpassnvalidate(String email, EditText Email, Context mContext) {

        if (email.trim().equals("")) {
            Email.setError(mContext.getResources().getString(R.string.Please_enter_register_email_address));
            return false;

        } else if (!email.trim().matches(mEmaiValidate)) {
            Email.setError(mContext.getResources().getString(R.string.Please_enter_a_valid_email_ID));
            return false;
        }
        return true;
    }

    public static boolean UserSignUpValidation(String email, EditText edEmail, String password, EditText edPassword, String confirmpassword, EditText edConfirmPassord, Context mContext) {

        if (email.trim().equals("")) {
            edEmail.setError(mContext.getResources().getString(R.string.Please_enter_a_valid_email_ID));
            return false;
        } else if (!email.trim().matches(mEmaiValidate)) {
            edEmail.setError(mContext.getResources().getString(R.string.Please_enter_a_valid_email_ID));
            return false;


        } else if (password.trim().equals("")) {
            edPassword.setError(mContext.getResources().getString(R.string.Please_enter_password));
            return false;

        } else if (password.trim().length() < 6) {
            edPassword.setError(mContext.getResources().getString(R.string.The_Password_must_be_at_least_6_characters_long_));
            return false;

        } else if (confirmpassword.trim().equals("")) {
            edConfirmPassord.setError(mContext.getResources().getString(R.string.The_password_and_confirmation_password_do_not_match_));
            return false;
        } else if (!password.trim().equals(confirmpassword)) {
            edConfirmPassord.setError(mContext.getResources().getString(R.string.The_password_and_confirmation_password_do_not_match_));
            return false;
        }

        return true;

    }
*/
    public static String getCurrentDate() {

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);
        String formattedDate = df.format(Calendar.getInstance().getTime());

        return formattedDate;

    }

    public static String getNextCurrentDate() {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 1);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy-EEE", Locale.US);
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }


    public static boolean creditCardExpiryDateValidation(String month, String year) {
        boolean expired;

        if(Integer.parseInt(month)<(Calendar.getInstance().get(Calendar.MONTH) + 1) && Integer.parseInt(year)<=(Calendar.getInstance().get(Calendar.YEAR)))
            expired=true;

        else
            expired=false;

        return expired;
    }


    public static boolean isValidName(String name) {
        boolean valid = true;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(REGEX_NAME);
        for (int i = 0; i < name.length(); i++) {
            java.util.regex.Matcher m = p.matcher(Character.toString(name.charAt(i)));
            if (!m.matches()) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static boolean isValidMobileNumber(String no) {
        boolean valid = true;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("[0-9]");
        for (int i = 0; i < no.length(); i++) {
            java.util.regex.Matcher m = p.matcher(Character.toString(no.charAt(i)));
            if (!m.matches()) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static boolean Check(String ccNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
