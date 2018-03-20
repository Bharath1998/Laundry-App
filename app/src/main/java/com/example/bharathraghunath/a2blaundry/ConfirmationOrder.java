package com.example.bharathraghunath.a2blaundry;

/**
 * Created by bharathraghunath on 11/03/18.
 */

public class ConfirmationOrder {
    String Address;
    String Delivery_Date;
    String Time;

    public ConfirmationOrder(){

    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDelivery_Date() {
        return Delivery_Date;
    }

    public void setDelivery_Date(String delivery_Date) {
        Delivery_Date = delivery_Date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}

