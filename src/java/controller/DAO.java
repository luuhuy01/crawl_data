package controller;

import DbUtil.CloseConnection;
import DbUtil.OpenConnection;
import static DbUtil.OpenConnection.con;
import static DbUtil.OpenConnection.ps;
import static DbUtil.OpenConnection.rs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import model.ptit.Baidang;
import model.ptit.Demuc;
import model.vtv.News;

public class DAO {

    public static final int limit = 20;
    public static Scanner sc;
    public static int dem = 0;

    // thêm news vào db
    public void addNew(News news) {
        boolean res = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        new OpenConnection();
        if (res == true) {
            try {
                con.setAutoCommit(false);
                if (news.getTime() != null) {

                    String query = " insert into News (title, urlImage, link, category, times, des) values ( ?, ?, ?, ?, ?,?)";
                    ps = con.prepareStatement(query);
                    ps.setString(1, news.getTitle());
                    ps.setString(2, news.getUrlImage());
                    ps.setString(3, news.getLink());
                    ps.setString(4, news.getCategory());
                    ps.setString(5, sdf.format(news.getTime()));
                    ps.setString(6, news.getDes());
                    ps.executeUpdate();
                    dem++;
                    System.out.println("them thanh cong so tin tuc: " + dem);
                } else {
                    new OpenConnection();
                    String query = " insert into News (title, urlImage, link, category, des) values ( ?, ?, ?, ?,?)";
                    ps = con.prepareStatement(query);
                    ps.setString(1, news.getTitle());
                    ps.setString(2, news.getUrlImage());
                    ps.setString(3, news.getLink());
                    ps.setString(4, news.getCategory());
                    ps.setString(5, news.getDes());
                    ps.executeUpdate();
                    dem++;
                    System.out.println("them thanh cong so tin tuc: " + dem);
                }
                con.commit();
            } catch (Exception ex) {
                //               ex.printStackTrace();
                try {
                    con.rollback();
                } catch (SQLException ex1) {
                    ex1.printStackTrace();
                }
            } finally {
                new CloseConnection();
            }
        }

    }

    //thêm danh mục và bài đăng vào db 
    public void addDemuc(Demuc demuc) {
        try {
            new OpenConnection();
            con.setAutoCommit(false);
            String query = " insert into demuc (name, link) values ( ?, ?)";
            String sqlBaidang = "insert into baidang(idDemuc, tieude, link) values (?,?,?)";
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, demuc.getName());
            ps.setString(2, demuc.getLink());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                for (int i = 0; i < demuc.getBaidang().size(); i++) {
                    ps = con.prepareStatement(sqlBaidang, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, generatedKeys.getInt(1));
                    ps.setString(2, demuc.getBaidang().get(i).getTieude());
                    ps.setString(3, demuc.getBaidang().get(i).getLink());
                    ps.executeUpdate();
                }
            }
            con.commit();
        } catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            String sqlBaidang = "insert into baidang(idDemuc, tieude, link) values (?,?,?)";
            String sqlId = "select id from demuc where name = ?";

            try {
                ps = con.prepareStatement(sqlId);
                ps.setString(1, demuc.getName());
                ResultSet r = ps.executeQuery();
                if (r.next()) {
                    for (int i = 0; i < demuc.getBaidang().size(); i++) {
                        ps = con.prepareStatement(sqlBaidang);
                        ps.setInt(1, r.getInt(1));
                        ps.setString(2, demuc.getBaidang().get(i).getTieude());
                        ps.setString(3, demuc.getBaidang().get(i).getLink());
                        ps.executeUpdate();
                        System.out.println("update");
                    }
                }
                con.commit();
            } catch (SQLException ex1) {
                //               ex1.printStackTrace();
                try {
                    con.rollback();
                } catch (SQLException ex2) {
                    ex2.printStackTrace();
                }
            }

            //           ex.printStackTrace();
        } finally {
            new CloseConnection();
        }

    }

    // tìm kiém theo loại, tiêu đề, time
    public List<News> findBy(HttpServletRequest request) {
        new OpenConnection();
        String sql = "select * from News where category like ? AND title like ? AND times like ?";
        try {
            ps = con.prepareStatement(sql);
            String category = request.getParameter("category");
            String title = request.getParameter("title");
            String time = request.getParameter("date");
            if (category != null) {
                ps.setString(1, "%" + category + "%");
            } else {
                ps.setString(1, "%%");
            }
            if (title != null) {
                ps.setString(2, "%" + title + "%");
            } else {
                ps.setString(2, "%%");
            }
            if (time != null) {
                ps.setString(3, "%" + time + "%");
            } else {
                ps.setString(3, "%%");
            }
            return getNews(ps);
        } catch (Exception ex) {
            //           ex.printStackTrace();
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

    // lấy ngày thêm select time
    public ArrayList<String> getTime() {
        new OpenConnection();
        ArrayList<String> arr = new ArrayList<>();
        String sqlTime = "select distinct times from news";
        try {
            ps = con.prepareStatement(sqlTime);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    // lấy loại thêm vào select category
    public ArrayList<String> getCategory() {
        new OpenConnection();
        ArrayList<String> arr = new ArrayList<>();
        String sqlTime = "select distinct category from news";
        try {
            ps = con.prepareStatement(sqlTime);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    // lấy Dề mục và bài đăng
    public ArrayList<Demuc> getDemucBaiDang() {
        ArrayList<Demuc> arrDemuc = new ArrayList<>();
        ArrayList<Baidang> arrBaidang = new ArrayList<>();
        String sqlDemuc = "select * from demuc";
        String sqlBaidang = "select * from baidang where idDemuc = ?";
        try {
            ps = con.prepareStatement(sqlDemuc);
            ResultSet rs = null;
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String link = rs.getString(3);
                Demuc demuc = new Demuc(id, name, link);
                arrDemuc.add(demuc);
            }

            for (int i = 0; i < arrDemuc.size(); i++) {
                ps = con.prepareStatement(sqlBaidang);
                ps.setInt(arrDemuc.get(i).getId(), 1);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String tieude = rs.getString(2);
                    String link = rs.getString(3);
                    Baidang baidang= new Baidang(id, tieude, link);
                    arrBaidang.add(baidang);                    
                }
                arrDemuc.get(i).setBaidang(arrBaidang);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arrDemuc;
    }
   
    // lấy toàn bộ bài dăng trong danh mục
   public ArrayList<Baidang> findByIddanhmuc(HttpServletRequest request) {
        new OpenConnection();
        
        String name = request.getParameter("danhmuc");
        String sqlDanhmuc = "select id from danhmuc where name = ?";
        
        String sqlBaidang = "select * from baidang where idDanhmuc = ?";
        try {
            ps= con.prepareStatement(sqlDanhmuc);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt(1);
                ps = con.prepareStatement(sqlBaidang);
                ps.setInt(1, id);
                return getBaidang(ps);
            }            
        } catch (Exception ex) {
                ex.printStackTrace();
        }
        return new ArrayList<>();
    }
      public ArrayList<Baidang> getBaidang(PreparedStatement stmt) {
        ArrayList<Baidang> arrBaidang = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Baidang baidang = new Baidang();
                baidang.setId(rs.getInt(1));
                baidang.setTieude(rs.getString(2));
                baidang.setLink(rs.getString(3));
                arrBaidang.add(baidang);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            new CloseConnection();
        }
        return arrBaidang;
    }

}
