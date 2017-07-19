package net.ats.webui.enums;

public enum ByTypes {
	XPATH("xpath", "XPATH:", 1, "当xpath的路径以/开头时，表示让Xpath解析引擎从文档的根节点开始解析。当xpath路径以//开头时，则表示让xpath引擎从文档的任意符合的元素节点开始进行解析。而当/出现在xpath路径中时，则表示寻找父节点的直接子节点，当//出现在xpath路径中时，表示寻找父节点下任意符合条件的子节点，不管嵌套了多少层级"), 
	ID("ID", "ID:", 2, "通过标签的ID属性值定位页面元素"),
	NAME("name", "NAME:", 5, "通过标签的Name属性值定位页面元素"),
	TAGNAME("tagName", "TAGNAME:", 6, "通过标签名称属性值定位页面元素"),
	CLASSNAME("className", "CLASSNAME:", 7, "通过标签的ClassName属性值定位页面元素，className属性是利用元素的css样式表所引用的伪类名称来进行元素查找的方法"),
	LINKTEXT("linkText", "LINKTEXT:", 3, "通过超文本链接上的文字信息来定位元素"),
	PARTIALLINKTEXT("partialLinkText", "PARTIALLINKTEXT:", 4, "通过部分超文本链接文字信息模糊匹配来定位元素"),
	CSSSELECTOR("cssSelector", "CSSSELECTOR:", 8, "");
	
	private String name;
	private String tag;
	private int type;
	private String desc;
	
	ByTypes(String name, String tag, int type, String desc){
		this.name = name;
		this.setTag(tag);
		this.type = type;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
