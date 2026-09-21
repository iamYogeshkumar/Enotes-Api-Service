package com.enotes.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.enotes.entity.Notes;
import com.enotes.repo.NotesRepositories;

@Component
public class NotesScheduler {
	
	private NotesRepositories notesRepositories;
 
	
	
	@Scheduled(cron = "0 0 0 * * *")
	public void deleteNotesSchedular() {
		
		LocalDateTime cutOfDate = LocalDateTime.now().minusDays(7);
		List<Notes> deleteNotes=notesRepositories.findByIsDeletedAndDeletedOnBefore(true,cutOfDate);
	    notesRepositories.deleteAll(deleteNotes);
	}
}
