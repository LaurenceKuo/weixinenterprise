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

<link href="css/test.css" rel="stylesheet">
<!-- 文字特效 -->
<link href="css/jquery-letterfx.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.min.js"></script>
<script src="js/jquery-letterfx.js"></script>
<script src="js/demo.js"></script>

<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script >
    
    function sendMessage(){
      var title=$("#title").val();
      
      if(title==""||title==undefined)
      {
        title=' ';
      }
      var data={
              toparty :$("#uxParty").val(),
              message:{
                title:title,
                description:$("#uxmsg").val(),
                picUrl:'',
                url:''
        }
      }
      var msg = $.ajax({  
          type: "post",  
          url:'http://10.41.56.60/weixinenterprise/rest/News/SendNews',
          dataType : 'json',
          contentType : 'application/json',
          data : JSON.stringify(data),
          async:false  
              }).responseText; 
          $("#error").html(decodeURI(msg)); 
    }

    function getParty(){
      /* var party = $.ajax({  
          type: "get",  
          url:'http://10.41.56.60/weixinenterprise/rest/News/SendNews',
          dataType : 'json',
          contentType : 'application/json',
          async:false  
              }).responseText; 
          $("#error").html(decodeURI(msg)); */
          var iparty= new HashMap();
          iparty.put('0','--请选择发送部门--');
          iparty.put('1','部门1');
          iparty.put('2','部门2');
          alert("size："+iparty.size()+" key1："+iparty.get("0")); 
          var party=document.getElementById("uxParty");
            for(var i=0;i<iparty.length;i++){
                party.options[i]= new Option(iparty[i],i)
                //$("#uxParty").options.add(new Option(i, iparty[i])); 
            }

              //选中省份之后，根据索引动态载入相应城市
            //清空上次的选项
       // city.options.length=0;
        //获取省一级的下拉列表选中的索引
     }
      

</script>

<script type="text/javascript" >
    function uploadFile(){
      alert(1);
      var ajax_option={
          url: 'http://10.41.56.60/weixinenterprise/',                  //String, 表单提交的目标地址，此属性会覆盖表单的action属性
          type:"POST",             //String，表单提交的方式(POST or GET)，此属性会覆盖表单的method属性
          dataType: 'json',    //String，指定接受服务端返回的数据类型(xml，script  or  json)
          clearFomr: false,   //boolean，默认为false，成功提交后是否清除所有表单元素的值
          resetFomr: false,  //boolean，默认为false，成功提交后是否重置所有表单元素的值
          timeout: 3000,    //number，单位ms，限制请求的时间，当请求大于设置的时间后，跳出请求
          success:function(responseText,statusText,xhr,$form){
              alert("success");
              //业务提示
          },//提交成功后的回调函数 。参数详解：responseText，服务器返回的数据内容；statusText，服务器返回的状态
          beforSubmit: function(formData, jqForm, options){
              alert("beforSubmit");
              //业务提示
          },//提交之前的回调函数。参数详解：formData，数组对象，为表单的内容；jqForm，jQuery对象，封装了表单的元素；options，之前设置的ajaxSubmit的option对象。
      };
    
      //表单提交事件
      $('#imageForm').submit(function(){
          $("#imageForm").ajaxSubmit(ajax_option);
          return false;
      })
    }
 </script>
 
</head>
<body onload="getParty()">

<%! 
  private int initVar=0;
  private int serviceVar=0;
  private int destroyVar=0;
%>
<p>
   <!-- time: <%= (new java.util.Date()).toLocaleString()%> -->
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
            <!--  <a class="navbar-brand" href="https://qy.weixin.qq.com/" id="vz">登录企业微信</a>
              <a class="navbar-brand" href="https://work.weixin.qq.com/wework_admin/commdownload?from=mngindex" id="reg">下载客户端</a>
               <a class="navbar-brand" href="https://work.weixin.qq.com/join_form/8Z6j5aeiRghKFU8ajQIwsQ/member_join_web" id="reg">点击链接加入我的企业“江”，一起开启全新办公体验吧</a> -->
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
          </div><!-- /.container-fluid -->
        </nav>
       
        <div class="row-fluid">
            <div class="col-sm-4"></div>            
                
            <div class="col-sm-3">
            
              <form id="sendMessage" method="post" action="" onsubmit="sendMessage();">
                <div class="form-group">
                  <select id='uxParty' class="form-control">
                    <option value=''>--请选择部门--</option>
                  </select>
                </div>
                <div class="form-group">
                  <input type="text" name="username" class="form-control" id="title" value="" placeholder="请输入发送主题">
                </div>
                <div class="form-group">
                   <textarea id="uxmsg" name="MSG"  class="form-control" cols=40 rows=4 placeholder="请输入发送信息"></textarea>
                   
                </div>
                <!-- 显示信息 -->
              	<label id="error"></label>
                <div class="form-group">
                  <input type="button" value="send" class="btn btn-info "  />
                </div>
              
              </form>
              <div>扫描二维码加入企业微信</div>  
              <img id="picture" class="img-responsive" src="image/QRCode.png"></div>
          <!--      <form id="imageForm" action="" method="post" enctype="multipart/form-data" name="upload_form" onsubmit="uploadFile()">
                  <label>选择图片文件</label>
                  <input name="imgfile" type="file" accept="" />
                  <input name="upload" type="submit" value="上传" />

              </form> -->
              
              <div id="place"></div>
        	</div>
           
        </div>
  	</div>
</body>
</html>