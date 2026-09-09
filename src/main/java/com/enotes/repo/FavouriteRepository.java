package com.enotes.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.FavouriteNote;

public interface FavouriteRepository extends JpaRepository<FavouriteNote, Integer> {

	List<FavouriteNote> findByUserId(Integer userId);

	
}
