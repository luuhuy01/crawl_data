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
        String[] type = {"chinh-tri.htm",
             "xa-hoi.htm",
             "phap-luat.htm",
             "the-gioi.htm", "kinh-te.htm",
            "truyen-hinh.htm", "van-hoa-giai-tri.htm", "cong-nghe.htm", "giao-duc.htm", "tam-long-viet.htm"
        };
        for (int i = 1; i < 100; i++) {
            String url = "https://vtv.vn/" + type[i];
//            String url = "https://vtv.vn/" + "timeline/180/trang-"+i+".htm";
            System.out.println(url);
            CrawlData crawl = new CrawlData(url);
            String html = crawl.HttpRequest();
            ArrayList<News> arr = crawl.tachData(html);
            for (int j = 0; j < arr.size(); j++) {
//                System.out.println(arr.get(j));
                dao.addNew(arr.get(j));
            }
        }
//        sc = new Scanner(System.in);
//        while (true) {
//            System.out.println("1. tim kiem theo tieu de.");
//            System.out.println("2. tim kiem theo the loai.");
//            System.out.println("3. tim kiem theo mieu ta.");
//            System.out.println("4. tim kiem theo thoi gian.");
//            System.out.println("5. tim kiem day du.");
//            System.out.println("6. delete.");
//            System.out.println("7. update.");
//            System.out.println("Xin moi chon:");
//            int choice = sc.nextInt();
//            switch (choice) {
//                case 1:
//                    dao.findByTitle();
//                    break;
//                case 2:
//                    dao.findByCategory();
//                    break;
//                case 3:
//                    dao.findByDes();
//                    break;
//                case 4:
//                    dao.findByTimes();
//                    break;
//                case 5:
//                    dao.findObject();
//                    break;
//                case 6:
//                    System.out.println("chon id muon xoa:");
//                    dao.delete(sc.nextInt());
//                    break;
//                case 7: {
//                    News n = new News();
//                    System.out.println("nhap vao thong tin can sua:");
//                    System.out.println("Nhap vao id:");
//                    n.setId(sc.nextInt());
//                    System.out.println("nhap vao tieu de:");
//                    n.setTitle(sc.nextLine());
//                    n.setTitle(sc.nextLine());
//                    System.out.println("nhap vao ten anh:");
//                    n.setUrlImage(sc.nextLine());
//                    System.out.println("nhap vao link:");
//                    n.setLink(sc.nextLine());
//                    System.out.println("nhap vao the loai:");
//                    n.setCategory(sc.nextLine());
//                    while (true) {
//                        System.out.println("nhap vao thoi gian theo dinh dang yyyy-MM-dd:");
//                        String date = sc.nextLine();
//                        try {
//                            if (dao.validateJavaDate(date) == true) {
//                                n.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
//                                break;
//                            } else {
//                                System.out.println("sai dinh dang yyyy-MM-dd");
//                            }
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                    System.out.println("nhap vao mieu ta:");
//                    n.setDes(sc.nextLine());
//                    dao.update(n);
//                }
//                default:
//                    break;
//            }
//            System.out.println("ban co muon tiep tuc: 1.YES/2.NO");
//            choice = sc.nextInt();
//            if (choice == 2) {
//                break;
//            }
//        }
    }
}
