package org.twt.entity;

import java.util.HashMap;
import java.util.List;

public class NoteDetailVO {
	List<NoteDetail> noteDetailList;
	HashMap<Integer, User> userMap;

	public List<NoteDetail> getNoteDetailList() {
		return noteDetailList;
	}

	public void setNoteDetailList(List<NoteDetail> noteDetailList) {
		this.noteDetailList = noteDetailList;
	}

	public HashMap<Integer, User> getUserMap() {
		return userMap;
	}

	public void setUserMap(HashMap<Integer, User> userMap) {
		this.userMap = userMap;
	}

	@Override
	public String toString() {
		return "NoteDetailVO [noteDetailList=" + noteDetailList + ", userMap=" + userMap + "]";
	}

	public NoteDetailVO(List<NoteDetail> noteDetailList, HashMap<Integer, User> userMap) {
		super();
		this.noteDetailList = noteDetailList;
		this.userMap = userMap;
	}

	public NoteDetailVO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
