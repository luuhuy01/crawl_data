<%@page import="model.ptit.Baidang"%>
<%@page import="model.ptit.Demuc"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="model.vtv.News"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controller.DAO"%>
<% DAO dao = new DAO();%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    </head>
    <body>
        <h1 style="text-align: center; margin-top: 50px;">Thu thập dữ liệu site tin tức</h1>
        <h2 style="text-align: center; font-style: italic">Thu thập dữ liệu trên website <a href="https://portal.ptit.edu.vn">https://portal.ptit.edu.vn</a></h2>
        <br>
        <% 
            ArrayList<Demuc> arr = dao.getDemucBaiDang();
            
            
        %>
        <div id="content" class="container">
            <div class="demuc" style="padding: 20px">Các đề mục            
             <% for(int i=0; i<arr.size(); i++) { %>
             <form method="GET">
                <div><input name="danhmuc"  value="<%=arr.get(i).getName()%>" type="submit"></div>
            </form>            
            <div href="<%=arr.get(i).getLink()%>"> <%=arr.get(i).getLink()%> </div>            
           <% } %>
            </div>
            <% 
                request.setCharacterEncoding("UTF-8");
                ArrayList<Baidang> arrBaidang = dao.findByIddanhmuc(request);
            %>
            <div class="noidung">Nội dung
            <% for(int i=0; i<arrBaidang.size(); i++) {                    
            %>    
            <div><h4><%= arrBaidang.get(i).getTieude() %></h4></div>
            <div href="<%= arrBaidang.get(i).getLink() %>"> <%= arrBaidang.get(i).getLink() %> </div>            
           <% } %>
            
            </div>
           
            <div class="clear"></div>
        </div>

        <style>
            .demuc {
                width: 300px;
                height: 150px;
                padding: 10px;
                float: left;
            }
            .noidung {
                width: 600px;
                height: 150px;
                padding: 10px;
                float: right;
            }
            .clear { clear: both }
        </style>
    </body>
</html>
