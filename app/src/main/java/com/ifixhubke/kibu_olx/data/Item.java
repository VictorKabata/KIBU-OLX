package com.ifixhubke.kibu_olx.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "posted_item_history")
public class Item implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    int id;
    Boolean itemStarred;
    private String sellerName;
    private String sellerLastSeen;
    private String sellerPhoneNum;
    private String itemImage;
    private String itemImage2;
    private String itemImage3;
    private String itemName;
    private String itemPrice;
    private String datePosted;
    private String location;
    private String itemDescription;
    private Boolean isSoldOut;

    public Item() {
    }

    public Item(String itemImage, String itemName, String itemPrice, Boolean itemStarred) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStarred = itemStarred;
    }

    public Item(String itemImage, String itemImage2, String itemImage3){
        this.itemImage=itemImage;
        this.itemImage2=itemImage2;
        this.itemImage3=itemImage3;
    }

    public Item(String sellerName, String sellerLastSeen, String sellerPhoneNum, String itemImage, String itemImage2, String itemImage3, String itemName, String itemPrice, String datePosted, String location, String itemDescription) {
        this.sellerName = sellerName;
        this.sellerLastSeen = sellerLastSeen;
        this.sellerPhoneNum = sellerPhoneNum;
        this.itemImage = itemImage;
        this.itemImage2 = itemImage2;
        this.itemImage3 = itemImage3;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.datePosted = datePosted;
        this.location = location;
        this.itemDescription = itemDescription;
    }

    public Item(String itemImage, String itemName, String itemPrice, String datePosted, Boolean isSoldOut){
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.datePosted = datePosted;
        this.isSoldOut = isSoldOut;
    }
    public Item(String itemImage, String itemImage2, String itemImage3){
        this.itemImage=itemImage;
        this.itemImage2=itemImage2;
        this.itemImage3=itemImage3;
    }

    public Boolean getIsSoldOut() {
        return isSoldOut;
    }

    public void setIsSoldOut(Boolean isSoldOut) {
        this.isSoldOut = isSoldOut;
    }

    public Boolean getItemStarred() {
        return itemStarred;
    }

    public void setItemStarred(Boolean itemStarred) {
        this.itemStarred = itemStarred;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerLastSeen() {
        return sellerLastSeen;
    }

    public void setSellerLastSeen(String sellerLastSeen) {
        this.sellerLastSeen = sellerLastSeen;
    }

    public String getSellerPhoneNum() {
        return sellerPhoneNum;
    }

    public void setSellerPhoneNum(String sellerPhoneNum) {
        this.sellerPhoneNum = sellerPhoneNum;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemImage2() {
        return itemImage2;
    }

    public void setItemImage2(String itemImage2) {
        this.itemImage2 = itemImage2;
    }

    public String getItemImage3() {
        return itemImage3;
    }

    public void setItemImage3(String itemImage3) {
        this.itemImage3 = itemImage3;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.itemStarred);
        dest.writeString(this.sellerName);
        dest.writeString(this.sellerLastSeen);
        dest.writeString(this.sellerPhoneNum);
        dest.writeString(this.itemImage);
        dest.writeString(this.itemImage2);
        dest.writeString(this.itemImage3);
        dest.writeString(this.itemName);
        dest.writeString(this.itemPrice);
        dest.writeString(this.datePosted);
        dest.writeString(this.location);
        dest.writeString(this.itemDescription);
    }

    public void readFromParcel(Parcel source) {
        this.itemStarred = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.sellerName = source.readString();
        this.sellerLastSeen = source.readString();
        this.sellerPhoneNum = source.readString();
        this.itemImage = source.readString();
        this.itemImage2 = source.readString();
        this.itemImage3 = source.readString();
        this.itemName = source.readString();
        this.itemPrice = source.readString();
        this.datePosted = source.readString();
        this.location = source.readString();
        this.itemDescription = source.readString();
    }

    protected Item(Parcel in) {
        this.itemStarred = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.sellerName = in.readString();
        this.sellerLastSeen = in.readString();
        this.sellerPhoneNum = in.readString();
        this.itemImage = in.readString();
        this.itemImage2 = in.readString();
        this.itemImage3 = in.readString();
        this.itemName = in.readString();
        this.itemPrice = in.readString();
        this.datePosted = in.readString();
        this.location = in.readString();
        this.itemDescription = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
