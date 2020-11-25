<%@page import="java.util.List"%>
<%@page import="model.News"%>
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
<h1 style="text-align: center; margin-top: 50px;">Demo thu thập dữ liệu site tin tức</h1>
<p style="text-align: center; font-style: italic">Thu thập dữ liệu trên website <a href="https://vtv.vn/">https://vtv.vn/</a></p>
<br>
<form id="filter" style="text-align: center;" method="GET" >
    <input name="des" type="text" placeholder="Search" aria-label="Search">
    <select style="height: 27px" name="category">
        <option value="">Tất cả</option>
        <option value="Xã hội">Xã hội</option>
        <option value="Chính trị">Chính trị</option>
        <option value="Pháp luật">Pháp luật</option>
        <option value="Thế giới">Thế giới</option>
        <option value="Kinh tế">Kinh tế</option>
        <option value="Truyền hình">Truyền hình</option>
        <option value="Văn hoá - Giải trí">Văn hóa giải trí</option>
        <option value="Cong nghe">Công nghệ</option>
        <option value="Giao duc">Giáo dục</option>
        <option value="Tam long viet">Tấm lòng việt</option>
    </select>
    <input type="submit" class="btn btn-primary" value="submit" >
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
<%  } %>
</table>
</body>
<script>
//    window.onload = function() {
//        var form = document.getElementById('filter');
//        form.onsubmit = function() {
//            var formData = new FormData(form);
//            console.log(formData);
//            alert(formData);
//            window.location.href = "?category=" + formData.get();
//        }
//    }
</script>
</html>
