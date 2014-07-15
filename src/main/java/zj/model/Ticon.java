package zj.model;

import java.util.LinkedList;
import java.util.List;

import zj.model.utils.QueryHandle;
import zj.util.ZJ_BeanUtils;
import zj.vo.IconModel;
import zj.vo.PageModel;
import zj.vo.ui.EasyuiDatagrid;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class Ticon extends Model<Ticon>{
	
	public static final Ticon dao = new Ticon();
	
	public EasyuiDatagrid datagrid(PageModel page) {
		EasyuiDatagrid dataGrid = new EasyuiDatagrid();
		String sql = "select * from ticon t";
		String totalSql = QueryHandle.getTotalSql(page, sql,"name");
		Page<Record> records=Db.paginate(page.getPageNum(), page.getRows(),totalSql,"");
		List<IconModel> iconModels = new LinkedList<IconModel>();
		for(Record r:records.getList()){
			IconModel im=new IconModel();
			ZJ_BeanUtils.copyProperties(r.getColumns(), im, true);
			iconModels.add(im);
		}
		dataGrid.setRows(iconModels);
		dataGrid.setTotal((long) records.getTotalRow());
		return dataGrid;
	}
	
	public String delete(String ids) {
		String failId="";
		for (String id : ids.split(",")) {
			if(Db.deleteById("ticon", id)==false){
				failId+=id+",";
			}
		}
		return failId;
	}
}