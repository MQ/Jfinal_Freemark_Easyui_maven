package zj.controller;

import zj.model.Tlog;
import zj.util.ZJ_BeanUtils;
import zj.vo.PageModel;

import com.jfinal.core.Controller;


public class LogController extends Controller{
	
	public void index() {
		render("/ht/log/log.html");
	}

	public void datagrid() {
		PageModel pageModel = new PageModel();
		ZJ_BeanUtils.copyPropertiesMapArray(getParaMap(), pageModel, false);
		renderJson(Tlog.dao.datagrid(pageModel));
	}
	public void showView(){
		setAttr("logModel",Tlog.dao.show(getPara("id")));
		render("/ht/log/logShow.html");
	}

	
}
