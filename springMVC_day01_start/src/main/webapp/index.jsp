<%--
  Created by IntelliJ IDEA.
  User: wangzhu
  Date: 2019/6/26
  Time: 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>入门程序</title>
</head>
<body>
<%--   <form action="/param/setParam" method="post">--%>
<%--       用户名：<input type="text" name="username"></br>--%>
<%--       密码：<input type="text" name="password"></br>--%>
<%--       金币：<input type="text" name="money"></br>--%>
<%--       姓名：<input type="text" name="User.name"></br>--%>
<%--       年龄：<input type="text" name="User.age"></br>--%>
<%--       <input type="submit" value="提交">--%>
<%--   </form>--%>
<form action="/param/stringTo" method="post">
    用户名：<input type="text" name="username"></br>
    密码：<input type="text" name="password"></br>
    金币：<input type="text" name="money"></br>
    生日：<input type="text" name="date">
    <input type="submit" value="提交">
</form>
</body>
</html>
