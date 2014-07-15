package zj.vo;

import java.util.Date;

public class Online implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String ip;
	private String loginname;
	private Date logindatetime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(Date logindatetime) {
		this.logindatetime = logindatetime;
	}

}
