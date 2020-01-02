<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>

<%--kindeditor--%>
<script charset="utf-8" src="${app}/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="${app}/kindeditor/lang/zh-CN.js"></script>

<script type="text/javascript">
    $(function () {
        $("#articleList").jqGrid({
            url:"${pageContext.request.contextPath}/article/showAll",
            editurl:"${pageContext.request.contextPath}/article/edit",
            datatype:"json",
            styleUI:"Bootstrap",
            pager:"#articlePager",               //分页
            rowNum:5,                           //每页显示多少条
            viewrecords:true,                   //是否显示总记录数
            autowidth:true,                     //自适应父容器
            multiselect:true,                   //是否多选
            height:"380px",
            colNames:["ID","标题","作者","内容","上师ID","创建日期","状态","操作"],
            colModel:[
                {name:"id",align:"center"},
                {name:"title",align:"center",editable:true,edittype:'text'},
                {name:"author",align:"center",editable:true,edittype:'text'},
                {name:"content",align:"center",editable:true,edittype:'text'},
                {name:"guruId",align:"center"},
                {name:"createDate",align:"center"},
                {name:"status",align:"center",editable:true,edittype:'select',editoptions:{
                        value:"展示:展示;不展示:不展示"
                }
                },
                {name:"oper",align:"center",formatter:function (celvalue,options, rowObject) {
                        return "<button onclick=\"preview()\" type='button' class='btn btn-info'><span class=\"glyphicon glyphicon-th-list\" aria-hidden=\"true\"></span></button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
                                "<button onclick=\"edit()\" type='button' class='btn btn-info'><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button>"
                    }}
            ]
        }).jqGrid("navGrid","#articlePager",{search:false,addtext:"添加",edittext:"修改",deltext:"删除"},
            {
                /*
                    修改
                */
                closeAfterEdit:true,
            },
            {
                /*
                    添加
                */
                closeAfterAdd:true,
            },
            {
                /*
                    删除
                */
                closeAfterDel: true,
            }
        );


        /*
        *   富文本编辑器
        * */
        //添加的
        window.editor2;
        window.editor3;
        var editor = KindEditor.create("#editor_id",{
            resizeType:0,                               //禁止拉动框
            minHeight:100,
            maxWidth:520,
            filePostName:'img',                             //后台接收参数
            allowFileManager:true,                          //是否展示 图片空间
            uploadJson:'${app}/article/uploadImg',          //上传后台的路径
            fileManagerJson:"${app}/article/getAllImgs",    //获取图片
            afterBlur: function () {
                this.sync();
            }
        });
        //修改的
        editor2 = KindEditor.create("#editor_id2",{
            resizeType:0,
            minHeight:100,
            maxWidth:520,
            filePostName:'img',                             //后台接收参数
            allowFileManager:true,                          //是否展示 图片空间
            uploadJson:'${app}/article/uploadImg',          //上传后台的路径
            fileManagerJson:"${app}/article/getAllImgs",    //获取图片
            afterBlur: function () {
                this.sync();
            }
        })
        //预览的
        editor3 = KindEditor.create("#previewTA",{
            resizeType:0,
            minHeight:100,
            maxWidth:520,
            items:[] //配置kindeditor编辑器的工具栏菜单项
        })

        /*
        *   添加文章
        * */
        $("#saveButton").click(function () {
            var serialize = $("#articleForm").serialize();
            $.post(
                "${app}/article/addArticle",
                serialize,
                function (result) {
                    $("#articleList").trigger("reloadGrid");
                    editor.html(null);
                    $("#articleForm")[0].reset();
                },
                "text",
            )
        })


    })
        /*
        *   修改文章
        * */
        //
        function edit() {
            var gr = $("#articleList").jqGrid('getGridParam', 'selrow');
            if (gr==null){
                alert("请选中要修改的数据");
            } else{
                var data = $("#articleList").jqGrid('getRowData', gr);
                //将数据回显到模态框
                console.log(data);
                $("#id").val(data.id);
                $("#title2").val(data.title);
                $("#author2").val(data.author);
                $("#status2").val(data.status);
                editor2.html(null);
                editor2.appendHtml(data.content);
                $("#editModal").modal("show");
            }
        }
        $("#editButton").click(function () {
            var serialize = $("#articleEditForm").serialize();
            $.post(
                "${app}/article/editArticle",
                serialize,
                function (result) {
                    $("#articleList").trigger("reloadGrid");
                    $("#articleEditForm")[0].reset();
                },
                "text",
            )
        })


        /*
        *   预览文章
        * */
        function preview(){
            var gr = $("#articleList").jqGrid('getGridParam', 'selrow');
            if (gr==null){
                alert("请选中要修改的数据");
            } else{
                var data = $("#articleList").jqGrid('getRowData', gr);
                editor3.html(data.content);
                $("#previewModal").modal("show");
            }
        }

        /*
        *   关闭模态框  清除数据
        * */
        $("#closeButton").click(function () {
            $("#articleForm")[0].reset();
        })

        /*
        *   清除数据
        * */
        $("#closeModal").click(function () {
            $("#articleForm")[0].reset();
        })

</script>
<div align="left">
    <h3><b>文章管理</b></h3>
</div>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">文章信息</a></li>
        <li role="presentation">
            <a href="#" type="button" class="btn" data-toggle="modal" data-target="#myModal">
            添加文章
            </a>
        </li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <table id="articleList"></table>
        <div id="articlePager" style="height: 50px"></div>
    </div>
    <div class="alert alert-success" style="display:none" id="msgDiv">
    </div>
</div>

<!-- 添加Modal -->
<div class="modal fade" id="myModal" data-backdrop="false" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="width: 700px">
        <div class="modal-content">
            <div class="modal-header">
                <button id="closeModal" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文章添加</h4>
            </div>
            <div class="modal-body">
                <form id="articleForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="title" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-9">
                            <input id="title" name="title" type="text" class="form-control" placeholder="请输入标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="author" class="col-sm-2 control-label">作者</label>
                        <div class="col-sm-9">
                            <input id="author" name="author" type="text" class="form-control" placeholder="请输入作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="status" class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-1" style="margin-top: 8px">
                            <select name="status" id="status">
                                <option value="展示">展示</option>
                                <option value="不展示">不展示</option>
                            </select>
                        </div>
                    </div>
                    <textarea id="editor_id" name="content" style="width:670px;height:350px;">
                    </textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button id="closeButton"  type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button id="saveButton" type="button" class="btn btn-default" data-dismiss="modal">save</button>
            </div>
        </div>
    </div>
</div>

<%--预览modal--%>
<div id="previewModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width: 700px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">预览文章</h4>
            </div>
            <div class="modal-body">
                <textarea id="previewTA" name="content" style="width:670px;height:450px;" readonly="readonly">
                </textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<%--修改modal--%>
<div id="editModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document" style="width: 700px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文章修改</h4>
            </div>
            <div class="modal-body">
                <form id="articleEditForm" class="form-horizontal">
                    <input id="id" name="id" style="display: none"></input>
                    <div class="form-group">
                        <label for="title2" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-9">
                            <input id="title2" name="title" type="text" class="form-control" placeholder="请输入标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="author2" class="col-sm-2 control-label">作者</label>
                        <div class="col-sm-9">
                            <input id="author2" name="author" type="text" class="form-control" placeholder="请输入作者">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="status2" class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-1" style="margin-top: 8px">
                            <select name="status" id="status2">
                                <option value="展示">展示</option>
                                <option value="不展示">不展示</option>
                            </select>
                        </div>
                    </div>
                    <textarea id="editor_id2" name="content" style="width:670px;height:350px;">
                    </textarea>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button id="editButton" type="button" class="btn btn-primary" data-dismiss="modal">Save changes</button>
            </div>
        </div>
    </div>
</div>