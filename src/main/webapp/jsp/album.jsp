<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<style>
    .ui-jqgrid .ui-userdata {
        padding: 13px 94px;
        overflow: hidden;
        min-height: 32px;
    }
</style>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url:"${app}/album/showAll",
            editurl:"${app}/album/oper",
            datatype:"json",
            styleUI:"Bootstrap",
            pager:"#albumPager",                 //分页
            rowNum:4,                           //每页显示多少条
            viewrecords:true,                   //是否显示总记录数
            autowidth:true,                     //自适应父容器
            multiselect:true,                   //是否多选
            height:"350px",
            caption:"专辑",
            colNames:["ID","标题","图片","评分","作者","播音员","数量","简介","日期","状态"],
            colModel:[
                {name:"id",align:"center"},
                {name:"title",align:"center",editable:true,edittype:'text'},
                {name:"img",align:"center",editable:true,edittype:'file',formatter:
                        function (cellvalue, options, rowObject) {
                        return "<img src='${app}/album/img/"+cellvalue+"' style='width:100%;height:70px'/>"
                        }
                },
                {name:"score",align:"center",editable:true,edittype:'text'},
                {name:"author",align:"center",editable:true,edittype:'text'},
                {name:"broadcaster",align:"center",editable:true,edittype:'text'},
                {name:"count",align:"center"},
                {name:"brief",align:"center",editable:true,edittype:'text'},
                {name:"create_Date",align:"center"},
                {name:"status",align:"center",editable:true,edittype:'select',editoptions:{
                        value:"展示:展示;不展示:不展示"
                    }
                }
            ],
            subGrid:true,
            subGridRowExpanded:function (subGridId,albumId) {
                addSubGrid(subGridId,albumId);
            }
        }).jqGrid("navGrid","#albumPager",{search:false,addtext:"添加",edittext:"修改",deltext:"删除"},
            {
                /*
                *   修改
                * */
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var id = response.responseJSON.id;
                    if (id!=null){
                        $.ajaxFileUpload({
                            url:"${app}/album/albumUpload",
                            fileElementId:'img',
                            data:{id:id},
                            type:"post",
                            success:function () {
                                $("#albumList").trigger("reloadGrid");
                            }
                        })
                    }
                    return response;
                }
            },
            {
                /*
                *   添加
                * */
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var id = response.responseJSON.id;
                    $.ajaxFileUpload({
                        url:"${app}/album/albumUpload",
                        fileElementId:'img',
                        data:{id:id},
                        type:"post",
                        success:function () {
                            $("#albumList").trigger("reloadGrid");
                        }
                    })
                    return response;
                }
            },
            {
                /*
                *   删除
                * */
                closeAfterDel: true,
            }
        );

        /*
        *   动态添加子表格
        * */
        function addSubGrid(subGridId,albumId) {
            //动态table  id
            var subGridTableId = subGridId+"table";
            //动态div   id
            var subGridDivId = albumId+"div";
            $("#"+subGridId).html("<table id='"+subGridTableId+"'></table>"+
                                    "<div id='"+subGridDivId+"' style='height:50px'></div>"
                                    )
            $("#"+subGridTableId).jqGrid({
                url:"${app}/chapter/showAll?id="+albumId,
                editurl:"${app}/chapter/oper?album_Id="+albumId,
                datatype:"json",
                styleUI:"Bootstrap",
                pager:"#"+subGridDivId,             //分页
                rowNum:3,                           //每页显示多少条
                viewrecords:true,                   //是否显示总记录数
                autowidth:true,                     //自适应父容器
                multiselect:true,                   //是否多选
                height: "350px",
                caption:"章节",
                toolbar:[true,"top"],
                colNames:["ID","标题","文件大小","时长","地址","状态"],
                colModel: [
                    {name:"id",align:"center"},
                    {name:"title",align:"center",editable:true,edittype:'text'},
                    {name:"size",align:"center"},
                    {name:"duration",align:"center"},
                    {name:"src",align:"center",editable:true,edittype:'file',formatter:
                            function (cellvalue, options, rowObject) {
                                return "<audio src='${app}/album/chapter/"+cellvalue+"' controls='controls' style='width:100%;height:30px'/>"
                            }},
                    {name:"status",align:"center",editable:true,edittype:'text',edittype:'select',editoptions:{
                            value:"展示:展示;不展示:不展示"
                        }},
                ]
            }).jqGrid("navGrid","#"+subGridDivId,{search:false,addtext:"添加",edittext:"修改",deltext:"删除"},
                {
                    /*
                    *   修改
                    * */
                    closeAfterEdit: true,
                    afterSubmit:function (response) {
                        var id = response.responseJSON.id;
                        if (id!=null){
                            $.ajaxFileUpload({
                                url:"${app}/chapter/chapterUpload",
                                fileElementId:'src',
                                data:{id:id},
                                type:"post",
                                success:function () {
                                    $("#"+subGridTableId).trigger("reloadGrid");
                                }
                            })
                        }
                        return response;
                    }
                },
                {
                    /*
                    *   添加
                    * */
                    closeAfterAdd: true,
                    afterSubmit:function (response) {
                        var id = response.responseJSON.id;
                        $.ajaxFileUpload({
                            url:"${app}/chapter/chapterUpload",
                            fileElementId:'src',
                            data:{id:id},
                            type:"post",
                            success:function () {
                                $("#albumList").trigger("reloadGrid");
                                $("#"+subGridTableId).trigger("reloadGrid");
                            }
                        })
                        return response;
                    }
                },
                {
                    /*
                    *   删除
                    * */
                    closeAfterDel: true,
                    afterComplete:function () {
                        $("#albumList").trigger("reloadGrid");
                    }
                }
            );

            //添加按钮
            $("#t_"+subGridTableId).html("<button type=\"button\" class=\"btn btn-danger\" onclick=\"play('"+subGridTableId+"')\">播放 <span class='glyphicon glyphicon-play'></span></button>"+
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
                "<button class='btn btn-danger' onclick=\"download('"+subGridTableId+"')\">下载 <span class='glyphicon glyphicon-arrow-down'></span></button>"
            )
        }
    })
    /*
        *   播放
        * */
    function play(subGridTableId) {
        // 判断 用户是否选中一行
        var gr = $("#"+subGridTableId).jqGrid('getGridParam', 'selrow');
        if(gr == null){
            alert("请选中要播放的音频");
        }else{
            //jqgrid 提供的方法 根据id拿到对应的值
            var data = $("#"+subGridTableId).jqGrid('getRowData', gr);
            var audio = $(data.src)[0];
            var src = $(audio).prop("src");
            $("#myModal").modal("show");
            $("#myAudio").prop("src",src);
        }
    }

    /*
    *   下载
    * */
    function download(subGridTableId){
        var gr = $("#"+subGridTableId).jqGrid('getGridParam', 'selrow');
        if(gr == null){
            alert("请选中要下载的音频");
        }else{
            //jqgrid 提供的方法 根据id拿到对应的值
            var data = $("#"+subGridTableId).jqGrid('getRowData', gr);
            var audio = $(data.src)[0];
            var src = $(audio).prop("src");
            location.href="${app}/chapter/chapterDownload?src="+src;

        }
    }
</script>

<div align="left">
    <h3><b>专辑管理</b></h3>
</div>
<div class="panel panel-default">
    <div class="panel-heading" align="left">
        <h3 class="panel-title">专辑信息</h3>
    </div>
    <div class="panel-body">
        <table id="albumList"></table>
        <div id="albumPager" style="height: 50px"></div>
    </div>
</div>

<%--
    播放模态框
--%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                <audio id="myAudio" src="" controls="controls" autoplay="autoplay"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>