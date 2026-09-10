package com.enotes.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.FavouriteNoteDto;
import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.entity.FavouriteNote;
import com.enotes.entity.FileDetails;
import com.enotes.exception.ResourceNotFoundException;

public interface NotesService {

	public boolean saveNotes(String notes,MultipartFile file) throws ResourceNotFoundException,IOException, Exception ;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails)  throws ResourceNotFoundException,IOException;

	public FileDetails getFileDetails(Integer id) throws ResourceNotFoundException;

	public NotesResponse getAllNotesByUser(Integer userId,int pageNo,int pageSize);

	public boolean softDeleteNotes(Integer id ) throws Exception;

	public boolean restoreNotes(Integer id)  throws Exception;

	public List<NotesDto> getUserRecycleBinNote(Integer id);

	public boolean hardDeleteNotes(Integer id)  throws Exception;

	public boolean emptyRecycleBin(int userId)  throws Exception;
	
	public void favouriteNotes(Integer noteId) throws Exception;
	
	public void unfavouriteNotes(Integer favouriteNoteId) throws Exception;
	
	public List<FavouriteNoteDto> getFavoriteNotes();

	public Boolean copyNotes(int notesId) throws Exception ;
	
	
}
