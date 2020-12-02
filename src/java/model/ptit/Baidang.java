/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ptit;

import java.util.ArrayList;

/**
 *
 * @author luuhu
 */
public class Baidang {
    private int id;
    private String tieude;
    private String link;
   

    public Baidang() {
    }

    public Baidang(String tieude, String link) {
        this.tieude = tieude;
        this.link = link;
    }

    public Baidang(int id, String tieude, String link) {
        this.id = id;
        this.tieude = tieude;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Baidang{" + "id=" + id + ", tieude=" + tieude + ", link=" + link + '}';
    }

   
    
}
