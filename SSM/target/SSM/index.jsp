<%--
  Created by IntelliJ IDEA.
  User: wangzhu
  Date: 2019/6/30
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <a href="/User/SSM">MVC</a>


    <form action="/User/save" method="post">
        姓名：<input type="text" name="name"/><br>
        金币：<input type="text" name="money"/><br>
        <input type="submit" value="提交"/>
    </form>
</body>
</html>
