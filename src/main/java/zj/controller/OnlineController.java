package zj.controller;

import zj.model.Tonline;
import zj.util.ZJ_BeanUtils;
import zj.vo.PageModel;


public class OnlineController extends com.jfinal.core.Controller{
	

	public void datagrid() {
		PageModel pageModel = new PageModel();
		ZJ_BeanUtils.copyPropertiesMapArray(getParaMap(), pageModel, false);
		renderJson(Tonline.dao.datagrid(pageModel));
	}
}
