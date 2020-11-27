package controller;

import static controller.DAO.sc;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Menu;
import model.News;

public class main {

    public static void main(String[] args) {

        DAO dao = new DAO();
        String urlVtv = "https://vtv.vn/";
        CrawlVtv crawlVtv = new CrawlVtv(urlVtv);
        String html = crawlVtv.HttpRequest();
        ArrayList<News> arr = crawlVtv.dataNoibat(html);
        for (int i = 0; i < arr.size(); i++) {
//            System.out.println(arr.get(i));
//            dao.addNew(arr.get(i));
        }
        // xu ly ajax https://vtv.vn/timelinehome/trang-1.htm
        int dem =0;
        for (int j = 1; j < 100; j++) {
            String urlAjax = urlVtv+"timelinehome/trang-"+j+".htm";
            CrawlVtv crawlAjax = new CrawlVtv(urlAjax);
            String html1 = crawlAjax.HttpRequest();
            System.out.println(urlAjax);
            ArrayList<News> arr1 = crawlAjax.dataMucNho(html1);
            for (int i = 0; i < arr1.size(); i++) {
//                System.out.println(arr1.get(i));
                dao.addNew(arr1.get(i));
            }
        }
        
        
        String urlPtit = "https://portal.ptit.edu.vn/";
        CrawlPtit crawlPtit = new CrawlPtit(urlPtit);
        String httpPtit = crawlPtit.getHttp();
        ArrayList<Menu> menuPtit = crawlPtit.dataMenu(httpPtit);
        for(int i=0; i<menuPtit.size(); i++){
            System.out.println(menuPtit.get(i));
        }
        
    }
}
