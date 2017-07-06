package com.holidayme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by supriya.sakore on 21-03-2017.
 */

public class PackageDetailResponse implements Parcelable {
    private ArrayList<PackagesListDto>PackageDetails;
    private ArrayList<CategoryFilterDto> CategoryFilter;
    private String ErrorMessage;

    public ArrayList<PackagesListDto> getPackageDetails() {
        return PackageDetails;
    }

    public void setPackageDetails(ArrayList<PackagesListDto> packageDetails) {
        PackageDetails = packageDetails;
    }

    public ArrayList<CategoryFilterDto> getCategoryFilter() {
        return CategoryFilter;
    }

    public void setCategoryFilter(ArrayList<CategoryFilterDto> categoryFilter) {
        CategoryFilter = categoryFilter;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.PackageDetails);
        dest.writeTypedList(this.CategoryFilter);
        dest.writeString(this.ErrorMessage);
    }

    public PackageDetailResponse() {
    }

    protected PackageDetailResponse(Parcel in) {
        this.PackageDetails = in.createTypedArrayList(PackagesListDto.CREATOR);
        this.CategoryFilter = in.createTypedArrayList(CategoryFilterDto.CREATOR);
        this.ErrorMessage = in.readString();
    }

    public static final Parcelable.Creator<PackageDetailResponse> CREATOR = new Parcelable.Creator<PackageDetailResponse>() {
        @Override
        public PackageDetailResponse createFromParcel(Parcel source) {
            return new PackageDetailResponse(source);
        }

        @Override
        public PackageDetailResponse[] newArray(int size) {
            return new PackageDetailResponse[size];
        }
    };
}
