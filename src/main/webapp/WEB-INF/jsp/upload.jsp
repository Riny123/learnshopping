<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/12/10
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图片上传</title>
</head>
<body>
    <%--如果action的值为空的话，会访问到当前浏览器对应的路径下--%>
    <form action="" method="post" enctype="multipart/form-data">
        <input type="file" name="upload_file">
        <input type="submit" value="图片上传">
    </form>
</body>
</html>
