package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.NotesDto;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.service.NotesService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/")
	public ResponseEntity<?>  saveNotes(@RequestBody NotesDto notesDto)throws ResourceNotFoundException{
		boolean saveNotes = notesService.saveNotes(notesDto);
		if(saveNotes) {
			return CommonUtil.createBuildResponseMessage("Notes Saved", HttpStatus.CREATED);
		}
		return CommonUtil.createBuildResponseMessage("Notes not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/")
	public ResponseEntity<?>  getAllNotes(NotesDto notesDto){
		 List<NotesDto> notes = notesService.getAllNotes();
		if(!CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}

}
