package zj.vo;

public class Role implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private String resourceIds;
	private String resourceNames;



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

}
