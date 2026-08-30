package com.enotes.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDto;
import com.enotes.exception.ResourceNotFoundException;

public interface NotesService {

	public boolean saveNotes(String notes,MultipartFile file) throws ResourceNotFoundException,IOException;
	
	public List<NotesDto> getAllNotes();
}
