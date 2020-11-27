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
        //xu ly khong tai trang ban dau
        String urlVtv = "https://vtv.vn/";
        CrawlVtv crawlVtv = new CrawlVtv(urlVtv);
        String html = crawlVtv.HttpRequest();
        ArrayList<News> arrNB = crawlVtv.dataNoibat(html);
        ArrayList<News> arrMN = crawlVtv.dataMucNho(html);
        for (int i = 0; i < arrNB.size(); i++) {
            dao.addNew(arrNB.get(i));
        }
        for (int i = 0; i < arrMN.size(); i++) {
            dao.addNew(arrMN.get(i));
        }
        // xu ly ajax https://vtv.vn/timelinehome/trang-1.htm
        int dem = 0;
        for (int j = 1; j < 100; j++) {
            String urlAjax = urlVtv + "timelinehome/trang-" + j + ".htm";
            CrawlVtv crawlAjax = new CrawlVtv(urlAjax);
            String html1 = crawlAjax.HttpRequest();
            System.out.println(urlAjax);
            ArrayList<News> arr1 = crawlAjax.dataMucNho(html1);
            for (int i = 0; i < arr1.size(); i++) {
                System.out.println(arr1.get(i));
                dao.addNew(arr1.get(i));
            }
        }

        String urlPtit = "https://portal.ptit.edu.vn/";
        CrawlPtit crawlPtit = new CrawlPtit(urlPtit);
        String httpPtit = crawlPtit.getHttp();
        ArrayList<Menu> menuPtit = crawlPtit.dataMenu(httpPtit);
        for (int i = 0; i < menuPtit.size(); i++) {
            System.out.println(menuPtit.get(i));
        }

    }
}
