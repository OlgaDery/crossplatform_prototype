package com.twovsodds.retrofit_smpl;

import java.io.Serializable;
import java.util.Objects;

//The only model class in the project. It represents JSON data which is being received from the external server.
//Contains 2 constructors, "getters" and "setters" for the fields, overrides "hashcode" and "equals".
public class Point implements Serializable {

    private String id;
    private String name;
    private String descript;
    private double lat;
    private double lng;
    private String category;
    private Integer catIndex;
    private String extraCategory;
    private Integer extraCategoryIndex;
    private Integer rating;
    private String pendStatus;
    private String photoLink = "lnk";
    private String webLink = "lnk";

    public Point(String name1, String descr1, String photoLink1, double lng1, double lat1,
                 String webLink, Integer rating) {
        this.descript= descr1;
        this.name= name1;
        this.photoLink= photoLink1;
        this.lat=lat1;
        this.lng = lng1;
        this.webLink = webLink;
        this.rating = rating;
    }

    public Point () {};

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescript() {
        return descript;
    }


    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCatIndex() {
        return catIndex;
    }

    public void setCatIndex(Integer catIndex) {
        this.catIndex = catIndex;
    }

    public String getExtraCategory() {
        return extraCategory;
    }

    public void setExtraCategory(String extraCategory) {
        this.extraCategory = extraCategory;
    }

    public Integer getExtraCategoryIndex() {
        return extraCategoryIndex;
    }

    public void setExtraCategoryIndex(Integer extraCategoryIndex) {
        this.extraCategoryIndex = extraCategoryIndex;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getPendStatus() {
        return pendStatus;
    }

    public void setPendStatus(String pendStatus) {
        this.pendStatus = pendStatus;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.lat, lat) == 0 &&
                Double.compare(point.lng, lng) == 0 &&
                Objects.equals(id, point.id) &&
                Objects.equals(name, point.name) &&
                Objects.equals(descript, point.descript) &&
                Objects.equals(category, point.category) &&
                Objects.equals(catIndex, point.catIndex) &&
                Objects.equals(extraCategory, point.extraCategory) &&
                Objects.equals(extraCategoryIndex, point.extraCategoryIndex) &&
                Objects.equals(rating, point.rating) &&
                Objects.equals(pendStatus, point.pendStatus) &&
                Objects.equals(photoLink, point.photoLink) &&
                Objects.equals(webLink, point.webLink);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, descript, lat, lng, category, catIndex, extraCategory, extraCategoryIndex, rating, pendStatus, photoLink, webLink);
    }
}
