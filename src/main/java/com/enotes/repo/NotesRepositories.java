package com.enotes.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.enotes.entity.Notes;

public interface NotesRepositories extends JpaRepository<Notes, Integer> {

	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId,PageRequest of);

	List<Notes> findByCreatedByAndIsDeletedTrue(Integer id);

	List<Notes> findByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOfDate);
	
	@Query("select n from Notes n where ( Lower(n.title) like lower(concat('%',:searchKeyword,'%')) "+
	        "or Lower(n.discription) like lower(concat('%',:searchKeyword,'%')) "+
			"or Lower(n.category.name) like lower(concat('%',:searchKeyword,'%')) ) "+
	        "and n.isDeleted=false "+
			"and n.createdBy=:userId "
	)
	Page<Notes> searchNotes( @Param("searchKeyword") String searchKeyword, @Param("userId") Integer userId, Pageable pageable);

}
