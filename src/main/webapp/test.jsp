<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<title>纬创企业微信</title>

<link href="bootstrap/bootstrap-3.2.0-dist/css/bootstrap.css" rel="stylesheet">
<script src="js/jquery-1.4.4.min.js" type="text/javascript"></script> 
<script src="bootstrap/bootstrap-3.2.0-dist/jquery-1.11.1.min.js"></script>
<script src="bootstrap/bootstrap-3.2.0-dist/js/holder.js"></script>
<script type="text/javascript" src="bootstrap/bootstrap-3.2.0-dist/js/bootstrap.js"></script>

<!-- 文字特效 -->
<link href="css/jquery-letterfx.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.min.js"></script>
<script src="js/jquery-letterfx.js"></script>
<script src="js/demo.js"></script>

<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script >
    
    function sendMessage(){
        var data={
                title:$("#title").val(),
                description:$("#uxmsg").val(),
                picUrl:'',
                url:''
        }
        alert(JSON.stringify(data));
        var msg = $.ajax({  
            type: "post",  
            url:'http://10.41.56.60/weixinenterprise/rest/News/SendNews',
            dataType : 'json',
            contentType : 'application/json',
            data : JSON.stringify(data),
            async:false  
                }).responseText; 
            $("#message").html(decodeURI(msg)); 
    }

</script>
</head>
<body>

<%! 
  private int initVar=0;
  private int serviceVar=0;
  private int destroyVar=0;
%>
<p>
   time: <%= (new java.util.Date()).toLocaleString()%>
</p>

<div class="container-fluid">
    		<nav class="navbar navbar-default" role="navigation">
          <div class="container-fluid">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="https://qy.weixin.qq.com/" id="vz">登录企业微信</a>
              <a class="navbar-brand" href="https://work.weixin.qq.com/wework_admin/commdownload?from=mngindex" id="reg">下载客户端</a>
               <a class="navbar-brand" href="https://work.weixin.qq.com/join_form/8Z6j5aeiRghKFU8ajQIwsQ/member_join_web" id="reg">点击链接加入我的企业“江”，一起开启全新办公体验吧</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
          </div><!-- /.container-fluid -->
        </nav>
       
        <div class="row-fluid">
            <div class="col-sm-4" id="log">
            	<img id="picture" class="img-responsive" src=""></div>
                
            <div class="col-sm-4">
            
              <form id="sendMessage" method="post" action="" onsubmit="sendMessage();">
                <div class="form-group">
                  <input type="text" name="username" class="form-control" id="title" value="" placeholder="请输入发送主题">
                </div>
                <div class="form-group">
                   <textarea id="uxmsg" name="MSG"  class="form-control" cols=40 rows=4 placeholder="请输入发送信息"></textarea>
                   <div class="form-group">
                      <input type="submit" value="send" class="btn btn-info"  />
                   </div>
                   <form action="" method="post" enctype="multipart/form-data" name="upload_form">
                     <label>选择图片文件</label>
                     <input name="imgfile" type="file" accept="image/gif, image/jpeg"/>
                     <input name="upload" type="submit" value="上传" />
                   </form>
                </div>
                <!-- 显示信息 -->
              	<div id="message"></div>
       
              </form>
              <div id="place"></div>
        	</div>

            <div class="col-sm-3" id="log"></div>
        </div>
  	</div>
</body>
</html>