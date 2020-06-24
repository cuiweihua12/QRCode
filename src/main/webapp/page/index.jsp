<%--
  Created by IntelliJ IDEA.
  User: CWH
  Date: 2020/6/23
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <input type="text" value="${uuid}" id="uuid"/>
    <div class="main">
        <div class="title">
            <img id="qrcode" alt="" src="" />
        </div>
        <div id="result" class="title"></div>
    </div>
<script type="text/javascript" src="<%=basePath %>static/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/public.js"></script>
<script type="text/javascript">
    let val = $('#uuid').val();
    $(function () {

        //文档就绪
        $('#qrcode').attr('src',cwh.getProjectUrl()+'/uuid/qrcode/${uuid}');
        $('#result').html('使用手机扫描二维码');
        keepPool();
    });
    //js请求服务器查看二维码是否被扫
    function keepPool() {
        cwh.postOrPut("/QRCode/uuid/pool","POST",{uuid:val},function (data) {
            if (data == 'success'){
                $('#result').html("登录成功");
            }else if(data == 'timeout'){
                $('#result').html("登录超时,请刷新重试");
            }else{
                keepPool();
            }
        })
    }
</script>
</body>
</html>
