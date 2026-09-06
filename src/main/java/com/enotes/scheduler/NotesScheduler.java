package com.enotes.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotesScheduler {
 
	@Scheduled(fixedRate = 1000)
	public void deleteNotesSchedular() {
		int i=0;
		System.err.println("i "+i);
		i++;
	}
}
