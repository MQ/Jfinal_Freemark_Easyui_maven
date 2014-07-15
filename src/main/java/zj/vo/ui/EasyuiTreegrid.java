package zj.vo.ui;

import java.math.BigDecimal;

public class EasyuiTreegrid implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pid;
	private String pname;
	private Integer type;
	private String id;
	private String text;
	private String url;
	private String iconCls;
	private BigDecimal seq;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BigDecimal getSeq() {
		return seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Override
	public String toString() {
		return "Resource [pid=" + pid + ", pname=" + pname + ", type=" + type + ", id=" + id + ", text=" + text + ", url=" + url + ", iconCls=" + iconCls + ", seq=" + seq + "]";
	}

}
