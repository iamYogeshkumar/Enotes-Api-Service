package com.enotes.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.entity.FileDetails;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.service.NotesService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/")
	public ResponseEntity<?>  saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file)throws ResourceNotFoundException, IOException{
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
			@RequestParam(name = "pageSize", defaultValue = "4") int pageSize
			
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

}
