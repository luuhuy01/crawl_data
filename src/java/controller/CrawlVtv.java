/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.vtv.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author luuhu
 */
public class CrawlVtv {

    private String link;

    public CrawlVtv() {
    }

    public CrawlVtv(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // tách data tin noi bat
    public ArrayList<News> dataNoibat(String html) {
        Document doc = Jsoup.parse(html);

        ArrayList<News> listNews = new ArrayList<>();

        Elements e1 = doc.getElementsByClass("noibat_home");

        String title = null;

        String link = null;
        Date date = new Date();
        String des = null;
        String catagory = "nổi bật"; // gán loại thành tin nổi bật
        //lay cac bai viet phu
        for (int i = 0; i < e1.size(); i++) {
            Elements e2 = e1.get(i).getElementsByTag("li");
            for (int j = 0; j < e2.size(); j++) {
                String urlImage = null;
                title = e2.get(j).text();
                Elements e3 = e2.get(j).getElementsByTag("a");
                for (int k = 0; k < e3.size(); k++) {
                    link = this.link + e3.get(k).attr("href");
                    Elements e4 = e3.get(i).getElementsByTag("img");

                    for (int n = 0; n < e4.size(); n++) {
                        urlImage = e4.get(n).attr("src");
                    }
                }
                News news = new News(title, urlImage, link, catagory, date, null);
                listNews.add(news);

            }
            // lay bai viet chinh avatar
            Elements e5 = e1.get(i).getElementsByClass("noibat1 equalheight");
            for (int j = 0; j < e5.size(); j++) {
                String urlImage = null;
                Elements e6 = e5.get(j).getElementsByTag("a");
                for (int k = 0; k < e6.size(); k++) {
                    title = e6.get(j).attr("title");
                    link = this.link + e6.get(k).attr("href");
                    Elements e7 = e6.get(i).getElementsByTag("img");
                    for (int n = 0; n < e7.size(); n++) {
                        urlImage = e7.get(n).attr("src");
                    }
                }
                Elements e8 = e5.get(j).getElementsByTag("p");
                for (int n = 0; n < e8.size(); n++) {
                    des = e8.text();
                }
                News news = new News(title, urlImage, link, catagory, date, des);
                listNews.add(news);
            }

        }
        return listNews;
    }

    // tach data muc nho 
    public ArrayList<News> dataMucNho(String html) {
        Document doc = Jsoup.parse(html);

        ArrayList<News> listNews = new ArrayList<>();

        String des = null;
        String title = null;
        String urlImage = null;
        String link = null;
        String category = null;
        Date dateTime = null;

        Elements e1 = doc.getElementsByClass("tlitem");
        for (int i = 0; i < e1.size(); i++) {

            Elements e2 = e1.get(i).children();
            //get title
            for (int j = 0; j < e2.size(); j++) {
                if (e2.get(j).attr("title") != "") {
                    title = e2.get(j).attr("title");
                }
                if (e2.get(j).attr("href") != "") {
                    link = "https://vtv.vn/" + e2.get(j).attr("href");
                }
                Elements e3 = e2.get(j).getElementsByTag("img");
                for (int k = 0; k < e3.size(); k++) {
                    urlImage = e3.get(k).attr("src");
                }
                Elements e4 = e2.get(j).getElementsByTag("a");
//                System.out.println(e4.html());
                Elements cate = e2.get(j).getElementsByClass("time");
                for (int k = 0; k < cate.size(); k++) {
                    Elements cate1 = cate.get(k).getElementsByTag("a");
                    for(int n= 0; n<cate1.size(); n++){
                        category = e4.get(n).text();
                    }                   
                }
                Elements e5 = e2.get(j).getElementsByTag("span");
                String time = null;
                for (int k = 0; k < e5.size(); k++) {
                    if (e5.get(k).attr("title") != "") {
                        time = e5.get(k).attr("title");
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            dateTime = df.parse(time);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                Elements e6 = e2.get(j).getElementsByClass("sapo");
                for (int k = 0; k < e6.size(); k++) {
                    des = e6.get(k).text();
                }
            }
            News news = new News(title, urlImage, link, category, dateTime, des);
            listNews.add(news);
        }
        return listNews;
    }

    public String HttpRequest() {

        HttpURLConnection connection;
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
//            // ghi file index.html
//            File file = new File("index.html");
//            FileOutputStream fos = new FileOutputStream(file);
//            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

            // dùng httpURLConnection gửi request để lấy về html
            URL url = new URL(this.link);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.setRequestProperty("Client-Platform", "android");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4302.0 Safari/537.36");

//            login bằng cookie
//            String cookie = "sw_version=1; _ga=GA1.2.2120110320.1604506591; _gid=GA1.2.1176469965.1604506591; orig_aid=7uw5nuy4wc16crdz.1604506590; _pk_ref=%5B%22%22%2C%22%22%2C1604506591%2C%22https%3A%2F%2Fwww.google.com%2F%22%5D; _pk_ses=*; fosp_location=32; fosp_country=vn; fosp_loc=32-3-vn; fosp_gender=3; fosp_isp=12; fosp_location_zone=3; login_system=1; __gads=ID=90b1b6d91831ff26:T=1604506591:S=ALNI_MYumeSg1ofnKWrpe7XKtEpA7I-t8Q; SL_GWPT_Show_Hide_tmp=1; SL_wptGlobTipTmp=1; _ym_uid=1604506593255132918; _ym_d=1604506593; _ym_isad=2; _ym_visorc_62978707=b; __utmz=139601012.1604506691.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmc=139601012; __utma=139601012.2120110320.1604506591.1604506691.1604506691.1; __utmb=139601012.2.10.1604506691; smartbanner=hide; device_env=4; device_env_real=4; cdevice=4; myvne_onetap=1; fosp_aid=1061582061; fosp_ptoken=d2f2cd8a80ff65bd2855785bff513c7b; myvne_user_id=1061582061; display_cpd=9; _pk_cvar=%7B%224%22%3A%5B%22fosp_aid%22%2C%221061582061%22%5D%2C%227%22%3A%5B%22fosp_aid_bk%22%2C%221061582061%22%5D%2C%228%22%3A%5B%22eatv%22%2C%2228-03-2014%22%5D%2C%229%22%3A%5B%22fosp_session%22%2C%221yzkzk1tzr1vzm21zk21zlzkziznznznzdzizlzjznzmzjzlzmzqzizdzkzdzizlzjznzmzjzlzkzqzlzdzizlzjznzmzjzlzkzrzhzdzizdzjzdzjzdzezdzg%22%5D%2C%2210%22%3A%5B%22fosp_gender%22%2C%220%22%5D%7D; _pk_id=0da4cdbdfa2fecd1.1604506591.1.1604506812.1604506591.";
//            String[] temp = cookie.split(";");
//            for (int i = 0; i < temp.length; i++) {
//                connection.setRequestProperty("Cookie", temp[i]);
//            }
            int status = connection.getResponseCode();
            System.out.println(status);
            connection.connect();
            System.out.println(connection.getResponseMessage());
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
//                    osw.write(line);
                }
                reader.close();

            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
//                    osw.write(line);
                }
                reader.close();
            }
//            osw.close();
//            fos.close();

            connection.disconnect();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // trả về html của trang web
        return responseContent.toString();
    }

}
