package com.example.dedan.digitalreceipts;

public class noteItem {
    private String quantity;
    private String item;
    private String each;
    private String cost;


    public noteItem(String quantity, String item, String each, String cost) {
        this.quantity = quantity;
        this.item = item;
        this.each = each;
        this.cost = cost;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getEach() {
        return each;
    }

    public void setEach(String each) {
        this.each = each;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
