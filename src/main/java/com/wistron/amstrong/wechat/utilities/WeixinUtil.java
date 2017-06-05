package com.wistron.amstrong.wechat.utilities;

import java.io.*;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import net.spy.memcached.MemcachedClient;

import org.json.JSONException;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import com.wistron.amstrong.wechat.dao.AccessTokenDao;
import com.wistron.amstrong.wechat.entities.AccessToken;
import com.wistron.amstrong.wechat.utilities.MyX509TrustManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



public class WeixinUtil {

    private final static Logger log = LogManager.getLogger(WeixinUtil.class);
    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=SECRETID";

    // 菜单创建（POST） 限100（次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

   
	
	/**
	 * 解析request中的xml 并将数据存储到一个Map中返回
	 * @param request
	 */
	public static Map<String, String> parseXml(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		try {
			InputStream inputStream = request.getInputStream();
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			for (Element e : elementList)
				//遍历xml将数据写入map
				map.put(e.getName(), e.getText());
			inputStream.close();
			inputStream = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	
	/**
	 * sha1加密算法
	 * @param key 需要加密的字符串
	 * @return 加密后的结果
	 */
	public static String sha1(String key) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(key.getBytes());
			String pwd = new BigInteger(1, md.digest()).toString(16);
			return pwd;
		} catch (Exception e) {
			e.printStackTrace();
			return key;
		}
	}

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     *  WeixinUtil.PostMessage(access_token, "POST", POST_URL, PostData);  
     */
    public static JSONObject PostMessage(String token, String requestMethod, String requestUrl, String postData) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            
            requestUrl = requestUrl.replace("ACCESS_TOKEN", token);
            
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != postData) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = new JSONObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
       // return jsonObject.getInt("errcode");
        return jsonObject;
 
    }
	
	

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            //jsonObject = JSONObject.fromObject(buffer.toString());
            jsonObject = new JSONObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }
        return jsonObject;
    }


    /**
     * 获取access_token
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     *//**
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

        Object obj = null;

        try {
        	//獲取本雞快取，當Token超過7200秒就失效。
        	MemcachedClient  mc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 80));
            obj = mc.get(appid);
            if (obj == null){
                //已过期 重新请求
                String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
                JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
                // 如果请求成功
                if (null != jsonObject) {
                    try {
                        accessToken = new AccessToken();
                        accessToken.setToken(jsonObject.getString("access_token"));
                        accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
                        mc.set(appid,7200,accessToken);
                    } catch (JSONException e) {
                        accessToken = null;
                        // 获取token失败
                        log.error("获取token失败 errcode:{"+jsonObject.getInt("errcode")+"} errmsg:{"+jsonObject.getString("errmsg")+"}");
                    }
                }
            }else {
                System.out.println("拉取AccessToken为---->缓存 " );
                accessToken = (AccessToken)obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }	
    */
    
    public static AccessToken getAccessToken(String corpid, String secretid) throws JSONException, SQLException, Exception {
    	AccessToken accessToken = null;
        AccessTokenDao accessTokenDao = new AccessTokenDao();
        Connection con = new ConnectDBUtil().createConnection();
        try {
        	//獲取本雞快取，取的到期前的ToKen。
        	accessToken = accessTokenDao.getToken(con, corpid, secretid);
            if (accessToken == null){
                //已过期 重新请求
                String requestUrl = access_token_url.replace("CORPID", corpid).replace("SECRETID", secretid);
                JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
                
                if (null != jsonObject)
                {
                	if(jsonObject.getInt("errcode")==0)
                	{
                		//取得新的貧症候，寫回到DB
                        accessToken = new AccessToken(jsonObject.getString("access_token"),jsonObject.getInt("expires_in"));
                        con.setAutoCommit(false);
                        accessTokenDao.updateToken(con, corpid, secretid, accessToken.getToken(), accessToken.getExpiresIn());
                        con.commit();
                	}
                	else if(jsonObject.getInt("errcode")== -1 || jsonObject.getInt("errcode")== 42001)
                	{
                		getAccessToken(corpid,secretid);
                	}
                	else
                	{
                		throw new Exception("Get Token From Weixin fail! ");
                	}
                }
            }
        }finally
        {
        	con.close();
        }

        return accessToken;
    }
}
