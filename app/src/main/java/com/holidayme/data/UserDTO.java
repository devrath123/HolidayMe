package com.holidayme.data;

/**
 * Created by santosh.patar on 10-08-2015.
 * <p/>
 * <p> to save user information </p>
 * <p> This is singleton class , UserDto calss will  use through out of appplication to get language and currency </p>
 */
public class UserDTO {

    private volatile static UserDTO instance;

    private String firstName,lastName,language,currency,token,userSecret,userIP,cityName, countryCode,userName,emailID,destinationName,SessionId,UserTrackingId;
    private double latitude,longitude;
    private int cityId,drawerSelectedPosition,AccountId;

    /**
     * is hotel to determine selected destination is hotel or City
     */
    private boolean isHotel;

    private UserDTO() {

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public static UserDTO getInstance() {
        return instance;
    }

    public static void setInstance(UserDTO instance) {
        UserDTO.instance = instance;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserSecret() {
        return userSecret;
    }

    public void setUserSecret(String userSecret) {
        this.userSecret = userSecret;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }



    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDrawerSelectedPosition() {
        return drawerSelectedPosition;
    }

    public void setDrawerSelectedPosition(int drawerSelectedPosition) {
        this.drawerSelectedPosition = drawerSelectedPosition;
    }

    public boolean isHotel() {
        return isHotel;
    }

    public void setHotel(boolean hotel) {
        isHotel = hotel;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getUserTrackingId() {
        return UserTrackingId;
    }

    public void setUserTrackingId(String userTrackingId) {
        UserTrackingId = userTrackingId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Makes sure that only one instance of StoreCusomerRegistrationData is possible.
     * <p>double checked locking principle is used.</p>
     *
     * @return
     */
    public static synchronized UserDTO getUserDTO() {
        if (instance == null) {
            synchronized (UserDTO.class) {
                if (instance == null) {
                    instance = new UserDTO();
                }
            }
        }

        return instance;
    }


}
