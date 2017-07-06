    package com.holidayme.Constants;

/**
 * Created by santosh.patar on 29-07-2015.
 */
public class Constant {

    public final static int MAX_RECENT_SEARCH_LIMIT = 10;
    public final static int MY_SOCKET_TIMEOUT_MS = 2 * 60 * 1000;


    public static final String regVisa = "4[0-9]{12}(?:[0-9]{3})?";
    public static final String regMaster = "5[1-5][0-9]{14}";
    public static final String regAmEx = "3[47][0-9]{13}$";

    public static String REFRESH_FRAGMENT = "RefreshFragment";

    //-----live---
    public final static String build_version="prod";
    public final static String API_URL = "http://hotelapi.holidayme.com/V1/Hotel/Dynamic/";
    public final static String AUTHAPPURL = "http://auth.holidayme.com/V4";
    public final static String HOTEL_BOOKING_CONFORMATION_ENDPOINT = "http://api.holidayme.com/V10";
    public final static String REGION_API_ENDPOINT = "http://regionapi.holidayme.com";

   /* public static String ApplicationToken_new = "68dbae36e77c40488bf441ac4b6f33fc";
    public static String ApplicationSecret_new = "c651915c456546929ef3f737204ad2fd213b32477c5b4d8b8a8be3493ef9039e";*/
    public static String ApplicationName = "Android";

    public static final String ConfirmationURL_EN = "https://booking.holidayme.com/Confirmation";
    public static final String ConfirmationURL_AR = "https://arbooking.holidayme.com/Confirmation";
    public static final String PaymentExceptionURL ="/Book/PaymentException";
    public static final int Base_Version_Code=5;

    public static String AppToken_new_api ="68dbae36e77c40488bf441ac4b6f33fc";

    public static String USER_TRACKING_ID = "user_tracking_id";//this is one per application install.
    public static String SESSION_ID = "session_id";//this will change for every launch.

    // constant
    public final static String LANGUAGE = "LANGUAGE";
    public final static String CURRENCY = "CURRENCY";
    public final static String CURRENCY_USD = "USD";

    // Auth
    public static String CHECKUSERLOGINMETHOD = "/Login/CheckUserLogin";

    public static String GETAUTHORIZATIONINFORMATIONMETHOD = "/User/GetAuthorizationInformation";

    // registration
    public static String USERSIGNUP_METHOD = "/Login/UserSignUp";
    public static String ACTIVE_REGISTER_USER_METHOD = "/Login/ActiveRegisterUser";


    //forgot password
    public static String FORGOTPASSMETHOD = "/Login/UserForgotPassword";

    // Hotel Listing
    public static String HOTELSEARCHMETHOD = "HotelListingAutocomplete";


    // language code
    public static String ENGLISH_LANGUAGE_CODE = "en";
    public static String ARABIC_LANGUAGE_CODE = "ar";
    public static String USERPREFERENCE = "UserInfoPreference";


    // hotelListing
    public final static String HOTEL_LISTING = "HotelListingByCityId";


    public static String GETHOTELBOOKDETAILMETHOD = "/Booking/GetBookingDetails";
    public static String GETUSERPROFILEINFORMATION = "/User/GetUserProfileInformation";
    public static String BOOKINGHISTORY = "/Hotel/GetBookingList";
    public static String EDITPROFILE = "/User/EditUserProfileInformation";

    //currency
    public static final String CURRENCY_USA = "USD";
    public static final String CURRENCY_DUBAI = "AED";

    //
    public static int HOTELMAXREQUEST = 100;

    // BroadCastReciver
    public static String MY_ACTION = "myBroacast";
    public static String MY_ACTION_CONFIRMATION = "Confirmation";
    public static String MY_ACTION_REFRESH = "RefreshFragment";

    // font
    public static String HelveticaNeueLight = "HelveticaNeueLTStd-Lt.ttf";
    public static String HelveticaNeueMedium = "HelveticaNeueLTStd-Md.otf";
    public static String HelveticaNeueRoman = "HelveticaNeueLTStd-Roman.ttf";
    public static String NotoKufiArabic_Bold="NotoKufiArabic-Bold.ttf";
    public static String NotoKufiArabic_Regular="NotoKufiArabic-Regular.ttf";


    // nabigation drawer
    public static int NAVIGATIONDRAWER = 10001;
    public static int CURRENYADAPTER = 20002;
    public static final String ENGLISH_PRIVACYPOLICY_URL = "https://www.holidayme.com/PrivacyPolicy";
    public static final String ENGLISH_TERMSOFUSE_URL = "https://www.holidayme.com/TermsAndConditions";
    public static final String ARABIC_PRIVACYPOLICY_URL = "https://www.holidayme.com.sa/PrivacyPolicy";
    public static final String ARABIC_TERMSOFUSE_URL = "https://www.holidayme.com.sa/TermsAndConditions";

    public enum Date {
        DAY, MONTH, YEAR, DATE
    }

    public static String COUPON_CODE_URL = "https://hme-images.azureedge.net/images/app/coupon.txt";

    public static String IS_UPDATE_AVAILABLE = "is_update_available";
    public static String CHECK_UPDATE_URL = "https://hmeimages.blob.core.windows.net/images/app/appupdate.png";

    //===============Nwe Api=====================
    public static String UserAgent = "UserAgent";
    public static String ContentType = "application/json; charset=utf-8";
    public static String Referrer = "Referrer";
    //public static String AffiliateId = "4";


    public static String HOTELDETAIL = "HotelDetails";
    public static String HOTELLISTING = "HotelListingByCityId";
    public static String TRIPADVISORDATA = "HotelTripAdvisorData";
    public static String GETDESTINATIONIDMETHOD = "GetDestinationForHotel";
    public static String HOTELSRATES = "HotelRates";
    public static String ROOMDETAILS = "RoomDetails";
    public static String GENERATETEMPID = "/InsertBookingDetailsInMongo";
    public static String GETBOOKINGDETAIL = "/Hotel/GetTempPropertyBookingDetails";
    public static String INSERTBOOKINGDETAIL = "/Booking/InsertBookingDetails";
    public static String APPLYCOUPNCODE = "/Common/ApplyCouponCode";
    public static String GETAREA = "/Common/GetAreasForCodCites";
    public static String HOTEL_AUTO_COMPLETE_URL = "HotelAutocomplete";
    public final static String NEAR_ME_URL = "/city/";
    public final static String CITY_AUTO_COMPLETE_ARABIC_URL = "/autocomplete/ar";
    public final static String CITY_AUTO_COMPLETE_ENG_URL = "/autocomplete/en";
    public static final String GET_MY_DETAIL_FROM_IP = "http://freegeoip.net/json/";

    public static String ApplicationToken_new = "d2eca3b03a44432c9e562504988e9468";
    public static String ApplicationSecret_new = "c20fb40c92df44f8a8b43005105f6a1e627f32961c894b649af690cce8e06da6";


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

   public static final String SEND_EMAIL_TO_USER_METHOD="/Home/SendBookingNotificationToUser";
   public static final String REQUEST_CANCELLATION_METHOD="/Common/CreateCrmCancellationIncident";

    public static final String Login_App_Token_ar="d2eca3b03a44432c9e562504988e9468";
    public static final String Login_App_Token_en="d2eca3b03a44432c9e562504988e9468";

    public static final String Login_Url_ar="https://login.holidayme.com/ar?popupLogin=0&AppToken=";
    public static final String Login_Url_en="https://login.holidayme.com/?AppToken=";

    public static final String Login_callBack_Url_ar="&CallBackUrl=https://www.holidayme.com/signin&expiry=525600&header=0";
    public static final String Login_CallBack_Url_en="&CallBackUrl=https://www.holidayme.com/signin&expiry=525600&header=0&popupLogin=0";

    public static final String LOGIN_SUCCESS_CALLBACK_URL = "https://www.holidayme.com/";
    public static final String LOGIN_RESPONSE_URL = "https://www.holidayme.com/Base/signin?";
    public static final String GET_USER_DETAILS = "https://login.holidayme.com/account-info?UserToken=";
    public static final String LOGOUT_URL = "http://login.holidayme.com/logout";

    public static final String PAYMENT_OPTIONS_URL ="https://payme.holidayme.com/Payment/Options";
    public  static  boolean getawayActive=false;


    public  static  final  String GetGetawaysURL="/getaways/packages/";
    public static final String getawaysListUrl="/city/packages/";
    public static final String checkAvailabilityURL= "/package/check/availability/";
    public static final String insertBookingDetailURL= "/booking/insert/";
    public static final String getawaysGetBookingDetailURL= "/booking/Details/";
    public static final String STAYCATION_PACKAGE_DETAILS = "/package/details/";
    public static final String STAYCATION_PACKAGE_ALLOCATION = "/package/allocation/";

    public static final String STATCATION_AMENITIES = "/GetAmenityList";
    public static final String STAYCATION_NEAR_AND_AROUND = "/HotelBasicInformation?hotelid=";

    public static final String getawaysBookingReturnEnURL="https://getaway.holidayme.com/Book/PaymentGetAwayReceipt";
    public static final String getawaysBookingReturnArURL="https://getaway.holidayme.com.sa/Book/PaymentGetAwayReceipt";

    public static final String STAYCATION_FRESHDESK = "http://devfreshdeskapi.holzoo.com:4017/api/Ticket/CreateTicket";

    public static final String GetawaysEndPointUrl="http://getawayapi.holidayme.com/GetAwayAPI";
    public   static  boolean dateChangeFlag=false;
    public  static  boolean filterChange=false;
    public  static  boolean clearFilter=false;
    public  static  boolean filterFlag=false;
    public  static  boolean autoCompleteHandlerFlag=true;
    public  static  boolean animationFlag=false;
    public static final String    productId="17000001678";
    public static final String freshDeskKey="lmaQXconehkxyk2JhXvB";
    public static final String FreshDeskURl="https://holidayme.freshdesk.com/api/v2/tickets";
    public static final String AffilateId_en="4";
    public static final String AffilateId_ar="6";

}
