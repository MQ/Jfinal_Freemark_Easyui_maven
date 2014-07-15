package zj.model;

import java.util.LinkedList;
import java.util.List;

import zj.model.utils.QueryHandle;
import zj.util.ZJ_BeanUtils;
import zj.util.ZJ_EncryptUtils;
import zj.vo.PageModel;
import zj.vo.UserModel;
import zj.vo.ui.EasyuiDatagrid;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class Tuser extends Model<Tuser>{

	public static final Tuser dao = new Tuser();

	public UserModel find(UserModel user) {
		String sql=String.format("select * from tuser t where t.name='%s' and t.pwd='%s'",user.getName(),ZJ_EncryptUtils.md5(user.getPwd()));
		Record	r=Db.findFirst(sql);
		if(r.getColumns().size()>0){
			ZJ_BeanUtils.copyProperties(r.getColumns(), user, true);
			String roleIds = "";
			String roleNames = "";
			String resourceIds = "";
			String resourceNames = "";
			for(Record re:Db.find("select * from tuser_trole t where t.userId=?",r.getStr("id"))){
				Record trole=Db.findById("trole", re.getStr("roleId"));
				roleIds+=trole.getStr("id")+",";
				roleNames+=trole.getStr("text")+",";
			for(Record rec:Db.find("select * from trole_tresource t where t.roleId=?",re.getStr("roleId"))){
				Record tresource=Db.findById("tresource",rec.getStr("resourceId"));
				resourceIds+=tresource.getStr("id")+",";
				resourceNames+=tresource.getStr("text")+",";		
			}
			}
			user.setRoleIds(roleIds);
			user.setRoleNames(roleNames);
			user.setResourceIds(resourceIds);
			user.setResourceNames(resourceNames);
		}
		return user;
	}
	
	public EasyuiDatagrid datagrid(PageModel page) {
		EasyuiDatagrid dataGrid = new EasyuiDatagrid();
		String sql = "select * from tuser t";
		//模糊查询条件
		String totalSql = QueryHandle.getTotalSql(page, sql,null);
		Page<Record> records=Db.paginate(page.getPageNum(), page.getRows(),totalSql,"");
		List<UserModel> models = new LinkedList<UserModel>();
		for(Record r:records.getList()){
			UserModel im=new UserModel();
			ZJ_BeanUtils.copyProperties(r.getColumns(), im, true);
			//tuser_trole
			String roleIds = "";
			String roleNames = "";
		for(Record re:Db.find("select * from tuser_trole t where t.userId=?",r.getStr("id"))){
			Record trole=Db.findById("trole", re.getStr("roleId"));
			roleIds+=trole.getStr("id")+",";
			roleNames+=trole.getStr("text")+",";
		}
			im.setRoleIds(roleIds);
			im.setRoleNames(roleNames);
			models.add(im);
		}
		dataGrid.setRows(models);
		dataGrid.setTotal((long) records.getTotalRow());
		return dataGrid;
	}
	
	public String delete(String ids) {
		String failId="";
		for (String id : ids.split(",")) {
			if(Db.deleteById("tuser", id)==false){
				failId+=id+",";
			}
		}
		return failId;
	}

	public UserModel edit(String id) {
		Record r=Db.findById("tuser", id);
		UserModel userModel = new UserModel();
		if(r.getColumns().size()>0){
			String roleIds = "";
			ZJ_BeanUtils.copyProperties(r.getColumns(), userModel, true);
			for(Record re:Db.find("select * from tuser_trole t where t.userId=?",id)){
				roleIds+=re.getStr("roleId")+",";
			}
			userModel.setRoleIds(roleIds);
		}
		return userModel;
	}
}