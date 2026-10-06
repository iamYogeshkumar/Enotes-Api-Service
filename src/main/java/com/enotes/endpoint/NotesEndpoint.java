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

import static com.enotes.util.Constant.ROLE_USER;
import static com.enotes.util.Constant.ROLE_ADMIN;
import static com.enotes.util.Constant.DEFAULT_PAGENO;
import static com.enotes.util.Constant.DEFAULT_PAGE_SIZE;
import static com.enotes.util.Constant.ROLE_ADMIN_USER;
import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.NotesDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notes Endpoint",description = "All notes endponit")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {
	
	@Operation(summary = "Save the notes",tags = {"Notes Endpoint"},description = "User can save the notes")
	@PostMapping(value="/",consumes = {"multipart/form-data"})
	@Parameter(description = "Json String notes",required = true,
	           content=@Content(schema = @Schema(implementation = NotesDto.class))
			)
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>  saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file)throws Exception;
	
	@Operation(summary = "Download Uploaded File",tags = {"Notes Endpoint"},description = "Download file")
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception, IOException;
	
	
	@Operation(summary = "Get All Notes",tags = {"Notes Endpoint"},description = "Admin can see All the Notes")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?>  getAllNotes();
	
	@Operation(summary = "log User can see its all Notes ",tags = {"Notes Endpoint"},description = "")
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>  getAllNotesByUser(
			@RequestParam(name = "pageNo", defaultValue =DEFAULT_PAGENO) int pageNo ,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
			 @AuthenticationPrincipal CustomUserDetails userDetails
			);
	
	
	@Operation(summary = "Log user can search notes",tags = {"Notes Endpoint"},description = "")
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?>  searchNotes(
			@RequestParam(name="searchKeyword", defaultValue = "") String searchKeyword,
			@RequestParam(name = "pageNo", defaultValue =DEFAULT_PAGENO) int pageNo ,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
			 @AuthenticationPrincipal CustomUserDetails userDetails
			);
	
	@Operation(summary = "log user can delete save notes",tags = {"Notes Endpoint"},description = "Delete notes by user")
	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "restore the delete note",tags = {"Notes Endpoint"},description = "restore the delete notes from recycle bin")
	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Get notes from the recycle bin",tags = {"Notes Endpoint"},description = "Get notes from the recycle bin")
	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserRecycleBinNotes( @AuthenticationPrincipal CustomUserDetails userDetails) throws Exception;
	
	@Operation(summary = "hard delete notes",tags = {"Notes Endpoint"},description = "hard delete notes")
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Empty Recycle Bin",tags = {"Notes Endpoint"},description = "log user can Empty Recycle Bin")
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyRecycleBin(@AuthenticationPrincipal CustomUserDetails details) throws Exception;
	
	@Operation(summary = "Favourite Note",tags = {"Notes Endpoint"},description = "User Favourite Note")
	@GetMapping("/fav/{notesId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> favouriteNote(@PathVariable int notesId,@AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception;
	
	@Operation(summary = "Unfavourite Note",tags = {"Notes Endpoint"},description = "User Unfavourite Note")
	@DeleteMapping("/un-fav/{notesId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> unfavouriteNote(@PathVariable int notesId) throws Exception;
	
	@Operation(summary = "Get All favourite Note",tags = {"Notes Endpoint"},description = "Get All User favourite Note")
	@GetMapping("/fav-note")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllfavouriteNote() throws Exception;
	
	@Operation(summary = "Copy Notes",tags = {"Notes Endpoint"},description = "copy Notes by User")
	@GetMapping("/copy/{notesId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNotes(@PathVariable int notesId) throws Exception;
	

}
