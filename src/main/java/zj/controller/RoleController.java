package zj.controller;

import zj.model.Trole;
import zj.util.ZJ_BeanUtils;
import zj.vo.PageModel;
import zj.vo.ViewData;

import com.jfinal.core.Controller;


public class RoleController extends Controller{
	

	public void datagrid() {
		PageModel pageModel = new PageModel();
		ZJ_BeanUtils.copyPropertiesMapArray(getParaMap(), pageModel, false);
		renderJson(Trole.dao.datagrid(pageModel));
	}

	public void add() {
		render("/ht/admin/jsglAdd.html");
	}

	public void save() {
		ViewData vd = new ViewData();
		vd.setSuccess(Trole.dao.save(getParaMap()));
		renderJson(vd);
	}
	
	public void edit() {
		setAttr("roleModel",Trole.dao.edit(getPara("id")));
		render("/ht/admin/jsglEdit.html");
	}

	public void update() {
		ViewData vd = new ViewData();
		vd.setSuccess(Trole.dao.update(getParaMap()));
		renderJson(vd);
	}
	
	public void delete() {
		ViewData vd = new ViewData();
		System.out.println("ids="+getPara("ids"));
		String ids = Trole.dao.delete(getPara("ids"));
		if (ids.length() == 0) {
			vd.setSuccess(true);
		} else {
			vd.setSuccess(false);
			vd.setObj(ids);
		}
		renderJson(vd);
	}
	
}
