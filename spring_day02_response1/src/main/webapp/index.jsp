<%--
  Created by IntelliJ IDEA.
  User: wangzhu
  Date: 2019/6/29
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery.min.js"></script>
    <script>
        $(function () {
            $("#ajax").click(function () {
                $.ajax({
                    url: "/Con/testJson",
                    contentType: "application/json;charset=UTF-8",
                    data: '{"username":"aa","password":100}',
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        alert(data);
                        alert(data.username);
                    }
                });
            })
        })
    </script>
</head>
<body>
<a href="/User/toString">访问你了</a>
<br>
<a href="/Con/toRes">给我返回他</a>
<br>
<a href="/Con/toModelAndView">toModelAndView</a>
<br>
<button id="ajax">发送异步请求</button>
</body>
</html>
