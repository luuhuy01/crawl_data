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
public class Demuc {

    private int id;
    private String name;
    private String link;
    private ArrayList<Baidang> baidang;

    public Demuc() {
    }

    public Demuc(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public Demuc(int id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    public Demuc(int id, String name, String link, ArrayList<Baidang> baidang) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.baidang = baidang;
    }

    public Demuc(String name, String link, ArrayList<Baidang> baidang) {
        this.name = name;
        this.link = link;
        this.baidang = baidang;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<Baidang> getBaidang() {
        return baidang;
    }

    public void setBaidang(ArrayList<Baidang> baidang) {
        this.baidang = baidang;
    }

    @Override
    public String toString() {
        return "Demuc{" + "id=" + id + ", name=" + name + ", link=" + link + ", baidang=" + baidang + '}';
    }

   
}
