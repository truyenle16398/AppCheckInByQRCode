package com.example.appcheckinbyqrcode.ui.client.model;

import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
public class Favorite {
    private String eventname_favorite;
    private String eventday_favorite;
    private String eventpersion_favorite;
    private String eventcheck_favorite;
    private String eventimage_favorite;

    public Favorite(String eventname_favorite, String eventday_favorite, String eventpersion_favorite, String eventcheck_favorite, String eventimage_favorite) {
        this.eventname_favorite = eventname_favorite;
        this.eventday_favorite = eventday_favorite;
        this.eventpersion_favorite = eventpersion_favorite;
        this.eventcheck_favorite = eventcheck_favorite;
        this.eventimage_favorite = eventimage_favorite;
    }
}
