package controller;

import static controller.DAO.sc;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.ptit.Baidang;
import model.ptit.Demuc;

import model.vtv.News;

public class main {

    public static void main(String[] args) throws InterruptedException {

        while(true){
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

        for (int j = 1; j <= 15; j++) {
            String urlAjax = urlVtv + "timelinehome/trang-" + j + ".htm";
            CrawlVtv crawlAjax = new CrawlVtv(urlAjax);
            String html1 = crawlAjax.HttpRequest();
            System.out.println(urlAjax);
            ArrayList<News> arr1 = crawlAjax.dataMucNho(html1);
            for (int i = 0; i < arr1.size(); i++) {
                dao.addNew(arr1.get(i));
            }
        }

        //crawl trang portal.ptit.edu.vn
        String urlPtit = "https://portal.ptit.edu.vn/";
        CrawlPtit crawlPtit = new CrawlPtit(urlPtit);
        String htmlPtit = crawlPtit.getHttp();
        ArrayList<Demuc> arrDm = crawlPtit.dataDemuc(htmlPtit);
        for (int i = 0; i < arrDm.size(); i++) {        
            CrawlPtit crawlMuc = new CrawlPtit(arrDm.get(i).getLink());
            String htmlbaidang = crawlMuc.getHttp();
            ArrayList<Baidang> arrbaidang = crawlMuc.dataBaidang(htmlbaidang);
            arrDm.get(i).setBaidang(arrbaidang);
            dao.addDemuc(arrDm.get(i));
        }
            Thread.sleep(600000);
        }
    }
}
