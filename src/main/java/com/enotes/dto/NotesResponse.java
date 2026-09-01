package com.enotes.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class NotesResponse {

	private List<NotesDto> notes;
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private long totalElement;
	
	private Integer totalPages;
	
	private boolean isFirst;
	
	private boolean isLast;
	
}
