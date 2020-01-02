<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <script src="assets/js/jquery-3.3.1.min.js">    </script>
    <!-- 引入 echarts.js -->
    <script src="../echarts/echarts.min.js"></script>
    <script src="../echarts/china.js"></script>
    <%--引Goeasy--%>
    <script src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>

    <script type="text/javascript">
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-f68d6b29c552443e9fc0725979b37a51", //替换为您的应用appkey
        });

        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            var myChart2 = echarts.init(document.getElementById('second'));
            var myChart3 = echarts.init(document.getElementById('third'));

            // 指定图表的配置项和数据
            option = {
                title: {
                    text: '最近七天用户注册表'
                },
                tooltip: {},
                legend: {
                    data:['注册数']
                },
                xAxis: {
                    type: 'category',
                    data: [ ]
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    name: '注册数',
                    type: 'bar'
                }]
            };


            option2 = {
                title: {
                    text: '1-12月用户注册表'
                },
                tooltip: {},
                legend: {
                    data:['注册数']
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: []
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    name:'注册数',
                    type: 'line',
                    areaStyle: {}
                }]
            };


            option3 = {
                title : {
                    text: '用户地理分布图',
                    left: 'center'
                },
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:['用户']
                },
                visualMap: {
                    min: 0,
                    max: 2500,
                    left: 'left',
                    top: 'bottom',
                    text:['高','低'],           // 文本，默认为数值文本
                    calculable : true
                },
                toolbox: {
                    show: true,
                    orient : 'vertical',
                    left: 'right',
                    top: 'center',
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                series : [
                    {
                        name: '用户',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data:[
                            /*{name: '北京',value: Math.round(Math.random()*1000)},
                            {name: '天津',value: Math.round(Math.random()*1000)},
                            {name: '上海',value: Math.round(Math.random()*1000)},
                            {name: '重庆',value: Math.round(Math.random()*1000)},
                            {name: '河北',value: Math.round(Math.random()*1000)},
                            {name: '安徽',value: Math.round(Math.random()*1000)},
                            {name: '新疆',value: Math.round(Math.random()*1000)},
                            {name: '浙江',value: Math.round(Math.random()*1000)},
                            {name: '江西',value: Math.round(Math.random()*1000)},
                            {name: '山西',value: Math.round(Math.random()*1000)},
                            {name: '内蒙古',value: Math.round(Math.random()*1000)},
                            {name: '吉林',value: Math.round(Math.random()*1000)},
                            {name: '福建',value: Math.round(Math.random()*1000)},
                            {name: '广东',value: Math.round(Math.random()*1000)},
                            {name: '西藏',value: Math.round(Math.random()*1000)},
                            {name: '四川',value: Math.round(Math.random()*1000)},
                            {name: '宁夏',value: Math.round(Math.random()*1000)},
                            {name: '香港',value: Math.round(Math.random()*1000)},
                            {name: '澳门',value: Math.round(Math.random()*1000)}*/
                        ]
                    }
                ]
            };



            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            myChart2.setOption(option2);
            myChart3.setOption(option3);
            var day = [];
            var count = [];
            $.post(
                "${pageContext.request.contextPath}/user/getSevenData",
                function (data) {
                    for(var i = 0;i<data.length;i++) {
                        day.push(data[i].name)
                        count.push(data[i].value)
                    }
                    myChart.setOption({
                        xAxis: {
                            data: day
                        },
                        series: [{
                            data:count
                        }]
                    });
                },
                "json"
            );
            goEasy.subscribe({
                channel: "day", //替换为您自己的channel
                onMessage: function (message) {
                    console.log(message.content);;
                    //var data = JSON.parse(message.content);
                    for(var i = 0;i<data.length;i++) {
                        day.push(data[i].name)
                        count.push(data[i].value)
                    }
                    console.log(data);
                    myChart.setOption({
                        xAxis: {
                            data: day
                        },
                        series: [{
                            data:count
                        }]
                    });
                }
            });

            var month = [];
            var count2 = [];
            $.post(
                "${pageContext.request.contextPath}/user/getYearData",
                function (data) {

                    for(var i = 0;i<data.length;i++){
                        month.push(data[i].name);
                        count2.push(data[i].value)
                    }
                    myChart2.setOption({
                        xAxis: {
                            data: month
                        },
                        series: [{
                            data:count2
                        }]
                    });
                },
                "json"
            );
            goEasy.subscribe({
                channel: "month", //替换为您自己的channel
                onMessage: function (message) {
                    /*var parse = JSON.parse(message.content);*/
                    myChart2.setOption({
                        series: [{
                            data:parse
                        }]
                    });
                }
            });


            $.post(
                "${pageContext.request.contextPath}/user/getAddrData",
                function (data) {
                    myChart3.setOption({
                        series: [{
                            data:data
                        }]
                    });
                },
                "json"
            );
        })
    </script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div>
    <div id="main" style="width: 500px;height:300px;float: left"></div>
    <div id="second" style="width: 500px;height:300px;float: left"></div>
    <div id="third" style="width: 500px;height:300px;float: left"></div>
</div>
</body>
</html>