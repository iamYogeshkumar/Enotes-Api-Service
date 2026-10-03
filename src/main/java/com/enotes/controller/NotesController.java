package com.enotes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.FavouriteNoteDto;
import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.endpoint.NotesEndpoint;
import com.enotes.entity.FileDetails;
import com.enotes.service.NotesService;
import com.enotes.util.CommonUtil;

@RestController
public class NotesController implements NotesEndpoint {
	
	@Autowired
	private NotesService notesService;
	
	@Override
	public ResponseEntity<?>  saveNotes( String notes , MultipartFile file)throws Exception{
		boolean saveNotes = notesService.saveNotes(notes,file);
		if(saveNotes) {
			return CommonUtil.createBuildResponseMessage("Notes Saved", HttpStatus.CREATED);
		}
		return CommonUtil.createBuildResponseMessage("Notes not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<?> downloadFile( Integer id) throws Exception, IOException{
		FileDetails fileDetails=notesService.getFileDetails(id);
		
		byte [] downloadfile=notesService.downloadFile(fileDetails);
		
		String contentType=CommonUtil.getContentType(fileDetails.getOriginalFileName());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		
		headers.setContentDispositionFormData("attachement", fileDetails.getDisplayFileName());
		
		return  ResponseEntity.ok().headers(headers).body(downloadfile);
	}
	
	@Override
	public ResponseEntity<?>  getAllNotes(){
		 List<NotesDto> notes = notesService.getAllNotes();
		if(!CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	
	@Override
	public ResponseEntity<?>  getAllNotesByUser(int pageNo ,int pageSize, CustomUserDetails userDetails){
		
//		Sort sort=direction.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
//		Integer userId=1;
		Integer userId = userDetails.getUser().getId();
		NotesResponse notesResponse= notesService.getAllNotesByUser(userId,pageNo,pageSize);
		
//		List<NotesDto> notes = notesService.getAllNotes();
		if(!ObjectUtils.isEmpty(notesResponse)) {
			return CommonUtil.createBuildResponse(notesResponse, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	
	@Override
	public ResponseEntity<?>  searchNotes(
			 String searchKeyword,
			 int pageNo ,
			 int pageSize,
			 CustomUserDetails userDetails
			){
		
//		Sort sort=direction.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
//		Integer userId=1;
		Integer userId = userDetails.getUser().getId();
		NotesResponse notesResponse= notesService.getNotesByUserSearch(pageNo,pageSize,searchKeyword);
		
//		List<NotesDto> notes = notesService.getAllNotes();
		if(!ObjectUtils.isEmpty(notesResponse)) {
			return CommonUtil.createBuildResponse(notesResponse, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	@Override
	public ResponseEntity<?> deleteNotes( Integer id) throws Exception{
		boolean softDeleteNotes = notesService.softDeleteNotes(id);
		
		if(softDeleteNotes) {
			return CommonUtil.createBuildResponseMessage("delete success", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponseMessage("Not deleted", HttpStatus.NOT_FOUND);
		
		
	}
	
	@Override
	public ResponseEntity<?> restoreNotes( Integer id) throws Exception{
		boolean restoreNotes = notesService.restoreNotes(id);
		
		if(restoreNotes) {
			return CommonUtil.createBuildResponseMessage("notes restore successfully", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponseMessage("notes not found ", HttpStatus.NOT_FOUND);
	}
	
	
	@Override
	public ResponseEntity<?> getUserRecycleBinNotes(  CustomUserDetails userDetails) throws Exception{
		Integer userId = userDetails.getUser().getId();
//		int userId=1;
		List<NotesDto> notes = notesService.getUserRecycleBinNote(userId);
		
		if(CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("notes not available", HttpStatus.NOT_FOUND);
		}
		
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<?> hardDeleteNotes( Integer id) throws Exception{
		boolean softDeleteNotes = notesService.hardDeleteNotes(id);
		
		if(softDeleteNotes) {
			return CommonUtil.createBuildResponseMessage("delete success", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponseMessage("Not deleted", HttpStatus.NOT_FOUND);
		
		
	}
	
	@Override
	public ResponseEntity<?> emptyRecycleBin( CustomUserDetails details) throws Exception{
//		int userId=1;
		Integer userId = details.getUser().getId();
		boolean softDeleteNotes = notesService.emptyRecycleBin(userId);
		
		if(softDeleteNotes) {
			return CommonUtil.createBuildResponseMessage("notes delete from recycle", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponseMessage("Not deleted", HttpStatus.NOT_FOUND);
		
		
	}
	
	@Override
	public ResponseEntity<?> favouriteNote( int notesId, CustomUserDetails customUserDetails) throws Exception{
//		int userId=1;
		Integer userId = customUserDetails.getUser().getId();
		 notesService.favouriteNotes(userId);
		return CommonUtil.createBuildResponseMessage("marked as favourite", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> unfavouriteNote( int notesId) throws Exception{
//		int userId=1;
         notesService.unfavouriteNotes(notesId);
		
		return CommonUtil.createBuildResponseMessage("marked as unfavourite", HttpStatus.OK);
		
		
	}
	
	@Override
	public ResponseEntity<?> getAllfavouriteNote() throws Exception{
//		int userId=1;
		List<FavouriteNoteDto> favoriteNotes = notesService.getFavoriteNotes();
		if(CollectionUtils.isEmpty(favoriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtil.createBuildResponse(favoriteNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> copyNotes( int notesId) throws Exception{
		int userId=1;
		 Boolean copyNotes = notesService.copyNotes(notesId);
		 if(copyNotes) {
			return CommonUtil.createBuildResponseMessage("notes copy successfully", HttpStatus.CREATED); 
		 }
		return CommonUtil.createErrorResponseMessage(" copying notes failed try again", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	

}
