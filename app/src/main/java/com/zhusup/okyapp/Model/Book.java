package com.zhusup.okyapp.Model;

import com.google.gson.annotations.Expose;

public class Book {

    @Expose
    private String name;

    private String  image, description, page, category, menuId,link,rating,author;

 public Book(){

 }
    public Book(String name, String image, String description, String page, String category, String menuId,String link,String rating,String author) {
        this.link= link;
        this.name = name;
        this.image = image;
        this.description = description;
        this.page = page;
        this.rating=rating;
        this.author=author;
        this.category = category;
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String price) {
        this.page = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String discount) {
        this.category = discount;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
