package zj.vo.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class EasyuiMenu implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String url;
	private String iconCls;
	private BigDecimal seq;
	private String pid;
	private String pname;
	private Map<String, Object> attributes = new HashMap<String, Object>();

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public BigDecimal getSeq() {
		return seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "Menu [pid=" + pid + ", pname=" + pname + ", attributes=" + attributes + ", id=" + id + ", text=" + text + ", url=" + url + ", iconCls=" + iconCls + ", seq=" + seq + "]";
	}

}
