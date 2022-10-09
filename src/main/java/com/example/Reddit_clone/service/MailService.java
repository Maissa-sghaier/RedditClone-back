package com.example.Reddit_clone.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.Reddit_clone.exception.SpringRedditException;
import com.example.Reddit_clone.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	private final JavaMailSender mailSender;
	private final MailContentBuilderService mailContentBuilderService;
	
	@Async
	void sendMail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("springreddit@email.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilderService.build(notificationEmail.getBody()));
		};
		try {
			mailSender.send(messagePreparator);
			log.info("Activation email sent");
		} catch (MailException e) {
			log.error("Exception occured when sending mail" , e);
			throw new SpringRedditException("Exception occured when sending mail to" + notificationEmail.getRecipient());
		}
	}

}
