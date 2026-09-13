package com.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.enotes.dto.EmailRequest;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String mailFrom;

	public void send(EmailRequest emailRequest) throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(mailFrom,emailRequest.getTitle());
        helper.setTo(emailRequest.getTo());
        helper.setSubject(emailRequest.getSubject());
        
        // discription send karne
        helper.setText(emailRequest.getMessage(),true);
        
        mailSender.send(mimeMessage);
		return ;
	}

}
