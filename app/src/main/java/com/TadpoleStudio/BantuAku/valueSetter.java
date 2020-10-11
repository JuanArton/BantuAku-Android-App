package com.TadpoleStudio.BantuAku;

public class valueSetter {

    //model class
    String name, imageUrl, sellername, deskripsi, whatsapp, email, phone, username;

    //constructor
    public valueSetter() {
    }

    public valueSetter(String name, String imageUrl, String sellername, String deskripsi, String whatsapp, String email, String phone, String username) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.sellername = sellername;
        this.deskripsi = deskripsi;
        this.whatsapp = whatsapp;
        this.email = email;
        this.phone = phone;
        this.username = username;
    }

    public String getSkill() {
        return name;
    }

    public void setSkill(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSeller() {
        return sellername;
    }

    public void setSeller(String sellername) {
        this.sellername = sellername;
    }

    public String getDesc() {
        return deskripsi;
    }

    public void setDesc(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getWA() {
        return whatsapp;
    }

    public void setWA(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
