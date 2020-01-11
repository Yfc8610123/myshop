
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../includes/header.jsp"/>
    <link rel="stylesheet" href="/static/assets/plugins/jquery-ztree/css/zTreeStyle/zTreeStyle.min.css">
    <title>我的商城|内容管理</title>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <jsp:include page="../includes/nav.jsp"/>
    <jsp:include page="../includes/menu.jsp"/>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                内容管理
                <small></small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/main"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active">内容管理</li>
            </ol>
        </section>
        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <c:if test="${baseResult != null}">
                        <div class="alert alert-${baseResult.status == 200 ?'success':'danger'} alert-dismissible">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                            ${baseResult.message}
                        </div>
                    </c:if>
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">${tbContent.id==null?"新增":"编辑"}内容</h3>
                        </div>
                        <!-- /.box-header -->
                        <!-- form start -->
                        <form:form id="inputForm" cssClass="form-horizontal" action="/content/category/save" method="post" modelAttribute="tbContentCategory">
                            <form:hidden path="id"/>
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="parent.id" class="col-sm-2 control-label ">父级类目</label>
                                    <div class="col-sm-10">
                                        <form:hidden path="parent.id"/>
                                        <input class="form-control required" id="parentName" placeholder="${tbContentCategory.parent.name}" readonly="true" data-toggle="modal" data-target="#modal-default"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="name" class="col-sm-2 control-label">标题</label>
                                    <div class="col-sm-10">
                                        <form:input cssClass="form-control required" path="name" placeholder="分类名称"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="sortOrder" class="col-sm-2 control-label">排序</label>
                                    <div class="col-sm-10">
                                        <form:input cssClass="form-control required digits" path="sortOrder" placeholder="排序"/>
                                    </div>
                                </div>

                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="button" class="btn btn-default" onclick="history.go(-1);">返回</button>
                                <button type="submit" class="btn btn-info pull-right">提交</button>
                            </div>
                            <!-- /.box-footer -->
                        </form:form>
                    </div>
                </div>
            </div>

        </section>
    </div>
    <!-- /.content-wrapper -->
    <jsp:include page="../includes/copyright.jsp"/>
</div>
<jsp:include page="../includes/footer.jsp"/>
<script src="/static/assets/plugins/jquery-ztree/js/jquery.ztree.core-3.5.min.js"></script>
<sys:modal title="请选择" msg="<ul id='myTree' class='ztree'></ul>"/>
<script>
    $(function () {
        App.initZtree("/content/category/tree/data",["id"],function (nodes) {
            var node = nodes[0];
            $("#parentId").val(node.id);
            $("#parentName").val(node.name);
            $("#modal-default").modal("hide");
        });
    });
</script>
</body>
</html>
