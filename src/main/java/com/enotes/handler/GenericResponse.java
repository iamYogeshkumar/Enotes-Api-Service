package com.enotes.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericResponse {

	private int status; //status code
	private String msg;    // save successfully
	private Object data;    //data
	private HttpStatus responseStatus;
	
	public ResponseEntity<?> create(){
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("status", status);
		map.put("msg", msg);
		if(!ObjectUtils.isEmpty(data)) {
			map.put("data", data);
		}
		return new ResponseEntity<>(map,responseStatus);
	}
}
