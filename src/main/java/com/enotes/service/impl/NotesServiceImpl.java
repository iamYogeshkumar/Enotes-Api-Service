package com.enotes.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesDto.CategoryDto;
import com.enotes.entity.Notes;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repo.CategoryRepository;
import com.enotes.repo.NotesRepositories;
import com.enotes.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {
	
	@Autowired
	private NotesRepositories notesRepositories;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public boolean saveNotes(NotesDto notesDto) throws ResourceNotFoundException {
		// category validation
		checkCategoryExist(notesDto.getCategory());
		
		
		Notes notes = mapper.map(notesDto, Notes.class);
		Notes save = notesRepositories.save(notes);
		if(!ObjectUtils.isEmpty(save)) {
		     return true;	
		}
		
		return false;
	}

	private void checkCategoryExist(CategoryDto category) throws ResourceNotFoundException {
		categoryRepository.findById(category.getId()).orElseThrow(()->new ResourceNotFoundException("Given category Id= "+category.getId()+ " does not exist"));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		List<Notes> all = notesRepositories.findAll();
		List<NotesDto> list = all.stream().map(n-> mapper.map(n, NotesDto.class)).toList();
		
		return list;
	}

}
