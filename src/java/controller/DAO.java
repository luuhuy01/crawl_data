package controller;

import DbUtil.CloseConnection;
import DbUtil.OpenConnection;
import static DbUtil.OpenConnection.con;
import static DbUtil.OpenConnection.ps;
import static DbUtil.OpenConnection.rs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import model.News;

public class DAO {
    public static final int limit = 20;
    public static Scanner sc;

    public void addNew(News news) {
        boolean res = true;
        news.setTitle(news.getTitle().replaceAll("[\'\"]*" ,  ""));
        String sql = "select title from News where title like '%" + news.getTitle() + "%'";        
        try {
            new OpenConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                res = false;
            }
        } catch (Exception ex) {
            System.out.println(sql);
            System.out.println(news.getTitle());
            ex.printStackTrace();
        } finally {
            new CloseConnection();
        }
        if (res == true) {
            try {
                new OpenConnection();
                String query = " insert into News (title, urlImage, link, category, times,des) values (?, ?, ?, ?, ?,?)";
                ps = con.prepareStatement(query);
                ps.setString(1, news.getTitle());
                ps.setString(2, news.getUrlImage());
                ps.setString(3, news.getLink());
                ps.setString(4, news.getCategory());
                ps.setObject(5, news.getTime().toInstant().atZone(ZoneId.of("Africa/Tunis")).toLocalDate());
                ps.setString(6, news.getDes());
                int row = ps.executeUpdate();
                System.out.println("them thanh cong " + row);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                new CloseConnection();
            }
        }
    }
    
   public List<News> findBy(HttpServletRequest request){
        new OpenConnection();
//        List<String> keys = new ArrayList<String>() {{
//            add("id");
//            add("title");
//            add("des");
//            add("category");
//            add("urlImage");
//            add("times");
//            add("link");
//        }};
        String sql = "select * from News where category like ? AND des like ?";
        try {
            ps = con.prepareStatement(sql);
            String category = request.getParameter("category");
            String des = request.getParameter("des");
            if (category != null)
                ps.setString(1, "%" + category + "%");
            else
                ps.setString(1, "%%");
            if (des != null)
                ps.setString(2, "%" + des + "%");
            else
                ps.setString(2, "%%");
            return getNews(ps);
        }  catch (Exception ex) {
        }
        return new ArrayList<>();
   }
   
    public List<News> getNews(PreparedStatement stmt) {
        ArrayList<News> listNews = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                News news = new News();
                news.setId(rs.getInt(1));
                news.setTitle(rs.getString(2));
                news.setUrlImage(rs.getString(3));
                news.setLink(rs.getString(4));
                news.setCategory(rs.getString(5));
                news.setTime(rs.getDate(6));
                news.setDes(rs.getString(7));
                listNews.add(news);
            }
        } catch (Exception ex) {
                ex.printStackTrace();
        } finally {
            new CloseConnection();
        }
        return listNews;
    }

    public void findByTitle() {
        String tmp = sc.nextLine();
        String sql = "select * from News where title like %1";
        this.truyVan(sql,1);
    }

    public String findByCategory() {
        sc = new Scanner(System.in, "UTF-8");
        String tmp = sc.nextLine();
        String sql = "select * from News where category like '%1%'";
        this.truyVan(sql,1);
        return "1";
    }

    public void findByDes() {
        sc = new Scanner(System.in, "UTF-8");
        String tmp = sc.nextLine();
        String sql = "select * from News where des like '%" + tmp + "%'";
        this.truyVan(sql,1);
    }

    public void findByTimes() {
        sc = new Scanner(System.in);
        String tmp = sc.nextLine();
        if (this.validateJavaDate(tmp)) {
            String sql = "select * from News where times between '" + tmp + " 00:00:00' and '" + tmp + " 23:59:59'";
            this.truyVan(sql,1);
        } else {
            System.out.println("Moi nhap lai voi dinh dang yyyy-MM-dd:");
            this.findByTimes();
        }
    }

    public void findObject() {
        sc = new Scanner(System.in);
        News n = new News();
        System.out.println("nhap vao tieu de:");
        n.setTitle(sc.nextLine());
        System.out.println("nhap vao ten anh:");
        n.setUrlImage(sc.nextLine());
        System.out.println("nhap vao link:");
        n.setLink(sc.nextLine());
        System.out.println("nhap vao the loai:");
        n.setCategory(sc.nextLine());
        System.out.println("nhap vao thoi gian:");
        String date = sc.nextLine();
        try {
            if (this.validateJavaDate(date) == true) {
                n.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            } else {
                System.out.println("sai dinh dang yyyy-MM-dd");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("nhap vao mieu ta:");
        n.setDes(sc.nextLine());
        StringBuffer where = new StringBuffer("  where 1 = 1 ");
        boolean coWhere = false;
        if (!n.getTitle().isEmpty()) {
            where.append(" and title like '%" + n.getTitle() + "%'");
            coWhere = true;
        }
        if (!n.getUrlImage().isEmpty()) {
            where.append(" and urlImage like '%" + n.getUrlImage() + "%'");
            coWhere = true;
        }
        if (!n.getLink().isEmpty()) {
            where.append(" and link like '%" + n.getLink() + "%'");
            coWhere = true;
        }
        if (!n.getCategory().isEmpty()) {
            where.append(" and category like '%" + n.getCategory() + "%'");
            coWhere = true;
        }
        if (n.getTime() != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = dateFormat.format(n.getTime());
            where.append(" and times between '" + strDate + " 00:00:00' and '" + strDate + " 23:59:59'");
            System.out.println(n.getTime().toString());
            coWhere = true;
        }
        if (!n.getDes().isEmpty()) {
            where.append(" and des like '%" + n.getDes() + "%'");
            coWhere = true;
        }
        String hql = "select * from News ";
        if (coWhere) {
            hql = hql + where;
        }
        this.truyVan(hql,1);
    }

    public void delete(int id) {
        String sql = "DELETE FROM News where id=" + id;
        if (this.truyVan(sql,2) == true) {
            System.out.println("xoa thanh cong!");
        } else {
            System.out.println("xoa KHONG thanh cong....");
        }

    }

    public void update(News n) {
        String sql = "UPDATE News SET title ='" + n.getTitle() + "', urlImage='" + n.getUrlImage()
                + "', link ='" + n.getLink() + "', category='" + n.getCategory() + "', des='" + n.getDes() + "'";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = dateFormat.format(n.getTime());
        sql = sql + ",times = '"+strDate+"' where ID="+n.getId();
        if (this.truyVan(sql,2) == true) {
            System.out.println("cap nhat thanh cong!");
        } else {
            System.out.println("cap nhat KHONG thanh cong....");
        }
    }

    private boolean truyVan(String sql, int t) {
        boolean check = false;
        try {
            new OpenConnection();
            ps = con.prepareStatement(sql);
            if (t == 1) {
                rs = ps.executeQuery();
                News n = new News();
                while (rs.next()) {
                    n.setId(rs.getInt(1));
                    n.setTitle(rs.getString(2));
                    n.setUrlImage(rs.getString(3));
                    n.setLink(rs.getString(4));
                    n.setCategory(rs.getString(5));
                    n.setTime(rs.getDate(6));
                    n.setDes(rs.getString(7));
                    System.out.println(n);
                    check = true;
                }
            }
            else {
                int row = ps.executeUpdate();
                System.out.println("so "+row+" row da dc update");
                check = true;
            }
        } catch (Exception ex) {
            check = false;
            ex.printStackTrace();
        } finally {
            new CloseConnection();
        }
        return check;
    }

    boolean validateJavaDate(String strDate) {
        if (strDate.trim().equals("")) {
            return true;
        } else {
            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
            sdfrmt.setLenient(false);
            try {
                Date javaDate = sdfrmt.parse(strDate);
            } catch (ParseException e) {
                return false;
            }
            return true;
        }
    }
}
