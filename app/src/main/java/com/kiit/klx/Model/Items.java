package com.kiit.klx.Model;

/**
 * Created by 1405214 on 24-02-2017.
 */

public class Items {

    public String Price;
    public String ProductName;
    public String Description;
    public String IMAGE1;
    public String IMAGE2;
    public String IMAGE3;
    public String IMAGE4;
    public String UploaderID;
    public String BAGValue;

    public Items() {
    }

    public Items(String price, String productName, String description, String IMAGE1, String IMAGE2, String IMAGE3, String IMAGE4, String uploaderID, String BAGValue) {
        Price = price;
        ProductName = productName;
        Description = description;
        this.IMAGE1 = IMAGE1;
        this.IMAGE2 = IMAGE2;
        this.IMAGE3 = IMAGE3;
        this.IMAGE4 = IMAGE4;
        UploaderID = uploaderID;
        this.BAGValue = BAGValue;
    }
}
