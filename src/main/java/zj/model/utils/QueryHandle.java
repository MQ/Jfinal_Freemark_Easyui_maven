package zj.model.utils;

import zj.vo.PageModel;

public class QueryHandle {
	
	public static  String getTotalSql(PageModel page, String sql,String attribute) {
		sql += " where 1=1 ";
		if (page.getName() != null && !page.getName().trim().equals("")&&attribute!=null&&!"".equals(attribute)) {
			sql += " and t."+attribute.trim()+" like '%" + page.getName().trim() + "%'";
		}
		return sql;
	}

	public static String getListSql(PageModel page, String sql,String attribute) {
		sql = getTotalSql(page, sql,attribute);
		if (page.getSort() != null) {
			sql += " order by " + page.getSort() + " " + page.getOrder();
		}
		return sql;
	}
}
