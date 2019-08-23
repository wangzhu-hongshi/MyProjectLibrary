<%--
  Created by IntelliJ IDEA.
  User: wangzhu
  Date: 2019/6/29
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/user/tofile" method="post" enctype="multipart/form-data">
        选择文件：<input type="file" name="upload">
        <br>
        <input type="submit" value="提交文件">
    </form>
    <br>
    <form action="/user/tofile2" method="post" enctype="multipart/form-data">
        选择文件：<input type="file" name="upload">
        <br>
        <input type="submit" value="提交文件">
    </form>
</body>
</html>
