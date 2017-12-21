package com.lueinfo.bshop.Adapter;

import java.util.ArrayList;

/**
 * Created by lue on 06-03-2017.
 */
public class Details {
    private int KeyID;
    private String ItemName;
    private String ItemId;
    private String ItemPrice;
    private byte[] ItemImage;
    private String itemimage;
    private String ItemQuntity;
    private String ItemPrice_Sale;
    private String ItemPriceTotal;
    ArrayList<String> CartItem;
    ArrayList<String> CartName;
    ArrayList<Integer> CartImage;
    String[]name;
    String[]Mrpprice;
    String[]SalePrice;
    private String pr_name;
    private String pr_qty;
    private String pr_price;
    private String pr_image;
    int[]image;

    public void setPr_name(String pr_name) {
        this.pr_name = pr_name;
    }

    public void setPr_qty(String pr_qty) {
        this.pr_qty = pr_qty;
    }

    public void setPr_price(String pr_price) {
        this.pr_price = pr_price;
    }

    public void setPr_image(String pr_image) {
        this.pr_image = pr_image;
    }

    public String getPr_name() {

        return pr_name;
    }

    public String getPr_qty() {
        return pr_qty;
    }

    public String getPr_price() {
        return pr_price;
    }

    public String getPr_image() {
        return pr_image;
    }

    public Details(int KeyId, String itemName, String itemPrice, String itemQuntity, String itemImage){
        this.KeyID=KeyId;
        this.itemimage=itemImage;
        this.ItemName=itemName;
        this.ItemQuntity=itemQuntity;
        this.ItemPrice=itemPrice;
        this.pr_name=pr_name;
        this.pr_qty=pr_qty;
        this.pr_price=pr_price;
        this.pr_image=pr_image;



    }

    public Details() {

    }
    public Details(ArrayList<String> CartItem, ArrayList<String> Cartname, ArrayList<Integer> CArttImage){
        this.CartItem=CartItem;
        this.CartName=Cartname;
        this.CartImage=CArttImage;

    }
    public Details(String itemId, String itemName, String itemPrice, String itemQuntity, String itemImage, String ItempriceTotal){
        this.ItemId=itemId;

        this.itemimage=itemImage;
        this.ItemName=itemName;
        this.ItemQuntity=itemQuntity;
        this.ItemPrice=itemPrice;
        this.ItemPriceTotal=ItempriceTotal;

    }
    public Details(String[] Cartname, String[] Cartprice, int[] CArttImage, String[]MrpPrice){
        this.name=Cartname;
        this.Mrpprice=Cartprice;
        this.SalePrice=MrpPrice;
        this.image=CArttImage;

    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public byte[] getItemImage() {
        return ItemImage;
    }

    public void setItemImage(byte[] itemImage) {
        ItemImage = itemImage;
    }

    public Integer getKeyID() {
        return KeyID;
    }

    public void setKeyID(Integer keyID) {
        KeyID = keyID;
    }

    public String getItemQuntity() {
        return ItemQuntity;
    }

    public void setItemQuntity(String itemQuntity) {
        ItemQuntity = itemQuntity;
    }

    public String getItemPrice_Sale() {
        return ItemPrice_Sale;
    }

    public void setItemPrice_Sale(String itemPrice_Sale) {
        ItemPrice_Sale = itemPrice_Sale;
    }

    public String getItemimage() {
        return itemimage;
    }

    public void setItemimage(String itemimage) {
        this.itemimage = itemimage;
    }

    public String getitemimage() {
        return itemimage;
    }

    public void setitemimage(String itemimage) {
        this.itemimage = itemimage;
    }

    public String getItemPriceTotal() {
        return ItemPriceTotal;
    }

    public void setItemPriceTotal(String itemPriceTotal) {
        ItemPriceTotal = itemPriceTotal;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }
}
