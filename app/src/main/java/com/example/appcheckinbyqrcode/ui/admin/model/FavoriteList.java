package com.example.appcheckinbyqrcode.ui.admin.model;

public class FavoriteList {
    public Integer id;
    public Integer idEvent;
    public String name;
    public String intro;
    public String chariman;
    public String image;


    public FavoriteList(int id, int idEvent, String name, String intro, String chariman, String image) {
        this.idEvent = idEvent;
        this.name = name;
        this.intro = intro;
        this.chariman = chariman;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getChariman() {
        return chariman;
    }

    public void setChariman(String chariman) {
        this.chariman = chariman;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
