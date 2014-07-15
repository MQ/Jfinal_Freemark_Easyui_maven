package zj.plugin.kindedtor.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.jfinal.core.Controller;

public class Upload extends Controller{
	@SuppressWarnings({ "rawtypes", "unused" })
	public void index() {
		ObjectMapper objectMapper = new ObjectMapper(null, null, null);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		String configPath = getRequest().getSession().getServletContext().getRealPath("/WEB-INF/classes/config.properties");
		Properties properties = new Properties();
		File configFile = new File(configPath);
		FileInputStream is =null;
		try {
			is = new FileInputStream(configFile);
			properties.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * KindEditor JSP
		 * 
		 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
		 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
		 * 
		 */
		String imageRootPath = properties.getProperty("uploadPath");
		String imageRootURL = properties.getProperty("uploadURL");
		//图片的最大宽度
		Integer limitWidth = Integer.valueOf(properties.getProperty("imgLimitWidth"));
		//图片的最大高度
		Integer limitHeight = Integer.valueOf(properties.getProperty("imgLimitHeight"));
		//文件保存目录路径
		//String savePath = pageContext.getServletContext().getRealPath("/") + "attached/";
		String savePath = imageRootPath;

		//文件保存目录URL
		//String saveUrl  = request.getContextPath() + "/attached/";
		String saveUrl = imageRootURL;

		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		//最大文件大小
		long maxSize = 1000000;

		getResponse().setContentType("text/html; charset=UTF-8");

		if (!ServletFileUpload.isMultipartContent(getRequest())) {
			renderText(getError("请选择文件。"));
			return;
		}
		//检查目录
		File uploadDir = new File(savePath);
		if (!uploadDir.isDirectory()) {
			/* renderText(getError("上传目录不存在。"));
			return; */
			uploadDir.mkdirs();
		}
		//检查目录写权限
		if (!uploadDir.canWrite()) {
			renderText(getError("上传目录没有写权限。"));
			return;
		}

		String dirName = getRequest().getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			renderText(getError("目录名不正确。"));
			return;
		}
		//创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = null;
		try {
			items = upload.parseRequest(getRequest());
		} catch (FileUploadException e1) {
			e1.printStackTrace();
		}
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			long fileSize = item.getSize();
			if (!item.isFormField()) {
				//检查文件大小
				if (item.getSize() > maxSize) {
					renderText(getError("上传文件大小超过限制。"));
					return;
				}
				//检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
					renderText(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
					return;
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				try {
					File uploadedFile = new File(savePath, newFileName);
					item.write(uploadedFile);
				} catch (Exception e) {
					renderText(getError("上传文件失败。"));
					return;
				}

				HashMap<String, Object> obj = new HashMap<String, Object>();
				;
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				try {
					renderText(objectMapper.writeValueAsString(obj));
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private String getError(String message) {
		ObjectMapper objectMapper = new ObjectMapper(null, null, null);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		HashMap<String, Object> obj = new HashMap<String, Object>();
		obj.put("error", 1);
		obj.put("message", message);
		try {
			String error = objectMapper.writeValueAsString(obj);
			return error;
		} catch (Exception e) {
			return "";
		}

	}
	
	
}
