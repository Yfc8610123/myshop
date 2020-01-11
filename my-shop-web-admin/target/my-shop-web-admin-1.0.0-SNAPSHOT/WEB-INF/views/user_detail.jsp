
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../includes/header.jsp"/>
    <title>我的商城|用户详情</title>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="box-body">
    <table class="table table-hover" id="Tbuser_table">
        <tbody>
        <tr>
            <td>邮箱：</td>
            <td>${tbUser.email}</td>
        </tr>
        <tr>
            <td>姓名：</td>
            <td>${tbUser.username}</td>
        </tr>
        <tr>
            <td>手机：</td>
            <td>${tbUser.phone}</td>
        </tr>
        <tr>
            <td>创建时间：</td>
            <td><fmt:formatDate value="${tbUser.created}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
        </tr>
        <tr>
            <td>更新时间：</td>
            <td><fmt:formatDate value="${tbUser.updated}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
        </tr>
        </tbody>
    </table>

</div>
<jsp:include page="../includes/footer.jsp"/>


</body>
</html>
