package com.wistron.armstrong.wechat.controller;

import com.wistron.armstrong.wechat.factory.ObjectFactory;
import com.wistron.armstrong.wechat.service.ITagService;
import org.json.JSONException;
import org.apache.log4j.Logger;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tag")
public class TagController {
	 
	final static Logger logger = Logger.getLogger(DeptController.class);
	 @POST
	 @Path("/get")
	 @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	 public Response getTagList() throws Exception, JSONException
	 { 
		 ITagService service=(ITagService)ObjectFactory.newInstance("TagServiceImpl");
		 return service.getTagList();
	 }
	 
	 public static void main(String[] args){
		 TagController controller=new TagController();
		 try {
			 controller.getTagList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
