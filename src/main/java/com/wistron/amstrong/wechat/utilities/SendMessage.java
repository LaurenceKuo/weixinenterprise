package com.wistron.amstrong.wechat.utilities;

public class SendMessage {

	//发送接口  
    public static String POST_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";  
    /** 
     * text消息 
     * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3" 
     * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 " 
     * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 " 
     * @param msgtype 消息类型，此时固定为：text 
     * @param agentid 企业应用的id，整型。可在应用的设置页面查看 
     * @param content 消息内容 
     * @param safe 表示是否是保密消息，0表示否，1表示是，默认0 
     * */  
    public static String STextMsg(String touser,String toparty,String totag,String agentid,String content){  
        String PostData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\":\"%s\",\"msgtype\": \"text\",\"agentid\": \"%s\",\"text\": {\"content\": \"%s\"},\"safe\":\"0\"}";  
        return String.format(PostData, touser,toparty,totag,agentid,content);  
    }  
      
    /** 
     * image消息 
     * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3" 
     * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 " 
     * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 " 
     * @param msgtype 消息类型，此时固定为：image 
     * @param agentid 企业应用的id，整型。可在应用的设置页面查看 
     * @param media_id 媒体资源文件ID 
     * @param safe 表示是否是保密消息，0表示否，1表示是，默认0 
     * */  
    public static String SImageMsg(String touser,String toparty,String agentid ,String media_id){  
        String PostData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"msgtype\": \"image\",\"agentid\": \"%s\",\"image\": {\"media_id\": \"%s\"},\"safe\":\"0\"}";  
        return String.format(PostData, touser,toparty,agentid,media_id);  
    }  
      
    /** 
     * voice消息 
     * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3" 
     * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 " 
     * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 " 
     * @param msgtype 消息类型，此时固定为：voice 
     * @param agentid 企业应用的id，整型。可在应用的设置页面查看 
     * @param media_id 媒体资源文件ID 
     * @param safe 表示是否是保密消息，0表示否，1表示是，默认0 
     * */  
    public static String SVoiceMsg(String touser,String toparty,String totag,String agentid ,String media_id){  
        String PostData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\",\"msgtype\": \"voice\",\"agentid\": \"%s\",\"voice\": {\"media_id\": \"%s\"},\"safe\":\"0\"}";  
        return String.format(PostData, touser,toparty,totag,agentid,media_id);  
    }  
      
    /** 
     * video消息 
     * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3" 
     * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 " 
     * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 " 
     * @param msgtype 消息类型，此时固定为：video 
     * @param agentid 企业应用的id，整型。可在应用的设置页面查看 
     * @param media_id 媒体资源文件ID 
     * @param title 视频消息的标题 
     * @param description 视频消息的描述 
     * @param safe 表示是否是保密消息，0表示否，1表示是，默认0 
     */  
    public static String SVideoMsg(String touser,String toparty,String totag,String agentid,String media_id,String title,String description){  
        String PostData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\",\"msgtype\": \"video\",\"agentid\": \"%s\",\" video\": {\"media_id\": \"%s\",\"title\": \"%s\",\"description\": \"%s\"},\"safe\":\"0\"}";  
        return String.format(PostData, touser,toparty,totag,agentid,media_id,title,description);  
    }  
      
    /** 
     * file消息 
     * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3" 
     * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 " 
     * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 " 
     * @param msgtype 消息类型，此时固定为：file 
     * @param agentid 企业应用的id，整型。可在应用的设置页面查看 
     * @param media_id 媒体资源文件ID 
     * @param safe 表示是否是保密消息，0表示否，1表示是，默认0 
     * */  
    public static String SFileMsg(String touser,String toparty,String totag,String agentid ,String media_id){  
        String PostData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\",\"msgtype\": \"file\",\"agentid\": \"%s\",\"file\": {\"media_id\": \"%s\"},\"safe\":\"0\"}";  
        return String.format(PostData, touser,toparty,totag,agentid,media_id);  
    }  
      
    /** 
     * news消息 
     * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3" 
     * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 " 
     * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 " 
     * @param msgtype 消息类型，此时固定为：news 
     * @param agentid 企业应用的id，整型。可在应用的设置页面查看 
     * @param articlesList 图文集合 
     */  
    public static String SNewsMsg(String touser,String toparty,String totag,String agentid , String articlesList){  
        String postData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\",\"msgtype\": \"news\",\"agentid\": \"%s\",\"news\": {\"articles\":%s}}";  
        return String.format(postData, touser,toparty,totag,agentid,articlesList);  
    }  
      
    /** 
     * mpnews消息 
     * @param touser UserID列表（消息接收者，多个接收者用‘|’分隔）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送————"touser": "UserID1|UserID2|UserID3" 
     * @param toparty PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"toparty": " PartyID1 | PartyID2 " 
     * @param totag TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数————"totag": " TagID1 | TagID2 " 
     * @param msgtype 消息类型，此时固定为：mpnews 
     * @param agentid 企业应用的id，整型。可在应用的设置页面查看 
     * @param articlesList mpnews集合 
     */  
    public static String SMpNewsMsg(String touser,String toparty,String totag,String agentid , String articlesList){  
        String postData = "{\"touser\": \"%s\",\"toparty\": \"%s\",\"totag\": \"%s\",\"msgtype\": \"mpnews\",\"agentid\": \"%s\",\"mpnews\": {\"articles\":%s}\"safe\":\"0\"}";  
        return String.format(postData, touser,toparty,totag,agentid,articlesList);  
    }  
    // public  String corpId = "wwc6d5528ef1cce0fa";
    // public  String secret = "pjRN8ZCO2y-fzjjNe6FzdFU0tkCJv8HgOsFZXcywC_A";
    
    //示例  
    /** 
     * text示例 
     * */
    /*
   public static void main(String[] args) throws Exception {  
  
	   JSONObject rulJsonObject = null;
	   System.out.println("Step1" ); 
       // 调取凭证  
       String access_token = WeixinUtil.getAccessToken(corpId, secret).getToken();  
       //String access_token = "sD55fphxyqgZyl3-1Jlwp4MzrUlliOLDGs7l2ecUx1h11KxlW7_4lBVfYqyKuVS-Ow8Es5FGpojJIovXLytC2noLVB5tQl9E_kNoHGEWVhhi5lOkvDn1oOSh7FIv7eiCif8hAsCWpZOSa-viiRcTnQ_1fZBkIPECSmf-MSLDWNyi5TE_R6OUGO2XO3KNS5ThusspAbTwd_1wN-nygdcYJ32TjaATsk1SaN9fLE6V-0wc4cAbh_CvKeDmmpe0ElPtll-k716naWvoEiKRo0Fd05MzYApk9a2AcTm08ehFOqU";
       // 整合图文  
       System.out.println("access_token: " + access_token ); ;  
 
       // Post的数据  
       String PostData = STextMsg("@ALL", "1", "@ALL", "1000002", "Send Text \\n 發送文字 \\n 发送文字 ");  
       rulJsonObject = WeixinUtil.PostMessage(access_token, "POST", POST_URL, PostData);  
       // 打印结果  
        if(0==rulJsonObject.getInt("errcode")){  
            System.out.println("操作成功");  
        }  
        else {  
            System.out.println("操作失败");  
        }  
}  
	*/
}
