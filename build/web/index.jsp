<%-- 
    Document   : index
    Created on : Dec 2, 2020, 3:58:19 AM
    Author     : luuhu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bóc tách dữ liệu</title>
    </head>
    <body style="text-align: center">
        <h1>Chọn trang bóc tách dữ liệu</h1>
        <form action="crawlVtv.jsp" method="GET">
            <input type="submit" style="width: 200px; padding: 10px; margin: 10px" value="vtv.vn">
        </form>
        <form action="crawlPortalPtit.jsp" method="GET">
            <input type="submit" style="width: 200px; padding: 10px; margin: 10px" value="portal.ptit.edu.vn">
        </form>
    </body>
</html>
