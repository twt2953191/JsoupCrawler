package org.twt.entity;

import java.util.HashMap;
import java.util.List;

public class NoteUserVO {
	private HashMap<Integer,User> userMap;
	private List<Note> noteList;
	
	public HashMap<Integer, User> getUserMap() {
		return userMap;
	}
	public void setUserMap(HashMap<Integer, User> userMap) {
		this.userMap = userMap;
	}
	public List<Note> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<Note> noteList) {
		this.noteList = noteList;
	}


}
