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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Menu;
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
            // ghi file index1.html
            File file = new File("index1.html");
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

            // dùng httpURLConnection gửi request để lấy về html
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            //      login bằng cookie
            int status = connection.getResponseCode();
            System.out.println(status);

            connection.connect();
            System.out.println(connection.getResponseMessage());

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
                osw.write(line);
            }
            reader.close();

            // System.out.println(responseContent.toString());
            // sử dụng jsoup để query html 
            osw.close();
            fos.close();

            connection.disconnect();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return responseContent.toString();
    }

    public String RegexHtml(String html, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String match = "";
        while (matcher.find()) {
            match = matcher.group(0);
        }
        return match;
    }

    public ArrayList<Menu> dataMenu(String html) {
        Document doc = Jsoup.parse(html);
        Element e1 = doc.getElementById("menu-main-menu");
        Elements e2 = e1.children();
        ArrayList<Menu> arr = new ArrayList<>();
        String title = null;
        String link = null;
        for (int i = 0; i < e2.size(); i++) {
            Elements e3 = e2.get(i).getElementsByTag("li");
            System.out.println(e3.size());
            for(int j =0; j<e3.size(); j++){
                Elements e4 = e3.get(j).getElementsByTag("a");
                for(int k =0; k< e4.size(); k++){
                     title = e4.get(k).text();
                     link = e4.get(k).attr("href");            
                }        
                Menu menu = new Menu(title, link);
                arr.add(menu);
            }
        }
        return arr;
    }
}
