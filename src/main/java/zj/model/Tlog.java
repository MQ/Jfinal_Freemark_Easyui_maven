package zj.model;

import java.util.LinkedList;
import java.util.List;

import zj.model.utils.QueryHandle;
import zj.util.ZJ_BeanUtils;
import zj.vo.LogModel;
import zj.vo.PageModel;
import zj.vo.ui.EasyuiDatagrid;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class Tlog extends Model<Tlog>{
	public static final Tlog dao = new Tlog();
	
	public EasyuiDatagrid datagrid(PageModel page) {
		EasyuiDatagrid dataGrid = new EasyuiDatagrid();
		String sql = "select * from tlog t";
		String totalSql = QueryHandle.getTotalSql(page, sql,null);
		Page<Record> records=Db.paginate(page.getPageNum(), page.getRows(),totalSql,"");
		List<LogModel> models = new LinkedList<LogModel>();
		for(Record r:records.getList()){
			LogModel im=new LogModel();
			ZJ_BeanUtils.copyProperties(r.getColumns(), im, true);
			System.out.println("LogModel:"+im);
			models.add(im);
		}
		dataGrid.setRows(models);
		dataGrid.setTotal((long) records.getTotalRow());
		return dataGrid;
	}
	
	public LogModel show(String id){
		LogModel lm=new LogModel();
		Record r=Db.findById("tlog", id);
		if(r.getColumns().size()>0){
			ZJ_BeanUtils.copyProperties(r.getColumns(), lm, true);
		}
		return lm;
	}


}