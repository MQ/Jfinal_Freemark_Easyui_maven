package zj.vo.ui;

import java.util.ArrayList;
import java.util.List;

public class EasyuiDatagrid {

	private Long total = 0L;
	@SuppressWarnings("rawtypes")
	private List rows = new ArrayList();

	public EasyuiDatagrid() {

	}

	@SuppressWarnings("rawtypes")
	public EasyuiDatagrid(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}

	@SuppressWarnings("rawtypes")
	public void setRows(List rows) {
		this.rows = rows;
	}

}
