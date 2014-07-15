package zj.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import zj.model.Tuser;
import zj.util.IpUtil;
import zj.util.ZJ_BeanUtils;
import zj.util.ZJ_EncryptUtils;
import zj.util.ZJ_GeneratorUtils;
import zj.util.ZJ_ResourceUtil;
import zj.vo.PageModel;
import zj.vo.SessionInfo;
import zj.vo.UserModel;
import zj.vo.ViewData;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;


public class UserController extends com.jfinal.core.Controller{
	
	

	public void datagrid() {
		PageModel pageModel = new PageModel();
		ZJ_BeanUtils.copyPropertiesMapArray(getParaMap(), pageModel, false);
		renderJson(Tuser.dao.datagrid(pageModel));
	}
	public void add() {
		render("/ht/admin/yhglAdd.html");
	}

	public void save() {
		ViewData vd = new ViewData();
		String id=ZJ_GeneratorUtils.idGenerator();
		if(getModel(Tuser.class).set("id", id).set("createtime",new Date()).save()){
			if(getPara("roleIds").trim().length()>0){
				for(String roleId:getPara("roleIds").trim().split(",")){
					Db.save("tuser_trole", new Record().set("id", ZJ_GeneratorUtils.idGenerator()).set("roleId", roleId).set("userId", id));
				}
			}
		}
		vd.setSuccess(true);
		renderJson(vd);
	}

	public void delete() {
		ViewData vd = new ViewData();
		String ids = Tuser.dao.delete(getPara("ids"));
		if (ids.length() == 0) {
			vd.setSuccess(true);
		} else {
			vd.setSuccess(false);
			vd.setObj(ids);
		}
		renderJson(vd);
	}

	public void edit() {
		setAttr("userModel", Tuser.dao.edit(getPara("id")));
		render("/ht/admin/yhglEdit.html");
	}

	public void update() {
		ViewData vd = new ViewData();
		boolean bool=false;
		if(getModel(Tuser.class).update()){
			for(Record r:Db.find("select * from tuser_trole t where t.userId=?", getPara("tuser.id"))){
				Db.delete("tuser_trole", r);
			}
			for(String roleId:getPara("roleIds").split(",")){
				Record record=new Record().set("id", ZJ_GeneratorUtils.idGenerator()).set("userId", getPara("tuser.id")).set("roleId", roleId);
				if(Db.save("tuser_trole", record)){
					bool=true;
				}
			}
		}	
		vd.setSuccess(bool);
		renderJson(vd);
	}

	public void login() {
		ViewData vd=new ViewData();
		String msg="";
		UserModel u = new UserModel();
		List<Record> list=Db.find("select * from tuser t where t.name=? and t.pwd=?", getPara("name"),ZJ_EncryptUtils.md5(getPara("pwd")));
		if (list.size()>0) {
			ZJ_BeanUtils.copyProperties(list.get(0).getColumns(), u, true);
			List<Record> records=Db.find("select * from tuser_trole t where t.userId=?",u.getId());
			String roleIds="";
			String roleNames="";
			for(Record r: records){
			Record re=Db.findById("trole", r.getStr("roleId"));
				roleIds+=re.getStr("id")+",";
				roleNames+=re.getStr("text")+",";
			}
			u.setRoleIds(roleIds);
			u.setRoleNames(roleNames);
			vd.setSuccess(true);
			msg="登录成功！";
			SessionInfo sessionInfo = new SessionInfo();
			sessionInfo.setAdminId(u.getId());
			sessionInfo.setLoginName(u.getName());
			sessionInfo.setIp(IpUtil.getIpAddr(getRequest()));
			sessionInfo.setRoleIds(u.getRoleIds());
			sessionInfo.setRoleNames(u.getRoleNames());
			sessionInfo.setResourceIds(u.getResourceIds());
			sessionInfo.setResourceNames(u.getResourceNames());
			sessionInfo.setResourceUrls(u.getResourceUrls());
			setSessionAttr(ZJ_ResourceUtil.getSessionInfoName(), sessionInfo);
			getRequest().getSession().setAttribute("user", u);
			vd.setObj(sessionInfo);
		} else {
			msg="登录失败！";
		}
		vd.setMsg(msg);
		renderJson(vd);
	}

	public void logout() {
		HttpSession session=getSession();
		ViewData vd = new ViewData();
		if (session != null) {
			session.invalidate();
		}
		vd.setSuccess(true);
		renderJson(vd);
	}

	

	public void showModifyRole(){
		//TODO
		render("/ht/admin/yhglEditRole.html");
	}
	
	public void modifyRole() {
		//TODO
	}
	
	public void showModifyPwd(){
		setAttr("userId", getPara("id"));
		render("/ht/admin/yhglEditPwd.html");
	}
	
	public void modifyPwd() {
		ViewData vd = new ViewData();
		Record r=Db.findById("tuser", getPara("id")).set("pwd",ZJ_EncryptUtils.md5(getPara("pwd")));
		vd.setSuccess(Db.update("tuser", r));
		renderJson(vd);
	}

	
	public void modifyCurrentAdminPwd() {
		ViewData vd = new ViewData();
		SessionInfo sessionInfo=(SessionInfo) getSession().getAttribute("sessionInfo");
		Record r=Db.findById("tuser", sessionInfo.getAdminId()).set("pwd", ZJ_EncryptUtils.md5(getPara("pwd")));
		vd.setSuccess(Db.update("tuser", r));
		renderJson(vd);
	}

	public void adminInfo() {
		setAttr("sessionInfo", getSessionAttr("sessionInfo"));
		render("/ht/admin/adminInfo.html");
	}
}
