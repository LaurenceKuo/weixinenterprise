package com.wistron.armstrong.wechat.controller;

import com.wistron.armstrong.wechat.factory.ObjectFactory;
import com.wistron.armstrong.wechat.service.IDeptService;
import org.json.JSONException;
import org.apache.log4j.Logger;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/department")
public class DeptController {
	 
	final static Logger logger = Logger.getLogger(DeptController.class);
	 @POST
	 @Path("/get")
	 @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	 public Response getDeptList() throws Exception, JSONException
	 { 
		 System.out.println("开始获取部门");
		 IDeptService service=(IDeptService)ObjectFactory.newInstance("DeptServiceImpl");
		 return service.getDeptList();
	 }
	 
	 public static void main(String[] args){
		 DeptController controller=new DeptController();
		 try {
			 controller.getDeptList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
