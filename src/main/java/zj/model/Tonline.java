package zj.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import zj.model.utils.QueryHandle;
import zj.util.ZJ_BeanUtils;
import zj.util.ZJ_GeneratorUtils;
import zj.vo.Online;
import zj.vo.PageModel;
import zj.vo.ui.EasyuiDatagrid;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

@SuppressWarnings("serial")
public class Tonline extends Model<Tonline>{
	
	public static final Tonline dao = new Tonline();
	
	public EasyuiDatagrid datagrid(PageModel page) {
		EasyuiDatagrid dataGrid = new EasyuiDatagrid();
		String sql = "select * from tonline t";
		//模糊查询条件
		String totalSql = QueryHandle.getTotalSql(page, sql,null);
		Page<Record> records=Db.paginate(page.getPageNum(), page.getRows(),totalSql,"");
		List<Online> models = new LinkedList<Online>();
		for(Record r:records.getList()){
			Online im=new Online();
			ZJ_BeanUtils.copyProperties(r.getColumns(), im, true);
			models.add(im);
		}
		dataGrid.setRows(models);
		dataGrid.setTotal((long) records.getTotalRow());
		return dataGrid;
	}
	public void deleteTonlineByLoginNameAndIp(String loginName, String ip) {
		Record record =
				Db.findFirst("select * from tonline t where t.loginname= ? and t.ip= ?", loginName,ip);
		if(record.getColumns().size()>0){
			Db.delete("tonline", record);
		}
	}
	public void saveOrUpdateTonlineByLoginNameAndIp(String loginName, String ip) {
		Record record =
				Db.findFirst("select * from tonline t where t.loginname= ? and t.ip= ?", loginName,ip);
		if(record.getColumns().size()>0){
			record.set("logindatetime", new Date());
			Db.update("tonline",record);
		}else{
			record.set("id",ZJ_GeneratorUtils.idGenerator()).set("ip", ip).set("loginName",loginName).set("logindatetime", new Date());
			Db.save("tonline", record);
		}
	}
	
}