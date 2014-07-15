package zj.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zj.util.ZJ_BeanUtils;
import zj.util.ZJ_StringUtils;
import zj.vo.ui.EasyuiMenu;
import zj.vo.ui.EasyuiTreegrid;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class Tresource extends Model<Tresource>{

	public static final Tresource dao = new Tresource();

	public List<EasyuiTreegrid> treegrid() {
		List<Record> l=Db.find("select * from tresource t order by t.seq");
		List<EasyuiTreegrid> nl = new ArrayList<EasyuiTreegrid>();
		if (l != null && l.size() > 0) {
			for(Record record:l){
				EasyuiTreegrid r = new EasyuiTreegrid();
				ZJ_BeanUtils.copyProperties(record.getColumns(), r, true);
				if(record.get("pid")!=null){
					Record precord=Db.findById("tresource", record.get("pid"));
					r.setPname(precord.getStr("text"));
				}
				nl.add(r);
			}
		}
		return nl;
	}

	public List<EasyuiTreegrid> allTreeNode() {
		List<EasyuiTreegrid> nl = new ArrayList<EasyuiTreegrid>();
		String sql = "select * from tresource t order by t.seq";
		List<Record> l = Db.find(sql);
		if (l != null && l.size() > 0) {
			for(Record record:l){
				EasyuiTreegrid r = new EasyuiTreegrid();
				ZJ_BeanUtils.copyProperties(record.getColumns(), r, true);
				nl.add(r);
			}
		}
		return nl;
	}

	public List<EasyuiTreegrid> allTreeNodeByResIds(String resIds) {
		List<EasyuiTreegrid> nl = new ArrayList<EasyuiTreegrid>();
		if (null == resIds || "".equals(resIds.trim())) {
			return nl;
		} else {
			resIds = ZJ_StringUtils.changeStr(resIds);
			String sql = String.format("select * from tresource t where t.id in (%s) order by t.seq", resIds);
			List<Record> l=Db.find(sql);
			if (l != null && l.size() > 0) {
				for(Record record:l){
					EasyuiTreegrid r = new EasyuiTreegrid();
					ZJ_BeanUtils.copyProperties(record.getColumns(), r, true);
					nl.add(r);
				}
			}
			return nl;
		}
	}
	public List<EasyuiMenu> allMenuNode() {
		List<EasyuiMenu> nl = new ArrayList<EasyuiMenu>();
		String sql = "select * from tresource t where t.type='1' order by t.seq";
		List<Record> l = Db.find(sql);
		if (l != null && l.size() > 0) {
			for(Record record:l){
				EasyuiMenu m = new EasyuiMenu();
				Map<String, Object> attr = new HashMap<String, Object>();
				ZJ_BeanUtils.copyProperties(record.getColumns(), m, true);
				attr.put("url", record.get("url"));
				m.setAttributes(attr);
				nl.add(m);
			}
		}
		return nl;
	}
	
	public List<EasyuiMenu> allMenuNodeByResIds(String resIds) {
		List<EasyuiMenu> nl = new ArrayList<EasyuiMenu>();
		if (null == resIds || "".equals(resIds.trim())) {
			return nl;
		} else {
		resIds = ZJ_StringUtils.changeStr(resIds);
		String sql = String.format("select * from tresource t where t.id in (%s) and t.type='1' order by t.seq",resIds);
		List<Record> l = Db.find(sql);
		if (l != null && l.size() > 0) {
			for(Record record:l){
				EasyuiMenu m = new EasyuiMenu();
				Map<String, Object> attr = new HashMap<String, Object>();
				ZJ_BeanUtils.copyProperties(record.getColumns(), m, true);
				attr.put("url", record.get("url"));
				m.setAttributes(attr);
				nl.add(m);
			}
		}		
		}
		return nl;
	}
	
//	public EasyuiTreegrid add(EasyuiTreegrid resource) {
//		Record r=new Record();
//		resource.setId(ZJ_GeneratorUtils.idGenerator());
//		ZJ_BeanUtils.copyProperties(resource, r.getColumns(), true);
//		
//		return Db.save("tresource", r) ? resource:null;
//	}
	
	public boolean delete(String id) {
		return Db.deleteById("tresource",id);
	}
	
	
	/*public EasyuiTreegrid edit(EasyuiTreegrid resource) {
		Record record=Db.findById("tresource", resource.getId());
		if(record.getColumns().size()>0){
			BeanUtils.copyProperties(resource, t);
			t.setTresource(null);// 现将当前节点的父节点置空
			if (resource.getPid() != null && !resource.getPid().trim().equals("") && !resource.getPid().equals(resource.getId())) {
				// 如果pid不为空，并且pid不跟自己的id相同，说明要修改当前节点的父节点
				Tresource presource = resourceDao.get(Tresource.class, resource.getPid());// 要设置的上级权限
				if (presource != null) {
					if (isDown(t, presource)) {// 要将当前节点修改到当前节点的子节点中
						Set<Tresource> tresources = t.getTresources();// 当前要修改的权限的所有下级权限
						if (tresources != null && tresources.size() > 0) {
							for (Tresource tresource : tresources) {
								if (tresource != null) {
									tresource.setTresource(null);
								}
							}
						}
					}
					t.setTresource(presource);
				}
			}
		}
		
	}*/
	
}