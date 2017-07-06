package com.holidayme.Constants;

/**
 * Created by santosh.patar on 29-07-2015.
 */
public class Constant {

    public final static int MAX_RECENT_SEARCH_LIMIT = 10;

    public static final String regVisa = "4[0-9]{12}(?:[0-9]{3})?";
    public static final String regMaster = "5[1-5][0-9]{14}";
    public static final String regAmEx = "3[47][0-9]{13}$";

    public   static  boolean dateChangeFlag=false;
    public  static  boolean filterChange=false;
    public  static  boolean clearFilter=false;
    public  static  boolean filterFlag=false;
    public  static  boolean autoCompleteHandlerFlag=true;
    public  static  boolean animationFlag=false;

    public  static  boolean getawayActive=false;

    // ----- qa API Url's
    public final static String build_version="qa";

    public final static String API_URL = "http://qa-hotelapi.holzoo.com/v1/hotel/dynamic";
    public final static String HOTEL_BOOKING_CONFORMATION_ENDPOINT = "http://qaapi.holzoo.com/v10";
    public final static String REGION_API_ENDPOINT = "http://qa-terralatioapi.holzoo.com";

  /*  public static String ApplicationToken = "3593c6d814404a5f8a634480e03fab69";
    public static String ApplicationSecret = "55c6382f8763438eacd6b57cbf10f463f4d6e58050254592a8f7f6aa8244f252";*/

    public static String ApplicationToken_new = "f172e395ae974f7ab971a1d5d4cfed7c";
    public static String ApplicationSecret_new = "68b934b7b316431d82e59fd8020667ff80b2ac54985a4041aa18bdc585a5b924";

    public static String ApplicationName = "Android";
    //public static final String ConfirmationURL_EN = "https://qabooking.holzoo.com/Confirmation";
    public static final String ConfirmationURL_AR = "https://qaarbooking.holzoo.com/Confirmation";
    public static final String PaymentExceptionURL ="/Book/PaymentException";
    public static final String ConfirmationURL_EN = "https://qahotel.holzoo.com/Confirmation";


    public static String USER_TRACKING_ID = "UserTrackingId";//this is one per application install.
    public static String SESSION_ID = "SessionId";//this will change for every launch.

    //currency
    public static final String CURRENCY_USA = "USD";
    public static final String CURRENCY_DUBAI = "AED";

    // constant
    public final static String LANGUAGE = "LANGUAGE";
    public final static String CURRENCY = "CURRENCY";
    public final static String CURRENCY_USD = "USD";


    // Hotel Listing
    public static String HOTELSEARCHMETHOD = "/HotelListingAutocomplete";

    // language code
    public static String ENGLISH_LANGUAGE_CODE = "en";
    public static String ARABIC_LANGUAGE_CODE = "ar";
    public static String USERPREFERENCE = "UserInfoPreference";

    // index page

    public static  String CHECK_IN_DATE="CheckInDate";
    public static  String CHECK_OUT_DATE="CheckOutDate";
    public static  String HOTEL_ID="HotelId";
    public static  String HOTEL_NAME="HotelName";
    public static  String ROOMSCOUNT="RoomsCount";
    public static  String NIGHTSCOUNT="NightsCount";
    public static  String HOTEL_SEARCH="HotelSearch";
    public  static  String  MIN_SELECTED="minSelected";
    public  static  String  MAX_SELECTED="maxSelected";

    // filters for hotelListing

    public static String FILTER_TYPE="FILTERTYPE";
    public  static  String HOTEL_LISTING_RESPONSE="HotelListingResponse";

    // hotelListing
    public final static String HOTEL_LISTING = "/HotelListingByCityId";

    // hotel details
    public static String GETHOTELBOOKDETAILMETHOD = "/Booking/GetBookingDetails";
    public static String BOOKINGHISTORY = "/Hotel/GetBookingList";

    //
    public static int HOTELMAXREQUEST = 100;

    // BroadCastReciver
    public static String MY_ACTION = "myBroacast";
    public static String MY_ACTION_CONFIRMATION = "Confirmation";
    public static String REFRESH_FRAGMENT = "RefreshFragment";

    // font
    public static String HelveticaNeueLight = "HelveticaNeueLTStd-Lt.ttf";
    public static String HelveticaNeueRoman = "HelveticaNeueLTStd-Roman.ttf";
    public static String NotoKufiArabic_Bold="NotoKufiArabic-Bold.ttf";
    public static String NotoKufiArabic_Regular="NotoKufiArabic-Regular.ttf";


    // nabigation drawer
    public static int NAVIGATIONDRAWER = 10001;
    public static final String ENGLISH_PRIVACYPOLICY_URL = "http://qa.holzoo.com/PrivacyPolicy";
    public static final String ENGLISH_TERMSOFUSE_URL = "http://qa.holzoo.com/TermsAndConditions";
    public static final String ARABIC_PRIVACYPOLICY_URL = "http://qa.holzoo.com/PrivacyPolicy";
    public static final String ARABIC_TERMSOFUSE_URL = "http://qaar.holzoo.com/TermsAndConditions";

    public enum Date {
        DAY, MONTH, YEAR, DATE;
    }

    public static String COUPON_CODE_URL = "https://hme-images.azureedge.net/images/app/coupon.txt";


    //===============Nwe Api=====================

    public static String UserAgent = "UserAgent";
    public static String ContentType = "application/json; charset=utf-8";
    public static String Referrer = "Referrer";
    //public static String AffiliateId = "4";



    public static String HOTELDETAIL = "/HotelDetails";
    public static String HOTELLISTING = "/HotelListingByCityId";
    public static String TRIPADVISORDATA = "/HotelTripAdvisorData";
    public static String GETDESTINATIONIDMETHOD = "/GetDestinationForHotel";
    public static String HOTELSRATES = "/HotelRates";
    public static String ROOMDETAILS = "/RoomDetails";
    public static String GENERATETEMPID = "/InsertBookingDetailsInMongo";
    public static String GETBOOKINGDETAIL = "/Hotel/GetTempPropertyBookingDetails";
    public static String INSERTBOOKINGDETAIL = "/Booking/InsertBookingDetails";
    public static String APPLYCOUPNCODE = "/Common/ApplyCouponCode";
    public static String GETAREA = "/Common/GetAreasForCodCites";
    public static String HOTEL_AUTO_COMPLETE_URL = "/HotelAutocomplete";
    public final static String NEAR_ME_URL = "/city/";
    public final static String CITY_AUTO_COMPLETE_ARABIC_URL = "/autocomplete/ar";
    public final static String CITY_AUTO_COMPLETE_ENG_URL = "/autocomplete/en";
    public static final String GET_MY_DETAIL_FROM_IP = "http://freegeoip.net/json/";
    public static final String SEND_EMAIL_TO_USER_METHOD="/Home/SendBookingNotificationToUser";
    public static final String REQUEST_CANCELLATION_METHOD="/Common/CreateCrmCancellationIncident";

    public static final String Login_App_Token_ar="11678a959e7c4175969fc81e5d4ac481";
    public static final String Login_App_Token_en="bb66d8f07ccc4f6da97bbd7ac541ae7a";

    public static final String Login_Url_ar="https://qaloblogin.holzoo.com/ar?popupLogin=0&AppToken=";
    //public static final String Login_Url_en="https://login.holzoo.com/?AppToken=";
    public static final String Login_Url_en="https://qaloblogin.holzoo.com/?AppToken=";

    public static final String Login_callBack_Url_ar="&CallBackUrl=https://qa.holzoo.com/signin&expiry=525600&header=0";
    public static final String Login_CallBack_Url_en = "&CallBackUrl=https://qa.holzoo.com/signin&expiry=525600&header=0&popupLogin=0";
    //public static final String Login_CallBack_Url_en="&CallBackUrl=https://dev.holzoo.com/signin&expiry=525600&header=0&popupLogin=0";
    public static final String LOGIN_SUCCESS_CALLBACK_URL = "https://qa.holzoo.com/";

    public static final String LOGIN_RESPONSE_URL = "https://qa.holzoo.com/Base/signin?";
    public static final String GET_USER_DETAILS = "https://qaloblogin.holzoo.com/account-info?UserToken=";
    public static final String LOGOUT_URL = "https://qaloblogin.holzoo.com/logout?callbackUrl=https://qa.holzoo.com/";

    public  static  final  String GetGetawaysURL="/getaways/packages/";
    public static final String getawaysListUrl="/city/packages/";
    public static final String checkAvailabilityURL= "/package/check/availability/";
    public static final String insertBookingDetailURL= "/booking/insert/";
    public static final String getawaysGetBookingDetailURL= "/booking/Details/";
    public static final String STAYCATION_PACKAGE_DETAILS = "/package/details/";
    public static final String STAYCATION_PACKAGE_ALLOCATION = "/package/allocation/";


    public static final String STATCATION_AMENITIES = "/GetAmenityList";
    public static final String STAYCATION_NEAR_AND_AROUND = "/HotelBasicInformation?hotelid=";

    public static final String getawaysBookingReturnEnURL="http://qagetaway.holzoo.com/Book/PaymentGetAwayReceipt";
    public static final String getawaysBookingReturnArURL="http://qagetaway.holzoo.com/Book/PaymentGetAwayReceipt";

    public static final String STAYCATION_FRESHDESK = "http://devfreshdeskapi.holzoo.com:4017/api/Ticket/CreateTicket";
    public static final String PAYMENT_OPTIONS_URL ="http://qapayme.holzoo.com/Payment/options";


    public static final String GetawaysEndPointUrl="http://qa-getawayapi.holzoo.com/GetAwayAPI";
    public static final String    productId="17000001664";
    public static final String freshDeskKey="UqjiRRArqQ6RACLFNfL";
    public static final String FreshDeskURl="https://holidaymetrial.freshdesk.com/api/v2/tickets";
    public static final String AffilateId_en="4";
    public static final String AffilateId_ar="8";



}
