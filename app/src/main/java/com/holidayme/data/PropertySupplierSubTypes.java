package com.holidayme.data;

/**
 * Created by supriya.sakore on 01-09-2015.
 */
public enum  PropertySupplierSubTypes {

 None(0),GDS(1), Expedia(2),Venere(3),Sabre(4),Worldspan(5);

    private int PropertySupplierSubTypesVal;
    PropertySupplierSubTypes(int PropertySupplierSubTypesVal) {
        this.PropertySupplierSubTypesVal = PropertySupplierSubTypesVal;
    }

    public int getOrderVal() {
        return PropertySupplierSubTypesVal;
    }




}
