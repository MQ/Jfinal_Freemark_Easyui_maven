package zj.vo;

import java.util.Date;



public class LogModel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Date createtime;
	private String event;
	private Integer type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public LogModel() {
		super();
	}
	public LogModel(String id, Date createtime, String event, Integer type) {
		super();
		this.id = id;
		this.createtime = createtime;
		this.event = event;
		this.type = type;
	}
	@Override
	public String toString() {
		return "LogModel [id=" + id + ", createtime=" + createtime + ", event="
				+ event + ", type=" + type + "]";
	}
	

}
