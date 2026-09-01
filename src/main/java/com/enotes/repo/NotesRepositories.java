package com.enotes.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Notes;

public interface NotesRepositories extends JpaRepository<Notes, Integer> {

	Page<Notes> findByCreatedBy(Integer userId,PageRequest of);

}
