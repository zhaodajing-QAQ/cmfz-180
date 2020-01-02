<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>main</title>
    <!-- CSS -->
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="assets/js/jquery-3.3.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <!-- CSS -->
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <script src="../jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <%--上传--%>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <!-- 引入 echarts.js -->
    <script src="../echarts/echarts.min.js"></script>
    <script src="../echarts/china.js"></script>
    <%--引Goeasy--%>
    <script src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>


    <script type="text/javascript">
        $(function () {
            $("#bannerist").click(function () {
                $("#centent").load("banner.jsp");
            })
        })
    </script>
</head>

<body>
    <div>
        <%--
            导航栏
        --%>
        <div>
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <a class="navbar-brand" href="#">持明法州管理系统</a>
                    </div>
                    <div class="nav navbar-right" style="margin-top: 10px">
                        <span><a href="${app}/admin/outPoi">导出管理员信息</a></span>&nbsp;&nbsp;
                        <span>欢迎<b>[zdj]</b></span>&nbsp;&nbsp;
                        <a href="#" aria-label="Left Align">
                            退出登录
                            <span class="glyphicon glyphicon glyphicon-log-out" aria-hidden="true"></span>
                        </a>
                    </div>
                </div>
            </nav>
        </div>

        <%--
            左侧导航
        --%>
        <div class="col-sm-2">
            <div class="panel-group">
                <!--
                    用户管理
                -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a id="usergl" href="#first" data-toggle="collapse" data-parent="#parent">
                                用户管理
                            </a>
                        </h3>
                    </div>

                    <div id="first" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="#">
                                <button id="userlist" class="btn btn-default btn-danger col-sm-12">用户列表</button>
                            </a>
                        </div>
                    </div>
                </div>
                <!--
                    上师管理
                -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a id="gurugl" href="#second" data-toggle="collapse" data-parent="#parent">
                                上师管理
                            </a>
                        </h3>
                    </div>

                    <div id="second" class="panel-collapse collapse">
                        <div class="panel-body">
                            <button id="gurulist" class="btn btn-default btn-danger col-sm-12">上师列表</button>
                        </div>
                    </div>
                </div>
                <!--
                    文章管理
                -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a id="articlegl" href="#thred" data-toggle="collapse" data-parent="#parent">
                                文章管理
                            </a>
                        </h3>
                    </div>

                    <div id="thred" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#centent').load('article.jsp');">
                            <button id="articlelist" class="btn btn-default btn-danger col-sm-12">文章列表</button>
                            </a>
                        </div>
                    </div>
                </div>
                <!--
                    专辑管理
                -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a id="albumgl" href="#four" data-toggle="collapse" data-parent="#parent">
                                专辑管理
                            </a>
                        </h3>
                    </div>

                    <div id="four" class="panel-collapse collapse">
                        <div class="panel-body">
                            <a href="javascript:$('#centent').load('album.jsp');">
                                <button id="albumlist" class="btn btn-default btn-danger col-sm-12">
                                    专辑列表
                                </button>
                            </a>
                        </div>
                    </div>
                </div>
                <!--
                    轮播图管理
                -->
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a id="bannergl" href="#five" data-toggle="collapse" data-parent="#parent">
                                轮播图管理
                            </a>
                        </h3>
                    </div>

                    <div id="five" class="panel-collapse collapse">
                        <div class="panel-body">
                            <button id="bannerist" class="btn btn-default btn-danger col-sm-12">轮播图列表</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--
            右侧
        --%>
        <div id="centent" class="col-sm-10">
            <%--
                大标题
            --%>
            <div class="jumbotron">
                <h2>欢迎来到持明法州后台管理系统</h2>
            </div>

            <%--
                轮播图展示
            --%>
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
                    <div class="item active">
                        <img src="../img/shouye.jpg" >
                    </div>
                    <div class="item">
                        <img src="../img/shouye.jpg" >
                    </div>
                    <div class="item">
                        <img src="../img/shouye.jpg" >
                    </div>
                </div>

                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                </a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                </a>
            </div>
        </div>
        <%--
            底部内容
        --%>
            <nav class="navbar navbar-default navbar-fixed-bottom">
                <div class="container">
                    <h6>@百知教育&nbsp;baizhi@zparkhr.com.cn</h6>
                </div>
            </nav>

    </div>
</body>

</html>