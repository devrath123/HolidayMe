package com.holidayme.staycationindex;

/**
 * Created by arshad.shaikh on 2/9/2017.
 */

class SampleData {

    private String name, address,image;

    SampleData(String name, String address, String imageUrl) {

        this.name=name;
        this.address=address;
        this.image=imageUrl;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
