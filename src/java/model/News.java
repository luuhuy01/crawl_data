package model;

import java.io.Serializable;
import java.util.Date;
public class News implements Serializable {

    private int id;
    private String title;
    private String urlImage;
    private String link;
    private String category; //thể loại
    private Date time;
    private String des;

    public News() {
    }

    public News(String title, String urlImage, String link, String category, Date time, String des) {
        this.title = title;
        this.urlImage = urlImage;
        this.link = link;
        this.category = category;
        this.time = time;
        this.des = des;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "News{" + "id=" + id + ", title=" + title + ", urlImage=" + urlImage + ", link=" + link + ", category=" + category + ", time=" + time + ", des=" + des + '}';
    }

}
