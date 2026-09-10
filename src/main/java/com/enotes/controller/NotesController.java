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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.FavouriteNoteDto;
import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.entity.FileDetails;
import com.enotes.service.NotesService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/")
	public ResponseEntity<?>  saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file)throws Exception{
		boolean saveNotes = notesService.saveNotes(notes,file);
		if(saveNotes) {
			return CommonUtil.createBuildResponseMessage("Notes Saved", HttpStatus.CREATED);
		}
		return CommonUtil.createBuildResponseMessage("Notes not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception, IOException{
		FileDetails fileDetails=notesService.getFileDetails(id);
		
		byte [] downloadfile=notesService.downloadFile(fileDetails);
		
		String contentType=CommonUtil.getContentType(fileDetails.getOriginalFileName());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		
		headers.setContentDispositionFormData("attachement", fileDetails.getDisplayFileName());
		
		return  ResponseEntity.ok().headers(headers).body(downloadfile);
	}
	
	@GetMapping("/")
	public ResponseEntity<?>  getAllNotes(NotesDto notesDto){
		 List<NotesDto> notes = notesService.getAllNotes();
		if(!CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/user-notes")
	public ResponseEntity<?>  getAllNotesByUser(
			@RequestParam(name = "pageNo", defaultValue ="0") int pageNo ,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize
			
			){
		
//		Sort sort=direction.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Integer userId=1;
		NotesResponse notesResponse= notesService.getAllNotesByUser(userId,pageNo,pageSize);
		
//		List<NotesDto> notes = notesService.getAllNotes();
		if(!ObjectUtils.isEmpty(notesResponse)) {
			return CommonUtil.createBuildResponse(notesResponse, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
		boolean softDeleteNotes = notesService.softDeleteNotes(id);
		
		if(softDeleteNotes) {
			return CommonUtil.createBuildResponseMessage("delete success", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponseMessage("Not deleted", HttpStatus.NOT_FOUND);
		
		
	}
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
		boolean restoreNotes = notesService.restoreNotes(id);
		
		if(restoreNotes) {
			return CommonUtil.createBuildResponseMessage("notes restore successfully", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponseMessage("notes not found ", HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("/recycle-bin")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
		int userId=1;
		List<NotesDto> notes = notesService.getUserRecycleBinNote(userId);
		
		if(CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponseMessage("notes not available", HttpStatus.NOT_FOUND);
		}
		
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
		boolean softDeleteNotes = notesService.hardDeleteNotes(id);
		
		if(softDeleteNotes) {
			return CommonUtil.createBuildResponseMessage("delete success", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponseMessage("Not deleted", HttpStatus.NOT_FOUND);
		
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> emptyRecycleBin() throws Exception{
		int userId=1;
		boolean softDeleteNotes = notesService.emptyRecycleBin(userId);
		
		if(softDeleteNotes) {
			return CommonUtil.createBuildResponseMessage("notes delete from recycle", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponseMessage("Not deleted", HttpStatus.NOT_FOUND);
		
		
	}
	
	@GetMapping("/fav/{notesId}")
	public ResponseEntity<?> favouriteNote(@PathVariable int notesId) throws Exception{
		int userId=1;
		 notesService.favouriteNotes(userId);
		return CommonUtil.createBuildResponseMessage("marked as favourite", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/un-fav/{notesId}")
	public ResponseEntity<?> unfavouriteNote(@PathVariable int notesId) throws Exception{
		int userId=1;
         notesService.unfavouriteNotes(notesId);
		
		return CommonUtil.createBuildResponseMessage("marked as unfavourite", HttpStatus.OK);
		
		
	}
	
	@GetMapping("/fav-note")
	public ResponseEntity<?> getAllfavouriteNote() throws Exception{
		int userId=1;
		List<FavouriteNoteDto> favoriteNotes = notesService.getFavoriteNotes();
		if(CollectionUtils.isEmpty(favoriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtil.createBuildResponse(favoriteNotes, HttpStatus.OK);
	}
	
	@GetMapping("/copy/{notesId}")
	public ResponseEntity<?> copyNotes(@PathVariable int notesId) throws Exception{
		int userId=1;
		 Boolean copyNotes = notesService.copyNotes(notesId);
		 if(copyNotes) {
			return CommonUtil.createBuildResponseMessage("notes copy successfully", HttpStatus.CREATED); 
		 }
		return CommonUtil.createErrorResponseMessage(" copying notes failed try again", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	

}
