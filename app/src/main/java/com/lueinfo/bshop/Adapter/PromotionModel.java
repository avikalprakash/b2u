package com.lueinfo.bshop.Adapter;

/**
 * Created by lue on 12-09-2017.
 */

public class PromotionModel {

    String image;
    String id;
    String name;
    String description;
    String price;
  //  String itemid;


    public static final int HEADER_ITEM_TYPE = 0;
    public static final int GRID_ITEM_TYPE = 1;

    public PromotionModel() {

    }


   /* public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }*/

    public PromotionModel(String image, String id, String name, String description, String price) {
        this.image = image;
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
