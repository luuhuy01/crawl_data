/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.ptit.Baidang;
import model.ptit.Demuc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author luuhu
 */
public class CrawlPtit {

    private String link;

    public CrawlPtit() {
    }

    public CrawlPtit(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHttp() {
        HttpURLConnection connection;
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
//            // ghi file index1.html
//            File file = new File("index1.html");
//            FileOutputStream fos = new FileOutputStream(file);
//            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

            // dùng httpURLConnection gửi request để lấy về html
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            System.out.println(status);

            connection.connect();
            System.out.println(connection.getResponseMessage());

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
//                osw.write(line);
            }
            reader.close();

            // System.out.println(responseContent.toString());
            // sử dụng jsoup để query html 
//            osw.close();
//            fos.close();
            connection.disconnect();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return responseContent.toString();
    }

    public ArrayList<Baidang> dataBaidang(String html) {
        ArrayList<Baidang> arrBd = new ArrayList<>();
        String tieude = "";
        String link = "";
        Document doc = Jsoup.parse(html);
        Elements e1 = doc.getElementsByClass("entry-title");
        for (int i = 0; i < e1.size(); i++) {
            Elements e2 = e1.get(i).getElementsByTag("a");
            for (int j = 0; j < e2.size(); j++) {
                tieude = e2.get(j).text();
                link = e2.get(j).attr("href");

            }
            Baidang baidang = new Baidang(tieude, link);
            arrBd.add(baidang);
        }
        return arrBd;
    }

    public ArrayList<Demuc> dataDemuc(String html) {
        ArrayList<Demuc> arrDm = new ArrayList<>();
        String id = null;
        String name = null;
        String link = null;
        Document doc = Jsoup.parse(html);
        Elements e1 = doc.getElementsByClass("column_attr"); //lấy toàn bộ đề mục ở class
        for (int i = 0; i < e1.size(); i++) {
            Elements e2 = e1.get(i).getElementsByTag("a");
            for (int j = 0; j < e2.size(); j++) {
                name = e2.get(j).text();
                link = e2.get(j).attr("href");
                if (name != "") {
                    Demuc demuc = new Demuc(name, link);
                    System.out.println(demuc);
                    arrDm.add(demuc);
                }
            }
        }
        return arrDm;
    }
}
