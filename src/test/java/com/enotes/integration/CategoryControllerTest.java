package com.enotes.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.LoginRequest;
import com.enotes.entity.Category;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;


@SpringBootTest
@ActiveProfiles("dev")
@AutoConfigureMockMvc
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private JsonMapper  objectMapper;
	
	private CategoryDto categoryDto = null;

	private Category category = null;

		
	@BeforeEach
	public void initialize() {
		categoryDto = CategoryDto.builder().id(null).name("Java Notes By Durga").discription("Java Notes").isActive(true)
				.build();

		category=new Category();
		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());
		category.setDiscription(categoryDto.getDiscription());
		category.setIsActive(categoryDto.getIsActive());	
		
	}
	
	@Test
	public void testSaveCategory() throws JacksonException, Exception {
		String token = generateToken("ys4139887@gmail.com", "becoder");
		
		mockMvc.perform(post("/api/v1/category/save-category")
				        .contentType(MediaType.APPLICATION_JSON)
				        .content(objectMapper.writeValueAsString(categoryDto))
				        .header("Authorization", token)
				).andExpect(status().isCreated())
		         .andExpect(jsonPath("$.msg").value("Saved scucess"))
		         .andExpect(jsonPath("$.status").value("201"));
		         
	}
	
	public String generateToken(String email,String password) throws JacksonException, UnsupportedEncodingException, Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail(email);
		loginRequest.setPassword(password);
		
		String response = mockMvc.perform(post("/api/v1/auth/login")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(loginRequest))
		       
		).andExpect(status().isOk())
		 .andReturn().getResponse().getContentAsString();
		
		
		JsonNode root = objectMapper.readTree(response);
		String token = root.path("data").path("token").asText();
		
		
		return "Bearer "+token;
	}
	
}
