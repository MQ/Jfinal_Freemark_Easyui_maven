package zj.controller;



import java.util.ArrayList;
import java.util.List;

import zj.model.Tresource;
import zj.util.ZJ_BeanUtils;
import zj.util.ZJ_GeneratorUtils;
import zj.util.ZJ_ResourceUtil;
import zj.vo.SessionInfo;
import zj.vo.ViewData;
import zj.vo.ui.EasyuiMenu;
import zj.vo.ui.EasyuiTreegrid;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;


public class ResourceController extends Controller{
	
	
	public void treegrid() {
		renderJson(Tresource.dao.treegrid());
	}
	
	public void allTreeNode(){
		SessionInfo sessionInfo =(SessionInfo) getSession().getAttribute(ZJ_ResourceUtil.getSessionInfoName());
		String userId = sessionInfo.getAdminId();
		String resourceIds = sessionInfo.getResourceIds();
		if (userId.equals("0")) {
			renderJson(Tresource.dao.allTreeNode());
		} else {
			renderJson(Tresource.dao.allTreeNodeByResIds(resourceIds));
		}
	}
	public void add() {
		render("/ht/admin/zyglAdd.html");
	}
	public void save() {
		ViewData vd = new ViewData();
		vd.setSuccess(getModel(Tresource.class).set("id", ZJ_GeneratorUtils.idGenerator()).save());
		renderJson(vd);
	}
	
	public void delete() {
		ViewData vd = new ViewData();
		vd.setSuccess(Tresource.dao.delete(getPara("id")));
		renderJson(vd);
	}

	public void edit() {
		EasyuiTreegrid resource =new EasyuiTreegrid();
		ZJ_BeanUtils.copyProperties(Db.findById("tresource", getPara("id")), resource, false);
		setAttr("resourceModel",resource);
		render("/ht/admin/zyglEdit.html");
	}
	public void update(){
		ViewData vd = new ViewData();
		vd.setSuccess(getModel(Tresource.class).update());
		renderJson(vd);
	}
	
	
	public void allMenuNode() {
		List<EasyuiMenu> list=new ArrayList<>();
		SessionInfo sessionInfo = (SessionInfo) getRequest().getSession().getAttribute(ZJ_ResourceUtil.getSessionInfoName());
		String userId = sessionInfo.getAdminId();
		String resourceIds = sessionInfo.getResourceIds();
		if ("0".equals(userId)) {
			list=Tresource.dao.allMenuNode();
		} else {
			list=Tresource.dao.allMenuNodeByResIds(resourceIds);
		}
		renderJson(list);
	}

}
