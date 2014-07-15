package zj.controller;

import zj.model.Ticon;
import zj.util.ZJ_BeanUtils;
import zj.util.ZJ_GeneratorUtils;
import zj.vo.PageModel;
import zj.vo.ViewData;

import com.jfinal.core.Controller;


public class IconController extends Controller{
	
	public void index(){
		render("/ht/icon/icon.html");
	}
	public void datagrid() {
		PageModel pageModel = new PageModel();
		ZJ_BeanUtils.copyPropertiesMapArray(getParaMap(), pageModel, false);
		renderJson(Ticon.dao.datagrid(pageModel));
	}
	public void add() {
		render("/ht/icon/iconAdd.html");
	}

	public void save() {
		ViewData vd = new ViewData();
		vd.setSuccess(getModel(Ticon.class).set("id", ZJ_GeneratorUtils.idGenerator()).save());
		renderJson(vd);
	}

	public void delete() {
		ViewData vd = new ViewData();
		String ids = Ticon.dao.delete(getPara("ids"));
		if (ids.length() == 0) {
			vd.setSuccess(true);
		} else {
			vd.setSuccess(false);
			vd.setObj(ids);
		}
		renderJson(vd);
	}

	public void edit() {
		setAttr("iconModel", Ticon.dao.findById(getPara("id")));
		render("/ht/icon/iconEdit.html");
	}

	public void update() {
		ViewData vd = new ViewData();
		vd.setSuccess(getModel(Ticon.class).update());
		renderJson(vd);
	}
	
}
