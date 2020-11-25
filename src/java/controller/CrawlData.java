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
import model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlData {

    private String link;

    public CrawlData() {
    }

    public CrawlData(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // tách data từ web
    public ArrayList<News> tachData(String html) {
        Document doc = Jsoup.parse(html);

        ArrayList<News> listNews = new ArrayList<>();

        // get data trong tin nổi bật
//        Element et1 = doc.getElementById("noibatmuc");
//        Elements et8 = doc.getElementsByClass("left fl");
//
//        String category1 = null;
//        for (int i = 0; i < et8.size(); i++) {
//            category1 = et8.get(i).getElementsByTag("a").text();
//        }
//
//        for (int i = 2; i <= 3; i++) {
//            Elements et2 = et1.getElementsByTag("h" + i);
//            if (et2.size() > 0) {     //kiểm tra xem bài viết có tiêu đề không.
//                Elements e7 = et2.get(0).getElementsByTag("a");
//                String title = e7.text();
//
//                //get link bài viết
//                String href = e7.attr("href");
//                String li = "https://vtv.vn/" + href;
//
//                //get des
//                Elements e3 = et1.getElementsByClass("sapo");
//                String des = e3.text();
//
//                //get img
//                Elements e4 = et1.getElementsByTag("img");
//                String urlImage = e4.get(i - 2).attr("src");
//
//                //get time
//                Elements e5 = et1.getElementsByClass("icon_clock");
//                Date date = null;
//                if (e5.size() > 0) {        // kiểm tra xem có class time không.
//                    Elements e6 = e5.get(0).getElementsByTag("p");
//                    String time = e6.text();
//                    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
//                    try {
//                        date = df.parse(time);
//
//                    } catch (ParseException ex) {
//                        Logger.getLogger(CrawlData.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                }
//                // get category
//
//                News news = new News(title, urlImage, li, category1, date, des);
//                listNews.add(news);
//            }
//        }

        //get data trong mục nhỏ
        Elements e1 = doc.getElementsByClass("tlitem");

        for (int i = 0; i < e1.size(); i++) {

            //get title
            Elements e2 = e1.get(i).getElementsByTag("h4");
//            Elements e9 = e1.get(i).getElementsByTag("h2");
            if (e2.size() > 0) {     //kiểm tra xem bài viết có tiêu đề không.
                Elements e7 = e2.get(0).getElementsByTag("a");
                String title = e7.text();

                //get link bài viết
                String href = e7.attr("href");
                String li = "https://vtv.vn/" + href;

                //get des
                Elements e3 = e1.get(i).getElementsByClass("sapo");
                String des = e3.text();

                //get img
                Elements e4 = e1.get(i).getElementsByTag("img");
                String urlImage = e4.attr("src");

                //get time
                Elements e5 = e1.get(i).getElementsByClass("time");
                Date date = null;
                String category = null;
                if (e5.size() > 0) {              // kiểm tra xem có class time không.
                    Elements e6 = e5.get(0).getElementsByTag("span");
                    String time = e6.text();
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        date = df.parse(time);

                    } catch (ParseException ex) {
                        Logger.getLogger(CrawlData.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // get category
                    Elements e8 = e5.get(0).getElementsByTag("a");
                    category = e8.text();

                }
                News news = new News(title, urlImage, li, category, date, des);
                listNews.add(news);
            }
        }

        return listNews;
    }

    public String HttpRequest() {

        HttpURLConnection connection;
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            // ghi file index.html
            File file = new File("index.html");
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

            // dùng httpURLConnection gửi request để lấy về html
            URL url = new URL(this.link);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            connection.setRequestProperty("Client-Platform", "android");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4302.0 Safari/537.36");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

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
                    osw.write(line);
                }
                reader.close();

            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                    osw.write(line);
                }
                reader.close();
            }

            osw.close();
            fos.close();

            connection.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(CrawlData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlData.class.getName()).log(Level.SEVERE, null, ex);
        }
        // trả về html của trang web
        return responseContent.toString();
    }

}
