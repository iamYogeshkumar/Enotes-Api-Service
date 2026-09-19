package com.enotes.service;

import com.enotes.dto.PasswordChangeReq;

public interface UserService {

	void changePassword(PasswordChangeReq changeReq);
}
