package com.enotes.endpoint;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.config.security.CustomUserDetails;

@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {
	
	@PostMapping("/")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file)throws Exception;
	
	@GetMapping("/download/{id}")
	@PreAuthorize("hasAnyRole('ADMIN,USER')")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception, IOException;
	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?>  getAllNotes();
	
	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  getAllNotesByUser(
			@RequestParam(name = "pageNo", defaultValue ="0") int pageNo ,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			 @AuthenticationPrincipal CustomUserDetails userDetails
			);
	
	
	@GetMapping("/search")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?>  searchNotes(
			@RequestParam(name="searchKeyword", defaultValue = "") String searchKeyword,
			@RequestParam(name = "pageNo", defaultValue ="0") int pageNo ,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
			 @AuthenticationPrincipal CustomUserDetails userDetails
			);
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	
	@GetMapping("/restore/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
	
	
	@GetMapping("/recycle-bin")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserRecycleBinNotes( @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception;
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> emptyRecycleBin(@AuthenticationPrincipal CustomUserDetails details) throws Exception;
	
	
	@GetMapping("/fav/{notesId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> favouriteNote(@PathVariable int notesId,@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception;
	
	@DeleteMapping("/un-fav/{notesId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> unfavouriteNote(@PathVariable int notesId) throws Exception;
	
	@GetMapping("/fav-note")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllfavouriteNote() throws Exception;
	
	@GetMapping("/copy/{notesId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> copyNotes(@PathVariable int notesId) throws Exception;
	

}
