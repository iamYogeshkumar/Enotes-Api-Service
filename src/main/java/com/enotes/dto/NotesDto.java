package com.enotes.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotesDto {

	
	private Integer id;
	private String title;
	private String discription;
	
	private CategoryDto category;
	
	private Integer createdBy;

	private Date createdOn;

	private Integer updatedBy;

	private Date updatedOn;
	
	private FileDetails fileDetails;
	
	@Setter
	@Getter
	public static class CategoryDto{
		private Integer id;

		private String name;

	}
	
	@Setter
	@Getter
	public static class FileDetails{
		private Integer id;
		private String originalFileName;
		private String displayFileName;
	}
	
	
}
