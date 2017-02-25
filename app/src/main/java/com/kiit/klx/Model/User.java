package com.kiit.klx.Model;

/**
 * Created by 1405214 on 19-02-2017.
 */

public class User {
    public String DisplayName;
    public String Image;
    public String Email;
    public String Uploads;
    public String Uid;
    public String Bought;
    public String Mobile;

    public User() {
    }

    public User(String displayName, String image, String email, String uploads, String uid, String bought, String mobile) {
        DisplayName = displayName;
        Image = image;
        Email = email;
        Uploads = uploads;
        Uid = uid;
        Bought = bought;
        Mobile = mobile;
    }
}
