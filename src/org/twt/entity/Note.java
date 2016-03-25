package org.twt.entity;

public class Note {
	private long noteid;//帖子ID
	private Integer modual_id;//模块ID
	private String modual_name;//模块名称
	private String title;//标题
	private String url;//帖子的url
	private Integer page_current;// 帖子是第几页的
	private Integer author;// 发帖人
	private Integer create_date;// 发帖时间
	private Integer lastpublish;// 最后回帖人
	private String lastpublish_time;// 最后回帖时间
	private Integer source;// 来源
	public long getNoteid() {
		return noteid;
	}
	public void setNoteid(long noteid) {
		this.noteid = noteid;
	}
	public Integer getModual_id() {
		return modual_id;
	}
	public void setModual_id(Integer modual_id) {
		this.modual_id = modual_id;
	}
	public String getModual_name() {
		return modual_name;
	}
	public void setModual_name(String modual_name) {
		this.modual_name = modual_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getPage_current() {
		return page_current;
	}
	public void setPage_current(Integer page_current) {
		this.page_current = page_current;
	}
	public Integer getAuthor() {
		return author;
	}
	public void setAuthor(Integer author) {
		this.author = author;
	}
	public Integer getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Integer create_date) {
		this.create_date = create_date;
	}
	public Integer getLastpublish() {
		return lastpublish;
	}
	public void setLastpublish(Integer lastpublish) {
		this.lastpublish = lastpublish;
	}
	public String getLastpublish_time() {
		return lastpublish_time;
	}
	public void setLastpublish_time(String lastpublish_time) {
		this.lastpublish_time = lastpublish_time;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Note() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Note(long noteid, Integer modual_id, String modual_name, String title, String url, Integer page_current,
			Integer author, Integer create_date, Integer lastpublish, String lastpublish_time, Integer source) {
		super();
		this.noteid = noteid;
		this.modual_id = modual_id;
		this.modual_name = modual_name;
		this.title = title;
		this.url = url;
		this.page_current = page_current;
		this.author = author;
		this.create_date = create_date;
		this.lastpublish = lastpublish;
		this.lastpublish_time = lastpublish_time;
		this.source = source;
	}
	public String toString() {
		return "Note [noteid=" + noteid + ", modual_id=" + modual_id + ", modual_name=" + modual_name + ", title="
				+ title + ", url=" + url + ", page_current=" + page_current + ", author=" + author + ", create_date="
				+ create_date + ", lastpublish=" + lastpublish + ", lastpublish_time=" + lastpublish_time + ", source="
				+ source + "]";
	}
	
}
