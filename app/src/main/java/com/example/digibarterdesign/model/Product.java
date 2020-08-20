package com.example.digibarterdesign.model;

public class Product {
    public int id,rewards,userid,cat_id,stocks,usertype;
    public String title,desc,createdDate;
    public String images[];

    public Product(int id, int rewards, int userid, int cat_id, int stocks, int usertype, String title, String desc, String createdDate, String[] images) {
        this.id = id;
        this.rewards = rewards;
        this.userid = userid;
        this.cat_id = cat_id;
        this.stocks = stocks;
        this.usertype = usertype;
        this.title = title;
        this.desc = desc;
        this.createdDate = createdDate;
        this.images = images;
    }
}
