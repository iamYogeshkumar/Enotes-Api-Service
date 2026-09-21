package com.enotes.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordChangeReq {

	private String oldPassword;
	
	private String newPassword;
}
