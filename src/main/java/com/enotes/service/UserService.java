package com.enotes.service;

import com.enotes.dto.PasswordChangeReq;
import com.enotes.dto.PwdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	void changePassword(PasswordChangeReq changeReq);

	void sendEmailPwdReset(String email,HttpServletRequest servletRequest) throws Exception;

	void verifyPasswordResetLink(int uid, String resetCode) throws Exception;

	void resetPwd(PwdResetRequest pwdResetRequest) throws Exception;
}
