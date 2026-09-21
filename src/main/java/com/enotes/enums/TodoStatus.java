package com.enotes.enums;

import java.util.Arrays;

import org.hibernate.ResourceClosedException;

public enum TodoStatus {
	
	NOT_STARTED(1,"Not Started"),
	In_PROGRESS(2,"In Progress"),
	COMPLETED(3,"Completed");
	
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	

	public String getName() {
		return name;
	}

	private TodoStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public static String getStatusName(int id) {
		String stsName = Arrays.stream(TodoStatus.values()).filter(st->st.getId()==id).map(st->st.getName().toUpperCase()).findFirst().orElseThrow(()->new ResourceClosedException("status not available"));
		return stsName;
	}


}
