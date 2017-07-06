package com.holidayme.data;

/**
 * Created by supriya.sakore on 26-10-2015.
 */
public class UserProfileDto {

    private int Id;
    private String Email;
    private String FirstName;
    private String LastName;
    private String DisplayName;
    private String Password;
    private boolean IsAdmin;
    private int RoleId;
    private String ReportingTo;
    private String Salutation;
    private String Address;
    private String City;
    private String ISDCode;
    private String MobileNumber;
    private String CountryOfResidence;
    private String PassportCountry;
    private String TempPassword;
    private String UserTypeId;
    private String MiddleName;
    private String IPAddress;
    private String State;
    private String PostalCode;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        IsAdmin = isAdmin;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    public String getReportingTo() {
        return ReportingTo;
    }

    public void setReportingTo(String reportingTo) {
        ReportingTo = reportingTo;
    }

    public String getSalutation() {
        return Salutation;
    }

    public void setSalutation(String salutation) {
        Salutation = salutation;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getISDCode() {
        return ISDCode;
    }

    public void setISDCode(String ISDCode) {
        this.ISDCode = ISDCode;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getCountryOfResidence() {
        return CountryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        CountryOfResidence = countryOfResidence;
    }

    public String getTempPassword() {
        return TempPassword;
    }

    public void setTempPassword(String tempPassword) {
        TempPassword = tempPassword;
    }

    public String getPassportCountry() {
        return PassportCountry;
    }

    public void setPassportCountry(String passportCountry) {
        PassportCountry = passportCountry;
    }

    public String getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        UserTypeId = userTypeId;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }
}
