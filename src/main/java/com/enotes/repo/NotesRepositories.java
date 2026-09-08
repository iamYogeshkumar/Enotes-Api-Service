package com.enotes.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Notes;

public interface NotesRepositories extends JpaRepository<Notes, Integer> {

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId,PageRequest of);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer id);

	List<Notes> findByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOfDate);

}
