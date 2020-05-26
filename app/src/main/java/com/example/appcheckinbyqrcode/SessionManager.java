package com.example.appcheckinbyqrcode;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String SHARED_PREFERENCES_NAME = "PREFERENCE_FILE_KEY";
    private static final String KEY_SAVE_TOKEN = "key_save_device_token";
    private static final String KEY_SAVE_VERSION = "key_save_device_version_app";
    private static final String KEY_SAVE_SIZE_TEXT = "key_save_size_text";
    private static final String KEY_SAVE_FIRST_APP = "key_save_first_app";
    private static final String KEY_SAVE_LANGUAGE = "key_save_language";
    private static final String KEY_SAVE_COLOR = "key_save_color";
    private static final String KEY_SAVE_CITY_NAME = "key_save_city_name";
    private static final String KEY_SAVE_CITY_NAME_LAT = "key_save_city_name_lat";
    private static final String KEY_SAVE_CITY_NAME_LONG = "key_save_city_name_long";
    private static final String KEY_SAVE_CITY = "key_save_city";
    private static final String KEY_SAVE_TOKEN_USER_DEVICE = "key_save_token";

    private static final String KEY_SAVE_NAME = "key_save_name";
    private static final String KEY_SAVE_CHECK = "key_save_check";
    private static final String KEY_LOGIN = "islogin";
    private static final String KEY_ROLE = "key_role";



    private static SessionManager sInstance;

    private SharedPreferences sharedPref;

    public synchronized static SessionManager getInstance() {//kiểm
        if (sInstance == null) {
            sInstance = new SessionManager();
        }
        return sInstance;
    }

    private SessionManager() {
        // no instance
    }

    public void init(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    //check chuc vu
    public void setKeyRole(String role) {
        sharedPref.edit().putString(KEY_ROLE, role).apply();
    }
    public String getKeyRole(){
        return sharedPref.getString(KEY_ROLE, "");
    }

    //check login
    public void setKeyLogin(boolean islogin) {
        sharedPref.edit().putBoolean(KEY_LOGIN, islogin).apply();
    }
    //    public void getKeyLogin(boolean islogin) {
//        sharedPref.edit().putBoolean(KEY_LOGIN,islogin).apply();
//    }
    public boolean CheckKeyLogin(){
        return sharedPref.getBoolean(KEY_LOGIN,false);
    }

    /**
     * Set key save name
     *
     */
    public void setKeySaveName(String name) {
        sharedPref.edit().putString(KEY_SAVE_NAME, name).apply();
    }

    public String getKeySaveName(){
        return sharedPref.getString(KEY_SAVE_NAME, "");
    }
    /**
     * Set key save checkin/out
     *
     */
    public void setKeySaveCheck(Boolean check) {
        sharedPref.edit().putBoolean(KEY_SAVE_CHECK, check).apply();
    }
    public Boolean getKeySaveCheck(){
        return sharedPref.getBoolean(KEY_SAVE_CHECK, false);
    }
    public boolean CheckKeyInOut(){
        return sharedPref.getBoolean(KEY_SAVE_CHECK,false);
    }



    //-------------------------------------------------------------------------------------------
    /**
     * Set key save language
     *
     */
    public void setKeySaveLanguage(String language) {
        sharedPref.edit().putString(KEY_SAVE_LANGUAGE, language).apply();
    }

    public String getKeySaveLanguage(){
        return sharedPref.getString(KEY_SAVE_LANGUAGE, "vn");
    }

    /**
     * Set key save color
     *
     */
    public void setKeySaveColor(String color) {
        sharedPref.edit().putString(KEY_SAVE_COLOR, color).apply();
    }
    public String getKeySaveColor(){
        return sharedPref.getString(KEY_SAVE_COLOR, "#F6B934");
    }

    /**
     * Set key save AddressCity
     *
     */
    public void setKeySaveAddressCity(String cityName) {
        sharedPref.edit().putString(KEY_SAVE_CITY_NAME, cityName).apply();
    }
    public String getKeySaveCityName(){
        return sharedPref.getString(KEY_SAVE_CITY_NAME, "");
    }

    /**
     * Set key save Lat
     *
     */
    public void setKeySaveLat(String lat) {
        sharedPref.edit().putString(KEY_SAVE_CITY_NAME_LAT, lat).apply();
    }
    public String getKeySaveLat(){
        return sharedPref.getString(KEY_SAVE_CITY_NAME_LAT, "");
    }

    /**
     * Set key save Log
     *
     */
    public void setKeySaveLong(String Long) {
        sharedPref.edit().putString(KEY_SAVE_CITY_NAME_LONG, Long).apply();
    }
    public String getKeySaveLong(){
        return sharedPref.getString(KEY_SAVE_CITY_NAME_LONG, "");
    }
    /**
     * Set key save city name
     *
     */
    public void setKeySaveCity(String city) {
        sharedPref.edit().putString(KEY_SAVE_CITY, city).apply();
    }
    public String getKeySaveCity(){
        return sharedPref.getString(KEY_SAVE_CITY, "");
    }
    /**
     * Set key save token
     *
     */
    public void setKeySaveToken(String token) {
        sharedPref.edit().putString(KEY_SAVE_TOKEN, token).apply();
    }

    /**
     * get key save mToken
     *
     */
    public String getKeySaveToken() {
        return sharedPref.getString(KEY_SAVE_TOKEN, "");
    }

    /**
     * Set key save token device
     *
     */
    public void setKeySaveTokenDevice(String token) {
        sharedPref.edit().putString(KEY_SAVE_TOKEN_USER_DEVICE, token).apply();
    }

    /**
     * get key save mToken device
     *
     */
    public String getKeySaveTokenDeviCe() {
        return sharedPref.getString(KEY_SAVE_TOKEN_USER_DEVICE, "");
    }
    /**
     * Set key save token
     *
     */
    public void setKeySaveVersion(String token) {
        sharedPref.edit().putString(KEY_SAVE_VERSION, token).apply();
    }

    /**
     * get key save mToken
     *
     */
    public String getKeySaveVersion() {
        return sharedPref.getString(KEY_SAVE_VERSION, "");
    }


    /**
     * Set key save token
     *
     */
    public void setKeySaveSize(String size) {
        sharedPref.edit().putString(KEY_SAVE_SIZE_TEXT, size).apply();
    }

    /**
     * get key save mToken
     *
     */
    public String getKeySaveSizeText() {
        return sharedPref.getString(KEY_SAVE_SIZE_TEXT, "");
    }


    /**
     * Set key save token
     *
     */
    public void setKeySaveFirstApp(boolean inapp) {
        sharedPref.edit().putBoolean(KEY_SAVE_FIRST_APP, inapp).apply();
    }

    /**
     * get key save mToken
     *
     */
    public boolean getKeySaveSizeFirstApp() {//lấy ra cái key de kt may nao da tai lan dau tien
        return sharedPref.getBoolean(KEY_SAVE_FIRST_APP, false);
    }

    /**
     * clear share
     */
    public void clear() {
        sharedPref.edit().clear().apply();
    }
}
