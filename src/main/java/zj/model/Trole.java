package zj.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import zj.util.ZJ_BeanUtils;
import zj.util.ZJ_GeneratorUtils;
import zj.vo.PageModel;
import zj.vo.Role;
import zj.vo.ui.EasyuiDatagrid;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class Trole extends Model<Trole>{

	public static final Trole dao = new Trole();

	public EasyuiDatagrid datagrid(PageModel page) {
		EasyuiDatagrid dataGrid = new EasyuiDatagrid();
		String sql = "select * from trole t";
		Page<Record> records=Db.paginate(page.getPageNum(), page.getRows(),sql,"");
		List<Role> models = new LinkedList<Role>();
		for(Record r:records.getList()){
			String resourceIds = "";
			String resourceNames = "";
			Role im=new Role();
			ZJ_BeanUtils.copyProperties(r.getColumns(), im, true);
			for(Record re:Db.find("select * from trole_tresource t where t.roleId=?",im.getId())){
				Record tresource=Db.findById("tresource",re.getStr("resourceId"));
				resourceIds+=tresource.getStr("id")+",";
				resourceNames+=tresource.getStr("text")+",";	
			}
			im.setResourceIds(resourceIds);
			im.setResourceNames(resourceNames);
			models.add(im);
		}
		dataGrid.setRows(models);
		dataGrid.setTotal((long) records.getTotalRow());
		return dataGrid;
	}
	public String delete(String ids) {
		String failId="";
		for (String id : ids.split(",")) {
			if(Db.deleteById("trole", id)==false){
				failId+=id+",";
			}
		}
		return failId;
	}

	public boolean save(Map<String,String[]> map){
		String roleId=ZJ_GeneratorUtils.idGenerator();
		boolean bool=false;
		Record r=new Record().set("id",roleId).set("text", map.get("text")[0]);
		if(Db.save("trole", r)){
			for(String resourceId:map.get("resourceIds")){
				Record re=new Record().set("id", ZJ_GeneratorUtils.idGenerator())
						.set("resourceId", resourceId).set("roleId", roleId);
				if(Db.save("trole_tresource", re)){
					bool=true;
				}else{
					bool=false;
				}
			}
		}
		return bool;
	}

	
	public boolean update(Map<String,String[]> map){
		boolean bool=false;
		Record r=Db.findById("trole", map.get("id")[0]).set("text", map.get("text")[0]);
		if(Db.update("trole", r)){
			String[] resourceIds=map.get("resourceIds");
			List<Record> list=Db.find("select * from  trole_tresource t where t.roleId=?", map.get("id")[0]);
			for(Record re:list){
				Db.delete("trole_tresource", re);
			}
			for(String resourceId:resourceIds){
				Record record=new Record().set("id", ZJ_GeneratorUtils.idGenerator())
						.set("resourceId", resourceId).set("roleId", map.get("id")[0]);
				if(Db.save("trole_tresource", record)){
					bool=true;
				}else{
					bool=false;
				}
			}
		}
		return bool;
	}
	public Role edit(String id){
		Role r=new Role();
		Record record=Db.findById("trole", id);
		ZJ_BeanUtils.copyProperties(record.getColumns(), r, false);
		String resourceIds="";
		for(Record re:Db.find("select * from trole_tresource t where t.roleId=?",id)){
			resourceIds+=re.getStr("resourceId")+",";
		}
		r.setResourceIds(resourceIds);
		return r;
	}
	
}