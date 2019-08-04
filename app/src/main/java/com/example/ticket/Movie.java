package com.example.ticket;

public class Movie {
    private int id;
    private String title;
    private String shortdesc;
    private String image;
    private String price;

    public Movie(int id, String title, String shortdesc, String image, String price) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }
}
