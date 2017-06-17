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
<!-- 信息弹框 -->
<link type="text/css" rel="stylesheet" href="css/popModal.css">
<script src="js/jquery.js"></script>
<script src="js/popModal.js"></script>


<script>
  var linkURL="10.41.56.60";
    function sendMessage(){
      var title=$("#title").val();
      var party=document.getElementById("party");
      var tag=document.getElementById("tag");
      var message=$("#msg").val();
      var url=$("#url").val();
      var msg=0;
      if((title==""||title==undefined) && (message==""||message==undefined) && (url==""||url==undefined) ){
        msg="未发送任何信息";
      }
      if(party.length>0){
         var partise=party.options[party.selectedIndex].value;
      }
      if(tag.length>0){
         var tags=tag.options[tag.selectedIndex].value;
      }

      if( partise <=0 && tags <=0){
        msg="未选择任何发送部门或群组";
      }
      
      if(title==""||title==undefined)
      {
        title=' ';
      }
      if(msg==undefined||msg==0){
        var data={
                toparty :partise,
                totag:tags,
                message:{
                  title:title,
                  description:message,
                  picUrl:'',
                  url:url
          }
        }
        msg = $.ajax({  
            type: "post",  
            url:'http://'+linkURL+'/weixinenterprise/rest/News/SendNews',
            dataType : 'json',
            contentType : 'application/json',
            data : JSON.stringify(data),
            async:false  
                }).responseText; 
      
            //document.getElementById("error").innerHTML=msg;
      }
        if(msg==undefined){
            msg="服务器连接失败";
        }
        $("#error").html(decodeURI(msg)); 
        $('#btnSend').popModal({
            html : $('#error'),
            placement : 'bottomLeft',
            showCloseBut : true,
            onDocumentClickClose : true,
            onOkBut : function(){},
            onCancelBut : function(){},
            onLoad : function(){},
            onClose : function(){}
          });
      }
   //新版本的 JSON 修改了 API，将 JSON.stringify() 和 JSON.parse() 两个要领都注入到了 Javascript 的内建对象里面，
   //前者变成了 Object.toJSONString()，而后者变成了 String.parseJSON()。如果提示找不到toJSONString()和parseJSON()要领，则说明您的json包版本太低
    function getPartyTag(){
        /*
                date:2017/6/13
                description:获取部门
        */
        show("div1");
        var party=document.getElementById("party");
        party.options[0]= new Option('--请选择发送部门--',0)
        var partise = $.ajax({  
            type: "post",  
            url:'http://'+linkURL+'/weixinenterprise/rest/Manager/GetPartiesList',//10.38.123.11
            dataType : 'json',
            contentType : 'application/json',
            async:false  
                }).responseText;
        var obj =JSON.parse(partise)  ;
        //$("#error").html(decodeURI(partise)); 
        for(var i=0;i<obj.length;i++){
          //alert(obj[i].name+":"+obj[i].id);
              party.options[i+1]= new Option(obj[i].depName,obj[i].depId)
          }
          //alert(party.options[0].text+":"+party.options[0].value);
        party.options[0].selected = true;

        /*
                date:2017/6/13
                description:获取标签
        */
        var tag=document.getElementById("tag");
        tag.options[0]= new Option('--请选择发送群组--',0)
        var tags = $.ajax({  
            type: "post",  
            url:'http://'+linkURL+'/weixinenterprise/rest/Manager/GetTagsList',
            dataType : 'json',
            contentType : 'application/json',
            async:false  
                }).responseText;
        var obj =JSON.parse(tags)  ;
        for(var i=0;i<obj.length;i++){
              tag.options[i+1]= new Option(obj[i].tagName,obj[i].tagId)
          }
        tag.options[0].selected = true;
     /*另一种呼叫方式 
      清空上次的选项
        party.options.length=0;
        $.ajax({
                // url:'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=appid&secret=secret', 10.38.123.11
                url:'http://10.41.56.60/weixinenterprise/rest/Manager/GetPartiesList',
                  type:"get",
                  async: false,
                  success: function (result) {
                       alert(1);
                    $("#error").html(decodeURI(result));
                      
                  },
                  error: function (result) {
                
                      for (var r in result){
                        $("#error").html(decodeURI(r));
                      }
                   
                      //  $("#error").html(decodeURI(mes));
                  }
              });    */               
     }

     

</script>


<script type="text/javascript" >
    function uploadFile(){
      alert(1);
      var ajax_option={
          url: 'http://'+linkURL+'/weixinenterprise/',                  //String, 表单提交的目标地址，此属性会覆盖表单的action属性
          type:"POST",             //String，表单提交的方式(POST or GET)，此属性会覆盖表单的method属性
          dataType: 'json',    //String，指定接受服务端返回的数据类型(xml，script  or  json)
          clearFomr: false,   //boolean，默认为false，成功提交后是否清除所有表单元素的值
          resetFomr: false,  //boolean，默认为false，成功提交后是否重置所有表单元素的值
          timeout: 3000,    //number，单位ms，限制请求的时间，当请求大于设置的时间后，跳出请求
          success:function(responseText,statusText,xhr,$form){
              alert("success");
          },//提交成功后的回调函数 。参数详解：responseText，服务器返回的数据内容；statusText，服务器返回的状态
          beforSubmit: function(formData, jqForm, options){
              alert("beforSubmit");
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
<body onload='getPartyTag()'>
   
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
              <div class="navbar-brand"> 企业微信 </div>
          
            <!--  <a class="navbar-brand" href="https://qy.weixin.qq.com/" id="vz">登录企业微信</a>
              <a class="navbar-brand" href="https://work.weixin.qq.com/wework_admin/commdownload?from=mngindex" id="reg">下载客户端</a>
               <a class="navbar-brand" href="https://work.weixin.qq.com/join_form/8Z6j5aeiRghKFU8ajQIwsQ/member_join_web" id="reg">点击链接加入我的企业“江”，一起开启全新办公体验吧</a> -->
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
          </div><!-- /.container-fluid -->
        </nav>
       <!--class="row-fluid"-->
        <div >
            <div class="col-sm-7" id="pageImage">            
                <img id="image" class="img-responsive" src="image/page.jpg">
            </div>
            <div class="col-sm-4" id="cen_right_top"> 
                <div class="form-group">
                  <h3 onmouseover="changeTab(this)" class="form-control" id="h31">发送讯息</h3>
                  <h3 onmouseover="changeTab(this)" class="form-control" id="h32">加入企业微信</h3>
                </div>
              <div style="display:block" id="div1">
                   <form id="sendMessage" method="post" action="" onsubmit="">
                      <span id="send" >
                        <div class="form-group">
                          <label for="title"></label>
                          <select id='party' class="form-control">
                          </select>
                        </div>
                        <div class="form-group">
                          <select id='tag' class="form-control">
                          </select>
                        </div>
                        <div class="form-group">
                          <input type="text" name="title" class="form-control" id="title" value="" placeholder="请输入发送主题">
                        </div>
                        <div class="form-group">
                          <input type="text" name="url" class="form-control" id="url" value="" placeholder="请输入链接地址">
                        </div>
                        <div class="form-group">
                          <textarea  name="msg"  class="form-control" id="msg" cols=40 rows=4 placeholder="请输入发送信息"></textarea> 
                        </div>
                        <!-- 显示信息 -->
                        <label id="err"></label>
                        <div class="form-group">
                            <input id="btnSend" type="button" value="发送" class="btn btn-info " onclick="sendMessage();" />
                            
                        </div>
                  
                      </span>
                                   
                 </form>  

              </div>
              <div id="div2">
                <form >
                    <div id="scan" class="form-group">扫描二维码</div>  
                    <img id="picture" class="img-responsive" src="image/QRCode.png" >   
                    <div id="add" class="form-group">加入企业微信</div>  
                </form>        
              </div>
            </div>
            <div style="display:none;width:auto;height:auto" >
                <div id="error" style="width:auto;height:auto" ></div>
            </div>  

          <!--      <form id="imageForm" action="" method="post" enctype="multipart/form-data" name="upload_form" onsubmit="uploadFile()">
                  <label>选择图片文件</label>
                  <input name="imgfile" type="file" accept="" />
                  <input name="upload" type="submit" value="上传" />

              </form> -->
        </div>

  	</div>
   
    <!--tab 特效-->
    <style type="text/css">
    *{margin:0;padding:0;list-style-type:none;}
    a,img{border:0;}

    #cen_right_top .active{background:url(images/qiehuan.jpg) no-repeat;color:#000000 ;}
    #cen_right_top h3{line-height:35px;text-align:center;float:left;height:35px;width:50%;margin:0px;padding:0px;background-color:   #DCDCDC   ;font-size:14px;color:#333333;font-weight:lighter;cursor:pointer;}
    #cen_right_top form{font-size:14px;display:none;clear:both;height:70%;padding:20px 0px 0px 20px;border-top-width:medium;border-top-style:solid;border-top-color:#A0603D;}
    </style>
    <script >
      
      //#cen_right_top .active{background:url(images/qiehuan.jpg) no-repeat;color:#FFDEAD ;}
        function hid(id){
          var hidArea = document.getElementById(id).getElementsByTagName("*");
                for(var i = 0;i<hidArea.length;i++){
                    hidArea[i].style.display="none";
                }
          }
          
        function show(id){
          var tag1=document.getElementById("h31");
          var tag2=document.getElementById("h32");
          if(id=="div1"){
              tag1.className="";
              tag2.className="active";
          }else if(id=="div2"){
              tag1.className="active";
              tag2.className="";
          }
          var showArea = document.getElementById(id).getElementsByTagName("*");
          for(var i = 0;i<showArea.length;i++){
              showArea[i].style.display="block";
          }
        }

        function changeTab(ob){
          var id=$(ob).attr("id");  
          if(id=="h31"){
              show("div1");
              hid("div2");
          }else{
              show("div2");
              hid("div1");
          }      
     }
    </script>
</body>
</html>