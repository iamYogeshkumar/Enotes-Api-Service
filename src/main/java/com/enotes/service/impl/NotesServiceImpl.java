package com.enotes.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesDto.CategoryDto;
import com.enotes.dto.NotesResponse;
import com.enotes.entity.FileDetails;
import com.enotes.entity.Notes;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repo.CategoryRepository;
import com.enotes.repo.FileRepository;
import com.enotes.repo.NotesRepositories;
import com.enotes.service.NotesService;

import tools.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService {
	
	@Autowired
	private NotesRepositories notesRepositories;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Autowired
	private FileRepository fileRepository;
	
	

	@Override
	public boolean saveNotes(String notes,MultipartFile file) throws ResourceNotFoundException, IOException {
		// category validation
		
		//deserialize JSON content from given JSON content String.
		ObjectMapper objMapper = new ObjectMapper();
		NotesDto notesDto = objMapper.readValue(notes, NotesDto.class);
		
		
		// category validation
		checkCategoryExist(notesDto.getCategory());
		
		Notes noteEntity = mapper.map(notesDto, Notes.class);
		
		FileDetails fileDetails =saveFileDetails(file);
		
		if(!ObjectUtils.isEmpty(fileDetails)) {
			noteEntity.setFileDetails(fileDetails);
		}
		
		Notes save = notesRepositories.save(noteEntity);
		if(!ObjectUtils.isEmpty(save)) {
		     return true;	
		}
		
		return false;
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		
		if(!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
			
			List<String> extensionAllow = Arrays.asList("pdf","xlsx","jpg","jpeg");
			
			if(extensionAllow.contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
				throw new IllegalArgumentException("invalid file format ! supported type pdf,jpeg,ipe,xlsx");
			}
			
			FileDetails fileDetails = new FileDetails();
			
			String originalFilename = file.getOriginalFilename();
			String rndString = UUID.randomUUID().toString();
			
			fileDetails.setOriginalFileName(originalFilename);
			
			//upload file name
			String extension = FilenameUtils.getExtension(originalFilename);
			String uploadFileName=rndString + "." +extension;
			fileDetails.setUploadFileName(uploadFileName);
			
			//display file name
			String displayName=getDisplayName(originalFilename);
			fileDetails.setDisplayFileName(displayName);
			
			File fileObj = new File(uploadPath);
			if(!fileObj.exists()) {
				fileObj.mkdir();
			}
			
			//store path : Enotes_API_Service/notes/xjxjxj.pdf
			String storePath=uploadPath.concat(uploadFileName);
			fileDetails.setPath(storePath);
			
			//file size
			fileDetails.setFileSize(file.getSize());
			
			long copy = Files.copy(file.getInputStream(), Paths.get(storePath));
			if(copy>0) {
				FileDetails save = fileRepository.save(fileDetails);
				return save;	 
			}
		}
		return null;
	}

	private String getDisplayName(String originalFilename) {
		// java_notes.pdf
		String extension = FilenameUtils.getExtension(originalFilename);  //pdf
		String fileName = FilenameUtils.removeExtension(originalFilename);  //java_notes
		
		if(fileName.length()>8) {
			fileName=fileName.substring(0,7);
		}
		
		return fileName+"."+extension;
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

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws ResourceNotFoundException, IOException {
		FileInputStream fileInputStream = new FileInputStream(fileDetails.getPath());
		byte[] copyToByteArray = StreamUtils.copyToByteArray(fileInputStream);
		return copyToByteArray;
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws ResourceNotFoundException {
		FileDetails fileDetails = fileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("file id not found "));

		return fileDetails;
	}

	@Override
	public NotesResponse getAllNotesByUser(Integer userId,int pageNo,int pageSize) {
		PageRequest of = PageRequest.of(pageNo, pageSize);
//		PageRequest of = PageRequest.of(pageNo, 2);	
		Page<Notes> page = notesRepositories.findByCreatedBy(userId,of);
		List<NotesDto> notesDto = page.get().map(m->mapper.map(m, NotesDto.class)).toList();
		
		NotesResponse notesResponse = NotesResponse.builder().
				                      notes(notesDto)
				                      .totalElement(page.getTotalElements())
				                      .pageNo(page.getNumber())
				                      .pageSize(page.getSize())
				                      .totalPages(page.getTotalPages())
				                      .isFirst(page.isFirst())
				                      .isLast(page.isLast())
				                      .build();
				                      
		return notesResponse;
	}

}
