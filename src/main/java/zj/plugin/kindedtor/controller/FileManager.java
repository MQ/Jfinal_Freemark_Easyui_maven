package zj.plugin.kindedtor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import zj.plugin.kindedtor.util.NameComparator;
import zj.plugin.kindedtor.util.SizeComparator;
import zj.plugin.kindedtor.util.TypeComparator;

import com.jfinal.core.Controller;

public class FileManager extends Controller{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void index() {
		HttpServletRequest request=getRequest();
		HttpServletResponse response=getResponse();
		ObjectMapper objectMapper = new ObjectMapper(null, null, null);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		String configPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/config.properties");
		Properties properties = new Properties();
		File configFile = new File(configPath);
		try {
			FileInputStream is = new FileInputStream(configFile);
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
		//根目录路径，可以指定绝对路径，比如 /var/www/attached/
		//String rootPath = pageContext.getServletContext().getRealPath("/")+ "attached/";
		String rootPath = imageRootPath;
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		//String rootUrl = request.getContextPath() + "/attached/";
		String rootUrl = imageRootURL;
		//图片扩展名
		String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };

		String dirName = request.getParameter("dir");
		if (dirName != null) {
			if (!Arrays.<String> asList(new String[] { "image", "flash", "media", "file" }).contains(dirName)) {
				renderText("Invalid Directory name.");
				return;
			}
			rootPath += dirName + "/";
			rootUrl += dirName + "/";
			File saveDirFile = new File(rootPath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
		}
		//根据path参数，设置各路径和URL
		String path = request.getParameter("path") != null ? request.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}

		//排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";

		//不允许使用..移动到上一级目录
		if (path.indexOf("..") >= 0) {
			renderText("Access is not allowed.");
			return;
		}
		//最后一个字符不是/
		if (!"".equals(path) && !path.endsWith("/")) {
			renderText("Parameter is not valid.");
			return;
		}
		//目录不存在或不是目录
		File currentPathFile = new File(currentPath);
		if (!currentPathFile.isDirectory()) {
			renderText("Directory does not exist.");
			return;
		}

		//遍历目录取的文件信息
		List<Hashtable> fileList = new ArrayList<Hashtable>();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String> asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}

		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);

		response.setContentType("application/json; charset=UTF-8");
	}
}
