package org.twt.entity;

public class NoteDetail {

	private long note_id;

	private long author;

	private String note_time;

	private String content;

	private int flag;// 是发帖人，1是回帖人

	public long getNote_id() {
		return note_id;
	}

	public void setNote_id(long note_id) {
		this.note_id = note_id;
	}

	public long getAuthor() {
		return author;
	}

	public void setAuthor(long author) {
		this.author = author;
	}

	public String getNote_time() {
		return note_time;
	}

	public void setNote_time(String note_time) {
		this.note_time = note_time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public NoteDetail(long note_id, long author, String note_time, String content, int flag) {
		super();
		this.note_id = note_id;
		this.author = author;
		this.note_time = note_time;
		this.content = content;
		this.flag = flag;
	}

	public NoteDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "NoteDetail [note_id=" + note_id + ", author=" + author + ", note_time=" + note_time + ", content="
				+ content + ", flag=" + flag + "]";
	}

}
