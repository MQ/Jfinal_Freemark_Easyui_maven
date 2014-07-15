package zj.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @文件名：UserModel.java
 * @作用：
 * @作者：张剑
 * @创建时间：2014-05-29 09:55:38
 */

//
public class UserModel implements Serializable {
	private static final long serialVersionUID = 1L;
	// 属性
	private String id;// id
	private String name;// 姓名
	private String pwd;// 密码
	private String sex;// 性别
	private String pic;// 头像url
	private String email;// 邮箱
	private String phone;// 手机号
	private Date createtime;
	private String resourceIds;
	private String resourceNames;
	private String roleIds;
	private String roleNames;
	private List<String> resourceUrls = new ArrayList<String>();

	// 构造方法
	public UserModel() {
	}

	public UserModel(String id, String jobNum, String name, String pwd, Date birthday, String nativePlace, String sex, String pic, String email, String phone, String address, String education, String positionId) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.sex = sex;
		this.pic = pic;
		this.email = email;
		this.phone = phone;
	}

	// get-set方法
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public List<String> getResourceUrls() {
		return resourceUrls;
	}

	public void setResourceUrls(List<String> resourceUrls) {
		this.resourceUrls = resourceUrls;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", pwd=" + pwd
				+ ", sex=" + sex + ", pic=" + pic + ", email=" + email
				+ ", phone=" + phone + ", createtime=" + createtime
				+ ", resourceIds=" + resourceIds + ", resourceNames="
				+ resourceNames + ", roleIds=" + roleIds + ", roleNames="
				+ roleNames + ", resourceUrls=" + resourceUrls + "]";
	}
	

}
