package controller;

import static controller.DAO.sc;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.News;

public class main {

    public static void main(String[] args) {

        DAO dao = new DAO();
        String url = "https://vtv.vn/";
        CrawlVtv crawl = new CrawlVtv(url);
        String html = crawl.HttpRequest();
        ArrayList<News> arr = crawl.dataNoibat(html);
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
            dao.addNew(arr.get(i));
        }
        // xu ly ajax https://vtv.vn/timelinehome/trang-1.htm
        int dem =0;
        for (int j = 1; j < 100; j++) {
            String urlAjax = url+"timelinehome/trang-"+j+".htm";
            CrawlVtv crawl1 = new CrawlVtv(urlAjax);
            String html1 = crawl1.HttpRequest();
            System.out.println(urlAjax);
            ArrayList<News> arr1 = crawl1.dataMucNho(html1);
            for (int i = 0; i < arr1.size(); i++) {
//                System.out.println(arr1.get(i));
                dao.addNew(arr1.get(i));
            }
        }
        
    }
}
