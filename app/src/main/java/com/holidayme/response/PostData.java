package com.holidayme.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shaikh.salim on 7/11/2016.
 */

public class PostData  implements Parcelable{


     String  Amount,BookingReference,BookingUrl,Currency,Email,FirstName,IpAddress, LanguageCode,LastName ,PhoneNumber,ReturnUrl,Signature,ISDCode,PaymentMode;
    int BookingPropertyID,Entity;





   /* public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getBookingReference() {
        return BookingReference;
    }

    public void setBookingReference(String bookingReference) {
        BookingReference = bookingReference;
    }

    public String getBookingUrl() {
        return BookingUrl;
    }

    public void setBookingUrl(String bookingUrl) {
        BookingUrl = bookingUrl;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String ipAddress) {
        IpAddress = ipAddress;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }

    public void setLanguageCode(String languageCode) {
        LanguageCode = languageCode;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getReturnUrl() {
        return ReturnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        ReturnUrl = returnUrl;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public int getBookingPropertyID() {
        return BookingPropertyID;
    }

    public void setBookingPropertyID(int bookingPropertyID) {
        BookingPropertyID = bookingPropertyID;
    }

    public int getEntity() {
        return Entity;
    }

    public void setEntity(int entity) {
        Entity = entity;
    }


    private String merchant_defined_data31;

    private String merchant_defined_data30;

    private String bill_to_email;

    private String merchant_defined_data32;

    private String card_number;

    private String transaction_type;

    private String bill_to_address_line1;

    private String amount;

    private String signed_field_names;

    private String merchant_defined_data12;

    private String ignore_cvn;

    private String ScriptToRender;

    private String merchant_defined_data1;

    private String merchant_defined_data2;

    private String merchant_defined_data3;

    private String merchant_defined_data4;

    private String signature;

    private String merchant_defined_data5;

    private String merchant_defined_data6;

    private String bill_to_address_city;

    private String merchant_defined_data9;

    private String merchant_defined_data8;

    private String merchant_defined_data7;

    private String signed_date_time;

    private String merchant_defined_data18;

    private String merchant_defined_data48;

    private String merchant_defined_data47;

    private String merchant_defined_data46;

    private String merchant_defined_data49;

    private String bill_to_address_postal_code;

    private String merchant_defined_data45;

    private String reference_number;

    private String card_cvn;

    private String merchant_defined_data70;

    private String device_fingerprint_id;

    private String card_expiry_date;

    private String payment_method;

    private String ignore_avs;

    private String profile_id;

    private String merchant_defined_data11;

    private String locale;

    private String merchant_defined_data10;

    private String merchant_defined_data13;

    private String transaction_uuid;

    private String merchant_defined_data14;

    private String merchant_defined_data19;

    private String currency;

    private String access_key;

    private String merchant_defined_data60;

    private String unsigned_field_names;

    private String bill_to_address_state;

    private String merchant_defined_data21;

    private String customer_ip_address;

    private String merchant_defined_data25;

    private String bill_to_address_country;

    private String bill_to_forename;

    private String card_type;

    private String merchant_defined_data50;

    private String merchant_defined_data51;

    private String bill_to_surname;

    public String getMerchant_defined_data31() {
        return merchant_defined_data31;
    }

    public void setMerchant_defined_data31(String merchant_defined_data31) {
        this.merchant_defined_data31 = merchant_defined_data31;
    }

    public String getMerchant_defined_data30() {
        return merchant_defined_data30;
    }

    public void setMerchant_defined_data30(String merchant_defined_data30) {
        this.merchant_defined_data30 = merchant_defined_data30;
    }

    public String getBill_to_email() {
        return bill_to_email;
    }

    public void setBill_to_email(String bill_to_email) {
        this.bill_to_email = bill_to_email;
    }

    public String getMerchant_defined_data32() {
        return merchant_defined_data32;
    }

    public void setMerchant_defined_data32(String merchant_defined_data32) {
        this.merchant_defined_data32 = merchant_defined_data32;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getBill_to_address_line1() {
        return bill_to_address_line1;
    }

    public void setBill_to_address_line1(String bill_to_address_line1) {
        this.bill_to_address_line1 = bill_to_address_line1;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSigned_field_names() {
        return signed_field_names;
    }

    public void setSigned_field_names(String signed_field_names) {
        this.signed_field_names = signed_field_names;
    }

    public String getMerchant_defined_data12() {
        return merchant_defined_data12;
    }

    public void setMerchant_defined_data12(String merchant_defined_data12) {
        this.merchant_defined_data12 = merchant_defined_data12;
    }

    public String getIgnore_cvn() {
        return ignore_cvn;
    }

    public void setIgnore_cvn(String ignore_cvn) {
        this.ignore_cvn = ignore_cvn;
    }

    public String getScriptToRender() {
        return ScriptToRender;
    }

    public void setScriptToRender(String ScriptToRender) {
        this.ScriptToRender = ScriptToRender;
    }

    public String getMerchant_defined_data1() {
        return merchant_defined_data1;
    }

    public void setMerchant_defined_data1(String merchant_defined_data1) {
        this.merchant_defined_data1 = merchant_defined_data1;
    }

    public String getMerchant_defined_data2() {
        return merchant_defined_data2;
    }

    public void setMerchant_defined_data2(String merchant_defined_data2) {
        this.merchant_defined_data2 = merchant_defined_data2;
    }

    public String getMerchant_defined_data3() {
        return merchant_defined_data3;
    }

    public void setMerchant_defined_data3(String merchant_defined_data3) {
        this.merchant_defined_data3 = merchant_defined_data3;
    }

    public String getMerchant_defined_data4() {
        return merchant_defined_data4;
    }

    public void setMerchant_defined_data4(String merchant_defined_data4) {
        this.merchant_defined_data4 = merchant_defined_data4;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMerchant_defined_data5() {
        return merchant_defined_data5;
    }

    public void setMerchant_defined_data5(String merchant_defined_data5) {
        this.merchant_defined_data5 = merchant_defined_data5;
    }

    public String getMerchant_defined_data6() {
        return merchant_defined_data6;
    }

    public void setMerchant_defined_data6(String merchant_defined_data6) {
        this.merchant_defined_data6 = merchant_defined_data6;
    }

    public String getBill_to_address_city() {
        return bill_to_address_city;
    }

    public void setBill_to_address_city(String bill_to_address_city) {
        this.bill_to_address_city = bill_to_address_city;
    }

    public String getMerchant_defined_data9() {
        return merchant_defined_data9;
    }

    public void setMerchant_defined_data9(String merchant_defined_data9) {
        this.merchant_defined_data9 = merchant_defined_data9;
    }

    public String getMerchant_defined_data8() {
        return merchant_defined_data8;
    }

    public void setMerchant_defined_data8(String merchant_defined_data8) {
        this.merchant_defined_data8 = merchant_defined_data8;
    }

    public String getMerchant_defined_data7() {
        return merchant_defined_data7;
    }

    public void setMerchant_defined_data7(String merchant_defined_data7) {
        this.merchant_defined_data7 = merchant_defined_data7;
    }

    public String getSigned_date_time() {
        return signed_date_time;
    }

    public void setSigned_date_time(String signed_date_time) {
        this.signed_date_time = signed_date_time;
    }

    public String getMerchant_defined_data18() {
        return merchant_defined_data18;
    }

    public void setMerchant_defined_data18(String merchant_defined_data18) {
        this.merchant_defined_data18 = merchant_defined_data18;
    }

    public String getMerchant_defined_data48() {
        return merchant_defined_data48;
    }

    public void setMerchant_defined_data48(String merchant_defined_data48) {
        this.merchant_defined_data48 = merchant_defined_data48;
    }

    public String getMerchant_defined_data47() {
        return merchant_defined_data47;
    }

    public void setMerchant_defined_data47(String merchant_defined_data47) {
        this.merchant_defined_data47 = merchant_defined_data47;
    }

    public String getMerchant_defined_data46() {
        return merchant_defined_data46;
    }

    public void setMerchant_defined_data46(String merchant_defined_data46) {
        this.merchant_defined_data46 = merchant_defined_data46;
    }

    public String getMerchant_defined_data49() {
        return merchant_defined_data49;
    }

    public void setMerchant_defined_data49(String merchant_defined_data49) {
        this.merchant_defined_data49 = merchant_defined_data49;
    }

    public String getBill_to_address_postal_code() {
        return bill_to_address_postal_code;
    }

    public void setBill_to_address_postal_code(String bill_to_address_postal_code) {
        this.bill_to_address_postal_code = bill_to_address_postal_code;
    }

    public String getMerchant_defined_data45() {
        return merchant_defined_data45;
    }

    public void setMerchant_defined_data45(String merchant_defined_data45) {
        this.merchant_defined_data45 = merchant_defined_data45;
    }

    public String getReference_number() {
        return reference_number;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public String getCard_cvn() {
        return card_cvn;
    }

    public void setCard_cvn(String card_cvn) {
        this.card_cvn = card_cvn;
    }

    public String getMerchant_defined_data70() {
        return merchant_defined_data70;
    }

    public void setMerchant_defined_data70(String merchant_defined_data70) {
        this.merchant_defined_data70 = merchant_defined_data70;
    }

    public String getDevice_fingerprint_id() {
        return device_fingerprint_id;
    }

    public void setDevice_fingerprint_id(String device_fingerprint_id) {
        this.device_fingerprint_id = device_fingerprint_id;
    }

    public String getCard_expiry_date() {
        return card_expiry_date;
    }

    public void setCard_expiry_date(String card_expiry_date) {
        this.card_expiry_date = card_expiry_date;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getIgnore_avs() {
        return ignore_avs;
    }

    public void setIgnore_avs(String ignore_avs) {
        this.ignore_avs = ignore_avs;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }

    public String getMerchant_defined_data11() {
        return merchant_defined_data11;
    }

    public void setMerchant_defined_data11(String merchant_defined_data11) {
        this.merchant_defined_data11 = merchant_defined_data11;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMerchant_defined_data10() {
        return merchant_defined_data10;
    }

    public void setMerchant_defined_data10(String merchant_defined_data10) {
        this.merchant_defined_data10 = merchant_defined_data10;
    }

    public String getMerchant_defined_data13() {
        return merchant_defined_data13;
    }

    public void setMerchant_defined_data13(String merchant_defined_data13) {
        this.merchant_defined_data13 = merchant_defined_data13;
    }

    public String getTransaction_uuid() {
        return transaction_uuid;
    }

    public void setTransaction_uuid(String transaction_uuid) {
        this.transaction_uuid = transaction_uuid;
    }

    public String getMerchant_defined_data14() {
        return merchant_defined_data14;
    }

    public void setMerchant_defined_data14(String merchant_defined_data14) {
        this.merchant_defined_data14 = merchant_defined_data14;
    }

    public String getMerchant_defined_data19() {
        return merchant_defined_data19;
    }

    public void setMerchant_defined_data19(String merchant_defined_data19) {
        this.merchant_defined_data19 = merchant_defined_data19;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public String getMerchant_defined_data60() {
        return merchant_defined_data60;
    }

    public void setMerchant_defined_data60(String merchant_defined_data60) {
        this.merchant_defined_data60 = merchant_defined_data60;
    }

    public String getUnsigned_field_names() {
        return unsigned_field_names;
    }

    public void setUnsigned_field_names(String unsigned_field_names) {
        this.unsigned_field_names = unsigned_field_names;
    }

    public String getBill_to_address_state() {
        return bill_to_address_state;
    }

    public void setBill_to_address_state(String bill_to_address_state) {
        this.bill_to_address_state = bill_to_address_state;
    }

    public String getMerchant_defined_data21() {
        return merchant_defined_data21;
    }

    public void setMerchant_defined_data21(String merchant_defined_data21) {
        this.merchant_defined_data21 = merchant_defined_data21;
    }

    public String getCustomer_ip_address() {
        return customer_ip_address;
    }

    public void setCustomer_ip_address(String customer_ip_address) {
        this.customer_ip_address = customer_ip_address;
    }

    public String getMerchant_defined_data25() {
        return merchant_defined_data25;
    }

    public void setMerchant_defined_data25(String merchant_defined_data25) {
        this.merchant_defined_data25 = merchant_defined_data25;
    }

    public String getBill_to_address_country() {
        return bill_to_address_country;
    }

    public void setBill_to_address_country(String bill_to_address_country) {
        this.bill_to_address_country = bill_to_address_country;
    }

    public String getBill_to_forename() {
        return bill_to_forename;
    }

    public void setBill_to_forename(String bill_to_forename) {
        this.bill_to_forename = bill_to_forename;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getMerchant_defined_data50() {
        return merchant_defined_data50;
    }

    public void setMerchant_defined_data50(String merchant_defined_data50) {
        this.merchant_defined_data50 = merchant_defined_data50;
    }

    public String getMerchant_defined_data51() {
        return merchant_defined_data51;
    }

    public void setMerchant_defined_data51(String merchant_defined_data51) {
        this.merchant_defined_data51 = merchant_defined_data51;
    }

    public String getBill_to_surname() {
        return bill_to_surname;
    }

    public void setBill_to_surname(String bill_to_surname) {
        this.bill_to_surname = bill_to_surname;
    }


    protected PostData(Parcel in) {
        merchant_defined_data31 = in.readString();
        merchant_defined_data30 = in.readString();
        bill_to_email = in.readString();
        merchant_defined_data32 = in.readString();
        card_number = in.readString();
        transaction_type = in.readString();
        bill_to_address_line1 = in.readString();
        amount = in.readString();
        signed_field_names = in.readString();
        merchant_defined_data12 = in.readString();
        ignore_cvn = in.readString();
        ScriptToRender = in.readString();
        merchant_defined_data1 = in.readString();
        merchant_defined_data2 = in.readString();
        merchant_defined_data3 = in.readString();
        merchant_defined_data4 = in.readString();
        signature = in.readString();
        merchant_defined_data5 = in.readString();
        merchant_defined_data6 = in.readString();
        bill_to_address_city = in.readString();
        merchant_defined_data9 = in.readString();
        merchant_defined_data8 = in.readString();
        merchant_defined_data7 = in.readString();
        signed_date_time = in.readString();
        merchant_defined_data18 = in.readString();
        merchant_defined_data48 = in.readString();
        merchant_defined_data47 = in.readString();
        merchant_defined_data46 = in.readString();
        merchant_defined_data49 = in.readString();
        bill_to_address_postal_code = in.readString();
        merchant_defined_data45 = in.readString();
        reference_number = in.readString();
        card_cvn = in.readString();
        merchant_defined_data70 = in.readString();
        device_fingerprint_id = in.readString();
        card_expiry_date = in.readString();
        payment_method = in.readString();
        ignore_avs = in.readString();
        profile_id = in.readString();
        merchant_defined_data11 = in.readString();
        locale = in.readString();
        merchant_defined_data10 = in.readString();
        merchant_defined_data13 = in.readString();
        transaction_uuid = in.readString();
        merchant_defined_data14 = in.readString();
        merchant_defined_data19 = in.readString();
        currency = in.readString();
        access_key = in.readString();
        merchant_defined_data60 = in.readString();
        unsigned_field_names = in.readString();
        bill_to_address_state = in.readString();
        merchant_defined_data21 = in.readString();
        customer_ip_address = in.readString();
        merchant_defined_data25 = in.readString();
        bill_to_address_country = in.readString();
        bill_to_forename = in.readString();
        card_type = in.readString();
        merchant_defined_data50 = in.readString();
        merchant_defined_data51 = in.readString();
        bill_to_surname = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(merchant_defined_data31);
        dest.writeString(merchant_defined_data30);
        dest.writeString(bill_to_email);
        dest.writeString(merchant_defined_data32);
        dest.writeString(card_number);
        dest.writeString(transaction_type);
        dest.writeString(bill_to_address_line1);
        dest.writeString(amount);
        dest.writeString(signed_field_names);
        dest.writeString(merchant_defined_data12);
        dest.writeString(ignore_cvn);
        dest.writeString(ScriptToRender);
        dest.writeString(merchant_defined_data1);
        dest.writeString(merchant_defined_data2);
        dest.writeString(merchant_defined_data3);
        dest.writeString(merchant_defined_data4);
        dest.writeString(signature);
        dest.writeString(merchant_defined_data5);
        dest.writeString(merchant_defined_data6);
        dest.writeString(bill_to_address_city);
        dest.writeString(merchant_defined_data9);
        dest.writeString(merchant_defined_data8);
        dest.writeString(merchant_defined_data7);
        dest.writeString(signed_date_time);
        dest.writeString(merchant_defined_data18);
        dest.writeString(merchant_defined_data48);
        dest.writeString(merchant_defined_data47);
        dest.writeString(merchant_defined_data46);
        dest.writeString(merchant_defined_data49);
        dest.writeString(bill_to_address_postal_code);
        dest.writeString(merchant_defined_data45);
        dest.writeString(reference_number);
        dest.writeString(card_cvn);
        dest.writeString(merchant_defined_data70);
        dest.writeString(device_fingerprint_id);
        dest.writeString(card_expiry_date);
        dest.writeString(payment_method);
        dest.writeString(ignore_avs);
        dest.writeString(profile_id);
        dest.writeString(merchant_defined_data11);
        dest.writeString(locale);
        dest.writeString(merchant_defined_data10);
        dest.writeString(merchant_defined_data13);
        dest.writeString(transaction_uuid);
        dest.writeString(merchant_defined_data14);
        dest.writeString(merchant_defined_data19);
        dest.writeString(currency);
        dest.writeString(access_key);
        dest.writeString(merchant_defined_data60);
        dest.writeString(unsigned_field_names);
        dest.writeString(bill_to_address_state);
        dest.writeString(merchant_defined_data21);
        dest.writeString(customer_ip_address);
        dest.writeString(merchant_defined_data25);
        dest.writeString(bill_to_address_country);
        dest.writeString(bill_to_forename);
        dest.writeString(card_type);
        dest.writeString(merchant_defined_data50);
        dest.writeString(merchant_defined_data51);
        dest.writeString(bill_to_surname);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PostData> CREATOR = new Parcelable.Creator<PostData>() {
        @Override
        public PostData createFromParcel(Parcel in) {
            return new PostData(in);
        }

        @Override
        public PostData[] newArray(int size) {
            return new PostData[size];
        }
    };*/

    public PostData() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Amount);
        dest.writeString(this.BookingReference);
        dest.writeString(this.BookingUrl);
        dest.writeString(this.Currency);
        dest.writeString(this.Email);
        dest.writeString(this.FirstName);
        dest.writeString(this.IpAddress);
        dest.writeString(this.LanguageCode);
        dest.writeString(this.LastName);
        dest.writeString(this.PhoneNumber);
        dest.writeString(this.ReturnUrl);
        dest.writeString(this.Signature);
        dest.writeString(this.ISDCode);
        dest.writeString(this.PaymentMode);
        dest.writeInt(this.BookingPropertyID);
        dest.writeInt(this.Entity);
    }

    protected PostData(Parcel in) {
        this.Amount = in.readString();
        this.BookingReference = in.readString();
        this.BookingUrl = in.readString();
        this.Currency = in.readString();
        this.Email = in.readString();
        this.FirstName = in.readString();
        this.IpAddress = in.readString();
        this.LanguageCode = in.readString();
        this.LastName = in.readString();
        this.PhoneNumber = in.readString();
        this.ReturnUrl = in.readString();
        this.Signature = in.readString();
        this.ISDCode = in.readString();
        this.PaymentMode = in.readString();
        this.BookingPropertyID = in.readInt();
        this.Entity = in.readInt();
    }

    public static final Creator<PostData> CREATOR = new Creator<PostData>() {
        @Override
        public PostData createFromParcel(Parcel source) {
            return new PostData(source);
        }

        @Override
        public PostData[] newArray(int size) {
            return new PostData[size];
        }
    };
}
