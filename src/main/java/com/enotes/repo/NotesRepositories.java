package com.enotes.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Notes;

public interface NotesRepositories extends JpaRepository<Notes, Integer> {

}
