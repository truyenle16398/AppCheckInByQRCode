package com.example.appcheckinbyqrcode.network;

public class url {
//    static String ip ="10.0.2.241";//10.0.2.241 //192.168.1.8
//    static String url = "http://"+ip+":8888/SDC-Event/public/api/";
//    static String urlimg = "http://"+ip+":8888/SDC-Event/public/";
//    static String urlimgevent = "http://"+ip+":8888/SDC-Event/public/uploads/";

    static String url = "http://events.sdc.click/api/";
    static String urlimg = "http://events.sdc.click/";
    static String urlimgevent = "http://events.sdc.click/uploads/";

    public static String getUrl() {
        return url;
    }

    public static String getUrlimg() {
        return urlimg;
    }

    public static String getUrlimgevent() {
        return urlimgevent;
    }
}
