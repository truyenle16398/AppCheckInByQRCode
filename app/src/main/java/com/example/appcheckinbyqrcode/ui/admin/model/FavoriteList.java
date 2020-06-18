package com.example.appcheckinbyqrcode.ui.admin.model;

public class FavoriteList {
    public Integer idEvent;
    public Boolean favoriteCheck;

    public FavoriteList(int id, int idEvent , boolean favoriteCheck) {
        this.idEvent = idEvent;
        this.favoriteCheck = favoriteCheck;
    }
}
