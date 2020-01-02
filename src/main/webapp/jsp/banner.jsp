<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>

<script type="text/javascript">
    $(function () {
        $("#bannerList").jqGrid({
            url:"${pageContext.request.contextPath}/banner/showAll",
            editurl:"${pageContext.request.contextPath}/banner/oper",
            datatype:"json",
            styleUI:"Bootstrap",
            pager:"#bannerPager",               //分页
            rowNum: 3,                           //每页显示多少条
            viewrecords:true,                   //是否显示总记录数
            autowidth:true,                     //自适应父容器
            multiselect:true,                   //是否多选
            height:"350px",
            colNames:["ID","名字","路径","日期","状态"],
            colModel:[
                {name:"id",align:"center"},
                {name:"title",align:"center",editable:true,edittype:'text'},
                {name:"img",align:"center",editable:true,edittype:'file',
                    formatter:function (cellvalue, options, rowObject) {
                        return "<img src='${pageContext.request.contextPath}/upload/"+cellvalue+"' style='width:100%;height:100px'/>"
                    }
                },
                {name:"create_date",align:"center"},
                {name:"status",align:"center",editable:true,edittype:'select',editoptions:{
                        value:"展示:展示;不展示:不展示"
                }
                }
            ]
        }).jqGrid("navGrid","#bannerPager",{search:false,addtext:"添加",edittext:"修改",deltext:"删除"},
            {
                /*
                    修改
                */
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var id = response.responseJSON.id;
                    var msg = response.responseJSON.meg;
                    if(id!=null){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/banner/bannerUpload",
                            fileElementId:'img',
                            data:{id:id},
                            type:"post",
                            success:function(){
                                $("#bannerList").trigger("reloadGrid");
                                $("#msgDiv").html(meg).show();
                                setTimeout(function () {
                                    $("#msgDiv"). hide();
                                },3000)
                            }
                        })
                    }
                    return response;
                }
            },
            {
                /*
                    添加
                */
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var id = response.responseJSON.id;
                    var msg = response.responseJSON.msg;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/bannerUpload",
                        fileElementId:'img',
                        data:{id:id},
                        type:"post",
                        success:function(){
                            $("#bannerList").trigger("reloadGrid");
                            $("#msgDiv").html(msg).show();
                            setTimeout(function () {
                                $("#msgDiv").hide();
                            },3000)
                        }
                    })
                    return response;
                }
            },
            {
                /*
                    删除
                */

            }
        );
    })
</script>


<div align="left">
    <h3><b>轮播图管理</b></h3>
</div>
<div class="panel panel-default">
    <div class="panel-heading" align="left">
        <h3 class="panel-title">轮播图信息&nbsp;&nbsp;
            <span><a href="${app}/banner/easyPoi">导出轮播图</a></span>
        </h3>
    </div>
    <div class="panel-body">
        <table id="bannerList"></table>
        <div id="bannerPager" style="height: 50px"></div>
    </div>
</div>
<div class="alert alert-success" style="display:none" id="msgDiv">
</div>
