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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            // ghi file index.html
            File file = new File("index.html");
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

    public static String RegexHtml(String html, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String match = "";
        while (matcher.find()) {
            match = matcher.group(0);
        }
        return match;
    }

    public static void Try(Elements e2, int j, int a[]) {
        for (int i = 0; i < e2.size(); i++) {
            String link = e2.get(i).attr("href");
            Elements e4 = e2.get(i).getElementsByTag("span");
            String title = "";

            for (int k = 0; k < e4.size(); k++) {
                title = e4.text();
            }
            if (!link.isEmpty()) {
                System.out.println("sub " + j + "; title" + ": " + title + "; Link " + ": " + link);
                System.out.println(a[i]);
            } else {
                j--;
                a[i]++;
                Elements e5 = e2.get(i).children();
                Try(e5, i, a);
                j--;
            }

        }

    }
}
