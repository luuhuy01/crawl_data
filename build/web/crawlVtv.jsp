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
        <h2 style="text-align: center; font-style: italic">Thu thập dữ liệu trên website <a href="https://vtv.vn/">https://vtv.vn/</a></h2>
        <br>
        <%
            ArrayList<String> arrTime = dao.getTime();
            ArrayList<String> arrCate = dao.getCategory();
        %>
        <form id="filter" style="text-align: center;" method="GET" >
            <input name="title" type="text" placeholder="Search" aria-label="Search">
            <select style="height: 27px" name="category">
                <option value="">Tất cả</option>
                <% for (int i = 0; i < arrCate.size(); i++) { %>               
                <option value="<%=arrCate.get(i)%>"><%=arrCate.get(i)%></option>
                <% }%>
            </select>
            <select style="height: 27px" name="date">
                <option value="">Tất cả</option>
                <% for (int i = 0; i < arrTime.size(); i++) {%>
                <option value="<%=arrTime.get(i)%>"><%=arrTime.get(i)%></option>
                <% }%>
            </select>
            <input type="submit" class="btn btn-primary" value="Tìm kiếm" >
        </form>
        <table border="1" cellpadding="2" cellspacing="0" style="table-layout:fixed;">
            <tr>
                <th width="5%">id</th>
                <th width="15%">title</th>
                <th width="15%">urlImage</th>
                <th width="15%">link</th>
                <th width="10%">category</th>
                <th width="10%">times</th>
                <th width="30%">description</th>
            </tr>
            <%
                request.setCharacterEncoding("UTF-8");
                List<News> newss;
                newss = dao.findBy(request);
            %>

            <% for (News news : newss) {%>
            <tr>
                <td width="5%"><%=news.getId()%></td>
                <td width="15%"><%=news.getTitle()%></td>
                <td width="15%"><a href="<%=news.getUrlImage()%>"><img target="_blank" src="<%=news.getUrlImage()%>" ></a></td>
                <td width="15%"><a target="_blank" href="<%=news.getLink()%>"><%=news.getLink()%></a></td>
                <td width="10%"><%=news.getCategory()%></td>
                <td width="10%"><%=news.getTime()%></td>
                <td width="30%"><%=news.getDes()%></td>
            </tr>
            <%  }%>
        </table>
    </body>
</html>
